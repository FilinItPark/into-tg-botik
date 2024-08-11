package ru.itpark.infrastructure.db;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itpark.domain.User;
import ru.itpark.domain.exception.AccessDeniedException;
import ru.itpark.domain.exception.UserNotFoundException;
import ru.itpark.infrastructure.configuration.DbConnectionFactory;
import ru.itpark.infrastructure.db.repositories.UserRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Getter
public class DbManager {
    private final Connection connection = DbConnectionFactory.createConnection();
    private final Logger log = LoggerFactory.getLogger(DbManager.class);
    private UserRepository userRepo = new UserRepository(connection);

    public List<User> getUsers() {
        return userRepo.getAll();
    }

    public Optional<User> getUserByTelegramId(long telegramId) {
        return userRepo.getByTelegramId(telegramId);
    }

    public void createUser(String name, double balance, long telegramId, String rawPassword) {
        final String passwordHash = BCrypt.withDefaults().hashToString(16, rawPassword.toCharArray());

        userRepo.create(
                User.builder()
                        .name(name)
                        .balance(balance)
                        .telegramId(telegramId)
                        .password(passwordHash).build()
        );
    }

    public boolean authorizeUser(long telegramId, String password) {
        final User user = getUserByTelegramId(telegramId)
                .orElseThrow(UserNotFoundException::new);

        final BCrypt.Verifyer verifyer = BCrypt.verifyer();

        log.info("Начался процесс сверки пароля... подождите немного");
        final BCrypt.Result verify = verifyer.verify(password.toCharArray(), user.getPassword().toCharArray());

        if (!verify.verified) {
            throw new AccessDeniedException();
        }

        return true;
    }
}
