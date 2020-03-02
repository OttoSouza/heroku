package com.otto.books.impl;

import java.util.List;
import java.util.Optional;

import com.otto.books.exception.NotNullException;
import com.otto.books.model.entity.User;
import com.otto.books.model.repository.UserRepository;
import com.otto.books.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User authenticate(String email, String password) {
        Optional<User> userOptional = this.repository.findByEmail(email);
        if(!userOptional.isPresent()){
            throw new NotNullException("User not found");
        }

        if(!userOptional.get().getPassword().equals(password)){
            throw new NotNullException("Invalid password");
        }
        return userOptional.get();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        validateEmail(user.getEmail());
        return this.repository.save(user);
    }

    @Override
    public void validateEmail(String email) {
        if (this.repository.existsByEmail(email)) {
            throw new NotNullException("Email already been registered");
        }
    }

    @Override
    public List<User> getAllUser() {
        return this.repository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return this.repository.findById(id);
    }

}