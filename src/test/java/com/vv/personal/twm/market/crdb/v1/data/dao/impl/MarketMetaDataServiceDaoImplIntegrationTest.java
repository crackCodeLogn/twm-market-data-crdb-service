package com.vv.personal.twm.market.crdb.v1.data.dao.impl;

import static com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto.Signal.SIG_BUY;
import static com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto.Signal.SIG_HOLD;
import static com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto.Signal.SIG_STRONG_BUY;
import static com.vv.personal.twm.market.crdb.v1.data.TestConstants.DELTA_PRECISION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.market.crdb.v1.data.dao.MarketMetaDataDao;
import com.vv.personal.twm.market.crdb.v1.data.repository.MarketMetaDataRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@SpringBootTest
public class MarketMetaDataServiceDaoImplIntegrationTest {

  @Autowired private MarketMetaDataRepository marketMetaDataRepository;

  private MarketMetaDataDao marketMetaDataDao;

  @BeforeEach
  public void setUp() {
    marketMetaDataDao = new MarketMetaDataDaoImpl(marketMetaDataRepository);
  }

  @Test
  void marketMetaDataForSingleTicker() {
    marketMetaDataDao.deleteMarketMetaDataByTicker("test-v2.to");

    MarketDataProto.Instrument instrument =
        MarketDataProto.Instrument.newBuilder()
            .setTicker(
                MarketDataProto.Ticker.newBuilder()
                    .setSymbol("test-v2.to")
                    .addData(MarketDataProto.Value.newBuilder().setDate(20251221).build())
                    .build())
            .setSignal(SIG_BUY)
            .setDividendYield(3.45)
            .setMer(0.09)
            .setNotes("test etf")
            .setIssueCountry(MarketDataProto.Country.CA)
            .setOriginCountry(MarketDataProto.Country.US)
            .setCcy(MarketDataProto.CurrencyCode.INR)
            .build();

    int rows = marketMetaDataDao.insertMarketMetaDataForSingleTicker(instrument);
    assertEquals(1, rows);

    Optional<MarketDataProto.Instrument> marketMetaDataByTicker =
        marketMetaDataDao.getMarketMetaDataByTicker("test-v2.to");
    assertTrue(marketMetaDataByTicker.isPresent());
    System.out.println(marketMetaDataByTicker.get());
    assertEquals("test-v2.to", marketMetaDataByTicker.get().getTicker().getSymbol());
    assertEquals(20251221, marketMetaDataByTicker.get().getTicker().getData(0).getDate());
    assertEquals(SIG_BUY, marketMetaDataByTicker.get().getSignal());
    assertEquals(3.45, marketMetaDataByTicker.get().getDividendYield(), DELTA_PRECISION);
    assertEquals(0.09, marketMetaDataByTicker.get().getMer(), DELTA_PRECISION);
    assertEquals(MarketDataProto.Country.CA, marketMetaDataByTicker.get().getIssueCountry());
    assertEquals(MarketDataProto.Country.US, marketMetaDataByTicker.get().getOriginCountry());
    assertEquals(MarketDataProto.CurrencyCode.INR, marketMetaDataByTicker.get().getCcy());

    // upsert test
    instrument =
        MarketDataProto.Instrument.newBuilder()
            .setTicker(
                MarketDataProto.Ticker.newBuilder()
                    .setSymbol("test-v2.to")
                    .addData(MarketDataProto.Value.newBuilder().setDate(20251221).build())
                    .build())
            .setSignal(SIG_STRONG_BUY)
            .setDividendYield(3.45)
            .setMer(0.12)
            .setNotes("test etf - upserted")
            .setIssueCountry(MarketDataProto.Country.CA)
            .setOriginCountry(MarketDataProto.Country.US)
            .setCcy(MarketDataProto.CurrencyCode.INR)
            .build();
    boolean upsertResult = marketMetaDataDao.upsertMarketMetaDataForSingleTicker(instrument);
    assertTrue(upsertResult);

    marketMetaDataByTicker = marketMetaDataDao.getMarketMetaDataByTicker("test-v2.to");
    assertTrue(marketMetaDataByTicker.isPresent());
    assertEquals(SIG_STRONG_BUY, marketMetaDataByTicker.get().getSignal());
    assertEquals(0.12, marketMetaDataByTicker.get().getMer(), DELTA_PRECISION);
    assertEquals("test etf - upserted", marketMetaDataByTicker.get().getNotes());

    int deletedRows = marketMetaDataDao.deleteMarketMetaDataByTicker("test-v2.to");
    assertEquals(1, deletedRows);

    marketMetaDataByTicker = marketMetaDataDao.getMarketMetaDataByTicker("test-v2.to");
    assertFalse(marketMetaDataByTicker.isPresent());
  }

