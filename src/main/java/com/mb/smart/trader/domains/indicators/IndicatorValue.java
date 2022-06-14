package com.mb.smart.trader.domains.indicators;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndicatorValue<T> {

    private final T value;

    private final IndicatorType indicatorType;
}
