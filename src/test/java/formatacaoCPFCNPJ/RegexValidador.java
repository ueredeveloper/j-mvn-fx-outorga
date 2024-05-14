package formatacaoCPFCNPJ;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidador {

	public static void main(String[] args) {
		
		
		String cpfs [] = {"69974926149", "699.749.261-51" , "07.007.955/0001-10", "07007955000110"};

		Pattern padraoCPF = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}"); 
		//                                [0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2}
		
		Pattern padraoCNPJ = Pattern.compile("[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}-?[0-9]{2}"); 
		//                                [0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2}

		
		for (String s : cpfs) {
			
			Matcher matcher = padraoCNPJ.matcher(s);
			
			System.out.println(matcher + " e " + matcher.matches());
			
			/*
			s = s.replace('.',' ');
	        s = s.replace('-',' ');
	        s = s.replaceAll(" ","");
	        s = s.replaceAll("/","");
	        */
			s=s.replaceAll("\\D","");
	        
	        System.out.println(s);
			
		}
		
		
		 
		

	}

}
