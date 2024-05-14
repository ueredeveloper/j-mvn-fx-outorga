package adasa;

import dao.ModelosDao;
import entidades.ModelosHTML;

public class TesteModeloHTMLporID {

	public static void main(String[] args) {


		ModelosDao mDao = new ModelosDao();
		
		
		ModelosHTML modelo = new  ModelosHTML();
		
		modelo = mDao.obterModeloHTMLPorID(3);
		
		System.out.println(modelo.getModConteudo());

	}

}
