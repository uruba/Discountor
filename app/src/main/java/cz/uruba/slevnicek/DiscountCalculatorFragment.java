package cz.uruba.slevnicek;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cz.uruba.slevnicek.adapters.DiscountBeforeAfterAdapter;
import cz.uruba.slevnicek.filters.discountEditInputFilter;
import cz.uruba.slevnicek.filters.priceEditInputFilter;
import cz.uruba.slevnicek.listeners.ListenerEditTextChange;
import cz.uruba.slevnicek.listeners.ListenerSelectDiscount;
import cz.uruba.slevnicek.models.DiscountItem;
import cz.uruba.slevnicek.utils.CurrencyProvider;
import cz.uruba.slevnicek.utils.DisCalc;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscountCalculatorFragment extends AbstractCalculatorFragment{

	// TextView declaration
	private TextView textPriceResultLabel, textPriceResultValue, textYouSaveResultValue, textCurrency1;
	// EditText declaration
	private EditText editPrice, editDiscountValue;
	// RadioGroup declaration
	private Spinner selectBeforeAfterDiscount;
	
	
	
	// --- CONSTRUCTOR ---
	public DiscountCalculatorFragment() {
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
		
		//init editPrice EditText and set onChange listener and init its label (with currency symbol)
		editPrice = (EditText) rootView.findViewById(R.id.editPrice);
		editPrice.addTextChangedListener(new ListenerEditTextChange(this));
		editPrice.setFilters(new InputFilter[]{ new priceEditInputFilter() });
		
		textCurrency1 = (TextView) rootView.findViewById(R.id.textCurrency1);
		textCurrency1.setText(CurrencyProvider.getSymbol());
		
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

	private DiscountItem calculateResult(){
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


        return new DiscountItem(price, isPriceBefore, discount);
	}
	
	private String getStringFromNumberEditText(EditText edit){
		return edit.getText().toString().equals("") ? edit.getHint().toString() : edit.getText().toString(); 
	}

	public void setTextResult(){
		DiscountItem result = calculateResult();
		
		String resultPrice;
		String resultYouSave;
		
		if(result == null){
			resultPrice = resultYouSave = this.getString(R.string.na); 
		} else {
            // result price is opposite to the input price (therefore, if the input price is marked as a price before, we must get the price after as a result – and vice versa)
			resultPrice = CurrencyProvider.getFormattedAmount((isCheckedPriceBefore()) ? result.getPriceAfter() : result.getPriceBefore(), true);
			resultYouSave = CurrencyProvider.getFormattedAmount(result.getSavings(), true);
		}
		
		textPriceResultValue
			.setText(resultPrice);
		textYouSaveResultValue
			.setText(resultYouSave);
		
		result = null;
	}

	public void resetEditValues(boolean showKeyboard){
		editPrice.setText("");
		editDiscountValue.setText("");
		
	    focusAndShowKeyboard(editPrice, showKeyboard);
	}
	
	
	
}