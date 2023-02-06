package com.assessment.cts.util;

import com.assessment.cts.service.CryptoCurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Scheduled(cron = "${cron.expression.crypto-online-data}")
    public void scheduleTaskRetrieveCryptoDataOnline() {

        long now = System.currentTimeMillis() / 1000;
        logger.debug("scheduleTaskRetrieveCryptoDataOnline using cron jobs - " + now);

        cryptoCurrencyService.updateCryptoTickerFromOnline();
    }

}
