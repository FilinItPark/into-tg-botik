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
import java.util.Optional;

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

    public Optional<User> getByTelegramId(long telegramId) {
        User user = null;

        var start = System.currentTimeMillis();

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
        var end = System.currentTimeMillis();

        log.info("Время выполнения запроса: {} ms", end - start);
        return Optional.of(user);
    }

    public void create(User user) {
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into users (name, balance, telegramid,password) values (?,?,?,?); 
                    """);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setDouble(2, user.getBalance());
            preparedStatement.setLong(3, user.getTelegramId());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}