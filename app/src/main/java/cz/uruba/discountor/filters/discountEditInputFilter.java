package cz.uruba.discountor.filters;

import android.text.InputFilter;
import android.text.Spanned;

public class discountEditInputFilter implements InputFilter {
    int MIN = 0;
    int MAX = 100;


    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        try {
            String destS = dest.toString();
            String sourceS = source.toString();

            String compoundS = destS.substring(0, dstart) + sourceS + destS.substring(dstart, destS.length());
            int input = Integer.parseInt(compoundS);
            if (!isInRange(MIN, MAX, input) || compoundS.matches("^0[0-9]$")) {
                return "";
            }
        } catch (Exception e) {

        }
        return null;
    }

    private boolean isInRange(int min, int max, int input) {
        return input >= min && input <= max;
    }

}
