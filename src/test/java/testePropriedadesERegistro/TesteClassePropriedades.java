package testePropriedadesERegistro;

import java.util.Properties;

public class TesteClassePropriedades {

	public static void main(String[] args) {

		
		Properties pro = new Properties();
		
		pro.setProperty("str", "strAqui");
		

		pro.setProperty("str", "strAqui");
		
		System.out.println(pro.get("str"));
		
		for (Object p : pro.values()) {
			
			System.out.println(p);
		}
		
		
		
		
		

	}

}
