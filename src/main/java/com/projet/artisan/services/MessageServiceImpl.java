package com.projet.artisan.services;

import com.projet.artisan.models.AppUser;
import com.projet.artisan.models.Message;
import com.projet.artisan.repository.AppUserRepository;
import com.projet.artisan.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final AppUserRepository appUserRepository;


    @Autowired
    private final AccountService accountService;

    public MessageServiceImpl(MessageRepository messageRepository, AccountService accountService,
                              AppUserRepository appUserRepository) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
        this.appUserRepository= appUserRepository;
    }

    @Override
    public Message sendMessage(Long senderId, String Destinataire, String content) {
        // Retrieve the sender and receiver user objects using their IDs
        AppUser sender = appUserRepository.findById(senderId).orElse(null);
        AppUser receiver = appUserRepository.findByUserName(Destinataire);

        if (sender == null || receiver == null) {
            // Handle the scenario if the sender or receiver is not found
            throw new IllegalArgumentException("Sender or receiver not found");
        }

        // Create a new Message object
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        // Set the current timestamp as the creation timestamp
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        message.setCreatedAt(currentTime);

        // Save the message in the database
        return messageRepository.save(message);
    }


    @Override
    public List<Message> getMessagesByUser(Long userId) {
        // Retrieve the user by their ID
        AppUser user = appUserRepository.findById(userId).orElse(null);

        if (user == null) {
            // Handle the scenario if the user is not found
            throw new IllegalArgumentException("User not found");
        }

        // Retrieve the messages for the user (either as a sender or receiver)
        List<Message> messages = messageRepository.findBySender( user);

        return messages;
    }
    @Override
    public List<Message> getMessagesByReceiver(Long userId) {
        // Retrieve the user by their ID
        AppUser user = appUserRepository.findById(userId).orElse(null);

        if (user == null) {
            // Handle the scenario if the user is not found
            throw new IllegalArgumentException("User not found");
        }

        // Retrieve the messages for the user (either as a sender or receiver)
        List<Message> messages = messageRepository.findByReceiver(user);

        return messages;
    }


}
