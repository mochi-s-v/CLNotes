package com.vicky.CLNotesV1.Service;

import com.vicky.CLNotesV1.Entity.UserEntity;
import com.vicky.CLNotesV1.Repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
public class AdminInitializer {
    @Bean
    public CommandLineRunner createAdmin(UserRepo userRepo,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            String adminName = "admin";
            boolean adminExists = userRepo.findByUsername(adminName).isPresent();
            if (!adminExists) {
                UserEntity user = new UserEntity();
                user.setUsername(adminName);
                user.setEmail("admin@CLNotes");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setRole("ADMIN");
                userRepo.save(user);
                System.out.println("Default admin user created");
            }
            else {
                System.out.println("admin already present");
            }
        };
    }
}
