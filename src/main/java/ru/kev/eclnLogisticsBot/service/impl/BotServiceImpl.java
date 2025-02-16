package ru.kev.eclnLogisticsBot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.kev.eclnLogisticsBot.controller.Bot;
import ru.kev.eclnLogisticsBot.model.Password;
import ru.kev.eclnLogisticsBot.service.BotService;
import ru.kev.eclnLogisticsBot.service.CompanyService;
import ru.kev.eclnLogisticsBot.service.PasswordService;
import ru.kev.eclnLogisticsBot.service.UserService;
import ru.kev.eclnLogisticsBot.utils.CompanyType;

import java.util.LinkedList;
import java.util.List;

import static ru.kev.eclnLogisticsBot.utils.Command.CHECK_INN;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    private final UserService userService;
    private final CompanyService companyService;
    private final PasswordService passwordService;
    private LinkedList<String> textMsgList = new LinkedList<>();

    @SneakyThrows
    @Override
    public void processMessage(Update update, Bot bot) {
        String incomeMsg = null;
        Long chatId = null;
        if (update.hasMessage()) {
            incomeMsg = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
        }
        if (update.hasCallbackQuery()) {
            incomeMsg = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        saveTextMsg(incomeMsg);
        if (textMsgList.size() >= 2 && textMsgList.get(textMsgList.size() - 2).equals("/checkPassword")) {
            Password savedPassword = passwordService.checkPassword(textMsgList.getLast());
            if(savedPassword != null) {
                bot.execute(collectAnswer(chatId, "Вы успешно авторизованы, как \n------> " + savedPassword.getCompanyType().name()));
                userService.saveNewUser(update);
                if(savedPassword.getCompanyType() == CompanyType.TRANSPORT_COMPANY) {
                    bot.execute(collectAnswer(chatId, "Для размещения ставок по заявкам необходимо указать ИНН компании"));
                    InlineKeyboardMarkup kb = InlineKeyboardMarkup.builder()
                            .keyboardRow(List.of(getButton("Ввести ИНН", CHECK_INN.name()))).build();
                    bot.execute(collectAnswer(chatId, "Что вы хотите сделать ?", kb));
                }
            }
        }

        if (textMsgList.size() >= 2 && textMsgList.get(textMsgList.size() - 2).equals(CHECK_INN.name())) {
            companyService.saveNewCompany(update);
            bot.execute(collectAnswer(chatId, "ИНН вашей компании успешно сохранён"));
        }

        switch (incomeMsg) {
            case "/start":
                InlineKeyboardMarkup kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Ввести пароль", "/checkPassword")))
                        .keyboardRow(List.of(getButton("Получить пароль", "/getPassword")))
                        .build();
                bot.execute(collectAnswer(chatId, "Вас приветствует Бот компании *Эколенд*!"));
                bot.execute(collectAnswer(chatId, "Для продолжения работы необходимо авторизоваться"));
                bot.execute(collectAnswer(chatId, "Что вы хотите сделать ?", kb)); break;
            case "/getPassword":
                InlineKeyboardMarkup kb1 = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Начать сначала", "/start"))).build();
                bot.execute(collectAnswer(chatId, "Получить пароль для входа можно у сотрудника отдела логистики компании *Эколенд*", kb1)); break;
            case "/checkPassword":
                bot.execute(collectAnswer(chatId, "Отправьте в ЧАТ ***ПАРОЛЬ***")); break;
            case "/checkINN":
                bot.execute(collectAnswer(chatId, "Отправьте в ЧАТ ***ИНН КОМПАНИИ***")); break;
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

    private SendMessage collectAnswer(Long chatId, String textToSend, ReplyKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .replyMarkup(keyboard)
                .build();
    }

    private void saveTextMsg(String textMsg) {
        if (textMsgList.size() < 5) {
            textMsgList.add(textMsg);
            return;
        }
        textMsgList.removeFirst();
        textMsgList.add(textMsg);
    }


}