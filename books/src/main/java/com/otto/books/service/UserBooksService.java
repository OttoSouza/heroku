package com.otto.books.service;

import java.util.List;
import java.util.Optional;

import com.otto.books.model.entity.User_Books;
import com.otto.books.model.enums.StatusUserBooks;

/**
 * UserBooksService
 */
public interface UserBooksService {

    User_Books createUserBooks(User_Books userBooks);

    User_Books update(User_Books userBooks);

    void delete(User_Books userBooks);

    List<User_Books> find(User_Books userBook);

    void updateStatus(User_Books userBooks, StatusUserBooks status);

    List<User_Books> getAllUserBooks();

    Optional<User_Books> getById(Long id);

    void validate(User_Books userBooks);
}