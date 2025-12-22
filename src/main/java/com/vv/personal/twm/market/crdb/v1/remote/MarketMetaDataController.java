package com.vv.personal.twm.market.crdb.v1.remote;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.market.crdb.v1.service.MarketMetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@Slf4j
@RequiredArgsConstructor
@RestController("market-metadata-controller")
@RequestMapping("/crdb/mkt/metadata")
public class MarketMetaDataController {
  private final MarketMetaDataService marketMetaDataService;

  @PostMapping("/data-single-imnt/")
  public String addMarketMetaDataForSingleInstrument(
      @RequestBody MarketDataProto.Instrument instrument) {
    log.info("Received request to add '{}' instrument metadata into db", instrument);
    try {
      boolean result = marketMetaDataService.addMarketMetaDataForSingleTicker(instrument);
      return result ? "Done" : "Failed";
    } catch (Exception e) {
      log.error("Failed to write all data correctly. ", e);
    }
    return "Failed";
  }

  @PostMapping("/data/")
  public String addMarketMetaDataForPortfolio(@RequestBody MarketDataProto.Portfolio portfolio) {
    log.info("Received request to add '{}' metadata portfolio into db", portfolio);
    try {
      boolean result = marketMetaDataService.addMarketMetaData(portfolio);
      return result ? "Done" : "Failed";
    } catch (Exception e) {
      log.error("Failed to write all data correctly. ", e);
    }
    return "Failed";
  }

  @GetMapping("/data/{ticker}")
  public MarketDataProto.Instrument getMarketMetaDataByTicker(@PathVariable String ticker) {
    log.info("Received request to get market metadata for ticker '{}'", ticker);
    MarketDataProto.Instrument t =
        marketMetaDataService
            .getMarketMetaDataByTicker(ticker)
            .orElse(MarketDataProto.Instrument.newBuilder().build());
    System.out.println(t); // todo - remove later
    return t;
  }

  @GetMapping("/data/")
  public MarketDataProto.Portfolio getEntireMarketMetaData() {
    log.info("Received request to get the entire market metadata");
    MarketDataProto.Portfolio portfolio =
        marketMetaDataService
            .getEntireMarketMetaData()
            .orElse(MarketDataProto.Portfolio.newBuilder().build());
    System.out.println(portfolio); // todo - remove later
    return portfolio;
  }

  @DeleteMapping("/data/{ticker}")
  public String deleteMarketMetaDataByTicker(@PathVariable String ticker) {
    log.info("Received request to delete market metadata for ticker '{}'", ticker);
    try {
      boolean result = marketMetaDataService.deleteMarketMetaDataByTicker(ticker);
      return result ? "Done" : "Failed";
    } catch (Exception e) {
      log.error("Failed to delete ticker data {} correctly. ", ticker, e);
    }
    return "Failed";
  }

  @PostMapping("/data/{ticker}/upsert")
  public String upsertMarketMetaDataForSingleTicker(
      @PathVariable String ticker, @RequestBody MarketDataProto.Instrument instrument) {
    log.info("Received request to upsert market metadata for ticker '{}'", ticker);
    try {
      boolean result = marketMetaDataService.upsertMarketMetaDataForSingleTicker(instrument);
      log.info("Upsert of {} happened", ticker);
      return result ? "Done" : "Failed";
    } catch (Exception e) {
      log.error("Failed to upsert imnt data {} dates correctly. ", ticker, e);
    }
    return "Failed";
  }
}
