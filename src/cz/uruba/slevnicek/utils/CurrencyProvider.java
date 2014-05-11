package cz.uruba.slevnicek.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyProvider {
	private Currency currency;
	
	// ---- CONSTRUCTOR ----
	public CurrencyProvider(){
		this.currency = Currency.getInstance(Locale.getDefault());
	}
	
	
	// returns currency symbol
	public String getSymbol(){
		return currency.getSymbol();
	}
	
	// returns formatted price amount
	public String getFormattedAmount(double price, boolean with_currency){
		NumberFormat currencyFormatter;
		
		if(with_currency){
			currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
		} else {
			currencyFormatter = NumberFormat.getNumberInstance();
			currencyFormatter.setMaximumFractionDigits(2);
			currencyFormatter.setMinimumFractionDigits(2);
		}
		
		return currencyFormatter.format(price);
	}
	
	//returns double from string input
	public double getParseddouble(String input) throws ParseException{
		return NumberFormat.getNumberInstance(Locale.getDefault()).parse(input).doubleValue();
	}

}
