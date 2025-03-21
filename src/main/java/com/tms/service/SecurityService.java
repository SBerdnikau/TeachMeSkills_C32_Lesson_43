package com.tms.service;

import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class SecurityService {
    
    public final SecurityRepository securityRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
    }

    public Optional<User> registration(RegistrationRequestDto registrationRequestDto) {
        try {
            Optional<Long> userId = securityRepository.registration(registrationRequestDto);
            if (userId.isEmpty()) {
                return Optional.empty();
            }
            return userRepository.getUserById(userId.get());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
