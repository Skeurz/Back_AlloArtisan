package com.projet.artisan.services;
import com.projet.artisan.models.PasswordResetToken;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.repository.AppRoleRepository;
import com.projet.artisan.repository.AppUserRepository;
import com.projet.artisan.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;



import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional

public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;






    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository= appRoleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public AppUser addUser(AppUser user) {
        String pw = user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));

        // Genère le token de confirmation
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);

        // Sauvegarde l'utilisateur dans la bdd
        AppUser savedUser = appUserRepository.save(user);

        // Envoi l'email de confirmation
        sendConfirmationEmail(savedUser);

        return savedUser;
    }

    private void sendConfirmationEmail(AppUser user) {
        String confirmationLink = "http://localhost:4200/confirm?token=" + user.getConfirmationToken();

        Email from = new Email("");
        String subject = "Email Confirmation";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", "Confirmez votre email en suivant ce lien : " + confirmationLink);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);


        } catch (IOException ex) {

        }}


    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        return appUserRepository.save(user);
    }

    @Override
    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).get();
    }

    @Override
    public AppUser getUserById(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }



    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser user= appUserRepository.findByUserName(userName);
        AppRole role= appRoleRepository.findByRole(roleName);
        user.getAppRoles().add(role);
    }

    @Override
    public void updateUserPassword(AppUser user, String newPassword) {
        // Update the user's password with the new value
        user.setPassword(newPassword);

        // Save the updated user in the repository
        appUserRepository.save(user);
    }


    @Override
    public List<AppRole> getUserRoles(Long id) {
        AppUser user = appUserRepository.findById(id).orElse(null);
        if (user != null) {
            return (List<AppRole>) user.getAppRoles();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AppRole> getRoleByUsername(String userName) {
        AppUser user = appUserRepository.findByUserName(userName);
        if (user != null) {
            return (List<AppRole>) user.getAppRoles();
        }
        return Collections.emptyList();
    }


    @Override
    public AppRole addRole(AppRole role) {

        return appRoleRepository.save(role);
    }

    @Override
    public List<AppUser> listeUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return appUserRepository.findByUserName(userName);
    }


    @Override
    public List<AppUser> getUserByAdresse(String adresse) {
        return appUserRepository.findByAdresse(adresse);
    }


    @Override
    public List<AppUser> getUsersByRoleId(Long idRole) {
        return appUserRepository.findByAppRoles_IdRole(idRole);
    }


    private AppUser findUserByEmail(String email) {
        Optional<AppUser> userOptional = AppUserRepository.findByEmail(email);
        return userOptional.orElse(null);
    }
    private String generateUniqueToken() {
        String token = UUID.randomUUID().toString();
        return token;
    }
    public String generatePasswordResetToken(String email) {
        String resetToken = generateUniqueToken();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(resetToken);
        token.setUser(findUserByEmail(email)); // Implement this method to find the user by email

        // Save the token to the database
        passwordResetTokenRepository.save(token);

        return resetToken;

        // Send the password reset email with the token
        // You can use an email service or library to send the email
    }


    public void sendPasswordResetEmail(String email, String resetToken) {
        // Compose the email
        String emailSubject = "Password Reset";
        String emailBody = "Please click on the following link to reset your password: "
                + "http://your-frontend-url/reset-password?token=" + resetToken;

        // Send the email using your preferred email sending mechanism (e.g., JavaMail, SMTP client, third-party service)
        // Implement the code to send the email to the specified email address with the subject and body

        // Example using JavaMail
        try {
            // Create a new JavaMailSender instance and configure it with your email server settings
            JavaMailSender mailSender = new JavaMailSenderImpl();

            // Create a new SimpleMailMessage and set the recipient, subject, and body
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(emailSubject);
            mailMessage.setText(emailBody);

            // Send the email
            mailSender.send(mailMessage);
        } catch (MailException e) {
            // Handle any exceptions that occur during the email sending process
            e.printStackTrace();
            // You can log the error or handle it based on your application's requirements
        }
    }

}
