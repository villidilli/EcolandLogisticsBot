package ru.kev.eclnLogisticsBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kev.eclnLogisticsBot.model.Password;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password getPasswordByPassword(String password);
}