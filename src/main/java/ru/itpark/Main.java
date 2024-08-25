package ru.itpark;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.itpark.handlers.MessageListenerHandler;
import ru.itpark.infrastructure.db.DbManager;
import ru.itpark.secrets.SecretManager;


@Slf4j
public class Main {
    public static void main(String[] args) {
        var db = new DbManager();


        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(SecretManager.getToken(), new MessageListenerHandler());


             Thread.currentThread().join();
//            db.getUserRepo().getAll();

//            db.createUser("Ivan", 5000.0, 12345L, "qwerty");
//            System.out.println("Успешно авторизовались: " + db.authorizeUser(12345L, "qwerty"));
//            System.out.println(db.authorizeUser(12345L, "wqwerty"));

            log.info("Bot registered");
        } catch (Exception e) {
            log.error("Error while registering bot", e);
        }
    }
}