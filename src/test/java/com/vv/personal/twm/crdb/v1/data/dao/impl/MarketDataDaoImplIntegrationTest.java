package com.vv.personal.twm.crdb.v1.data.dao.impl;

import static com.vv.personal.twm.crdb.v1.data.TestConstants.DELTA_PRECISION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.data.repository.MarketDataRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Vivek
 * @since 2024-10-30
 */
@SpringBootTest
public class MarketDataDaoImplIntegrationTest {

  @Autowired private MarketDataRepository marketDataRepository;

  private MarketDataDao marketDataDao;

  @BeforeEach
  public void setUp() {
    marketDataDao = new MarketDataDaoImpl(marketDataRepository);
  }

  @Test
  void marketDataForSingleTicker() {
    marketDataDao.deleteMarketDataByTicker("test-v2.to");

    MarketDataProto.Ticker ticker =
        MarketDataProto.Ticker.newBuilder()
            .setSymbol("test-v2.to")
            .addData(MarketDataProto.Value.newBuilder().setDate(20241030).setPrice(999.999).build())
            .addData(
                MarketDataProto.Value.newBuilder().setDate(20241031).setPrice(11.11111).build())
            .build();
    int rows = marketDataDao.insertMarketDataForSingleTicker(ticker);
    assertEquals(2, rows);

    Optional<MarketDataProto.Ticker> optionalTicker =
        marketDataDao.getMarketDataByTicker("test-v2.to");
    assertTrue(optionalTicker.isPresent());
    assertEquals("test-v2.to", optionalTicker.get().getSymbol());
    assertEquals(20241030, optionalTicker.get().getData(0).getDate());
    assertEquals(999.999, optionalTicker.get().getData(0).getPrice(), DELTA_PRECISION);
    assertEquals(20241031, optionalTicker.get().getData(1).getDate());
    assertEquals(11.11111, optionalTicker.get().getData(1).getPrice(), DELTA_PRECISION);

    rows = marketDataDao.deleteMarketDataByTicker("test-v2.to");
    assertEquals(2, rows);
  }

  @Test
  void marketData() {
    marketDataDao.deleteMarketDataByTicker("test-v2.to");
    marketDataDao.deleteMarketDataByTicker("test-v1.to");
    marketDataDao.deleteMarketDataByTicker("test-v3.to");

    MarketDataProto.Portfolio portfolio =
        MarketDataProto.Portfolio.newBuilder()
            .addInstruments(
                MarketDataProto.Instrument.newBuilder()
                    .setTicker(
                        MarketDataProto.Ticker.newBuilder()
                            .setSymbol("test-v2.to")
                            .addData(
                                MarketDataProto.Value.newBuilder()
                                    .setPrice(131.23)
                                    .setDate(20241001)
                                    .build())
                            .addData(
                                MarketDataProto.Value.newBuilder()
                                    .setPrice(132.23)
                                    .setDate(20241002)
                                    .build())
                            .build())
                    .build())
            .addInstruments(
                MarketDataProto.Instrument.newBuilder()
                    .setTicker(
                        MarketDataProto.Ticker.newBuilder()
                            .setSymbol("test-v1.to")
                            .addData(
                                MarketDataProto.Value.newBuilder()
                                    .setPrice(87.95)
                                    .setDate(20241030)
                                    .build())
                            .build())
                    .build())
            .addInstruments(
                MarketDataProto.Instrument.newBuilder()
                    .setTicker(
                        MarketDataProto.Ticker.newBuilder()
                            .setSymbol("test-v3.to")
                            .addData(
                                MarketDataProto.Value.newBuilder()
                                    .setPrice(97.95)
                                    .setDate(20241030)
                                    .build())
                            .build())
                    .build())
            .build();

    int rows = marketDataDao.insertMarketDataForPortfolio(portfolio);
    assertEquals(4, rows);

    //    will return too huge dataset, thus commenting out
    //    Optional<MarketDataProto.Portfolio> readPortfolio = marketDataDao.getEntireMarketData();
    //    assertTrue(readPortfolio.isPresent());

    assertEquals(2, marketDataDao.deleteMarketDataByTicker("test-v2.to"));
    assertEquals(1, marketDataDao.deleteMarketDataByTicker("test-v1.to"));
    assertEquals(1, marketDataDao.deleteMarketDataByTicker("test-v3.to"));
  }
}
