package com.unsa.backend.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostRepository;
import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;

@Service
public class TimelineService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public TimelineService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<PostModel> getTimelinePosts(Long userId) {
        // Obtener el usuario actual como Optional<UserModel>
        Optional<UserModel> currentUserOptional = userRepository.findById(userId);

        // Verificar si el usuario está presente antes de realizar operaciones
        if (currentUserOptional.isPresent()) {
            UserModel currentUser = currentUserOptional.get();
            List<PostModel> currentUserPosts = postRepository.findByUserId(currentUser.getId());

            // Obtener publicaciones de usuarios seguidos
            List<PostModel> followingPosts = postRepository.findByUserIdIn(currentUser.getFollowing());

            // Combinar y ordenar las publicaciones
            List<PostModel> timelinePosts = new ArrayList<>();
            timelinePosts.addAll(currentUserPosts);
            timelinePosts.addAll(followingPosts);
            timelinePosts = timelinePosts.stream()
                 .filter(post -> post.getCreatedAt() != null)
                 .sorted(Comparator.comparing(PostModel::getCreatedAt).reversed())
                 .collect(Collectors.toList());
            return timelinePosts;
        } else {
            // Manejar el caso en que el usuario no está presente
            return new ArrayList<>(); // o lanzar una excepción, según tus requisitos
        }
    }
}