package com.vv.personal.twm.crdb.v1.data.dao.impl;

import com.vv.personal.twm.artifactory.generated.equitiesMarket.MarketDataProto;
import com.vv.personal.twm.crdb.v1.data.dao.MarketDataDao;
import com.vv.personal.twm.crdb.v1.data.entity.MarketDataEntity;
import com.vv.personal.twm.crdb.v1.data.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2024-10-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MarketDataDaoImpl implements MarketDataDao {
    private final MarketDataRepository marketDataRepository;

    @Override
    public Optional<MarketDataProto.Ticker> getMarketDataByTicker(String ticker) {
        try {
            List<MarketDataEntity> marketDataEntities = marketDataRepository.getAllByTicker(ticker);
            if (marketDataEntities.isEmpty()) {
                log.warn("Found no data for ticker {}", ticker);
                return Optional.empty();
            }
            System.out.println(marketDataEntities);

            List<MarketDataProto.Value> values = marketDataEntities.stream().map(marketDataEntity ->
                    MarketDataProto.Value.newBuilder()
                            .setDate(marketDataEntity.getId().getDate())
                            .setPrice(marketDataEntity.getPrice())
                            .build()
            ).toList();
            return Optional.of(
                    MarketDataProto.Ticker.newBuilder()
                            .setSymbol(ticker)
                            .addAllData(values)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error on getting market data for ticker {}", ticker);
        }
        return Optional.empty();
    }

    @Override
    public int insertMarketData(int date, String ticker, double price) {
        return 0;
    }


    /*public boolean addBank(BankProto.Bank bank) {
        Instant currentTime = Instant.now();
        try {
            BankEntity bankEntity = generateBankEntity(bank, currentTime);
            bankRepository.saveAndFlush(bankEntity);
        } catch (Exception e) { //TODO - streamline exception later
            log.error("Error on bank save. ", e);
            return false;
        }
        return true;
    }*/


}