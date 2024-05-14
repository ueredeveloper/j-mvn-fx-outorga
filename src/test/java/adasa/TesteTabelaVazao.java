package adasa;

import java.util.List;

import dao.InterferenciaDao;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.Subterranea;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.FinalidadeRequerida;

public class TesteTabelaVazao {

	public static void main(String[] args) throws Exception {
	
		//inserirInterferenciaSubterranea();
		
		listarInterferencia();
	
	}
	
	public static void listarInterferencia () throws Exception {

		InterferenciaDao interDao = new InterferenciaDao();
		
		List<Interferencia> list = interDao.listInterferencia("");
		
		System.out.println("tamanho da lista " + list.size());
		
		for(Interferencia i : list) {
			
			System.out.println("inter ID " + i.getInterID());
			
			for (Finalidade f : ((Subterranea) i).getFinalidades()) {
				
				if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
			
				//	System.out.println(((FinalidadeAutorizada) f).getVaFinalidade1());
				}
					
				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					
						System.out.println(((FinalidadeRequerida) f).getFrFinalidade1());
				}
				
				
			}

		
		}
	}

	public static void inserirInterferenciaSubterranea() {
		
		Subterranea sub = new Subterranea();
		
		Endereco end = new Endereco ();
		end.setEndID(1);
		
		sub.setInterEnderecoFK(end);
	
		sub.setInterDDLatitude(-55.99999999);
		
		FinalidadeRequerida vr = new FinalidadeRequerida();
			vr.setFrFinalidade1("contrução civil");
			vr.setFinInterferenciaFK(sub);
		
		FinalidadeAutorizada va = new FinalidadeAutorizada();
			//va.setVaFinalidade1("irrigação paisagística");
			va.setFinInterferenciaFK(sub);
		
			sub.getFinalidades().add(va);
			sub.getFinalidades().add(vr);
		
		InterferenciaDao interDao = new InterferenciaDao();
		
		interDao.salvaInterferencia(sub);
	
	}
}
