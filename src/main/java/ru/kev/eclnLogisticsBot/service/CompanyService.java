package ru.kev.eclnLogisticsBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kev.eclnLogisticsBot.model.Company;
import ru.kev.eclnLogisticsBot.repository.CompanyRepository;

public interface CompanyService {
    Company saveNewCompany(Update update);
}