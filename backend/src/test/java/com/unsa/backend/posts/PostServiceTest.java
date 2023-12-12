package com.unsa.backend.posts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("Test get all posts")
    void testGetPosts() {
        // given
        List<PostModel> mockPosts = Arrays.asList(new PostModel(), new PostModel());
        when(postRepository.findAll()).thenReturn(mockPosts);

        // when
        List<PostModel> posts = postService.getPosts();

        // then
        assertNotNull(posts);
        assertEquals(mockPosts, posts);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test get post by id")
    void testGetPostById() {
        // given
        long postId = 1L;
        PostModel mockPost = new PostModel();
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        // when
        PostModel post = postService.getPostById(postId);
        // then
        System.out.println("Post: " + post);
        System.out.println("MockPost: " + mockPost);
        assertNotNull(post);
        assertEquals(mockPost, post);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("Test get post by id when not found")
    void testGetPostByIdNotFound() {
        // given
        long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when
        PostModel post = postService.getPostById(postId);

        // then
        assertNull(post);
        verify(postRepository, times(1)).findById(postId);
    }
}
