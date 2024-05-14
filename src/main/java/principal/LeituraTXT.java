package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeituraTXT {
	
	
	public String lerArquivo () throws IOException {
		
		String str;
		
		FileReader arq = new FileReader("C:/Users/fabricio.barrozo/Documents/2018BancoDeDados/adasa.txt"); // /src/arquivos/arquivo.txt
		
		BufferedReader lerArq = new BufferedReader(arq);
		 
		str = lerArq.readLine(); // lê a primeira linha
		
		arq.close();
	    
		return str;
	}

}
