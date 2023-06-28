package com.projet.artisan.services;

import com.projet.artisan.models.AppUser;
import com.projet.artisan.repository.AppRoleRepository;
import com.projet.artisan.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.projet.artisan.models.Post;
import com.projet.artisan.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional

public class ArtisanServiceImp implements ArtisanService {

    @Autowired
    private PostRepository postRepository;

    public ArtisanServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;    }
    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }


    @Override
    public List<Post> getPostsByType(String type) {
        return postRepository.findByType(type);
    }

    @Override
    public List<Post> getPostsByArtisan(String artisan) {
        return postRepository.findByArtisan(artisan);
    }

    @Override
    public List<Post> getPostsByVille(String ville) {
        return postRepository.findByVille(ville);
    }



    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
