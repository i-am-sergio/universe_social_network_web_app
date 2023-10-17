package com.unsa.backend.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Long> {
    List<PostModel> findByUserId(String userId);
}
