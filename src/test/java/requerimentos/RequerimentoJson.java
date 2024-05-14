package requerimentos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entidades.Endereco;
import entidades.SubSistema;
import entidades.Subterranea;
import entidades.Usuario;

public class RequerimentoJson {

	public static void main(String[] args) {


		Usuario us = new Usuario();
				us.setUsID(1);
				us.setUsNome("João Gilberto");
				us.setUsCPFCNPJ("111222333-56");
				us.setUsLogadouro("Rua dos Brejos");
				us.setUsRA("Sobradinho");
				us.setUsCEP("72130-050");
				us.setUsCidade("Brasília");
				us.setUsEstado("DF");
				us.setUsTelefone("3354-5687");
				us.setUsCelular("9 9562-5689");
				us.setUsEmail("joaogil@gmail.com");
				us.setUsRepresentante("Aqualit - Engenharia de Poços");
				us.setUsRepresentanteTelefone("3365-4578");
				
				Endereco end;
				end = new Endereco(2, "Rua dos Novaes, Casa 10");
				
				Subterranea sub;
				// INTERFERENCIA 1
				sub = new Subterranea();
				sub.setInterID(1);
				sub.setInterDDLatitude(-15.66);
				sub.setInterDDLongitude(-47.55);
				sub.setSubCaesb("sim");
				sub.setSubEstatico("150");
				sub.setSubDinamico("120");
				sub.setSubVazaoTeste("5.000");
				
				SubSistema subsistema = new SubSistema(1, "P1");
						
				sub.setSubSubSistemaFK(subsistema);
				sub.setSubVazaoOutorgada(4000.0);
				sub.setSubProfundidade("180");
				sub.setSubCod_plan("125_TRF");
				
				
				end.getInterferencias().add(sub);
				
				// INTERFERENCIA 2
				sub = new Subterranea();
				sub.setInterID(2);
				sub.setInterDDLatitude(-16.66);
				sub.setInterDDLongitude(-48.55);
				
				
				end.getInterferencias().add(sub);
			
				us.getEnderecos().add(end);
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				//Gson gson = new Gson();
		
				//System.out.println(interferencia.get);
		
				String jsonInString = gson.toJson(us);
		
		System.out.println(jsonInString);
		

	}

}
