package ru.itpark.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private int id;
    private long telegramId;
    private String name;
    private String password;
    private double balance;

}
