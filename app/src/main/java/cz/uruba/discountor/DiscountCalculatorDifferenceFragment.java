package cz.uruba.discountor;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cz.uruba.discountor.constants.Constants;
import cz.uruba.discountor.filters.priceEditInputFilter;
import cz.uruba.discountor.listeners.ListenerEditTextChange;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemDifference;
import cz.uruba.discountor.utils.CurrencyProvider;
import cz.uruba.discountor.utils.DisCalc;

/**
 * Created by Temp on 5.8.2014.
 */
public class DiscountCalculatorDifferenceFragment extends AbstractCalculatorFragment {

    private TextView textDifferencePercentResult, textYouSave, textYouSaveResult, textCurrencyInlineBefore, textCurrencyInlineAfter, textPriceBeforeMinusPriceAfter;
    private EditText editPriceBefore, editPriceAfter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_difference_discount, container,
                false);

        textDifferencePercentResult = (TextView) rootView.findViewById(R.id.textDifferencePercentResult);
        textYouSave = (TextView) rootView.findViewById(R.id.textYouSave);
        textYouSaveResult = (TextView) rootView.findViewById(R.id.textYouSaveResult);


        editPriceBefore = (EditText) rootView.findViewById(R.id.editPriceBefore);
        editPriceBefore.addTextChangedListener(new ListenerEditTextChange(this));
        editPriceBefore.setFilters(new InputFilter[]{ new priceEditInputFilter() });

        editPriceAfter = (EditText) rootView.findViewById(R.id.editPriceAfter);
        editPriceAfter.addTextChangedListener(new ListenerEditTextChange(this));
        editPriceAfter.setFilters(new InputFilter[]{ new priceEditInputFilter() });

        textCurrencyInlineBefore = (TextView) rootView.findViewById(R.id.textCurrencyInlineBefore);
        textCurrencyInlineBefore.setText(CurrencyProvider.getSymbol());

        textCurrencyInlineAfter = (TextView) rootView.findViewById(R.id.textCurrencyInlineAfter);
        textCurrencyInlineAfter.setText(CurrencyProvider.getSymbol());

        textPriceBeforeMinusPriceAfter = (TextView) rootView.findViewById(R.id.textPriceBeforeMinusPriceAfter);


        resetEditValues(true);

        return rootView;
    }

    @Override
    protected DiscountItemDifference calculateResult() {
        double priceBefore, priceAfter;

        try{
            priceBefore = Double.valueOf(getStringFromNumberEditText(editPriceBefore));
            priceAfter = Double.valueOf(getStringFromNumberEditText(editPriceAfter));
        } catch(Exception e) {
            Log.i("Slevnicek", e.toString());
            return null;
        }

        return new DiscountItemDifference(priceBefore, priceAfter);
    }

    @Override
    public void setTextResult() {
        DiscountItemDifference result = calculateResult();

        String resultPercent;
        String resultYouSave;

        Double percentage_discount_double = result.getPercentageDiscount();
        Double savings_double = DisCalc.roundToDecimals(result.getSavings(), 2);

        resultPercent = CurrencyProvider.getFormattedPercentage(percentage_discount_double);
        resultYouSave = CurrencyProvider.getFormattedAmount(savings_double, true);

        textDifferencePercentResult.setText(resultPercent);
        textDifferencePercentResult.setTextColor(percentage_discount_double < Constants.DEFAULT_DOUBLE ?
                                                getResources().getColor(R.color.theme_red) :
                                               getResources().getColor(R.color.black));


        boolean isSavings = savings_double > Constants.DEFAULT_DOUBLE;
        boolean isZero = savings_double == Constants.DEFAULT_DOUBLE;

        textYouSave.setText(isSavings || isZero ?
                                getResources().getText(R.string.you_save) :
                                getResources().getText(R.string.you_lose));

        textYouSaveResult.setText(resultYouSave);
        textYouSaveResult.setTextColor(isSavings ?
                                          getResources().getColor(R.color.theme_green) :
                                       isZero ?
                                          getResources().getColor(R.color.black) :
                                          getResources().getColor(R.color.theme_red));


        textPriceBeforeMinusPriceAfter
                .setText(getStringPriceBeforeMinusPriceAfter(result));

        result = null;
    }

    @Override
    public void resetEditValues(boolean showKeyboard) {
        editPriceBefore.setText("");
        editPriceAfter.setText("");

        focusAndShowKeyboard(editPriceBefore, showKeyboard);
    }
}
