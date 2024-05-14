package adasa;

import entidades.GetterAndSetter;
import entidades.TipoInterferencia;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TesteMetodoListernerComboBox extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		ObservableList<String> olTipoInterferencia = FXCollections.observableArrayList(

				"Superficial",
				"Subterrânea" ,
				"Canal",
				"Caminhão Pipa",
				"Lançamento de Águas Pluviais",
				"Lançamento de Efluentes",
				"Barragem"

				);
		
		ComboBox<String> comboBox = new ComboBox<>();
		
		GetterAndSetter gs  = new GetterAndSetter();
		
		TipoInterferencia objeto_Relacinado = new TipoInterferencia();
		
		String [][] variaveis_Objeto_Relacionado = {
				
				{"raID", "raNome"},
				{"tipoInterID", "tipoInterDescricao"},
				{"tipoOutorgaID", "tipoOutorgaDescricao"},
				{"subtipoOutorgaID", "subtipoOutorgaDescricao"},
				{"tipoAtoID", "tipoAtoDescricao"},
				{"situacaoProcessoID", "situacaoProcessoDescricao"}
		
				};
		
		comboBox.setItems(olTipoInterferencia);
		
		comboBox.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number old_value, Number new_value) {

				gs.callSetter(objeto_Relacinado, variaveis_Objeto_Relacionado[1][0], ((Integer) new_value + 1));
				
				System.out.println("id " + objeto_Relacinado.getTipoInterID() + " e descricao " + objeto_Relacinado.getTipoInterDescricao());

			}
		});
		
		comboBox.getSelectionModel().selectedItemProperty().addListener( 
				(ObservableValue<? extends String> observable, String old_value, String new_value) ->

			gs.callSetter(objeto_Relacinado, variaveis_Objeto_Relacionado[1][1], new_value)

		);

		
		VBox vBox = new VBox();

		vBox.getChildren().addAll(comboBox);

		Scene scene = new Scene(vBox, 700, 500);
		stage.setScene(scene);
		stage.setTitle("TableView App");
		stage.show();
		
		
	}

}
