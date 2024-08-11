package ru.itpark.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;

    private List<Category> category;
}
