package controladores.principal;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import dao.DemandaDao;
import entidades.Demanda;
import entidades.Endereco;
import entidades.Processo;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import mapas.GoogleMap;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;

public class TabDemandaControlador implements Initializable {
	
  String strPesquisa = "";
  String strPesquisaProcesso = "";
  
  TableView<Demanda> tvLista = new TableView<Demanda>();
	  TableColumn<Demanda, String> tcTipoDemanda = new TableColumn<Demanda, String>("Tipo");
	  	TableColumn<Demanda, String> tcNumeroDemanda = new TableColumn<Demanda, String>("Número");
	  		TableColumn<Demanda, String> tcNumeroDemandaSEI = new TableColumn<Demanda, String>("Número SEI");
	  			TableColumn<Demanda, String> tcNumeroProcesso = new TableColumn<Demanda, String>("Número do Processo");
	  		
  Set<Demanda> setListDemanda;
  Demanda demanda = new  Demanda();
  Processo processo = new Processo();
  
  public void habilitarDemanda() {
    
	  cbTipoDemanda.setValue(null);
	  tfNumeroDemanda.setText("");
	  tfDemandaSei.setText("");
	  tfProcessoSei.setText("");
    
	  dpDataDistribuicao.getEditor().clear();
	  dpDataRecebimento.getEditor().clear();
    
	  dpDataDistribuicao.setDisable(false);
	  dpDataRecebimento.setDisable(false);
    
	  cbTipoDemanda.setDisable(false);
	  tfNumeroDemanda.setDisable(false);
	  tfDemandaSei.setDisable(false);
	  tfProcessoSei.setDisable(false);
    
	  btnSalvar.setDisable(false);
    
	  btnEditar.setDisable(true);
	  btnExcluir.setDisable(true);
	  btnNovo.setDisable(true);
	  
  }
  
  public void salvarDemanda()	{
    
	  try {
		  
		      if ((tfDemandaSei.getText().isEmpty()) || 
		        (tfProcessoSei.getText().isEmpty()))
		      {
		        Alerta a = new Alerta();
		        a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
		      }
		      else
		      {
		        Demanda dem = new Demanda();
		        
		        dem.setDemTipo(cbTipoDemanda.getValue());
		        dem.setDemNumero(tfNumeroDemanda.getText());
		        dem.setDemNumeroSEI(tfDemandaSei.getText());
		        dem.setDemProcesso(tfProcessoSei.getText());
		        
		        if (dpDataDistribuicao.getValue() == null) {
		        	dem.setDemDistribuicao(null);
		        } else {
		        	dem.setDemDistribuicao(Date.valueOf((LocalDate)dpDataDistribuicao.getValue()));
		        }
			        if (dpDataRecebimento.getValue() == null) {
			        	dem.setDemRecebimento(null);
			        } else {
			        	dem.setDemRecebimento(Date.valueOf((LocalDate)dpDataRecebimento.getValue()));
			        }
			        
			        dem.setDemAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
		        
		        DemandaDao dao = new DemandaDao();
		        
		        dao.salvarDemanda(dem);
		        

			    if (intControlador == 0) {
			    	//TabEnderecoControlador.controladorAtendimento.setDemanda(dem);
		
			 	}
			     
			    if (intControlador == 1) {
			 		//TabEnderecoControlador.controladorFiscalizacao.setDemanda(dem);
			    }
			    
			    if (intControlador == 2) {
		 			//TabEnderecoControlador.controladorOutorga.setDemanda(dem);
			    }
				
		        obsList.add(dem);
		        
		        modularBotoesDemanda();
		        
		        Alerta a = new Alerta();
		        a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro salvo com sucesso!!!", new ButtonType[] { ButtonType.OK }));
		      }
		      
	    	}
	  
		  catch (Exception ex)	{
			  System.out.println("Erro: " + ex);
			  ex.printStackTrace();
      
      Alerta a = new Alerta();
      a.alertar(new Alert(Alert.AlertType.ERROR, "erro na conexão, tente novamente!", new ButtonType[] { ButtonType.OK }));
      
    }
	  
  }
  
