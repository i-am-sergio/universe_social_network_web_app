package com.unsa.backend.messages;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unsa.backend.users.UserModel;

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


    /*
    public List<ChatModel> getUserChats(Long members) {
        return chatRepository.findByMembersContaining(members);
    }
    
    public ChatModel findChat(String firstUserId, String secondUserId) {
        return chatRepository.findByMembers(firstUserId, secondUserId);
    }

    */
}
