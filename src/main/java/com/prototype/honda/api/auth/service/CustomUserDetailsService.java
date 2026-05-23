package com.prototype.honda.api.auth.service;

import com.prototype.honda.api.auth.dto.UserPrincipal;
import com.prototype.honda.api.user.model.User;
import com.prototype.honda.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userCode)
            throws UsernameNotFoundException {

        User user = userRepository.findById(userCode)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        return new UserPrincipal(user);
    }
}
