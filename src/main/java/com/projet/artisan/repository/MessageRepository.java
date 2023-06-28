package com.projet.artisan.repository;

import com.projet.artisan.models.Message;
import com.projet.artisan.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender(AppUser sender);
    List<Message> findByReceiver(AppUser receiver);

}
