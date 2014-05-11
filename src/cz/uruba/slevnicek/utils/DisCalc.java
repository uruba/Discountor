package cz.uruba.slevnicek.utils;

public class DisCalc {
	private static final int ONE_HUNDRED = 100;
	
	public static double discFromPercentage(double origPrice, int percentage){		
		return origPrice - ((origPrice / ONE_HUNDRED) * percentage);
	}
	
	public static double origFromPercentage(double discPrice, int percentage){		
		return (discPrice / (ONE_HUNDRED - percentage)) * ONE_HUNDRED;
	}
	
	public static double roundToDecimals(double inputValue, int decimalPlaces){
		double decimMultiplier = (int) Math.pow(10, decimalPlaces);
		return Math.round(inputValue * decimMultiplier) / decimMultiplier;
	}
}
