package io.github.wkktoria.blog.config;

import io.github.wkktoria.blog.models.Account;
import io.github.wkktoria.blog.models.Post;
import io.github.wkktoria.blog.services.AccountService;
import io.github.wkktoria.blog.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    private final PostService postService;
    private final AccountService accountService;

    public SeedData(final PostService postService, final AccountService accountService) {
        this.postService = postService;
        this.accountService = accountService;
    }

    @Override
    public void run(final String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {
            Account account1 = new Account();
            account1.setUsername("admin");
            account1.setPassword("password");
            account1.setEmail("admin@blog.com");

            Account account2 = new Account();
            account2.setUsername("user");
            account2.setPassword("password");
            account2.setEmail("user@domain.com");

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("Title of Post 1");
            post1.setContent("Et officiis sunt id. Unde expedita assumenda sit amet omnis ipsa dicta voluptas.");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Title of Post 2");
            post2.setContent("Omnis et molestiae tenetur et fugiat est doloribus. Consectetur voluptas quia dolor voluptatum temporibus. Impedit est quas ex perspiciatis autem reprehenderit. Voluptate non quisquam magnam quia quidem ipsam nobis sint. Dolorem eligendi quo optio tenetur est corrupti debitis hic. Autem laboriosam fuga veniam consequatur quo aut consequatur dolores.");
            post2.setAccount(account2);

            Post post3 = new Post();
            post3.setTitle("Title of Post 3");
            post3.setContent("Omnis et molestiae tenetur et fugiat est doloribus. Consectetur voluptas quia dolor voluptatum temporibus. Impedit est quas ex perspiciatis autem reprehenderit. Voluptate non quisquam magnam quia quidem ipsam nobis sint. Dolorem eligendi quo optio tenetur est corrupti debitis hic. Autem laboriosam fuga veniam consequatur quo aut consequatur dolores.");
            post3.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }
}
