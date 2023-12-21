package com.unsa.backend.chattest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.backend.messages.ChatModel;
import com.unsa.backend.messages.ChatService;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ChatController Tests")
@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    private static final String ERROR_STRING = "Simulated error";
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Test create chat")
    void testCreateChat() throws Exception {
        ChatModel chatModel = ChatModel.builder().build(); // Usa el constructor generado por Lombok

        when(chatService.createChat(any(ChatModel.class))).thenReturn(chatModel);

        mockMvc.perform(post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(chatModel)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test create chat with error")
    void testCreateChatWithError() throws Exception {
        ChatModel chatModel = ChatModel.builder().build(); // Usa el constructor generado por Lombok

        when(chatService.createChat(any(ChatModel.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(chatModel)))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    @DisplayName("Test get user chats with valid user")
    void testGetUserChatsWithValidUser() throws Exception {
        Long userId = 1L;
        List<ChatModel> expectedChats = Arrays.asList(
                ChatModel.builder().id(1L).build(),
                ChatModel.builder().id(2L).build());

        when(chatService.getUserChats(userId)).thenReturn(expectedChats);

        mockMvc.perform(MockMvcRequestBuilders.get("/chat/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test get user chats with invalid user")
    void testGetUserChatsWithInvalidUser() throws Exception {
        Long userId = 1L;

        when(chatService.getUserChats(userId)).thenThrow(new RuntimeException(ERROR_STRING));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat/{userId}", userId))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    @DisplayName("Test find chat with valid IDs")
    void testFindChatWithValidIDs() throws Exception {
        Long firstUserId = 1L;
        Long secondUserId = 2L;
        ChatModel expectedChat = ChatModel.builder().id(1L).build();

        when(chatService.findChat(firstUserId, secondUserId)).thenReturn(expectedChat);

        mockMvc.perform(MockMvcRequestBuilders.get("/chat/find/{firstId}/{secondId}", firstUserId, secondUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test find chat with invalid IDs")
    void testFindChatWithInvalidIDs() throws Exception {
        Long firstUserId = 1L;
        Long secondUserId = 2L;

        when(chatService.findChat(firstUserId, secondUserId)).thenThrow(new RuntimeException(ERROR_STRING));

        mockMvc.perform(MockMvcRequestBuilders.get("/chat/find/{firstId}/{secondId}", firstUserId, secondUserId))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    @DisplayName("Test delete chat with valid ID")
    void testDeleteChatWithValidID() throws Exception {
        Long chatId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/chat/{chatId}", chatId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test delete chat with invalid ID")
    void testDeleteChatWithInvalidID() throws Exception {
        Long chatId = 1L;

        doThrow(new RuntimeException(ERROR_STRING)).when(chatService).deleteChat(chatId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/chat/{chatId}", chatId))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

}
