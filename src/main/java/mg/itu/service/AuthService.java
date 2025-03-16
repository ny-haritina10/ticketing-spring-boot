package mg.itu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import mg.itu.model.Admin;
import mg.itu.model.Client;
import mg.itu.repository.AdminRepository;
import mg.itu.repository.ClientRepository;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClientRepository clientRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean adminAuth(String username, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(username);

        if (!admin.isPresent()) {
            return false;
        }

        return passwordEncoder.matches(password, admin.get().getPassword());
    }

    public boolean isAuthenticatedAsAdmin(HttpSession session) {
        if (session.getAttribute("role") != null || session.getAttribute("is_auth") != null) { 
            String role = (String) session.getAttribute("role");
            boolean isAuthenticated = (boolean) session.getAttribute("is_auth");

            if (isAuthenticated && role.equals("ADMIN"))
            { return true; }
        }

        return false;
    }

    public boolean clientAuth(String username, String password) {
        Optional<Client> client = clientRepository.findByEmail(username);

        if (!client.isPresent()) {
            return false;
        }

        return passwordEncoder.matches(password, client.get().getPasswordHash());
    }

    public boolean isAuthenticatedAsClient(HttpSession session) {
        if (session.getAttribute("role") != null || session.getAttribute("is_auth") != null) { 
            String role = (String) session.getAttribute("role");
            boolean isAuthenticated = (boolean) session.getAttribute("is_auth");

            if (isAuthenticated && role.equals("CLIENT"))
            { return true; }
        }

        return false;
    }

    public Client getClientByUsername(String username) 
    { return clientRepository.findByEmail(username).get(); }
}