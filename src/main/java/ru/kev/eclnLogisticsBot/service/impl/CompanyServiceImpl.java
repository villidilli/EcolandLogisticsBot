package ru.kev.eclnLogisticsBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kev.eclnLogisticsBot.model.Company;
import ru.kev.eclnLogisticsBot.model.User;
import ru.kev.eclnLogisticsBot.repository.CompanyRepository;
import ru.kev.eclnLogisticsBot.service.CompanyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Company saveNewCompany(Update update) {
        User user = new User();
        user.setUserId(update.getMessage().getFrom().getId());
        Company newCompany = new Company();
        newCompany.setEmployee(List.of(user));
        newCompany.setCompanyInn(Long.parseLong(update.getMessage().getText()));
        return companyRepository.save(newCompany);
    }
}