package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.utils.BaseServices;
import com.jiinfotech.restomanager.utils.Routes;
import com.jiinfotech.restomanager.utils.Templates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController extends BaseServices {

    @GetMapping(Routes.dashboard)
    public String showDashboard(Model model) {
        return Templates.index;
    }

    @GetMapping(Routes.login)
    public String getLogin(Model model) {

        return Templates.login;
    }

    @PostMapping(Routes.login)
    public String postLogin(Model model) {
       return redirect(Routes.dish);
    }
}
