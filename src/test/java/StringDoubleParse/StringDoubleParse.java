package StringDoubleParse;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import entidades.Subterranea;

public class StringDoubleParse {

	public static void main(String[] args) {
	
		
		String[] array = { "0.80000001","0.30000001","0.5","7.5","9","12.5","6.5","33","4.5","3.5"};
		
		Locale Local = new Locale("pt","BR"); 
		
		
		DecimalFormat df = new DecimalFormat("#,##0.00"); 
		
		//DecimalFormat df = new DecimalFormat("#,##0.00"); 
		
		for(int i = 0; i<array.length;i++) {
			
			Double d = Double.valueOf(array[i])*1000;
			
			System.out.println(d);
			
			System.out.println(df.format(d).replace(",00", ""));
			
			
		}

		/*
		DecimalFormat df = new DecimalFormat("#,##0.00");  
		
		
		String str [] = {	"5.000,55", "5000,00", ""};
	
	
		for (String s : str ) {
			System.out.println(s);
			
			try {
				System.out.println("parse " + df.parseObject(s).toString());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			//System.out.println(Double.parseDouble(s));
		}
		*/

	}
	
	/*
	
		// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
				try{	listQuantidadesCadastradas.add(	df.format(
*
*
*
*/
}
