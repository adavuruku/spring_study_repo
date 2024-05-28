package com.example.retry_recover.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Book {

    @Id
    Long id;
    String title;
    String isbn;
    Integer qty;

}