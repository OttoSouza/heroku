package com.otto.books.model.repository;

import com.otto.books.model.entity.User_Books;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserBooksRepository
 */
public interface UserBooksRepository extends JpaRepository<User_Books, Long> {

}