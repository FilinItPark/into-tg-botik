package ru.itpark.domain;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static User createNew(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .balance(resultSet.getFloat("balance"))
                    .telegramId(resultSet.getLong("telegramId"))
                    .password(resultSet.getString("password"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
