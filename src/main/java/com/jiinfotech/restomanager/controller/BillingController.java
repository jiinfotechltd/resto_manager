package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.billing.Billing;
import com.jiinfotech.restomanager.db.billing.BillingService;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishesServices;
import com.jiinfotech.restomanager.utils.BaseServices;
import com.jiinfotech.restomanager.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class BillingController extends BaseServices {

    @Autowired
    private OrderDishesServices orderDishesServices;
    @Autowired
    private BillingService billingService;

    @PostMapping(Routes.createBill)
    protected String createBilling(@ModelAttribute Billing billing, Model model) {
        Billing createdBilling = billingService.createBilling(billing);
        ByteArrayInputStream pdfStream = billingService.generateBill(billing.getTableId(), billing.getOrderId());
        try (FileOutputStream outputStream = new FileOutputStream("bills/bill-" + createdBilling.getId() + ".pdf")) {
            byte[] buffer = pdfStream.readAllBytes();
            outputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to generate PDF for the bill.");
            return "error";
        }
        return redirect(Routes.dashboard);
    }

    @GetMapping(Routes.bills)
    protected String getAllBillings(Model model) {
        List<Billing> billings = billingService.getAllBillings();
        model.addAttribute("billings", billings);
        return "bills";
    }

    @GetMapping(Routes.getBillById)
    protected String getBillingById(@PathVariable int id, Model model) {
        Billing billing = billingService.getBillingById(id);
        if (billing != null) {
            model.addAttribute("billing", billing);
            return "billing-details";
        } else {
            model.addAttribute("error", "Billing entry not found.");
            return "error";
        }
    }

    @PutMapping(Routes.billUpdate)
    protected String updateBilling(@PathVariable int id, @ModelAttribute Billing billing, Model model) throws Exception {
        Billing updatedBilling = billingService.updateBilling(id, billing);
        model.addAttribute("message", "Billing entry updated successfully.");
        model.addAttribute("billing", updatedBilling);
        return redirect(Routes.dashboard);
    }

    @DeleteMapping(Routes.billDelete)
    protected String deleteBilling(@PathVariable int id, Model model) throws Exception {
        billingService.deleteBilling(id);
        model.addAttribute("message", "Billing entry with ID " + id + " has been deleted.");
        return redirect(Routes.dashboard);
    }
}