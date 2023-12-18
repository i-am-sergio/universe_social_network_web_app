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

    private PostService postService;

    @Autowired
    public PostServiceTest(PostService postService) {
        this.postService = postService;
    }

    /**
     * Test case for finding all posts from the service.
     */
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

    /**
     * Test case for finding all posts from the service when the posts are Empty.
     */
    @DisplayName("Test get all posts")
    @Test
    void testGetPostsEmpty() {
        // given
        when(postRepository.findAll()).thenReturn(Arrays.asList());
        // when
        List<PostModel> posts = postService.getPosts();
        // then
        assertNotNull(posts);
        assertEquals(0, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    /**
     * Test case for finding all posts by user ID from the service.
     */
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

    /**
     * Test case for finding all posts by user ID from the service when the post is
     * not found.
     */
    @DisplayName("Test get post by id")
    @Test
    void testGetPostByIdNotFound() {
        // given
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        // when
        PostModel postFound = postService.getPostById(postId);
        // then
        assertNull(postFound);
        verify(postRepository, times(1)).findById(postId);
    }

    /**
     * Test case for creating a post from the service.
     */
    @DisplayName("Test create post")
    @Test
    void testCreatePost() {
        // given
        PostModel post = new PostModel();
        when(postRepository.save(post)).thenReturn(post);
        // when
        PostModel postCreated = postService.createPost(post);
        // then
        assertNotNull(postCreated);
        verify(postRepository, times(1)).save(post);
    }

    /**
     * Test case for updating a post from the service.
     */
    @DisplayName("Test update post")
    @Test
    void testUpdatePost() {
        // given
        Long postId = 1L;
        PostModel existingPost = new PostModel();
        existingPost.setId(postId);
        existingPost.setDesc("Existing post");
        existingPost.setLikes(Arrays.asList(1L, 2L));
        existingPost.setImage("existing-post.jpg");
        PostModel updatedPost = new PostModel();
        updatedPost.setId(postId);
        updatedPost.setDesc("Updated post");
        updatedPost.setLikes(Arrays.asList(1L, 2L, 3L));
        updatedPost.setImage("updated-post.jpg");
        when(postRepository.save(existingPost)).thenReturn(existingPost);
        // when
        postService.updatePost(existingPost, updatedPost);
        // then
        assertEquals(updatedPost.getDesc(), existingPost.getDesc());
        assertEquals(updatedPost.getLikes(), existingPost.getLikes());
        assertEquals(updatedPost.getImage(), existingPost.getImage());
        verify(postRepository, times(1)).save(existingPost);
    }

    /**
     * Test case for deleting a post from the service.
     */
    @DisplayName("Test delete post")
    @Test
    void testDeletePost() {
        // given
        Long postId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // when
        boolean deleted = postService.deletePost(postId);
        // then
        assertTrue(deleted);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    /**
     * Test case for deleting a post from the service when the post is not found.
     */
    @DisplayName("Test delete post")
    @Test
    void testDeletePostNotFound() {
        // given
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        // when
        boolean deleted = postService.deletePost(postId);
        // then
        assertFalse(deleted);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(0)).delete(any(PostModel.class));
    }

    /**
     * Test case for adding a like to a post from the service.
     */
    @DisplayName("Test add like")
    @Test
    void testAddLike() {
        // given
        Long postId = 1L;
        Long userId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        post.setLikes(Arrays.asList(2L, 3L));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);
        // when
        postService.addLike(post, userId);
        // then
        assertTrue(post.getLikes().contains(userId));
        verify(postRepository, times(1)).save(post);
    }

    /**
     * Test case for adding a like to a post from the service when the user already
     * liked the post.
     */
    @DisplayName("Test add like")
    @Test
    void testAddLikeAlreadyLiked() {
        // given
        Long postId = 1L;
        Long userId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        post.setLikes(Arrays.asList(1L, 2L));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // when
        postService.addLike(post, userId);
        // then
        assertTrue(post.getLikes().contains(userId));
        verify(postRepository, times(0)).save(post);
    }

    /**
     * Test case for removing a like from a post from the service.
     */
    @DisplayName("Test remove like")
    @Test
    void testRemoveLike() {
        // given
        Long postId = 1L;
        Long userId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        post.setLikes(Arrays.asList(1L, 2L));
        when(postRepository.save(post)).thenReturn(post);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // when
        postService.removeLike(post, userId);
        // then
        assertFalse(post.getLikes().contains(userId));
        verify(postRepository, times(1)).save(post);
    }

    /**
     * Test case for removing a like from a post from the service when the user
     * already removed the like from the post.
     */
    @DisplayName("Test remove like")
    @Test
    void testRemoveLikeAlreadyRemoved() {
        // given
        Long postId = 1L;
        Long userId = 1L;
        PostModel post = new PostModel();
        post.setId(postId);
        post.setLikes(Arrays.asList(3L, 2L));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // when
        postService.removeLike(post, userId);
        // then
        assertFalse(post.getLikes().contains(userId));
        verify(postRepository, times(0)).save(post);
    }

}
