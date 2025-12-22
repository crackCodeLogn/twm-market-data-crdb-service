package com.vv.personal.twm.market.crdb.v1.service.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.market.crdb.v1.data.dao.MarketMetaDataDao;
import com.vv.personal.twm.market.crdb.v1.service.MarketMetaDataService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketMetaDataServiceImpl implements MarketMetaDataService {
  private final MarketMetaDataDao marketMetaDataDao;

  @Override
  public boolean addMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument) {
    return marketMetaDataDao.insertMarketMetaDataForSingleTicker(instrument) != 0;
  }

  @Override
  public boolean addMarketMetaData(MarketDataProto.Portfolio portfolio) {
    return marketMetaDataDao.insertMarketMetaDataForPortfolio(portfolio) != 0;
  }

  @Override
  public Optional<MarketDataProto.Instrument> getMarketMetaDataByTicker(String ticker) {
    return marketMetaDataDao.getMarketMetaDataByTicker(ticker);
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketMetaData() {
    return marketMetaDataDao.getEntireMarketMetaData();
  }

  @Override
  public boolean deleteMarketMetaDataByTicker(String ticker) {
    return marketMetaDataDao.deleteMarketMetaDataByTicker(ticker) != 0;
  }

  @Override
  public boolean upsertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument) {
    return marketMetaDataDao.upsertMarketMetaDataForSingleTicker(instrument);
  }

  @Override
  public String extractDataInDelimitedFormat(String delimiter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
