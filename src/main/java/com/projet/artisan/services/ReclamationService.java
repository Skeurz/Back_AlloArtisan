package com.projet.artisan.services;
import com.projet.artisan.models.Reclamation;
import java.util.List;
import com.projet.artisan.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.Collections;



@Service

public class ReclamationService {


    private final ReclamationRepository reclamationRepository;

    public ReclamationService(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    public Reclamation saveReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

}