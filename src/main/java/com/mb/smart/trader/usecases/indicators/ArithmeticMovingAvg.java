package com.mb.smart.trader.usecases.indicators;

import com.google.common.collect.Lists;
import com.mb.smart.trader.domains.Price;
import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.indicators.IndicatorType;
import com.mb.smart.trader.domains.indicators.IndicatorValue;
import com.mb.smart.trader.domains.parameters.Parameter;
import com.mb.smart.trader.domains.parameters.ParameterType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArithmeticMovingAvg implements CalculateIndicator {

    @Override
    public void putIndicator(final PriceContext priceContext) {
        final Parameter parameter = priceContext.getParameters().get(ParameterType.ARITHMETIC_MOVING_AVERAGE_NUMBER_OF_PERIODS);
        final Integer periods = (Integer) parameter.getValue();

        final List<List<Price>> pricesGroupedPeriods = Lists.partition(priceContext.getPrices(), periods);
        final List<Double> pricesAvg = pricesGroupedPeriods.stream()
                .map(prices -> {
                    final double sum = prices.stream().mapToDouble(Price::getPrice).sum();
                    return sum / prices.size();
                })
                .collect(Collectors.toList());

        final var indicatorValue = IndicatorValue.<List<Double>>builder()
                .indicatorType(IndicatorType.ARITHMETIC_MOVING_AVERAGE)
                .value(pricesAvg)
                .build();

        priceContext.putIndicator(indicatorValue);
    }
}