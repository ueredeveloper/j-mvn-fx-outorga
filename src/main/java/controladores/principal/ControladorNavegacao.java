package controladores.principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import principal.MalaDiretaAnexoParecer;


public class ControladorNavegacao implements Initializable {
	
	@FXML Pane pNavegador;
	
	String strHTML;
	
	
	String strAnexoParecer;
	
	String strTabela1;
	String strTabela2;
	
	ObservableList<String> obsListUsuariosMalaDireta = FXCollections.observableArrayList();
	

	List<Object[][]> listMalaDireta = new ArrayList<>();
	
	public void setObjetosAnexo (List<Object[][]> listMalaDireta, ObservableList<String>  obsListUsuariosMalaDireta, String strAnexoParecer, String strTabela1, String strTabela2) {
		this.listMalaDireta = listMalaDireta;
		this.obsListUsuariosMalaDireta = obsListUsuariosMalaDireta;
		this.strAnexoParecer = strAnexoParecer;
		this.strTabela1 = strTabela1;
		this.strTabela2 = strTabela2;
	}
	
	
	public void setHTML (String strHTML) {
		this.strHTML = strHTML;
	}
	
	
	AnchorPane ap;
	WebView webView;
	WebView webViewPopUp;
	
	Button btnCapturarDocumentosSEI, btnMostarDocumentosSEI;

	Button btnGoogle;
	Button btnSEI;
	
	HBox hbControles;
	
	
	public static ControladorNavegacao conNav;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		conNav = this;
		
		ap = new AnchorPane();
		
		ap.minWidthProperty().bind(pNavegador.widthProperty());
		ap.maxWidthProperty().bind(pNavegador.widthProperty());
    	
		ap.minHeightProperty().bind(pNavegador.heightProperty());
		ap.maxHeightProperty().bind(pNavegador.heightProperty());
		
		hbControles = new HBox();
			hbControles.setPrefHeight(30);
		
			btnCapturarDocumentosSEI = new Button("CAPTURAR");
			btnCapturarDocumentosSEI.setPrefSize(90, 30);
			
			btnMostarDocumentosSEI = new Button("MOSTRAR");
			btnMostarDocumentosSEI.setPrefSize(90, 30);
			
			btnGoogle = new Button("GOOGLE");
			btnGoogle.setPrefSize(90, 30);
			
			btnSEI = new Button("SEI");
			btnSEI.setPrefSize(90, 30);
			
			hbControles.setStyle("-fx-background-color: white");
			
		hbControles.getChildren().addAll(btnCapturarDocumentosSEI, btnMostarDocumentosSEI, btnGoogle, btnSEI);
	
		inicializarNavegadorWeb ();
    	
    	ap.getChildren().add(hbControles);
    	
    	AnchorPane.setTopAnchor(hbControles, 0.0);
 	    AnchorPane.setLeftAnchor(hbControles, 0.0);
 	    AnchorPane.setRightAnchor(hbControles, 0.0);
 	   
    	AnchorPane.setTopAnchor(webView, 30.0);
 	    AnchorPane.setLeftAnchor(webView, 0.0);
 	    AnchorPane.setRightAnchor(webView, 0.0);
 	    AnchorPane.setBottomAnchor(webView, 0.0);
    	
	    pNavegador.getChildren().add(ap);
	    
        btnGoogle.setOnAction((ActionEvent evt)->{
        	
        	link = "http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
        	navegarWeb (link);
        	
        });
        
        btnSEI.setOnAction((ActionEvent evt)->{
              	
          	link = "https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI";
          	navegarWeb (link);
              	
        });	
        
        
        btnCapturarDocumentosSEI.setOnAction((ActionEvent evt)->{
          	
          	webView.getEngine().executeScript(
				
    			"var doc;"

    			+ 	"$(function() {"

    			+	"$( '#ifrArvore' ).load(function(){"
    			 
    			//+	"alert('iframe carregado');"
    			        
    			//+	"alert($(this).contents().find('span'));"
    			        
    			+	"$(this).contents().find( 'span' ).css( 'background-color', '#BADA55' );"
    				
    			+	"doc = ($(this).contents().find('span'));" 
    				
    			//+	"alert(doc);"
    			  
    			+	"for (var i = 0; i < doc.length; i++) {"
    			
    			//+	"alert (doc[i].textContent);"  
    				
    			+	"}"
    			  
    			+   "});"

    			+ 	"});"
    			
    			);
          	
          
        
        });	
        
