package com.vv.personal.twm.crdb.v1.data.dao.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketTransactionsDao;
import com.vv.personal.twm.crdb.v1.data.entity.MarketTransactionEntity;
import com.vv.personal.twm.crdb.v1.data.repository.MarketTransactionsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Vivek
 * @since 2024-12-08
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MarketTransactionsDaoImpl implements MarketTransactionsDao {
  private final MarketTransactionsRepository marketTransactionsRepository;

  @Override
  public Optional<MarketDataProto.Portfolio> getMarketTransactions(String direction) {
    List<MarketDataProto.Instrument> instruments =
        marketTransactionsRepository.getAllByDirection(direction).stream()
            .map(this::generateMarketTransaction)
            .toList();

    return Optional.of(
        MarketDataProto.Portfolio.newBuilder().addAllInstruments(instruments).build());
  }

  @Override
  public int insertMarketTransactions(MarketDataProto.Portfolio portfolio) {
    List<MarketTransactionEntity> marketTransactionEntities =
        portfolio.getInstrumentsList().stream().map(this::generateMarketTransaction).toList();
    return marketTransactionsRepository.saveAll(marketTransactionEntities).size();
  }

  private MarketDataProto.Instrument generateMarketTransaction(MarketTransactionEntity entity) {
    return MarketDataProto.Instrument.newBuilder()
        .setTicker(
            MarketDataProto.Ticker.newBuilder()
                .setSymbol(entity.getTicker())
                .setName(entity.getTickerName())
                .setSector(entity.getSector())
                .setType(MarketDataProto.InstrumentType.valueOf(entity.getInstrumentType()))
                .addData(
                    MarketDataProto.Value.newBuilder()
                        .setPrice(entity.getPrice())
                        .setDate(entity.getTradeDate())
                        .build())
                .build())
        .setQty(entity.getQuantity())
        .setAccountType(MarketDataProto.AccountType.valueOf(entity.getAccountType()))
        .setDirection(MarketDataProto.Direction.valueOf(entity.getDirection()))
        .putMetaData("cusip", entity.getCusip())
        .putMetaData("transactionType", entity.getTransactionType())
        .putMetaData("orderId", entity.getOrderId())
        .putMetaData("accountNumber", entity.getAccountNumber())
        .putMetaData("accountType", entity.getAccountType())
        .putMetaData("pricePerShare", String.valueOf(entity.getPricePerShare()))
        .putMetaData("settlementDate", String.valueOf(entity.getSettlementDate()))
        .build();
  }

  private MarketTransactionEntity generateMarketTransaction(MarketDataProto.Instrument instrument) {
    MarketDataProto.Ticker ticker = instrument.getTicker();
    String tickerSymbol = ticker.getSymbol();
    String name = ticker.getName();
    String sector = ticker.getSector();
    String imntType = ticker.getType().name();
    int tradeDate = ticker.getData(0).getDate();
    double price = ticker.getData(0).getPrice();

    double quantity = instrument.getQty();
    String accountType = instrument.getAccountType().name();
    String direction = instrument.getDirection().name();
    String orderId = instrument.getMetaDataOrDefault("orderId", "");
    int settlementDate = Integer.parseInt(instrument.getMetaDataOrDefault("settlementDate", ""));
    double pricePerShare = Double.parseDouble(instrument.getMetaDataOrDefault("pricePerShare", ""));
    String cusip = instrument.getMetaDataOrDefault("cusip", "");
    String accountNumber = instrument.getMetaDataOrDefault("accountNumber", "");
    String transactionType = instrument.getMetaDataOrDefault("transactionType", "");

    return MarketTransactionEntity.builder()
        .orderId(orderId)
        .direction(direction)
        .ticker(tickerSymbol)
        .quantity(quantity)
        .price(price)
        .pricePerShare(pricePerShare)
        .tradeDate(tradeDate)
        .settlementDate(settlementDate)
        .tickerName(name)
        .sector(sector)
        .accountType(accountType)
        .accountNumber(accountNumber)
        .cusip(cusip)
        .transactionType(transactionType)
        .instrumentType(imntType)
        .build();
  }
}
