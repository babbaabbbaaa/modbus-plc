package com.demo.authentication;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "plc_user")
public class User {

    @Id
    private int id;
    private String username;
    private String password;

}
