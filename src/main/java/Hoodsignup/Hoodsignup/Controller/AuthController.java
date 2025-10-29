package Hoodsignup.Hoodsignup.Controller;

import Hoodsignup.Hoodsignup.DTOs.ApiResponse;
import Hoodsignup.Hoodsignup.DTOs.LoginRequest;
import Hoodsignup.Hoodsignup.Entity.User;
import Hoodsignup.Hoodsignup.Repository.UserRepository;
import Hoodsignup.Hoodsignup.Security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid email or password!", false));
        }

        User user = userOpt.get();

        if (!encoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid email or password!", false));
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity
                .ok(new ApiResponse("Login successful! Token: " + token, true));
    }
}

