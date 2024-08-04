package ru.itpark.infrastructure.db.repositories;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Slf4j
public class UserRepository {
    private final Connection connection;

    public void getAll() {
        try {
            final ResultSet resultSet = connection.prepareStatement("select * from users").executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + ": " + resultSet.getString("username") + ": " + resultSet.getString("password") + ": " + resultSet.getString("balance"));
            }

            System.out.println(resultSet);
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
        }
    }
}
