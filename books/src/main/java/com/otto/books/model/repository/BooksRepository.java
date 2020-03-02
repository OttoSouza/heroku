package com.otto.books.model.repository;

import com.otto.books.model.entity.Books;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BooksRepository
 */ 
public interface BooksRepository extends JpaRepository<Books, Long> {

     
}