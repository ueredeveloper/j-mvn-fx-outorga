package controladores.principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.DocumentoDao;
import dao.ModelosDao;
import entidades.Documento;
import entidades.Endereco;
import entidades.Finalidade;
import entidades.GetterAndSetter;
import entidades.Interferencia;
import entidades.ModelosHTML;
import entidades.NotaTecnica;
import entidades.Parecer;
import entidades.Subterranea;
import entidades.Usuario;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;
import principal.MalaDiretaDocumentos;
import util.NavegadorExterno;
import util.Registro;

public class TabParecerControlador implements Initializable {

	TableView<Documento> tvDocumento = new TableView<Documento>();
	TableColumn<Documento, String> tcTipoDocumento = new TableColumn<Documento, String>("Parecer");
	TableColumn<Documento, String> tcSEI = new TableColumn<Documento, String>("SEI");
	TableColumn<Documento, String> tcEndereco = new TableColumn<Documento, String>("Endereço");

	ObservableList<Documento> obsList = FXCollections.observableArrayList();

	Documento documento = new Documento();
	Endereco endereco = new Endereco();
		
	public void setEndereco (Endereco endereco) {
	
		this.endereco = endereco;
	
		if (endereco != null ) {
			
			lblEndereco.setText(
				endereco.getEndLogradouro()
				+ ", CEP n°: " + endereco.getEndCEP()
				+ ", Cidade: " + endereco.getEndCidade()
				
				);
			
			lblEndereco.setStyle("-fx-text-fill: #4A4A4A;"); 
		}
		
		else {
		
			lblEndereco.setText(
					"Não há endereco relacionado! "
					);
			lblEndereco.setStyle("-fx-text-fill: #FF0000;");
		}
	
	}

	Label lblDataAtualizacao = new Label();
  
  	@FXML Pane pParecer;
	  
  	Pane p1 = new Pane();
  	BorderPane bp1 = new BorderPane();
  	BorderPane bp2 = new BorderPane();
  	ScrollPane sp = new ScrollPane();

  	ControladorOutorga controladorOutorga;
	ControladorAtendimento controladorAtendimento;
	ControladorFiscalizacao controladorFiscalizacao;

	public TabParecerControlador (ControladorOutorga controladorOutorga) {
		this.controladorOutorga = controladorOutorga;

	}
	
	public TabParecerControlador (ControladorAtendimento controladorAtendimento) {
		this.controladorAtendimento = controladorAtendimento;

	}
	
	public TabParecerControlador (ControladorFiscalizacao controladorFiscalizacao) {
		this.controladorFiscalizacao = controladorFiscalizacao;

	}
	
	  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		bp1.minWidthProperty().bind(pParecer.widthProperty());
	    bp1.maxHeightProperty().bind(pParecer.heightProperty().subtract(60));
	    
	    bp1.getStyleClass().add("border-pane");
	    
	    bp2.setPrefHeight(800.0D);
	    bp2.minWidthProperty().bind(pParecer.widthProperty());
	    
	    sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    
	    sp.setContent(bp2);
	    
	    bp1.setCenter(sp);
	    
	    pParecer.getChildren().add(bp1);
	    
	    p1.setMaxSize(980.0, 1100.0);
	    p1.setMinSize(980.0, 1100.0);
	    
	    bp2.setTop(p1);
	    BorderPane.setAlignment(p1, Pos.CENTER);
    
    
	    lblDataAtualizacao.setPrefSize(247.0, 22.0);
	    lblDataAtualizacao.setLayoutX(705.0);
	    lblDataAtualizacao.setLayoutY(485.0);
	    
