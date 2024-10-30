package com.vv.personal.twm.crdb.v1.service.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.service.MarketData;
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
public class MarketDataImpl implements MarketData {
  private final MarketDataDao marketDataDao;

  @Override
  public boolean addMarketData(MarketDataProto.Ticker ticker) {
    throw new UnsupportedOperationException("Not supported yet.");
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
  public String extractDataInDelimitedFormat(String delimiter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
