package ru.kev.eclnLogisticsBot.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kev.eclnLogisticsBot.utils.BidStatus;

@Entity
@Table(name = "bids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long bidId;
    @Column(name = "bid_description")
    private String bidDescription;
    @Column(name = "bid_status")
    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;
}