package com.otto.books.service;

import java.util.Optional;

import com.otto.books.model.entity.Books;

/**
 * BooksService
 */
public interface BooksService {

    Books createBook(Books book);

    Books update(Books book);

    void deleteBook(Books book);

    void updateBook(Books book);

    Optional<Books> getById(Long id);
    
}