package com.lucas.domain.service;

import com.lucas.common.exceptions.AuthorizationException;
import com.lucas.common.exceptions.UserDoesNotExistException;
import com.lucas.domain.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Transactional
    public User create(User user) {
        boolean existUser = User.searchByEmail(user.getEmail())
                .stream().anyMatch(existingUser -> !existingUser.equals(user));

        if (existUser) {
            throw new AuthorizationException("User with email:" + user.getEmail() + ", already exists!");
        }

        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        user.persist();

        return user;
    }

    public Optional<User> searchByEmail(String email) {
        Optional<User> user = User.searchByEmail(email);

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("Email user: "+ email + ", does not exist!");
        }

        return user;
    }
}
