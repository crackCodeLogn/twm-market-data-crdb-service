package com.vv.personal.twm.crdb.v1.remote;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Slf4j
@RequiredArgsConstructor
@RestController("market-data-controller")
@RequestMapping("/crdb/mkt/")
public class MarketDataController {
  private final MarketDataService marketDataService;

  @PostMapping("/data-single-ticker/")
  public String addMarketDataForSingleTicker(@RequestBody MarketDataProto.Ticker ticker) {
    log.info("Received request to add '{}' ticker into db", ticker);
    try {
      boolean result = marketDataService.addMarketDataForSingleTicker(ticker);
      if (result) return "Done";
      return "Failed";
    } catch (Exception e) {
      log.error("Failed to write all data correctly. ", e);
    }
    return "Failed";
  }

  @PostMapping("/data/")
  public String addMarketData(@RequestBody MarketDataProto.Portfolio portfolio) {
    log.info("Received request to add '{}' portfolio into db", portfolio);
    try {
      boolean result = marketDataService.addMarketData(portfolio);
      if (result) return "Done";
      return "Failed";
    } catch (Exception e) {
      log.error("Failed to write all data correctly. ", e);
    }
    return "Failed";
  }

  @GetMapping("/data/{ticker}")
  public MarketDataProto.Ticker getMarketDataByTicker(@PathVariable String ticker) {
    log.info("Received request to get market data for ticker '{}'", ticker);
    MarketDataProto.Ticker t =
        marketDataService
            .getMarketDataByTicker(ticker)
            .orElse(MarketDataProto.Ticker.newBuilder().build());
    System.out.println(t); // todo - remove later
    return t;
  }

  @GetMapping("/data/")
  public MarketDataProto.Portfolio getEntireMarketData() {
    log.info("Received request to get the entire market data");
    MarketDataProto.Portfolio portfolio =
        marketDataService
            .getEntireMarketData()
            .orElse(MarketDataProto.Portfolio.newBuilder().build());
    System.out.println(portfolio); // todo - remove later
    return portfolio;
  }

  @GetMapping("/data/{ticker}/{limit}")
  public MarketDataProto.Portfolio getLimitedDataByTicker(
      @PathVariable String ticker, @PathVariable int limit) {
    log.info("Received request to limit get {} records of market data of {}", limit, ticker);
    MarketDataProto.Portfolio portfolio =
        marketDataService
            .getLimitedDataByTicker(ticker, limit)
            .orElse(MarketDataProto.Portfolio.newBuilder().build());
    System.out.println(portfolio); // todo - remove later
    return portfolio;
  }
}
