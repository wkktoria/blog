package io.github.wkktoria.blog.services;

import io.github.wkktoria.blog.models.Post;
import io.github.wkktoria.blog.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> getById(final Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }

        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public void delete(final Post post) {
        postRepository.delete(post);
    }
}
