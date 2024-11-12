package com.vv.personal.twm.crdb.v1.data.dao;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
public interface MarketDataDao {

  Optional<MarketDataProto.Ticker> getMarketDataByTicker(String ticker);

  Optional<MarketDataProto.Portfolio> getEntireMarketData();

  int insertMarketDataForSingleTicker(MarketDataProto.Ticker ticker);

  int insertMarketDataForPortfolio(MarketDataProto.Portfolio portfolio);

  int deleteMarketDataByTicker(String ticker);

  Optional<MarketDataProto.Portfolio> getLimitedDataByTicker(String ticker, int numberOfRecords);
}
