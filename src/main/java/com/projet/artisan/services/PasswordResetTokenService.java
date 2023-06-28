package com.projet.artisan.services;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.models.PasswordResetToken;
import com.projet.artisan.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;




    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    public void savePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    public PasswordResetToken findPasswordResetTokenByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public void generatePasswordResetToken(AppUser user) {
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        passwordResetTokenRepository.save(token);

        // Compose the email
        String emailSubject = "Password Reset";
        String emailBody = "Please click on the following link to reset your password: "
                + "http://your-frontend-url/reset-password?token=" + token.getToken();

        // Send the email
        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }
    public void deletePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }


}

