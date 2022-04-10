package com.salvatore.dipalo.movies.api;

import com.salvatore.dipalo.movies.util.JWTUtil;
import com.salvatore.dipalo.movies.model.JwtResponse;
import com.salvatore.dipalo.movies.model.LoginCredentials;
import com.salvatore.dipalo.movies.model.RegistrationRequest;
import com.salvatore.dipalo.movies.model.db.User;
import com.salvatore.dipalo.movies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public JwtResponse registerHandler(@RequestBody @Valid RegistrationRequest request) {

        User user = userService.createUser(request);
        // Generating JWT
        String token = jwtUtil.generateToken(user);

        // Responding with JWT
        return new JwtResponse(token);
    }

    @PostMapping("/login")
    public JwtResponse loginHandler(@RequestBody @Valid LoginCredentials body) {
        try {
            // Creating the Authentication Token which will contain the credentials for authenticating
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            // Authenticating the Login Credentials
            authManager.authenticate(authInputToken);

            User user = userService.getUserByUsername(body.getUsername());

            // If this point is reached it means Authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(user);

            // Respond with the JWT
            return new JwtResponse(token);
        } catch (AuthenticationException authExc) {
            // Auhentication Failed
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
