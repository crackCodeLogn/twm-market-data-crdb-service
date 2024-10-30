package com.vv.personal.twm.crdb.v1.service;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
public interface MarketData extends BackUpAndRestore {
  boolean addMarketData(MarketDataProto.Ticker ticker);

  Optional<MarketDataProto.Ticker> getMarketDataByTicker(String symbol);

  Optional<MarketDataProto.Portfolio> getEntireMarketData();
}
