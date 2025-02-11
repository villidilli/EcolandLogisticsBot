package ru.kev.eclnLogisticsBot.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kev.eclnLogisticsBot.controller.Bot;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotService {
    Update lastUpd = null;
    Update curUpd = null;
    String lastMsg = null;
    String curMsg = null;
    String lastQuery = null;
    String curQuery = null;
    Long chatId = null;
    InlineKeyboardMarkup kb = null;

    @SneakyThrows
    public void getAnswer(Bot bot, Update update) {
        lastUpd = curUpd;
        curUpd = update;
        if(curUpd.hasMessage()) {
            lastMsg = curMsg;
            curMsg = update.getMessage().getText();
            doProcess(bot, update.getMessage().getChatId(), curMsg);
        }
        if(curUpd.hasCallbackQuery()) {
            lastQuery = curQuery;
            curQuery = update.getCallbackQuery().getData();
            doProcess(bot, update.getCallbackQuery().getMessage().getChatId(), curQuery);
        }
    }

    @SneakyThrows
    private void doProcess(Bot bot, Long chatId, String text) {
        switch (text) {
            case "/start":
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Сотрудник *Эколенд*", "/roleEcln")))
                        .keyboardRow(List.of(getButton("Транспортная компания", "/roleTransport")))
                        .build();
                bot.execute(collectAnswer(chatId, "Приветствую Вас!\nНеобходимо авторизоваться\nВыберите тип аккаунта:", kb));
                break;
            case "/roleEcln":
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Проверить", "/acceptPassword")))
                        .build();
                bot.execute(collectAnswer(chatId,"Введите в текстовое поле пароль и нажмите *Проверить*", kb));
                break;
            case "/roleTransport":
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Проверить", "/acceptINN")))
                        .build();
                bot.execute(collectAnswer(chatId, "Введите ваш ИНН и нажмите *Проверить*:", kb));
                break;
            case "/acceptPassword": // TODO
                bot.execute(collectAnswer(chatId, "Вы успешно авторизованы, как сотрудник *Эколенд*\nВыберите")); break;
            case "/acceptINN": // TODO
                bot.execute(collectAnswer(chatId, "Вы ввели ИНН: " + curMsg)); break;
        }
    }

    private InlineKeyboardButton getButton(String textOnButton, String textToServer) {
        return InlineKeyboardButton.builder()
                .text(textOnButton)
                .callbackData(textToServer)
                .build();
    }

    private SendMessage collectAnswer(Long chatId, String textToSend) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .build();
    }

    private SendMessage collectAnswer(Long chatId, String textToSend, InlineKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .replyMarkup(keyboard)
                .build();
    }
}