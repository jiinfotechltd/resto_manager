function showToast(msg, msgType = "info") {
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toastr-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    switch (msgType) {
        case "success":
            toastr.success(msg);
            break;
        case "warning":
            toastr.warning(msg);
            break;
        case "error":
            toastr.error(msg);
            break;
        default:
            toastr.info(msg);
            break;
    }
}

function addLoader(components) {
    for (let componentName of components) {
        let notificationDiv = document.querySelector('[td-component="' + componentName + '"]');
        notificationDiv.innerHTML = `<div class="spinner-border text-primary" role="status"><span class="visually-hidden">Loading...</span></div>`;
        notificationDiv.classList.add("d-flex", "justify-content-center", "align-items-center");
    }
}

function cleanupBodyStyles() {
    document.body.style.overflow = '';
    document.body.style.paddingRight = '';
}

$(document).on('hidden.bs.modal', '.modal', function () {
    cleanupBodyStyles();
});

$(document).ready(function () {
    const tdRefreshComponents = $('body').attr('td-refresh').toString().split(",");
    addLoader(tdRefreshComponents)
    tdRefreshComponents.forEach((tdVal) => {
        let div = $('[td-component="' + tdVal + '"]');
        let sectionRoute = div.attr('td-component-url');
        $.get(sectionRoute, function (response) {
            $(div).replaceWith(response);
        });
    })
});


$.ajaxSetup({
    cache: false
});

$(document).on('submit', 'form.ajax-form', function (e) {
    e.preventDefault();

    let form = $(this);
    const formData = new FormData(this);
    let tdRefreshValues = form.attr('td-refresh').toString().split(",");
    addLoader(tdRefreshValues)

    let headers = {};

    let csrf = form.find('input[name="_csrf"]');
    if (csrf.length > 0) {
        headers['X-CSRF-TOKEN'] = csrf.val();
    }

    $.ajax({
        url: form.attr('action'),
        method: 'POST',
        headers: headers,
        data: formData,
        processData: false,
        contentType: false,
        success: function (responseString) {

            let response = JSON.parse(responseString)
            let params = JSON.parse(response.parameters)

            if (response.refresh !== null && response.refresh !== "") {
                let newRefreshValues = response.refresh.toString().split(",")
                tdRefreshValues = [...tdRefreshValues, ...newRefreshValues];
            }

            showToast(response.msg, response.msgType)

            if (response.msgType === "success") {
                let modal = form.closest(".modal");
                if (modal.length) {
                    modal.modal("hide");
                    $(".modal-backdrop").remove();
                }
            }

            tdRefreshValues.forEach((tdVal) => {
                let div = $('[td-component="' + tdVal + '"]');
                let queryParams = Object.entries(params).map(([key, value]) => {
                    if (Array.isArray(value)) {
                        return value.map(v => `${encodeURIComponent(key)}=${encodeURIComponent(v)}`).join("&");
                    }
                    return `${encodeURIComponent(key)}=${encodeURIComponent(value)}`;
                }).join("&");

                let route = div.attr('td-component-url')
                let sectionRoute = route.includes('?') ? route + queryParams : route + "?" + queryParams;

                $.get(sectionRoute, function (htmlResponse) {
                    $(div).replaceWith(htmlResponse);
                });
            });
        },
        error: function (xhr, err) {
        }
    });
});