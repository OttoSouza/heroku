package com.otto.books.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.otto.books.exception.NotNullException;
import com.otto.books.model.entity.User_Books;
import com.otto.books.model.enums.StatusUserBooks;
import com.otto.books.model.repository.UserBooksRepository;
import com.otto.books.service.UserBooksService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

/**
 * UserBooksServiceImpl
 */
@Service
public class UserBooksServiceImpl implements UserBooksService {

    UserBooksRepository repository;

    public UserBooksServiceImpl(UserBooksRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User_Books createUserBooks(User_Books userBooks) {
        validate(userBooks);
        userBooks.setStatus(StatusUserBooks.NOT_READ);
        return this.repository.save(userBooks);
    }

    @Override
    @Transactional
    public User_Books update(User_Books userBooks) {
        Objects.nonNull(userBooks.getId());
        validate(userBooks);
        return this.repository.save(userBooks);
    }

    @Override
    public void delete(User_Books userBooks) {
        Objects.nonNull(userBooks.getId());
        this.repository.delete(userBooks);
    }

    @Override
    public void updateStatus(User_Books userBooks, final StatusUserBooks status) {
        userBooks.setStatus(status);
        update(userBooks);
    }

    @Override
    public List<User_Books> getAllUserBooks() {
        return this.repository.findAll();
    }

    @Override
    public Optional<User_Books> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void validate( User_Books userBooks) {
        if (userBooks.getUser() == null) {
            throw new NotNullException("Enter a valid user");
        }
    }

    @Override
    @Transactional
    public List<User_Books> find(User_Books userBook) {
        Example example = Example.of(userBook,
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
        return repository.findAll(example);

    }
}