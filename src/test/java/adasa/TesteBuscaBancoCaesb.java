package adasa;

import java.util.List;

import dao.BancoCaesbDao;
import entidades.BancoCaesb;

public class TesteBuscaBancoCaesb {
	
	public static void main (String[] args) {

		BancoCaesbDao bcDao = new BancoCaesbDao();
		
		List<BancoCaesb> list = bcDao.listarBancoCaesb("");
		
		
		for (BancoCaesb b : list) {
			
			
			System.out.println(b.getBcCPFCNPJ());
			
		}
		
		
		
	}

}
