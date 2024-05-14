package adasa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TesteWebViewMapaShapeAPartirDeUmPonto extends Application{

	public static void main(String[] args) {
	 launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("JavaFX WebView Example");

        WebView webView = new WebView();
        webView.setPrefSize(900, 900);
        
        WebEngine we = webView.getEngine();

       // webView.getEngine().load("http://google.com");
        
        we.load(getClass().getResource("/html/ComUmPontoCriarUmaShapeQuadrada.html").toExternalForm()); // originalMap

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();
        
		
	}

}
