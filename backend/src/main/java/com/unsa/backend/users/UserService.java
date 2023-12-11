package com.unsa.backend.users;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    final UserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel getUser(Long id) {
        try {
            UserModel user = userRepository.findById(id).orElse(null);
            if (user != null) {
                // Elimina la contraseña por razones de seguridad
                user.setPassword(null);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error para fines de depuración
        }
        return null;
    }

    public UserModel updateUser(Long id, UserModel updatedUser) {
        try {
            UserModel user = userRepository.findById(id).orElse(null);
            if (user != null) {
                // Actualiza los campos relevantes de user con los datos de updatedUser
                // Implementa la lógica de actualización según tus requisitos
                user.setFirstname(updatedUser.getFirstname());
                user.setLastname(updatedUser.getLastname());
                // Actualiza otros campos aquí
                UserModel updatedUserResult = userRepository.save(user);
                updatedUserResult.setPassword(null); // Elimina la contraseña por razones de seguridad
                return updatedUserResult;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error para fines de depuración
        }
        return null;
    }

    public UserModel deleteUser(Long id) {
        try {
            UserModel user = userRepository.findById(id).orElse(null);
            if (user != null) {
                userRepository.delete(user); // Elimina el usuario
                user.setPassword(null); // Elimina la contraseña por razones de seguridad
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void followUser(Long followerId, Long targetUserId) {
        UserModel follower = userRepository
                                .findById(followerId)
                                .orElseThrow(() -> 
                                    new EntityNotFoundException("Follower not found"));
        UserModel targetUser = userRepository
                                .findById(targetUserId)
                                .orElseThrow(() -> 
                                    new EntityNotFoundException("Target user not found"));
        // Agregar seguidor
        if (!targetUser.getFollowers().contains(followerId)) {
            targetUser.getFollowers().add(followerId);
            userRepository.save(targetUser);
        }
        // Agregar a lista de following
        if (!follower.getFollowing().contains(targetUserId)) {
            follower.getFollowing().add(targetUserId);
            userRepository.save(follower);
        }
    }

    public void unfollowUser(Long followerId, Long targetUserId) {
        UserModel follower = userRepository
                                .findById(followerId)
                                .orElseThrow(() -> 
                                    new EntityNotFoundException("Follower not found"));
        UserModel targetUser = userRepository
                                .findById(targetUserId)
                                .orElseThrow(() -> 
                                    new EntityNotFoundException("Target user not found"));

        // Quitar seguidor
        targetUser.getFollowers().remove(followerId);
        userRepository.save(targetUser);

        // Quitar de la lista de following
        follower.getFollowing().remove(targetUserId);
        userRepository.save(follower);
    }
}