  @Test
  void marketMetaDataForPortfolio() {
    marketMetaDataDao.deleteMarketMetaDataByTicker("test-v3.to");
    marketMetaDataDao.deleteMarketMetaDataByTicker("test-v1.to");

    MarketDataProto.Portfolio portfolio =
        MarketDataProto.Portfolio.newBuilder()
            .addInstruments(
                MarketDataProto.Instrument.newBuilder()
                    .setTicker(
                        MarketDataProto.Ticker.newBuilder()
                            .setSymbol("test-v1.to")
                            .addData(MarketDataProto.Value.newBuilder().setDate(20251220).build())
                            .build())
                    .setSignal(SIG_HOLD)
                    .setDividendYield(0.99)
                    .setNotes("test stk imnt")
                    .setIssueCountry(MarketDataProto.Country.CA)
                    .setOriginCountry(MarketDataProto.Country.CA)
                    .setCcy(MarketDataProto.CurrencyCode.CAD)
                    .build())
            .addInstruments(
                MarketDataProto.Instrument.newBuilder()
                    .setTicker(
                        MarketDataProto.Ticker.newBuilder()
                            .setSymbol("test-v3.to")
                            .addData(MarketDataProto.Value.newBuilder().setDate(20251221).build())
                            .build())
                    .setSignal(SIG_BUY)
                    .setDividendYield(3.45)
                    .setMer(0.09)
                    .setNotes("test etf")
                    .setIssueCountry(MarketDataProto.Country.CA)
                    .setOriginCountry(MarketDataProto.Country.US)
                    .setCcy(MarketDataProto.CurrencyCode.INR)
                    .build())
            .build();

    int rows = marketMetaDataDao.insertMarketMetaDataForPortfolio(portfolio);
    assertEquals(2, rows);

    Optional<MarketDataProto.Instrument> marketMetaDataByTicker =
        marketMetaDataDao.getMarketMetaDataByTicker("test-v3.to");
    assertTrue(marketMetaDataByTicker.isPresent());
    System.out.println(marketMetaDataByTicker.get());
    assertEquals("test-v3.to", marketMetaDataByTicker.get().getTicker().getSymbol());
    assertEquals(20251221, marketMetaDataByTicker.get().getTicker().getData(0).getDate());
    assertEquals(SIG_BUY, marketMetaDataByTicker.get().getSignal());
    assertEquals(3.45, marketMetaDataByTicker.get().getDividendYield(), DELTA_PRECISION);
    assertEquals(0.09, marketMetaDataByTicker.get().getMer(), DELTA_PRECISION);
    assertEquals(MarketDataProto.Country.CA, marketMetaDataByTicker.get().getIssueCountry());
    assertEquals(MarketDataProto.Country.US, marketMetaDataByTicker.get().getOriginCountry());
    assertEquals(MarketDataProto.CurrencyCode.INR, marketMetaDataByTicker.get().getCcy());

    marketMetaDataByTicker = marketMetaDataDao.getMarketMetaDataByTicker("test-v1.to");
    assertTrue(marketMetaDataByTicker.isPresent());
    System.out.println(marketMetaDataByTicker.get());
    assertEquals("test-v1.to", marketMetaDataByTicker.get().getTicker().getSymbol());
    assertEquals(20251220, marketMetaDataByTicker.get().getTicker().getData(0).getDate());
    assertEquals(SIG_HOLD, marketMetaDataByTicker.get().getSignal());
    assertEquals(0.99, marketMetaDataByTicker.get().getDividendYield(), DELTA_PRECISION);
    assertEquals(MarketDataProto.Country.CA, marketMetaDataByTicker.get().getIssueCountry());
    assertEquals(MarketDataProto.Country.CA, marketMetaDataByTicker.get().getOriginCountry());
    assertEquals(MarketDataProto.CurrencyCode.CAD, marketMetaDataByTicker.get().getCcy());

    int deletedRows = marketMetaDataDao.deleteMarketMetaDataByTicker("test-v1.to");
    assertEquals(1, deletedRows);
    deletedRows = marketMetaDataDao.deleteMarketMetaDataByTicker("test-v3.to");
    assertEquals(1, deletedRows);

    marketMetaDataByTicker = marketMetaDataDao.getMarketMetaDataByTicker("test-v1.to");
    assertFalse(marketMetaDataByTicker.isPresent());
    marketMetaDataByTicker = marketMetaDataDao.getMarketMetaDataByTicker("test-v3.to");
    assertFalse(marketMetaDataByTicker.isPresent());
  }
}
