package controladores.principal;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.DemandaDao;
import entidades.Demanda;
import entidades.Endereco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;

public class TelaDemandaControlador implements Initializable {
	
	@FXML Pane pTelaDemanda;
	
	Endereco endereco;
	
	/* recebimento do endereco e preenchimento na parte superior - ENDEREÇO: ... */
	public void setEndereco (Endereco endereco)  {
		
		this.endereco = endereco;
		
		if (endereco.getEndLogradouro() != null) {
			
			// preencher o label com a demanda selecionada //
			lblEndereco.setText(
					endereco.getEndLogradouro()
					+ ", RA:" + endereco.getEndRAFK().getRaNome()
					
					);
			lblEndereco.setStyle("-fx-text-fill: #4A4A4A;"); 
	
			
		} else {
			
			lblEndereco.setText(
					"não há endereco relacionado! "
					);
			lblEndereco.setStyle("-fx-text-fill: #FF0000;");
		}
		
		
		
	}
	
	Pane pPrincipal = new Pane();
	
	Pane pEndereco;
		Label lblEndereco;
			Button btnEndereco;

				ArrayList<Node> listComponentesEndereco = new ArrayList<Node>();
				
	Pane pDadosDemanda;
  	ComboBox<String> cbTipoDemanda;
  		TextField tfNumeroDemanda;
  			TextField tfDemandaSei;
  				TextField tfProcessoSei;
  					DatePicker dpDataDistribuicao;
  						DatePicker dpDataRecebimento;
  
  					ArrayList<Node> listComponentesDemanda = new ArrayList<Node>();
  					
  					ObservableList<Demanda> obsList = FXCollections.observableArrayList();
			  
	
	
	 Pane pPersistencia;
	  	Button btnNovo;
	  		Button btnSalvar;
	  			Button btnEditar;
	  				Button btnExcluir;
	  					Button btnCancelar;
	  						Button btnPesquisar;
	  						
	  							TextField tfPesquisar;
	  							
	  								ArrayList<Node> listComponentesPersistencia = new ArrayList<Node>();
				  								
	Label lblDataAtualizacao= new Label();
  
  	TableView<Demanda> tvLista = new TableView<Demanda>();
		  TableColumn<Demanda, String> tcTipoDemanda = new TableColumn<Demanda, String>("Tipo");
  		  	TableColumn<Demanda, String> tcNumeroDemanda = new TableColumn<Demanda, String>("Número");
  		  		TableColumn<Demanda, String> tcNumeroDemandaSEI = new TableColumn<Demanda, String>("Número SEI");
  		  			TableColumn<Demanda, String> tcNumeroProcesso = new TableColumn<Demanda, String>("Número do Processo");
    
						ObservableList<Demanda> obsListDemandas = FXCollections.observableArrayList();
				  							  							
				  							  
	Double prefSizeWHeLayXY [][];
	
	Componentes com;
	
	public static TelaDemandaControlador telaDemCon;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		telaDemCon = this;
		
		inicializarComponentes ();
	   
		pTelaDemanda.getChildren().addAll(pPrincipal);
		
