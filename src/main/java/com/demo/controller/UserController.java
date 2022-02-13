package com.demo.controller;


import com.demo.authentication.Role;
import com.demo.authentication.RoleService;
import com.demo.authentication.User;
import com.demo.authentication.UserService;
import com.demo.model.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("all")
    public Response<List<User>> getUsers() {
        return Response.success(userService.getUsers());
    }

    @PostMapping("update")
    public Response<User> update(@RequestBody User user) {
        return Response.success(userService.addUser(user));
    }

    @GetMapping("roles")
    public Response<List<Role>> getRoles() {
        return Response.success(roleService.getRoles());
    }
}
