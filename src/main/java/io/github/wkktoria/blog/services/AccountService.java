package io.github.wkktoria.blog.services;

import io.github.wkktoria.blog.models.Account;
import io.github.wkktoria.blog.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(final Account account) {
        return accountRepository.save(account);
    }
}
