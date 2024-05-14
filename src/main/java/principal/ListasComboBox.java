package principal;

import java.util.ArrayList;
import java.util.List;

import dao.BaciasHidrograficasDao;
import dao.EnderecoDao;
import dao.UnidadeHidrograficaDao;
import entidades.BaciasHidrograficas;
import entidades.FormaCaptacao;
import entidades.GetterAndSetter;
import entidades.LocalCaptacao;
import entidades.MetodoIrrigacao;
import entidades.RA;
import entidades.SituacaoProcesso;
import entidades.SubSistema;
import entidades.SubtipoOutorga;
import entidades.TipoAto;
import entidades.TipoInterferencia;
import entidades.TipoOutorga;
import entidades.TipoPoco;
import entidades.UnidadeHidrografica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListasComboBox {
	
	EnderecoDao endDao = new EnderecoDao();
	
	ArrayList<Object> list;
	
	public static ObservableList<String> obsListRA = FXCollections.observableArrayList();
	public static ObservableList<String> obsListTipoInterferencia = FXCollections.observableArrayList();
	public static ObservableList<String> obsListTipoOutorga = FXCollections.observableArrayList();
	public static ObservableList<String> obsListSubtipoOutorga = FXCollections.observableArrayList();
	public static ObservableList<String> obsListTipoAto = FXCollections.observableArrayList();
	public static ObservableList<String> obsListSituacao = FXCollections.observableArrayList();
	
	public static ObservableList<String> obsListBacia = FXCollections.observableArrayList();
	public static ObservableList<String> obsListUH = FXCollections.observableArrayList();
	public static ObservableList<String> obsListLocalCaptacao = FXCollections.observableArrayList();
	public static ObservableList<String> obsListFormaCaptacao = FXCollections.observableArrayList();
	public static ObservableList<String> obsListMetodoIrrigacao = FXCollections.observableArrayList();
	
	public static ObservableList<String> obsListTipoPoco = FXCollections.observableArrayList();
	public static ObservableList<String> obsListSubsistema = FXCollections.observableArrayList();

	String [] variaveis = {
			
			"raNome",
			"tipoInterDescricao",
			"tipoOutorgaDescricao",
			"subtipoOutorgaDescricao",
			"tipoAtoDescricao",
			"situacaoProcessoDescricao",
			
			"baciaNome",
			"uhCodigo",
			"localCaptacaoDescricao",
			"formaCaptacaoDescricao",
			"metodoIrrigacaoDescricao",
			
			"tipoPocoDescricao",
			"subDescricao"
			
			};
	
	
	public static List<BaciasHidrograficas> listaBaciasHidrograficas;
	public static List<UnidadeHidrografica> listaUnidadesHidrograficas;

	@SuppressWarnings("unchecked")
	public void preencherListasComboBox () {
		
		ArrayList<ArrayList<Object>> arrayObjetos = new ArrayList<ArrayList<Object>>();
			
			ArrayList<Object> listObservableList = new ArrayList<>();
			
				listObservableList.add(obsListRA);
				listObservableList.add(obsListTipoInterferencia);
				listObservableList.add(obsListTipoOutorga);
				listObservableList.add(obsListSubtipoOutorga);
				listObservableList.add(obsListTipoAto);
				listObservableList.add(obsListSituacao);
				
				listObservableList.add(obsListBacia); // 6 bacias
				listObservableList.add(obsListUH);
				listObservableList.add(obsListLocalCaptacao);
				listObservableList.add(obsListFormaCaptacao);
				listObservableList.add(obsListMetodoIrrigacao);
				
				listObservableList.add(obsListTipoPoco);
				listObservableList.add(obsListSubsistema);
				
			ArrayList<Object> listEntidades = new ArrayList<>();
			
				listEntidades.add(new RA());
				listEntidades.add(new TipoInterferencia());
				listEntidades.add(new TipoOutorga());
				listEntidades.add(new SubtipoOutorga());
				listEntidades.add(new TipoAto());
				listEntidades.add(new SituacaoProcesso());
				
				listEntidades.add(new BaciasHidrograficas()); // 6
				listEntidades.add(new UnidadeHidrografica());
				listEntidades.add(new LocalCaptacao());
				listEntidades.add(new FormaCaptacao());
				listEntidades.add(new MetodoIrrigacao());
				
				listEntidades.add(new TipoPoco());
				listEntidades.add(new SubSistema());
				
				arrayObjetos.add(listObservableList);
	
				arrayObjetos.add(listEntidades);
			
			for (int i = 0; i<arrayObjetos.get(0).size(); i++)	 {		
		
				
				if (!((ObservableList<String>) arrayObjetos.get(0).get(i)).isEmpty()) {
					((ObservableList<String>)  arrayObjetos.get(0).get(i)).clear();
				}
				
			
				list = (ArrayList<Object>) endDao.listarObjeto(arrayObjetos.get(1).get(i));

				GetterAndSetter gs  = new GetterAndSetter();
			
				for (Object o: list) {
		
					((ObservableList<String>)arrayObjetos.get(0).get(i)).add(gs.callGetter(o, variaveis[i]));
					
				} // fim loop for Object o
		
			
			} // fim loop for arrayObjetos
			
			// inicializar tambem depois unidades hidrograficas e subsistemas
			listaBaciasHidrograficas = new BaciasHidrograficasDao().listarBaciasHidrograficas("");
			listaUnidadesHidrograficas = new UnidadeHidrograficaDao().listarUnidadesHidrograficas("");
			
	}
	
}
