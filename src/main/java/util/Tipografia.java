package util;

public class Tipografia {
	
	
	public static String colocarCadaPalavraEmMaiusculo (String str) {
		
		
		String[] strPalavras = str.split(" ");
		
		String strResultadoFinal = ""; 
		
		for (String s : strPalavras) {
			
			String strToUper = s.substring(0, 1).toUpperCase() ; 
		
			strToUper = strToUper + (s.substring(1, s.length()).toLowerCase()) ;
			
			strResultadoFinal = strResultadoFinal + " " + strToUper;
			
			
		
		}
		
		strResultadoFinal = strResultadoFinal.substring(1, strResultadoFinal.length());

		System.out.println(strResultadoFinal);
		
		return strResultadoFinal;
		
	}

}
