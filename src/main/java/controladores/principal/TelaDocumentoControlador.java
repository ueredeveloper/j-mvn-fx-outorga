package controladores.principal;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.DocumentoDao;
import entidades.Documento;
import entidades.Endereco;
import javafx.animation.TranslateTransition;
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

public class TelaDocumentoControlador implements Initializable {

	@FXML Pane pTelaDocumento;
	
	Documento documento = new Documento();
	
	Endereco endereco = new Endereco();
	
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
		
	TabEnderecoControlador tabEndCon;
	/**
	 * Construtor
	 * @param tabEndCon
	 */
	public TelaDocumentoControlador (TabEnderecoControlador tabEndCon) {
		this.tabEndCon = tabEndCon;
	}
	
	public static TelaDocumentoControlador telaDocCon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		telaDocCon = this;

		inicializarComponentes ();

		pTelaDocumento.getChildren().addAll(pPrincipal);

		modularBotoes();
		selecionarDocumento();
		acionarBotoes();
	}

	Componentes com;
	Double prefSizeWHeLayXY [][];
	Pane pPrincipal = new Pane();

	Pane pEndereco;
	Label lblEndereco;
	Button btnEndereco;

	ArrayList<Node> componentesEndereco = new ArrayList<Node>();

	Pane pDadosDocumento;
	ComboBox<String> cbTipo;
	TextField tfNumeracao;
	TextField tfSEI;
	TextField tfProcesso;
	DatePicker dpDataDistribuicao;
	DatePicker dpDataRecebimento;

	ArrayList<Node> componentesDocumento = new ArrayList<Node>();

	Pane pPersistencia = new Pane();

	Button btnNovo;
	Button btnSalvar;
	Button btnEditar;
	Button btnExcluir;
	Button btnCancelar;
	Button btnPesquisar;
	TextField tfPesquisar;

	ArrayList<Node> componentesPersistencia = new ArrayList<Node>();

	Label lblDataAtualizacao= new Label();

	TableView<Documento> tvLista = new TableView<Documento>();
	TableColumn<Documento, String> tcTipo = new TableColumn<Documento, String>("Tipo");
	TableColumn<Documento, String> tcNumeracao = new TableColumn<Documento, String>("Numeração");
	TableColumn<Documento, String> tcSEI = new TableColumn<Documento, String>("Número SEI");
	TableColumn<Documento, String> tcProcesso = new TableColumn<Documento, String>("Número do Processo");


	ObservableList<Documento> obsList = FXCollections.observableArrayList();

  	public void inicializarComponentes () {

  		componentesEndereco.add(pEndereco = new Pane());
  		componentesEndereco.add(new Label ("ENDEREÇO"));
  		componentesEndereco.add(lblEndereco = new Label ());
  		componentesEndereco.add(btnEndereco = new  Button(">>>"));

  		prefSizeWHeLayXY = new Double[][] { 
  			{850.0,60.0,20.0,10.0},
  			{90.0,30.0,15.0,15.0},
  			{648.0,30.0,110.0,15.0},
  			{65.0,25.0,770.0,19.0},
  		};

  		com = new Componentes();
  		com.popularTela(componentesEndereco, prefSizeWHeLayXY, pPrincipal);

  		componentesDocumento.add(pDadosDocumento = new Pane());
  		componentesDocumento.add(new Label("TIPO:"));
  		componentesDocumento.add(cbTipo =  new ComboBox<String>());
  		componentesDocumento.add(new Label("NUMERAÇÃO"));
  		componentesDocumento.add(tfNumeracao = new TextField());
  		componentesDocumento.add(new Label("SEI:"));
  		componentesDocumento.add(tfSEI = new TextField());
  		componentesDocumento.add(new Label("PROCESSO:"));
  		componentesDocumento.add(tfProcesso = new TextField());

  		componentesDocumento.add(new Label("DATA DE RECEBIMENTO:"));
  		componentesDocumento.add(dpDataRecebimento = new DatePicker());
  		componentesDocumento.add(new Label("DATA DE DISTRIBUIÇÃO:"));
  		componentesDocumento.add(dpDataDistribuicao = new DatePicker());


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


  		ObservableList<String> obsListTiposDocumento = FXCollections
  				.observableArrayList(

  						"Requerimento de Outorga"	,
  						"Solicitação",
  						"Ofício"	,
  						"Carta",
  						"Memorando"	,
  						"Relatório de Vistoria"	,
  						"Auto de Infração"	,
  						"Termo de Notificação"	,
  						"Recurso",
  						"Outros"

  						); 	

  		cbTipo.setItems(obsListTiposDocumento);

  		com = new Componentes();
  		com.popularTela(componentesDocumento, prefSizeWHeLayXY, pPrincipal);

  		componentesPersistencia.add(pPersistencia = new Pane());
  		componentesPersistencia.add(btnNovo = new Button("NOVO"));
  		componentesPersistencia.add(btnSalvar = new Button("SALVAR"));
  		componentesPersistencia.add(btnEditar = new Button("EDITAR"));
  		componentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
  		componentesPersistencia.add(btnCancelar = new Button("CANCELAR"));

  		componentesPersistencia.add(tfPesquisar = new TextField());
  		componentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

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
  		com.popularTela(componentesPersistencia, prefSizeWHeLayXY, pPrincipal);

  		
  		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});
		

  		tcTipo.setCellValueFactory(new PropertyValueFactory<Documento, String>("docTipo"));
  		tcNumeracao.setCellValueFactory(new PropertyValueFactory<Documento, String>("docNumeracao"));
  		tcSEI.setCellValueFactory(new PropertyValueFactory<Documento, String>("docSEI"));
  		tcProcesso.setCellValueFactory(new PropertyValueFactory<Documento, String>("docProcesso"));

  		tcTipo.setPrefWidth(200.0);
  		tcNumeracao.setPrefWidth(200.0);
  		tcSEI.setPrefWidth(200.0);
  		tcProcesso.setPrefWidth(210.0);


  		tvLista.getColumns().add(tcTipo); //, tcDocsSEI, tcProcsSEI });
  		tvLista.getColumns().add(tcNumeracao);
  		tvLista.getColumns().add(tcSEI);
  		tvLista.getColumns().add(tcProcesso);

  		tvLista.setPrefSize(840.0, 185.0);
  		tvLista.setLayoutX(25.0);
  		tvLista.setLayoutY(320.0);


  		tvLista.setItems(obsList);    

  		lblDataAtualizacao.setPrefSize(247.0, 22.0);
  		lblDataAtualizacao.setLayoutX(615.0);
  		lblDataAtualizacao.setLayoutY(515.0);

  		pPrincipal.getChildren().addAll(tvLista, lblDataAtualizacao);

  		pTelaDocumento.setStyle("-fx-background-color: rgba(223,226,227, 0.7);");

  		pPrincipal.setStyle("-fx-background-color: white");
  		pPrincipal.setPrefSize(890, 670);
  		pPrincipal.setLayoutX(60);
  		pPrincipal.setLayoutY(0.0);


  		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {

  			@Override public void handle(ActionEvent e) {

  				tabEndCon.movimentarTelaDocumento();

  			}
  		});


  	}		

	public void habilitarDocumento () {

		cbTipo.setValue(null);
		tfNumeracao.setText("");
		tfSEI.setText("");
		tfProcesso.setText("");

		dpDataDistribuicao.getEditor().clear();
		dpDataRecebimento.getEditor().clear();

		dpDataDistribuicao.setDisable(false);
		dpDataRecebimento.setDisable(false);

		cbTipo.setDisable(false);
		tfNumeracao.setDisable(false);
		tfSEI.setDisable(false);
		tfProcesso.setDisable(false);

		btnSalvar.setDisable(false);

		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(true);

	}
	  
	public void salvarDocumento ()	{

		try {

			if ((tfSEI.getText().isEmpty()) || 
					(tfProcesso.getText().isEmpty()))
			{
				Alerta a = new Alerta();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));
			}
			else
			{
				Documento doc = new Documento();

				doc.setDocTipo(cbTipo.getValue());
				doc.setDocNumeracao(tfNumeracao.getText());
				doc.setDocSEI(tfSEI.getText());
				doc.setDocProcesso(tfProcesso.getText());

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

				// Endereco end = endereco;

				//end.getDocumentos().add(doc);

				doc.setDocEnderecoFK(endereco);

				DocumentoDao docDao = new DocumentoDao();

				docDao.salvarDocumento(doc);


				/*
				if (intControlador == 0) {
					TabEnderecoControlador.controladorAtendimento.setDocumento(doc);

				}

				if (intControlador == 1) {
					TabEnderecoControlador.controladorFiscalizacao.setDocumento(doc);
				}

				if (intControlador == 2) {
					TabEnderecoControlador.controladorOutorga.setDocumento(doc);
				}
				*/

				obsList.add(doc);

				modularBotoes ();

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
	  
	public void editarDocumento ()	{

		if (tfNumeracao.isDisable()) {

			// Não desabilitar o tipo de documento, não é possível editar o tipo de documento
			//cbTipo.setDisable(false);
			tfNumeracao.setDisable(false);
			tfSEI.setDisable(false);
			tfProcesso.setDisable(false);

			dpDataDistribuicao.setDisable(false);
			dpDataRecebimento.setDisable(false);

		}

		else if ((tfSEI.getText().isEmpty()) || (tfProcesso.getText().isEmpty())) {

			Alerta a = new Alerta();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Documento, Processo SEI!!!", new ButtonType[] { ButtonType.OK }));

		}

		else
		{
			Documento doc = (Documento)tvLista.getSelectionModel().getSelectedItem();

			doc.setDocTipo(cbTipo.getValue());
			doc.setDocNumeracao(tfNumeracao.getText());
			doc.setDocSEI(tfSEI.getText());
			doc.setDocProcesso(tfProcesso.getText());

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


			doc.setDocEnderecoFK(endereco);

			DocumentoDao dDao = new DocumentoDao();

			dDao.mergeDocumento(doc);

			obsList.remove(doc);
			obsList.add(doc);

			/* transmitir demanda para a tab endereco */

			/*
			if (intControlador == 0) {
				TabEnderecoControlador.controladorAtendimento.setDocumento(doc);

			}

			if (intControlador == 1) {
				TabEnderecoControlador.controladorFiscalizacao.setDocumento(doc);
			}

			if (intControlador == 2) {
				TabEnderecoControlador.controladorOutorga.setDocumento(doc);
			}
			*/

			modularBotoes();

			Alerta a = new Alerta();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Cadastro editado com sucesso!!!", new ButtonType[] { ButtonType.OK }));

		} // fim else

	}
	  
	public void excluirDocumento ()	{

		try
		{

			Documento doc = (Documento)tvLista.getSelectionModel().getSelectedItem();

			DocumentoDao docDao = new DocumentoDao();

			docDao.removerDocumento(doc.getDocID());

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
	  
	public void acionarBotoes () {

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
			}
		});



		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});



	}
	  
	  	TranslateTransition tProEsquerda;
	  		TranslateTransition tProDireita;
	  			Pane pTelaProcesso;
	  				Double dblTransTelaProcesso;
	  						
	public void listarDocumentos (String strPesquisa) {

		DocumentoDao docDao = new DocumentoDao();
		List<Documento> docList = docDao.listarDocumentos(strPesquisa);

		if (!obsList.isEmpty()) {
			obsList.clear();

		}

		for (Documento d : docList) {
			obsList.add(d);
		}

		tvLista.setItems(obsList);

	}

	//-- selecionar demandas -- //
	public void selecionarDocumento () {

		// TableView - selecionar demandas ao clicar //
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {

				Documento doc = (Documento) newValue;

				if (doc == null) {

					cbTipo.setValue(null);
					tfNumeracao.setText("");
					tfSEI.setText("");
					tfProcesso.setText("");

					dpDataRecebimento.getEditor().clear();
					dpDataDistribuicao.getEditor().clear();

					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);
					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);

				} else {

					documento = doc;

					// Não desabilitar o tipo de documento, não é possível editar o tipo de documento
					cbTipo.setDisable(true);

					// preencher os campos //
					cbTipo.setValue(doc.getDocTipo());
					tfNumeracao.setText(doc.getDocNumeracao());
					tfSEI.setText(doc.getDocSEI());
					tfProcesso.setText(doc.getDocProcesso());

					if (doc.getDocDataDistribuicao() == null) {
						dpDataDistribuicao.setValue(null);

					} else {
						Date dataDis = doc.getDocDataDistribuicao();
						dpDataDistribuicao.setValue(dataDis.toLocalDate());
					}

					if (doc.getDocDataRecebimento() == null) {
						dpDataRecebimento.setValue(null);
					} else {

						Date dataRec = doc.getDocDataRecebimento();
						dpDataRecebimento.setValue(dataRec.toLocalDate());
					}


					// mostrar data de atualizacao //
					FormatoData d = new FormatoData();
					try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(doc.getDocDataAtualizacao()));  // d.formatarData(demanda.getDemAtualizacao())
					lblDataAtualizacao.setTextFill(Color.BLACK);
					} catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
					lblDataAtualizacao.setTextFill(Color.RED);}

					/*
					if (intControlador == 0) {
						TabEnderecoControlador.controladorAtendimento.setDocumento(doc);

					}

					if (intControlador == 1) {
						TabEnderecoControlador.controladorFiscalizacao.setDocumento(doc);
					}

					if (intControlador == 2) {
						TabEnderecoControlador.controladorOutorga.setDocumento(doc);
					}
					*/


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

	public void modularBotoes () {

		cbTipo.setDisable(true);
		tfNumeracao.setDisable(true);
		tfNumeracao.setDisable(true);
		tfSEI.setDisable(true);
		tfProcesso.setDisable(true);

		dpDataDistribuicao.setDisable(true);
		dpDataRecebimento.setDisable(true);

		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);

		btnNovo.setDisable(false);
	}

}