  public void editarDemanda()	{
	  
	    if (tfNumeroDemanda.isDisable()) {
	    	
	      cbTipoDemanda.setDisable(false);
	      tfNumeroDemanda.setDisable(false);
	      tfDemandaSei.setDisable(false);
	      tfProcessoSei.setDisable(false);
	      
	      dpDataDistribuicao.setDisable(false);
	      dpDataRecebimento.setDisable(false);
	      
	      System.out.println("editar demanda if tfnumero demana is disable");
	      
	    }
	    
		    else if ((tfDemandaSei.getText().isEmpty()) || (tfProcessoSei.getText().isEmpty())) {
		    	
		      Alerta a = new Alerta();
		      a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
		      
		    }
	    
			    else
			    {
			    	
			    
			    System.out.println(" else ");
			      Demanda dem = (Demanda)tvLista.getSelectionModel().getSelectedItem();
			      
				      dem.setDemTipo(cbTipoDemanda.getValue());
				      dem.setDemNumero(tfNumeroDemanda.getText());
				      dem.setDemNumeroSEI(tfDemandaSei.getText());
				      dem.setDemProcesso(tfProcessoSei.getText());
			        
				      if (dpDataDistribuicao.getValue() == null) {
				        dem.setDemDistribuicao(null);
				      } else {
				        dem.setDemDistribuicao(Date.valueOf((LocalDate)dpDataDistribuicao.getValue()));
				      }
					      if (dpDataRecebimento.getValue() == null) {
					        dem.setDemRecebimento(null);
					      } else {
					        dem.setDemRecebimento(Date.valueOf((LocalDate)dpDataRecebimento.getValue()));
					      }
			      
				      dem.setDemAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
			      
			      DemandaDao dDao = new DemandaDao();
			      
			      dDao.mergeDemanda(dem);
			      
			      obsList.remove(dem);
			      obsList.add(dem);
			     
			     /* transmitir demanda para a tab endereco */
			   
			    if (intControlador == 0) {
			    	//TabEnderecoControlador.controladorAtendimento.setDemanda(dem);
		
			 	}
			     
			    if (intControlador == 1) {
			 		//TabEnderecoControlador.controladorFiscalizacao.setDemanda(dem);
			    }
			    
			    if (intControlador == 2) {
		 			//TabEnderecoControlador.controladorOutorga.setDemanda(dem);
			    }
				
			    modularBotoesDemanda();
			      
			    Alerta a = new Alerta();
			    a.alertar(new Alert(Alert.AlertType.ERROR, "Cadastro editado com sucesso!!!", new ButtonType[] { ButtonType.OK }));
			    
			    } // fim else
	    
  }
  
  public void excluirDemanda()	{
   
	  try
		    {
		      Demanda dem = (Demanda)tvLista.getSelectionModel().getSelectedItem();
		      
		      int id = dem.getDemID();
		      
		      DemandaDao dDao = new DemandaDao();
		      
		      dDao.removerDemanda(Integer.valueOf(id));
		      
		      obsList.remove(dem);
		      
		      modularBotoesDemanda();
		      
		      Alerta a = new Alerta();
		      a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído com sucesso!!!", new ButtonType[] { ButtonType.OK }));
		    }
	  
    catch (Exception e)	{
    	
	      Alerta a = new Alerta();
	      a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao excluir o cadastro!!!", new ButtonType[] { ButtonType.OK }));
      
    	}
	  
  }
  
  public void cancelarDemanda()	{
   
	  modularBotoesDemanda();
  }
  
  public void pesquisarDemanda()	{
	  
    strPesquisa = tfPesquisar.getText();
    
    listarDemandas(strPesquisa);
    
    modularBotoesDemanda();
    
  }
  
  Label lblDataAtualizacao = new Label();
  
  @FXML Pane pDemanda;
  
  Pane p1 = new Pane();
  BorderPane bp1 = new BorderPane();
  BorderPane bp2 = new BorderPane();
  ScrollPane sp = new ScrollPane();
  Pane pMapa = new Pane();
  
  GoogleMap googleMaps = new GoogleMap();
  
  public static TabDemandaControlador tabDemCon;
  
  Double[][] prefSizeWHeLayXY;

  Pane pProcesso;
  Label lblProcessoPrincipal;
  Button btnTelaProcesso;
  
  Componentes com;
  
  public static TabDemandaControlador controladorAtendimento;
  	public static TabDemandaControlador controladorFiscalizacao;
  		public static TabDemandaControlador controladorOutorga;
  	
  		int intControlador;
	
  public TabDemandaControlador (int i) {
		
		if (i==0) {
			controladorAtendimento = this;
			intControlador = i;
		}
		if(i==1) {
			controladorFiscalizacao = this;
			intControlador = i;
		}
		if(i==2) {
			controladorOutorga = this;
			intControlador = i;
		}
	
	}

