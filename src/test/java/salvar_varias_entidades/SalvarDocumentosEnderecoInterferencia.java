package salvar_varias_entidades;

import dao.EnderecoDao;
import dao.UsuarioDao;
import entidades.Documento;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.RA;
import entidades.Usuario;

public class SalvarDocumentosEnderecoInterferencia {

	public static void main(String[] args) {


		Documento doc = new Documento();
		doc.setDocTipo("Requerimento");
		doc.setDocNumeracao("1234 - Teste");
		
		
		Endereco end = new Endereco();
		end.setEndLogradouro("Rua 04 - Teste");
		
		RA ra = new RA();
		
		
		Interferencia inter = new Interferencia();
		
		inter.setInterDDLatitude(-15.0);
		inter.setInterDDLongitude(-47.0);
		
		Usuario us = new Usuario();
		
		us.setUsNome("Teste persistencia doc us  end int ");
		
		
		end.getDocumentos().add(doc);
		end.getInterferencias().add(inter);
		
		UsuarioDao usDao = new UsuarioDao();
		usDao.salvarUsuario(us);
		
		
		end.setEndUsuarioFK(us);
		
		
		EnderecoDao endDao = new EnderecoDao();
		
		endDao.salvarEndereco(end);
		
		System.out.println("ok");
		

	}

}
