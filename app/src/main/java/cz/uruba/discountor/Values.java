package cz.uruba.discountor;

public class Values {

	// application modes array of String arrays { mode_identifier, string_resource }
	public static String[][] modes = {
		{ "standard"	, "mode_standard" },
		{ "multipack"	, "mode_multipack" },
		{ "percentage"	, "mode_percentage" }
	};

    // price before and after for the spinner on the fragment_discount_calculator
	public static String[][] price_before_after = {
            { "price before"    , "spinner_price_before" },
            { "price after"     , "spinner_price_after" }
    };

}
