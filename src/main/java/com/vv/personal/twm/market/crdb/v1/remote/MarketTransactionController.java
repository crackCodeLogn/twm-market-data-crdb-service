package com.vv.personal.twm.market.crdb.v1.remote;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.market.crdb.v1.service.MarketTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vivek
 * @since 2024-12-08
 */
@Slf4j
@RequiredArgsConstructor
@RestController("market-transactions-controller")
@RequestMapping("/crdb/mkt/transactions/")
public class MarketTransactionController {
  private final MarketTransactionService marketTransactionService;

  @PostMapping("")
  public String addMarketData(@RequestBody MarketDataProto.Portfolio portfolio) {
    log.info("Received request to add '{}' transactions into db", portfolio.getInstrumentsCount());
    try {
      boolean result = marketTransactionService.addMarketTransactions(portfolio);
      if (result) return "Done";
      return "Failed";
    } catch (Exception e) {
      log.error("Failed to write all data correctly. ", e);
    }
    return "Failed";
  }

  @GetMapping("/{direction}")
  public MarketDataProto.Portfolio getTransactions(@PathVariable String direction) {
    log.info("Received request to get market transactions for '{}'", direction);
    MarketDataProto.Portfolio portfolio =
        marketTransactionService
            .getEntireMarketTransactions(direction)
            .orElse(MarketDataProto.Portfolio.newBuilder().build());
    System.out.println(portfolio); // todo - remove later
    return portfolio;
  }

  @GetMapping("/dividends/{accountType}")
  public MarketDataProto.Portfolio getDividends(@PathVariable String accountType) {
    MarketDataProto.AccountType type = MarketDataProto.AccountType.valueOf(accountType);
    if (type == MarketDataProto.AccountType.UNRECOGNIZED) {
      log.error("Failed to find accountType '{}'", accountType);
      return MarketDataProto.Portfolio.newBuilder().build();
    }
    log.info("Received request to get dividend market transactions for '{}'", type);
    MarketDataProto.Portfolio portfolio =
        marketTransactionService
            .getDividends(type)
            .orElse(MarketDataProto.Portfolio.newBuilder().build());
    System.out.println(portfolio);
    return portfolio;
  }
}
