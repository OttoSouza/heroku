package com.otto.books.api.dto;
import lombok.*;
/**
 * UserDTO
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String email;
}