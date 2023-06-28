package com.projet.artisan.services;

import com.projet.artisan.models.AppRole;
import com.projet.artisan.models.AppUser;


import java.util.List;

public interface AccountService {

    default void deleteUser(Long id) {
    }
    //static AppUser deleteUser(int id);

    AppUser addUser(AppUser user);
    AppUser getUser(Long id);

    public AppUser updateUser(AppUser user);

    AppUser getUserById(Long id);

    AppUser saveUser(AppUser user);



    List<AppRole> getUserRoles(Long id);
    List<AppRole> getRoleByUsername(String userName);


    AppRole addRole(AppRole role);

    List<AppUser> listeUsers();
    AppUser loadUserByUserName(String userName);

 //   AppUser delete(Long id);
 //   AppUser loadUserByAdresse(String adresse);

    List<AppUser> getUserByAdresse(String adresse);


    List<AppUser> getUsersByRoleId(Long idRole);

   // AppUser getUserByUserName(String userName);

    void addRoleToUser(String userName, String role);


    void updateUserPassword(AppUser user, String newPassword);
}
