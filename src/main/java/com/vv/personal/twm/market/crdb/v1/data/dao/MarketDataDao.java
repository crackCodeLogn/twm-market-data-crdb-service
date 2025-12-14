package com.vv.personal.twm.market.crdb.v1.data.dao;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.List;
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

  int deleteMarketDataByTickerAndDate(String ticker, int date);

  int deleteMarketDataByTickerAndDates(String ticker, List<Integer> dates);

  Optional<MarketDataProto.Portfolio> getLimitedDataByTicker(String ticker, int numberOfRecords);
}
