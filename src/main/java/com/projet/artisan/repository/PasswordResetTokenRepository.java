package com.projet.artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.models.PasswordResetToken;
import org.springframework.stereotype.Repository;
import com.sun.xml.bind.v2.model.core.ID;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

}

