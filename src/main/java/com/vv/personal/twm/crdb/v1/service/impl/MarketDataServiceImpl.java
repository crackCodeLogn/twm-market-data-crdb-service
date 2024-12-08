package com.vv.personal.twm.crdb.v1.service.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.service.MarketDataService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketDataServiceImpl implements MarketDataService {
  private final MarketDataDao marketDataDao;

  @Override
  public boolean addMarketDataForSingleTicker(MarketDataProto.Ticker ticker) {
    return marketDataDao.insertMarketDataForSingleTicker(ticker) != 0;
  }

  @Override
  public boolean addMarketData(MarketDataProto.Portfolio portfolio) {
    return marketDataDao.insertMarketDataForPortfolio(portfolio) != 0;
  }

  @Override
  public Optional<MarketDataProto.Ticker> getMarketDataByTicker(String symbol) {
    return marketDataDao.getMarketDataByTicker(symbol);
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketData() {
    return marketDataDao.getEntireMarketData();
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getLimitedDataByTicker(
      String ticker, int numberOfRecords) {
    return marketDataDao.getLimitedDataByTicker(ticker, numberOfRecords);
  }

  @Override
  public String extractDataInDelimitedFormat(String delimiter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
