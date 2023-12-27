package com.unsa.backend.chattest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.backend.chat.ChatModel;
import com.unsa.backend.chat.ChatService;

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
    @DisplayName("Create Chat - Successful")
    void testCreateChatSuccessful() throws Exception {
        ChatModel chatModel = new ChatModel();
        chatModel.setId(1L);
        chatModel.setMembers(Arrays.asList(1L, 2L));

        when(chatService.createChat(any())).thenReturn(chatModel);

        mockMvc.perform(post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(chatModel)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create Chat - Error")
    void testCreateChatError() throws Exception {
        ChatModel chatModel = new ChatModel();
        chatModel.setId(1L);
        chatModel.setMembers(Arrays.asList(1L, 2L));

        doThrow(new RuntimeException(ERROR_STRING)).when(chatService).createChat(any());

        mockMvc.perform(post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(chatModel)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Get User Chats - Valid User")
    void testGetUserChatsValidUser() throws Exception {
        Long userId = 1L;
        List<ChatModel> userChats = Arrays.asList(new ChatModel(), new ChatModel());

        when(chatService.getUserChats(userId)).thenReturn(userChats);

        mockMvc.perform(get("/chat/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get User Chats - Invalid User")
    void testGetUserChatsInvalidUser() throws Exception {
        Long userId = 1L;

        when(chatService.getUserChats(userId)).thenThrow(new RuntimeException(ERROR_STRING));

        mockMvc.perform(get("/chat/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Find Chat - Valid Chat")
    void testFindChatValidChat() throws Exception {
        Long firstId = 1L;
        Long secondId = 2L;
        ChatModel validChat = new ChatModel();

        when(chatService.findChat(firstId, secondId)).thenReturn(validChat);

        mockMvc.perform(get("/chat/find/{firstId}/{secondId}", firstId, secondId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Find Chat - Invalid Chat")
    void testFindChatInvalidChat() throws Exception {
        Long firstId = 1L;
        Long secondId = 2L;

        when(chatService.findChat(firstId, secondId)).thenThrow(new RuntimeException(ERROR_STRING));

        mockMvc.perform(get("/chat/find/{firstId}/{secondId}", firstId, secondId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Delete Chat - Valid Chat")
    void testDeleteChatValidChat() throws Exception {
        Long chatId = 1L;

        mockMvc.perform(delete("/chat/{chatId}", chatId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Chat - Invalid Chat")
    void testDeleteChatInvalidChat() throws Exception {
        Long chatId = 1L;

        doThrow(new RuntimeException(ERROR_STRING)).when(chatService).deleteChat(chatId);

        mockMvc.perform(delete("/chat/{chatId}", chatId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

}
