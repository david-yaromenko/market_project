package com.example.myShop.repository;

import com.example.myShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByEmail(String email);
}
