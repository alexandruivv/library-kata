package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.model.UserCredentials;
import com.softwaremind.librarykata.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class UserService extends BaseCrudService<User> {
    private final UserCredentialsService userCredentialsService;

    public UserService(UserRepository repository, UserCredentialsService userCredentialsService) {
        super(repository);
        this.userCredentialsService = userCredentialsService;
    }

    public Optional<User> findByUserCredentials(String username, String password) {
        return userCredentialsService.findByCredentials(username, password).map(UserCredentials::getUser);
    }
}
