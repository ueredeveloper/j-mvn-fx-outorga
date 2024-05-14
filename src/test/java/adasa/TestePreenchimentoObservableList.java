package adasa;

import java.util.ArrayList;
import java.util.List;

import dao.EnderecoDao;
import entidades.GetterAndSetter;
import entidades.RA;
import entidades.SituacaoProcesso;
import entidades.SubtipoOutorga;
import entidades.TipoAto;
import entidades.TipoInterferencia;
import entidades.TipoOutorga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestePreenchimentoObservableList {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		
		EnderecoDao endDao = new EnderecoDao();
		
		ArrayList<Object> list;
		
		ObservableList<String> obsListRA = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoInterferencia = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListSubtipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoAto = FXCollections.observableArrayList();
		ObservableList<String> obsListSituacao = FXCollections.observableArrayList();
		
		RA regiaoAdministrativa = new RA();
		TipoInterferencia tipoInterferencia = new TipoInterferencia();
		TipoOutorga tipoOutorga = new TipoOutorga();
		SubtipoOutorga subtipoOutorga = new SubtipoOutorga();
		TipoAto tipoAto = new TipoAto();
		SituacaoProcesso situacaoProcesso = new SituacaoProcesso();
		
		
		String [] variaveis = {
				
				"raNome",
				"tipoInterDescricao",
				"tipoOutorgaDescricao",
				"subtipoOutorgaDescricao",
				"tipoAtoDescricao",
				"situacaoProcessoDescricao"
		
				};

		
		ArrayList<ArrayList<Object>> arrayObjetos = new ArrayList<ArrayList<Object>>();
			
			ArrayList<Object> listObservableList = new ArrayList<>();
				listObservableList.add(obsListRA);
				listObservableList.add(obsListTipoInterferencia);
				listObservableList.add(obsListTipoOutorga);
				listObservableList.add(obsListSubtipoOutorga);
				listObservableList.add(obsListTipoAto);
				listObservableList.add(obsListSituacao);
				
			ArrayList<Object> listEntidades = new ArrayList<>();
				listEntidades.add(regiaoAdministrativa);
				listEntidades.add(tipoInterferencia);
				listEntidades.add(tipoOutorga);
				listEntidades.add(subtipoOutorga);
				listEntidades.add(tipoAto);
				listEntidades.add(situacaoProcesso);
				
	
						arrayObjetos.add(listObservableList);
	
						arrayObjetos.add(listEntidades);
						
						System.out.println("size" + arrayObjetos.get(0).size());
						System.out.println("size" + arrayObjetos.get(1).size());
						
		
			for (int i = 0; i<arrayObjetos.get(0).size(); i++)	 {		
		
				
				if (!((ObservableList<String>) arrayObjetos.get(0).get(i)).isEmpty()) {
					((ObservableList<String>)  arrayObjetos.get(0).get(i)).clear();
				}
				
			
			list = (ArrayList<Object>) endDao.listarObjeto(arrayObjetos.get(1).get(i));
			
			GetterAndSetter gs  = new GetterAndSetter();
			
			for (Object o: list) {
	
				((ObservableList<String>)arrayObjetos.get(0).get(i)).add(gs.callGetter(o, variaveis[i]));
				
			}
		
			
			}
			
			/*
			for (String s: obsListRA) {
				System.out.println(s);
			}*/
			

	}

}
