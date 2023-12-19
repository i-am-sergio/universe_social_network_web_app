package com.unsa.backend.messagestest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.unsa.backend.messages.MessageModel;

@SpringBootTest
@DisplayName("Test Message Model")
@ExtendWith(MockitoExtension.class)
class MessageModelTest {

    private static Long testMessageId = 1L;
    private static Long testChatId = 2L;
    private static Long testSenderId = 3L;
    private static String testText = "Hello!";

    @MockBean
    private DateTimeProvider dateTimeProviderMock;

    @Test
    @DisplayName("Test Getters and Setters")
    void testGettersAndSetters() {
        MessageModel message = new MessageModel();
        message.setMessageId(testMessageId);
        message.setChatId(testChatId);
        message.setSenderId(testSenderId);
        message.setText(testText);
        assertEquals(testMessageId, message.getMessageId());
        assertEquals(testChatId, message.getChatId());
        assertEquals(testSenderId, message.getSenderId());
        assertEquals(testText, message.getText());
    }

    /**
     * Test case for create a Model message.
     */
    @DisplayName("Test create a message")
    @Test
    void testCreateMessage() {
        Date mockDate = new Date();
        when(dateTimeProviderMock.getNow()).thenReturn(Optional.of(mockDate.toInstant()));
        MessageModel messageModel = MessageModel.builder().build();
        messageModel.onCreate();
        assertNotNull(messageModel.getCreatedAt());
        assertNotNull(messageModel.getUpdatedAt());
        long expectedTime = mockDate.getTime();
        long time1Actual = messageModel.getCreatedAt().getTime();
        long time2Actual = messageModel.getUpdatedAt().getTime();
        long tolerance = 20;
        assertTrue(Math.abs(expectedTime - time1Actual) <= tolerance);
        assertTrue(Math.abs(expectedTime - time2Actual) <= tolerance);
    }

    /**
     * Test case for update a Model message.
     */
    @DisplayName("Test update a message")
    @Test
    void testUpdateMessage() {
        Date mockDate = new Date();
        when(dateTimeProviderMock.getNow()).thenReturn(Optional.of(mockDate.toInstant()));
        MessageModel messageModel = MessageModel.builder().build();
        messageModel.onUpdate();
        assertNotNull(messageModel.getUpdatedAt());
        long expectedTime = mockDate.getTime();
        long actualTime = messageModel.getUpdatedAt().getTime();
        long tolerance = 20;
        assertTrue(Math.abs(expectedTime - actualTime) <= tolerance);
    }

}
