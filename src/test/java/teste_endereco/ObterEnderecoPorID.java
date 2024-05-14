package teste_endereco;

import dao.EnderecoDao;
import entidades.Endereco;

public class ObterEnderecoPorID {

	public static void main(String[] args) {

		
		Endereco end = new EnderecoDao().obterEnderecoPorID(10);
		
		
		System.out.println(end.getEndLogradouro());
	
		
		
	}

}
