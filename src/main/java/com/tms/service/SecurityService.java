package com.tms.service;

import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class SecurityService {
    
    public final SecurityRepository securityRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
    }

    public Boolean registration(RegistrationRequestDto registrationRequestDto) {
        try {
            if (securityRepository.isLoginUsed(registrationRequestDto.getLogin())){
                return false;
            }
            return  securityRepository.registration(registrationRequestDto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
