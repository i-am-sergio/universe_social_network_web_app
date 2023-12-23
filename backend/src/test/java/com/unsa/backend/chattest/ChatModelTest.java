package com.unsa.backend.chattest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.data.auditing.DateTimeProvider;

import com.unsa.backend.messages.ChatModel;

@SpringBootTest
@DisplayName("Test Model")
@ExtendWith(MockitoExtension.class)
class ChatModelTest {
    /*
     * @MockBean
     * private DateTimeProvider dateTimeProviderMock;
     * 
     * @DisplayName("Test create a chat")
     * 
     * @Test
     * void testCreateChat() {
     * Date mockDate = new Date();
     * when(dateTimeProviderMock.getNow()).thenReturn(Optional.of(mockDate.toInstant
     * ()));
     * 
     * ChatModel chatModel = ChatModel.builder().members(Arrays.asList(1L,
     * 2L)).build();
     * chatModel.onCreate();
     * 
     * assertNotNull(chatModel.getCreatedAt());
     * assertNotNull(chatModel.getUpdatedAt());
     * 
     * long expectedTime = mockDate.getTime();
     * 
     * // Check createdAt
     * long actualCreateTime = chatModel.getCreatedAt().getTime();
     * long toleranceCreateTime = 20;
     * assertTrue(Math.abs(expectedTime - actualCreateTime) <= toleranceCreateTime);
     * 
     * // Check updatedAt (should be the same as createdAt during creation)
     * long actualUpdateTime = chatModel.getUpdatedAt().getTime();
     * long toleranceUpdateTime = 20;
     * assertTrue(Math.abs(expectedTime - actualUpdateTime) <= toleranceUpdateTime);
     * }
     * 
     * @DisplayName("Test update a chat")
     * 
     * @Test
     * void testUpdateChat() {
     * Date mockDate = new Date();
     * when(dateTimeProviderMock.getNow()).thenReturn(Optional.of(mockDate.toInstant
     * ()));
     * 
     * ChatModel chatModel = ChatModel.builder().members(Arrays.asList(1L,
     * 2L)).build();
     * chatModel.onUpdate();
     * 
     * assertNotNull(chatModel.getUpdatedAt());
     * 
     * long expectedTime = mockDate.getTime();
     * 
     * // Check updatedAt
     * long actualUpdateTime = chatModel.getUpdatedAt().getTime();
     * long toleranceUpdateTime = 20;
     * assertTrue(Math.abs(expectedTime - actualUpdateTime) <= toleranceUpdateTime);
     * }
     */
}
