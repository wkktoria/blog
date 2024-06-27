package io.github.wkktoria.blog.services;

import io.github.wkktoria.blog.models.Account;
import io.github.wkktoria.blog.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(final AccountRepository accountRepository, final PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account save(final Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.saveAndFlush(account);
    }

    public Optional<Account> findByEmail(final String email) {
        return accountRepository.findOneByEmail(email);
    }
}
