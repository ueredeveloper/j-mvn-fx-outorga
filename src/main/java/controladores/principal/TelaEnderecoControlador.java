package controladores.principal;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


import dao.EnderecoDao;
import entidades.Documento;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.RA;
import entidades.Usuario;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;
import principal.ListasComboBox;

public class TelaEnderecoControlador implements Initializable {
	
	Object objetoDeEdicao = new Object();
	
	public void setObjetoDeEdicao (Object objetoDeEdicao) {
		
		this.objetoDeEdicao = objetoDeEdicao;
		
		System.out.println(objetoDeEdicao.getClass().getName());
		
		if (objetoDeEdicao != null) {
		
			if (objetoDeEdicao.getClass().getName().equals("entidades.Documento") || objetoDeEdicao.getClass().getName().equals("entidades.Parecer")) {
				
				lblTipoObjeto.setText("DOCUMENTO:");
				
				if (((Documento) objetoDeEdicao).getDocTipo() != null) {
					
					lblDescricaoObjeto.setText(
							((Documento) objetoDeEdicao).getDocTipo()
							+ ", Sei n° " + ((Documento) objetoDeEdicao).getDocSEI()
							+ ", Processo n° " + ((Documento) objetoDeEdicao).getDocProcesso()
							);
					
					lblDescricaoObjeto.setStyle("-fx-text-fill: #4A4A4A;"); 
			
					
				} else {
					
					lblDescricaoObjeto.setText(
							"não há documento relacionado! "
							);
					lblDescricaoObjeto.setStyle("-fx-text-fill: #FF0000;");
				}
				
			}
			
			else if (objetoDeEdicao.getClass().getName() == "entidades.Subterranea" || objetoDeEdicao.getClass().getName() == "entidades.Superficial" ) {
				
				lblTipoObjeto.setText("INTERFERÊNCIA:");
				
				if (((Interferencia) objetoDeEdicao).getInterDDLatitude() != null) {
					
					lblDescricaoObjeto.setText(
							((Interferencia) objetoDeEdicao).getInterTipoInterferenciaFK().getTipoInterDescricao()
							+ ", Latitude: " + ((Interferencia) objetoDeEdicao).getInterDDLatitude() + ", Longitude: " + ((Interferencia) objetoDeEdicao).getInterDDLongitude()
							);
					
					lblDescricaoObjeto.setStyle("-fx-text-fill: #4A4A4A;"); 
			
					
				} else {
					
					lblDescricaoObjeto.setText(
							"não há endereco relacionado! "
							);
					lblDescricaoObjeto.setStyle("-fx-text-fill: #FF0000;");
				}
				
			
				
			}
			
			else if (objetoDeEdicao.getClass().getName() == "entidades.Usuario") {
				
				lblTipoObjeto.setText("USUÁRIO:");
				
				
				if (((Usuario) objetoDeEdicao).getUsNome() != null) {
					

					lblDescricaoObjeto.setText(
							((Usuario) objetoDeEdicao).getUsNome() + "| CPF/CNPJ: " + ((Usuario) objetoDeEdicao).getUsCPFCNPJ());
					
					lblDescricaoObjeto.setStyle("-fx-text-fill: #4A4A4A;"); 
			
					
				} else {
					
					lblDescricaoObjeto.setText(
							"não há usuário relacionado! "
							);
					lblDescricaoObjeto.setStyle("-fx-text-fill: #FF0000;");
				}

				
				
			}
			
			
			else {
				
				/*
				 * esse else só acontece quando nao e identificada a interferencia subterranea ou superficial, por isso colocarei o lbltipo especifico para a interferencia
				 */
				
				lblTipoObjeto.setText("INTERFERÊNCIA:");
				
				lblDescricaoObjeto.setText(
						"não há interferência relacionada! "
						);
				lblDescricaoObjeto.setStyle("-fx-text-fill: #FF0000;");
				
			}
		
		}
		

	}
	
	@FXML Pane pTelaEndereco;
											 
