package com.FullstackEcommerce.Ecommerce.BinStore.security.repo;

import com.FullstackEcommerce.Ecommerce.BinStore.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}