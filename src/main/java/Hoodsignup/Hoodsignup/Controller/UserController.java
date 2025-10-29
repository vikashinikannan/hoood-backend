package Hoodsignup.Hoodsignup.Controller;

import Hoodsignup.Hoodsignup.DTOs.ApiResponse;
import Hoodsignup.Hoodsignup.DTOs.UserSignupRequest;
import Hoodsignup.Hoodsignup.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserSignupRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(new ApiResponse("You have successfully signed up!", true));
    }
}
