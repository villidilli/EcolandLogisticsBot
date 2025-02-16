package ru.kev.eclnLogisticsBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kev.eclnLogisticsBot.controller.Bot;

public interface BotService {
    void processMessage(Update update, Bot bot);
}