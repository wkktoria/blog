package io.github.wkktoria.blog.config;

import io.github.wkktoria.blog.models.Post;
import io.github.wkktoria.blog.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    private final PostService postService;

    public SeedData(final PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(final String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {
            Post post1 = new Post();
            post1.setTitle("Title of Post 1");
            post1.setContent("Content of Post 1");

            Post post2 = new Post();
            post2.setTitle("Title of Post 2");
            post2.setContent("Content of Post 2");

            Post post3 = new Post();
            post3.setTitle("Title of Post 3");
            post3.setContent("Content of Post 3");

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }
}
