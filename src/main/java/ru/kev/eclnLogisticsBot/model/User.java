package ru.kev.eclnLogisticsBot.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kev.eclnLogisticsBot.utils.CompanyType;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private CompanyType userRole;
}