	//-- TableView endereco --//
	private TableView <Endereco> tvLista = new TableView<>();

	TableColumn<Endereco, String> tcLogradouro = new TableColumn<>("Endereço");
	TableColumn<Endereco, String> tcEndRA = new TableColumn<>("Região Administrativa");
	TableColumn<Endereco, String> tcCidade = new TableColumn<>("CEP");
	
	Label lblDataAtualizacao = new Label();
	
	Endereco endereco = new Endereco();
	
	
	ObservableList<String> olEndUF = FXCollections
			.observableArrayList("DF" , "GO", "Outro");
	
	//public static TelaEnderecoControlador telaEndCon;
	
	/* lista de enderecos para adicionar a tableview */
	ObservableList<Endereco> obsList = FXCollections.observableArrayList();
	
	RA ra = new RA ();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		pTelaEndereco.setStyle("-fx-background-color:#FFFFFF;");
		
		inicializarComponentes ();
		    
		pTelaEndereco.getChildren().addAll(pPrincipal);
	    
	    // para trazer o valor da entidade principal, no caso Endereco
		tcLogradouro.setCellValueFactory(new PropertyValueFactory<Endereco, String>("endLogradouro"));
		// para trazer valor de outra entidade, no caso RA
	    tcEndRA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Endereco, String>, ObservableValue<String>>() {
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Endereco, String> e) {
		    	return new SimpleStringProperty(e.getValue().getEndRAFK().getRaNome());
		       
		    }
		});
		 
	    cbRA.setItems(ListasComboBox.obsListRA);
	    cbRA.setValue("Plano Piloto");
	    
	    cbUF.setItems(olEndUF);
	    cbUF.setValue("DF");
	    
	    
	    cbRA.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	    	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
	    		Number old_value, Number new_value) {
	    		// setar id da RA selecinada de acordo com a selecao no ComboBox
	    		ra.setRaID((Integer) new_value + 1);
	    
            }
	    });
	    
	    cbRA.getSelectionModel()
	    	.selectedItemProperty()
	    	.addListener( 
	    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
	    	// setar nome (descricao) da RA selecinada de acordo com a selecao no ComboBox
	    	ra.setRaNome(new_value)
	    
	    );
		 
	    acionarBotoes ();
	    selecionarEndereco();
		
	} // FIM INITIALIZE
	
	/* array de posicoes prefWidth prefHeight Layout Y e X */
	Double prefSizeWHeLayXY [][];
	
	/* classe necessaria para atribuir as medidas pref width e heigh layout x e y para os componentes de cada tab ou tela */
	Componentes com;
	
	/*@intControlador - valor referente ao controlador chamado. 0 para atendimento e 1 para fiscalizacao
	 *  Utilizado no método acionarBotoes e assim movimentar a tela a partir do controlador chamado
	 */
	
	Pane pPrincipal = new Pane();
	
	Pane pDocumento;
	Label lblTipoObjeto;
	Label lblDescricaoObjeto;
	Button btnDocumento;

	ArrayList<Node> componentesDocumento = new ArrayList<Node>();

	Pane pDadosEndereco;
	TextField tfLogradouro;
	ComboBox<String> cbRA;
	TextField tfCEP;
	TextField tfCidade;
	ComboBox<String> cbUF;
	TextField tfLatitude;
	TextField tfLongitude;
	Button btnLatLon;

	ArrayList<Node> componentesEndereco = new ArrayList<Node>();

	Pane pPersistencia;
	Button btnNovo;
	Button btnSalvar;
	Button btnEditar;
	Button btnExcluir;
	Button btnCancelar;
	Button btnPesquisar;

	TextField tfPesquisar;

	ArrayList<Node> componentesPersistencia = new ArrayList<Node>();

	public void inicializarComponentes () {

		componentesDocumento.add(pDocumento = new Pane());
		componentesDocumento.add(lblTipoObjeto = new Label());
		componentesDocumento.add(lblDescricaoObjeto = new Label());
		componentesDocumento.add(btnDocumento = new Button(">>>"));

		prefSizeWHeLayXY = new Double [][] { 
			{850.0,60.0,25.0,14.0},
			{115.0,30.0,15.0,15.0},
			{630.0,30.0,130.0,15.0},
			{65.0,25.0,771.0,18.0},
		};

		com = new Componentes();
		com.popularTela(componentesDocumento, prefSizeWHeLayXY, pPrincipal);

		componentesEndereco.add(pDadosEndereco = new Pane());
		componentesEndereco.add(new Label("ENDEREÇO DO EMPREENDIMENTO:"));
		componentesEndereco.add(tfLogradouro = new TextField());
		componentesEndereco.add(new Label("REGIÃO ADMINISTRATIVA:"));
		componentesEndereco.add(cbRA = new  ComboBox<>());
		componentesEndereco.add(new Label("CEP:"));
		componentesEndereco.add(tfCEP = new TextField());
		componentesEndereco.add(new Label("CIDADE:"));
		componentesEndereco.add(tfCidade = new TextField());
		componentesEndereco.add(new Label("UF:"));
		componentesEndereco.add(cbUF = new  ComboBox<>());
		componentesEndereco.add(new Label("LATITUDE (Y):"));
		componentesEndereco.add(tfLatitude = new TextField());
		componentesEndereco.add(new Label("LONGITUDE(X):"));
		componentesEndereco.add(tfLongitude = new TextField());
		componentesEndereco.add(btnLatLon = new Button());

		cbRA.setItems(ListasComboBox.obsListRA);
		cbRA.setValue("Plano Piloto");
		cbUF.setItems(olEndUF);

		prefSizeWHeLayXY = new Double [][] { 

			{850.0,125.0,20.0,85.0},
			{330.0,30.0,13.0,0.0},
			{330.0,30.0,13.0,30.0},
			{160.0,30.0,353.0,0.0},
			{160.0,30.0,353.0,30.0},
			{100.0,30.0,523.0,0.0},
			{100.0,30.0,523.0,30.0},
			{90.0,30.0,633.0,0.0},
			{90.0,30.0,633.0,30.0},
			{100.0,30.0,733.0,0.0},
			{100.0,30.0,733.0,30.0},
			{100.0,30.0,158.0,76.0},
			{140.0,30.0,258.0,76.0},
			{100.0,30.0,413.0,76.0},
			{140.0,30.0,513.0,76.0},
			{25.0,25.0,668.0,79.0},
		};

		com = new Componentes();
		com.popularTela(componentesEndereco, prefSizeWHeLayXY, pPrincipal);


		componentesPersistencia.add(pPersistencia = new Pane());
		componentesPersistencia.add(btnNovo = new Button("NOVO"));
		componentesPersistencia.add(btnSalvar = new Button("SALVAR"));
		componentesPersistencia.add(btnEditar = new Button("EDITAR"));
		componentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
		componentesPersistencia.add(btnCancelar = new Button("CANCELAR"));
		componentesPersistencia.add(tfPesquisar = new TextField());
		componentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		prefSizeWHeLayXY = new Double[][] { 
			{850.0,60.0,20.0,225.0},
			{95.0,25.0,15.0,18.0},
			{95.0,25.0,120.0,18.0},
			{95.0,25.0,225.0,18.0},
			{95.0,25.0,330.0,18.0},
			{95.0,25.0,435.0,18.0},
			{190.0,25.0,540.0,18.0},
			{95.0,25.0,740.0,18.0}, 
		}; 

		com = new Componentes();
		com.popularTela(componentesPersistencia, prefSizeWHeLayXY, pPrincipal);
		
		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});


		tcLogradouro.setPrefWidth(375);
		tcEndRA.setPrefWidth(220);
		tcCidade.setPrefWidth(220);

		tvLista.getColumns().add(tcLogradouro);
		tvLista.getColumns().add(tcEndRA);
		tvLista.getColumns().add(tcCidade);

		tvLista.setItems(obsList);

		tvLista.setPrefSize(840, 185);
		tvLista.setLayoutX(25);
		tvLista.setLayoutY(300);

		lblDataAtualizacao.setPrefSize(247, 22);
		lblDataAtualizacao.setLayoutX(615);
		lblDataAtualizacao.setLayoutY(495);

		pPrincipal.getChildren().addAll(tvLista, lblDataAtualizacao);

		pTelaEndereco.setStyle("-fx-background-color: rgba(223,226,227, 0.7);");

		pPrincipal.setStyle("-fx-background-color: white");
		pPrincipal.setPrefSize(890, 670);
		pPrincipal.setLayoutX(60);
		pPrincipal.setLayoutY(0.0);


	}
	
	TabDocumentoControlador tabDocCon;
	TabUsuarioControlador tabUsCon;
	TabInterferenciaControlador tabInterCon;
	TabParecerControlador tabParCon;
	TabAtosOutorgaControlador tabAtosOutCon;
	
	
	/*
	 * Construtor
	 */
	
	public TelaEnderecoControlador (TabDocumentoControlador tabDocCon) {
		this.tabDocCon = tabDocCon;
	}
	
	public TelaEnderecoControlador (TabUsuarioControlador tabUsCon) {
		this.tabUsCon = tabUsCon;
	}
	
	public TelaEnderecoControlador (TabInterferenciaControlador tabInterCon) {
		this.tabInterCon = tabInterCon;
	}
	
	public TelaEnderecoControlador (TabParecerControlador tabParCon) {
		this.tabParCon = tabParCon;
	}
	
	public TelaEnderecoControlador (TabAtosOutorgaControlador tabAtosOutCon) {
		this.tabAtosOutCon = tabAtosOutCon;
	}
	
	
	public void acionarBotoes () {
		  
	    btnDocumento.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	
	        	if (tabDocCon != null)
	        	tabDocCon.movimentarTelaEndereco();
	        	if (tabUsCon != null)
	        		tabUsCon.movimentarTelaEndereco();
	        	if (tabInterCon != null)
	        		tabInterCon.movimentarTelaEndereco();
	        	if (tabParCon != null)
	        		tabParCon.movimentarTelaEndereco();
	        	if (tabAtosOutCon != null)
	        		tabAtosOutCon.movimentarTelaEndereco();
	        	
	        }
	    });
	    
	    btnNovo.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	habilitarEndereco();
	        }
	    });
	    
	    btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	salvarEndereco();
	        }
	    });
	    
	    btnEditar.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	editarEndereco();
	        }
	    });
	    btnExcluir.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	excluirEndereco();
	        }
	    });
	    
	    btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	cancelarEndereco();
	        }
	    });
	    
	    btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	pesquisarEndereco();
	        }
	    });
	  
	    
	    btnLatLon.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	capturarLatitudeLongitude ();
	        }
	    });
	    
  }
	
	/* capturar as coordenadas clicadas no mapa e trazer para o cadastro do endereco */
	public void capturarLatitudeLongitude () {
		
		tfLatitude.setText( ControladorPrincipal.capturarGoogleMaps().getLat() );
		tfLongitude.setText( ControladorPrincipal.capturarGoogleMaps().getLon());
		
	}
	
	public void habilitarEndereco () {
		
		tfLogradouro.setText("");
		
		cbRA.setValue(null);
		
		tfCEP.setText("");
		tfCidade.setText("Brasília");
		
		cbUF.setValue("DF");
	
		tfLatitude.setText("");
		tfLongitude.setText("");
		
		
		tfLogradouro.setDisable(false);
		cbRA.setDisable(false);
		
		
		tfCEP.setDisable(false);
		tfCidade.setDisable(false);
		cbUF.setDisable(false);
		tfLatitude.setDisable(false);
		tfLongitude.setDisable(false);
	
		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnEditar.setDisable(true);
		
	}

	public void salvarEndereco () {

		if (tfLatitude.getText().isEmpty() || 
				tfLongitude.getText().isEmpty()) {
			
			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Coordenadas inválidas!!!", ButtonType.OK));
			
		} 
		
			else if (objetoDeEdicao == null) {
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Não há documento ou endereço selecionado!!!", ButtonType.OK));
				
			} 
		
				else {
				
				
					if (tfLogradouro.getText().isEmpty()) {
						
						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.ERROR, "Informe o logadouro do empreendimento!!!", ButtonType.OK));
						
					} else {
					
						Endereco end = new Endereco();
						
						try {
							
							end.setEndLogradouro(tfLogradouro.getText());
							end.setEndRAFK(ra);
								
							end.setEndCEP(tfCEP.getText());
							end.setEndCidade(tfCidade.getText());
							end.setEndUF(cbUF.getValue());
							
							end.setEndDDLatitude(Double.parseDouble(tfLatitude.getText()));
							end.setEndDDLongitude(Double.parseDouble(tfLongitude.getText()));
							
							end.setEndAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
							

							if (objetoDeEdicao.getClass().getName().equals("entidades.Documento") || objetoDeEdicao.getClass().getName().equals("entidades.Parecer")) {
								
								Documento doc = (Documento) objetoDeEdicao;
									doc.setDocEnderecoFK(end);
									
									Set<Documento> docList = new HashSet<>();
									docList.add(doc);
									
									end.setDocumentos(docList);
							
							}
							
							else if (objetoDeEdicao.getClass().getName().equals("entidades.Usuario")) {
								
								Usuario us = (Usuario) objetoDeEdicao;
								//us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
								
									// adiciona este endereco no setEnderecos do usuario
									us.getEnderecos().add(end);
									
									// adiciona neste endereco o id usuario selecionado
									end.setEndUsuarioFK(us);
								
							}
							
							
							else if (objetoDeEdicao.getClass().getName().equals("entidades.Subterranea") || objetoDeEdicao.getClass().getName().equals("entidades.Superficial") ) {
								
								System.out.println("nome da classe - btn edicao " + objetoDeEdicao.getClass().getName());
								
								Interferencia inter = (Interferencia) objetoDeEdicao;
								
									// adiciona este endereco no setEnderecos do usuario
									inter.setInterEnderecoFK(end);
									
									//List<Interferencia> iList = end.getInterferencias();
								
									//end.setInterferencias(iList);
									
									end.getInterferencias().add(inter);
									
								
							}
							
							
							else if (objetoDeEdicao.getClass().getName().equals("entidades.Subterranea") || objetoDeEdicao.getClass().getName().equals("entidades.Superficial") ) {
								
								
								System.out.println("nome da classe " + objetoDeEdicao.getClass().getName());
								
								
								Interferencia inter = (Interferencia) objetoDeEdicao;
								//us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
								
									// adiciona este endereco no setEnderecos do usuario
									inter.setInterEnderecoFK(end);
									
									List<Interferencia> iList = end.getInterferencias();
								
									end.setInterferencias(iList);
									
								
							}
							
							
							EnderecoDao endDao = new EnderecoDao();
							
								endDao.salvarEndereco(end); //solução para recuperar o id do endereço
								endDao.mergeEndereco(end); // assim adiciona o id end na demanda dem
							
							
							//-- modular botoes--//
							modularBotoesInicial ();
							
							obsList.remove(end);
							obsList.add(end);
										
										
										selecionarEndereco();
										
										modularBotoesInicial();
										
										Alerta a = new Alerta ();
										a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro salvo com sucesso!!!", ButtonType.OK));
										
								} 
							
								catch (Exception e) {
									
									Alerta a = new Alerta ();
									a.alertar(new Alert(Alert.AlertType.ERROR, "erro ao salvar!!!", ButtonType.OK));
									
									e.printStackTrace();
								}
				}
			
		}
		
	}
	
	int contador = 0;
	
	public void editarEndereco () {

		if (tfLogradouro.isDisable()) {

			tfLogradouro.setDisable(false);
			cbRA.setDisable(false);
			tfCEP.setDisable(false);
			tfCidade.setDisable(false);
			cbUF.setDisable(false);
			tfLatitude.setDisable(false);
			tfLongitude.setDisable(false);


		} else {

			if (tfLatitude.getText().isEmpty()|| tfLongitude.getText().isEmpty() ) {
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Coordenadas inválidas!!!", ButtonType.OK));
				// colocar para não aceitar texto e somente número
			} 

			else if (objetoDeEdicao == null) {
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "não selecionado(a)!!!", ButtonType.OK));
			}

			else {

				Endereco end = new Endereco ();

				end = tvLista.getSelectionModel().getSelectedItem();
				
				end.setEndLogradouro(tfLogradouro.getText());
	
				end.setEndRAFK(ra);
				
				end.setEndCEP(tfCEP.getText());
				end.setEndCidade(tfCidade.getText());
				end.setEndUF(cbUF.getValue());
				end.setEndDDLatitude(Double.parseDouble(tfLatitude.getText()));
				end.setEndDDLongitude(Double.parseDouble(tfLongitude.getText()));

				end.setEndAtualizacao(
						Timestamp.valueOf((LocalDateTime.now())));

				Documento doc = new Documento();
				Usuario us = new Usuario();


				if (objetoDeEdicao.getClass().getName().equals("entidades.Documento") || objetoDeEdicao.getClass().getName().equals("entidades.Parecer")) {

					doc = (Documento) objetoDeEdicao;
					doc.setDocEnderecoFK(end);

					/* retirar na lista de demandas do endereco uma demanda repetida */
					Iterator<Documento> iDoc;

					Set<Documento> hashsetDemandas = new HashSet<Documento>();
					hashsetDemandas = end.getDocumentos();

					for (iDoc = hashsetDemandas.iterator(); iDoc.hasNext();)
					{
						Documento d = (Documento) iDoc.next();
						if (d.getDocID() == doc.getDocID()) {
							iDoc.remove();
						}
					}

					// adicionar a demanda editada //
					end.getDocumentos().add(doc);

				}

				else if (objetoDeEdicao.getClass().getName().equals("entidades.Usuario")) {

					us = (Usuario) objetoDeEdicao;
					//us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
					// adiciona neste endereco o id usuario selecionado
					end.setEndUsuarioFK(us);
					// adiciona este endereco no setEnderecos do usuario
					//us.getEnderecos().add(end);

				}
				
				else if (objetoDeEdicao.getClass().getName().equals("entidades.Subterranea") || objetoDeEdicao.getClass().getName().equals("entidades.Superficial") ) {
					
					System.out.println("nome da classe - btn edicao " + objetoDeEdicao.getClass().getName());
					
					Interferencia inter = (Interferencia) objetoDeEdicao;
					
						// adiciona este endereco no setEnderecos do usuario
						inter.setInterEnderecoFK(end);
						
						//List<Interferencia> iList = end.getInterferencias();
					
						//end.setInterferencias(iList);
						
						end.getInterferencias().add(inter);
						
					
				}

				// dao //
				EnderecoDao enderecoDao = new EnderecoDao();

				enderecoDao.mergeEndereco(end);

				// atualizar a tableview //
				obsList.remove(end);
				obsList.add(end);

				modularBotoesInicial (); 

				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro editado com sucesso!!!", ButtonType.OK));

			}

		}

	}
	
	public void excluirEndereco () {
		
		Endereco end = tvLista.getSelectionModel().getSelectedItem();
		
		int id = end.getEndID();
		
		EnderecoDao endDao = new EnderecoDao();
		
			try {
				
				endDao.removerEndereco(id);
				
				obsList.remove(end);
				
				modularBotoesInicial();
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro deletado com sucesso!!!", ButtonType.OK));
				
		
					}
		
					catch (Exception e) {
						
						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.ERROR, "Há denúncia associada a este endereço!", ButtonType.OK));
						
					}
			
	}
	
	public void cancelarEndereco () {
		
		modularBotoesInicial ();
		
		tfLogradouro.setText("");
		
		cbRA.setValue(null);
		
		tfCEP.setText("");
		
		cbUF.setValue(null);
		
		tfLatitude.setText("");
		tfLongitude.setText("");
		
	}

	public void pesquisarEndereco () {
		
		strPesquisa = tfPesquisar.getText();
		
		listarEnderecos (strPesquisa);
		
		selecionarEndereco () ;
		
		modularBotoesInicial (); 
		
	}
	
	String strPesquisa = "";
	
	//-- Modular os botoes na inicializacao do programa --//
	private void modularBotoesInicial () {
		
		tfLogradouro.setDisable(true);
		cbRA.setDisable(true);
		tfCEP.setDisable(true);
		tfCidade.setDisable(true);
		cbUF.setDisable(true);
		tfLatitude.setDisable(true);
		tfLongitude.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(false);
		
	}
	
	// --- metodo para listar endereco --- //
 	public void listarEnderecos (String strPesquisa) {
 		
	 	// --- conexao - listar enderecos --- //
		EnderecoDao enderecoDao = new EnderecoDao();
		List<Endereco> enderecoList = enderecoDao.listarEndereco(strPesquisa);
		//obsList = FXCollections.observableArrayList();
		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
			
		// funcionando
		List<Endereco> iList = enderecoList;
		
		
		for (Endereco e : iList) {
			
			obsList.add(e);
			
		}
			
		tvLista.setItems(obsList); 
	
	}
		
	// método selecionar endereço -- //
 	public void selecionarEndereco () {
	
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			Endereco end = (Endereco) newValue;
			
			if (end == null) {
				
				tfLogradouro.setText("");
				
				tfCEP.setText("");
				tfCidade.setText("");
				tfLatitude.setText("");
				tfLongitude.setText("");
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			} else {

				// -- preencher os campos -- //
				tfLogradouro.setText(end.getEndLogradouro());
				
				cbRA.setValue(end.getEndRAFK().getRaNome()); 
				
				tfCEP.setText(end.getEndCEP());
				tfCidade.setText(end.getEndCidade());
				
				cbUF.setValue(end.getEndUF());
				
				tfLatitude.setText(end.getEndDDLatitude().toString());
				tfLongitude.setText(end.getEndDDLongitude().toString());
				
				// levar o endereco salvo para a tabinterferencia //	
				
				/*
				 if (intTableView == 0) {
					 TabInterferenciaControlador.controladorAtendimento.setEndereco(end);
					 TabUsuarioControlador.controladorAtendimento.setEndereco(end);
			
				 	}
				     
				    if (intTableView == 1) {
				    	TabInterferenciaControlador.controladorFiscalizacao.setEndereco(end);
				    	TabUsuarioControlador.controladorFiscalizacao.setEndereco(end);
				    }
				    
				    if (intTableView == 2) {
				    	TabInterferenciaControlador.controladorOutorga.setEndereco(end);
				    	TabUsuarioControlador.controladorOutorga.setEndereco(end);
				    	TabParecerControlador.controladorOutorga.setEndereco(end);
				    }
				    */
					//tabInterCon.setEndereco(end);
					//tabUsCon.setEndereco(end);
					//tabParCon.setEndereco(end);
					
				
				
				// -- habilitar e desabilitar botoes -- //
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
			
				FormatoData d = new FormatoData();
				
				// mostrar data de atualizacao //
				
				try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(end.getEndAtualizacao()));
						lblDataAtualizacao.setTextFill(Color.BLACK);
				}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
						lblDataAtualizacao.setTextFill(Color.RED);}
				
				
				endereco = end;
				
				
				// atualizar o valor da demanda //
				
				
				/*
				if (end.getDemandas().size() != 0) { // colocar regra de só pode enditar escolhendo uma demanda...
					demanda = end.getDemandas().get(0);
				}
				*/
				
				
			}
			
			}
			
		});
		
		
	}
	
}





