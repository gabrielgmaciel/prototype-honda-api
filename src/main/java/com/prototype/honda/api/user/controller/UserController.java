package com.prototype.honda.api.user.controller;

import com.prototype.honda.api.auth.dto.UserPrincipal;
import com.prototype.honda.api.user.dto.UserResponse;
import com.prototype.honda.api.user.model.User;
import com.prototype.honda.api.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints relacionados à gestão de usuários")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestBody User user,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.updateUser(user, userPrincipal.getUser().getId()));
    }

    @PutMapping(value = "/upload/image/profile", consumes = "multipart/form-data")
    public ResponseEntity<User> uploadProfileImage(
            @RequestParam(name = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(userService.uploadProfileImage(user.getUser().getId(), image));
    }

    @GetMapping("/data")
    public ResponseEntity<User> getDataUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(userPrincipal.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.searchUsers(email, name, page, size));
    }

    @PutMapping("/update/access")
    public ResponseEntity<User> updateUserAccess(
            @RequestParam String userCode,
            @RequestParam String newAccess) {
        return ResponseEntity.ok(userService.updateUserAccess(userCode, newAccess));
    }

    @DeleteMapping("/close/account")
    public ResponseEntity<Void> closeAccount(
            @RequestParam(required = false) String userCode) {
        userService.deleteUser(userCode);
        return ResponseEntity.noContent().build();
    }

}