        /*
         * Mostrar em uma listview todos os documentos capturados na parte superior esquerda do sei
         * 
         * 	--------------------------------------------------------------------
         * |	00197-00000573/2018-10      	|								|
         * |		Requerimento 5154170 	   	|								|
         * |		Termo de Abertura 5387339  	|								|
         * |		Parecer 					|								|
         * ---------------------------------------------------------------------
         * |									|								|
         * 	--------------------------------------------------------------------
         */
        btnMostarDocumentosSEI.setOnAction((ActionEvent evt)->{
        	
        	
          	
        	int int_numero_documentos = (int) webView.getEngine().executeScript("doc.length");
          	
          	ObservableList<String> documentos = FXCollections.observableArrayList();
          	
          	for (int i = 0; i < int_numero_documentos; i++ ) {
          		
          		String s = (String) webView.getEngine().executeScript("doc[" + i + "].textContent");
          		
          		if (!(s.length() == 0)) {
          		documentos.add(s);
          		}
          		
			}
          	
          	ListView<String> listView = new ListView<String>(documentos);
			TableColumn<List, String> tc = new TableColumn<List, String> ("Documentos");
			
			tc.setCellValueFactory(new Callback<CellDataFeatures<List, String>, ObservableValue<String>>() {
				
			     public ObservableValue<String> call(CellDataFeatures<List, String> p) {
			 
			         return new SimpleStringProperty(p.getValue().toString());
			     }
			 });

				//TableView tv = new TableView(listView);
				
				Scene scene = new Scene(listView);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(400);
				stage.setHeight(300);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setTitle("Documentos SEI");
			    
			    stage.setAlwaysOnTop(true);
			    stage.show();
          	
			    listView.getSelectionModel().selectedItemProperty().addListener(
		                new ChangeListener<String>() {
		                    public void changed(ObservableValue<? extends String> 
		                    ov, String old_val, String new_val) {
		                  
		                         Clipboard clip = Clipboard.getSystemClipboard();
		                         ClipboardContent conteudo = new ClipboardContent();
		                         conteudo.putString(new_val);
		                         clip.setContent(conteudo);
		                    }
		                });
        	
        	
              	
        });	
        	
	} // FIM INITIALIZE
	
	Button btnAtoAdministrativo;
	ComboBox<String> cbUsuarios;
	Button btnAtoAdministrativoAnexo;
	
	Button btnCapturaHTML;
	Button btnInsereHTML;
	
	public void inicializarBotoesPopUp (){
		
		btnAtoAdministrativo = new Button("ATO ADMINISTRATIVO");
		cbUsuarios = new ComboBox<>();
		cbUsuarios.setItems(obsListUsuariosMalaDireta);
		
		btnAtoAdministrativoAnexo = new Button("ANEXO");
		btnCapturaHTML = new Button("CAPTURAR");
		btnInsereHTML = new Button("INSERIR");
		
		
		/**
		 *	Inserir ato adiministrativo (Documento) no iframe 2 do editor html do sei
		 */
		btnAtoAdministrativo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
        			//-- imprimir o relatório ou tn no editor do SEI --//
            		webViewPopUp.getEngine().executeScript(
	            				"document.getElementsByTagName('iframe')[2].contentDocument.body.innerHTML = " + strHTML + ";"
	            			);
					
            }
        });
		
		/**
		 * Inserir o anexo do ato administrativo (Parecer Coletivo) no editor HTML do sei quando este estiver preenchido com o parecer
		 */
		btnAtoAdministrativoAnexo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	
            		MalaDiretaAnexoParecer anexo = new MalaDiretaAnexoParecer(listMalaDireta, strAnexoParecer, strTabela1, strTabela2);
            		String strAnexo = anexo.criarAnexoParecer(int_interferencia_selecionada);
            		
            	
        			//-- imprimir o relatório ou tn no editor do SEI --//
            		webViewPopUp.getEngine().executeScript(
            				"document.getElementsByTagName('iframe')[2].contentDocument.body.lastElementChild.innerHTML = " + strAnexo + ";"
	            			);
					
            }
        });
		
		/**
		 * Capturar o ato editado e inserir em outros locais necessarios
		 */
		btnCapturaHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	strParecerCapturado = (String) webViewPopUp.getEngine().executeScript("document.getElementsByTagName('iframe')[2].contentDocument.body.innerHTML");
       
            }
        });
		
		/**
		 * Inserir o ato capturado onde  for necessario. O tecnico edita um parecer e quer inseri-lo em outros documentos
		 */
		btnInsereHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	
            	strParecerCapturado = strParecerCapturado.replace("\"", "'");
            	strParecerCapturado = strParecerCapturado.replace("\n", "");

            	strParecerCapturado =  "\"" + strParecerCapturado + "\"";
     
        			//-- imprimir o relatório ou tn no editor do SEI --//
            		webViewPopUp.getEngine().executeScript(
            				"document.getElementsByTagName('iframe')[2].contentDocument.body.innerHTML = " + strParecerCapturado + ";"
	            			);
					
            }
        });
		
		
		
	}
	
	String link;
	
	String strParecerCapturado = "";
	
	// selecionar qual anexo ira para o parecer coletivo
	int int_interferencia_selecionada = 0;
	
	public void inicializarNavegadorWeb () {
		
		// inicializacao webVew //
		webView = new WebView();
		webView.setPrefSize(500, 500);
    	WebEngine webEnginer = webView.getEngine();
    	
    	webView.minWidthProperty().bind(pNavegador.widthProperty());
    	webView.maxWidthProperty().bind(pNavegador.widthProperty());
    	
    	webView.minHeightProperty().bind(pNavegador.heightProperty());
    	webView.maxHeightProperty().bind(pNavegador.heightProperty());
    	
    	// https://www.google.com.br/webhp?hl=pt-BR&sa=X&ved=0ahUKEwiQm_2BrN3hAhXKIrkGHRukAZUQPAgH
    	
    	//https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI
    	
    	// String link = "https://www.w3schools.com/howto/howto_js_popup.asp";
    	
    	// treinamento sei http://treinamento3.sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI
    	
    	webEnginer.load("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
    	
    	ap.getChildren().add(webView);
    	
    	// inicializacao do pop up onde necessitar nas telas do SEI
    	webView.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

		    public WebEngine call(PopupFeatures p) {
		    	
		    	// inicializacao do webViewPopUp, responsavel pela tela de popup
		    	webViewPopUp = new WebView(); 
		    	
			    	AnchorPane apPopUp = new AnchorPane();
			    	
			    	apPopUp.setPrefHeight(645.0);
			    	apPopUp.setPrefWidth(1140.0);
			    	
			    	inicializarBotoesPopUp ();
			    	
			    	btnAtoAdministrativo.setPrefSize(300, 30);
			    	cbUsuarios.setPrefSize(600, 30);
			    	btnAtoAdministrativoAnexo.setPrefSize(200, 30);
			    	btnCapturaHTML.setPrefSize(200, 30);
			    	btnInsereHTML.setPrefSize(200, 30);
			    	
			    	HBox hbPopUp = new HBox(btnAtoAdministrativo, cbUsuarios, btnAtoAdministrativoAnexo, btnCapturaHTML, btnInsereHTML);
			    	
			    	hbPopUp.setPrefSize(900, 30);
			    	
			    	
			    	cbUsuarios.getSelectionModel().selectedIndexProperty().addListener(new
			 	            ChangeListener<Number>() {
			 	    	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
			 	    		Number value, Number new_value) {
			 	    		
			 	    		if ( (Integer) new_value !=  -1)
			 	    		int_interferencia_selecionada = (int) new_value;
			 	    		
			 	    		//System.out.println("interferencia seleciondada " + int_interferencia_selecionada);
			 	    	
			             }
			 	    });
			    	
			    	/*
			    	cbUsuarios.setConverter(new StringConverter<Interferencia>() {

						public String toString(Interferencia i) {
							return i.getInterTipoInterferenciaFK().getTipoInterDescricao() 
									+ ", Situação: " + i.getInterSituacaoProcessoFK().getSituacaoProcessoDescricao()
									+ ", Tipo Outorga:  " + i.getInterTipoOutorgaFK().getTipoOutorgaDescricao() 
									+ ", SubTipo: "  + i.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao()
									+ ", Tipo Ato: " + i.getInterTipoAtoFK().getTipoAtoDescricao();

						}

						public Interferencia fromString(String string) {
							return null;
						}
					});
					*/
			    	
			    	
			    	AnchorPane.setTopAnchor(hbPopUp, 0.0);
			 	    AnchorPane.setLeftAnchor(hbPopUp, 0.0);
			 	    AnchorPane.setRightAnchor(hbPopUp, 0.0);
			 	    
			 	    AnchorPane.setTopAnchor(webViewPopUp, 30.0);
			 	    AnchorPane.setLeftAnchor(webViewPopUp, 0.0);
			 	    AnchorPane.setRightAnchor(webViewPopUp, 0.0);
			 	    AnchorPane.setBottomAnchor(webViewPopUp, 0.0);
		 	    
			 	    apPopUp.getChildren().addAll(hbPopUp, webViewPopUp);
		 	   
		 	   
				Stage stage = new Stage();
				
	            stage.setScene(new Scene(apPopUp));
	            
	            stage.setMaximized(false);
		        stage.setResizable(false);
	            stage.show();
	            
	            return webViewPopUp.getEngine();
	            
		    }
		    
	    });
	
    	      
		
	}

	
	//-- navegar para o site google --//
	public void  navegarWeb (String link) {
			
		webView.getEngine().load(link);
			
	}

}
