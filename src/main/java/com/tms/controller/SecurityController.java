package com.tms.controller;

import com.tms.exception.AgeException;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/security")
public class SecurityController {

    public SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid RegistrationRequestDto requestDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                if (Objects.equals(error.getCode(), "CustomAge")) {
                    throw new AgeException(error.getDefaultMessage());
                }
                System.out.println(error);
            }
            System.out.println(bindingResult.getAllErrors());
            return "registration";
        }

        Optional<User> resultUser = securityService.registration(requestDto);
        if (resultUser.isPresent()) {
            model.addAttribute("user", resultUser.get());
            return "user";
        }
        return "registration";
    }
}