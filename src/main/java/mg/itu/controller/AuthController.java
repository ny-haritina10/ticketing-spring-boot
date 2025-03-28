package mg.itu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import mg.itu.model.Client;
import mg.itu.service.AuthService;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String index() {
        return "auth/login-admin";
    }    

    @GetMapping("/admin-login")
    public String adminLogin() {
        return "auth/login-admin";
    }

    @GetMapping("/client-login")
    public String clientLogin() {
        return "auth/login-client";
    }

    @PostMapping("/admin/auth")
    public String adminAuth(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session) {
        boolean auth = authService.adminAuth(username, password);

        if (auth) {
            session.setAttribute("role", "ADMIN");
            session.setAttribute("is_auth", true);
            session.setAttribute("username", username);

            return "home/admin-dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials"); 
            return "redirect:/admin-login";  
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        if (authService.isAuthenticatedAsAdmin(session)) 
        { return "home/admin-dashboard"; }

        else 
        { return "redirect:/"; }
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "auth/login-admin";
    }

    @PostMapping("/client/auth")
    public String clientAuth(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session) {
        boolean auth = authService.clientAuth(username, password);
        Client client = authService.getClientByUsername(username);

        if (auth) {
            // base session attributes
            session.setAttribute("role", "CLIENT");
            session.setAttribute("is_auth", true);
            session.setAttribute("username", username);

            // clients details
            session.setAttribute("client", client);

            return "home/client-dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials"); 
            return "redirect:/client-login";  
        }
    }

    @GetMapping("/client/dashboard")
    public String clientDashboard(HttpSession session) {
        if (authService.isAuthenticatedAsClient(session)) 
        { return "home/client-dashboard"; }

        else 
        { return "redirect:/client-login"; }
    }

    @GetMapping("/client/logout")
    public String clientLogout(HttpSession session) {
        session.invalidate();
        return "auth/login-client";
    }
}