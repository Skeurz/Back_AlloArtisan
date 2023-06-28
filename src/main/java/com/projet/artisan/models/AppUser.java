package com.projet.artisan.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data

@AllArgsConstructor
@NoArgsConstructor
@Table(name="appUser")
public class AppUser implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String nom;
    private String prenom;
    private Long telephone;
    private String adresse;
    private String profileImage;


    @Column(nullable = false, unique = true)
    private String userName;
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles =new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "user_post",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Collection<Post> posts = new ArrayList<>();


    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "email_confirmed")
    private boolean emailConfirmed;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateCreated;
    @UpdateTimestamp
    Timestamp lastModified;




}
