package com.demo.controller;


import com.demo.authentication.Role;
import com.demo.authentication.RoleService;
import com.demo.authentication.User;
import com.demo.authentication.UserService;
import com.demo.model.Response;
import org.springframework.dao.DataIntegrityViolationException;
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

    @PostMapping("remove")
    public Response<Void> delete(@RequestBody User user) {
        userService.deleteUser(user);
        return Response.success();
    }

    @GetMapping("roles")
    public Response<List<Role>> getRoles() {
        return Response.success(roleService.getRoles());
    }

    @PostMapping("role")
    public Response<Role> updateRole(@RequestBody Role role) {
        return Response.success(roleService.addRole(role));
    }

    @DeleteMapping("role")
    public Response<Void> deleteRole(@RequestBody Role role) {
        try {
            roleService.deleteRole(role);
        } catch (DataIntegrityViolationException e) {
            return Response.fail(String.format("角色【%s】无法被删除，请先将该角色从对应用户中剔除！", role.getRoleName()));
        }
        return Response.success();
    }
}
