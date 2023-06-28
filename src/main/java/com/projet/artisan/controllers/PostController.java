package com.projet.artisan.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projet.artisan.ArtisanApplication;
import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.AppUser;
import com.projet.artisan.models.Post;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:4200")

@RestController
public class PostController {
    private ArtisanService artisanService;
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository, ArtisanService artisanService)
    {
        this.artisanService = artisanService;
        this.postRepository = postRepository;
    }

    @PostMapping("/offre") // Postuler une offre
    public Post post (@RequestBody Post post) {
        return artisanService.addPost(post);
    }

    @GetMapping(path="/offres") // Recevoir toutes les offres
    public List<Post> getAllPosts(){ return artisanService.getAllPosts();}


    @DeleteMapping("deletepost/{id}") // Supprimer une offre en fonction de l'id de l'offre fourni
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        artisanService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping (path="/offre/{id}")  // Recevoir une offre en fonction de l'id de l'offre fourni
    public Post getPostById( @PathVariable Long id){
        return artisanService.getPost(id);
    }




    @PutMapping("editoffre/{id}") // Editer une offre en fonction de l'id fourni
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post post = artisanService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        post.setType(updatedPost.getType());
        post.setArtisan(updatedPost.getArtisan());
        post.setTitreAnnonce(updatedPost.getTitreAnnonce());
        post.setVille(updatedPost.getVille());
        post.setImgUrl(updatedPost.getImgUrl());
        post.setPrix(updatedPost.getPrix());
        post.setDescription(updatedPost.getDescription());


        Post updatedPosty = artisanService.updatePost(post);
        return ResponseEntity.ok(updatedPosty);
    }

    @GetMapping("/offres/{type}") // Recevoir une/des offre par type (Offre ou Besoin)
    public ResponseEntity<List<Post>> getPostsByType(@PathVariable String type) {
        List<Post> posts = artisanService.getPostsByType(type);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/offres/u/{artisan}") // Recevoir une/des offre par utilisateur
    public ResponseEntity<List<Post>> getPostsByArtisan(@PathVariable String artisan) {
        List<Post> posts = artisanService.getPostsByArtisan(artisan);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/offres/ville/{ville}") // Recevoir une/des offre par utilisateur
    public ResponseEntity<List<Post>> getPostsByVille(@PathVariable String ville) {
        List<Post> posts = artisanService.getPostsByVille(ville);
        return ResponseEntity.ok(posts);
    }




}