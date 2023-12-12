package com.unsa.backend.posts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test Repository")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        System.out.println("Setup...................");
    }

    /**
     * Test case for getting a post by its ID from the repository.
     */
    @DisplayName("Test get post by id")
    @Test
    void testGetPostById() {
        // given
        PostModel post = null;
        // when
        post = postRepository.findById(1L).orElse(null);
        // then
        assertNull(post);
    }

    /**
     * Test case for getting all posts from the repository.
     */
    @DisplayName("Test get all posts")
    @Test
    void testGetPosts() {
        // given
        List<PostModel> posts = null;
        // when
        posts = postRepository.findAll();
        // then
        assertNotNull(posts);
    }
}
