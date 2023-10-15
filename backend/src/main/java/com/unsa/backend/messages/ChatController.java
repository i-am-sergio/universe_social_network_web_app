package com.unsa.backend.messages;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsa.backend.users.UserModel;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public ResponseEntity<ChatModel> createChat(@RequestBody ChatModel chatModel) {
        try {
            ChatModel result = chatService.createChat(chatModel);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(chatModel);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ChatModel>> getUserChats(@PathVariable Long userId) {
        try {
            List<ChatModel> chats = chatService.getUserChats(userId);
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        /*
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return new ResponseEntity<>("Chat deleted successfully.", HttpStatus.OK);
    }
    @GetMapping("/userChats/{members}")
    public ResponseEntity<List<ChatModel>> getUserChats(@PathVariable Long members) {
        List<ChatModel> chats = chatService.getUserChats(members);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
    
    @GetMapping("/findChat/{firstUserId}/{secondUserId}")
    public ResponseEntity<ChatModel> findChat(
            @PathVariable String firstUserId,
            @PathVariable String secondUserId
    ) {
        ChatModel chat = chatService.findChat(firstUserId, secondUserId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    */
}
