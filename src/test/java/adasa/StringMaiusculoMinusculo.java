package adasa;

import util.Tipografia;

public class StringMaiusculoMinusculo {

	public static void main(String[] args) {

		//StringMaiusculoMinusculo.toLowerUperCase ("EDINEI BATISTA DE SOUZA");
		
	
		

		
		for (int i = 0; i<1000;i++) {
			
			Tipografia t = new Tipografia();
			String str = 		t.colocarCadaPalavraEmMaiusculo("RUA DOS REMÉDIOS, DE José bONIFÁCIO souza");
			
			System.out.println(str);
		}
		

	}
	
	public static String toLowerUperCase (String str) {
		
		
		String[] textoSeparado = str.split(" ");
		
		String ss = ""; 
		
		for (String s : textoSeparado) {
			
			String strToUper = s.substring(0, 1).toUpperCase() ; 
		
			strToUper = strToUper + (s.substring(1, s.length()).toLowerCase()) ;
			
			ss = ss + " " + strToUper;
		
		}

		System.out.println(ss.substring(1, ss.length()));
		
		return null;
		
	}
	
	
	/*
			System.out.println("String: " + s);
			
			String strToUper = s.substring(0, 1).toUpperCase() ; 
			
			System.out.println(" ///// strToUper 1: " + strToUper);
			
			strToUper = strToUper + (s.substring(1, s.length()).toLowerCase()) ;
			
			System.out.println("---- strToUper 2: " + strToUper);
			
			ss = ss + " " + strToUper;
	
	 */
	

}


