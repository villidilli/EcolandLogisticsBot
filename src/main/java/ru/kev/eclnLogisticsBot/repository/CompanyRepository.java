package ru.kev.eclnLogisticsBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kev.eclnLogisticsBot.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}