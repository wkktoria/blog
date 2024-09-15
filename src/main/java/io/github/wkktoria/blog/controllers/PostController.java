package io.github.wkktoria.blog.controllers;

import io.github.wkktoria.blog.models.Account;
import io.github.wkktoria.blog.models.Post;
import io.github.wkktoria.blog.services.AccountService;
import io.github.wkktoria.blog.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostController {
    private final PostService postService;
    private final AccountService accountService;

    public PostController(final PostService postService, final AccountService accountService) {
        this.postService = postService;
        this.accountService = accountService;
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "post_new";
    }

    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String saveNewPost(@ModelAttribute Post post, Principal principal) {
        String authUsername = "anonymous";

        if (principal != null) {
            authUsername = principal.getName();
        }

        Account account = accountService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        post.setAccount(account);

        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "anonymous";

        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (post.getAccount().getEmail().equalsIgnoreCase(authUsername) || Objects.requireNonNull(principal).toString().contains("ROLE_ADMIN")) {
                model.addAttribute("post", post);
                return "post_edit";
            } else {
                return "403";
            }
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

            postService.save(existingPost);
        }

        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/";
        } else {
            return "404";
        }
    }
}
