package formatacaoCPFCNPJ;

import java.text.ParseException;

public class MascaraStringParaParecer {
	
	public static void main (String [] args) {
		
		System.out.println("ola mundo");
		
		
		CPFCNPJFormat ccFormato = new CPFCNPJFormat();
		
		String strCPF = "22255588845";
		
		try {
			System.out.println(ccFormato.formatCnpj("Física", strCPF));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
