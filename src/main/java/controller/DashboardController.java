package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import utils.Routes;

@Controller
public class DashboardController {

    @GetMapping(Routes.dashboard)
    public String showDashboard(Model model) {
        // Add attributes to the model if needed
        return "index"; // Ensure 'index.html' or 'index.jsp' exists in the correct templates folder
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // Ensure 'index.html' or 'index.jsp' exists in the correct templates folder
    }
}
