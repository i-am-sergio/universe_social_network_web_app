package com.unsa.backend.messages;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping("/")
    public ResponseEntity<MessageModel> createMessage(@RequestBody MessageModel messageModel) {
        try {
            MessageModel result = messageService.createMessage(messageModel);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageModel);
        }
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageModel>> getMessages(@PathVariable Long chatId){
        try{
            List<MessageModel> messages = messageService.getMessages(chatId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
