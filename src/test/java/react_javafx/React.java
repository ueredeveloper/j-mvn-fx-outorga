package react_javafx;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class React extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		BufferedReader br = new BufferedReader( new FileReader("C:\\Users\\fabricio.barrozo\\eclipse-workspace\\adasa6\\src\\test\\java\\react_javafx\\react.html"));
	     

		String html = "";

        String s = "";
        while (null != ((s = br.readLine()))) {
            html+=s;
        }
        
		stage.setTitle("Exemplo Leitura HTML");

        WebView webView = new WebView();

        webView.getEngine().loadContent(html);

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        stage.setScene(scene);
        stage.show();
		
	}

}
