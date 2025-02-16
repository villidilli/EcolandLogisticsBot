package ru.kev.eclnLogisticsBot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.kev.eclnLogisticsBot.model.User;
import ru.kev.eclnLogisticsBot.repository.UserRepository;
import ru.kev.eclnLogisticsBot.service.UserService;
import ru.kev.eclnLogisticsBot.utils.UserMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveNewUser(Update update) {
        User newUser = UserMapper.updateToUser(update);
        User savedUser = userRepository.save(newUser);
        System.out.println(savedUser.toString());
    }
}