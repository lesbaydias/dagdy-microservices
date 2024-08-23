package com.example.userservice.user.repository;

import com.example.userservice.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByUsername(String username);
    Optional<Users> findUsersByPhoneNumber(String phoneNumber);

}
