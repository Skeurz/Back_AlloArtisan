package com.projet.artisan.controllers;

import com.projet.artisan.models.Message;
import com.projet.artisan.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/chats")
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")// Envoi un message
    public ResponseEntity<Message> sendMessage(@RequestParam("senderId") Long senderId,
                                               @RequestParam("destinataire") String destinataire,
                                               @RequestParam("content") String content) {
        try {
            // Call the messageService to send the message
            Message message = messageService.sendMessage(senderId, destinataire, content);
            // Return the created message as a successful response
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            // Return a BAD_REQUEST response if the sender or receiver is not found
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return an INTERNAL_SERVER_ERROR response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/messages/{userId}") //Affiche tous les messages envoyé par userId
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable("userId") Long userId) {
        try {
            // Call the messageService to retrieve messages for the specified user
            List<Message> messages = messageService.getMessagesByUser(userId);
            // Return the messages as a successful response
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            // Return a BAD_REQUEST response if the user is not found
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return an INTERNAL_SERVER_ERROR response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/recu/{userId}") //Affiche tous les messages envoyé par userId
    public ResponseEntity<List<Message>> getMessagesByReceiver(@PathVariable("userId") Long userId) {
        try {
            // Call the messageService to retrieve messages for the specified user
            List<Message> messages = messageService.getMessagesByReceiver(userId);
            // Return the messages as a successful response
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            // Return a BAD_REQUEST response if the user is not found
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Return an INTERNAL_SERVER_ERROR response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


