package com.otto.books.api.dto;

import lombok.*;

/**
 * UserBooksDTO
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserBooksDTO {

    private String status;
    private Long id_user;
    private Long id_book;

}