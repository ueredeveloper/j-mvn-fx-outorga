package adasa;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import entidades.Documento;

public class TesteDataPorExtenso {

	public static void main(String[] args) {

		/* Site: https://respostas.guj.com.br/28068-data-por-extenso-em-java-xx-de-xxxx-de-xxxx */
		
		Documento doc = new Documento();
		
		doc.setDocDataCriacao(Date.valueOf("2019-04-02"));
		
		System.out.println(doc.getDocDataCriacao());
		
		// formatador
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
		
		// string com a data:  Terça-feira, 2 de Abril de 2019
		String dataExtenso = formatador.format(doc.getDocDataCriacao());
		
		System.out.println(dataExtenso);
		
		// retirar o dia da semana:  2 de Abril de 2019
		int index  = dataExtenso.indexOf(","); // inicio da substring
	
		 System.out.println(dataExtenso.substring(index + 2));
		 
		 
		
		
		

	}

}
