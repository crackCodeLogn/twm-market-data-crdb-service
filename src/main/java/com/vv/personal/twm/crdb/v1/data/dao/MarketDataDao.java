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

    int insertMarketData(int date, String ticker, double price);
}
