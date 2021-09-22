package com.tencent.nft.mapper;

import com.tencent.nft.entity.security.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/22
 * @description:
 */
@Mapper
public interface UserMapper {

    int insert(User newUser);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByEmail(String email);
}
