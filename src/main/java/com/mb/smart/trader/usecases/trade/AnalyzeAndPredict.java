package com.mb.smart.trader.usecases.trade;

import com.mb.smart.trader.domains.DatePriceValue;
import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.indicators.IndicatorType;
import com.mb.smart.trader.domains.indicators.IndicatorValue;
import com.mb.smart.trader.domains.parameters.Parameter;
import com.mb.smart.trader.domains.parameters.ParameterType;
import org.meteoinfo.math.fitting.*;
import org.meteoinfo.ndarray.Array;

import java.time.ZoneId;
import java.util.List;

public class AnalyzeAndPredict {

    private final PolyTrendLine polyTrendLine = new PolyTrendLine(1);

    private final ExpTrendLine expTrendLine = new ExpTrendLine();

    private final LogTrendLine logTrendLine = new LogTrendLine();

    private final PowerTrendLine powerTrendLine = new PowerTrendLine();

    private final PriceContext priceContext;

    private final double[] timestamp;

    private final double[] values;

    public AnalyzeAndPredict(final PriceContext priceContext) {
        final var pricesSimpleAvg = ((IndicatorValue<List<DatePriceValue>>) priceContext
                .getIndicators()
                .get(IndicatorType.ARITHMETIC_MOVING_AVERAGE))
                .getValue();

        this.timestamp = new double[pricesSimpleAvg.size()];
        this.values = new double[pricesSimpleAvg.size()];
        for (int i = 0; i < pricesSimpleAvg.size(); i++) {
            final var datePriceValue = pricesSimpleAvg.get(i);
            final long epochSecond = datePriceValue.getDate().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
            this.timestamp[i] = (double) epochSecond;
            this.values[i] = datePriceValue.getPriceValue();
        }
        this.priceContext = priceContext;
    }

    public boolean buyConsideringPolyTrend() {
        return this.buyConsideringTrend(polyTrendLine, ParameterType.PERCENT_POLY_TREND_FOR_BUY);
    }

    public boolean buyConsideringExpTrendLine() {
        return this.buyConsideringTrend(expTrendLine, ParameterType.PERCENT_EXP_TREND_FOR_BUY);
    }

    public boolean buyConsideringLogTrendLine() {
        return this.buyConsideringTrend(logTrendLine, ParameterType.PERCENT_LOG_TREND_FOR_BUY);
    }

    public boolean buyConsideringPowerTrendLine() {
        return this.buyConsideringTrend(powerTrendLine, ParameterType.PERCENT_POWER_TREND_FOR_BUY);
    }

    private boolean buyConsideringTrend(final OLSTrendLine trendLine, final ParameterType parameterType) {
        final double currentPriceValue = priceContext.getCurrentPrice().getPrice();
        trendLine.setValues(Array.factory(values), Array.factory(timestamp));
        final double newPrice = trendLine.predict(currentPriceValue);

        final Parameter parameter = priceContext.getParameters().get(parameterType);
        final var percentage = (Double) parameter.getValue();
        return (newPrice / currentPriceValue) > percentage;
    }
}
