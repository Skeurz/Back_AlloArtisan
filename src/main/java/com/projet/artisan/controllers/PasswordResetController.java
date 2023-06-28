package com.projet.artisan.controllers;

import com.projet.artisan.models.PasswordResetToken;
import com.projet.artisan.services.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.services.AccountServiceImpl;
import org.springframework.http.ResponseEntity;

import java.util.Map;
@CrossOrigin(origins="http://localhost:4200")
@RestController
//@RequestMapping("/api/password-reset")
public class PasswordResetController {
    private final PasswordResetTokenService passwordResetTokenService;
    private final AccountServiceImpl accountServiceImpl;


    @Autowired
    public PasswordResetController(PasswordResetTokenService passwordResetTokenService, AccountServiceImpl accountServiceImpl) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.accountServiceImpl = accountServiceImpl;
    }


    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody String newPassword) {
        // Find the password reset token by token value
        PasswordResetToken passwordResetToken = passwordResetTokenService.findPasswordResetTokenByToken(token);
        if (passwordResetToken == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        // Get the user associated with the password reset token
        AppUser user = passwordResetToken.getUser();

        // Update the user's password
        accountServiceImpl.updateUserPassword(user, newPassword);

        // Delete the password reset token as it has been used
        passwordResetTokenService.deletePasswordResetToken(passwordResetToken);

        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendPasswordResetEmail(@RequestBody Map<String, String> emailMap) {
        String email = emailMap.get("email");

        // Generate password reset token and save it to the database
        String resetToken = accountServiceImpl.generatePasswordResetToken(email);

        // Send the email with the reset token
        accountServiceImpl.sendPasswordResetEmail(email, resetToken);

        return ResponseEntity.ok().build();
    }
}
