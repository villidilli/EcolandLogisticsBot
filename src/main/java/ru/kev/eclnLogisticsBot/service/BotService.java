package ru.kev.eclnLogisticsBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kev.eclnLogisticsBot.controller.Bot;

@Service
public class BotService {

    public SendMessage getAnswer(Bot bot, Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("ТЕКСТ")
                .build();
    }
}