package com.projet.artisan.repository;
import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.Message;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.stereotype.Repository;
import com.projet.artisan.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projet.artisan.services.AccountServiceImpl;
import java.util.List;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
   public AppUser findByUserName( String userName);


   List<AppUser> findByAdresse(String adresse);




   List<AppUser> findByAppRoles_IdRole(Long idRole);


   static Optional<AppUser> findByEmail(String email) {
      return null;
   }


   void deleteById(ID id);

   AppUser findByConfirmationToken(String confirmationToken);

   boolean existsByUserName(String userName);

   boolean existsByEmail(String userName);


   // AppUser delete(long id);
   // Boolean existsByUsername(String userName);

}
