package cz.uruba.discountor.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by vasek on 21/08/14.
 */
public class NumberFormatter {

    private static NumberFormatter numberFormatter;
    private static NumberFormat twoDecimalDigits, zeroDecimalDigits;

    private NumberFormatter(){
       twoDecimalDigits = new DecimalFormat("0.##");
       zeroDecimalDigits = new DecimalFormat("0");
    }

    public static NumberFormatter getInstance(){
        if (numberFormatter == null){
            numberFormatter = new NumberFormatter();
        }

        return numberFormatter;
    }

    public static String formatTwoDecimalDigits(double input){
        return twoDecimalDigits.format(input);
    }

    public static String formatZeroDecimalDigits(double input){
        return zeroDecimalDigits.format(input);
    }
}
