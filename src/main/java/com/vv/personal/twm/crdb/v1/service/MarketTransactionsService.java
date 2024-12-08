package com.vv.personal.twm.crdb.v1.service;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-12-08
 */
public interface MarketTransactionsService extends BackUpAndRestore {
  boolean addMarketTransactions(MarketDataProto.Portfolio portfolio);

  Optional<MarketDataProto.Portfolio> getEntireMarketTransactions(String direction);
}
