package com.demo.authentication;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "plc_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"roleName"})})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @Column(unique = true)
    private String roleName;

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Role) {
            return this.roleName.equals(((Role) obj).roleName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.roleName.hashCode();
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
