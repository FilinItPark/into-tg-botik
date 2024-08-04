package ru.itpark.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Category {
    private int id;
    private String title;
}
