package com.demo.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "from User where cardNumber = :#{#cardNumber}")
    User findUserByCardNo(String cardNumber);
}
