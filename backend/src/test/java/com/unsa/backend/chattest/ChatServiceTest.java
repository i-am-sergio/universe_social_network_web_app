package com.unsa.backend.chattest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.messages.ChatModel;
import com.unsa.backend.messages.ChatRepository;
import com.unsa.backend.messages.ChatService;
import com.unsa.backend.messages.MessageModel;
import com.unsa.backend.messages.MessageRepository;
import com.unsa.backend.messages.UserChatException;

@SpringBootTest
@DisplayName("ChatService Tests")
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private MessageRepository messageRepository;

    private ChatService chatService;

    @Autowired
    public ChatServiceTest(ChatService chatService) {
        this.chatService = chatService;
    }

    @DisplayName("Test obtenerChats")
    @Test
    void testObtenerChats() {
        when(chatRepository.findAll()).thenReturn(new ArrayList<>());
        List<ChatModel> result = chatService.obtenerChats();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chatRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Create Chat - Successful")
    void testCreateChatSuccessful() {
        ChatModel newChat = new ChatModel();
        newChat.setMembers(Arrays.asList(1L, 2L));
        when(chatRepository.findAll()).thenReturn(new ArrayList<>());
        when(chatRepository.save(any())).thenReturn(newChat);
        ChatModel result = chatService.createChat(newChat);
        assertNotNull(result);
        assertEquals(newChat.getMembers(), result.getMembers());
    }

    @Test
    @DisplayName("Create Chat - Chat Exists")
    void testCreateChatChatExists() {
        ChatModel existingChat = new ChatModel();
        existingChat.setMembers(Arrays.asList(1L, 2L));
        ChatModel newChat = new ChatModel();
        newChat.setMembers(Arrays.asList(1L, 2L));
        when(chatRepository.findAll()).thenReturn(Arrays.asList(existingChat));
        UserChatException exception = assertThrows(UserChatException.class,
                () -> chatService.createChat(newChat));
        assertEquals("Error al crear el Chat.", exception.getMessage());
    }

    @Test
    @DisplayName("Get User Chats - User Exists in Chats")
    void testGetUserChatsUserExists() {
        Long userId = 1L;
        ChatModel chat1 = new ChatModel();
        chat1.setMembers(Arrays.asList(userId, 2L));
        ChatModel chat2 = new ChatModel();
        chat2.setMembers(Arrays.asList(userId, 3L));
        List<ChatModel> allChats = Arrays.asList(chat1, chat2);
        when(chatRepository.findAll()).thenReturn(allChats);
        List<ChatModel> result = chatService.getUserChats(userId);
        assertEquals(2, result.size());
        assertTrue(result.contains(chat1));
        assertTrue(result.contains(chat2));
    }

    @Test
    @DisplayName("Get User Chats - User Does Not Exist in Chats")
    void testGetUserChatsUserDoesNotExist() {
        Long userId = 1L;
        ChatModel chat1 = new ChatModel();
        chat1.setMembers(Arrays.asList(2L, 3L));
        ChatModel chat2 = new ChatModel();
        chat2.setMembers(Arrays.asList(4L, 5L));
        List<ChatModel> allChats = Arrays.asList(chat1, chat2);
        when(chatRepository.findAll()).thenReturn(allChats);
        List<ChatModel> result = chatService.getUserChats(userId);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Find Chat - Successful")
    void testFindChatSuccessful() {
        Long firstId = 1L;
        Long secondId = 2L;
        ChatModel chat = new ChatModel();
        chat.setMembers(Arrays.asList(firstId, secondId));
        List<ChatModel> chatList = Arrays.asList(chat);
        when(chatRepository.findAll()).thenReturn(chatList);
        ChatModel result = chatService.findChat(firstId, secondId);
        assertNotNull(result);
        assertEquals(chat, result);
    }

    @Test
    @DisplayName("Find Chat - Invalid")
    void testFindChatInvalid() {
        Long firstId = 1L;
        Long secondId = 2L;
        ChatModel chat = new ChatModel();
        chat.setMembers(Arrays.asList(3L, 4L));
        List<ChatModel> chatList = Arrays.asList(chat);
        when(chatRepository.findAll()).thenReturn(chatList);
        ChatModel result = chatService.findChat(firstId, secondId);
        assertNull(result);
    }

    @DisplayName("Test deleteChat - Successful")
    @Test
    void testDeleteChatSuccessful() {
        List<MessageModel> allMessages = Arrays.asList(
                MessageModel.builder().chatId(1L).text("Hola").build(),
                MessageModel.builder().chatId(1L).text("Cómo estás").build(),
                MessageModel.builder().chatId(2L).text("Mensaje en otro chat").build());
        when(messageRepository.findAll()).thenReturn(allMessages);
        chatService.deleteChat(1L);
        verify(messageRepository, times(2)).delete(any(MessageModel.class));
        verify(chatRepository, times(1)).deleteById(1L);
    }

    @DisplayName("Test deleteChat - Invalid")
    @Test
    void testDeleteChatInvalid() {
        when(messageRepository.findAll()).thenThrow(new RuntimeException("Error al buscar mensajes"));
        assertThrows(UserChatException.class, () -> chatService.deleteChat(1L));
        verify(messageRepository, never()).delete(any(MessageModel.class));
        verify(chatRepository, never()).deleteById(1L);
    }

}