	    tcTipoDocumento.setCellValueFactory(new PropertyValueFactory<Documento, String>("docTipo"));
	    tcSEI.setCellValueFactory(new PropertyValueFactory<Documento, String>("docNumeracao"));
	    tcEndereco.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Documento, String>, ObservableValue<String>>() {
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<Documento, String> d) {
		    	return new SimpleStringProperty(d.getValue().getDocEnderecoFK().getEndLogradouro());
		       
		    }
		});
   
	    tcTipoDocumento.setPrefWidth(250.0);
    	tcSEI.setPrefWidth(250.0);
    	tcEndereco.setPrefWidth(410.0);

    
		tvDocumento.setPrefSize(930.0, 185.0);
		tvDocumento.setLayoutX(25.0);
		tvDocumento.setLayoutY(290.0);

		tvDocumento.getColumns().add(tcTipoDocumento); //, tcDocsSEI, tcProcsSEI });
		tvDocumento.getColumns().add(tcSEI);
		tvDocumento.getColumns().add(tcEndereco);
    		
    
    	tvDocumento.setItems(obsList);
  
	    p1.getChildren().addAll(tvDocumento, lblDataAtualizacao);
	    
	    inicializarComponentes ();
	    selecionarDocumento (); 
	    selecionarUsuario ();
	    selecionarInterferencia();
	    modularBotoes();
	    acionarBotoes();
	    
	} // FIM INITIALIZE
	
	Componentes com;
	Double prefSizeWHeLayXY [][];
	
	// inicializacao do endereco relacionado
	Pane pEndereco;
		Label lblEndereco;
		Button btnEndereco;
		ArrayList<Node> componentesEndereco = new ArrayList<Node>();
	
	// inicializacao dos dados basicos de cadastro
	Pane pDadosParecer;
	
		ComboBox<String> cbTipoDocumento;		
		ObservableList<String> obsListTipoDocumento = FXCollections
				.observableArrayList("Parecer" , "Nota Técnica");
	
	
		TextField tfDocumento;
  		TextField tfSEI;
  		TextField tfProcessoSEI;
  		DatePicker dpDataCriacao;
  		DatePicker dpDataDistribuicao;
  		DatePicker dpDataRecebimento;
  		
  		ArrayList<Node> componentesParecer = new ArrayList<Node>();
	
  		// inicializacao dos botoes de persistencia
		Pane  pPersistencia;
  		Button btnNovo;
		Button btnSalvar;
		Button btnEditar;
		Button btnExcluir;
		Button btnCancelar;
		Button btnPesquisar;
		TextField tfPesquisar;
			  			
		ArrayList<Node> componentesPersistencia = new ArrayList<Node>();
		
		Pane pUsuario;

		// table view para visualizar todos os atos da outorga ou pesquisar um especifico
		TableView<Usuario> tvUsuarios;
		TableColumn<Usuario, String> tcNomeUsuario = new TableColumn<Usuario, String>("Nome");
		TableColumn<Usuario, String> tcCPFCNPJ = new TableColumn<Usuario, String>("CPF/CNPJ");
		ObservableList<Usuario> obsListUsuario = FXCollections.observableArrayList();
		
		// listar usuarios
		Set<Usuario> setListUsuarios; 

		// combobox para listar enderecos
		ComboBox<Endereco> cbEndereco;		
		ObservableList<Endereco> obsListEnderecos = FXCollections.observableArrayList();
		
		// combobox para listar documentos
		ComboBox<Documento> cbDocumento;		
		ObservableList<Documento> obsListDocumentos = FXCollections.observableArrayList();
		

		//-- TableView Endereco --//
		private TableView <Interferencia> tvInterferencia = new TableView<>();

		TableColumn<Interferencia, String> tcTipoInterferencia  = new TableColumn<>("Tipo de Interferência");
		TableColumn<Interferencia, String> tcSituacaoInterferencia  = new TableColumn<>("Situação");
		ObservableList<Interferencia> obsListInterferencia = FXCollections.observableArrayList();

		Button btnUsuario;
		Button btnParecer;
	
		Button btnLimpaListaMalaDireta, btnRemoveInterferenciaMalaDireta, btnExcel, btnLocalExcel;
		ComboBox<ModelosHTML> cbModelosHTML;
			ObservableList<ModelosHTML> obsListModelosHTML = FXCollections.observableArrayList();
		
		ArrayList<Node> componentesDocumento = new ArrayList<Node>();
		

		NavegadorExterno navExt;
		
	
	public void inicializarComponentes (){

		componentesEndereco.add(pEndereco = new Pane());
		componentesEndereco.add(new Label("ENDEREÇO:"));
		componentesEndereco.add(lblEndereco = new Label());
		componentesEndereco.add(btnEndereco = new Button("<<<"));
	
		prefSizeWHeLayXY = new Double [][] { 
	
			{950.0,60.0,15.0,10.0},
			{85.0,30.0,43.0,15.0},
			{710.0,30.0,128.0,15.0},
			{70.0,20.0,838.0,19.0},
		};
	
		com = new Componentes();
		com.popularTela(componentesEndereco, prefSizeWHeLayXY, p1);
	
		componentesParecer.add(pDadosParecer = new Pane());
		
		componentesParecer.add(new Label("TIPO:"));
		componentesParecer.add(cbTipoDocumento = new ComboBox<String>(obsListTipoDocumento));
		
		componentesParecer.add(new Label("NUMERAÇÃO:"));
		componentesParecer.add(tfDocumento = new TextField());
		componentesParecer.add(new Label("SEI:"));
		componentesParecer.add(tfSEI = new TextField());
		componentesParecer.add(new Label("PROCESSO:"));
		componentesParecer.add(tfProcessoSEI = new TextField());
		componentesParecer.add(new Label("DATA DE CRIAÇÃO:"));
		componentesParecer.add(dpDataCriacao = new DatePicker());
		componentesParecer.add(new Label("DATA DE RECEBIMENTO:"));
		componentesParecer.add(dpDataRecebimento = new DatePicker());
		componentesParecer.add(new Label("DATA DE DISTRIBUIÇÃO:"));
		componentesParecer.add(dpDataDistribuicao = new DatePicker());
	
		prefSizeWHeLayXY = new Double [][] { 
			
			{950.0,134.0,15.0,70.0},
			{170.0,30.0,16.0,0.0},
			{170.0,30.0,15.0,32.0},
			{130.0,30.0,16.0,64.0},
			{130.0,30.0,15.0,94.0},
			{130.0,30.0,156.0,64.0},
			{130.0,30.0,156.0,94.0},
			{160.0,30.0,296.0,64.0},
			{160.0,30.0,296.0,94.0},
			{150.0,30.0,466.0,64.0},
			{150.0,30.0,466.0,94.0},
			{150.0,30.0,626.0,64.0},
			{150.0,30.0,626.0,94.0},
			{150.0,30.0,786.0,64.0},
			{150.0,30.0,786.0,94.0},
	
		};
	
		com = new Componentes();
		com.popularTela(componentesParecer, prefSizeWHeLayXY, p1);


			componentesPersistencia.add(pPersistencia = new Pane());
			componentesPersistencia.add(btnNovo = new Button("NOVO"));
			componentesPersistencia.add(btnSalvar = new Button("SALVAR"));
			componentesPersistencia.add(btnEditar = new Button("EDITAR"));
			componentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
			componentesPersistencia.add(btnCancelar = new Button("CANCELAR"));
			componentesPersistencia.add(tfPesquisar = new TextField());
			componentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

			prefSizeWHeLayXY = new Double[][] { 

				{930.0,60.0,25.0,215.0},
				{95.0,25.0,18.0,18.0},
				{95.0,25.0,123.0,18.0},
				{95.0,25.0,228.0,18.0},
				{95.0,25.0,333.0,18.0},
				{95.0,25.0,438.0,18.0},
				{265.0,25.0,543.0,18.0},
				{95.0,25.0,818.0,18.0},

			}; 

		com = new Componentes();
		com.popularTela(componentesPersistencia, prefSizeWHeLayXY, p1);


		componentesDocumento.add(pUsuario = new Pane());
		componentesDocumento.add(btnUsuario = new Button("<<<"));

		componentesDocumento.add(new Label("USUÁRIOS:"));
		componentesDocumento.add(tvUsuarios = new TableView<Usuario>());
		
		componentesDocumento.add(new Label("ENDEREÇOS:"));
		componentesDocumento.add(cbEndereco = new ComboBox<>());
		
		componentesDocumento.add(new Label("REQUERIMENTO:"));
		componentesDocumento.add(cbDocumento = new ComboBox<>());
		
		componentesDocumento.add(new Label("INTERFERÊNCIAS:"));
		componentesDocumento.add(tvInterferencia = new TableView<>());

		componentesDocumento.add(new Label("MODELOS:"));
		componentesDocumento.add(cbModelosHTML = new ComboBox<>());

		componentesDocumento.add(btnLimpaListaMalaDireta = new Button("limpar"));
		componentesDocumento.add(btnRemoveInterferenciaMalaDireta = new Button("remover"));

		componentesDocumento.add(btnParecer = new Button("GERAR PARECER"));
		componentesDocumento.add(btnExcel = new Button("excel"));
		componentesDocumento.add(btnLocalExcel = new Button("..."));

		prefSizeWHeLayXY = new Double [][] { 

			{930.0,431.0,25.0,515.0},
			{70.0,25.0,850.0,10.0},
			{420.0,30.0,14.0,41.0},
			//--- TableView --
			{420.0,300.0,14.0,71.0},
			{400.0,30.0,444.0,41.0},
			{400.0,30.0,444.0,71.0},
			{400.0,30.0,444.0,101.0},
			{400.0,30.0,444.0,131.0},
			{400.0,30.0,444.0,161.0},
			//--- TableView --
			{400.0,120.0,444.0,191.0},
			{400.0,30.0,444.0,311.0},
			{400.0,30.0,444.0,341.0},
			{70.0,25.0,850.0,220.0},
			{70.0,25.0,850.0,255.0},
			{790.0,30.0,14.0,386.0},
			{70.0,25.0,815.0,389.0},
			{25.0,25.0,895.0,389.0},
			
			

		};

		com = new Componentes();
		com.popularTela(componentesDocumento, prefSizeWHeLayXY, p1);


		tcNomeUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usNome"));
		tcCPFCNPJ.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usCPFCNPJ"));

		tcNomeUsuario.setPrefWidth(250.0);
		tcCPFCNPJ.setPrefWidth(150.0);
		tvUsuarios.getColumns().add(tcNomeUsuario); //, tcDocsSEI, tcProcsSEI });
		tvUsuarios.getColumns().add(tcCPFCNPJ);
		tvUsuarios.setItems(obsListUsuario);



		tcTipoInterferencia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Interferencia, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Interferencia, String> i) {
				return new SimpleStringProperty(i.getValue().getInterTipoInterferenciaFK().getTipoInterDescricao());

			}
		});

		tcSituacaoInterferencia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Interferencia, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Interferencia, String> i) {
				return new SimpleStringProperty(i.getValue().getInterSituacaoProcessoFK().getSituacaoProcessoDescricao());

			}
		});

		tcTipoInterferencia.setPrefWidth(200.0);
		tcSituacaoInterferencia.setPrefWidth(180.0);

		tvInterferencia.getColumns().add(tcTipoInterferencia); //, tcDocsSEI, tcProcsSEI });
		tvInterferencia.getColumns().add(tcSituacaoInterferencia);

		tvInterferencia.setItems(obsListInterferencia);

		cbEndereco.setItems(obsListEnderecos);
		
		cbDocumento.setItems(obsListDocumentos);

		cbEndereco.setConverter(new StringConverter<Endereco>() {

			public String toString(Endereco e) {
				return e.getEndLogradouro();
			}

			public Endereco fromString(String string) {
				return null;
			}
		});

		cbEndereco.valueProperty().addListener(new ChangeListener<Endereco>() {
			@Override 
			public void changed(ObservableValue<? extends Endereco> ov, Endereco oldValue, Endereco newValue) {  

				obsListInterferencia.clear();
				obsListDocumentos.clear();
				
				if (newValue != null) {
					
					// para nao repetir valores
					obsListInterferencia.addAll(newValue.getInterferencias().stream().distinct().collect(Collectors.toList()));
					endereco = newValue;
				}
				
				if (cbEndereco.getSelectionModel().getSelectedItem() != null) {
					obsListDocumentos.addAll(newValue.getDocumentos().stream().distinct().collect(Collectors.toList()));
				}
				
						
			}    
		});
		
		cbDocumento.setConverter(new StringConverter<Documento>() {

			public String toString(Documento d) {
				return d.getDocTipo() + ", nº " + d.getDocNumeracao();
			}

			public Documento fromString(String string) {
				return null;
			}
		});
		
		cbDocumento.valueProperty().addListener(new ChangeListener<Documento>() {
			@Override 
			public void changed(ObservableValue<? extends Documento> ov, Documento oldValue, Documento newValue) {  

				//if(newValue != null)
				//System.out.println(newValue.getDocID());
						
			}    
		});

		cbModelosHTML.setItems(obsListModelosHTML);

		cbModelosHTML.setConverter(new StringConverter<ModelosHTML>() {

			public String toString(ModelosHTML m) {
				return m.getModIdentificacao();

			}

			public ModelosHTML fromString(String string) {
				return null;
			}
		});

		cbModelosHTML.valueProperty().addListener(new ChangeListener<ModelosHTML>() {
			@Override 
			public void changed(ObservableValue<? extends ModelosHTML> ov, ModelosHTML oldValue, ModelosHTML newValue) {  

				//System.out.println("modelos escolhido " + newValue.getModIdentificacao());
			}    
		});
		
		navExt = new NavegadorExterno(p1);
		
		navExt.inicializarNavegadorExterno(25.0, 950.0);
		
	}

	public void listarDocumentos (String strPesquisa) {
	  
	    DocumentoDao docDao = new DocumentoDao();
	    
	    List<Documento> docList = docDao.listarParecerNotaTecnica(strPesquisa);
	    
	    if (!obsList.isEmpty()) {
	      obsList.clear();
	      
	    }
	    
	    
	    for (Documento d : docList) {
	      obsList.add(d);
	    }
	    
	    tvDocumento.setItems(obsList);
		    
	}
	
	public void selecionarDocumento () {
		
		// TableView - selecionar demandas ao clicar //
		tvDocumento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			Documento doc = (Documento) newValue;
			
			if (doc == null) {
				
				cbTipoDocumento.setValue(null);
				tfDocumento.setText("");
				tfSEI.setText("");
				tfProcessoSEI.setText("");
			
				dpDataCriacao.getEditor().clear();
				dpDataRecebimento.getEditor().clear();
				dpDataDistribuicao.getEditor().clear();
			
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
				System.out.println("documento nulo");
				
				listaMalaDireta.clear();
				
			} else {
				
				listaMalaDireta.clear();
			
				documento = doc;
				
				setEndereco(doc.getDocEnderecoFK());
				
				cbTipoDocumento.setDisable(true);
				cbTipoDocumento.setValue(doc.getDocTipo());
				
				// preencher os campos //
				tfDocumento.setText(doc.getDocNumeracao());
				tfSEI.setText(doc.getDocSEI());
				tfProcessoSEI.setText(doc.getDocProcesso());
				
				if (doc.getDocDataCriacao() == null) {
					dpDataCriacao.setValue(null);
					
	 				} else {
	 					Date d = doc.getDocDataCriacao();
	 					dpDataCriacao.setValue(d.toLocalDate());
	 			
	 				}
				
				if (doc.getDocDataDistribuicao() == null) {
					dpDataDistribuicao.setValue(null);
					
	 				} else {
	 					Date d = doc.getDocDataDistribuicao();
	 					dpDataDistribuicao.setValue(d.toLocalDate());
	 				
	 				}
				
				if (doc.getDocDataRecebimento() == null) {
					dpDataRecebimento.setValue(null);
	 				} else {
	 					
	 					Date d = doc.getDocDataRecebimento();
	 					dpDataRecebimento.setValue(d.toLocalDate());
	 				
	 				}
				
				
				if (!obsListUsuario.isEmpty()) {
					obsListUsuario.clear();
				      
				}
				
				// listar usuários relacionados ao  documento
				Set<Usuario> usList = doc.getUsuarios();
				// adicionar usuario buscado pelo endereco relacionado ao  documento
				//usList.add(doc.getDocEnderecoFK().getEndUsuarioFK());
				
					// Preencher a tableView Usuario 
					for(Usuario us : usList) {
						
						obsListUsuario.add(us);
						//System.out.println(us.getUsNome());
						
					}
					
						// Limpar a tableView Interferencia
						obsListInterferencia.clear();
				
				// mostrar data de atualizacao //
				FormatoData d = new FormatoData();
				try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(doc.getDocDataAtualizacao()));  // d.formatarData(demanda.getDemAtualizacao())
						lblDataAtualizacao.setTextFill(Color.BLACK);
					} catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
							lblDataAtualizacao.setTextFill(Color.RED);}
				
				// copiar número sei da demanda ao selecionar //
				Clipboard clip = Clipboard.getSystemClipboard();
	            ClipboardContent conteudo = new ClipboardContent();
	            conteudo.putString(doc.getDocSEI());
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
	
	Usuario usuario = new Usuario();
	
	public void selecionarUsuario () {
		
		tvUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
				
				Usuario us = (Usuario) newValue;
				
				if (us == null) {
					
					System.out.println("tabParcecer -  selecionar usuario - if usuario == null");
					
				} else {

					obsListEnderecos.clear();
				
					Set<Endereco> setEnderecos = us.getEnderecos();
			
					if (! us.getEnderecos().isEmpty()) {
						
						for(Endereco e: setEnderecos) {
						
							obsListEnderecos.add(e);
							
							/*
							 * para atualizar o endereço com qualquer um dos  relacionados
							 * 		o objeto endereco não pode ficar vazio
							 */
					
							endereco = e;
							
						}
						
					} else {
						endereco = null;
					}
					
					// copiar cpf do usuario ao selecionar //
					Clipboard clip = Clipboard.getSystemClipboard();
	                ClipboardContent conteudo = new ClipboardContent();
	                conteudo.putString(us.getUsCPFCNPJ());
	                clip.setContent(conteudo);
	                
					
				}
				}
			});
	}

	List<Object[][]> listaMalaDireta = new ArrayList<>();
	
	// metodo selecionar interferencia -- //
	public void selecionarInterferencia () {
			
		 		tvInterferencia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
					
					public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
					
					Interferencia inter = (Interferencia) newValue;
					
					if (inter == null) {
						
						
						System.out.println("selecionarInterferencia - null");
					
					} else {
						
						
						Object[][] dados = new Object [][] {
							{
							cbDocumento.getSelectionModel().getSelectedItem(),
							tvUsuarios.getSelectionModel().getSelectedItem(),
							tvInterferencia.getSelectionModel().getSelectedItem(),
							cbEndereco.getSelectionModel().getSelectedItem()
							},
						} ;
				
						listaMalaDireta.add(dados);
						
						obsListModelosHTML.clear();
						
						for (ModelosHTML m : listaModelosHTML) {
							
							if (m.getModTipoDocumento().equals("Parecer")) {
		
								obsListModelosHTML.add(m);
							}
						}
					
					}
					
					}
				});
			}
	
	public void modularBotoes () {
		
		cbTipoDocumento.setDisable(true);
		tfDocumento.setDisable(true);
	    tfSEI.setDisable(true);
	    tfProcessoSEI.setDisable(true);
	    
	    dpDataCriacao.setDisable(true);
	    dpDataDistribuicao.setDisable(true);
	    dpDataRecebimento.setDisable(true);
	    
	    btnSalvar.setDisable(true);
	    btnEditar.setDisable(true);
	    btnExcluir.setDisable(true);
	    
	    btnNovo.setDisable(false);
	}

	List<ModelosHTML> listaModelosHTML;
	
	public void acionarBotoes() {

		btnNovo.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				habilitarDocumento();
			}
		});


		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				salvarDocumento();
			}
		});


		btnEditar.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				editarDocumento();
			}
		});



		btnExcluir.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				excluirDocumento();
			}
		});


		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				cancelarDocumento();
			}
		});


		btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				pesquisarDocumento();

				if (obsListModelosHTML.isEmpty()) {
					
					ModelosDao mDao = new  ModelosDao();

					listaModelosHTML = mDao.listarModelo("");

						obsListModelosHTML.addAll(listaModelosHTML);
					
				}

			}
		});

		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});

		// inicializar tela usuario
		btnUsuario.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				inicializarTelaUsuario();

				TelaUsuarioControlador.telaUsCon.setDocumento(documento);

			}
		});
		
		ObservableList<String> obsListUsuariosMalaDireta = FXCollections.observableArrayList();

		
		// inicializar tela usuario
		btnParecer.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				// selecionar o parecer desejado
				String modeloHTML = cbModelosHTML.getSelectionModel().getSelectedItem().getModConteudo();
				
				// criar a mala direta
				MalaDiretaDocumentos mlDoc = new MalaDiretaDocumentos(modeloHTML, documento, listaMalaDireta);
				String strHTML = mlDoc.criarDocumento();
			
				// setar no navegador o html desejado
				try { 
					
					ControladorNavegacao.conNav.setHTML(strHTML);
				
					strAnexoParecer = listaModelosHTML.get(6).getModConteudo();
				
					strTabela1 = listaModelosHTML.get(7).getModConteudo();
					strTabela2 = listaModelosHTML.get(3).getModConteudo();
				
					strAnexoParecer = strAnexoParecer.replace("\"", "'");
					strAnexoParecer = strAnexoParecer.replace("\n", "");
					strAnexoParecer =  "\"" + strAnexoParecer + "\"";
		
					obsListUsuariosMalaDireta.clear();
				
				for (int i=0; i<listaMalaDireta.size();i++) {
					
					obsListUsuariosMalaDireta.add(
							((Usuario)listaMalaDireta.get(i)[0][1]).getUsNome()
							+ ", " 
							+ ((Endereco)listaMalaDireta.get(i)[0][3]).getEndLogradouro()
							+ ", " 
							+ ((Interferencia)listaMalaDireta.get(i)[0][2]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao()
							);
					
					
					//System.out.println(((Usuario)listaMalaDireta.get(i)[0][1]).getUsNome());
					
				}
		
				ControladorNavegacao.conNav.setObjetosAnexo(listaMalaDireta, obsListUsuariosMalaDireta, strAnexoParecer, strTabela1, strTabela2);
				
				} catch (Exception ee) {

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Inicialize o navegador SEI !!!", ButtonType.OK));
				}
				

				// setar o html do documento no navagador externo
				navExt.setarStringHTML(strHTML);
				
				navExt.setObjetosAnexo(listaMalaDireta, obsListUsuariosMalaDireta, strAnexoParecer, strTabela1, strTabela2);
				
			}

		});

		// inicializar tela usuario
		btnLimpaListaMalaDireta.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				listaMalaDireta.clear();
				
				obsListDocumentos.clear();
				obsListEnderecos.clear();
				obsListInterferencia.clear();
			
			}
		});

		// inicializar tela usuario
		btnRemoveInterferenciaMalaDireta.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
				//System.out.println(listaMalaDireta.size());
				
				for (int i=0; i<listaMalaDireta.size();i++) {
					//System.out.println(i +  ": " + listaMalaDireta.get(i));
				}
				
				if(listaMalaDireta.size()>0) {
				listaMalaDireta.remove(listaMalaDireta.size()-1);
				}
				
				for (int i=0; i<listaMalaDireta.size();i++) {
					//System.out.println(i +  ": " + listaMalaDireta.get(i));
				}
			
			}
		});
		
		List<String> listVariaveisVazaoHoraAutorizadas = Arrays.asList(

				"faQHoraJan","faQHoraFev","faQHoraMar","faQHoraAbr","faQHoraMai","faQHoraJun",
				"faQHoraJul","faQHoraAgo","faQHoraSet","faQHoraOut","faQHoraNov","faQHoraDez"

				);

		List<String> listVariaveisTempoAutorizadas = Arrays.asList(

				"faTempoCapJan","faTempoCapFev","faTempoCapMar","faTempoCapAbr","faTempoCapMai","faTempoCapJun",
				"faTempoCapJul","faTempoCapAgo","faTempoCapSet","faTempoCapOut","faTempoCapNov","faTempoCapDez"

				);

		
	
		// inicializar tela usuario
		btnExcel.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				Registro registro = new Registro();
				
				String strArquivoExcel = null;
				
				Properties prop = new Properties();
				
				try {
					prop = registro.lerRegistro();
					
					strArquivoExcel = prop.getProperty("strExcel");
				
				} catch (Exception e1) {
					
				}
			
				
				// Uma string nao pode ficar c:local\excel.xlsm - tem que mudar para duas barras invertidas c:local\\excel.xlsm
				//strLocalArquivoExcel = strLocalArquivoExcel.replaceAll("/[\"]/g", "\\");

				// cortar o endereco completo do arquivo excel e capturar o endereco da pasta somente
				String [] str = strArquivoExcel.toString().split("\\\\");
			
				strPastaArquivoExcel = "";
				
				// capturar o endereco da pasta do arquivo excel
				for (int i=0;i<str.length-1;i++) {
					strPastaArquivoExcel = strPastaArquivoExcel + str[i] + "\\\\";
				}
				
				//System.out.println("btn excel - local pasta: " + strPastaArquivoExcel);
				
				// data e hora da criacao do documento excel
				String strTempoAtual = Timestamp.valueOf((LocalDateTime.now())).toString();
				strTempoAtual = strTempoAtual.replaceAll("\\D",""); 
				

				FileOutputStream fileOutput = null;
				XSSFWorkbook workbook;

				try {
					workbook = (XSSFWorkbook)WorkbookFactory.create(new FileInputStream(strArquivoExcel));

					Sheet sheetOutorgas = workbook.getSheetAt(0);

					GetterAndSetter gs  = new GetterAndSetter();

					for (int i = 0; i<listaMalaDireta.size(); i++) {

						//	ANÁLISE
						Row row = sheetOutorgas.getRow(i+1);

						// Subsistema - Interferencia  	
						Cell cellSubsistema = row.getCell(5);
						cellSubsistema.setCellValue(((Subterranea)listaMalaDireta.get(i)[0][2]).getSubSubSistemaFK().getSubDescricao());

						// Usuario listaMalaDireta.get(i)[0][1]
						Cell cellNome = row.getCell(6);
						cellNome.setCellValue(((Usuario)listaMalaDireta.get(i)[0][1]).getUsNome());

						Cell cellCPF = row.getCell(7);
						cellCPF.setCellValue(((Usuario)listaMalaDireta.get(i)[0][1]).getUsCPFCNPJ());	

						// Endereco do Empreendimento listaMalaDireta.get(i)[0][3] 	
						Cell cellEnderecoEmpreendimento = row.getCell(8);
						cellEnderecoEmpreendimento.setCellValue(((Endereco)listaMalaDireta.get(i)[0][3]).getEndLogradouro());	

						// Interferencia listaMalaDireta.get(i)[0][2]	
						Cell cellInterferenciaLatitude = row.getCell(10);
						cellInterferenciaLatitude.setCellValue(((Interferencia)listaMalaDireta.get(i)[0][2]).getInterDDLatitude());	

						//System.out.println("latitude " + ((Interferencia)listaMalaDireta.get(i)[0][2]).getInterDDLatitude());

						Cell cellInterferenciaLongitude= row.getCell(11);
						cellInterferenciaLongitude.setCellValue(((Interferencia)listaMalaDireta.get(i)[0][2]).getInterDDLongitude());

						Cell cellInterferenciaTipoPoco = row.getCell(12);
						cellInterferenciaTipoPoco.setCellValue(((Subterranea)listaMalaDireta.get(i)[0][2]).getSubTipoPocoFK().getTipoPocoDescricao());

						Cell cellInterferenciaProcesso = row.getCell(18);
						cellInterferenciaProcesso.setCellValue(((Documento)listaMalaDireta.get(i)[0][0]).getDocProcessoFK().getProSEI());

						for (Finalidade f : ((Interferencia) listaMalaDireta.get(i)[0][2]).getFinalidades()) {

							if ( f.getClass().getName().equals("entidades.FinalidadeAutorizada")) {

								for (int ii = 41; ii<53; ii++) {

									Cell c = row.getCell(ii);
									c.setCellValue( 	(((Subterranea)listaMalaDireta.get(i)[0][2]).getSubVazaoOutorgada()	 ));


								}

								int iii = 0;

								for (int ii = 65; ii<77; ii++) {


									Cell c = row.getCell(ii);
									c.setCellValue( 	gs.callGetter(f, listVariaveisVazaoHoraAutorizadas.get(iii))	);
									
									iii++;
									
								}

								iii = 0;
								
								for (int ii = 89; ii<101; ii++) {

									Cell c = row.getCell(ii);
									c.setCellValue( 	gs.callGetter(f, listVariaveisTempoAutorizadas.get(iii))	);

									iii++;

								}


							}


						} //fim for finalidade
						
						
						// Data de Recebimento do Requerimento
						Cell cellCodigo = row.getCell(114);
						cellCodigo.setCellValue(((Subterranea)listaMalaDireta.get(i)[0][2]).getSubCod_plan());

						// Data de Recebimento do Requerimento
						Cell cellDataRequerimento = row.getCell(121);
						cellDataRequerimento.setCellValue(((Documento)listaMalaDireta.get(i)[0][0]).getDocDataRecebimento());
						// Nome da Bacia Hidrografica
						Cell cellBacia = row.getCell(123);
						cellBacia.setCellValue(((Subterranea)listaMalaDireta.get(i)[0][2]).getInterBaciaFK().getBaciaNome());
						// Nome da Unidade Hidrografica
						Cell cellUnidadeHidrografica = row.getCell(124);
						cellUnidadeHidrografica.setCellValue(((Subterranea)listaMalaDireta.get(i)[0][2]).getInterUHFK().getUhNome());


					}


					//System.out.println("hora de salvar excel local pasta : " + strPastaArquivoExcel);
					// Nomear o excel gerado como: Parecer Numero (123456) Data(20190512) .xslm
					fileOutput = new FileOutputStream(
							strPastaArquivoExcel 
							+ "Parecer-" 
							+ documento.getDocSEI()
							+ "-"
							+ strTempoAtual
							+ ".xlsm");

					workbook.write(fileOutput);
					fileOutput.close();


				} catch (InvalidFormatException e1) {
					e1.printStackTrace();
				}
				catch (FileNotFoundException ffe) {
					ffe.printStackTrace();
					System.out.println("Arquivo não encontrado!");
				} catch (IOException ioe) {
					ioe.printStackTrace();
					System.out.println("Erro na edição do arquivo!");
				}


			}
		});
		
		
		// salvar o locar (link no computador) onde esta o excel
		btnLocalExcel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Registro registro = new Registro();
				
				//String strArquivoExcel = null;
				
				Properties prop = new Properties();
				
				try {
					prop = registro.lerRegistro();
			
					
				} catch (Exception e1) {
			
					try {
						registro.salvarRegistro(prop);
					} catch (URISyntaxException e2) {
				
						e1.printStackTrace();
					} catch (IOException e2) {
				
						e1.printStackTrace();
					}
					
					//System.out.println("salvar excel ");
					
				}
		
				// para escolher o arquivo no computador
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Selecione o arquivo excel");
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter("XLS", "*.xls", "*.xlsm", "*.xlsx"));
				File file = fileChooser.showOpenDialog(null);
				

				//  link do diretorio do web driver 0;  link com diretorio do arquivo excel 1;
				strArquivoExcel = file.toString();
				
				prop.setProperty("strExcel", strArquivoExcel);

				Registro rSalvar = new Registro();

				try {
					rSalvar.salvarRegistro(prop);
					
				} catch (URISyntaxException e3) {

					e3.printStackTrace();
				} catch (IOException e3) {

					e3.printStackTrace();
				}


			}

		});
		
		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				inicializarTelaEndereco();
				telaEnderecoControlador.setObjetoDeEdicao(documento);
			
			}
		});
		
	   
	}
	
	/*
	 * Strings necessarias para a criacao do parecer
	 */
	String strAnexoParecer, strTabela1, strTabela2 = "";
	
	// endereco da pasta onde está o arquivo excel, nao pode ser null
	String strPastaArquivoExcel = "";
	
	// endereco completo do arquivo excel, precisa ser null para a condicional no comeco do btnExcel
	String strArquivoExcel = null;
	
	
	public void habilitarDocumento () {
		
	  cbTipoDocumento.setValue(null);
		  
	  tfDocumento.setText("");
	  tfSEI.setText("");
	  tfProcessoSEI.setText("");
    
	  dpDataCriacao.getEditor().clear();
	  dpDataDistribuicao.getEditor().clear();
	  dpDataRecebimento.getEditor().clear();
	  
	  cbTipoDocumento.setDisable(false);
    
	  dpDataCriacao.setDisable(false);
	  dpDataDistribuicao.setDisable(false);
	  dpDataRecebimento.setDisable(false);
   
	  tfDocumento.setDisable(false);
	  tfSEI.setDisable(false);
	  tfProcessoSEI.setDisable(false);
    
	  btnSalvar.setDisable(false);
    
	  btnEditar.setDisable(true);
	  btnExcluir.setDisable(true);
	  btnNovo.setDisable(true);
		  
	}
	 
	public void salvarDocumento ()	{
		    
	  try {
		  
		  if (endereco.getEndLogradouro().equals(null) || endereco.getEndLogradouro() == null) {
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Endereço não selecionado!!!", ButtonType.OK));
				
		  }
		  
		  else if ((tfDocumento.getText().isEmpty()) || (tfProcessoSEI.getText().isEmpty()))
	      {
		        Alerta a = new Alerta();
		        a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Numero e Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
		  }
		  
	      else
	      {
	    	  
	    	  Documento doc;
	    	  
	    	  if (cbTipoDocumento.getValue().equals("Parecer")) {
	    		  
	    		  doc = new Parecer();
	    	  } else {
	    		  doc = new NotaTecnica();
	    	  }
		      
		        
	    	  	doc.setDocTipo(cbTipoDocumento.getValue());
	    	  	doc.setDocNumeracao(tfDocumento.getText());
	    	  	doc.setDocSEI(tfSEI.getText());
	    	  	doc.setDocProcesso(tfProcessoSEI.getText());
			        
		        if (dpDataCriacao.getValue() == null) {
		        		doc.setDocDataCriacao(null);
			        } else {
			        	doc.setDocDataCriacao(Date.valueOf((LocalDate)dpDataCriacao.getValue()));
			        }
			      
		        if (dpDataDistribuicao.getValue() == null) {
		        	doc.setDocDataDistribuicao(null);
		        } else {
		        	doc.setDocDataDistribuicao(Date.valueOf((LocalDate)dpDataDistribuicao.getValue()));
		        }
			        
		        if (dpDataRecebimento.getValue() == null) {
		        	doc.setDocDataRecebimento(null);
		        } else {
		        	doc.setDocDataRecebimento(Date.valueOf((LocalDate)dpDataRecebimento.getValue()));
		        }
				        
		        doc.setDocDataAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
			    
			    // relacionar a um endereco //
		        doc.setDocEnderecoFK(endereco);
			        
		        DocumentoDao docDao = new DocumentoDao();
		        
		        docDao.salvarDocumento(doc);
		      
		        obsList.add(doc);
		        
		        modularBotoes();
		        
		        Alerta a = new Alerta();
		        a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro salvo com sucesso!!!", new ButtonType[] { ButtonType.OK }));
			      
	      		} // fim else
			      
		  } // fim try
		  
		  catch (Exception ex)	{
			  System.out.println("Erro: " + ex);
			  ex.printStackTrace();
	      
	      Alerta a = new Alerta();
	      a.alertar(new Alert(Alert.AlertType.ERROR, "erro na conexão, tente novamente!", new ButtonType[] { ButtonType.OK }));
	      
	    }
		  
	}

	public void editarDocumento()	{
		  
		    if (tfDocumento.isDisable()) {
		
		    // Não desabilitar o tipo de documento, não é possível editar o tipo de documento
			// cbTipoDocumento.setDisable(false);
		    tfDocumento.setDisable(false);
		     tfSEI.setDisable(false);
		     tfProcessoSEI.setDisable(false);
		      
		      dpDataCriacao.setDisable(false);
		      dpDataDistribuicao.setDisable(false);
		      dpDataRecebimento.setDisable(false);
		      
		    }
		    
			    else if ((tfDocumento.getText().isEmpty()) || (tfProcessoSEI.getText().isEmpty())) {
			    	
			      Alerta a = new Alerta();
			      a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
			      
			    }
		    
				    else
				    {
				      Documento doc = (Documento) tvDocumento.getSelectionModel().getSelectedItem();
				      
				      
				      doc.setDocNumeracao(tfDocumento.getText());
				      doc.setDocSEI(tfSEI.getText());
				      doc.setDocProcesso(tfProcessoSEI.getText());
				      
				      
				      if (dpDataCriacao.getValue() == null) {
				    	  doc.setDocDataCriacao(null);
				      } else {
				    	  doc.setDocDataCriacao(Date.valueOf((LocalDate)dpDataCriacao.getValue()));
				      }
				        
				      if (dpDataDistribuicao.getValue() == null) {
				    	  doc.setDocDataDistribuicao(null);
				      } else {
				    	  doc.setDocDataDistribuicao(Date.valueOf((LocalDate)dpDataDistribuicao.getValue()));
				      }
				      if (dpDataRecebimento.getValue() == null) {
				    	  doc.setDocDataRecebimento(null);
				      } else {
				    	  doc.setDocDataRecebimento(Date.valueOf((LocalDate)dpDataRecebimento.getValue()));
				      }
				      
				      doc.setDocDataAtualizacao(Timestamp.valueOf(LocalDateTime.now()));
					  
					    
					  // relacionar a um endereco //
				      doc.setDocEnderecoFK(endereco);
				      
				      DocumentoDao docDao = new DocumentoDao();
				      
				      docDao.mergeDocumento(doc);
				      
				      obsList.remove(doc);
				      obsList.add(doc);
				     
				  
				      modularBotoes();
				      
				    Alerta a = new Alerta();
				    a.alertar(new Alert(Alert.AlertType.ERROR, "Cadastro editado com sucesso!!!", new ButtonType[] { ButtonType.OK }));
				    
				    } // fim else
		    
	  }
  
	public void excluirDocumento ()	{
		   
		  try
			    {
			  	  Documento doc = (Documento) tvDocumento.getSelectionModel().getSelectedItem();
			      
			      int id = doc.getDocID();
			      
			      DocumentoDao dDao = new DocumentoDao();
			      
			      dDao.removerDocumento(Integer.valueOf(id));
			      
			      obsList.remove(doc);
			      
			      modularBotoes();
			      
			      Alerta a = new Alerta();
			      a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído com sucesso!!!", new ButtonType[] { ButtonType.OK }));
			    }
		  
			    catch (Exception e)	{
			    	
				      Alerta a = new Alerta();
				      a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao excluir o cadastro!!!", new ButtonType[] { ButtonType.OK }));
			      
			    	}
		  
	  }
	  
	public void cancelarDocumento ()	{
	   
		modularBotoes();
	}
	  
	String strPesquisa = "";
	public void pesquisarDocumento ()	{
		  
	  strPesquisa = tfPesquisar.getText();
	    
	  listarDocumentos(strPesquisa);
	   
	   modularBotoes();
	    
	  }

	Pane pTelaUsuario;
		TranslateTransition ttParUsDireita;
		TranslateTransition ttParUsEsquerda;
		Double dblTUsuario;
	  
	public void inicializarTelaUsuario () {
		  
	    if (pTelaUsuario == null) {
	    	
	    	pTelaUsuario = new Pane();
	    	pTelaUsuario.setPrefSize(500.0, 500.0);
	    	
	    	Pane p = new Pane();
	    
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TelaUsuario.fxml"));
			loader.setRoot(p);
			loader.setController(new TelaUsuarioControlador (this));
		
			try {
				loader.load();
			} 
				catch (IOException e)	{
					System.out.println("erro leitura do pane");
					e.printStackTrace();
				}
		
			pTelaUsuario.getChildren().add(p);
			
			p1.getChildren().add(pTelaUsuario);
		
			ttParUsEsquerda = new TranslateTransition(new Duration(350.0), pTelaUsuario);
			ttParUsEsquerda.setToX(15.0);
					  
			ttParUsDireita = new TranslateTransition(new Duration(350.0), pTelaUsuario);
			ttParUsDireita.setToX(1300.0);
  
			pTelaUsuario.setTranslateX(1300.0);
		  
		}
	    
	    ttParUsEsquerda.play();
	    
	  }
	  
	public void movimentarTelaUsuario ()	{

		if (ttParUsDireita != null)
			ttParUsDireita.play();

	}
	
	
	TranslateTransition ttEndDireita;
	TranslateTransition ttEndEsquerda;
	Pane pTelaEndereco;
	Double dblTransicaoEndereco = 0.0;
	
	TelaEnderecoControlador telaEnderecoControlador;

	public void inicializarTelaEndereco() {
		  
	    if (pTelaEndereco == null) {
	    	
	    	pTelaEndereco = new Pane();
	    	pTelaEndereco.setPrefSize(500.0, 500.0);
	
	    	Pane p = new Pane();
	    	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TelaEndereco.fxml"));
				loader.setRoot(p);
			
				loader.setController(telaEnderecoControlador = new TelaEnderecoControlador(this));
		
			try {
				loader.load();
			}
				catch (IOException e)	{
					System.out.println("erro leitura do pane");
					e.printStackTrace();
				}
		
			pTelaEndereco.getChildren().add(p);
			
			p1.getChildren().add(pTelaEndereco);
		
			ttEndEsquerda = new TranslateTransition(new Duration(350.0), pTelaEndereco);
			ttEndEsquerda.setToX(15.0);
				  
			ttEndDireita = new TranslateTransition(new Duration(350.0), pTelaEndereco);
			ttEndDireita.setToX(1300.0);
		  
			pTelaEndereco.setTranslateX(1300.0);
		  
		}
	    
	    ttEndEsquerda.play();
	    
	  }
	
	public void movimentarTelaEndereco ()	{
		
		if (ttEndDireita != null)
			ttEndDireita.play();

	  }
}




