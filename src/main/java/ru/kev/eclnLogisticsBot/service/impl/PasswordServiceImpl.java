package ru.kev.eclnLogisticsBot.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kev.eclnLogisticsBot.model.Password;
import ru.kev.eclnLogisticsBot.repository.PasswordRepository;
import ru.kev.eclnLogisticsBot.service.PasswordService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository passwordRepository;

    @Override
    public Password checkPassword(String password) {
        return passwordRepository.getPasswordByPassword(password);
    }
}