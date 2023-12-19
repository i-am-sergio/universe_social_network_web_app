package com.unsa.backend.messagestest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.messages.MessageModel;

@SpringBootTest
@DisplayName("Test Message Model")
class MessageModelTest {

    private static Long testMessageId = 1L;
    private static Long testChatId = 2L;
    private static Long testSenderId = 3L;
    private static String testText = "Hello!";

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
}
