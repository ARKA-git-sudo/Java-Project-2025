package com.example.ecommerce.service;

//import com.example.ecommerce.model.User;
//import com.example.ecommerce.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    public User registerUser(User user) {
//        try
//        {
//            User newUser = userRepository.save(user);
//            System.out.println("User Added to database");
//            return newUser;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User loginUser(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if(user!=null && user.getPassword().equals(password))
//        {
//            return user;
//        }
//        return null;
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//}







import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            // Check if phone already exists
            if (user.getPhone() != null && userRepository.existsByPhone(user.getPhone())) {
                throw new RuntimeException("Phone number already exists");
            }

            // Encrypt password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());

            User newUser = userRepository.save(user);
            System.out.println("User registered successfully: " + newUser.getEmail());
            return newUser;
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            return user;
        }

        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}