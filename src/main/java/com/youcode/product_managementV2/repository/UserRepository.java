package com.youcode.product_managementV2.repository;

import com.youcode.product_managementV2.Entity.User;
import com.youcode.product_managementV2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
