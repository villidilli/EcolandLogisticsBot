package ru.kev.eclnLogisticsBot.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.kev.eclnLogisticsBot.controller.Bot;

import java.util.LinkedList;
import java.util.List;

@Service
public class BotService {
    LinkedList<Update> updatesList = new LinkedList<>();
    LinkedList<SendMessage> sendMessagesList = new LinkedList<>();
    LinkedList<Message> messagesList = new LinkedList<>();
    LinkedList<String> textsList = new LinkedList<>();
    LinkedList<String> queriesList = new LinkedList<>();
    Message prevSentMsg = null;
    Message curSentMsg = null;
    Update prevUpd = null;
    Update curUpd = null;
    String prevText = null;
    String curText = null;
    String prevQuery = null;
    String curQuery = null;
    Long chatId = null;
    InlineKeyboardMarkup kb = null;

    @SneakyThrows
    private void deleteLastMessage(Bot bot, Update update) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(update.getMessage().getChatId())
                .messageId(update.getMessage().getMessageId()).build();
        bot.execute(deleteMessage);
    }

    @SneakyThrows
    private void deleteLastMessage(Bot bot, Message message) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(prevSentMsg.getChatId())
                .messageId(prevSentMsg.getMessageId()).build();
        bot.execute(deleteMessage);
    }

    @SneakyThrows
    private void deleteChat(Bot bot, LinkedList<Update> updatesList, LinkedList<Message> messagesList) {
        for (int i = updatesList.size() - 2; i >= 0; i--) {
            Update update = updatesList.get(i);
            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .messageId(update.getMessage().getMessageId()).build();
            bot.execute(deleteMessage);
        }

        for (int i = messagesList.size() - 2; i >= 0 ; i--) {
            Message message = messagesList.get(i);
            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .chatId(message.getChatId())
                    .messageId(message.getMessageId()).build();
            bot.execute(deleteMessage);
        }
    }


    @SneakyThrows
    public void getAnswer(Bot bot, Update update) {

        if(update.hasMessage()) {
            updatesList.add(update);
            textsList.add(update.getMessage().getText());
            sendMessagesList.add(doProcess(bot, update.getMessage().getChatId(), textsList.getLast()));
            messagesList.add(bot.execute(sendMessagesList.getLast()));

        }

//        if(curUpd.hasCallbackQuery()) {
//            prevQuery = curQuery;
//            curQuery = update.getCallbackQuery().getData();
//            curSentMsg = bot.execute(doProcess(bot, update.getCallbackQuery().getMessage().getChatId(), curQuery));
//            prevSentMsg = curSentMsg;
//            deleteLastMessage(bot, update);
//        }
    }

    @SneakyThrows
    private SendMessage doProcess(Bot bot, Long chatId, String text) {
//        SendMessage msgToSend = null;
        switch (text) {
            case "/start":
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Сотрудник *Эколенд*", "/roleEcln")))
                        .keyboardRow(List.of(getButton("Транспортная компания", "/roleTransport")))
                        .build();
                return collectAnswer(chatId, "Приветствую Вас!\nНеобходимо авторизоваться\nВыберите тип аккаунта:", kb);

            case "/goPrev":
                curSentMsg = prevSentMsg;

            case "/roleEcln":
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Проверить", "/acceptPassword")))
                        .keyboardRow(List.of(getButton("Назад", "/goPrev"), getButton("Меню", "/goMenu")))
                        .build();
                deleteLastMessage(bot, prevSentMsg);
                return collectAnswer(chatId,"Отправьте в чат *пароль* и нажмите *Проверить*", kb);
//            case "/roleTransport":
//                kb = InlineKeyboardMarkup.builder()
//                        .keyboardRow(List.of(getButton("Проверить", "/acceptINN")))
//                        .keyboardRow(List.of(getButton("Назад", "/goPrev"), getButton("Меню", "/goMenu")))
//                        .build();
//                bot.execute(collectAnswer(chatId, "Отправьте в чат *ИНН компании* и нажмите *Проверить*:", kb));
//                deleteLastMessage(bot, prevSentMsg);
//                break;
//            case "/acceptPassword": // TODO
//                userService.saveUser()
//                bot.execute(collectAnswer(chatId, "Вы успешно авторизованы, как сотрудник *Эколенд*\nВыберите")); break;
//            case "/acceptINN": // TODO
//                bot.execute(collectAnswer(chatId, "Вы ввели ИНН: " + curText)); break;
            default:
                kb = InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(getButton("Назад", "/goPrev"), getButton("Меню", "/goMenu")))
                        .build();
                bot.execute(collectAnswer(chatId, "***** Ошибка ввода ******", kb));
                deleteLastMessage(bot, prevSentMsg);
        }
        return null;
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