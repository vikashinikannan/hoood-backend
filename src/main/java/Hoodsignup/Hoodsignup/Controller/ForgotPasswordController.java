package Hoodsignup.Hoodsignup.Controller;

import Hoodsignup.Hoodsignup.DTOs.ApiResponse;
import Hoodsignup.Hoodsignup.Repository.UserRepository;
import Hoodsignup.Hoodsignup.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email) {
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Email not registered!", false));
        }

        String resetLink = "https://yourfrontend.com/reset-password?email=" + email;

        try {
            emailService.sendResetEmail(email, resetLink);
            return ResponseEntity.ok(new ApiResponse("Password reset link sent to your email.", true));
        } catch (Exception e) {
            e.printStackTrace(); // show actual error in console
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Failed to send email: " + e.getMessage(), false));
        }
    }
}

