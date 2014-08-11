package cz.uruba.discountor;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cz.uruba.discountor.adapters.DiscountBeforeAfterAdapter;
import cz.uruba.discountor.constants.Constants;
import cz.uruba.discountor.dialogs.SaveDiscountItemPromptDialog;
import cz.uruba.discountor.filters.discountEditInputFilter;
import cz.uruba.discountor.filters.priceEditInputFilter;
import cz.uruba.discountor.listeners.ListenerEditTextChange;
import cz.uruba.discountor.listeners.ListenerSelectDiscount;
import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItemPercentage;
import cz.uruba.discountor.utils.CurrencyProvider;
import cz.uruba.discountor.utils.DisCalc;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscountCalculatorPercentageFragment extends AbstractCalculatorFragment{

	// TextView declaration
	private TextView textPriceResultLabel, textPriceResultValue, textYouSaveResultValue, textCurrencyInline, textPriceBeforeMinusPriceAfter;
	// EditText declaration
	private EditText editPrice, editDiscountValue;
	// RadioGroup declaration
	private Spinner selectBeforeAfterDiscount;
	
	
	
	// --- CONSTRUCTOR ---
	public DiscountCalculatorPercentageFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_discount_calculator, container,
				false);
		
				
		// init selectBeforeAfterDiscount Spinner and set onSelectItem listener
        ArrayList<String> itemList = new ArrayList<String>();
        for(String[] item: Values.price_before_after){
            itemList.add(this.getString(getResources().getIdentifier(item[1], "string", this.getActivity().getPackageName())));
        }
        DiscountBeforeAfterAdapter spinAdapt = new DiscountBeforeAfterAdapter(this.getActivity(), R.layout.spinner_item_price_before_after, itemList);
        spinAdapt.setDropDownViewResource(R.layout.spinner_item_price_before_after_dropdown);

        selectBeforeAfterDiscount = (Spinner) rootView.findViewById(R.id.textPrice);
        selectBeforeAfterDiscount.setAdapter(spinAdapt);
        selectBeforeAfterDiscount.setOnItemSelectedListener(new ListenerSelectDiscount(this));

		
		// init textPriceResultLabel
		textPriceResultLabel = (TextView) rootView.findViewById(R.id.textPrice2);
		setTextBeforeAfter(selectBeforeAfterDiscount.getSelectedItemId());
		
		textPriceResultValue = (TextView) rootView.findViewById(R.id.textPriceResult);
		textYouSaveResultValue = (TextView) rootView.findViewById(R.id.textYouSaveResult);
        textPriceBeforeMinusPriceAfter = (TextView) rootView.findViewById(R.id.textPriceBeforeMinusPriceAfter);

		
		//init editPrice EditText and set onChange listener and init its label (with currency symbol)
		editPrice = (EditText) rootView.findViewById(R.id.editPrice);
		editPrice.addTextChangedListener(new ListenerEditTextChange(this));
		editPrice.setFilters(new InputFilter[]{ new priceEditInputFilter() });
		
		textCurrencyInline = (TextView) rootView.findViewById(R.id.textCurrencyInline);
		textCurrencyInline.setText(CurrencyProvider.getSymbol());

        //((TextView) rootView.findViewById(R.id.textDiscountValue)).append(":");

		// init editDiscountValue EditText
		editDiscountValue = (EditText) rootView.findViewById(R.id.editDiscountValue);
		editDiscountValue.addTextChangedListener(new ListenerEditTextChange(this));
		editDiscountValue.setFilters(new InputFilter[]{ new discountEditInputFilter() });

		resetEditValues(true);

		return rootView;
	}

	//----- START Text Before/After Discount manipulation
	
	private String formatTextBeforeAfter(long id){
		String price_formatted_append = 
				id == 0 ?
				this.getString(R.string.price_after) : 
				this.getString(R.string.price_before);

		return String.format(this.getString(R.string.price_formatted), price_formatted_append);
	}
	
	public void setTextBeforeAfter(long id){
		textPriceResultLabel
			.setText(formatTextBeforeAfter(id));
	}
	
	//----- END Text Before/After Discount manipulation

    private boolean isCheckedPriceBefore(){
        return selectBeforeAfterDiscount.getSelectedItemId() == 0;
    }

	protected DiscountItemPercentage calculateResult(){
		double price;
		int discount;
		
		
		try{
			price = Double.valueOf(getStringFromNumberEditText(editPrice));
			discount = Integer.valueOf(getStringFromNumberEditText(editDiscountValue));
		} catch(Exception e) {
			Log.i("Slevnicek", e.toString());
			return null;
		}

        boolean isPriceBefore = isCheckedPriceBefore();


        return new DiscountItemPercentage(isPriceBefore, price, discount);
	}

	public void setTextResult(){
		DiscountItemPercentage result = calculateResult();
		
		String resultPrice;
		String resultYouSave;

        Double savings_double = result.getSavings();
        Double resultPrice_double = result.isPriceBefore() ? result.getPriceAfter() : result.getPriceBefore();
		
		/*if(result == null){
			resultPrice = resultYouSave = this.getString(R.string.na); 
		} else {*/
            // result price is opposite to the input price (therefore, if the input price is marked as a price before, we must get the price after as a result – and vice versa)
			resultPrice = CurrencyProvider.getFormattedAmount(resultPrice_double, true);
			resultYouSave = CurrencyProvider.getFormattedAmount(savings_double, true);
		/*}*/

        editPrice.setTextColor(result.isPriceBefore() ?
                                    getResources().getColor(R.color.theme_red) :
                                    getResources().getColor(R.color.black));
		textPriceResultValue
			.setText(resultPrice);
        textPriceResultValue.setTextColor(!result.isPriceBefore() && resultPrice_double > Constants.DEFAULT_DOUBLE ?
                                                getResources().getColor(R.color.theme_red) :
                                                getResources().getColor(R.color.black));
		textYouSaveResultValue
			.setText(resultYouSave);
        textYouSaveResultValue.setTextColor(DisCalc.roundToDecimals(savings_double,2) > Constants.DEFAULT_DOUBLE ?
                                                getResources().getColor(R.color.theme_green) :
                                                getResources().getColor(R.color.black));

        textPriceBeforeMinusPriceAfter
                .setText(getStringPriceBeforeMinusPriceAfter(result));
		
		result = null;
	}

	public void resetEditValues(boolean showKeyboard){
		editPrice.setText("");
		editDiscountValue.setText("");
		
	    focusAndShowKeyboard(editPrice, showKeyboard);
	}

    public void saveResultValuesToDB(String optionalName){
        DiscountItemPercentage savedItem = calculateResult();

        if (optionalName != null && !optionalName.isEmpty()) {
            savedItem.setDiscountName(optionalName);
        }

        modelDiscountItem.addNew(savedItem);

        Toast confirmationToast = Toast
                .makeText(getActivity(), R.string.prompt_save_succesful, Toast.LENGTH_SHORT);

        confirmationToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, ((ActionBarActivity)getActivity()).getSupportActionBar().getHeight() + getResources().getDimensionPixelSize(R.dimen.toastSaveConfirmationMarginTop));
        confirmationToast.show();
    }
	
	
	
}