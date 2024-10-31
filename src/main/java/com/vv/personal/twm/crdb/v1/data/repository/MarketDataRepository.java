package com.vv.personal.twm.crdb.v1.data.repository;

import com.vv.personal.twm.crdb.v1.data.entity.CompositePrimaryKey;
import com.vv.personal.twm.crdb.v1.data.entity.MarketDataEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Repository
public interface MarketDataRepository extends JpaRepository<MarketDataEntity, CompositePrimaryKey> {

  @Query(value = "SELECT * FROM market_data WHERE ticker = :ticker", nativeQuery = true)
  List<MarketDataEntity> getAllByTicker(@Param("ticker") String ticker);

  @Modifying
  @Transactional
  @Query(
      value = "INSERT INTO market_data(date, ticker, price) VALUES(:date, :ticker, :price)",
      nativeQuery = true)
  int insertMarketData(
      @Param("date") int date, @Param("ticker") String ticker, @Param("price") double price);

  @Query(value = "SELECT * FROM market_data", nativeQuery = true)
  List<MarketDataEntity> getAll();

  @Modifying
  @Transactional
  @Query(value = "DELETE from market_data where ticker = :ticker", nativeQuery = true)
  int deleteAllByTicker(@Param("ticker") String ticker);
}
