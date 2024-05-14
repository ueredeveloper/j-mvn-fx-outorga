package adasa;

import java.util.List;

import dao.DocumentoDao;
import entidades.Documento;
import entidades.NotaTecnica;

public class TesteNotaTecnica {

	public static void main(String[] args) {

/*
			NotaTecnica nt = new NotaTecnica ();
			
			nt.setDocNumeracao("123456789");
			nt.setDocSEI("9876543211")*/
			
			DocumentoDao docDao = new DocumentoDao();
			
			//docDao.salvarDocumento(nt);
			
			
			
			List<Documento> list = docDao.listarParecerNotaTecnica("");
			
			for (Documento doc : list) {
				System.out.println(doc.getDocNumeracao());
			}

	}

}
