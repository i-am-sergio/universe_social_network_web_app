package com.unsa.backend.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
    Optional<UserModel> findByUsername(String username);

    // Método personalizado para verificar la existencia de un usuario por nombre de usuario
    boolean existsByUsername(String username);

    // Método para encontrar a los usuarios seguidos por un usuario específico
    // @Query("SELECT u.followedPosts FROM users u WHERE u.username = :userId")
    // List<PostModel> findFollowingPosts(String userId);
}
