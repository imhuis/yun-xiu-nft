package com.imhui.security.repository;

import com.imhui.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByEmail(String email);

}
