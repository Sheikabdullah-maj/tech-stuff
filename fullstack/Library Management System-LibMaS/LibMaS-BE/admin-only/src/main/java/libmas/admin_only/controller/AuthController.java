package libmas.admin_only.controller;

import libmas.admin_only.dto.AuthTokenResponseDTO;
import libmas.admin_only.service.CustomUserDetailsService;
import libmas.admin_only.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public AuthTokenResponseDTO authenticate(@RequestBody libmas.admin_only.dto.AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid credentials", e);
        }

        String jwtToken = jwtUtil.generateToken(authRequest.getUsername());
        return AuthTokenResponseDTO.builder().userName(authRequest.getUsername()).jwtToken(jwtToken).build();
    }
}

