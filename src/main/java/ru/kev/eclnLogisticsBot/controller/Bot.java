package ru.kev.eclnLogisticsBot.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import ru.kev.eclnLogisticsBot.service.BotService;

import java.util.ArrayList;
import java.util.List;

@Component

public class Bot extends TelegramLongPollingBot {
    private final BotService botService;

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
        this.botService = new BotService();
    }

    @Override
    public String getBotUsername() {
        return "${bot.name}";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        botService.getAnswer(this, update);
    }
}