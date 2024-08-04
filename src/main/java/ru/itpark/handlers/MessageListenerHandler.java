package ru.itpark.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.itpark.infrastructure.db.DbManager;
import ru.itpark.secrets.SecretManager;

public class MessageListenerHandler implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client = new OkHttpTelegramClient(SecretManager.getToken());
    private final DbManager dbManager = new DbManager();

    private final Logger logger = LoggerFactory.getLogger(MessageListenerHandler.class);

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            logger.info("Message received: {}", update.getMessage().getText());

            Message message = update.getMessage();

            if (message.hasText()) {

//                sendMessage(message.getChatId(), message.getText());
            }

            /*
            1) trace - уровень логирования, при котором логируется абсолюнто вся информация
            2) debug - информация, которая необходимо для отлдаки
            3) info - информация, которая позволяет нам понять ОСНОВНЫЕ этапы работы нашего приложения (подключились к базе данных)
            4) warn - предупреждение о некритичиной ситуации - наше приложение работает, но работает не все
            5) error - все, приплыли, приложение работать не может
             */
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);

        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Не получилось отправить сообщение: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
