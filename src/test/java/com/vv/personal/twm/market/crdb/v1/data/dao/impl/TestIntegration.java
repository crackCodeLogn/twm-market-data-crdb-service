package com.vv.personal.twm.market.crdb.v1.data.dao.impl;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestIntegration {

  @Autowired private MarketDataPythonEngineFeign marketDataPythonEngineFeign;

  @Test
  void testMultiThread() {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Set<String> imnts =
        Sets.newHashSet(
            "ABX.TO",
            "AC.TO",
            "AEM.TO",
            "AFM.V",
            "AI.TO",
            "ALV.V",
            "AQN.TO",
            "ATD.TO",
            "ATZ.TO",
            "BBD-B.TO",
            "BCE.TO",
            "BEP-UN.TO",
            "BMO.TO",
            "BNS.TO",
            "BRE.TO",
            "CADINR=X",
            "CADUSD=X",
            "CAE.TO",
            "CAR-UN.TO",
            "CCA.TO",
            "CCO.TO",
            "CLS.TO",
            "CM.TO",
            "CMR.TO",
            "CNQ.TO",
            "CNR.TO",
            "CP.TO",
            "CRT-UN.TO",
            "CSU.TO",
            "CVE.TO",
            "DBM.TO",
            "DIV.TO",
            "DOL.TO",
            "EMA.TO",
            "ENB.TO",
            "FTS.TO",
            "GH.TO",
            "GRT-UN.TO",
            "H.TO",
            "K.TO",
            "L.TO",
            "MFC.TO",
            "MRU.TO",
            "NTR.TO",
            "POU.TO",
            "PPL.TO",
            "PXT.TO",
            "PZA.TO",
            "QSR.TO",
            "RY.TO",
            "SGY.TO",
            "SHOP.TO",
            "SLF.TO",
            "STLC.TO",
            "SU.TO",
            "T.TO",
            "TAL.TO",
            "TD.TO",
            "TEC.TO",
            "TRI.TO",
            "TRP.TO",
            "URC.TO",
            "USDINR=X",
            "VBAL.TO",
            "VCE.TO",
            "VDY.TO",
            "VEE.TO",
            "VFV.TO",
            "VLB.TO",
            "VSB.TO",
            "WCN.TO",
            "WPM.TO",
            "WSP.TO",
            "XBAL.TO",
            "XEQT.TO",
            "XIT.TO",
            "XIU.TO",
            "ZDB.TO",
            "ZQQ.TO",
            "^GSPTSE",
            "^VIX",
            "dpro.cn",
            "lun.to",
            "TPZ.TO",
            "ASTL.TO",
            "CASH.TO");
    List<Callable<String>> callables = new ArrayList<>();
    for (String imnt : imnts)
      callables.add(
          () -> {
            System.out.println("Invoking ticker name for " + imnt);
            return marketDataPythonEngineFeign.getTickerName(imnt);
          });
    List<Future<String>> futures;
    StopWatch stopWatch = StopWatch.createStarted();
    try {
      futures = executorService.invokeAll(callables);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    stopWatch.stop();
    for (Future<String> future : futures) {
      try {
        System.out.println(future.get());
      } catch (Exception e) {
      }
    }
    System.out.println("Time taken to run > " + stopWatch.getTime(TimeUnit.MILLISECONDS));
    // 18s for 86 imnts on 2 instances
    executorService.shutdown();
  }
}
