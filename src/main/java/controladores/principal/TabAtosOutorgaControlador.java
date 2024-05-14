package controladores.principal;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import dao.DocumentoDao;
import dao.ModelosDao;
import entidades.Documento;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.ModelosHTML;
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
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;
import principal.MalaDiretaAtosOutorga;
import util.NavegadorExterno;

public class TabAtosOutorgaControlador implements Initializable {


	Endereco endereco = new Endereco();

	Documento documento = new Documento();

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

	TableView<Documento> tvDocumento = new TableView<Documento>();
	TableColumn<Documento, String> tcTipoDocumento = new TableColumn<Documento, String>("Parecer");
	TableColumn<Documento, String> tcSEI = new TableColumn<Documento, String>("SEI");
	TableColumn<Documento, String> tcEndereco = new TableColumn<Documento, String>("Endereço");

	ObservableList<Documento> obsList = FXCollections.observableArrayList();

	@FXML Pane pAtosOutorga;

	Pane p1 = new Pane();
	BorderPane bp1 = new BorderPane();
	BorderPane bp2 = new BorderPane();
	ScrollPane sp = new ScrollPane();

	Label lblDataAtualizacao = new Label();


	ControladorOutorga controladorOutorga;
	ControladorAtendimento controladorAtendimento;
	ControladorFiscalizacao controladorFiscalizacao;
	
	NavegadorExterno navExt;

	public TabAtosOutorgaControlador (ControladorOutorga controladorOutorga) {
		this.controladorOutorga = controladorOutorga;

	}
	
	public TabAtosOutorgaControlador (ControladorAtendimento controladorAtendimento) {
		this.controladorAtendimento = controladorAtendimento;

	}
	
	public TabAtosOutorgaControlador (ControladorFiscalizacao controladorFiscalizacao) {
		this.controladorFiscalizacao = controladorFiscalizacao;

	}
	


	@Override
	public void initialize(URL url, ResourceBundle rb) {

		bp1.minWidthProperty().bind(pAtosOutorga.widthProperty());
		bp1.maxHeightProperty().bind(pAtosOutorga.heightProperty().subtract(60));

		bp1.getStyleClass().add("border-pane");

		bp2.setPrefHeight(800.0D);
		bp2.minWidthProperty().bind(pAtosOutorga.widthProperty());

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		sp.setContent(bp2);

		bp1.setCenter(sp);

		pAtosOutorga.getChildren().add(bp1);

		p1.setMaxSize(980.0, 1000.0);
		p1.setMinSize(980.0, 1000.0);

		p1.setStyle("fx-background-color: red");

		bp2.setTop(p1);
		BorderPane.setAlignment(p1, Pos.CENTER);



		lblDataAtualizacao.setPrefSize(247.0, 22.0);
		lblDataAtualizacao.setLayoutX(705.0);
		lblDataAtualizacao.setLayoutY(450.0);

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
		tvDocumento.setLayoutY(255.0);

		tvDocumento.getColumns().add(tcTipoDocumento); //, tcDocsSEI, tcProcsSEI });
		tvDocumento.getColumns().add(tcSEI);
		tvDocumento.getColumns().add(tcEndereco);


		tvDocumento.setItems(obsList);

		p1.getChildren().addAll(tvDocumento, lblDataAtualizacao);

		inicializarComponentes ();

		modularBotoes();
		acionarBotoes();
		selecionarDocumento (); 


	}


	Componentes com;
	Double prefSizeWHeLayXY [][];

	Pane pEndereco;
	Label lblEndereco;
	Button btnEndereco;
	ArrayList<Node> componentesEndereco = new ArrayList<Node>();

	Pane pDadosAtosOutorga;
	ComboBox<String> cbTipoDocumento;
	TextField tfDocumento;
	TextField tfSEI;
	TextField tfProcessoSEI;
	DatePicker dpDataCriacao;
	DatePicker dpDataDistribuicao;
	DatePicker dpDataRecebimento;

	ArrayList<Node> componentesParecer = new ArrayList<Node>();

	ArrayList<Node> componentesInterferencias = new ArrayList<Node>();



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


	// inicializacao dos botoes de persistencia
	Pane  pInterferencias;
	ComboBox<Interferencia> cbInterferencia;
	TableView<Interferencia> tvInterferencia;
	ComboBox<ModelosHTML> cbModelosHTML;
	Button btnLimparInterferencias;
	Button btnAutalizarModelosHTML;
	Button btnGeradorAtoOutorga;


	ArrayList<Node> componentesInterferencia = new ArrayList<Node>();


