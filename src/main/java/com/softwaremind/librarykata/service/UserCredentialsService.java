package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.UserCredentials;
import com.softwaremind.librarykata.repository.UserCredentialsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class UserCredentialsService extends BaseCrudService<UserCredentials> {
    private final UserCredentialsRepository repository;

    public UserCredentialsService(UserCredentialsRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Optional<UserCredentials> findByCredentials(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }
}
