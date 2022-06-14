package com.mb.smart.trader.domains;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Price {
    private String id;
    private String ticker;
    private double price;
    private double open24;
    private double volume24;
    private double low24h;
    private double high24h;
    private double volume30d;
    private double bestBid;
    private double bestAsk;
    private String side;
    private String provider;
    private LocalDateTime collectedAt;
}
