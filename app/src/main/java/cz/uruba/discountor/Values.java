package cz.uruba.discountor;

public class Values {

	// application modes array of String arrays { mode_identifier, string_resource }
	public static String[][] modes = {
		{ "percentage"	, "mode_percentage" },
        { "difference"	, "mode_difference" },
		{ "multipack"	, "mode_multipack" }
	};

    // price before and after for the spinner on the fragment_discount_calculator
	public static String[][] price_before_after = {
            { "price before"    , "spinner_price_before" },
            { "price after"     , "spinner_price_after" }
    };

}
