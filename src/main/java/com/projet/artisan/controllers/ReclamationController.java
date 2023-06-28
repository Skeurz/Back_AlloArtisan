package com.projet.artisan.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projet.artisan.ArtisanApplication;
import com.projet.artisan.models.Reclamation;

import com.projet.artisan.repository.ReclamationRepository;

import com.projet.artisan.services.ReclamationService;

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


public class ReclamationController {

    private final ReclamationService reclamationService;

    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @PostMapping("/ajouter/reclamation") // Postuler une reclamation
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        Reclamation savedReclamation = reclamationService.saveReclamation(reclamation);
        return ResponseEntity.ok(savedReclamation);
    }

    @GetMapping("/reclamations")  // Recevoir les reclamations
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        return ResponseEntity.ok(reclamations);
    }

    @DeleteMapping("/deletereclamation/{id}")  // Supprimer une reclamation en fonction de l'id de la reclamation fourni
    public ResponseEntity<String> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.ok("Reclamation deleted successfully");
    }

}
