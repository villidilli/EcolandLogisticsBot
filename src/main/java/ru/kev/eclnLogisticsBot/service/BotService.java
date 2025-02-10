package ru.kev.eclnLogisticsBot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class BotService {
    private String name = "service";
}