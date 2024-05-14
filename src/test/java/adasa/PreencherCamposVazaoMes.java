package adasa;

public class PreencherCamposVazaoMes {

	public static void main(String[] args) {
		
		
		
		String s = "1.000,00";
		
		String strVazaoLD [] = new String[12];
		
		int c = 0;
		
		for (c=0; c<5;c++) {
		
			
			System.out.println(c + "------------------------");
			
		for (int i = 0; i<12;i++) {
			
			// preencher todos os meses com valores iguais
			if (c%2==0) {
				strVazaoLD [i] = s;
			}
			
			// preencher retirando vazao dos meses de jan fev mar nov dez
			else {
				
				if (i == 0 || i == 1 || i == 2 || i == 10 || i == 11) {
					strVazaoLD [i] = "0";
				} else {
					strVazaoLD [i] = s;
				}
			}
	
			System.out.println(strVazaoLD [i]);
		} // fim for 12
		
		
		}
			


	}

}
