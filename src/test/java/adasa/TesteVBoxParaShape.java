package adasa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TesteVBoxParaShape extends Application{

	public static void main(String[] args) {
		
		launch(args);
		

	}
	


	@Override
	public void start(Stage primaryStage) throws Exception {


		
		primaryStage.setTitle("HBox Experiment 1");

		VBox vBoxShape = new VBox();
		vBoxShape.setPrefSize(85, 365);
		vBoxShape.setLayoutX(842);
		vBoxShape.setLayoutY(8);

		Button btnShape = new Button("shape");
		btnShape.setPrefSize(85, 25);
		
		Button btnLimparShape = new Button("limpar shape");
		btnLimparShape.setPrefSize(85, 25);
		
		Button btnLimparMapa = new Button("limpar mapa");
		btnLimparMapa.setPrefSize(85, 25);

		vBoxShape.getChildren().addAll(btnShape, btnLimparShape, btnLimparMapa);

        Scene scene = new Scene(vBoxShape);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
	}

}
