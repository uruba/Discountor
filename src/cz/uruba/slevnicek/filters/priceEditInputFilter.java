package cz.uruba.slevnicek.filters;

import android.text.InputFilter;
import android.text.Spanned;

public class priceEditInputFilter implements InputFilter {
	double MIN = 0.00;
	double MAX = 999999.99;
	
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		try{
			String destS = dest.toString();
			String sourceS = source.toString();
			
			String compoundS = destS.substring(0, dstart) + sourceS + destS.substring(dstart, destS.length());
			double input = Double.parseDouble(compoundS);
			if (!isInRange(MIN, MAX, input) || compoundS.matches("^0[0-9].*$")){
				return "";
			}
			
		    int dotPos = -1;
		    int len = dest.length();
		    for (int i = 0; i < len; i++) {
		      char c = dest.charAt(i);
		      if (c == '.' || c == ',') {
		        dotPos = i;
		        break;
		      }
		    }
		    if (dotPos >= 0) {

		      // protects against many dots
		      if (source.equals(".") || source.equals(","))
		      {
		          return "";
		      }
		      // if the text is entered before the dot
		      if (dend <= dotPos) {
		        return null;
		      }
		      if (len - dotPos > 2) {
		        return "";
		      }
		    }
			
		} catch(Exception e) { }
		return null;
	}
	
	private boolean isInRange(double min, double max, double input){
		return input >= min && input <= max;
	}

}
