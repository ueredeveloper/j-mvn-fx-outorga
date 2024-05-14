package controladores.principal;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class ControladorBancosDiversos {
	
	@FXML Pane pBancosDiversos;
	@FXML TabPane tpBancosDiversos = new TabPane();
	
	@FXML Tab tabBancoAccess;
	@FXML Tab tabBancoCaesb;

	
	@FXML 
    private void initialize() {
		
		tpBancosDiversos.prefWidthProperty().bind(pBancosDiversos.widthProperty());
		tpBancosDiversos.prefHeightProperty().bind(pBancosDiversos.heightProperty());

		abrirTab (new Pane() , new ControladorBancoAccess(), "/fxml/bancos_diversos/BancoAccess.fxml", tabBancoAccess );
		abrirTab (new Pane() , new ControladorBancoCaesb(), "/fxml/bancos_diversos/BancoCaesb.fxml", tabBancoCaesb );

	}
	
	public void abrirTab (Pane p , Object o, String strFXML, Tab t ) {
		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(strFXML));
		
		loader.setRoot(p);
		loader.setController(o);
		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("erro na abertura do pane");
			e.printStackTrace();
		}
		
		p.minWidthProperty().bind(pBancosDiversos.widthProperty());
		p.minHeightProperty().bind(pBancosDiversos.heightProperty());
		
		t.setContent(p);
		
	}
}
