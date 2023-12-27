package com.unsa.backend.messages;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageModel createMessage(MessageModel newMessage) {
        try {
            return messageRepository.save(newMessage);
        } catch (Exception e) {
            throw new UserChatException("Error al crear el mensaje.");
        }
    }

    @Override
    public List<MessageModel> getMessages(Long chatId) {
        try {
            List<MessageModel> allMessages = (List<MessageModel>) messageRepository.findAll();
            return allMessages.stream()
                    .filter(messageModel -> messageModel.getChatId().equals(chatId))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserChatException("Error al obtener los mensajes del chat.");
        }
    }

}