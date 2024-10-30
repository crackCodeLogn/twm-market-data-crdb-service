package com.vv.personal.twm.crdb.v1.data.dao.impl;

import static com.vv.personal.twm.crdb.v1.data.TestConstants.DELTA_PRECISION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.data.entity.CompositePrimaryKey;
import com.vv.personal.twm.crdb.v1.data.entity.MarketDataEntity;
import com.vv.personal.twm.crdb.v1.data.repository.MarketDataRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Vivek
 * @since 2024-10-29
 */
@ExtendWith(MockitoExtension.class)
class MarketDataDaoImplTest {

  @Mock MarketDataRepository marketDataRepository;

  private MarketDataDao marketDataDao;

  @BeforeEach
  public void setUp() {
    marketDataDao = new MarketDataDaoImpl(marketDataRepository);
  }

  @Test
  public void testGetMarketDataByTicker() {
    List<MarketDataEntity> entities =
        Lists.newArrayList(
            generateMarketDataEntity("cm.to", 20241029, 87.95),
            generateMarketDataEntity("vfv.to", 20241002, 132.23),
            generateMarketDataEntity("vfv.to", 20241001, 131.23));
    when(marketDataRepository.getAllByTicker("cm.to")).thenReturn(entities.subList(0, 1));
    when(marketDataRepository.getAllByTicker("vfv.to")).thenReturn(entities.subList(1, 3));

    Optional<MarketDataProto.Ticker> optionalTicker = marketDataDao.getMarketDataByTicker("cm.to");
    System.out.println(optionalTicker);

    assertTrue(optionalTicker.isPresent());
    MarketDataProto.Ticker ticker = optionalTicker.get();
    assertEquals("cm.to", ticker.getSymbol());
    assertEquals(1, ticker.getDataCount());
    assertEquals(20241029, ticker.getData(0).getDate());
    assertEquals(87.95, ticker.getData(0).getPrice(), DELTA_PRECISION);

    optionalTicker = marketDataDao.getMarketDataByTicker("vfv.to");
    System.out.println(optionalTicker);

    assertTrue(optionalTicker.isPresent());
    ticker = optionalTicker.get();
    assertEquals("vfv.to", ticker.getSymbol());
    assertEquals(2, ticker.getDataCount());
    assertEquals(20241001, ticker.getData(0).getDate()); // also tests the sorting
    assertEquals(131.23, ticker.getData(0).getPrice(), DELTA_PRECISION);
    assertEquals(20241002, ticker.getData(1).getDate()); // also tests the sorting
    assertEquals(132.23, ticker.getData(1).getPrice(), DELTA_PRECISION);
  }

  @Test
  public void testGetEntireMarketData() {
    List<MarketDataEntity> entities =
        Lists.newArrayList(
            generateMarketDataEntity("cm.to", 20241029, 87.95),
            generateMarketDataEntity("vfv.to", 20241002, 132.23),
            generateMarketDataEntity("vfv.to", 20241001, 131.23));
    when(marketDataRepository.getAll()).thenReturn(entities);

    Optional<MarketDataProto.Portfolio> optionalPortfolio = marketDataDao.getEntireMarketData();
    System.out.println(optionalPortfolio);
    assertTrue(optionalPortfolio.isPresent());
    MarketDataProto.Portfolio portfolio = optionalPortfolio.get();
    assertEquals(2, portfolio.getInstrumentsCount());

    MarketDataProto.Instrument instrument = portfolio.getInstruments(0);
    MarketDataProto.Instrument instrument2 = portfolio.getInstruments(1);
    if (instrument2.getTicker().getSymbol().equals("cm.to")) {
      MarketDataProto.Instrument tmp = instrument;
      instrument = instrument2;
      instrument2 = tmp;
    }

    assertEquals("cm.to", instrument.getTicker().getSymbol());
    assertEquals(87.95, instrument.getTicker().getData(0).getPrice(), DELTA_PRECISION);
    assertEquals("vfv.to", instrument2.getTicker().getSymbol());
    assertEquals(20241001, instrument2.getTicker().getData(0).getDate()); // also tests the sorting
    assertEquals(131.23, instrument2.getTicker().getData(0).getPrice(), DELTA_PRECISION);
    assertEquals(20241002, instrument2.getTicker().getData(1).getDate()); // also tests the sorting
    assertEquals(132.23, instrument2.getTicker().getData(1).getPrice(), DELTA_PRECISION);
  }

  @Test
  public void testInsertMarketDataForSingleTicker() {
    MarketDataProto.Ticker ticker =
        MarketDataProto.Ticker.newBuilder()
            .setSymbol("vfv.to")
            .addData(MarketDataProto.Value.newBuilder().setPrice(131.23).setDate(20241001).build())
            .addData(MarketDataProto.Value.newBuilder().setPrice(132.23).setDate(20241002).build())
            .build();
    List<MarketDataEntity> entities =
        Lists.newArrayList(
            generateMarketDataEntity("vfv.to", 20241001, 131.23),
            generateMarketDataEntity("vfv.to", 20241002, 132.23));

    int result = marketDataDao.insertMarketDataForSingleTicker(ticker);
    assertEquals(0, result); // cause it's a mock invocation
    verify(marketDataRepository).saveAll(entities);

    result =
        marketDataDao.insertMarketDataForSingleTicker(MarketDataProto.Ticker.newBuilder().build());
    assertEquals(-1, result);

    result =
        marketDataDao.insertMarketDataForSingleTicker(
            MarketDataProto.Ticker.newBuilder().setSymbol("vfv.to").build());
    assertEquals(-1, result);
  }

  private MarketDataEntity generateMarketDataEntity(String ticker, int date, double price) {
    return MarketDataEntity.builder()
        .price(price)
        .id(CompositePrimaryKey.builder().date(date).ticker(ticker).build())
        .build();
  }
}
