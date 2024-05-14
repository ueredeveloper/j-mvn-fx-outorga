package pertimitirSomenteNumerosTextField;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import principal.Alerta;

public class PermitirNumeros extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		 primaryStage.setTitle("HBox Experiment 1");

	        TextField tfVazLD = new TextField();

	        HBox hbox = new HBox(tfVazLD);

	        Scene scene = new Scene(hbox, 200, 100);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        
	        
	        tfVazLD.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {

						/*  Nao permitir letras e caracteres especiais, exceto ponto e virgula [^.,] 
						 */
					
							if ( tfVazLD.getText().matches("(.*)[a-zA-Z](.*)")== true ) {
						
								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								// retirar caracter errado, como letra, virgula etc
								tfVazLD.setText(tfVazLD.getText().substring(0, tfVazLD.getText().length() - 1));

							}


					} // fim if length
				}
			});
		
	}

}
