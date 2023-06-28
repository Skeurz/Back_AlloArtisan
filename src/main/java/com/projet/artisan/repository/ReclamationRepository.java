package com.projet.artisan.repository;
import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.Reclamation;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.stereotype.Repository;
import com.projet.artisan.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projet.artisan.services.AccountServiceImpl;
import java.util.List;

import java.util.Optional;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {


    void deleteById(Long id);
}
