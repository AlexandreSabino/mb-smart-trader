package com.mb.smart.trader.domains;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DatePriceValue {
    private double priceValue;
    public LocalDateTime date;
}
