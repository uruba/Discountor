package cz.uruba.discountor.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyProvider {

    public static final String percentage_symbol = "%";
    private static final String not_available = "N/A";

    // returns currency symbol
    public static String getSymbol() {
        return Currency.getInstance(Locale.getDefault()).getSymbol();
    }

    // returns formatted price amount
    public static String getFormattedAmount(double price, boolean with_currency) {
        if (Double.isNaN(price)) {
            return not_available;
        }

        NumberFormat currencyFormatter;

        if (with_currency) {
            currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        } else {
            currencyFormatter = NumberFormat.getNumberInstance();
            currencyFormatter.setMaximumFractionDigits(2);
            currencyFormatter.setMinimumFractionDigits(2);
        }

        return currencyFormatter.format(price);
    }

    public static String getFormattedAmount(double price) {
        return getFormattedAmount(price, false);
    }

    public static String getFormattedPercentage(double percentage) {
        if (Double.isNaN(percentage)) {
            return not_available;
        }

        return NumberFormatter.getInstance().formatTwoDecimalDigits(percentage).concat(percentage_symbol);
    }

    //returns double from string input
    public static double getParseddouble(String input) throws ParseException {
        return NumberFormat.getNumberInstance(Locale.getDefault()).parse(input).doubleValue();
    }

}
