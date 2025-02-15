package com.application.exam.Authentication;


import com.application.exam.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register/etudiant")
    public ResponseEntity<AuthenticationResponse> registerEtu(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerEtu(registerRequest));
    }

    @PostMapping("/register/professeur")
    public ResponseEntity<AuthenticationResponse> registerPrf(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerProf(registerRequest));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @GetMapping("/data")
    public ResponseEntity<User> getUserData(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            System.out.println("obtenut: " + authService.getCurrentUser().getFirstName() + " " + authService.getCurrentUser().getLastName());
            return ResponseEntity.ok((User) userDetails);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/data/v1")
    public ResponseEntity<User> getUserDataV1() {
        try {
            System.out.println("obtenut: " + authService.getCurrentUser().getFirstName() + " " + authService.getCurrentUser().getLastName());
            return ResponseEntity.ok(authService.getCurrentUser()); // Calls getCurrentUser() in service
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
