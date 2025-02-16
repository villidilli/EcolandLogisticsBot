package ru.kev.eclnLogisticsBot.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kev.eclnLogisticsBot.service.BotService;
import ru.kev.eclnLogisticsBot.service.impl.BotServiceImpl;
import ru.kev.eclnLogisticsBot.service.UserService;

@Component
public class Bot extends TelegramLongPollingBot {
    private final BotService botService;

    public Bot(@Value("${bot.token}") String botToken, BotService botService) {
        super(botToken);
        this.botService = botService;
    }

    @Override
    public String getBotUsername() {
        return "${bot.name}";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        botService.processMessage(update, this);
    }
}