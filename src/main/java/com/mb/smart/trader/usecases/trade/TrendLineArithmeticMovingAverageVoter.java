package com.mb.smart.trader.usecases.trade;

import com.mb.smart.trader.domains.PriceContext;
import com.mb.smart.trader.domains.VoteType;
import org.springframework.stereotype.Service;

@Service
public class TrendLineArithmeticMovingAverageVoter implements TraderVote {

    @Override
    public VoteType executor(final PriceContext priceContext) {
        final AnalyzeAndPredict analyzeAndPredict = new AnalyzeAndPredict(priceContext);
        final boolean polyTrend = analyzeAndPredict.buyConsideringPolyTrend();
        final boolean logTrendLine = analyzeAndPredict.buyConsideringLogTrendLine();
        final boolean powerTrendLine = analyzeAndPredict.buyConsideringPowerTrendLine();
        final boolean expTrendLine = analyzeAndPredict.buyConsideringExpTrendLine();

        final boolean allIndicatorsBuy = polyTrend && logTrendLine && powerTrendLine && expTrendLine;
        return allIndicatorsBuy ? VoteType.BUY : VoteType.NEUTRAL;
    }
}
