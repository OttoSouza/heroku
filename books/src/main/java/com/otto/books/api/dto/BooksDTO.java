package com.otto.books.api.dto;

import java.math.BigDecimal;

import lombok.*;

/**
 * BooksDTO
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BooksDTO {

    private String title;

    private String author;

    private Integer edition;

    private Integer year;

    private BigDecimal value;

}