package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.services.PasswordService;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_AUTH })
public class AuthController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";

    public static final String AUTHORIZATION = "authorization";

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<?> register(@RequestBody User user) {
        ResponseEntity<?> response = userService.createUser(user);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> login(@RequestHeader Map<String, String> headers) {
        // Extract email and password from headers
        String decodedAuthHeader = new String(Base64.decodeBase64(headers.get(AUTHORIZATION).substring(6)));
        String email = decodedAuthHeader.substring(0, decodedAuthHeader.indexOf(":"));
        String password = decodedAuthHeader.substring(decodedAuthHeader.indexOf(":") + 1);

        // Perform additional checks or validations if needed

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent() && PasswordService.checkPassword(password, userOpt.get().getPassword())) {
            // Get the user ID from the User object
            String userId = String.valueOf(userOpt.get().getId());

            // Create token
            String token = tokenService.createTokenHeader(userOpt.get().getEmail(), "USER", userId);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }

}
