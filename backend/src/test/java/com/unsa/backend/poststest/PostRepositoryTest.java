package com.unsa.backend.poststest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostRepository;

@SpringBootTest
@DisplayName("Test Repository")
@ExtendWith(MockitoExtension.class)
class PostRepositoryTest {

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        postRepository = Mockito.mock(PostRepository.class);
    }

    /**
     * Test case for finding posts by user ID from the repository.
     */
    @DisplayName("Test find posts by user ID")
    @Test
    void testFindPostsByUserId() {
        // given
        long userId = 1L;
        List<PostModel> mockPosts = Arrays.asList(new PostModel(), new PostModel());
        when(postRepository.findByUserId(userId)).thenReturn(mockPosts);

        // when
        List<PostModel> posts = postRepository.findByUserId(userId);

        // then
        assertNotNull(posts);
        assertEquals(mockPosts, posts);
        verify(postRepository, times(1)).findByUserId(userId);
    }

    /**
     * Test case for finding posts by user ID when no posts are found.
     */
    @DisplayName("Test find posts by user ID")
    @Test
    void testFindPostsByUserIdNoResults() {
        // given
        long userId = 1L;
        when(postRepository.findByUserId(userId)).thenReturn(List.of());

        // when
        List<PostModel> posts = postRepository.findByUserId(userId);
        // then
        assertEquals(0, posts.size());
        verify(postRepository, times(1)).findByUserId(userId);
    }

    /**
     * Test case for finding posts by user ID from the repository.
     */
    @DisplayName("Test find posts by user ID")
    @Test
    void testFindPostsByUserIds() {
        // given
        List<Long> userIds = Arrays.asList(1L, 2L);
        List<PostModel> mockPosts = Arrays.asList(new PostModel(), new PostModel());
        when(postRepository.findByUserIdIn(userIds)).thenReturn(mockPosts);

        // when
        List<PostModel> posts = postRepository.findByUserIdIn(userIds);

        // then
        assertNotNull(posts);
        assertEquals(mockPosts, posts);
        verify(postRepository, times(1)).findByUserIdIn(userIds);
    }

    /**
     * Test case for finding posts by user IDs when no posts are found.
     */
    @DisplayName("Test find posts by user IDs")
    @Test
    void testFindPostsByUserIdsNoResults() {
        // given
        List<Long> userIds = Arrays.asList(1L, 2L);
        when(postRepository.findByUserIdIn(userIds)).thenReturn(List.of());

        // when
        List<PostModel> posts = postRepository.findByUserIdIn(userIds);
        // then
        assertEquals(0, posts.size());
        verify(postRepository, times(1)).findByUserIdIn(userIds);
    }
}
