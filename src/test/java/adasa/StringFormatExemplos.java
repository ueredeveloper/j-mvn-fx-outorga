package adasa;

import java.text.DecimalFormat;
import java.text.ParseException;

public class StringFormatExemplos {
	
	

	public static void main(String[] args) {

		/*
		Double dd [] = {1.22, 12.34, 123.45, 1234.56, 12345.67, 123456.78,1234567.89, 12345678.99};	
		
		Double cc [] =  {1.22, 12.34, 123.45, 1234.56, 12345.67, 123456.78,1234567.89, 12345678.99};	
		
		
		DecimalFormat df = new DecimalFormat("#,##0.00");  
		DecimalFormat df2 = new DecimalFormat("###,###.###");  
		
		
		for(double d : dd){
		
		System.out.println(d);
		String s = df.format(d);  
		System.out.println(" ====== " + s);//imprime 2.637,64
		
		
		}
		
		for(double c : cc){
			
			String s = df2.format(c);
			System.out.println("dd2    " + s);
			
			try {
				System.out.println(df.parseObject(s));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		*/
		
System.out.println(  " -------------------------------------------------------------------------");

/*
		String str [] = {"12,5", "123,5", "5.000,25", "1,5", "12", "1", "500"};
	
		DecimalFormat df3 = new DecimalFormat("#,##0.00");  
		
		for (String s : str) {
			
			try {
				
				String ss = df3.parseObject(s).toString();
				
				System.out.println(s);
				
				System.out.println(ss);
				
				System.out.println("double parse " + Double.parseDouble(df3.parseObject(s).toString()));
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		*/

		DecimalFormat df3 = new DecimalFormat("#,##0.00");  
		
		String s1 = "15,56";
		String s2 = "1.000,00";
		
		Double result1 = 0.0;
		Double result2 = 0.0;
		
		
		try {
			result1 = Double.parseDouble(df3.parseObject(s1).toString());
			result2 = Double.parseDouble(df3.parseObject(s2).toString());
			
		} catch (NumberFormatException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(s1 + "-----------");
		System.out.println(result1);
		System.out.println(s2 + "-----------");
		System.out.println(result2);
		
		
		
		String str = "15,00".replaceAll(",00", "");
		
		System.out.println(str);
		
		System.out.println(str.replaceAll(",00", ""));

	}

}