	TableColumn<Interferencia, String> tcTipoInterferencia  = new TableColumn<>("Tipo de Interferência");
	TableColumn<Interferencia, String> tcSituacaoInterferencia  = new TableColumn<>("Situação");
	ObservableList<Interferencia> tvObsListInterferencia = FXCollections.observableArrayList();
	ObservableList<Interferencia> cbObsListInterferencia = FXCollections.observableArrayList();
	ObservableList<ModelosHTML> obsListModelosHTML = FXCollections.observableArrayList();


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

		componentesParecer.add(pDadosAtosOutorga = new Pane());
		componentesParecer.add(new Label("TIPO:"));
		componentesParecer.add(cbTipoDocumento = new ComboBox<String>());
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

			{950.0,90.0,15.0,80.0},
			{135.0,30.0,10.0,15.0},
			{135.0,30.0,10.0,45.0},

			{95.0,30.0,155.0,15.0},
			{95.0,30.0,155.0,45.0},

			{95.0,30.0,260.0,15.0},
			{95.0,30.0,260.0,45.0},

			{120.0,30.0,365.0,15.0},
			{120.0,30.0,365.0,45.0},

			{125.0,30.0,495.0,15.0},
			{125.0,30.0,495.0,45.0},

			{150.0,30.0,630.0,15.0},
			{150.0,30.0,630.0,45.0},

			{150.0,30.0,790.0,15.0},
			{150.0,30.0,790.0,45.0},

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

