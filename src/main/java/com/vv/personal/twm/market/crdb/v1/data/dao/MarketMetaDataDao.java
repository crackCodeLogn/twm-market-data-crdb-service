package com.vv.personal.twm.market.crdb.v1.data.dao;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-12-21
 */
public interface MarketMetaDataDao {

  Optional<MarketDataProto.Instrument> getMarketMetaDataByTicker(String ticker);

  Optional<MarketDataProto.Portfolio> getEntireMarketMetaData();

  int insertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument);

  int insertMarketMetaDataForPortfolio(boolean truncateFirst, MarketDataProto.Portfolio portfolio);

  int deleteMarketMetaDataByTicker(String ticker);

  boolean upsertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument);

  boolean truncateMetaData();
}
