package com.unsa.backend.messages;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {
    
    private final ChatService chatService;

    @PostMapping
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

    @GetMapping("/find/{firstId}/{secondId}")
    public ResponseEntity<ChatModel> findChat(
        @PathVariable Long firstId,
        @PathVariable Long secondId
    ) {
        try {
            ChatModel chat = chatService.findChat(firstId, secondId);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        try {
            chatService.deleteChat(chatId);
            return new ResponseEntity<>("Chat deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
