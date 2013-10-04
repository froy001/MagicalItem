package com.froy.magicalitem;

import java.util.Locale;

public class StringManipulation {
	
	public String formatPrice(Item item){
		
		String price = item.getPrice();
		if(price.toLowerCase().contains("bonus")){
			return price;
		}else if(price.toLowerCase(Locale.ENGLISH).contains("+") && price.toLowerCase(Locale.ENGLISH).contains("gp")){
		price = price.substring(2, price.length()-3);
		
		}
		
		return price ;
		
	}

}
