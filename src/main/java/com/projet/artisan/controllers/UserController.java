package com.projet.artisan.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projet.artisan.ArtisanApplication;
import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.models.Post;
import com.projet.artisan.repository.AppRoleRepository;
import com.projet.artisan.repository.AppUserRepository;
import com.projet.artisan.repository.PostRepository;
import com.projet.artisan.services.AccountService;
import com.projet.artisan.services.ArtisanService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;


import java.util.*;

@CrossOrigin(origins="http://localhost:4200")

@RestController

public class UserController {

    private AccountService accountService;
    private final AppUserRepository appUserRepository;

    private final AppRoleRepository appRoleRepository;
    private  final AuthenticationManager authenticationManager;


    public UserController(AccountService accountService, AuthenticationManager authenticationManager, AppUserRepository appUserRepository,
                          AppRoleRepository appRoleRepository
    ) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;

    }



    @GetMapping(path="/users") //Recevoir les informations de tous les utilisateurs
   // @PostAuthorize("hasAuthority('user')")
    public List<AppUser> getAllUsers(){
        return accountService.listeUsers();
    }





    @DeleteMapping("delete/{id}") //Supprimez l'utilisateur en fonction de son id
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        accountService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")//Generateur de token
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody AppUser authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }

        String token = generateJWT(authRequest.getUserName());

        // Créer une map contenant le token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        // Renvoyer une réponse JSON contenant la map
        return ResponseEntity.ok().body(response);
    }
    private String generateJWT(String username) {
        AppUser user =accountService.loadUserByUserName(username);
        Long id=user.getId();

        String secret = "mysecret123";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 10); // 10 heures session

        // Build the JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("id", id);
        claims.put("exp", expiryDate);

        // Build the JWT and sign it with the secret key
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("mysecret123")
                .withHeader(null)
                .withClaim("payload", claims)
                .sign(algorithm);
        return token;
    }
    @GetMapping (path="/profil/{id}")   //Recevoir donnée d'un utilisateur en fonction de l'ip fourni
    public AppUser getUserById( @PathVariable Long id){
        return accountService.getUser(id);
    }


    @PutMapping("edit/{id}")   //Modifier les données de l'utilisateur en fonction de l'ip fourni
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser) {
        AppUser user = accountService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setNom(updatedUser.getNom());
        user.setPrenom(updatedUser.getPrenom());
        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        user.setTelephone(updatedUser.getTelephone());
        user.setAdresse(updatedUser.getAdresse());
        user.setProfileImage(updatedUser.getProfileImage());
       /* user.setPassword(updatedUser.getPassword());*/

        AppUser updatedAppUser = accountService.updateUser(user);
        return ResponseEntity.ok(updatedAppUser);
    }




    public class Constants {
        public static final String DEFAULT_PROFILE_IMAGE
        = "https://static.vecteezy.com/system/resources/previews/005/544/718/original/profile-icon-design-free-vector.jpg";
    }


    private AppUserRepository repo;
    @PostMapping(path="/register")   // Création du compte
    public AppUser appUser(@RequestBody AppUser user) {
        if (user.getProfileImage() == null || user.getProfileImage().isEmpty()) {
            user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE);
        }
        try {
            return accountService.addUser(user);
        } catch (ResponseStatusException ex) {
            throw ex; // Let the exception propagate to capture and handle it in the frontend
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating account", ex);
        }
    }





    @PostMapping(path="/roles") // Ajout de rôles
    public AppRole appRole ( @RequestBody AppRole role){
        return accountService.addRole(role);
    }


    @PostMapping(path="/addRoleToUser") // Octroyer un role a un utilisateur
    public void addRoleToUser( @RequestBody RoleForm roleForm){
        accountService.addRoleToUser(roleForm.getUserName(),roleForm.getRoleName());
    }

    @GetMapping("users/{username}") // Recevoir les données d'un utilisateur en fonction du nom de l'utilisateur fourni
    public AppUser getUserByUserName(@PathVariable String username){
        return accountService.loadUserByUserName(username);

    }


    @GetMapping("/{id}/roles")     //Reçevoir le role en fonction de l'id de l'utilisateur fourni
    public ResponseEntity<List<AppRole>> getUserRoles(@PathVariable Long id) {
        List<AppRole> roles = accountService.getUserRoles(id);
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roles);
        }
    }

    @GetMapping("/{username}/role")   //Reçevoir le role en fonction du nom d'utilisateur fourni
    public ResponseEntity<List<AppRole>> getRoleByUsername(@PathVariable String username) {
        List<AppRole> roles = accountService.getRoleByUsername(username);
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roles);
        }
    }

    @GetMapping("/{adresse}") // Recevoir les données des utilisateurs en fonction de l'adresse fourni
    public ResponseEntity<List<AppUser>> getUserByAdresse(@PathVariable String adresse) {
        List<AppUser> user = accountService.getUserByAdresse(adresse);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/roles/{idRole}") // Recevoir les données des utilisateurs qui possèdent l'id du role fourni
    public ResponseEntity<List<AppUser>> getUsersByRoleId(@PathVariable Long idRole) {
        List<AppUser> users = accountService.getUsersByRoleId(idRole);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }





}
@Data
class RoleForm{
    private String userName;
    private String roleName;


}

