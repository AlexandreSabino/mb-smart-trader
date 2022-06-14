package com.mb.smart.trader.domains;

import com.mb.smart.trader.domains.indicators.IndicatorType;
import com.mb.smart.trader.domains.indicators.IndicatorValue;
import com.mb.smart.trader.domains.parameters.Parameter;
import com.mb.smart.trader.domains.parameters.ParameterType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class PriceContext {

    private final List<Price> prices;

    private final Price currentPrice;

    private final Map<IndicatorType, IndicatorValue<?>> indicators;

    private final Map<ParameterType, Parameter> parameters;

    public void putIndicator(final IndicatorValue<?> indicatorValue) {
        indicators.put(indicatorValue.getIndicatorType(), indicatorValue);
    }
}
