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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unsa.backend.messages.ChatModel;
import com.unsa.backend.messages.ChatRepository;
import com.unsa.backend.messages.ChatService;
import com.unsa.backend.messages.MessageModel;
import com.unsa.backend.messages.MessageRepository;
import com.unsa.backend.messages.UserChatException;

@ExtendWith(MockitoExtension.class)
@DisplayName("ChatService Tests")
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatService chatService;

    @DisplayName("Test obtenerChats")
    @Test
    void testObtenerChats() {
        when(chatRepository.findAll()).thenReturn(new ArrayList<>());

        List<ChatModel> result = chatService.obtenerChats();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test createChat - Successful")
    @Test
    void testCreateChatSuccessful() {
        ChatModel newChat = ChatModel.builder().members(Arrays.asList(1L, 2L)).build();
        when(chatRepository.findAll()).thenReturn(new ArrayList<>());
        when(chatRepository.save(newChat)).thenReturn(newChat);

        ChatModel result = chatService.createChat(newChat);

        assertNotNull(result);
        assertEquals(newChat, result);

        verify(chatRepository, times(1)).findAll();
        verify(chatRepository, times(1)).save(newChat);
    }

    @DisplayName("Test createChat - Invalid Data")
    @Test
    void testCreateChatInvalidData() {
        ChatModel existingChat = ChatModel.builder().members(Arrays.asList(1L, 2L)).build();
        when(chatRepository.findAll()).thenReturn(Arrays.asList(existingChat));

        ChatModel newChat = ChatModel.builder().members(Arrays.asList(1L, 2L)).build();

        assertThrows(UserChatException.class, () -> chatService.createChat(newChat));

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test createChat - Chat Already Exists")
    @Test
    void testCreateChatChatAlreadyExists() {
        ChatModel existingChat = ChatModel.builder().members(Arrays.asList(1L, 2L)).build();
        when(chatRepository.findAll()).thenReturn(Arrays.asList(existingChat));

        ChatModel newChat = ChatModel.builder().members(Arrays.asList(1L, 2L)).build();

        assertThrows(UserChatException.class, () -> chatService.createChat(newChat));

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test getUserChats - Valid User")
    @Test
    void testGetUserChatsValidUser() {
        List<ChatModel> allChats = Arrays.asList(
                ChatModel.builder().id(1L).members(Arrays.asList(1L, 2L)).build(),
                ChatModel.builder().id(2L).members(Arrays.asList(1L, 3L)).build(),
                ChatModel.builder().id(3L).members(Arrays.asList(2L, 3L)).build());
        when(chatRepository.findAll()).thenReturn(allChats);

        List<ChatModel> userChats = chatService.getUserChats(1L);

        assertNotNull(userChats);
        assertEquals(2, userChats.size());
        assertTrue(userChats.stream().allMatch(chatModel -> chatModel.getMembers().contains(1L)));
        assertTrue(userChats.stream().noneMatch(chatModel -> !chatModel.getMembers().contains(1L)));

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test getUserChats - Nonexistent User")
    @Test
    void testGetUserChatsNonexistentUser() {
        List<ChatModel> allChats = Arrays.asList(
                ChatModel.builder().id(1L).members(Arrays.asList(2L, 3L)).build(),
                ChatModel.builder().id(2L).members(Arrays.asList(4L, 5L)).build());
        when(chatRepository.findAll()).thenReturn(allChats);

        List<ChatModel> userChats = chatService.getUserChats(1L);

        assertNotNull(userChats);
        assertTrue(userChats.isEmpty());

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test findChat - Successful")
    @Test
    void testFindChatSuccessful() {
        List<ChatModel> chatList = Arrays.asList(
                ChatModel.builder().id(1L).members(Arrays.asList(1L, 2L)).build(),
                ChatModel.builder().id(2L).members(Arrays.asList(2L, 3L)).build(),
                ChatModel.builder().id(3L).members(Arrays.asList(1L, 3L)).build());
        when(chatRepository.findAll()).thenReturn(chatList);

        ChatModel result = chatService.findChat(1L, 2L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(chatRepository, times(1)).findAll();
    }

    @DisplayName("Test findChat - Invalid IDs")
    @Test
    void testFindChatInvalidIDs() {
        List<ChatModel> chatList = Arrays.asList(
                ChatModel.builder().id(1L).members(Arrays.asList(1L, 2L)).build(),
                ChatModel.builder().id(2L).members(Arrays.asList(2L, 3L)).build(),
                ChatModel.builder().id(3L).members(Arrays.asList(1L, 3L)).build());
        when(chatRepository.findAll()).thenReturn(chatList);

        ChatModel result = chatService.findChat(4L, 5L);

        assertNull(result);

        verify(chatRepository, times(1)).findAll();
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
