package com.vv.personal.twm.crdb.v1.data.dao.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.data.entity.MarketDataEntity;
import com.vv.personal.twm.crdb.v1.data.repository.MarketDataRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MarketDataDaoImpl implements MarketDataDao {
  private final MarketDataRepository marketDataRepository;

  @Override
  public Optional<MarketDataProto.Ticker> getMarketDataByTicker(String ticker) {
    try {
      List<MarketDataEntity> marketDataEntities = marketDataRepository.getAllByTicker(ticker);
      if (marketDataEntities.isEmpty()) {
        log.warn("Found no data for ticker {}", ticker);
        return Optional.empty();
      }
      System.out.println(marketDataEntities); // todo - delete later

      MarketDataProto.Ticker.Builder tickerBuilder = generateEmptyTicker(marketDataEntities.get(0));
      marketDataEntities.forEach(
          marketDataEntity -> tickerBuilder.addData(generateValue(marketDataEntity)));
      return Optional.of(sortTickerDataOnDate(tickerBuilder).build());

    } catch (Exception e) {
      log.error("Error on getting market data for ticker {}", ticker, e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketData() {
    try {
      List<MarketDataEntity> marketDataEntities = marketDataRepository.getAll();
      if (marketDataEntities.isEmpty()) {
        log.warn("Found no data!");
        return Optional.empty();
      }
      System.out.println(marketDataEntities); // todo - remove later

      Map<String, MarketDataProto.Ticker.Builder> tickerMap = new HashMap<>();
      marketDataEntities.forEach(
          marketDataEntity -> {
            tickerMap.computeIfAbsent(
                marketDataEntity.getId().getTicker(), k -> generateEmptyTicker(marketDataEntity));
            tickerMap.computeIfPresent(
                marketDataEntity.getId().getTicker(),
                (k, ticker) -> ticker.addData(generateValue(marketDataEntity)));
          });

      MarketDataProto.Portfolio.Builder portfolioBuilder = MarketDataProto.Portfolio.newBuilder();
      tickerMap
          .values()
          .forEach(
              ticker ->
                  portfolioBuilder.addInstruments(
                      MarketDataProto.Instrument.newBuilder()
                          .setTicker(sortTickerDataOnDate(ticker))
                          .build()));
      return Optional.of(portfolioBuilder.build());
    } catch (Exception e) {
      log.error("Error on getting the entire market data ", e);
    }
    return Optional.empty();
  }

  @Override
  public int insertMarketData(int date, String ticker, double price) {
    return 0;
  }

  /**
   * Sorts the entire data values for a ticker in increasing order of date
   *
   * @param ticker
   * @return
   */
  private MarketDataProto.Ticker.Builder sortTickerDataOnDate(
      MarketDataProto.Ticker.Builder ticker) {
    List<MarketDataProto.Value> values = Lists.newArrayList(ticker.getDataList()); // copy creation
    values.sort(Comparator.comparingInt(MarketDataProto.Value::getDate));
    ticker.clearData();
    ticker.addAllData(values);
    return ticker;
  }

  private MarketDataProto.Ticker.Builder generateEmptyTicker(MarketDataEntity marketDataEntity) {
    return MarketDataProto.Ticker.newBuilder().setSymbol(marketDataEntity.getId().getTicker());
  }

  private MarketDataProto.Value.Builder generateValue(MarketDataEntity marketDataEntity) {
    return MarketDataProto.Value.newBuilder()
        .setDate(marketDataEntity.getId().getDate())
        .setPrice(marketDataEntity.getPrice());
  }

  /*public boolean addBank(BankProto.Bank bank) {
      Instant currentTime = Instant.now();
      try {
          BankEntity bankEntity = generateBankEntity(bank, currentTime);
          bankRepository.saveAndFlush(bankEntity);
      } catch (Exception e) { //TODO - streamline exception later
          log.error("Error on bank save. ", e);
          return false;
      }
      return true;
  }*/

}
