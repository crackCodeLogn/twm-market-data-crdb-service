package com.vv.personal.twm.market.crdb.v1.service;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-12-21
 */
public interface MarketMetaDataService extends BackUpAndRestore {
  boolean addMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument);

  boolean addMarketMetaData(MarketDataProto.Portfolio portfolio);

  Optional<MarketDataProto.Instrument> getMarketMetaDataByTicker(String ticker);

  Optional<MarketDataProto.Portfolio> getEntireMarketMetaData();

  boolean deleteMarketMetaDataByTicker(String ticker);

  boolean upsertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument);
}
