package com.vv.personal.twm.crdb.v1.service;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
public interface MarketData extends BackUpAndRestore {
  boolean addMarketDataForSingleTicker(MarketDataProto.Ticker ticker);

  boolean addMarketData(MarketDataProto.Portfolio portfolio);

  Optional<MarketDataProto.Ticker> getMarketDataByTicker(String symbol);

  Optional<MarketDataProto.Portfolio> getEntireMarketData();
}
