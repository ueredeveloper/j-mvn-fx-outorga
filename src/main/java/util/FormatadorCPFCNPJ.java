package util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatadorCPFCNPJ {
	

	@SuppressWarnings("unused")
	public String formatCnpj(String strPessoaTipo, String strCPFCNPJ) throws ParseException {
		
		MaskFormatter mascara;
		
		String strMascara = null;
		
		if (strPessoaTipo.equals("Física")) {
			
			try {
			
				mascara = new MaskFormatter("###.###.###-##");
				mascara.setValueContainsLiteralCharacters(false);
				
				strMascara = mascara.valueToString(strCPFCNPJ.replaceAll("\\D",""));
				
		       // System.out.println("CPF : " + strMascara );
		        
		        
		    } catch (ParseException ex) {
		    	
		    	ex.printStackTrace();
		      
		    }
			
			
		} else {
			
			
			try {
				
				mascara = new MaskFormatter("##.###.###/####-##");
				
				mascara.setValueContainsLiteralCharacters(false);
				
				strMascara = mascara.valueToString(strCPFCNPJ.replaceAll("\\D",""));
				
		       // System.out.println("CNPJ : " +strMascara);
		        
		        
		    } catch (ParseException ex) {
		    	
		    	ex.printStackTrace();
		     
		    }
			
		}
		return strMascara;
	    
	}

	

}
