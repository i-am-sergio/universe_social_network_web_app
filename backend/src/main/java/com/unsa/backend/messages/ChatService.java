package com.unsa.backend.messages;

import java.util.List;

public interface ChatService {
    List<ChatModel> obtenerChats();

    ChatModel createChat(ChatModel newChat);

    List<ChatModel> getUserChats(Long userId);

    ChatModel findChat(Long firstId, Long secondId);

    void deleteChat(Long chatId);
}

