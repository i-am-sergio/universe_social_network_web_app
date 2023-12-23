package com.unsa.backend.messages;

import java.util.List;

public interface MessageService {

    MessageModel createMessage(MessageModel newMessage);

    List<MessageModel> getMessages(Long chatId);
}