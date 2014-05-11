package cz.uruba.slevnicek;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import cz.uruba.slevnicek.filters.discountEditInputFilter;
import cz.uruba.slevnicek.filters.priceEditInputFilter;
import cz.uruba.slevnicek.listeners.ListenerEditTextChange;
import cz.uruba.slevnicek.listeners.ListenerSelectDiscount;
import cz.uruba.slevnicek.utils.DisCalc;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscountCalculatorFragment extends AbstractCalculatorFragment {

	// TextView declaration
	private TextView textPriceResultLabel, textPriceResultValue, textYouSaveResultValue, textCurrency1;
	// EditText declaration
	private EditText editPrice, editDiscountValue;
	// RadioGroup declaration
	private RadioGroup selectBeforeAfterDiscount;
	
	
	
	// --- CONSTRUCTOR ---
	public DiscountCalculatorFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView();
		
		View rootView = inflater.inflate(R.layout.fragment_discount_calculator, container,
				false);
		
				
		// init selectBeforeAfterDiscount RadioGroup and set onCheckedChange listener
		selectBeforeAfterDiscount = (RadioGroup) rootView.findViewById(R.id.selectBeforeAfterDiscount);
		selectBeforeAfterDiscount.setOnCheckedChangeListener(new ListenerSelectDiscount(this));
		
		// init textPriceResultLabel
		textPriceResultLabel = (TextView) rootView.findViewById(R.id.textPrice2);
		setTextBeforeAfter();
		
		textPriceResultValue = (TextView) rootView.findViewById(R.id.textPriceResult);
		textYouSaveResultValue = (TextView) rootView.findViewById(R.id.textYouSaveResult);
		
		//init editPrice EditText and set onChange listener and init its label (with currency symbol)
		editPrice = (EditText) rootView.findViewById(R.id.editPrice);
		editPrice.addTextChangedListener(new ListenerEditTextChange(this));
		editPrice.setFilters(new InputFilter[]{ new priceEditInputFilter() });
		
		textCurrency1 = (TextView) rootView.findViewById(R.id.textCurrency1);
		textCurrency1.setText(currencyProvider.getSymbol());
		
		// init editDiscountValue EditText
		editDiscountValue = (EditText) rootView.findViewById(R.id.editDiscountValue);
		editDiscountValue.addTextChangedListener(new ListenerEditTextChange(this));
		editDiscountValue.setFilters(new InputFilter[]{ new discountEditInputFilter() });

		resetEditValues(false);
		
		return rootView;
	}

	
	//----- START Text Before/After Discount manipulation
	
	private String formatTextBeforeAfter(){
		String price_formatted_append = 
				selectBeforeAfterDiscount.getCheckedRadioButtonId() == R.id.radioBeforeDiscount ? 
				this.getString(R.string.price_after) : 
				this.getString(R.string.price_before);

		return String.format(this.getString(R.string.price_formatted), price_formatted_append);
	}
	
	public void setTextBeforeAfter(){
		textPriceResultLabel
			.setText(formatTextBeforeAfter());
	}
	
	//----- END Text Before/After Discount manipulation
	
	private class PriceResult {
		public double resultPrice, resultAmountSaved;
	}
	
	private PriceResult calculateResult(){	
		double price;
		int discount;
		PriceResult result = new PriceResult();
		
		
		try{
			price = Double.valueOf(getStringFromNumberEditText(editPrice));
			discount = Integer.valueOf(getStringFromNumberEditText(editDiscountValue));
		} catch(Exception e) {
			Log.i("Slevnicek", e.toString());
			return null;
		}
		
        
		if(selectBeforeAfterDiscount.getCheckedRadioButtonId() == R.id.radioBeforeDiscount){
			result.resultPrice = DisCalc.discFromPercentage(price, discount);
			result.resultAmountSaved = price - result.resultPrice;
		} else {
			result.resultPrice = DisCalc.roundToDecimals(DisCalc.origFromPercentage(price, discount), 2);
			result.resultAmountSaved = result.resultPrice - price;
		}
		
		return result;
	}
	
	private String getStringFromNumberEditText(EditText edit){
		return edit.getText().toString().equals("") ? edit.getHint().toString() : edit.getText().toString(); 
	}
	
	public void setTextResult(){
		PriceResult result = calculateResult();
		
		String resultPrice;
		String resultYouSave;
		
		if(result == null){
			resultPrice = resultYouSave = this.getString(R.string.na); 
		} else {
			resultPrice = currencyProvider.getFormattedAmount(result.resultPrice, true);
			resultYouSave = currencyProvider.getFormattedAmount(result.resultAmountSaved, true);
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