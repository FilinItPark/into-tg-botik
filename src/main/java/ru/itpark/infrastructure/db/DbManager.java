package ru.itpark.infrastructure.db;

import lombok.Getter;
import ru.itpark.infrastructure.configuration.DbConnectionFactory;
import ru.itpark.infrastructure.db.repositories.UserRepository;

import java.sql.Connection;

@Getter
public class DbManager {
    private final Connection connection = DbConnectionFactory.createConnection();

    private UserRepository userRepo = new UserRepository(connection);
}
