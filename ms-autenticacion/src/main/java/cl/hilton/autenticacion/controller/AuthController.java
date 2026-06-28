package cl.hilton.autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.hilton.autenticacion.dto.LoginRequest;
import cl.hilton.autenticacion.dto.LoginResponse;
import cl.hilton.autenticacion.dto.RegisterRequest;
import cl.hilton.autenticacion.dto.UsuarioResponse;
import cl.hilton.autenticacion.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import cl.hilton.common.security.JwtTokenProvider;
import cl.hilton.common.security.TokenBlacklistService;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
        @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        Date expiration = jwtTokenProvider.getExpirationFromToken(token);
        tokenBlacklistService.addToBlacklist(token, expiration);

        String email = jwtTokenProvider.getEmailFromToken(token);
        log.info("Logout exitoso para: {}", email);

        return ResponseEntity.ok(Map.of("message", "Sesión cerrada exitosamente"));
    }
}