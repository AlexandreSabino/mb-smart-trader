package com.mb.smart.trader.usecases.trade;

import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.VoteType;
import com.mb.smart.trader.domains.indicators.IndicatorType;
import com.mb.smart.trader.domains.indicators.IndicatorValue;
import org.meteoinfo.math.fitting.PolyTrendLine;
import org.meteoinfo.ndarray.Array;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolyTrendLineArithmeticMovingAverageVoter implements TraderVote {

    @Override
    public VoteType executor(final PriceContext priceContext) {
        final var pricesSimpleAvg = ((IndicatorValue<List<Double>>) priceContext
                .getIndicators()
                .get(IndicatorType.ARITHMETIC_MOVING_AVERAGE))
                .getValue();


        return null;
    }

    public static void main(String... args) {
        PolyTrendLine polyTrendLine = new PolyTrendLine(1);

        double[] horas = new double[]  {10D, 11D, 12D, 13D};
        double[] valores = new double[]{10D,  15D,  12D,  7D};

        polyTrendLine.setValues(Array.factory(valores), Array.factory(horas));

        System.out.println(polyTrendLine.predict(14D));
    }
}
