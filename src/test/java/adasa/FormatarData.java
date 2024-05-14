package adasa;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.DocumentoDao;
import entidades.Documento;

public class FormatarData {

	public static void main(String[] args) {


		DocumentoDao docDao = new DocumentoDao();
		
		List<Documento> list = new ArrayList<>();
		
		list = docDao.listarDocumentos("");
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        
		for (int i=0;i<list.size();i++) {
			//list.get(i).getDocDataRecebimento()
			
			try {
			String date = DATE_FORMAT.format(list.get(i).getDocDataRecebimento());
			System.out.println(date);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			
			
		}

	}

}
