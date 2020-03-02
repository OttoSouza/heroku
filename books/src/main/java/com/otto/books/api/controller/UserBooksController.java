package com.otto.books.api.controller;

import java.util.List;
import java.util.Optional;

import com.otto.books.api.dto.UpdateStatusDTO;
import com.otto.books.api.dto.UserBooksDTO;
import com.otto.books.exception.NotNullException;
import com.otto.books.model.entity.Books;
import com.otto.books.model.entity.User;
import com.otto.books.model.entity.User_Books;
import com.otto.books.model.enums.StatusUserBooks;
import com.otto.books.service.BooksService;
import com.otto.books.service.UserBooksService;
import com.otto.books.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * UserBooks
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userbooks")
public class UserBooksController {

    private final BooksService serviceBook;
    private final UserService serviceUser;
    private final UserBooksService service;

    private User_Books changeToUserBooks(UserBooksDTO dto) {
        User_Books userBooks = new User_Books();
        User user = this.serviceUser.getById(dto.getId_user())
                .orElseThrow(() -> new NotNullException("User not found"));
        userBooks.setUser(user);
        Books book = this.serviceBook.getById(dto.getId_book())
                .orElseThrow(() -> new NotNullException("Book not found"));
        userBooks.setBook(book);
        return userBooks;

    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserBooksDTO dto) {
        try {
            User_Books userBooks = changeToUserBooks(dto);
            userBooks = this.service.createUserBooks(userBooks);
            return ResponseEntity.ok(userBooks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserBooksDTO dto) {
        return this.service.getById(id).map(entity -> {
            try {
                User_Books userBooks = changeToUserBooks(dto);
                userBooks.setId(id);
                service.update(userBooks);
                return ResponseEntity.ok(userBooks);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("UserBooks Not Found", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User_Books>> find() {
        List<User_Books> userBooks = null;
        try {
            userBooks = this.service.getAllUserBooks();
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(userBooks);
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "estrela") Long id) {
        User_Books userBook = new User_Books();
        Optional<User> user = serviceUser.getById(id);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User Not Found");
        } else {
            userBook.setUser(user.get());
        }
        List<User_Books> userBooks = service.find(userBook);
        return ResponseEntity.ok(userBooks);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<User_Books> userBook = service.getById(id);
        if (!userBook.isPresent()) {
            return ResponseEntity.badRequest().body("Lancamento não encontrado.");
        }
        return ResponseEntity.ok(userBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.getById(id).map(entity -> {
            service.delete(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("User_Book não encontrado.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("status/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusDTO dto) {
        return this.service.getById(id).map(entity -> {
            StatusUserBooks status = StatusUserBooks.valueOf(dto.getStatus());
            if (status == null) {
                return ResponseEntity.badRequest().body("DEU RUIM CUZAO");
            }
            entity.setStatus(status);
            this.service.update(entity);
            return ResponseEntity.ok(entity);
        }).orElseGet(() -> new ResponseEntity("User_Book não encontrado.", HttpStatus.BAD_REQUEST));
    }

}