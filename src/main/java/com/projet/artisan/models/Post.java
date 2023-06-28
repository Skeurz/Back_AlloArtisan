package com.projet.artisan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="post")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Post {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @CreationTimestamp
     @Column(updatable = false)
     Timestamp dateCreated;

     private String type;
     private String artisan;
     private String titreAnnonce;
     private String description;
     private String ville;
     private String imgUrl;
     private Long prix;


     @ManyToMany(mappedBy = "posts")
     private Collection<AppUser> users = new ArrayList<>();

}
