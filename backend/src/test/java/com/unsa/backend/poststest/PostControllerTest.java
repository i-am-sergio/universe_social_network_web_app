package com.unsa.backend.poststest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.backend.posts.PostModel;
import com.unsa.backend.posts.PostService;
import com.unsa.backend.services.TimelineService;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

        private static final String IMAGE1 = "image1";
        private static final String IMAGE2 = "image2";
        private static final String DESC1 = "desc1";
        private static final String DESC2 = "desc2";
        private static final String URL_BASE = "/posts";
        private static final String BASE_URL_WITH_ID = "/posts/{id}";
        private static final String BASE_URL_ID_WITH_LIKE = "/posts/{id}/like";
        private static final String JSON_CONTENT_TYPE = "application/json";
        private static final String JSON_USER_ID_1 = "{\"userId\": 1}";
        private static final String INTERNAL_ERROR = "Internal Server Error";
        private static final String JSON_CONTENT = "{\"desc\": \"desc1\", \"image\": \"image1\"}";
        private static final String UPDATED_DESC = "updated desc";
        private static final String UPDATED_IMG = "updated image";

        @MockBean
        private PostService postService;

        @MockBean
        private TimelineService timelineService;

        private MockMvc mockMvc;

        @Autowired
        void setMockMvc(MockMvc mockMvc) {
                this.mockMvc = mockMvc;
        }

        private void performRequestAndExpectNotFound(Long postId) throws Exception {
                when(postService.getPostById(postId)).thenReturn(null);
                mockMvc.perform(put(BASE_URL_ID_WITH_LIKE, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_USER_ID_1))
                                .andExpect(status().isNotFound());
        }

        /**
         * Test case for finding all posts from the Controller.
         */
        @DisplayName("Test get all posts")
        @Test
        void testGetPosts() throws Exception {
                PostModel post1 = PostModel.builder().id(1L).desc(DESC1).image(IMAGE1).build();
                PostModel post2 = PostModel.builder().id(2L).desc(DESC2).image(IMAGE2).build();
                List<PostModel> posts = Arrays.asList(post1, post2);
                when(postService.getPosts()).thenReturn(posts);
                mockMvc.perform(get(URL_BASE))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id", is(1)))
                                .andExpect(jsonPath("$[1].id", is(2)));
        }

        /**
         * Test case for finding all posts from the Controller when the posts are Empty.
         */
        @DisplayName("Test get all posts")
        @Test
        void testGetPostsEmpty() throws Exception {
                List<PostModel> posts = Arrays.asList();
                when(postService.getPosts()).thenReturn(posts);
                mockMvc.perform(get(URL_BASE))
                                .andExpect(status().isNotFound());
        }

        /**
         * Test case for finding all posts from the Controller and give Error 505.
         */
        @DisplayName("Test get all posts")
        @Test
        void testGetPostsError() throws Exception {
                when(postService.getPosts()).thenReturn(null);
                mockMvc.perform(get(URL_BASE))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for finding a post by ID from the Controller.
         */
        @DisplayName("Test get post by id")
        @Test
        void testGetPostById() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).build();
                when(postService.getPostById(postId)).thenReturn(post);
                mockMvc.perform(get(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id", is(1)));
        }

        /**
         * Test case for finding a post by ID from the Controller when the post is not
         * found.
         */
        @DisplayName("Test get post by id")
        @Test
        void testGetPostByIdNotFound() throws Exception {
                Long postId = 1L;
                when(postService.getPostById(postId)).thenReturn(null);
                mockMvc.perform(get(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isNotFound());
        }

        /**
         * Test case for finding a post by ID from the Controller and give Error 505.
         */
        @DisplayName("Test get post by id")
        @Test
        void testGetPostByIdError() throws Exception {
                Long postId = 1L;
                when(postService.getPostById(postId)).thenReturn(null);
                doThrow(new RuntimeException(INTERNAL_ERROR)).when(postService).getPostById(postId);
                mockMvc.perform(get(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for creating a post from the Controller.
         */
        @DisplayName("Test create post")
        @Test
        void testCreatePost() throws Exception {
                PostModel post = PostModel.builder().desc(DESC1).image(IMAGE1).build();
                when(postService.createPost(post)).thenReturn(null);
                mockMvc.perform(post(URL_BASE)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_CONTENT))
                                .andExpect(status().isOk());
        }

        /**
         * Test case for creating a post from the Controller and give Error 505.
         */
        @DisplayName("Test create post")
        @Test
        void testCreatePostError() throws Exception {
                doThrow(new RuntimeException(INTERNAL_ERROR)).when(postService).createPost(any());
                mockMvc.perform(post(URL_BASE)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_CONTENT))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for updating a post from the Controller.
         */
        @DisplayName("Test update post")
        @Test
        void testUpdatePost() throws Exception {
                Long postId = 1L;
                Long userId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).userId(userId).build();
                PostModel updatedPost = PostModel.builder().id(postId).desc(UPDATED_DESC).image(UPDATED_IMG)
                                .userId(userId).build();
                when(postService.getPostById(postId)).thenReturn(post);
                doAnswer(invocation -> {
                        PostModel existingPost = invocation.getArgument(0);
                        PostModel updated = invocation.getArgument(1);
                        existingPost.setDesc(updated.getDesc());
                        existingPost.setImage(updated.getImage());
                        return null;
                }).when(postService).updatePost(any(PostModel.class), any(PostModel.class));

                mockMvc.perform(put(BASE_URL_WITH_ID, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(new ObjectMapper().writeValueAsString(updatedPost)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", is("Post updated!")));
        }

        /**
         * Test case for updating a post from the Controller when the post is FORBIDDEN
         */
        @DisplayName("Test update post forbidden")
        @Test
        void testUpdatePostForbidden() throws Exception {
                Long postId = 1L;
                Long userId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).userId(userId).build();
                PostModel updatedPost = PostModel.builder().id(postId).desc(UPDATED_DESC).image(UPDATED_IMG)
                                .userId(2L).build();
                when(postService.getPostById(postId)).thenReturn(post);
                doAnswer(invocation -> {
                        PostModel existingPost = invocation.getArgument(0);
                        PostModel updated = invocation.getArgument(1);
                        existingPost.setDesc(updated.getDesc());
                        existingPost.setImage(updated.getImage());
                        return null;
                }).when(postService).updatePost(any(PostModel.class), any(PostModel.class));

                mockMvc.perform(put(BASE_URL_WITH_ID, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(new ObjectMapper().writeValueAsString(updatedPost)))
                                .andExpect(status().isForbidden())
                                .andExpect(jsonPath("$", is("Authentication failed")));
        }

        /**
         * Test case for updating a post from the Controller when the post is not found.
         */
        @DisplayName("Test update post not found")
        @ParameterizedTest
        @ValueSource(longs = { 1L, 2L, 3L })
        void testUpdatePostNotFound(Long postId) throws Exception {
                when(postService.getPostById(postId)).thenReturn(null);
                mockMvc.perform(put(BASE_URL_WITH_ID, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_CONTENT))
                                .andExpect(status().isNotFound());
        }

        /**
         * Test case for updating a post from the Controller and give Error 505.
         */
        @DisplayName("Test update post")
        @Test
        void testUpdatePostError() throws Exception {
                Long postId = 1L;
                Long userId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).userId(userId).build();
                PostModel updatedPost = PostModel.builder().id(postId).desc(UPDATED_DESC).image(UPDATED_IMG)
                                .userId(userId).build();
                when(postService.getPostById(postId)).thenReturn(post);
                doThrow(new RuntimeException(INTERNAL_ERROR)).when(postService).updatePost(any(),
                                any());
                mockMvc.perform(put(BASE_URL_WITH_ID, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(new ObjectMapper().writeValueAsString(updatedPost)))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for deleting a post from the Controller.
         */
        @DisplayName("Test delete post")
        @Test
        void testDeletePost() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).build();
                when(postService.getPostById(postId)).thenReturn(post);
                when(postService.deletePost(postId)).thenReturn(true);
                mockMvc.perform(delete(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", is("Post deleted!")));
        }

        /**
         * Test case for deleting a post from the Controller when the post is not found.
         */
        @DisplayName("Test delete post not found")
        @Test
        void testDeletePostNotFound() throws Exception {
                Long postId = 1L;
                when(postService.getPostById(postId)).thenReturn(null);
                when(postService.deletePost(postId)).thenReturn(false);
                mockMvc.perform(delete(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isNotFound());
        }

        /**
         * Test case for deleting a post from the Controller and give Error 505.
         */
        @DisplayName("Test delete post")
        @Test
        void testDeletePostError() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1).build();
                when(postService.getPostById(postId)).thenReturn(post);
                when(postService.deletePost(postId)).thenReturn(true);
                doThrow(new RuntimeException(INTERNAL_ERROR)).when(postService).deletePost(postId);
                mockMvc.perform(delete(BASE_URL_WITH_ID, postId))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for adding a likePost from the Controller.
         */
        @DisplayName("Test like post")
        @Test
        void testLikePost() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1)
                                .likes(Arrays.asList()).build();
                when(postService.getPostById(postId)).thenReturn(post);
                mockMvc.perform(put(BASE_URL_ID_WITH_LIKE, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_USER_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", is("Post liked!")));
        }

        /**
         * Test case for adding a likePost from the Controller when the post is not
         * found.
         */
        @DisplayName("Test like post not found")
        @Test
        void testLikePostNotFound() throws Exception {
                Long postId = 1L;
                performRequestAndExpectNotFound(postId);
        }

        /**
         * Test case for deleting a likePost from the Controller.
         */
        @DisplayName("Test dislike post")
        @Test
        void testDislikePost() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1)
                                .likes(Arrays.asList(2L)).build();
                when(postService.getPostById(postId)).thenReturn(post);
                mockMvc.perform(put(BASE_URL_ID_WITH_LIKE, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content("{\"userId\": 2}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", is("Post disliked!")));
        }

        /**
         * Test case for deleting a likePost from the Controller when the post is not
         * found.
         */
        @DisplayName("Test dislike post not found")
        @Test
        void testDislikePostNotFound() throws Exception {
                Long postId = 2L;
                performRequestAndExpectNotFound(postId);
        }

        /**
         * Test case for adding or deleting a likePost from the Controller and give
         * error 505.
         */
        @DisplayName("Test like post")
        @Test
        void testLikePostError() throws Exception {
                Long postId = 1L;
                PostModel post = PostModel.builder().id(postId).desc(DESC1).image(IMAGE1)
                                .likes(Arrays.asList()).build();
                when(postService.getPostById(postId)).thenReturn(post);
                doThrow(new RuntimeException(INTERNAL_ERROR)).when(postService).getPostById(postId);
                mockMvc.perform(put(BASE_URL_ID_WITH_LIKE, postId)
                                .contentType(JSON_CONTENT_TYPE)
                                .content(JSON_USER_ID_1))
                                .andExpect(status().isInternalServerError());
        }

        /**
         * Test case for finding all posts from the Controller.
         */
        @DisplayName("Test get timeline")
        @Test
        void testGetTimeline() throws Exception {
                PostModel post1 = PostModel.builder().id(1L).desc(DESC1).image(IMAGE1).userId(1L).build();
                PostModel post2 = PostModel.builder().id(2L).desc(DESC2).image(IMAGE2).userId(1L).build();
                List<PostModel> posts = Arrays.asList(post1, post2);
                when(timelineService.getTimelinePosts(1L)).thenReturn(posts);
                mockMvc.perform(get(BASE_URL_WITH_ID + "/timeline", 1L))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id", is(1)))
                                .andExpect(jsonPath("$[1].id", is(2)));
        }

        /**
         * Test case for finding all posts from the Controller when the posts are Empty.
         */
        @DisplayName("Test get timeline empty")
        @Test
        void testGetTimelineEmpty() throws Exception {
                when(timelineService.getTimelinePosts(1L)).thenReturn(Arrays.asList());
                mockMvc.perform(get(BASE_URL_WITH_ID + "/timeline", 1L))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$", hasSize(0)));
        }

}
