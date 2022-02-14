package com.demo.security;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserModel {

    private String username;
    private Set<String> roles;

}
