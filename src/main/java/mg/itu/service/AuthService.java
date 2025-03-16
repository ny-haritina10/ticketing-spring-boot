package mg.itu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mg.itu.model.Admin;
import mg.itu.repository.AdminRepository;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean adminAuth(String username, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(username);

        if (!admin.isPresent()) {
            return false;
        }

        System.out.println("Raw password entered: " + password);
        System.out.println("Stored password from DB: " + admin.get().getPassword());

        return passwordEncoder.matches(password, admin.get().getPassword());
    }
}