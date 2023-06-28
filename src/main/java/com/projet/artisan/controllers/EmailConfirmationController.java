package com.projet.artisan.controllers;

import com.projet.artisan.models.AppUser;
import com.projet.artisan.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class EmailConfirmationController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        AppUser user = appUserRepository.findByConfirmationToken(token);

        if (user == null) {
            // Handle invalid or expired token
            return "Invalid Token";
        }

        // Update the user's status to indicate email confirmation
        user.setEmailConfirmed(true);
        appUserRepository.save(user);

        // Redirect the user to a success page or display a success message
        return "Email Confirmed";
    }


    @GetMapping("/{userName}/confirm")
    public ResponseEntity<Boolean> verifyEmailConfirmation(@PathVariable String userName) {
        AppUser user = appUserRepository.findByUserName(userName);
        if (user != null) {
            if (user.isEmailConfirmed()) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

}