  public void initialize(URL url, ResourceBundle rb) {
	  
    bp1.minWidthProperty().bind(pDemanda.widthProperty());
    bp1.maxHeightProperty().bind(pDemanda.heightProperty().subtract(60));
    
    bp1.getStyleClass().add("border-pane");
    
    bp2.setPrefHeight(800.0D);
    bp2.minWidthProperty().bind(pDemanda.widthProperty());
    
    sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    
    sp.setContent(bp2);
    
    bp1.setCenter(sp);
    
    pDemanda.getChildren().add(bp1);
    
    p1.setMaxSize(980.0, 1000.0);
    p1.setMinSize(980.0, 1000.0);
    
    bp2.setTop(p1);
    BorderPane.setAlignment(p1, Pos.CENTER);
    
    lblDataAtualizacao.setPrefSize(247.0, 22.0);
    lblDataAtualizacao.setLayoutX(707.0);
    lblDataAtualizacao.setLayoutY(510.0);
    
    tcTipoDemanda.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demTipo"));
    tcNumeroDemanda.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demNumero"));
    tcNumeroDemandaSEI.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demNumeroSEI"));
    tcNumeroProcesso.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demProcesso"));
    
    tcTipoDemanda.setPrefWidth(255.0);
    	tcNumeroDemanda.setPrefWidth(210.0);
    		tcNumeroDemandaSEI.setPrefWidth(220.0);
    			tcNumeroProcesso.setPrefWidth(220.0);
    
    tvLista.setPrefSize(930.0, 185.0);
    	tvLista.setLayoutX(25.0);
    		tvLista.setLayoutY(316.0);
    
    tvLista.getColumns().add(tcTipoDemanda); //, tcDocsSEI, tcProcsSEI });
    	tvLista.getColumns().add(tcNumeroDemanda);
    		tvLista.getColumns().add(tcNumeroDemandaSEI);
    			tvLista.getColumns().add(tcNumeroProcesso);
    
    tvLista.setItems(obsList);
    
    pMapa.setPrefSize(930.0, 400.0);
    pMapa.setLayoutX(25.0);
    pMapa.setLayoutY(543.0);
    pMapa.getStyleClass().add("panes");
    
    pMapa.getChildren().add(googleMaps);
    googleMaps.setWidth(930.0);
    googleMaps.setHeight(400.0);
    googleMaps.switchHybrid();
    
    p1.getChildren().addAll(tvLista, lblDataAtualizacao, pMapa);
    
    listNodesProcesso.add(pProcesso = new Pane());
    listNodesProcesso.add(new Label("PROCESSO PRINCIPAL:"));
    listNodesProcesso.add(lblProcessoPrincipal = new Label());
    listNodesProcesso.add(btnTelaProcesso = new Button("<<<"));
    
    prefSizeWHeLayXY = new Double[][] { 
    	
    	{950.0,60.0,15.0,10.0},
    		{150.0,30.0,32.0,15.0},
    			{655.0,30.0,182.0,15.0},
    				{70.0,20.0,848.0,19.0},
	};
    
    com = new Componentes();
    com.popularTela(listNodesProcesso, prefSizeWHeLayXY, p1);
    
    
    listNodesDemanda.add(pDadosDemanda = new Pane());
    
    listNodesDemanda.add(new Label("TIPO:"));
    listNodesDemanda.add(cbTipoDemanda =  new ComboBox<String>());
	    listNodesDemanda.add(new Label("NÚMERO:"));
	    listNodesDemanda.add(tfNumeroDemanda = new TextField());
	    	listNodesDemanda.add(new Label("SEI:"));
	    	listNodesDemanda.add(tfDemandaSei = new TextField());
	    		listNodesDemanda.add(new Label("PROCESSO:"));
	    		listNodesDemanda.add(tfProcessoSei = new TextField());
	    	
	    		listNodesDemanda.add(new Label("DATA DE RECEBIMENTO:"));
	    		listNodesDemanda.add(dpDataRecebimento = new DatePicker());
	    			listNodesDemanda.add(new Label("DATA DE DISTRIBUIÇÃO:"));
	    			listNodesDemanda.add(dpDataDistribuicao = new DatePicker());
    
    prefSizeWHeLayXY = new Double[][] { 
    	{530.0,150.0,15.0,80.0},
    	{200.0,30.0,15.0,13.0},
    	{200.0,30.0,15.0,43.0},
    	{130.0,30.0,225.0,13.0},
    	{130.0,30.0,225.0,43.0},
    	{150.0,30.0,366.0,13.0},
    	{150.0,30.0,366.0,43.0},
    	{160.0,30.0,15.0,75.0},
    	{160.0,30.0,15.0,108.0},
    	{160.0,30.0,185.0,78.0},
    	{160.0,30.0,185.0,108.0},
    	{160.0,30.0,355.0,78.0},
    	{160.0,30.0,355.0,108.0},
    	};
    	
    com = new Componentes();
    com.popularTela(listNodesDemanda, prefSizeWHeLayXY, p1);
    
    ObservableList<String> obsListTiposDemanda = FXCollections
    		.observableArrayList(
    				
    				"Requerimento de Outorga"	,
    				"Memorando"	,
    				"Ofício"	,
    				"Carta",
    				"Recurso"
    				
    				); 	
    
    cbTipoDemanda.setItems(obsListTiposDemanda);
    
    listNodesEndereco.add(pEndereco = new Pane());
    
    listNodesEndereco.add(new Label("ENDERECO:"));
    listNodesEndereco.add(lblLogradouro = new Label());
    	listNodesEndereco.add(new Label("RA:"));
    	listNodesEndereco.add(lblRegiaoAdministrativa = new Label());
		    listNodesEndereco.add(new Label("LAT:"));
		    listNodesEndereco.add(lblLatitude = new Label());
			    listNodesEndereco.add(new Label("LON:"));
			    listNodesEndereco.add(lblLongitude = new Label());
			    	listNodesEndereco.add(btnTelaEndereco = new Button("<<<"));
    
    prefSizeWHeLayXY = new Double[][] { 

    	{380.0,110.0,580.0,100.0}, 
	    	{80.0,30.0,10.0,10.0}, 
		    {275.0,30.0,96.0,10.0}, 
			    	{40.0,30.0,10.0,39.0}, 
				    {160.0,30.0,50.0,39.0}, 
					    	{40.0,30.0,10.0,70.0}, 
						    {100.0,30.0,49.0,70.0}, 
							    	{40.0,30.0,139.0,70.0}, 
								    {100.0,30.0,190.0,70.0}, 
								    		{70.0,20.0,296.0,74.0} 
	    	
    };
    	   
    Componentes comEndereco = new Componentes();
    comEndereco.popularTela(listNodesEndereco, prefSizeWHeLayXY, p1);
    
    listNodesPersistencia.add(pPersistencia = new Pane());
    	listNodesPersistencia.add(btnNovo = new Button("NOVO"));
    		listNodesPersistencia.add(btnSalvar = new Button("SALVAR"));
			    listNodesPersistencia.add(btnEditar = new Button("EDITAR"));
			    	listNodesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
			    		listNodesPersistencia.add(btnCancelar = new Button("CANCELAR"));
    
			    			listNodesPersistencia.add(tfPesquisar = new TextField());
    
			    				listNodesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));
    
    prefSizeWHeLayXY = new Double[][] { 
    	
    	{930.0,60.0,25.0,240.0}, 
	    	{95.0,25.0,17.0,18.0}, 
		    	{95.0,25.0,123.0,18.0}, 
			    	{95.0,25.0,228.0,18.0}, 
				    	{95.0,25.0,333.0,18.0}, 
					    	{95.0,25.0,438.0,18.0}, 
						    	{265.0,25.0,543.0,18.0}, 
						    		{95.0,25.0,818.0,18.0} }; 
	    	
	com = new Componentes();
    com.popularTela(listNodesPersistencia, prefSizeWHeLayXY, p1);
    
    modularBotoesDemanda();
   
    acionarBotoesDemanda();
    
    selecionarDemanda ();
    
  }
  
  public void acionarBotoesDemanda() {
   
    btnNovo.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
        	habilitarDemanda();
        }
    });
    
    
	    btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	salvarDemanda();
	        }
	    });
    
    
		    btnEditar.setOnAction(new EventHandler<ActionEvent>() {
		        @Override public void handle(ActionEvent e) {
		        	editarDemanda();
		        	System.out.println("bnt editar demanda clicado");
		        }
		    });
   
    
    
			    btnExcluir.setOnAction(new EventHandler<ActionEvent>() {
			        @Override public void handle(ActionEvent e) {
			        	excluirDemanda();
			        }
			    });
   
    
				    btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
				        @Override public void handle(ActionEvent e) {
				        	cancelarDemanda();
				        }
				    });
   
    
					    btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {
					        @Override public void handle(ActionEvent e) {
					        	pesquisarDemanda();
					        }
					    });
					   
    
    btnTelaEndereco.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
        	inicializarTelaEndereco();
        	
        	//TelaEnderecoControlador.telaEndCon.setObjetoDeEdicao(demanda);
        	
        }
    });
    
    
    tfPesquisar.setOnKeyReleased(event -> {
		  if (event.getCode() == KeyCode.ENTER){
		     btnPesquisar.fire();
		  }
		});
   
    
    btnTelaProcesso.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
        	
        	inicializarTelaProcesso ();
        
        	TelaProcessoControlador.telaProCon.setDemanda(demanda);
        	
        	
        }
    });
    
  }
  
  ArrayList<Node> listNodesProcesso = new ArrayList<Node>();
  
  Pane pDadosDemanda;
  	ComboBox<String> cbTipoDemanda;
  		TextField tfNumeroDemanda;
  			TextField tfDemandaSei;
  				TextField tfProcessoSei;
  					DatePicker dpDataDistribuicao;
  						DatePicker dpDataRecebimento;
  
  					ArrayList<Node> listNodesDemanda = new ArrayList<Node>();
  
  						Pane pPersistencia = new Pane();
  
		  					Button btnNovo;
		  						Button btnSalvar;
								  Button btnEditar;
									  Button btnExcluir;
									  	Button btnCancelar;
									  		Button btnPesquisar;
									  			TextField tfPesquisar;
									  			
									  				ArrayList<Node> listNodesPersistencia = new ArrayList<Node>();
		  
							  				Pane pEndereco = new Pane();
											  Label lblLogradouro;
											  	Label lblRegiaoAdministrativa;
											  		Label lblLatitude;
											  			Label lblLongitude;
											  				Button btnTelaEndereco;
											  				
											  					ArrayList<Node> listNodesEndereco = new ArrayList<Node>();
  
											  
  ObservableList<Demanda> obsList = FXCollections.observableArrayList();
  	TranslateTransition transicaoTelaProEsquerda;
  		TranslateTransition transicaoTelaProDireita;
  			Pane pTelaProcesso;
  				Double dblTranslateTelaProcesso;
  					Pane pTelaProcessoDemanda;
  						
  public void listarDemandas(String strPesquisa) {
	  
    DemandaDao demandaDao = new DemandaDao();
    List<Demanda> demandaList = demandaDao.listarDemandas(strPesquisa);
    
    if (!obsList.isEmpty()) {
      obsList.clear();
      
    }
    
    List<Demanda> iList = demandaList;
    
    for (Demanda d : iList) {
      obsList.add(d);
    }
    
    tvLista.setItems(obsList);
    
  }
  
  TranslateTransition transicao_TE_Direita;
  TranslateTransition transicao_TE_Esquerda;
  Pane pTelaEndereco;
  Double dblTransicao_TE_Endereco;
  
  public void inicializarTelaProcesso () {
	  
    if (pTelaProcesso == null) {
    	
    	pTelaProcesso = new Pane();
    	pTelaProcesso.setPrefSize(500.0, 500.0);
    	
    	pTelaProcesso.setStyle("-fx-background-color: red;");
	    
    	Pane p = new Pane();
    	
    	p.setStyle("-fx-background-color: blue;");
    	
    	System.out.println("intControlador na tab Demanda " + intControlador);
		//telaProCon = new TelaProcessoControlador();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TelaProcesso.fxml"));
		loader.setRoot(p);
		//loader.setController(new TelaProcessoControlador(intControlador));
	
		try {
			loader.load();
		}
			catch (IOException e)	{
				System.out.println("erro leitura do pane");
				e.printStackTrace();
			}
	
		pTelaProcesso.getChildren().add(p);
		
		p1.getChildren().add(pTelaProcesso);
	
			transicaoTelaProEsquerda = new TranslateTransition(new Duration(350.0), pTelaProcesso);
				transicaoTelaProEsquerda.setToX(15.0);
			  
			transicaoTelaProDireita = new TranslateTransition(new Duration(350.0), pTelaProcesso);
				transicaoTelaProDireita.setToX(1300.0);
	  
				pTelaProcesso.setTranslateX(1300.0);
	  
	}
    
    movimentarTelaProcesso (15.0);
    
  }
  
  public void movimentarTelaProcesso (Double dbltransEsquerda)	{
	  
	  /*
    if (demanda.getDemID() == 0) {
    	
      lbl_TP_Demanda.setText("Não há demanda selecionada!!!");
      lbl_TP_Demanda.setTextFill(Color.RED);
      
    }
    else {
    	
      lbl_TP_Demanda.setText(demanda
        .getDemDocumento() + ", Sei nº" + demanda
        	.getDemDocumentoSEI() + ", Processo nº " + demanda
        		.getDemProcessoSEI());
      
      	lbl_TP_Demanda.setTextFill(Color.BLACK);
    }*/
    
	  dblTranslateTelaProcesso = Double.valueOf(pTelaProcesso.getTranslateX());
    
	  if (dblTranslateTelaProcesso.equals(dbltransEsquerda)) {
	    	transicaoTelaProDireita.play();
	    } else {
	    	transicaoTelaProEsquerda.play();
	    }
	    
	  }
	  
  public void inicializarTelaEndereco ()
  {
    if (pTelaEndereco == null)
    {
      pTelaEndereco = new Pane();
      pTelaEndereco.setPrefSize(500.0, 500.0);
      
      Pane p = new Pane();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TelaEndereco.fxml"));
      loader.setRoot(p);
     // loader.setController(new TelaEnderecoControlador(intControlador));
      try
      {
        loader.load();
      }
      catch (IOException e)
      {
        System.out.println("erro leitura do pane - chamada legisla��o");
        e.printStackTrace();
      }
      pTelaEndereco.getChildren().add(p);
      
      p1.getChildren().add(pTelaEndereco);
      
      transicao_TE_Esquerda = new TranslateTransition(new Duration(350.0D), pTelaEndereco);
      transicao_TE_Esquerda.setToX(15.0D);
      
      transicao_TE_Direita = new TranslateTransition(new Duration(350.0D), pTelaEndereco);
      transicao_TE_Direita.setToX(1300.0D);
      
      pTelaEndereco.setTranslateX(1300.0D);
    }
    movimentarTelaEndereco(15.0);
  }
  
  public void movimentarTelaEndereco(Double dbltransEsquerda){
	  
    System.out.println("movimentar tela endereco ");
    
    dblTransicao_TE_Endereco = Double.valueOf(pTelaEndereco.getTranslateX());
    if (dblTransicao_TE_Endereco.equals(dbltransEsquerda)) {
      transicao_TE_Direita.play();
    } else {
      transicao_TE_Esquerda.play();
    }
    
    
  }
  
  public void modularBotoesDemanda() {
	  
    cbTipoDemanda.setDisable(true);
    tfNumeroDemanda.setDisable(true);
    tfNumeroDemanda.setDisable(true);
    tfDemandaSei.setDisable(true);
    tfProcessoSei.setDisable(true);
    
    dpDataDistribuicao.setDisable(true);
    dpDataRecebimento.setDisable(true);
    
    btnSalvar.setDisable(true);
    btnEditar.setDisable(true);
    btnExcluir.setDisable(true);
    
    btnNovo.setDisable(false);
  }
  
  //-- selecionar demandas -- //
  public void selecionarDemanda () {
		
	// TableView - selecionar demandas ao clicar //
	tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
		
		public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
		
		Demanda dem = (Demanda) newValue;
		
		if (dem == null) {
			
			cbTipoDemanda.setValue(null);
			tfNumeroDemanda.setText("");
			tfDemandaSei.setText("");
			tfProcessoSei.setText("");
			
			dpDataRecebimento.getEditor().clear();
			dpDataDistribuicao.getEditor().clear();
		
			btnNovo.setDisable(true);
			btnSalvar.setDisable(true);
			btnEditar.setDisable(false);
			btnExcluir.setDisable(false);
			btnCancelar.setDisable(false);
			
		} else {

			demanda = dem;
			
			// preencher os campos //
			cbTipoDemanda.setValue(dem.getDemTipo());
			tfNumeroDemanda.setText(dem.getDemNumero());
			tfDemandaSei.setText(dem.getDemNumeroSEI());
			tfProcessoSei.setText(dem.getDemProcesso());
			
			if (dem.getDemDistribuicao() == null) {
				dpDataDistribuicao.setValue(null);
				
 				} else {
 					Date dataDis = dem.getDemDistribuicao();
 					dpDataDistribuicao.setValue(dataDis.toLocalDate());
 				}
			
				if (dem.getDemRecebimento() == null) {
					dpDataRecebimento.setValue(null);
	 				} else {
	 					
	 					Date dataRec = dem.getDemRecebimento();
	 					dpDataRecebimento.setValue(dataRec.toLocalDate());
	 				}
			
			
			// endereço relacionado //
			if (dem.getDemEnderecoFK() != null) {
				
					lblLogradouro.setText(dem.getDemEnderecoFK().getEndLogradouro());
					lblRegiaoAdministrativa.setText(dem.getDemEnderecoFK().getEndRAFK().getRaNome());
					lblLatitude.setText(dem.getDemEnderecoFK().getEndDDLatitude().toString());
					lblLongitude.setText(dem.getDemEnderecoFK().getEndDDLongitude().toString());
					 		
					lblLogradouro.setStyle("-fx-text-fill: #4A4A4A;"); 
					
				
					// listar as interferencias
					//Set<Demanda> setDem = dem.getDemEnderecoFK().getDemandas();
					Endereco end = dem.getDemEnderecoFK();
						
					// preparar strings para transmitir para o javascript pelo metodo 'setEnderecoInterferencias()'
						
						String strInfoDemandas = "";
					
							String strEndereco = end.getEndDDLatitude() + "," + end.getEndDDLongitude();
							
						
					/* chamar os metodo necessarios, primeiro as coordenadas e detalhes, 
						zoom do mapa e deois centralizar o mapa de acordo com o endereco
						*/
					googleMaps.mostrarDemandas(strEndereco, strInfoDemandas);
					googleMaps.setZoom (11);
					googleMaps.setMapCenter(end.getEndDDLatitude().toString(), end.getEndDDLongitude().toString());
				
				
			} else {
				
					lblLogradouro.setText("Sem endereço cadastrado!");
					lblRegiaoAdministrativa.setText("");
					lblLatitude.setText("");
					lblLongitude.setText("");
				
					lblLogradouro.setStyle("-fx-text-fill: #FF0000;"); // fonte color: vermelho
			}
			
			if (dem.getDemProcessoFK() != null) {
				
				lblProcessoPrincipal.setText(
						
						dem.getDemProcessoFK().getProSEI()
						+ ", Interessado n° " + dem.getDemProcessoFK().getProInteressado()
						
						);
				lblProcessoPrincipal.setStyle("-fx-text-fill: #4A4A4A;"); 
		
				
			} else {
				
				lblProcessoPrincipal.setText("Não está relacionado a nenhum processo principal!");
				lblProcessoPrincipal.setStyle("-fx-text-fill: #FF0000;");
			}
			
			
			
			// mostrar data de atualizacao //
			FormatoData d = new FormatoData();
			try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(dem.getDemAtualizacao()));  // d.formatarData(demanda.getDemAtualizacao())
					lblDataAtualizacao.setTextFill(Color.BLACK);
			}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
					lblDataAtualizacao.setTextFill(Color.RED);}
			
			//Levar a demanda para cadastrar o endereco //
			//tabEndCon.setDemanda(demanda);
			//enditarEnderecoControlador.setObjetoDeEdicao(demanda);
			
			System.out.println("valor do int controlador " + intControlador);
			
		    if (intControlador == 0) {
		    	//TabEnderecoControlador.controladorAtendimento.setDemanda(dem);
	
		 	}
		     
		    if (intControlador == 1) {
		 		//TabEnderecoControlador.controladorFiscalizacao.setDemanda(dem);
		    }
		    
		    if (intControlador == 2) {
	 			//TabEnderecoControlador.controladorOutorga.setDemanda(dem);
		    }
			
		    
			// copiar número sei da demanda ao selecionar //
			Clipboard clip = Clipboard.getSystemClipboard();
            ClipboardContent conteudo = new ClipboardContent();
            conteudo.putString(dem.getDemNumeroSEI());
            clip.setContent(conteudo);
			
			// habilitar e desabilitar botões //
			btnNovo.setDisable(true);
			btnSalvar.setDisable(true);
			btnEditar.setDisable(false);
			btnExcluir.setDisable(false);
			btnCancelar.setDisable(false);
			
		} // fim do else
		
	} // fim do metodo changed
			
	}); // fim do selection model
		
	}
  }
