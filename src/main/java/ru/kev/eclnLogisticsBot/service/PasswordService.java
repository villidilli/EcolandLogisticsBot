package ru.kev.eclnLogisticsBot.service;


import ru.kev.eclnLogisticsBot.model.Password;

public interface PasswordService {
    Password checkPassword(String password);
}