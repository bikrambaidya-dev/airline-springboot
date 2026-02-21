package com.airline.airline.service;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.airline.airline.config.JwtUtil;
import com.airline.airline.dto.request.LoginRequest;
import com.airline.airline.dto.request.RegisterRequest;
import com.airline.airline.dto.response.LoginResponse;
import com.airline.airline.entity.User;
import com.airline.airline.repository.UserRepository;
import com.airline.airline.util.ResponseUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public ResponseEntity<?> register(RegisterRequest user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseUtil.error("User already exists");
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setPhone(user.getPhone());
        newUser.setRole(User.Role.USER);
        newUser.setStatus(User.Status.ACTIVE);
        userRepository.save(newUser);

        // ✅ Generate JWT verification token
        String token = JwtUtil.generateToken(user.getEmail());
        emailService.sendVerificationEmail(user.getEmail(), token);

        return ResponseUtil.created(newUser, "User registered successfully");
    }

    public ResponseEntity<?> login(LoginRequest user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty() ||
                !passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {

            return ResponseUtil.error(
                    "Invalid email or password");
        }

        User dbUser = existingUser.get();

        String token = JwtUtil.generateToken(dbUser.getEmail());

        LoginResponse response = new LoginResponse(
                token,
                dbUser.getEmail(),
                dbUser.getRole().name());

        return ResponseUtil.success(response, "Login successful");
    }

}
