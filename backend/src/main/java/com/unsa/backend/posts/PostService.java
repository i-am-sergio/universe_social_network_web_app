package com.unsa.backend.posts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unsa.backend.users.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public List<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel getPostById(Long id) {
        Optional<PostModel> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public PostModel createPost(PostModel newPost) {
        return postRepository.save(newPost);
    }

    public void updatePost(PostModel existingPost, PostModel updatedPost) {
        // Actualizar el post existente con los datos del post actualizado
        existingPost.setDesc(updatedPost.getDesc());
        existingPost.setLikes(updatedPost.getLikes());
        existingPost.setImage(updatedPost.getImage());
        // Guardar el post actualizado en la base de datos
        postRepository.save(existingPost);
    }

    public boolean deletePost(Long postId) {
        Optional<PostModel> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void addLike(PostModel post, String userId) {
        List<String> likes = post.getLikes();
        if (!likes.contains(userId)) {
            likes.add(userId);
            postRepository.save(post);
        }
    }

    @Transactional
    public void removeLike(PostModel post, String userId) {
        List<String> likes = post.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
            postRepository.save(post);
        }
    }

    public List<PostModel> getTimelinePosts(String userId) {
        return null;
        // try {
        //     List<PostModel> currentUserPosts = postRepository.findByUserId(userId);
        //     List<PostModel> followingPosts = userRepository.findFollowingPosts(userId);
        //     // Combine and sort the posts
        //     List<PostModel> timelinePosts = combineAndSortPosts(currentUserPosts, followingPosts);

        //     return timelinePosts;
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     throw new RuntimeException("Error retrieving timeline posts");
        // }
    }

    private List<PostModel> combineAndSortPosts(List<PostModel> currentUserPosts, List<PostModel> followingPosts) {
        List<PostModel> combinedPosts = new ArrayList<>();

        // Combina los posts del usuario actual y los de los usuarios seguidos
        combinedPosts.addAll(currentUserPosts);
        combinedPosts.addAll(followingPosts);

        // Ordena la lista combinada por createdAt en orden descendente
        Collections.sort(combinedPosts, Comparator.comparing(PostModel::getCreatedAt).reversed());

        return combinedPosts;
    }


}
