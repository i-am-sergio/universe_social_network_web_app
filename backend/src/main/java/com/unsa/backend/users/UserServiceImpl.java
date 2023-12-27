package com.unsa.backend.users;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    final UserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel getUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setPassword(null);
                    return user;
                })
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public UserModel updateUser(Long id, UserModel updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstname(updatedUser.getFirstname());
                    user.setLastname(updatedUser.getLastname());
                    UserModel updatedUserResult = userRepository.save(user);
                    updatedUserResult.setPassword(null);
                    return updatedUserResult;
                })
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public UserModel deleteUser(Long id) {
        try {
            UserModel user = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
            user.setPassword(null);
            userRepository.delete(user);
            return user;
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(UserService.class);
            if (e instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) e;
            } else {
                logger.error("Internal Server Error: {}", e.getMessage());
                throw new IllegalStateException("Internal Server Error");
            }
        }
    }

    public void followUser(Long followerId, Long targetUserId) {
        UserModel follower = userRepository
                .findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
        UserModel targetUser = userRepository
                .findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Target user not found"));
        if (!targetUser.getFollowers().contains(followerId)) {
            targetUser.getFollowers().add(followerId);
            userRepository.save(targetUser);
        } else {
            throw new IllegalStateException("Follower already exists");
        }
        if (!follower.getFollowing().contains(targetUserId)) {
            follower.getFollowing().add(targetUserId);
            userRepository.save(follower);
        } else {
            throw new IllegalStateException("Already following the target user");
        }
    }
    
    public void unfollowUser(Long followerId, Long targetUserId) {
        UserModel follower = userRepository
                .findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
        UserModel targetUser = userRepository
                .findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Target user not found"));
        targetUser.getFollowers().remove(followerId);
        userRepository.save(targetUser);
        follower.getFollowing().remove(targetUserId);
        userRepository.save(follower);
    }
}
