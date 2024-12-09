package com.vv.personal.twm.crdb.v1.service.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketTransactionDao;
import com.vv.personal.twm.crdb.v1.service.MarketTransactionService;
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
public class MarketTransactionServiceImpl implements MarketTransactionService {
  private final MarketTransactionDao marketTransactionDao;

  @Override
  public boolean addMarketTransactions(MarketDataProto.Portfolio portfolio) {
    return marketTransactionDao.insertMarketTransactions(portfolio)
        == portfolio.getInstrumentsCount();
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketTransactions(String direction) {
    return marketTransactionDao.getMarketTransactions(direction);
  }

  @Override
  public String extractDataInDelimitedFormat(String delimiter) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
