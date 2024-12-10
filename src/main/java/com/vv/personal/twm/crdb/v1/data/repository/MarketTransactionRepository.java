package com.vv.personal.twm.crdb.v1.data.repository;

import com.vv.personal.twm.crdb.v1.data.entity.MarketTransactionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Vivek
 * @since 2024-12-08
 */
@Repository
public interface MarketTransactionRepository
    extends JpaRepository<MarketTransactionEntity, String> {

  @Query(
      value = "SELECT * FROM market_transaction WHERE direction = :direction",
      nativeQuery = true)
  List<MarketTransactionEntity> getAllByDirection(@Param("direction") String direction);
}