		selecionarDemanda();
		modularBotoesDemanda();
		acionarBotoesDemanda();
	    
	}
	
	int intControlador;
	/* construtor para trazer o intControlador correto. 0 para atendimento e 1 para fiscalizacao */
	public TelaDemandaControlador (int intControlador) {
		  this.intControlador = intControlador;
	}
	
	public void inicializarComponentes () {


		listComponentesEndereco.add(pEndereco = new Pane());
		listComponentesEndereco.add(new Label ("ENDEREÇO"));
		listComponentesEndereco.add(lblEndereco = new Label ());
		listComponentesEndereco.add(btnEndereco = new  Button(">>>"));

		prefSizeWHeLayXY = new Double[][] { 
			{850.0,60.0,20.0,10.0},
			{90.0,30.0,15.0,15.0},
			{648.0,30.0,110.0,15.0},
			{65.0,25.0,770.0,19.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesEndereco, prefSizeWHeLayXY, pPrincipal);


		listComponentesDemanda.add(pDadosDemanda = new Pane());

		listComponentesDemanda.add(new Label("TIPO:"));
		listComponentesDemanda.add(cbTipoDemanda =  new ComboBox<String>());
		listComponentesDemanda.add(new Label("NUMERAÇÃO:"));
		listComponentesDemanda.add(tfNumeroDemanda = new TextField());
		listComponentesDemanda.add(new Label("SEI:"));
		listComponentesDemanda.add(tfDemandaSei = new TextField());
		listComponentesDemanda.add(new Label("PROCESSO:"));
		listComponentesDemanda.add(tfProcessoSei = new TextField());

		listComponentesDemanda.add(new Label("DATA DE RECEBIMENTO:"));
		listComponentesDemanda.add(dpDataRecebimento = new DatePicker());
		listComponentesDemanda.add(new Label("DATA DE DISTRIBUIÇÃO:"));
		listComponentesDemanda.add(dpDataDistribuicao = new DatePicker());

		prefSizeWHeLayXY = new Double[][] { 
			{850.0,149.0,19.0,85.0},
			{200.0,30.0,90.0,5.0},
			{200.0,30.0,90.0,35.0},
			{130.0,30.0,300.0,5.0},
			{130.0,30.0,300.0,35.0},
			{150.0,30.0,441.0,5.0},
			{150.0,30.0,441.0,35.0},
			{160.0,30.0,601.0,5.0},
			{160.0,30.0,601.0,35.0},
			{160.0,30.0,265.0,70.0},
			{160.0,30.0,265.0,105.0},
			{160.0,30.0,435.0,70.0},
			{160.0,30.0,435.0,106.0},
		};

		pTelaDemanda.setStyle("-fx-background-color: rgba(223,226,227, 0.7);");

		pPrincipal.setStyle("-fx-background-color: white");
		pPrincipal.setPrefSize(890, 670);
		pPrincipal.setLayoutX(60);
		pPrincipal.setLayoutY(0.0);


		com = new Componentes();
		com.popularTela(listComponentesDemanda, prefSizeWHeLayXY, pPrincipal);


		ObservableList<String> obsListTiposDemanda = FXCollections
				.observableArrayList(

						"Requerimento de Outorga"	,
						"Memorando"	,
						"Ofício"	,
						"Carta",
						"Recurso"

						); 	

		cbTipoDemanda.setItems(obsListTiposDemanda);



		listComponentesPersistencia.add(pPersistencia = new Pane());
		listComponentesPersistencia.add(btnNovo = new Button("NOVO"));
		listComponentesPersistencia.add(btnSalvar = new Button("SALVAR"));
		listComponentesPersistencia.add(btnEditar = new Button("EDITAR"));
		listComponentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
		listComponentesPersistencia.add(btnCancelar = new Button("CANCELAR"));

		listComponentesPersistencia.add(tfPesquisar = new TextField());

		listComponentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		prefSizeWHeLayXY = new Double[][] { 

			{850.0,60.0,19.0,245.0},
			{95.0,25.0,15.0,18.0},
			{95.0,25.0,120.0,18.0},
			{95.0,25.0,225.0,18.0},
			{95.0,25.0,330.0,18.0},
			{95.0,25.0,435.0,18.0},
			{190.0,25.0,540.0,18.0},
			{95.0,25.0,740.0,18.0},

		};

		com = new Componentes();
		com.popularTela(listComponentesPersistencia, prefSizeWHeLayXY, pPrincipal);


		tcTipoDemanda.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demTipo"));
		tcNumeroDemanda.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demNumero"));
		tcNumeroDemandaSEI.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demNumeroSEI"));
		tcNumeroProcesso.setCellValueFactory(new PropertyValueFactory<Demanda, String>("demProcesso"));

		tcTipoDemanda.setPrefWidth(200.0);
		tcNumeroDemanda.setPrefWidth(200.0);
		tcNumeroDemandaSEI.setPrefWidth(200.0);
		tcNumeroProcesso.setPrefWidth(210.0);

		tvLista.setPrefSize(840.0, 185.0);
		tvLista.setLayoutX(25.0);
		tvLista.setLayoutY(320.0);

		tvLista.getColumns().add(tcTipoDemanda); //, tcDocsSEI, tcProcsSEI });
		tvLista.getColumns().add(tcNumeroDemanda);
		tvLista.getColumns().add(tcNumeroDemandaSEI);
		tvLista.getColumns().add(tcNumeroProcesso);

		tvLista.setItems(obsList);

		lblDataAtualizacao.setPrefSize(247.0, 22.0);
		lblDataAtualizacao.setLayoutX(615.0);
		lblDataAtualizacao.setLayoutY(515.0);

		pPrincipal.getChildren().addAll(tvLista, lblDataAtualizacao);

		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {

			@Override public void handle(ActionEvent e) {

				/*
				if (intControlador == 0) {
					TabEnderecoControlador.controladorAtendimento.movimentarTelaDemanda(15.0);
				}
				if (intControlador == 1) {
					TabEnderecoControlador.controladorFiscalizacao.movimentarTelaDemanda(15.0);
				}
				if (intControlador == 2) {
					TabEnderecoControlador.controladorOutorga.movimentarTelaDemanda(15.0);
				}
				*/

				System.out.println("valor do intControlador TelaDemanda " + intControlador);

			}
		});

		selecionarDemanda ();

	}
	
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
			        
			        /* teste salvamento demanda com endereco */ 
			        if (!(endereco.getEndLogradouro() == null)) {
			        	
				        dem.setDemEnderecoFK(endereco);
			        }
			      
			        
			        dao.salvarDemanda(dem);
			        
			        
			        /* passar a demanda para a tabEndereco e preencher a lblDemanda */ 
				    if (intControlador == 0) {
				    	//TabEnderecoControlador.controladorAtendimento.setDemanda(dem);
			
				 	}
				     
				    if (intControlador == 1) {
				 			//TabEnderecoControlador.controladorFiscalizacao.setDemanda(dem);
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
		  
		    if (cbTipoDemanda.isDisable()) {
		    	
		      cbTipoDemanda.setDisable(false);
		      tfNumeroDemanda.setDisable(false);
		      tfDemandaSei.setDisable(false);
		      tfProcessoSei.setDisable(false);
		      
		      dpDataDistribuicao.setDisable(false);
		      dpDataRecebimento.setDisable(false);
		      
		    }
		    
			    else if ((tfDemandaSei.getText().isEmpty()) || (tfProcessoSei.getText().isEmpty())) {
			    	
			      Alerta a = new Alerta();
			      a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
			      
			    }
		    
				    else
				    {
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
				      
				      /* teste salvamento demanda com endereco */ 
				        if (!(endereco.getEndLogradouro() == null)) {
				        	
					        dem.setDemEnderecoFK(endereco);
				        }
				      
				      dDao.mergeDemanda(dem);
				      
				      obsList.remove(dem);
				      obsList.add(dem);
				     
				    /* passar a demanda para a tabEndereco e preencher a lblDemanda */ 
				    if (intControlador == 0) {
				    //	TabEnderecoControlador.controladorAtendimento.setDemanda(dem);
			
				 	}
				     
				    if (intControlador == 1) {
				 		//	TabEnderecoControlador.controladorFiscalizacao.setDemanda(dem);
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
	  
	String strPesquisa = "";
	  
	public void pesquisarDemanda()	{
		  
	    strPesquisa = tfPesquisar.getText();
	    
	    listarDemandas(strPesquisa);
	    
	    modularBotoesDemanda();
	    
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
				
				
				// mostrar data de atualizacao //
				FormatoData d = new FormatoData();
				try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(dem.getDemAtualizacao()));  // d.formatarData(demanda.getDemAtualizacao())
						lblDataAtualizacao.setTextFill(Color.BLACK);
				}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
						lblDataAtualizacao.setTextFill(Color.RED);}
			
				
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

    tfPesquisar.setOnKeyReleased(event -> {
		  if (event.getCode() == KeyCode.ENTER){
		     btnPesquisar.fire();
		  }
	});
	
    /* Evento - ao clicar no pane fechar a TelaDemanda */
    /*
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
    	   @Override 
    	   public void handle(MouseEvent e) { 
    		   
    		   if (intControlador == 0) {
	        		TabEnderecoControlador.controladorAtendimento.movimentarTelaDemanda(15.0);
	        	}
	        	if (intControlador == 1) {
	        		TabEnderecoControlador.controladorFiscalizacao.movimentarTelaDemanda(15.0);
	        	}
    	      
    	   } 
    	};   
    	
    pTelaDemanda.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    */
			
	} // fim metodo habilitar acoes nos botoes
	
}
