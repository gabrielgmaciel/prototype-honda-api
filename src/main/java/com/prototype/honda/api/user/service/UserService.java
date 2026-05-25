package com.prototype.honda.api.user.service;

import com.prototype.honda.api.auth.dto.Login;
import com.prototype.honda.api.auth.service.JwtService;
import com.prototype.honda.api.exception.exceptions.BusinessException;
import com.prototype.honda.api.exception.exceptions.NotAuthorizedException;
import com.prototype.honda.api.exception.exceptions.NotFoundException;
import com.prototype.honda.api.user.model.User;
import com.prototype.honda.api.user.repository.UserRepository;
import com.prototype.honda.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        return convertUser(userRepository.save(user));
    }

    public User updateUser(User user, String userCode) {
        var data = userRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        data.setImageProfile(data.getImageProfile());
        data.setEmail(data.getEmail());
        data.setRoles(data.getRoles());

        data.setName(user.getName());
        data.setPhone(user.getPhone());
        data.setAddress(user.getAddress());

        if (verifyPassword(data.getPassword(), passwordEncoder.encode(user.getPassword()))) {
            data.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            data.setPassword(data.getPassword());
        }

        return convertUser(userRepository.save(data));
    }

    public User uploadProfileImage(String userId, MultipartFile file) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (file == null || file.isEmpty()) {
            user.setImageProfile(null);
        } else {
            user.setImageProfile(Utils.saveFile(file));
        }

        return convertUser(userRepository.save(user));
    }

    private User convertUser(User user) {
        if (user.getImageProfile() != null) {
            user.setImageProfile(Utils.getFile(user.getImageProfile()));
        }
        return user;
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
        return convertUser(userRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
    }

    private Boolean verifyPassword(String oldPassword, String password) {
        if (null == password) {
            return false;
        } else return !password.equals(oldPassword);
    }
}
