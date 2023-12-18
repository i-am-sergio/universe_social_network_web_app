package com.unsa.backend.timelinetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostRepository;
import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;
import com.unsa.backend.services.TimelineService;

@SpringBootTest
@DisplayName("Test Timeline Service")
@ExtendWith(MockitoExtension.class)
class TimelineServiceTest {

    private PostModel post1;
    private PostModel post2;
    private PostModel post3;
    private PostModel post4;
    private PostModel post5;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    private TimelineService timelineService;

    @Autowired
    public TimelineServiceTest(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @BeforeEach
    void setUp() {
        post1 = new PostModel(); post1.setCreatedAt(onCreate());
        post2 = new PostModel(); post2.setCreatedAt(onCreate());
        post3 = new PostModel(); post3.setCreatedAt(onCreate());
        post4 = new PostModel(); post4.setCreatedAt(onCreate());
        post5 = new PostModel(); post5.setCreatedAt(onCreate());
    }

    @Test
    @DisplayName("Test getTimelinePosts - User Present")
    void testGetTimelinePostsUserPresent() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList(2L, 3L));

        List<PostModel> currentUserPosts = Arrays.asList(post1, post2);
        List<PostModel> followingPosts = Arrays.asList(post3, post4, post5);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);
        
        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        assertEquals(currentUserPosts.size()+followingPosts.size(), resultTimelinePosts.size());
    }

    @Test
    @DisplayName("Test getTimelinePosts - User Not Present")
    void testGetTimelinePostsUserNotPresent() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList(2L, 3L));
        List<PostModel> currentUserPosts = Arrays.asList(post1, post2);
        List<PostModel> followingPosts = Arrays.asList(post3, post4, post5);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);
        
        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        assertEquals(0, resultTimelinePosts.size());
    }

    @Test
    @DisplayName("Test getTimelinePosts - User Following Empty")
    void testGetTimelinePostsUserFollowingEmpty() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList());
        List<PostModel> currentUserPosts = Arrays.asList(post1, post2);
        List<PostModel> followingPosts = Arrays.asList();

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);
        
        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        assertEquals(currentUserPosts.size(), resultTimelinePosts.size());
    }

    @Test
    @DisplayName("Test getTimelinePosts - User Posts Empty")
    void testGetTimelinePostsUserPostsEmpty() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList(2L, 3L));
        List<PostModel> currentUserPosts = Arrays.asList();
        List<PostModel> followingPosts = Arrays.asList(post3, post2, post1);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);
        
        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        assertEquals(followingPosts.size(), resultTimelinePosts.size());
    }

    @Test
    @DisplayName("Empty Timeline with No User or Following Posts")
    void testEmptyTimelineNoUserOrFollowingPosts() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList());
        List<PostModel> currentUserPosts = Arrays.asList();
        List<PostModel> followingPosts = Arrays.asList();

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);
        
        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        assertEquals(0, resultTimelinePosts.size());
    }

    // para esta condicion => .filter(post -> post.getCreatedAt() != null)

    @Test
    @DisplayName("Test getTimelinePosts - Filter Null CreatedAt")
    void testGetTimelinePostsFilterNullCreatedAt() {
        // given
        Long userId = 1L;
        UserModel currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFollowing(Arrays.asList(2L, 3L));

        // Crear algunos PostModel con createdAt nulo
        PostModel postWithNullCreatedAt1 = new PostModel();
        postWithNullCreatedAt1.setCreatedAt(null);

        PostModel postWithNullCreatedAt2 = new PostModel();
        postWithNullCreatedAt2.setCreatedAt(null);

        List<PostModel> currentUserPosts = Arrays.asList(post1, post2, postWithNullCreatedAt1);
        List<PostModel> followingPosts = Arrays.asList(post3, post4, postWithNullCreatedAt2, post5);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(postRepository.findByUserId(userId)).thenReturn(currentUserPosts);
        when(postRepository.findByUserIdIn(currentUser.getFollowing())).thenReturn(followingPosts);

        // then
        List<PostModel> resultTimelinePosts = timelineService.getTimelinePosts(userId);
        assertNotNull(resultTimelinePosts);
        
        // Verificar que los PostModel con createdAt nulo no estén presentes en el resultado
        assertEquals(currentUserPosts.size() + followingPosts.size() - 2, resultTimelinePosts.size());
    }

    private Date onCreate() {
        return new Date();
    }
}
