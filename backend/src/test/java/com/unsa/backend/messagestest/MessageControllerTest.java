package com.unsa.backend.messagestest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
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

import com.unsa.backend.messages.MessageModel;
import com.unsa.backend.messages.MessageService;
import com.unsa.backend.messages.UserChatException;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Message Controller Test")
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    private static final String URL_BASE = "/message";

    @MockBean
    private MessageService messageService;

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Create Message - Success")
    void createMessageSuccess() throws Exception {
        // Arrange
        MessageModel newMessage = new MessageModel();
        newMessage.setChatId(1L);
        newMessage.setSenderId(2L);
        newMessage.setText("Hello!");

        when(messageService.createMessage(any(MessageModel.class))).thenReturn(newMessage);
        mockMvc.perform(post(URL_BASE + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newMessage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(newMessage.getChatId()))
                .andExpect(jsonPath("$.senderId").value(newMessage.getSenderId()))
                .andExpect(jsonPath("$.text").value(newMessage.getText()));
    }

    @Test
    @DisplayName("Create Message - Internal Server Error")
    void createMessageInternalServerError() throws Exception {
        // Arrange
        MessageModel newMessage = new MessageModel();
        newMessage.setChatId(1L);
        newMessage.setSenderId(2L);
        newMessage.setText("Hello!");
        when(messageService.createMessage(any(MessageModel.class))).thenThrow(new UserChatException("Error"));
        mockMvc.perform(post(URL_BASE + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newMessage)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Get Messages - Success")
    void getMessagesSuccess() throws Exception {
        Long chatId = 1L;
        List<MessageModel> messages = new ArrayList<>();
        when(messageService.getMessages(chatId)).thenReturn(messages);
        mockMvc.perform(get(URL_BASE + "/{chatId}", chatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Get Messages - Internal Server Error")
    void getMessagesInternalServerError() throws Exception {
        // Arrange
        Long chatId = 1L;
        when(messageService.getMessages(chatId)).thenThrow(new RuntimeException("Internal Server Error"));

        // Act and Assert
        mockMvc.perform(get(URL_BASE + "/{chatId}", chatId))
                .andExpect(status().isInternalServerError());

    }

    // Utilidad para convertir un objeto a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}