package controladores.principal;

import java.io.IOException;

import entidades.Documento;
import entidades.Endereco;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class ControladorOutorga {

	@FXML Pane pOutorga;
	@FXML TabPane tpOutorga = new TabPane();

	@FXML Tab tpTabDocumento;
	String strDem = "/fxml/principal/TabDocumento.fxml";

	@FXML Tab tpTabEndereco;
	String strEnd = "/fxml/principal/TabEndereco.fxml";

	@FXML Tab tpTabInterferencia;
	String strInt = "/fxml/principal/TabInterferencia.fxml";

	@FXML Tab tpTabUsuario;
	String strUs = "/fxml/principal/TabUsuario.fxml";

	@FXML Tab tpTabParecer;
	String strPar = "/fxml/principal/TabParecer.fxml";

	@FXML Tab tpTabAtosOutorga;
	String strAtosOutorga = "/fxml/principal/TabAtosOutorga.fxml";

	TabDocumentoControlador tabDocCon;// = new TabDocumentoControlador(2);
	TabEnderecoControlador tabEndCon;// = new TabEnderecoControlador(2);
	TabInterferenciaControlador tabInterCon;
	TabUsuarioControlador tabUsCon;
	TabParecerControlador tabParCon;
	TabAtosOutorgaControlador tabAtosOutCon;

	ControladorPrincipal controladorPrincipal;
	/*
	 * Construtor
	 * @ControladorPrincipal - transmitindo a inicialiacao do controlador principal para enviar objectos de uma aba para outra
	 */
	public ControladorOutorga (ControladorPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}
	
	// setar documentos nas tabs da tabpane
	public void setDocumento (Documento doc) {
		tabEndCon.setDocumento(doc);
	}
	
	public void setEndereco(Endereco end) {
		
		tabInterCon.setEndereco(end);
		tabUsCon.setEndereco(end);
		tabParCon.setEndereco(end);
		tabAtosOutCon.setEndereco(end);
		
	}
	
	@FXML 
    private void initialize() {
		
		tpOutorga.prefWidthProperty().bind(pOutorga.widthProperty());
		tpOutorga.prefHeightProperty().bind(pOutorga.heightProperty());
		
		abrirTab (new Pane() , tabDocCon = new TabDocumentoControlador(this), strDem, tpTabDocumento );
			abrirTab (new Pane()  , tabEndCon = new TabEnderecoControlador(this), strEnd, tpTabEndereco );
				abrirTab (new Pane() , tabInterCon =  new TabInterferenciaControlador(this), strInt, tpTabInterferencia );
					abrirTab (new Pane() , tabUsCon =  new TabUsuarioControlador(this), strUs, tpTabUsuario );
						abrirTab (new Pane() , tabParCon =   new TabParecerControlador(this), strPar, tpTabParecer );
							abrirTab (new Pane() ,tabAtosOutCon = new TabAtosOutorgaControlador(this), strAtosOutorga, tpTabAtosOutorga );

							
		
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
		
		p.minWidthProperty().bind(pOutorga.widthProperty());
		p.minHeightProperty().bind(pOutorga.heightProperty());
		
		t.setContent(p);
	}

	

}
