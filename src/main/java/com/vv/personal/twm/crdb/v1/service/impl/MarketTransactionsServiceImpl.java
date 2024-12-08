package com.vv.personal.twm.crdb.v1.service.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketTransactionsDao;
import com.vv.personal.twm.crdb.v1.service.MarketTransactionsService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2024-12-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketTransactionsServiceImpl implements MarketTransactionsService {
  private final MarketTransactionsDao marketTransactionsDao;

  @Override
  public boolean addMarketTransactions(MarketDataProto.Portfolio portfolio) {
    return marketTransactionsDao.insertMarketTransactions(portfolio)
        == portfolio.getInstrumentsCount();
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketTransactions(String direction) {
    return marketTransactionsDao.getMarketTransactions(direction);
  }

  @Override
  public String extractDataInDelimitedFormat(String delimiter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
