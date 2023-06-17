package com.example.clickup_part_2.service;
import com.example.clickup_part_2.entity.User;
import com.example.clickup_part_2.entity.api.ApiResponse;
import com.example.clickup_part_2.entity.enums.SystemRoleName;
import com.example.clickup_part_2.entity.payload.RegisterDTO;
import com.example.clickup_part_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service

public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email));
    }

    public ApiResponse registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ApiResponse("this email is already exists", false);
        }
        User user = new User(
                registerDTO.getFullName(),
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                SystemRoleName.SYSTEM_ROLE_USER
        );
        int randomCode = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(randomCode).substring(0, 4));
        userRepository.save(user);
        sendMail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("New user saved successfully", true);
    }

    public Boolean sendMail(String sendingMail, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("samarkandps@gmail.com");
            simpleMailMessage.setTo(sendingMail);
            simpleMailMessage.setSubject("verifying the account");
            simpleMailMessage.setText(emailCode);
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (emailCode.equals(user.getEmailCode())){
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("The account is activated", true);
            }
            return new ApiResponse("The code is incorrect", false);
        }

        return new ApiResponse("The user is not exists", false);
    }
}
