package com.otto.books.api.controller;

import java.util.Optional;
import com.otto.books.service.BooksService;

import com.otto.books.api.dto.BooksDTO;
import com.otto.books.model.entity.Books;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * BooksController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService service;  

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Optional<Books> bookOptional = this.service.getById(id);
        if(!bookOptional.isPresent()){
            return ResponseEntity.badRequest().body("Book not found");
        }
        return ResponseEntity.ok(bookOptional);
    }
    
    @PostMapping
    public ResponseEntity create(@RequestBody BooksDTO dto) {
        try {
            Books book = changeToBooks(dto);
            book = this.service.createBook(book);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody BooksDTO dto){
            return this.service.getById(id).map(entity -> {
                Books book = changeToBooks(dto);
                book.setId(id);
                this.service.update(book);
                return  ResponseEntity.ok(book);
            }).orElseGet(() -> new ResponseEntity("UserBooks Not Found", HttpStatus.BAD_REQUEST));
    }
    
    private Books changeToBooks(BooksDTO dto) {
        Books book = new Books();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setEdition(dto.getEdition());
        book.setYear(dto.getYear());
        book.setValue(dto.getValue());
        return book;
    }
    
}