package com.otto.books.service;

import java.util.List;
import java.util.Optional;

import com.otto.books.model.entity.User;

/**
 * UserService
 */
public interface UserService {

    //salvar, lista, validar, atualizar, autenticar , buscarId

    User authenticate(String email, String password);
    User createUser(User user);
    void validateEmail(String email);
    List<User> getAllUser();
    Optional<User> getById(Long id);


}