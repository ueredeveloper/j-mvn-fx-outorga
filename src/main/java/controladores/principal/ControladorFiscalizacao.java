package controladores.principal;

import java.io.IOException;

import entidades.Documento;
import entidades.Endereco;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class ControladorFiscalizacao {

	@FXML Pane pFiscalizacao;
	@FXML TabPane tpFiscalizacao = new TabPane();

	@FXML Tab tpTabDocumento;
	String strDem = "/fxml/principal/TabDocumento.fxml";

	@FXML Tab tpTabEndereco;
	String strEnd = "/fxml/principal/TabEndereco.fxml";

	@FXML Tab tpTabInterferencia;
	String strInt = "/fxml/principal/TabInterferencia.fxml";

	@FXML Tab tpTabUsuario;
	String strUs = "/fxml/principal/TabUsuario.fxml";

	@FXML Tab tpTabVistoria;
	String strVis = "/fxml/fiscalizacao/TabVistoria.fxml";

	@FXML Tab tpTabAto;
	String strAto = "/fxml/fiscalizacao/TabAto.fxml";

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
	public ControladorFiscalizacao (ControladorPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}

	// setar documentos nas tabs da tabpane
	public void setDocumento (Documento doc) {
		tabEndCon.setDocumento(doc);
	}
	
	public void setEndereco(Endereco end) {
		
		tabInterCon.setEndereco(end);
		tabUsCon.setEndereco(end);
	
	}

						
	@FXML 
    private void initialize() {
		
		tpFiscalizacao.prefWidthProperty().bind(pFiscalizacao.widthProperty());
		tpFiscalizacao.prefHeightProperty().bind(pFiscalizacao.heightProperty());



		abrirTab (new Pane() , tabDocCon = new TabDocumentoControlador(this), strDem, tpTabDocumento );
			abrirTab (new Pane()  , tabEndCon = new TabEnderecoControlador(this), strEnd, tpTabEndereco );
				abrirTab (new Pane() , tabInterCon =  new TabInterferenciaControlador(this), strInt, tpTabInterferencia );
					abrirTab (new Pane() , tabUsCon =  new TabUsuarioControlador(this), strUs, tpTabUsuario );
					
						abrirTab (new Pane() ,  new TabVistoriaControlador(), strVis, tpTabVistoria );
							abrirTab (new Pane(),  new TabAtoControlador(), strAto, tpTabAto );
							
							
		
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
		
		p.minWidthProperty().bind(pFiscalizacao.widthProperty());
		p.minHeightProperty().bind(pFiscalizacao.heightProperty());
		
		t.setContent(p);
		
	}

}



/*
Pane pVistoria = new Pane();

tabVis = new TabVistoriaControlador();

FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fiscalizacao/TabVistoria.fxml"));

loader.setRoot(pVistoria);
loader.setController(tabVis);
try {
	loader.load();
} catch (IOException e) {
	System.out.println("erro na abertura do pane vistoria");
	e.printStackTrace();
}

pVistoria.minWidthProperty().bind(pFiscalizacao.widthProperty());
pVistoria.minHeightProperty().bind(pFiscalizacao.heightProperty());

tpTabVistoria.setContent(pVistoria);
*/


/*
tpFiscalizacao.widthProperty().addListener(new ChangeListener<Number>() {
	   
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
    	
    	//tabVistoria.redimWid (newValue);
    	
    	System.out.println("bind tpFisc " + newValue);
    	
    }
});
*/



/*
if (tabDemandaControlador == null) {
	
	tabDemandaControlador = new TabDemandaControlador();
}
*/

/*
pFiscalizacao.widthProperty().addListener(new ChangeListener<Number>() {
	   
    @Override 
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
    	
    	
    	System.out.println("largura p fiscalizacao "  + pFiscalizacao.getWidth());
    	//tabDemandaControlador.redimWid (newValue);
    	
    }
});

pFiscalizacao.heightProperty().addListener(new ChangeListener<Number>() {
	   
    @Override 
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
    	
    	
    	System.out.println("altura p fiscalizacao "  + pFiscalizacao.getHeight());
    	//tabDemandaControlador.redimHei (newValue);
    	
    }
});
*/


/*
p = new Pane();

tabDemandaControlador = new TabDemandaControlador();

FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fiscalizacao/TabDemanda.fxml"));

//loader.setRoot(p);
loader.setController(tabDemandaControlador);

try {
	loader.load();
} catch (IOException e) {
	e.printStackTrace();
}
*/


/*
tabDemandaControlador = new TabDemandaControlador();

pFiscalizacao.widthProperty().addListener(new ChangeListener<Number>() {
	   
    @Override 
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
    	
    	// Demanda //
    	tabDemandaControlador.redimensionarLargura(newValue);
    	
    }
});



pFiscalizacao.heightProperty().addListener(new ChangeListener<Number>() {
	   
    @Override 
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
    	
    	// Demanda //
    	tabDemandaControlador.redimensionarAltura(newValue);
    	
    }
});
*/
