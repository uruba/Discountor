package cz.uruba.discountor;

import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cz.uruba.discountor.constants.Constants;
import cz.uruba.discountor.filters.discountEditInputFilter;
import cz.uruba.discountor.filters.priceEditInputFilter;
import cz.uruba.discountor.listeners.ListenerEditTextChange;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemMultipack;
import cz.uruba.discountor.utils.CurrencyProvider;

public class DiscountCalculatorMultipackFragment extends AbstractCalculatorFragment {

    private EditText editPriceSingle, editPriceMultipack, editMultipackPcs;
    private TextView textSavedPercentageResult, textSavedAmountResult, textCurrencyInlineSingle, textCurrencyInlineMultipack, textWouldCost, textPriceBeforeMinusPriceAfter, textSavedAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multipack_discount, container,
                false);

        editPriceSingle = (EditText) rootView.findViewById(R.id.editPriceSingle);
        editPriceSingle.addTextChangedListener(new ListenerEditTextChange(this));
        editPriceSingle.setFilters(new InputFilter[]{ new priceEditInputFilter() });

        editPriceMultipack = (EditText) rootView.findViewById(R.id.editPriceMultipack);
        editPriceMultipack.addTextChangedListener(new ListenerEditTextChange(this));
        editPriceMultipack.setFilters(new InputFilter[]{ new priceEditInputFilter() });

        editMultipackPcs = (EditText) rootView.findViewById(R.id.editMultipackPcs);
        editMultipackPcs.addTextChangedListener(new ListenerEditTextChange(this));
        editMultipackPcs.setFilters(new InputFilter[]{ new discountEditInputFilter() });

        textWouldCost = (TextView) rootView.findViewById(R.id.textWouldCost);

        textSavedPercentageResult = (TextView) rootView.findViewById(R.id.textSavedPercentageResult);
        textSavedAmount = (TextView) rootView.findViewById(R.id.textSavedAmount);
        textSavedAmountResult = (TextView) rootView.findViewById(R.id.textSavedAmountResult);
        textPriceBeforeMinusPriceAfter = (TextView) rootView.findViewById(R.id.textPriceBeforeMinusPriceAfter);

        textCurrencyInlineSingle = (TextView) rootView.findViewById(R.id.textCurrencyInlineSingle);
        textCurrencyInlineSingle.setText(CurrencyProvider.getSymbol());

        textCurrencyInlineMultipack = (TextView) rootView.findViewById(R.id.textCurrencyInlineMultipack);
        textCurrencyInlineMultipack.setText(CurrencyProvider.getSymbol());

        resetEditValues(false);

        return rootView;
    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.discount_multipack, menu);
	}

    @Override
    public void saveResultValuesToDB(String optionalName) {
        return;
    }

    @Override
    protected DiscountItemMultipack calculateResult() {
        double priceSingle, priceMultipack;
        int multipackPcs;

        try{
            priceSingle = Double.valueOf(getStringFromNumberEditText(editPriceSingle));
            priceMultipack = Double.valueOf(getStringFromNumberEditText(editPriceMultipack));
            multipackPcs = Integer.valueOf(getStringFromNumberEditText(editMultipackPcs));
        } catch(Exception e) {
            Log.i("Slevnicek", e.toString());
            return null;
        }

        return new DiscountItemMultipack(priceSingle, priceMultipack, multipackPcs);
    }

    @Override
    public void setTextResult() {
		DiscountItemMultipack result = calculateResult();

        String resultSavedPercentage;
        String resultSavedAmount;

        Double saved_percentage_double = result.getSavingsAltogetherPercentage();
        Double saved_amount_double = result.getSavingsAltogether();

        boolean isSavings = saved_amount_double > Constants.DEFAULT_DOUBLE;
        boolean isZero = saved_amount_double == Constants.DEFAULT_DOUBLE;

        resultSavedPercentage = CurrencyProvider.getFormattedPercentage(saved_percentage_double);
        resultSavedAmount = CurrencyProvider.getFormattedAmount(saved_amount_double, true);

        textSavedPercentageResult.setText(resultSavedPercentage);
        textSavedPercentageResult.setTextColor(saved_percentage_double < Constants.DEFAULT_DOUBLE ?
                getResources().getColor(R.color.theme_red) :
                getResources().getColor(R.color.black));

        textSavedAmount.setText(isSavings || isZero ?
                                    getResources().getText(R.string.saved_total) :
                                    getResources().getText(R.string.lost_total)
                                    );

        textSavedAmountResult.setText(resultSavedAmount);
        textSavedAmountResult.setTextColor(isSavings ?
                getResources().getColor(R.color.theme_green) :
                isZero ?
                        getResources().getColor(R.color.black) :
                        getResources().getColor(R.color.theme_red));

        textWouldCost.setText(
                Html.fromHtml(
                    String.format(
                            getActivity().getResources().getString(R.string.when_purchased_separately_it_would_cost),
                            String.valueOf(result.getNoOfItems()),
                            CurrencyProvider.getFormattedAmount(result.getPriceSingle(), true),
                            CurrencyProvider.getFormattedAmount(result.getNoOfItems() * result.getPriceSingle(), true)
                    )
                )

        );

        textPriceBeforeMinusPriceAfter.setText(getStringPriceBeforeMinusPriceAfter(result));


	}

	@Override
    public void resetEditValues(boolean showKeyboard) {
        editPriceMultipack.setText(Constants.EMPTY_STRING);
        editMultipackPcs.setText(Constants.EMPTY_STRING);
        editPriceSingle.setText(Constants.EMPTY_STRING);

        focusAndShowKeyboard(editPriceMultipack, showKeyboard);
	}
	
	
}
