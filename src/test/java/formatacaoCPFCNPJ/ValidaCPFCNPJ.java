package formatacaoCPFCNPJ;

public class ValidaCPFCNPJ {
	
	   private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	   private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	   private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	   }
	   public static boolean isValidCPF(String cpf) {
		   
		   cpf=cpf.replaceAll("\\D","");
		   
		   if ((cpf==null) || (cpf.length()!=11) ||
									                cpf.equals("00000000000") || cpf.equals("11111111111") ||
									                cpf.equals("22222222222") || cpf.equals("33333333333") ||
									                cpf.equals("44444444444") || cpf.equals("55555555555") ||
									                cpf.equals("66666666666") || cpf.equals("77777777777") ||
									                cpf.equals("88888888888") || cpf.equals("99999999999")) 	return false; 
	      
		   Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
	      
		   Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
	      
		   																	return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	   }
	   public static boolean isValidCNPJ(String cnpj) {
		   
		  cnpj=cnpj.replaceAll("\\D","");
		   
	      if ((cnpj==null)||(cnpj.length()!=14) || cnpj.equals("00000000000000") 
	    		  
	    		  ) return false;
	      Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
	      Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
	      return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	   }
	   
	   
	   public static void main(String[] args) {
		   
		   String strCPFCNPS [] = {
				   "99988877714", "206.221.329-89", "656.511.179-03", "681.891.593-05", "356.788.355-05", "111 111 111 11", "222 222 222 22", "333 333 333 33",
				   "444 444 444 44", "555 555 555 55", "666 666 666 66", "777 777 777 77", "888 888 888 88", "999 999 999 99",
				   "853.672.138-39",
				   
				   "30.999.256/0001-15", "15.562.867/0001-08", "11.216.575/0001-62", "11 111 111 1111 11",
				   "00 000 000 0000 00",
				   "11 111 111 1111 11",
				   "22 222 222 2222 22",
				   "33 333 333 3333 33",
				   "44 444 444 4444 44",
				   "55 555 555 5555 55",
				   "66 666 666 6666 66",
				   "77 777 777 7777 77",
				   "88 888 888 8888 88",
				   "99 999 999 9999 99",
		   
		   };
		   
		   for (String s : strCPFCNPS) {
			   
			  // s=s.replaceAll("\\D","");
			   
			  System.out.printf("CPF Valido: %s resultado -  %s \n", s, isValidCPF(s));
			   
			  // System.out.printf("CNPJ Valido:%s resultado -  %s \n", s, isValidCNPJ(s));
			   
		   }
		   
		   for (int i=0; i<10;i++) {
			   System.out.println("\"" + i + "" + i + " " +  i + "" +i +i +"" + " " +  i + i + i + " " +  i + i + i + i + " " +  i + i + "\"," );
		   }
	     
	      
	   }
	   
	   
	   
	   
	   
	}
