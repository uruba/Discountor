package cz.uruba.discountor.utils;

import cz.uruba.discountor.constants.Constants;

public class DisCalc {

    public static double discFromPercentage(double origPrice, int percentage) {
        return origPrice - (roundToDecimals((origPrice / Constants.ONE_HUNDRED) * percentage, 2));
    }

    public static double origFromPercentage(double discPrice, int percentage) {
        if (percentage < Constants.ONE_HUNDRED) {
            return (discPrice / (Constants.ONE_HUNDRED - percentage)) * Constants.ONE_HUNDRED;
        }

        return Double.NaN;
    }

    public static double roundToDecimals(double inputValue, int decimalPlaces) {
        if (Double.isNaN(inputValue)) {
            return inputValue;
        }

        double decimMultiplier = (int) Math.pow(10, decimalPlaces);
        return Math.round(inputValue * decimMultiplier) / decimMultiplier;
    }

    public static double amountSavedFromOrigAndPercentage(double origPrice, int percentage) {
        return origPrice - DisCalc.discFromPercentage(origPrice, percentage);
    }

    public static double amountSavedFromDiscAndPercentage(double discPrice, int percentage) {
        return DisCalc.origFromPercentage(discPrice, percentage) - discPrice;
    }

    public static double amountSavedFromOrigAndDisc(double origPrice, double discPrice) {
        return origPrice - discPrice;
    }

    public static double percentageFromOrigAndDisc(double origPrice, double discPrice) {
        if (origPrice == Constants.DEFAULT_DOUBLE) {
            if (origPrice != discPrice)
                return Double.NaN;
            if (discPrice == Constants.DEFAULT_DOUBLE)
                return Constants.DEFAULT_DOUBLE;
        }

        return Constants.ONE_HUNDRED - (discPrice / (origPrice / Constants.ONE_HUNDRED));
    }
}
