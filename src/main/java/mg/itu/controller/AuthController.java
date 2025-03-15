package mg.itu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        return "redirect:/admin-login";
    }

    @GetMapping("/admin-login")
    public String login() {
        return "auth/login-admin";
    }

    @GetMapping("/client-login")
    public String clientLogin() {
        return "auth/login-client";
    }
    
    @GetMapping("/client/dashboard")
    public String dashboard() {
        return "home/client-dashboard"; 
    }
    
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "home/admin-dashboard"; 
    }
}