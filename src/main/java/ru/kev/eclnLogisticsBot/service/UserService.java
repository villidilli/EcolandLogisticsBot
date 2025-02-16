package ru.kev.eclnLogisticsBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserService {
    void saveNewUser(Update update);
}