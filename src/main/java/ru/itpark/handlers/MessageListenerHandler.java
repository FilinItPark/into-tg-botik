package ru.itpark.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.itpark.infrastructure.db.DbManager;
import ru.itpark.secrets.SecretManager;
import ru.itpark.state.UserState;

import java.util.ArrayList;
import java.util.List;

public class MessageListenerHandler implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient client = new OkHttpTelegramClient(SecretManager.getToken());
    private final DbManager dbManager = new DbManager();
    private final UserState userState = UserState.getInstance();

    private final Logger logger = LoggerFactory.getLogger(MessageListenerHandler.class);

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            logger.info("Message received: {}", update.getMessage().getText());

            Message message = update.getMessage();

            if (message.hasText()) {
//                sendMessage(message.getChatId(), message.getText());

                // /посчитай + 2 2
//               userState.getMessages().put(message.getChatId(), e);

                userState.getMessages().computeIfAbsent(message.getChatId(), k -> new ArrayList<>());

                userState.getMessages().get(message.getChatId()).add(message);

                userState.execute("createUser", message.getChatId());

            }
            /*
            1) trace - уровень логирования, при котором логируется абсолюнто вся информация
            2) debug - информация, которая необходимо для отлдаки
            3) info - информация, которая позволяет нам понять ОСНОВНЫЕ этапы работы нашего приложения (подключились к базе данных)
            4) warn - предупреждение о некритичиной ситуации - наше приложение работает, но работает не все
            5) error - все, приплыли, приложение работать не может
             */
        }


        if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            var content = callbackQuery.getData();

            if (content.contains("+")) {
                var parts = content.split("\\+");
                int sum = Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                sendMessage(callbackQuery.getFrom().getId(), String.valueOf(sum));
            } else if (content.contains("/")) {
                var parts = content.split("/");
                double sum = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
                sendMessage(callbackQuery.getFrom().getId(), String.valueOf(sum));
            } else if (content.contains("-")) {
                var parts = content.split("-");
                int sum = Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]);
                sendMessage(callbackQuery.getFrom().getId(), String.valueOf(sum));
            } else {
                var parts = content.split("\\*");
                int sum = Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
                sendMessage(callbackQuery.getFrom().getId(), String.valueOf(sum));
            }
        }
    }

    private void sendMessage(Long chatId, String a, String b) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text("Выберите операцию:")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(new InlineKeyboardRow(
                                        List.of(
                                                InlineKeyboardButton
                                                        .builder()
                                                        .text("Сложить")
                                                        .callbackData(a + "+" + b)
                                                        .build(),
                                                InlineKeyboardButton
                                                        .builder()
                                                        .text("Умножить")
                                                        .callbackData(a + "*" + b)
                                                        .build(),
                                                InlineKeyboardButton
                                                        .builder()
                                                        .text("Поделить")
                                                        .callbackData(a + "/" + b)
                                                        .build(),
                                                InlineKeyboardButton
                                                        .builder()
                                                        .text("Найти разность")
                                                        .callbackData(a + "-" + b)
                                                        .build()

                                        )
                                )
                        ).build()
                )
                .build();

        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Не получилось отправить сообщение: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Не получилось отправить сообщение: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