			{930.0,60.0,25.0,180.0},
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
		
		
		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});


		componentesInterferencia.add(pInterferencias = new Pane());
		componentesInterferencia.add(new Label("INTERFERÊNCIAS"));
		componentesInterferencia.add(cbInterferencia = new ComboBox<Interferencia>());
		componentesInterferencia.add(tvInterferencia = new TableView<Interferencia>());
		componentesInterferencia.add(btnLimparInterferencias = new Button("limpar"));
		componentesInterferencia.add(new Label("MODELOS"));
		componentesInterferencia.add(cbModelosHTML = new ComboBox<ModelosHTML>());
		componentesInterferencia.add(btnAutalizarModelosHTML = new Button("atualizar"));

		componentesInterferencia.add(btnGeradorAtoOutorga = new Button("GERAR ATO"));
		
		//Button btnLimparInterferencias;
		//Button btnAutalizarModelosHTML;


		prefSizeWHeLayXY = new Double[][] { 

			{930.0,305.0,25.0,480.0},
			
			{400.0,30.0,265.0,10.0},
			{400.0,30.0,265.0,40.0},
			
			{400.0,100.0,265.0,80.0},
			
			{100.0,30.0,681.0,80.0},
			
			{400.0,30.0,265.0,180.0},
			{400.0,30.0,265.0,210.0},
			
			{100.0,30.0,681.0,210.0},
			{400.0,30.0,265.0,250.0},
			

		}; 

		com = new Componentes();
		com.popularTela(componentesInterferencia, prefSizeWHeLayXY, p1);
		
		// inicializar navegador externo
		navExt = new NavegadorExterno(p1);
		
		navExt.inicializarNavegadorExterno(25.0, 795.0);
		
		
		cbInterferencia.setConverter(new StringConverter<Interferencia>() {

			public String toString(Interferencia i) {
				return i.getInterTipoInterferenciaFK().getTipoInterDescricao() 
						+ ", Tipo de Outorga: " + i.getInterTipoOutorgaFK().getTipoOutorgaDescricao()
						+ ", Subtipo: " + i.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao();
			}

			public Interferencia fromString(String string) {
				return null;
			}
		});
		
		cbInterferencia.valueProperty().addListener(new ChangeListener<Interferencia>() {
			@Override 
			public void changed(ObservableValue<? extends Interferencia> ov, Interferencia oldValue, Interferencia newValue) {  

				if (newValue != null) {
					tvObsListInterferencia.addAll(newValue);
					
					obsListModelosHTML.clear();
					
					for (ModelosHTML m : listaModelosHTML) {

						if (m.getModTipoInterferencia().equals(newValue.getInterTipoInterferenciaFK().getTipoInterDescricao())) {
		
							obsListModelosHTML.add(m);
						}
					}
		
				} // fim if
				
					
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
		
		cbInterferencia.setItems(cbObsListInterferencia);

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

		tvInterferencia.setItems(tvObsListInterferencia);


		ObservableList<String> obsListTipoDocumento = FXCollections
				.observableArrayList(

						"Despacho"	,
						"Registro"

						); 	

		cbTipoDocumento.setItems(obsListTipoDocumento);



	}
	
	public void listarAtosOutorga (String strPesquisa) {
		
		DocumentoDao docDao = new DocumentoDao();

		List<Documento> docList = docDao.listarAtosOutorga(strPesquisa);

		if (!obsList.isEmpty()) {
			obsList.clear();

		}

		for (Documento doc : docList) {
			
			if (doc.getDocTipo().equals("Despacho") || doc.getDocTipo().equals("Registro")) {
				
				obsList.add(doc);
			}
			
		}
		

		tvDocumento.setItems(obsList);
		
		
		if (obsListModelosHTML.isEmpty()) {

			ModelosDao mDao = new  ModelosDao();

				listaModelosHTML = mDao.listarModelo("");

				obsListModelosHTML.addAll(listaModelosHTML);
			
		}

	}

	public void modularBotoes () {

		cbTipoDocumento.setDisable(true);
		cbTipoDocumento.setValue(null);
		
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

	public void habilitarDocumento () {
		
		cbTipoDocumento.setDisable(false);
		cbTipoDocumento.setValue(null);
		
		tfDocumento.setText("");
		tfSEI.setText("");
		tfProcessoSEI.setText("");

		dpDataCriacao.getEditor().clear();
		dpDataDistribuicao.getEditor().clear();
		dpDataRecebimento.getEditor().clear();
		
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
	
	List<ModelosHTML> listaModelosHTML = new ArrayList<>();

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
		
		btnGeradorAtoOutorga.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent e) {

				// capturar o modelo html que será usado pelo combobox
				
				// e preciso para o futuro ver de buscar apenas os modelos necessários ao invés de trazer todos
				String modeloHTML = cbModelosHTML.getSelectionModel().getSelectedItem().getModConteudo();
				
				// capturar a tabela que sera colocada dentro do modelo html selecionado, pode ser varias interferencias em um modelo, entao varias tabelas
				String modeloTabelaLimitesOutorgados = listaModelosHTML.get(3).getModConteudo();
			
				
				if (documento.getDocProcessoFK() == null) {
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Documento sem Processo Principal !!!", ButtonType.OK));
				}
				
				MalaDiretaAtosOutorga mlDoc = new MalaDiretaAtosOutorga(modeloHTML, modeloTabelaLimitesOutorgados, documento, endereco, endereco.getEndUsuarioFK(), tvObsListInterferencia);
				
				String strHTML = mlDoc.criarAtoOutorga();
				
				try { ControladorNavegacao.conNav.setHTML(strHTML); 
				} 
					catch (Exception ee) {

						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.ERROR, "Inicialize o navegador SEI !!!", ButtonType.OK));
					} // fim catch
				
				
				navExt.setarStringHTML(strHTML);
				
				//so e necessario no parecer
				//navExt.setObjetosAnexo(listaMalaDireta, obsListUsuariosMalaDireta, strAnexoParecer, strTabela1, strTabela2);
				
			} // fim metodo handle
		
		});

		
		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				inicializarTelaEndereco();
				telaEnderecoControlador.setObjetoDeEdicao(documento);
			}
		});
		
		
		btnLimparInterferencias.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				tvObsListInterferencia.clear();
			}
		});
		
		
		btnAutalizarModelosHTML.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				
				obsListModelosHTML.clear();
				
				ModelosDao mDao = new  ModelosDao();

					listaModelosHTML = mDao.listarModelo("");
					obsListModelosHTML.addAll(listaModelosHTML);
			}
		});
		

	}

	public void salvarDocumento ()	{

		try {

			if (endereco.getEndLogradouro() == null) {

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
				Documento doc = new Documento();

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

			// doc.setDocTipo(cbTipoDocumento.getValue()); nao pode editar o tipo de  documento
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

		listarAtosOutorga(strPesquisa);

		modularBotoes();

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

				} else {

					documento = doc;
					
					setEndereco(doc.getDocEnderecoFK());

					// preencher os campos //
					
					cbTipoDocumento.setValue(doc.getDocTipo());
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


					if (!cbObsListInterferencia.isEmpty()) {
						cbObsListInterferencia.clear();

					}
					
					tvObsListInterferencia.clear();
					
					// adicionar o list dentro de um hashset para nao repetir valores
					List<Interferencia> interList = new ArrayList<Interferencia>(new HashSet<Interferencia>(doc.getDocEnderecoFK().getInterferencias())); 
	
					
					cbObsListInterferencia.addAll(interList);
				
					// Limpar a tableView Interferencia
					//obsListInterferencia.clear();

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
