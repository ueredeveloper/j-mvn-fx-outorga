package testeParecer;

import java.util.List;

import dao.DocumentoDao;
import entidades.Documento;

public class TesteListarParecer {
	
	
	
	public static void main (String[] args) {


		List<Documento> list = new DocumentoDao().listarParecerNotaTecnica("");
		
		
		
		for (Documento doc : list) {
			System.out.println(doc.getDocNumeracao());
		}
		
		
	}

}