/*
System.out.println("||||||||||||||||| INÍCIO ||||||||||||||||||||||||");

for (int i = 0; i<listaMalaDireta.size(); i++) {

	for (int ii=0; ii < listaMalaDireta.get(i)[0].length; ii++) {
		
		switch (listaMalaDireta.get(i)[0][ii].getClass().getName()) {
		
		case "entidades.Subterranea":

			for (Finalidade f : ((Subterranea)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

				System.out.println("subterranea");
				
				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					System.out.println(" +++++ Requerida: " + ((FinalidadeRequerida) f).getFrFinalidade1());
				}
				
				if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
					System.out.println(" +++++ Autorizada: " + ((FinalidadeAutorizada) f).getFaFinalidade1());
				}

			}
			*/
			
			/*
			for (int a = 0; a<5; a++) {
				
				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					fr = (FinalidadeRequerida) f;
					System.out.println("sub - finalidade requerida ID " + fr.getFinID());
				}

			}*/
/*

			break;
		case "entidades.Superficial":

			for (Finalidade f : ((Superficial)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

				System.out.println("superficial");
				
				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					System.out.println(" +++++ Requerida: " + ((FinalidadeRequerida) f).getFrFinalidade1());
				}
				
				if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
					System.out.println(" +++++ Autorizada: " + ((FinalidadeAutorizada) f).getFaFinalidade1());
				}

			}

			break;

		default:
			break;
			
		} // fim while

	}


} // fim loop for

*/
