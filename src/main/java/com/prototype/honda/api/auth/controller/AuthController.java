package com.prototype.honda.api.auth.controller;

import com.prototype.honda.api.auth.dto.Login;
import com.prototype.honda.api.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints relacionados à autenticação e gerenciamento de usuários")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(login));
    }
}
