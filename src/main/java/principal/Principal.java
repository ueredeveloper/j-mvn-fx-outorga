package principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe Principal
 * @author fabricio.barrozo
 * @version 1.0
 *
 */

/*
 * backup antes de fazer reinstalação do eclipse 
 */
public class Principal extends Application {
	
	// teste
	
		public Parent rootNode;
		
		/** Método principal - Main
		 * 
		 * @param args
		 */
	    public static void main(String[] args) {
	    	System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	        Application.launch(args);
	        
	    }
	    
	    /** 
	     * Método de leitura do arquivo fxml da classe principal
	     */
	    public void init() throws Exception {
	      
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/principal/TelaLogin.fxml"));
	        rootNode = fxmlLoader.load();
	        
	    }

	    /** 
	     * Método de inicialização do JavaFX
	     */
		public void start(Stage stage) throws Exception {
			
			Scene scene = new Scene (rootNode);
			
	        stage.setScene(scene);
	        
	        stage.setTitle("Seja Bem Vindo!!!"); 
	        
	        stage.show();
		
		}

}
