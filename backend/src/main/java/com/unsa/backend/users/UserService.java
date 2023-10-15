package com.unsa.backend.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepository.findAll();
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

    // public class FollowUserException extends RuntimeException {
    // public FollowUserException(String message) {
    // super(message);
    // }
    // }

    // public UserModel followUser(Long id, Long user) {
    // try {
    // UserModel followUser = userRepository.findById(id).orElse(null);
    // UserModel followingUser = userRepository.findById(user).orElse(null);

    // if (followUser != null && followingUser != null) {
    // if (!followUser.getFollowers().contains(followingUser)) {
    // followUser.getFollowers().add(followingUser);
    // followingUser.getFollowing().add(followUser);
    // userRepository.save(followUser);
    // userRepository.save(followingUser);
    // return followUser;
    // } else {
    // // Lanza una excepción personalizada
    // throw new FollowUserException("You are already following this user");
    // }
    // } else {
    // // Lanza una excepción personalizada
    // throw new FollowUserException("User not found");
    // }
    // } catch (FollowUserException e) {
    // // Puedes personalizar la respuesta de error en función de tus necesidades
    // e.printStackTrace();
    // throw e;
    // }
    // }

    // public class UnfollowUserException extends RuntimeException {
    // public UnfollowUserException(String message) {
    // super(message);
    // }
    // }

    // public UserModel unfollowUser(Long id, Long _id) {
    // try {
    // UserModel unFollowUser = userRepository.findById(id).orElse(null);
    // UserModel unFollowingUser = userRepository.findById(_id).orElse(null);

    // if (unFollowUser != null && unFollowingUser != null) {
    // if (unFollowUser.getFollowers().contains(unFollowingUser)) {
    // unFollowUser.getFollowers().remove(unFollowingUser);
    // unFollowingUser.getFollowing().remove(unFollowUser);
    // userRepository.save(unFollowUser);
    // userRepository.save(unFollowingUser);
    // return unFollowUser;
    // } else {
    // // Lanza una excepción personalizada
    // throw new UnfollowUserException("You are not following this User");
    // }
    // } else {
    // // Lanza una excepción personalizada
    // throw new UnfollowUserException("User not found");
    // }
    // } catch (UnfollowUserException e) {
    // // Puedes personalizar la respuesta de error en función de tus necesidades
    // e.printStackTrace();
    // throw e;
    // }
    // }

}
