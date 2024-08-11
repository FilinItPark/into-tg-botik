package ru.itpark.infrastructure.db.repositories;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itpark.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class UserRepository {
    private final Connection connection;

    public List<User> getAll() {
        try {
            final ResultSet resultSet = connection.prepareStatement("""
                    select * from users
                    """).executeQuery();

            final List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                final User build = User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .balance(resultSet.getFloat("balance"))
                        .telegramId(resultSet.getLong("telegramId"))
                        .password(resultSet.getString("password"))
                        .build();

                users.add(build);
            }

            return users;
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
        }

        return null;
    }

    public User getByTelegramId(long telegramId) {
        User user = null;

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from users where telegramid = ?
                    """);

            preparedStatement.setLong(1, telegramId);

            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .balance(resultSet.getFloat("balance"))
                        .telegramId(resultSet.getLong("telegramId"))
                        .password(resultSet.getString("password"))
                        .build();

            }
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
        }

        return user;
    }

    public void create(User user) {

    }
}