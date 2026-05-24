package com.prototype.honda.api.user.service;

import com.prototype.honda.api.auth.dto.Login;
import com.prototype.honda.api.auth.service.JwtService;
import com.prototype.honda.api.exception.exceptions.BusinessException;
import com.prototype.honda.api.exception.exceptions.NotAuthorizedException;
import com.prototype.honda.api.exception.exceptions.NotFoundException;
import com.prototype.honda.api.user.model.User;
import com.prototype.honda.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("user"));
        return userRepository.save(user);
    }

    public String login(Login login) {
        if (!userRepository.existsByEmail(login.getEmail())) {
            throw new NotFoundException("Usuário não encontrado");
        }

        var data = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        if (!passwordEncoder.matches(login.getPassword(), data.getPassword())) {
            throw new NotAuthorizedException("Senha incorreta");
        }

        return jwtService.generateToken(data.getId(), data.getEmail(), data.getRoles());
    }

    public User getUserById(String userCode) {
        return userRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }
}
