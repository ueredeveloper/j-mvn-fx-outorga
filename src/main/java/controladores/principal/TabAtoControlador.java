package controladores.principal;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import dao.AtoDao;
import entidades.Ato;
import entidades.Vistoria;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import principal.Alerta;
import principal.FormatoData;


public class TabAtoControlador implements Initializable {

	static Vistoria vistoria = new Vistoria ();

	public void setVistoria (Vistoria vistoria) {
	
	TabAtoControlador.vistoria = vistoria;
	
		TabAtoControlador.lblVistoria.setText(
			vistoria.getVisSEI()
			+ ", Endereço: " + vistoria.getVisEnderecoFK().getEndLogradouro()
			+ ", RA: " + vistoria.getVisEnderecoFK().getEndRAFK().getRaNome()
		);
			
	}

	public static Vistoria getVistoria () {
		return vistoria;
	}
	
	//Pane p_lbl_Vistoria= new Pane();
	static Label lblVistoria = new Label();
	Label lblDataAtualizacao = new Label();
	
	String strPesquisa = "";
	
	Button btnNovo = new Button("Novo");
	Button btnSalvar = new Button("Salvar");
	Button btnEditar = new Button("Editar");
	Button btnExcluir = new Button("Excluir");
	Button btnCancelar = new Button("Cancelar");
	Button btnPesquisar = new Button("Pesquisar");
	TextField tfPesquisar = new TextField();
	
	TextField tfAto  = new TextField();
	TextField tfAtoSEI  = new TextField();
	
	DatePicker dpDataFiscalizacao = new DatePicker();
	DatePicker dpDataCriacaoAto = new DatePicker();
	
	// TableView Endereço //
	private TableView <Ato> tvLista  = new TableView<>();
	ObservableList<Ato> obsList = FXCollections.observableArrayList();
	
	TableColumn<Ato, String> tcTipo = new TableColumn<>();
	TableColumn<Ato, String> tcNumero = new TableColumn<>();
	TableColumn<Ato, String> tcSEI = new TableColumn<>();
	
	ChoiceBox<String> cbAtoTipo = new ChoiceBox<String>();
		ObservableList<String> olAtoTipo = FXCollections
			.observableArrayList(
					"Termo de Notificação", 
					"Auto de Infração",
					"Auto de Infração de Multa");
	
	HTMLEditor htmlCaracteriza = new HTMLEditor();
	HTMLEditor htmlRecomenda = new HTMLEditor();
	
  	int u = 0;

	ChoiceBox<String> cbUsuario = new ChoiceBox<String>();
		ObservableList<String> olUsuario = FXCollections
			.observableArrayList("0" , "1", "2", "3", "4");
	
	@FXML Pane pAto;
	AnchorPane apPrincipal = new AnchorPane();
	BorderPane bpPrincipal = new BorderPane();
	ScrollPane spPrincipal = new ScrollPane();
	Pane p1 = new Pane ();
	
	Pane p_lblVistoria = new Pane();
	Pane pDadosBasicos = new Pane();
	Pane pPersistencia = new Pane();
		
