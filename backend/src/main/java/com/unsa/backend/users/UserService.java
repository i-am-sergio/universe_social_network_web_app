package com.unsa.backend.users;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    public List<UserModel> getUsers();

    public UserModel getUser(Long id);

    public UserModel updateUser(Long id, UserModel updatedUser);

    public UserModel deleteUser(Long id);

    public void followUser(Long followerId, Long targetUserId);

    public void unfollowUser(Long followerId, Long targetUserId);
}
