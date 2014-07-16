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
import cz.uruba.slevnicek.models.DiscountItem;
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

    private boolean isCheckedPriceBefore(){
        return selectBeforeAfterDiscount.getCheckedRadioButtonId() == R.id.radioBeforeDiscount;
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
			resultPrice = currencyProvider.getFormattedAmount((isCheckedPriceBefore()) ? result.getPriceAfter() : result.getPriceBefore(), true);
			resultYouSave = currencyProvider.getFormattedAmount(result.getSavings(), true);
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