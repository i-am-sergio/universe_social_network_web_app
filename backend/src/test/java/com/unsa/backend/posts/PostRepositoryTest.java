package com.unsa.backend.posts;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.AllArgsConstructor;

/**
 * This class contains unit tests for the PostRepository class.
 */
@DisplayName("Test Repository")
@DataJpaTest
@AllArgsConstructor
public class PostRepositoryTest {
    
    private final PostRepository postRepository;

    // private PostModel post;

    @BeforeEach
    void setup() {
        System.out.println("Setup...................");
        // post = PostModel.builder()
        //     .userId(1L)
        //     .desc("Test description 1")
        //     .build();
    }

    /**
     * Test case for getting all posts from the repository.
     */
    @DisplayName("Test get all posts")
    @Test
    public void testGetPosts() {
        // given
        List<PostModel> posts = null; 
        // when
        posts = postRepository.findAll();
        // then
        assertNotNull(posts);
    }

    /**
     * Test case for getting a post by its ID from the repository.
     */
    @DisplayName("Test get post by id")
    @Test
    public void testGetPostById() {
        // given
        PostModel post = null;
        // when
        post = postRepository.findById(1L).orElse(null);
        // then
        assertNotNull(post);
    }

}
