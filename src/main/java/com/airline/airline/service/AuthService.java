package com.airline.airline.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.airline.airline.config.JwtUtil;
import com.airline.airline.dto.request.LoginRequest;
import com.airline.airline.dto.request.RegisterRequest;
import com.airline.airline.dto.response.LoginResponse;
import com.airline.airline.entity.User;
import com.airline.airline.producer.NotificationProducer;
import com.airline.airline.event.UserRegisteredEvent;
import com.airline.airline.repository.UserRepository;
import com.airline.airline.util.ResponseUtil;

@Service
public class AuthService {

    private final NotificationProducer notificationProducer;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, NotificationProducer notificationProducer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.notificationProducer = notificationProducer;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
              throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(User.Role.USER);
        user.setStatus(User.Status.ACTIVE);

        User savedUser = userRepository.save(user);

        // Publish event instead of direct email
        UserRegisteredEvent event = new UserRegisteredEvent(savedUser.getEmail(), savedUser.getName());
        notificationProducer.publishUserRegisteredEvent(event);

        return savedUser;
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
