package com.projet.artisan.services;

import com.projet.artisan.models.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, String destinataire, String content);
    List<Message> getMessagesByUser(Long userId);

    List<Message> getMessagesByReceiver(Long userId);
}