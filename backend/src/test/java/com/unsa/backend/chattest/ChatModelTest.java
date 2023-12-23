package com.unsa.backend.chattest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZoneId;
import java.util.Date;

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

    @MockBean
    private DateTimeProvider dateTimeProviderMock;

    @Test
    @DisplayName("onCreate - Sets Creation and Update Dates")
    void testOnCreate() {
        ChatModel chatModel = new ChatModel();
        chatModel.onCreate();

        assertNotNull(chatModel.getCreatedAt());
        assertNotNull(chatModel.getUpdatedAt());
        assertEquals(chatModel.getCreatedAt(), chatModel.getUpdatedAt());
    }

    @Test
    @DisplayName("onUpdate - Sets Update Date Different from Creation Date")
    void testOnUpdate() {
        ChatModel chatModel = new ChatModel();
        chatModel.setCreatedAt(new Date());

        chatModel.onUpdate();

        assertNotNull(chatModel.getUpdatedAt());

        assertEquals(chatModel.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                chatModel.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