	@SuppressWarnings("unchecked")
	public void initialize(URL url, ResourceBundle rb) {
		
		pAto.getChildren().add(apPrincipal);
		
		apPrincipal.minWidthProperty().bind(pAto.widthProperty());
		apPrincipal.minHeightProperty().bind(pAto.heightProperty());
		
		apPrincipal.getChildren().add(spPrincipal);
		
		spPrincipal.setHbarPolicy(ScrollBarPolicy.NEVER);
		spPrincipal.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
	    AnchorPane.setLeftAnchor(spPrincipal, 0.0);
		AnchorPane.setRightAnchor(spPrincipal, 0.0);
		AnchorPane.setTopAnchor(spPrincipal, 0.0);
		AnchorPane.setBottomAnchor(spPrincipal, 47.0);
		
		spPrincipal.setPrefSize(200, 200);
		
	    bpPrincipal.minWidthProperty().bind(spPrincipal.widthProperty());
	    bpPrincipal.setPrefHeight(1200);

	    spPrincipal.setContent(bpPrincipal);
	    
	    p1.setMaxSize(1140, 680);
	    p1.setMinSize(1140, 680);
	    
		bpPrincipal.setTop(p1);
	    BorderPane.setAlignment(p1, Pos.CENTER);
	    
	    
		tcTipo.setCellValueFactory(new PropertyValueFactory<Ato, String>("atoTipo")); 
		tcNumero.setCellValueFactory(new PropertyValueFactory<Ato, String>("atoIdentificacao")); 
		tcSEI.setCellValueFactory(new PropertyValueFactory<Ato, String>("atoSEI")); 
		
		tcTipo.setPrefWidth(409);
		tcNumero.setPrefWidth(232);
		tcSEI.setPrefWidth(232);
		
		tvLista.getColumns().addAll(tcTipo, tcNumero, tcSEI);
		tvLista.setItems(obsList);
		
		tvLista.setPrefSize(900, 185);
		tvLista.setLayoutX(120);
		tvLista.setLayoutY(246);
		
		lblDataAtualizacao.setPrefSize(247, 22);
		lblDataAtualizacao.setLayoutX(772);
		lblDataAtualizacao.setLayoutY(440);
		
		obterVistoria();
		obterDadosBasicos();
		obterPersistencia();
		obterEditoresHTML();
		
		p1.getChildren().addAll(p_lblVistoria, pDadosBasicos, pPersistencia, lblDataAtualizacao, tvLista);
		
		modularBotoes();
		selecionarAto();
		
		cbAtoTipo.setItems(olAtoTipo);
		
		btnNovo.setOnAction(new EventHandler<ActionEvent>() {

		        @Override
		        public void handle(ActionEvent event) {
		            btnNovoHab();
		        }
		    });
			    
		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnSalvarHab();
	        }
	    });
		    
	    btnEditar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnEditarHab();
	        }
	    });
	    
	    btnEditar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnEditarHab();
	        }
	    });
	    
	    btnExcluir.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnExcluirHab();
	        }
	    });
	    
	    btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnPesquisarHab();
	        }
	    });
		
	}
	
	public void obterEditoresHTML () {
		
		Label lblCarac = new Label("CARACTERIZAÇÃO: ");
		lblCarac.setLayoutX(120);
		lblCarac.setLayoutY(459);
		
		htmlCaracteriza.setPrefSize(820, 200);
		htmlCaracteriza.setLayoutX(160);
		htmlCaracteriza.setLayoutY(485);
		
		Label lblRecom = new Label("RECOMENDAÇÃO: ");
		lblRecom.setLayoutX(120);
		lblRecom.setLayoutY(700);
		
		htmlRecomenda.setPrefSize(820, 200);
		htmlRecomenda.setLayoutX(160);
		htmlRecomenda.setLayoutY(726);
		
		htmlCaracteriza.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.SPACE  
		            || event.getCode() == KeyCode.TAB ) {
		        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
		        event.consume();
		    }
		});
		
		htmlRecomenda.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.SPACE  
		            || event.getCode() == KeyCode.TAB ) {
		        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
		        event.consume();
		    }
		});
		
		htmlCaracteriza.setHtmlText( 
	    		"<p><b>This text is bold</b></p>"
	    		+ "<p><i>This text is italic</i></p>"
	    		+ "<p>This is<sub> subscript</sub> and <sup>superscript</sup></p>"
	    		
	    		);
		
		htmlRecomenda.setHtmlText( 
	    		"<p><b>This text is bold</b></p>"
	    		+ "<p><i>This text is italic</i></p>"
	    		+ "<p>This is<sub> subscript</sub> and <sup>superscript</sup></p>"
	    		
	    		);
		
		p1.getChildren().addAll(lblCarac, htmlCaracteriza, lblRecom, htmlRecomenda);
		
	}
	
	Button btnBuscaVis = new Button();
	
	public void obterVistoria () {
		
		p_lblVistoria.setPrefSize(900, 50);
		p_lblVistoria.setLayoutX(120);
		p_lblVistoria.setLayoutY(20);
		p_lblVistoria.setStyle("-fx-background-color: #E9E9E9;");
		
		Label lblVis = new Label ("Vistoria: ");
		lblVis.setLayoutX(48);
		lblVis.setLayoutY(17);
		
		lblVistoria.setPrefSize(712, 25);
		lblVistoria.setLayoutX(105);
		lblVistoria.setLayoutY(13);
		lblVistoria.setStyle("-fx-font-weight: bold;");
		
		btnBuscaVis.setPrefSize(25, 25);
		btnBuscaVis.setLayoutX(828);
		btnBuscaVis.setLayoutY(13);
		
		p_lblVistoria.getChildren().addAll(lblVis, lblVistoria, btnBuscaVis);
		
	}
	
	public void obterDadosBasicos () {
		
		pDadosBasicos.setPrefSize(900, 90);
		pDadosBasicos.setLayoutX(120);
		pDadosBasicos.setLayoutY(85);
		pDadosBasicos.setStyle("-fx-background-color: #E9E9E9;");
		
		Label lblTipoAto = new Label ("Tipo de Ato: ");
		lblTipoAto.setLayoutX(117);
		lblTipoAto.setLayoutY(17);
		
			cbAtoTipo.setPrefSize(252, 25);
			cbAtoTipo.setLayoutX(194);
			cbAtoTipo.setLayoutY(13);
			
				Label lblNumAto = new Label ("Número do ato: ");
				lblNumAto.setLayoutX(456);
				lblNumAto.setLayoutY(17);
					
					tfAto.setPrefSize(87, 25);
					tfAto.setLayoutX(551);
					tfAto.setLayoutY(13);
					
						Label lblSEI = new Label ("n° SEI: ");
						lblSEI.setLayoutX(650);
						lblSEI.setLayoutY(17);
			
							tfAtoSEI.setPrefSize(87, 25);
							tfAtoSEI.setLayoutX(697);
							tfAtoSEI.setLayoutY(13);
							
								Label lblDataFis = new Label ("Data da Fiscalização: ");
								lblDataFis.setLayoutX(228);
								lblDataFis.setLayoutY(58);
							
									dpDataFiscalizacao.setPrefSize(110, 25);
									dpDataFiscalizacao.setLayoutX(351);
									dpDataFiscalizacao.setLayoutY(54);
									
										Label lblDataCri = new Label ("Data de Criação: ");
										lblDataCri.setLayoutX(472);
										lblDataCri.setLayoutY(58);
									
											dpDataCriacaoAto.setPrefSize(110, 25);
											dpDataCriacaoAto.setLayoutX(575);
											dpDataCriacaoAto.setLayoutY(54);
		

		pDadosBasicos.getChildren().addAll(
				
				lblTipoAto, cbAtoTipo, lblNumAto, tfAto, lblSEI, tfAtoSEI,
				lblDataFis, dpDataFiscalizacao, lblDataCri, dpDataCriacaoAto
				);
		
	}
	
    public void obterPersistencia () {
    	
   	    pPersistencia.setPrefSize(900, 50);
   	    pPersistencia.setLayoutX(120);
   	    pPersistencia.setLayoutY(180);
   
		btnNovo.setPrefSize(76, 25);
		btnNovo.setLayoutX(42);
		btnNovo.setLayoutY(12);
	
	    btnSalvar.setPrefSize(76, 25);
	    btnSalvar.setLayoutX(129);
	    btnSalvar.setLayoutY(12);
	
	    btnEditar.setPrefSize(76, 25);
	    btnEditar.setLayoutX(216);
	    btnEditar.setLayoutY(12);
	
	    btnExcluir.setPrefSize(76, 25);
	    btnExcluir.setLayoutX(303);
	    btnExcluir.setLayoutY(12);
	    
	    btnCancelar.setPrefSize(76, 25);
	    btnCancelar.setLayoutX(390);
	    btnCancelar.setLayoutY(12);
	    
	    btnPesquisar.setPrefSize(76, 25);
	    btnPesquisar.setLayoutX(783);
	    btnPesquisar.setLayoutY(12);
	    
	    tfPesquisar.setPrefSize(295, 25);
	    tfPesquisar.setLayoutX(477);
	    tfPesquisar.setLayoutY(12);
	    
	    pPersistencia.getChildren().addAll( 
	    		btnNovo, btnSalvar, btnEditar, btnExcluir,
	    		btnCancelar, tfPesquisar, btnPesquisar
	    		
	    		);
	    
	    
    }
	
	
	public void btnNovoHab () {
		
		cbAtoTipo.setDisable(false);
		tfAto.setDisable(false);
		tfAtoSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		
		cbAtoTipo.setValue(null);
		tfAto.setText("");
		tfAtoSEI.setText("");
		dpDataFiscalizacao.setValue(null);
		dpDataCriacaoAto.setValue(null);
		
		htmlCaracteriza.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		
		
		abrirEditorHTML();
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
	}

	public void btnSalvarHab () {
		
		if (vistoria == null) {
			
			Alert a = new Alert (Alert.AlertType.ERROR);
			a.setTitle("Alerta!!!");
			a.setContentText("Vistoria não selecionada!!!");
			a.setHeaderText(null);
			a.show();
			
		} else {
			
				if (cbAtoTipo.getValue() == null  ||
						tfAto.getText().isEmpty() ||
						tfAtoSEI.getText().isEmpty() 
						) 
				{
					
					Alert a = new Alert (Alert.AlertType.ERROR);
					a.setTitle("Alerta!!!");
					a.setContentText("Informe: Tipo de Ato, Número do Ato!!!");
					a.setHeaderText(null);
					a.show();
					
				} else {
				
				Ato ato = new Ato();
				
				ato.setAtoTipo(cbAtoTipo.getValue());
				ato.setAtoIdentificacao(tfAto.getText());
				ato.setAtoSEI(tfAtoSEI.getText());
				
				if (dpDataFiscalizacao.getValue() == null) {
					ato.setAtoDataFiscalizacao(null);}
					else {
						ato.setAtoDataFiscalizacao(Date.valueOf(dpDataFiscalizacao.getValue())); // DATA
					}
				
				if (dpDataCriacaoAto.getValue() == null) {
					ato.setAtoDataCriacao(null);}
					else {
						ato.setAtoDataCriacao(Date.valueOf(dpDataCriacaoAto.getValue())); // DATA
					}
				
				ato.setAtoCaracterizacao(htmlCaracteriza.getHtmlText());
				ato.setAtoRecomendacao(htmlRecomenda.getHtmlText());
				
				ato.setAtoVistoriaFK(vistoria);
				
				ato.setAtoAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
				
				AtoDao atoDao = new  AtoDao();
				
				atoDao.mergeAto(ato);
				
				obsList.add(ato);
				
				modularBotoes();
				fecharEditorHTML();
				
				Alert a = new Alert (Alert.AlertType.INFORMATION);
				a.setTitle("Parabéns!!!");
				a.setContentText("Cadastro salvo com sucesso!!!");
				a.setHeaderText(null);
				a.show();
		}
		}
	}
	
	public void btnEditarHab () {
		
		if (cbAtoTipo.isDisable()) {
			
			cbAtoTipo.setDisable(false);
			tfAto.setDisable(false);
			tfAtoSEI.setDisable(false);
			
			dpDataFiscalizacao.setDisable(false);
			dpDataCriacaoAto.setDisable(false);
			
			abrirEditorHTML();
			
			
		} else {
			
			if (cbAtoTipo.getValue() == null  ||
					tfAto.getText().isEmpty() ) 
			{
				
				Alert a = new Alert (Alert.AlertType.ERROR);
				a.setTitle("Alerta!!!");
				a.setContentText("Informe: Tipo de Ato, Número do Ato!!!");
				a.setHeaderText(null);
				a.show();
				
			} else {
			
			Ato ato = tvLista.getSelectionModel().getSelectedItem();
			
			ato.setAtoTipo(cbAtoTipo.getValue());
			ato.setAtoIdentificacao(tfAto.getText());
			ato.setAtoSEI(tfAtoSEI.getText());
			
			
			if (dpDataFiscalizacao.getValue() == null) {
				ato.setAtoDataFiscalizacao(null);}
				else {
					ato.setAtoDataFiscalizacao(Date.valueOf(dpDataFiscalizacao.getValue())); // DATA
				}
			
			if (dpDataCriacaoAto.getValue() == null) {
				ato.setAtoDataCriacao(null);}
				else {
					ato.setAtoDataCriacao(Date.valueOf(dpDataCriacaoAto.getValue())); // DATA
				}

			ato.setAtoCaracterizacao(htmlCaracteriza.getHtmlText());
			ato.setAtoRecomendacao(htmlRecomenda.getHtmlText());
			
			ato.setAtoAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
			
			AtoDao atoDao = new AtoDao();
			
				atoDao.mergeAto(ato);
			
			obsList.remove(ato);
			obsList.add(ato);
			
			modularBotoes();
			fecharEditorHTML();
			
			Alert a = new Alert (Alert.AlertType.INFORMATION);
			a.setTitle("Parabéns!!!");
			a.setContentText("Cadastro editado com sucesso!!!");
			a.setHeaderText(null);
			a.show();
			
			}
			
		}
		
	}

	public void btnExcluirHab () {
		
		try {
	
			Ato ato = tvLista.getSelectionModel().getSelectedItem();
			
			AtoDao atoDao = new AtoDao();
			
			atoDao.removerAto(ato.getAtoID());
			
			obsList.remove(ato);
			
			modularBotoes();
			
			fecharEditorHTML();
			
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído com sucesso!!!", ButtonType.OK));
			
			}	catch (Exception e) {
				
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao escluir o cadastro!!!", ButtonType.OK));
				
			};
		
	}
	
	public void btnCancelarHab () {
		
		modularBotoes();
		fecharEditorHTML();
	}
	
	public void btnPesquisarHab () {
		
		strPesquisa = tfPesquisar.getText();
		
		listarAtos(strPesquisa);
		
		modularBotoes();
		fecharEditorHTML();
	
	}
	
	public void modularBotoes () {
		
		cbAtoTipo.setDisable(true);
		tfAto.setDisable(true);
		tfAtoSEI.setDisable(true);
		
		dpDataFiscalizacao.setDisable(true);
		dpDataCriacaoAto.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		 
		btnNovo.setDisable(false);
	}
	
	//@FXML Pane pAto;
	@FXML AnchorPane apAtoInt;
	@FXML BorderPane bpAto;
	
