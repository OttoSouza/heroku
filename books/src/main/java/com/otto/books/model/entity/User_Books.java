package com.otto.books.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.otto.books.model.enums.StatusUserBooks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User_Books
 */

 
@Entity
@Table(name="user_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private StatusUserBooks status;
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    @OneToOne
    @JoinColumn(name="id_book")
    private Books book;


}