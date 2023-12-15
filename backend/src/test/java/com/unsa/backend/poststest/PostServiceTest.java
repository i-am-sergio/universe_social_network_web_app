package com.unsa.backend.poststest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostRepository;
import com.unsa.backend.posts.PostService;

@SpringBootTest
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @DisplayName("Test get all posts")
    @Test
    void testGetPosts() {
        // given
        when(postRepository.findAll()).thenReturn(Arrays.asList(new PostModel(), new PostModel()));
        // when
        List<PostModel> posts = postService.getPosts();
        // then
        assertNotNull(posts);
        assertEquals(2, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @DisplayName("Test get post by id")
    @Test
    void testGetPostById() {
        // given
        Long postId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // when
        PostModel postFound = postService.getPostById(postId);
        // then
        assertNotNull(postFound);
        assertEquals(postId, postFound.getId());
        verify(postRepository, times(1)).findById(postId);
    }
}
