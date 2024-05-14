package testeListarAtosOutorga;

import java.util.List;

import dao.DocumentoDao;
import entidades.Documento;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.Interferencia;

public class ListarAtosOutorga {

	public static void main(String[] args) {
		
		

		List<Documento> list = new DocumentoDao().listarAtosOutorga("25041456");
		
		
		for (Documento doc : list) {
			 System.out.println("doc sei " + doc.getDocSEI());
			 
			 System.out.println("doc Processo Principal SEI " + doc.getDocProcessoFK().getProSEI());
			 
			 System.out.println("doc endereco " + doc.getDocEnderecoFK().getEndLogradouro());
			 
			 for (Interferencia i : doc.getDocEnderecoFK().getInterferencias()) {
				 
				 System.out.println("doc get interferencias " + i.getInterDDLatitude() + ", " + i.getInterDDLongitude());
				 
				 for (Finalidade f : i.getFinalidades()) {
					 
					if (f.getClass().getName().equals("entidades.FinalidadeAutorizada")) {
						
						 System.out.println( ((FinalidadeAutorizada) f).getFaFinalidade1() );
					}
					 
				 } // fim for
				 
				 System.out.println(doc.getDocEnderecoFK().getEndUsuarioFK().getUsNome());
			 }
			
		}

	}

}
