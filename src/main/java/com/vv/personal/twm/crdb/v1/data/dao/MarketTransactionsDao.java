package com.vv.personal.twm.crdb.v1.data.dao;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-12-08
 */
public interface MarketTransactionsDao {

  Optional<MarketDataProto.Portfolio> getMarketTransactions(String direction);

  int insertMarketTransactions(MarketDataProto.Portfolio portfolio);
}
