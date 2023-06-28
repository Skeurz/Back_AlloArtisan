package com.projet.artisan.repository;

import com.projet.artisan.models.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRole(String role);

}
