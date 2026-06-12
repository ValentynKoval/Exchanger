package com.exchanger.service;

import com.exchanger.entity.Rate;
import com.exchanger.entity.enums.CurrencyEnum;
import com.exchanger.repository.RateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {
    private final RateRepository rateRepository;
    private final ObjectMapper objectMapper;

    @Value("${bank.rate.url}")
    private String bankUrl;

    @SneakyThrows
    @Scheduled(cron = "${schedule.cron.time.table}")
        /*
    +-------------------- second (0 - 59)
    |  +----------------- minute (0 - 59)
    |  |  +-------------- hour (0 - 23)
    |  |  |  +----------- day of month (1 - 31)
    |  |  |  |  +-------- month (1 - 12)
    |  |  |  |  |  +----- day of week (0 - 6) (Sunday=0 or 7)
    |  |  |  |  |  |  +-- year [optional]
    |  |  |  |  |  |  |
    *  *  *  *  *  *  * command to be executed
    */

    public void getRates() {
        log.info("Get rates");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(bankUrl, String.class);
        log.info("Rates: {}", response.getBody());

        if (response.getStatusCode().isError()) {
            log.warn("Error during get current rate");
        }

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode.forEach(node -> {
            if (node.get("ccy").asText().equals("USD") || node.get("ccy").asText().equals("EUR")) {
                rateRepository.save(new Rate()
                        .setBuy(node.get("buy").asText())
                        .setSale(node.get("sale").asText())
                        .setReceive(new Timestamp(System.currentTimeMillis()))
                        .setCurrency(CurrencyEnum.valueOf(node.get("ccy").asText())));
            }
        });
    }
}
