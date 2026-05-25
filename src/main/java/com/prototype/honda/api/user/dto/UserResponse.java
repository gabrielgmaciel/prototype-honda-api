package com.prototype.honda.api.user.dto;

import com.prototype.honda.api.user.model.User;

import java.util.Collection;

public record UserResponse(
        Collection<User> users,
        Integer totalElements,
        Integer totalPages
) {
}
