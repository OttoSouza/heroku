package com.otto.books.impl;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.otto.books.model.entity.Books;
import com.otto.books.model.repository.BooksRepository;
import com.otto.books.service.BooksService;

import org.springframework.stereotype.Service;

/**
 * BooksServiceImpl
 */
@Service
public class BooksServiceImpl implements BooksService {
    
    BooksRepository repository;

    public BooksServiceImpl(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Books createBook(Books book) {
        return this.repository.save(book);
    }

    @Override
    public Books update(Books book) {
        Objects.nonNull(book.getId());
        return this.repository.save(book);
   
    }

    @Override
    public void deleteBook(Books book) {
        Objects.requireNonNull(book.getId());
        this.repository.delete(book);
    }

    @Override
    public void updateBook(Books book) {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<Books> getById(Long id) {
        return this.repository.findById(id);
    }

}