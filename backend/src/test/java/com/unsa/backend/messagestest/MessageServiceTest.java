package com.unsa.backend.messagestest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.messages.MessageModel;
import com.unsa.backend.messages.MessageRepository;
import com.unsa.backend.messages.MessageService;
import com.unsa.backend.messages.UserChatException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Message Service Test")
class MessageServiceTest {

    @MockBean
    private MessageRepository messageRepository;
    private MessageService messageService;

    @Autowired
    public MessageServiceTest(MessageService messageService) {
        this.messageService = messageService;
    }

    @Test
    @DisplayName("Create Message - Success")
    void createMessageSuccess() {
        MessageModel newMessage = new MessageModel();
        when(messageRepository.save(any(MessageModel.class))).thenReturn(newMessage);
        MessageModel createdMessage = messageService.createMessage(newMessage);
        assertNotNull(createdMessage);
    }

    @Test
    @DisplayName("Create Message - Exception")
    void createMessageException() {
        MessageModel newMessage = new MessageModel();
        when(messageRepository.save(any(MessageModel.class))).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(UserChatException.class, () -> messageService.createMessage(newMessage));
    }

    @Test
    @DisplayName("Get Messages - Success")
    void getMessagesSuccess() {
        Long chatId = 1L;
        List<MessageModel> allMessages = new ArrayList<>();
        MessageModel message1 = new MessageModel();
        message1.setChatId(chatId);
        MessageModel message2 = new MessageModel();
        message2.setChatId(chatId);
        allMessages.add(message1);
        allMessages.add(message2);

        when(messageRepository.findAll()).thenReturn(allMessages);
        List<MessageModel> chatMessages = messageService.getMessages(chatId);
        assertNotNull(chatMessages);
        assertEquals(2, chatMessages.size());
    }

    @Test
    @DisplayName("Get Messages - Exception")
    void getMessagesException() {
        Long chatId = 1L;
        when(messageRepository.findAll()).thenThrow(new RuntimeException("Simulated exception"));
        assertThrows(UserChatException.class, () -> messageService.getMessages(chatId));
    }
}
