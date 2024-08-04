package ru.itpark.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private double balance;

}
