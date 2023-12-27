package com.unsa.backend.users;

import java.util.List;


public interface UserService {
    public List<UserModel> getUsers();

    public UserModel getUser(Long id);

    public UserModel updateUser(Long id, UserModel updatedUser);

    public UserModel deleteUser(Long id);

    public void followUser(Long followerId, Long targetUserId);

    public void unfollowUser(Long followerId, Long targetUserId);
}

