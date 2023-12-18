package com.unsa.backend.poststest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.DateTimeProvider;

import com.unsa.backend.posts.PostModel;

@SpringBootTest
@DisplayName("Test Model")
@ExtendWith(MockitoExtension.class)
class PostModelTest {
    @MockBean
    private DateTimeProvider dateTimeProviderMock;

    /**
     * Test case for create a Model Post.
     */
    @DisplayName("Test create a post")
    @Test
    void testCreatePost() {
        Date mockDate = new Date();
        when(dateTimeProviderMock.getNow()).thenReturn(Optional.of(mockDate.toInstant()));
        PostModel postModel = PostModel.builder().build();
        postModel.onCreate();
        assertNotNull(postModel.getCreatedAt());
        long expectedTime = mockDate.getTime();
        long actualTime = postModel.getCreatedAt().getTime();
        long tolerance = 20;
        assertTrue(Math.abs(expectedTime - actualTime) <= tolerance);
    }
}
