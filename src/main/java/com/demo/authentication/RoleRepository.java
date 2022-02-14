package com.demo.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("from Role where roleName = :#{#roleName}")
    Role findRoleByRoleName(String roleName);
}
