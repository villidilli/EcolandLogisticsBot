package ru.kev.eclnLogisticsBot.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kev.eclnLogisticsBot.utils.CompanyType;

@Entity
@Table(name = "passwords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Password {
    @Id
    @Column(name = "password")
    private String password;
    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
}