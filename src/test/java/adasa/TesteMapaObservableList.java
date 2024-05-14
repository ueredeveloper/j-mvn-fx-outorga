package adasa;

import java.util.ArrayList;
import java.util.List;

import dao.EnderecoDao;
import entidades.GetterAndSetter;
import entidades.RA;
import entidades.TipoInterferencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TesteMapaObservableList {
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		System.out.println("comeco");
		
		EnderecoDao endDao = new EnderecoDao();
		
		ObservableList<String> obsListRA = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoInterferencia = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListSubtipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoAto = FXCollections.observableArrayList();
		ObservableList<String> obsListSituacao = FXCollections.observableArrayList();
		
		RA regiaoAdministrativa = new RA();
		TipoInterferencia tipoInterferencia = new TipoInterferencia();
	
	
		String[][] variaveis = {
				
				{"raID","raNome"},
				{"tipoInterID", "tipoInterDescricao"},
				{"tipoOutorgaID", "tipoOutorgaDescricao"},
				{"subtipoOutorgaID", "subtipoOutorgaDescricao"},
				{"tipoAtoID", "tipoAtoDescricao"},
				{"situacaoProcessoID", "situacaoProcessoDescricao"}
				
				};
		
		
		ArrayList<ArrayList<Object>> arrayObjetos = new ArrayList<ArrayList<Object>>();
		
			ArrayList<Object> listObservableList = new ArrayList<>();
				listObservableList.addAll(obsListRA);
				
			ArrayList<Object> listEntidades = new ArrayList<>();
				listEntidades.add(regiaoAdministrativa);		
		
		arrayObjetos.add(listObservableList);
		
		arrayObjetos.add(listEntidades);
	
		List<Object> list;
		
		for (int i=0;i<arrayObjetos.get(0).size();i++) {
	
			list = endDao.listarObjeto(arrayObjetos.get(1).get(i));
			
			GetterAndSetter gs  = new GetterAndSetter();
			
			for (Object o: list) {
				
				((ObservableList<String>) arrayObjetos.get(0).get(i)).add(gs.callGetter(o, variaveis[0][i]));
				
				System.out.println(obsListRA.get(i));
				
			}
			
			
			
			
			
		}
	
	
		
		

	}
	
	
	public void preencherListasComboBox () {
		
	
		
		/*
		
		for (int i=0;i<mapa.size();i++) {
			
			if (!mapa.get(i).isEmpty()) {
				arrayObservableList.get(i).clear();
			}
			
			list = (ArrayList<Object>) endDao.listarObjeto(obj);
			
		}
		
		
		
		list = (ArrayList<Object>) endDao.listarObjeto(obj);
		
		GetterAndSetter gs  = new GetterAndSetter();
		
		for (Object o: list) {
			
			obsList.add(gs.callGetter(o, strVarivelTabelaDescricao));
			
		}
		*/
		
		
		
	}
	
	
}
		
	
	
	
