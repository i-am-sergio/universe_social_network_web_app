package com.unsa.backend.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unsa.backend.messages.MessageModel;
import com.unsa.backend.messages.MessageRepository;
import com.unsa.backend.messages.UserChatException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    @Override
    public List<ChatModel> obtenerChats() {
        return (ArrayList<ChatModel>) chatRepository.findAll();
    }

    @Override
    public ChatModel createChat(ChatModel newChat) {
        try {
            List<Long> members = newChat.getMembers();
            List<ChatModel> existingChats = (List<ChatModel>) chatRepository.findAll();
            boolean chatExists = existingChats.stream()
                    .anyMatch(chatModel -> chatModel.getMembers().containsAll(members));
            if (chatExists) {
                throw new UserChatException("Ya existe un chat con los mismos miembros.");
            }
            return chatRepository.save(newChat);
        } catch (Exception e) {
            throw new UserChatException("Error al crear el Chat.");
        }
    }

    @Override
    public List<ChatModel> getUserChats(Long userId) {
        try {
            List<ChatModel> allChats = (List<ChatModel>) chatRepository.findAll();
            return allChats.stream()
                    .filter(chatModel -> chatModel.getMembers().contains(userId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserChatException("Error al obtener los chats del usuario.");
        }
    }

    @Override
    public ChatModel findChat(Long firstId, Long secondId) {
        try {
            List<ChatModel> chatList = (List<ChatModel>) chatRepository.findAll();
            return chatList
                    .stream()
                    .filter(chatModel -> chatModel.getMembers().contains(firstId)
                            && chatModel.getMembers().contains(secondId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new UserChatException("Error al obtener el chat entre los usuarios.");
        }
    }

    @Override
    public void deleteChat(Long chatId) {
        try {
            List<MessageModel> allMessages = (List<MessageModel>) messageRepository.findAll();
            List<MessageModel> messagesToDelete = allMessages.stream()
                    .filter(messageModel -> messageModel.getChatId().equals(chatId))
                    .collect(Collectors.toList());
            messagesToDelete.forEach(messageRepository::delete);
            chatRepository.deleteById(chatId);
        } catch (Exception e) {
            throw new UserChatException("Error al eliminar el chat.");
        }
    }
}
