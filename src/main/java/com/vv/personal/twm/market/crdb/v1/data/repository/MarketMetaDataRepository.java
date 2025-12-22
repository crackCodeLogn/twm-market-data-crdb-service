package com.vv.personal.twm.market.crdb.v1.data.repository;

import com.vv.personal.twm.market.crdb.v1.data.entity.MarketMetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vivek
 * @since 2025-12-21
 */
@Repository
public interface MarketMetaDataRepository extends JpaRepository<MarketMetaDataEntity, String> {}
