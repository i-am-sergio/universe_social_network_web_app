package com.unsa.backend.poststest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostRepository;

@SpringBootTest
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
class PostModelTest {
    @MockBean
    private PostRepository postRepository;

    /**
     * Test case for create a Model Post.
     */
    @DisplayName("Test create a post")
    @Test
    void testCreatePost() {
        when(postRepository.save(any(PostModel.class))).thenAnswer(invocation -> {
            PostModel post = invocation.getArgument(0);
            post.setId(1L);
            post.setCreatedAt(new Date());
            return post;
        });

        PostModel post = new PostModel();
        post.setUserId(1L);
        post.setDesc("Test");
        post.setLikes(Arrays.asList(1L, 2L));
        post.setImage("Test");
        PostModel savedPost = postRepository.save(post);
        assertNotNull(savedPost);
        assertNotNull(savedPost.getCreatedAt());
        assertEquals(1L, savedPost.getUserId());
        assertEquals("Test", savedPost.getDesc());
        assertEquals(Arrays.asList(1L, 2L), savedPost.getLikes());
        assertEquals("Test", savedPost.getImage());
        when(postRepository.findById(1L)).thenReturn(Optional.of(savedPost));
        PostModel retrievedPost = postRepository.findById(1L).orElse(null);
        assertNotNull(retrievedPost);
        assertNotNull(retrievedPost.getCreatedAt());
        assertEquals(1L, retrievedPost.getUserId());
        assertEquals("Test", retrievedPost.getDesc());
        assertEquals(Arrays.asList(1L, 2L), retrievedPost.getLikes());
        assertEquals("Test", retrievedPost.getImage());
    }
}
