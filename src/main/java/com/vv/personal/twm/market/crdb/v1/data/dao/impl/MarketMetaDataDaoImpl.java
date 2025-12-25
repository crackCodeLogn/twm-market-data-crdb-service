package com.vv.personal.twm.market.crdb.v1.data.dao.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.market.crdb.v1.data.dao.MarketMetaDataDao;
import com.vv.personal.twm.market.crdb.v1.data.entity.MarketMetaDataEntity;
import com.vv.personal.twm.market.crdb.v1.data.repository.MarketMetaDataRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MarketMetaDataDaoImpl implements MarketMetaDataDao {
  private final MarketMetaDataRepository marketMetaDataRepository;

  @Override
  public Optional<MarketDataProto.Instrument> getMarketMetaDataByTicker(String ticker) {
    Optional<MarketMetaDataEntity> optionalMarketMetaDataEntity =
        marketMetaDataRepository.findById(ticker);
    if (optionalMarketMetaDataEntity.isEmpty()) return Optional.empty();

    return generateInstrument(optionalMarketMetaDataEntity.get());
  }

  @Override
  public Optional<MarketDataProto.Portfolio> getEntireMarketMetaData() {
    List<MarketMetaDataEntity> allMarketMetaDataEntities = marketMetaDataRepository.findAll();

    List<MarketDataProto.Instrument> instruments =
        allMarketMetaDataEntities.stream()
            .map(this::generateInstrument)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    if (instruments.isEmpty()) return Optional.empty();
    return Optional.of(
        MarketDataProto.Portfolio.newBuilder().addAllInstruments(instruments).build());
  }

  @Override
  public int insertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument) {
    Optional<MarketMetaDataEntity> optionalMarketMetaDataEntity =
        generateMarketMetaDataEntity(instrument);
    if (optionalMarketMetaDataEntity.isEmpty()) return 0;

    try {
      marketMetaDataRepository.save(optionalMarketMetaDataEntity.get());
      return 1;
    } catch (Exception e) {
      log.error("Failed to save market meta data for instrument {}", instrument, e);
    }
    return 0;
  }

  @Override
  public int insertMarketMetaDataForPortfolio(
      boolean truncateFirst, MarketDataProto.Portfolio portfolio) {
    List<MarketMetaDataEntity> marketMetaDataEntities =
        portfolio.getInstrumentsList().stream()
            .map(this::generateMarketMetaDataEntity)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    if (marketMetaDataEntities.isEmpty()) return 0;

    if (truncateFirst) {
      log.warn("Truncating first the entire market metadata");
      truncateMetaData();
    }
    return marketMetaDataRepository.saveAll(marketMetaDataEntities).size();
  }

  @Override
  public int deleteMarketMetaDataByTicker(String ticker) {
    try {
      marketMetaDataRepository.deleteById(ticker);
      return 1;
    } catch (Exception e) {
      log.error("Failed to delete market meta data for {}", ticker, e);
    }
    return 0;
  }

  @Override
  public boolean upsertMarketMetaDataForSingleTicker(MarketDataProto.Instrument instrument) {
    try {
      String imnt = instrument.getTicker().getSymbol();
      if (marketMetaDataRepository.existsById(imnt)) {
        deleteMarketMetaDataByTicker(imnt);
      }
      insertMarketMetaDataForSingleTicker(instrument);
      return true;
    } catch (Exception e) {
      log.error("Failed to save market meta data for instrument {}", instrument, e);
    }
    return false;
  }

  @Override
  public boolean truncateMetaData() {
    try {
      marketMetaDataRepository.truncate();
      return true;
    } catch (Exception e) {
      log.error("Failed to truncate market meta data", e);
    }
    return false;
  }

  private Optional<MarketDataProto.Instrument> generateInstrument(
      MarketMetaDataEntity marketMetaDataEntity) {
    String imnt = marketMetaDataEntity.getTicker();
    String metadata = marketMetaDataEntity.getMetadata();
    int date = marketMetaDataEntity.getDate();

    try {
      MarketDataProto.Instrument.Builder builder = MarketDataProto.Instrument.newBuilder();
      builder.mergeFrom(Base64.getDecoder().decode(metadata));
      MarketDataProto.Ticker.Builder readBackTickerBuilder = builder.getTickerBuilder();
      readBackTickerBuilder.clearData();
      readBackTickerBuilder.addData(MarketDataProto.Value.newBuilder().setDate(date).build());

      return Optional.of(builder.build());
    } catch (InvalidProtocolBufferException e) {
      log.error("Failed to parse imnt from metadata for {}", imnt, e);
    }
    return Optional.empty();
  }

  private Optional<MarketMetaDataEntity> generateMarketMetaDataEntity(
      MarketDataProto.Instrument instrument) {
    try {
      String ticker = instrument.getTicker().getSymbol();
      int date = instrument.getTicker().getDataList().get(0).getDate();
      MarketDataProto.Instrument.Builder builder =
          MarketDataProto.Instrument.newBuilder().mergeFrom(instrument);
      builder.getTickerBuilder().clearData();
      String metadata = Base64.getEncoder().encodeToString(builder.build().toByteArray());

      return Optional.of(
          MarketMetaDataEntity.builder().ticker(ticker).metadata(metadata).date(date).build());
    } catch (Exception e) {
      log.error("Failed to parse ticker from instrument {}", instrument, e);
    }
    return Optional.empty();
  }
}
