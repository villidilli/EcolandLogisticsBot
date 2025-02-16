package ru.kev.eclnLogisticsBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kev.eclnLogisticsBot.model.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
}