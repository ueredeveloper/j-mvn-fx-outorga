package adasa;


import java.util.ArrayList;

import dao.EnderecoDao;
import entidades.PreenchimentoComboBox;
import entidades.RA;
import entidades.SituacaoProcesso;
import entidades.SubtipoOutorga;
import entidades.TipoAto;
import entidades.TipoInterferencia;
import entidades.TipoOutorga;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestePreenchimentoAutomaticoTabelas extends Application {

	String strRA = "Plano Piloto";
	int intRA = 1;

	RA ra = new RA();
	
	TipoInterferencia tipoInterferencia = new TipoInterferencia();
	TipoOutorga tipoOutorga = new TipoOutorga();
	SubtipoOutorga subtipoOutorga = new SubtipoOutorga();
	TipoAto tipoAto = new TipoAto();
	SituacaoProcesso situacaoProcesso = new  SituacaoProcesso();


	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {

		ComboBox<String> cbRA = new ComboBox<>();

		ComboBox<String> cbTipoInterferencia = new ComboBox<>();
		ComboBox<String> cbTipoOutorga = new ComboBox<>();
		ComboBox<String> cbSubtipoOutorga = new ComboBox<>();
		ComboBox<String> cbTipoAto = new ComboBox<>();
		ComboBox<String> cbSituacao = new ComboBox<>();

		Button btn = new Button("clique");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	
				System.out.println(ra.getRaID());
				System.out.println(ra.getRaNome());
				
				System.out.println(tipoInterferencia.getTipoInterID());
				System.out.println(tipoInterferencia.getTipoInterDescricao());
				
				System.out.println(tipoOutorga.getTipoOutorgaID());
				System.out.println(tipoOutorga.getTipoOutorgaDescricao());
				
				System.out.println(subtipoOutorga.getSubtipoOutorgaID());
				System.out.println(subtipoOutorga.getSubtipoOutorgaDescricao());
				
				System.out.println(tipoAto.getTipoAtoID());
				System.out.println(tipoAto.getTipoAtoDescricao());
				
				System.out.println(situacaoProcesso.getSituacaoProcessoID());
				System.out.println(situacaoProcesso.getSituacaoProcessoDescricao());
	
			}
		});

		ObservableList<String> obsListRA = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoInterferencia = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListSubtipoOutorga = FXCollections.observableArrayList();
		ObservableList<String> obsListTipoAto = FXCollections.observableArrayList();
		ObservableList<String> obsListSituacao = FXCollections.observableArrayList();

		PreenchimentoComboBox pre = new PreenchimentoComboBox();

		pre.preencherComboBox (new EnderecoDao(), obsListRA, new ArrayList<>() , "raID", "raNome" , ra, cbRA);
		pre.preencherComboBox (new EnderecoDao(), obsListTipoInterferencia, new ArrayList<>() , "tipoInterID", "tipoInterDescricao" , tipoInterferencia, cbTipoInterferencia);
		pre.preencherComboBox (new EnderecoDao(), obsListTipoOutorga, new ArrayList<>() , "tipoOutorgaID", "tipoOutorgaDescricao" , tipoOutorga, cbTipoOutorga);
		pre.preencherComboBox (new EnderecoDao(), obsListSubtipoOutorga, new ArrayList<>() , "subtipoOutorgaID", "subtipoOutorgaDescricao" , subtipoOutorga, cbSubtipoOutorga);
		pre.preencherComboBox (new EnderecoDao(), obsListTipoAto, new ArrayList<>() , "tipoAtoID", "tipoAtoDescricao" , tipoAto, cbTipoAto);
		pre.preencherComboBox (new EnderecoDao(), obsListSituacao, new ArrayList<>() , "situacaoProcessoID", "situacaoProcessoDescricao" , situacaoProcesso, cbSituacao);
		
		VBox vBox = new VBox();

		vBox.getChildren().addAll(cbRA, cbTipoInterferencia, cbTipoOutorga, cbSubtipoOutorga, cbTipoAto, cbSituacao, btn);

		Scene scene = new Scene(vBox, 700, 500);
		stage.setScene(scene);
		stage.setTitle("TableView App");
		stage.show();

	}


}

/*
cbTipoInterferencia.setItems(olTipoInterferencia);
cbTipoOutorga.setItems(olTipoOutorga);
cbSubtipoOutorga.setItems(olSubtipoOutorga);
cbTipoAto.setItems(olTipoAto);
cbSituacao.setItems(olSituacao);
 */



/*
cbRA.getSelectionModel()
	.selectedItemProperty()
	.addListener( 
	(ObservableValue<? extends String> observable, String oldValue, String newValue) ->

	 strRA = newValue

);

cbRA.getSelectionModel().selectedIndexProperty().addListener(new
        ChangeListener<Number>() {
	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
		Number old_value, Number new_value) {

		intRA = (Integer) new_value + 1;

		//System.out.println("RA, Id: " + intRA);

    }
 });
 */


/*
EnderecoDao endDao = new EnderecoDao();

ObservableList<String> obsList = FXCollections.observableArrayList();


if (!obsList.isEmpty()) {
	obsList.clear();
}
List <RA> list = endDao.listarRA();

for (RA ra: list) {

	obsList.add(ra.getRaNome());

	System.out.println(ra.getRaNome());
 */



/*
cbRA.valueProperty().addListener(new ChangeListener<String>() {
    @Override public void changed(ObservableValue ov, String old_value, String new_value) {

        System.out.println("RA, nome: " + new_value);

    }    
});
 */


/*
	ModelosDao mDao = new ModelosDao();

	mDao.obterModeloHTMLPorID(1);

	System.out.println("conteúdo html " + mDao.obterModeloHTMLPorID(1).getModConteudo());
 */


/*
EnderecoDao endDao = new EnderecoDao();

	ObservableList<String> obsList = FXCollections.observableArrayList();


	if (!obsList.isEmpty()) {
		obsList.clear();
	}
	List <RA> list = endDao.listarRA();

	for (RA ra: list) {

		obsList.add(ra.getRaNome());

		//System.out.println(ra.getRaNome());
	}

	cbRA.setItems(obsList);
 */

/*
EnderecoDao endDao = new EnderecoDao();

	ObservableList<String> obsList = FXCollections.observableArrayList();

	if (!obsList.isEmpty()) {
		obsList.clear();
	}

	List <Object> list = endDao.listarObjeto(new BaciasHidrograficas());

	GetterAndSetterTabelas gs  = new GetterAndSetterTabelas();

	gs.obterTabela(obsList, list, "baciaNome");


	//gs.obterTabela (obsList, list, "baciaNome");

	cbRA.setItems(obsList);
 */
