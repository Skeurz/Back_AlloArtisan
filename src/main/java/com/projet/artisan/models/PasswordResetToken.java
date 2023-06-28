package com.projet.artisan.models;

import org.apache.commons.lang3.RandomStringUtils;
import com.projet.artisan.models.AppUser;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
public class PasswordResetToken {
    private static final int TOKEN_LENGTH = 32;
    private static final int EXPIRATION_HOURS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    public PasswordResetToken() {
        this.token = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
        this.expiryDate = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
    }
}

