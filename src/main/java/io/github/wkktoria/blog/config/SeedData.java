package io.github.wkktoria.blog.config;

import io.github.wkktoria.blog.models.Account;
import io.github.wkktoria.blog.models.Authority;
import io.github.wkktoria.blog.models.Post;
import io.github.wkktoria.blog.repositories.AuthorityRepository;
import io.github.wkktoria.blog.services.AccountService;
import io.github.wkktoria.blog.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    private final PostService postService;
    private final AccountService accountService;
    private final AuthorityRepository authorityRepository;

    public SeedData(final PostService postService, final AccountService accountService, final AuthorityRepository authorityRepository) {
        this.postService = postService;
        this.accountService = accountService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {
            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Account account1 = new Account();
            account1.setUsername("admin");
            account1.setPassword("password");
            account1.setEmail("admin@blog.com");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);

            Account account2 = new Account();
            account2.setUsername("user");
            account2.setPassword("password");
            account2.setEmail("user@domain.com");
            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("Title of Post 1");
            post1.setContent("""       
                    This is a first post.
                                        
                    Example of list:
                    * list item 1
                    * list item 2
                    * list item 3
                                        
                    And an image of cat:
                    ![Cat](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fpixnio.com%2Ffree-images%2F2017%2F09%2F26%2F2017-09-26-07-47-55-550x825.jpg&f=1&nofb=1&ipt=432d5fdda32fc03478610aaa374a7325c855537adde8308464ad241a71c22cc4)                    
                    """);
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Title of Post 2");
            post2.setContent("""
                    This is a second post.
                                        
                                        
                    Examples of source code:
                    ```js
                    let greeting = 'Hello World';
                    console.log(greeting);
                    ```
                                        
                    ```python
                    greeting = 'Hello World';
                    print(greeting);
                    ```
                    """);
            post2.setAccount(account2);

            Post post3 = new Post();
            post3.setTitle("Title of Post 3");
            post3.setContent("""
                    This is a third post.
                                        
                    Example of table:
                    | Column 1      | Column 2      |
                    | ------------- | ------------- |
                    | Cell 1, Row 1 | Cell 2, Row 1 |
                    | Cell 1, Row 2 | Cell 1, Row 2 |
                    """);
            post3.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }
}
