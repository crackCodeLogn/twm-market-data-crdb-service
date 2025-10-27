package com.vv.personal.twm.market.crdb.v1.service;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
public interface MarketDataService extends BackUpAndRestore {
  boolean addMarketDataForSingleTicker(MarketDataProto.Ticker ticker);

  boolean addMarketData(MarketDataProto.Portfolio portfolio);

  Optional<MarketDataProto.Ticker> getMarketDataByTicker(String symbol);

  Optional<MarketDataProto.Portfolio> getEntireMarketData();

  Optional<MarketDataProto.Portfolio> getLimitedDataByTicker(String ticker, int numberOfRecords);

  boolean deleteMarketDataByTickerAndDate(String ticker, int date);
}