//  metodo para listar interferencias  //
 	public void listarAtos (String strPesquisaAto) {
 	
	 	// --- conexão - listar endereços --- //
		AtoDao atoDao = new AtoDao();
		List<Ato> atoList = atoDao.listAto(strPesquisaAto);
		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
		
			for (Ato ato : atoList) {
				
				ato.getAtoID();
				ato.getAtoVistoriaFK();
				ato.getAtoTipo();
				ato.getAtoIdentificacao();
				ato.getAtoSEI();
				ato.getAtoCaracterizacao();
				ato.getAtoDataFiscalizacao();
				ato.getAtoDataCriacao();
				
				obsList.add(ato);
			
		}
		
 	}
		
 	// método selecionar interferência //
  	public void selecionarAto () {
 	
 		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
 			
 			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
 			
 			Ato ato = (Ato) newValue;
 			
 			if (ato == null) {
 				
 				btnNovo.setDisable(true);
 				btnSalvar.setDisable(true);
 				btnEditar.setDisable(false);
 				btnExcluir.setDisable(false);
 				btnCancelar.setDisable(false);
 				
 			} else {

 				// -- preencher os campos -- //
 				cbAtoTipo.setValue(ato.getAtoTipo());
 				tfAto.setText(ato.getAtoIdentificacao());
 				tfAtoSEI.setText(ato.getAtoSEI());
 				
 				
 				if (ato.getAtoDataFiscalizacao() == null) {
 					dpDataFiscalizacao.getEditor().clear();
	 				} else {
	 					Date dataFis = ato.getAtoDataFiscalizacao();
	 					dpDataFiscalizacao.setValue(dataFis.toLocalDate());
	 				}
 				
 				if (ato.getAtoDataCriacao() == null) {
 					dpDataCriacaoAto.getEditor().clear();
	 				} else {
	 					Date dataCri = ato.getAtoDataCriacao();
	 					dpDataCriacaoAto.setValue(dataCri.toLocalDate());
	 				}
 				
 				htmlCaracteriza.setHtmlText(ato.getAtoCaracterizacao());
 				htmlRecomenda.setHtmlText(ato.getAtoRecomendacao());
 				
				// mostrar data de atualizacao //
 				FormatoData d = new FormatoData();
				try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(ato.getAtoAtualizacao()));
						lblDataAtualizacao.setTextFill(Color.BLACK);
				}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
						lblDataAtualizacao.setTextFill(Color.RED);}
 			
 				vistoria = ato.getAtoVistoriaFK();
				
 				
 				lblVistoria.setText(
 						"Vistoria n°: " + vistoria.getVisSEI()
 						+ ", Data da fiscalização: " + vistoria.getVisDataFiscalizacao()
 				);
				
				// copiar número do ato  sei ao selecionar //
				Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                conteudo.putString(ato.getAtoSEI());
                clip.setContent(conteudo);
                
				//-- modular botoes --//
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				
 				}
 			}
 		});
  	}
  	
  	public void fecharEditorHTML (){
		htmlCaracteriza.setDisable(true);
		htmlRecomenda.setDisable(true);
	}
  	
	public void abrirEditorHTML (){
		htmlCaracteriza.setDisable(false);
		htmlRecomenda.setDisable(false);
	}

}
