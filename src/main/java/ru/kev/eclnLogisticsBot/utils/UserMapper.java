package ru.kev.eclnLogisticsBot.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kev.eclnLogisticsBot.model.User;

@UtilityClass
public class UserMapper {
    public User updateToUser(Update update) {
        User user = new User();
        if (update.hasMessage()) {
            user.setUserId(update.getMessage().getFrom().getId());
            user.setFirstName(update.getMessage().getFrom().getFirstName());
            user.setLastName(update.getMessage().getFrom().getLastName());
            user.setUserName(update.getMessage().getFrom().getUserName());
            return user;
        }
        user.setUserId(update.getCallbackQuery().getFrom().getId());
        user.setFirstName(update.getCallbackQuery().getFrom().getFirstName());
        user.setLastName(update.getCallbackQuery().getFrom().getLastName());
        user.setUserName(update.getCallbackQuery().getFrom().getUserName());
        return user;
    }
}