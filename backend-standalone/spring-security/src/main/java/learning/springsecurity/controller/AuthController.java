package learning.springsecurity.controller;

import learning.springsecurity.dao.AppUserDTO;
import learning.springsecurity.model.AuthenticationRequest;
import learning.springsecurity.model.AuthenticationResponse;
import learning.springsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
            String email = authentication.getName();
            AppUserDTO userDTO = AppUserDTO.builder()
                    .firstName("Sheik")
                    .lastName("Jinna")
                    .build();
            String token = jwtUtil.createToken(userDTO);
            AuthenticationResponse loginRes = AuthenticationResponse.builder().userName(loginReq.getUserName())
                    .token(token)
                    .build();

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}