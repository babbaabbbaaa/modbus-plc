package com.demo.configuration;


import com.demo.authentication.Role;
import com.demo.authentication.RoleService;
import com.demo.authentication.User;
import com.demo.authentication.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.HashSet;

@Slf4j
@Configuration
public class InitialDataLine {

    private final UserService userService;
    private final RoleService roleService;

    public InitialDataLine(UserService userService, RoleService roleService) {
        this.userService = userService;

        this.roleService = roleService;
    }

    @Bean
    public CommandLineRunner initUserData() {
        return args -> {
            Role role = roleService.getRoleByName("ADMIN");
            try {
                userService.loadUserByUsername("12345678");
                if (null == role) {
                    role = new Role();
                    role.setRoleName("ADMIN");
                    roleService.addRole(role);
                    log.info("Initiate role [ADMIN] for system.");
                }
            } catch (UsernameNotFoundException e) {
                User user = new User();
                user.setUsername("管理员");
                user.setCardNumber("12345678");
                user.setRoles(new HashSet<>(Collections.singletonList(role)));
                userService.addUser(user);
                log.info("Initiate user [管理员] for system.");
            }



        };
    }
}
