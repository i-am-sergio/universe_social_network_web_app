package com.unsa.backend.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    public List<ChatModel> obtenerChats(){
        return (ArrayList<ChatModel>)chatRepository.findAll();
    }

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatModel createChat(ChatModel newChat) {
        return chatRepository.save(newChat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    public List<ChatModel> getUserChats(Long userId) {
        try {
            List<ChatModel> allChats = (List<ChatModel>) chatRepository.findAll();
            return allChats.stream()
                .filter(chatModel -> chatModel.getMembers().contains(userId))
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserChatException("Error al obtener los chats del usuario.");
        }
    }
}
