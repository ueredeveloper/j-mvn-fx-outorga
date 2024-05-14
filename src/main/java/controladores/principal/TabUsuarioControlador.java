package controladores.principal;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import dao.ModelosDao;
import dao.UsuarioDao;
import entidades.BancoAccess;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.ModelosHTML;
import entidades.Usuario;
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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;
import principal.ListasComboBox;
import principal.MalaDireta;
import util.BuscadorBancos;
import util.FormatadorCPFCNPJ;
import util.MalaDiretaUnica;
import util.NavegadorExterno;
import util.Registro;

public class TabUsuarioControlador implements Initializable {

	Usuario usuario = new Usuario();
	Endereco endereco = new Endereco ();
	Interferencia interferencia = new Interferencia();
	
	// Driver do Navegador Externo
	public static WebDriver webDriver;

	public void setEndereco (Endereco endereco) {

		this.endereco = endereco;

		// preencher o label com a demanda selecionada //

		if (endereco != null) {

			lblEndereco.setText(
					endereco.getEndLogradouro() 
					+ ", CEP: " + endereco.getEndCEP()
					+ ", Cidade: " + endereco.getEndCidade()
					);

			lblEndereco.setStyle("-fx-text-fill: #4A4A4A;"); 

		} else {

			lblEndereco.setText("Não há endereco de empreendimento cadastrado!");
			lblEndereco.setStyle("-fx-text-fill: #FF0000;");
		}


		//System.out.println("tabUsuario setEndereco" + endereco.getEndLogradouro());


	}

	//-- Strings --//
	String strPesquisa = "";

	//-- TableView Endereço --//
	private TableView <Usuario> tvLista = new TableView<>();

	TableColumn<Usuario, String> tcNome = new TableColumn<>("Nome");
	TableColumn<Usuario, String> tcCPFCNPJ = new TableColumn<>("CPF/CNPJ");
	TableColumn<Usuario, String> tcEndereco = new TableColumn<>("Endereço de Correpondência");

	ObservableList<Usuario> obsList = FXCollections.observableArrayList();

	ObservableList<Endereco> obsListEnderecoEmpreendimento = FXCollections.observableArrayList();

	ObservableList<Interferencia> obsListInterferencia = FXCollections.observableArrayList();

	ObservableList<String> olTipoPessoa = FXCollections
			.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica

	

	ObservableList<String> olDF = FXCollections
			.observableArrayList("DF" , "GO", "Outro"); // box - seleção pessoa físcia ou jurídica

	public void habilitarEndereco () {

		cbTipoPessoa.setValue("Física");

		tfNome.setText(null);
		tfCPFCNPJ.setText(null);
		tfLogradouro.setText(null);

		cbRA.setValue(null);

		tfCEP.setText(null);
		tfCidade.setText("Brasília");

		cbUF.setValue("DF");

		tfTelefone.setText(null);
		tfCelular.setText(null);
		tfEmail.setText(null);
		
		tfRepresentante.setText(null);
		tfRepreTelefone.setText(null);

		cbTipoPessoa.setDisable(false);

		tfNome.setDisable(false);
		tfCPFCNPJ.setDisable(false);

		checkEnderecoEmpreendimento.setDisable(false);

		tfLogradouro.setDisable(false);
		cbRA.setDisable(false);
		tfCEP.setDisable(false);
		tfCidade.setDisable(false);
		cbUF.setDisable(false);
		tfTelefone.setDisable(false);
		tfCelular.setDisable(false);
		tfEmail.setDisable(false);
		
		tfRepresentante.setDisable(false);
		tfRepreTelefone.setDisable(false);

		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnEditar.setDisable(true);


	}

	public void salvarEndereco () {


		if (endereco.getEndLogradouro() == null || endereco == null) {

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Endereço relacionado ao usuário não selecionado!!!", ButtonType.OK));

		} else {

			if (cbTipoPessoa.getValue() == null  ||
					tfNome.getText().isEmpty()

					) {

				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Tipo e Nome do Usuário!!!", ButtonType.OK));

			} else {
				
				try {

				Usuario us = new  Usuario ();

				us.setUsTipo(cbTipoPessoa.getValue());
				us.setUsNome(tfNome.getText());
				// retirar os caracteres especiais do cpf ou cnpj
				us.setUsCPFCNPJ(tfCPFCNPJ.getText().replaceAll("\\D","")); 
				us.setUsLogadouro(tfLogradouro.getText());
				us.setUsRA(cbRA.getValue());
				us.setUsCidade(tfCidade.getText());
				us.setUsEstado(cbUF.getValue());
				us.setUsCEP(tfCEP.getText());
				us.setUsTelefone(tfTelefone.getText());
				us.setUsCelular(tfCelular.getText());
				us.setUsEmail(tfEmail.getText());
				
				us.setUsRepresentante(tfRepresentante.getText());
				us.setUsRepresentanteTelefone(tfRepreTelefone.getText());


				us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

				// chama instancia endereco //	
				Endereco end = new Endereco();

				// iguala endereco ao selecionado pelo usuario
				end = endereco;
				// neste endereco seta o usuario
				end.setEndUsuarioFK(us);
				// na lista de endereco que  pertence ao usuario adiciona o endereco em questao
				us.getEnderecos().add(end);

				UsuarioDao  usDao = new UsuarioDao();

				usDao.salvarUsuario(us);
				usDao.mergeUsuario(us);

				obsList.add(us);

				modularBotoes();

				//Alerta //
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Informe: Cadastro salvo com sucesso!!!", ButtonType.OK));

				}
			
				catch (Exception e ) {
					
					Alerta a = new Alerta();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Erro ao salvar ou editar!!!", new ButtonType[] { ButtonType.OK }));
					
					System.out.println(e);
				}

			}
		}

	}

	public void editarEndereco () {

		if (cbTipoPessoa.isDisable()) {

			cbTipoPessoa.setDisable(false);
			tfNome.setDisable(false);

			checkEnderecoEmpreendimento.setDisable(false);

			tfCPFCNPJ.setDisable(false);
			tfLogradouro.setDisable(false);
			cbRA.setDisable(false);
			tfCEP.setDisable(false);
			tfCidade.setDisable(false);
			cbUF.setDisable(false);
			tfTelefone.setDisable(false);
			tfCelular.setDisable(false);
			tfEmail.setDisable(false);
			
			tfRepresentante.setDisable(false);
			tfRepreTelefone.setDisable(false);

			btnSalvar.setDisable(true);
			btnEditar.setDisable(false);
			btnExcluir.setDisable(true);
			btnCancelar.setDisable(false);

		}

		else {

			if (cbTipoPessoa.getValue() == null  ||
					tfNome.getText().isEmpty()

					) {

				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Tipo e Nome do Usuário!!!", ButtonType.OK));

			} else {
				
				try {

					Usuario us = tvLista.getSelectionModel().getSelectedItem(); 
	
					// -- preencher os campos -- //
					us.setUsTipo(cbTipoPessoa.getValue()); 
					us.setUsNome(tfNome.getText());
					// retirar os caracteres especiais do cpf ou cnpj
					us.setUsCPFCNPJ(tfCPFCNPJ.getText().replaceAll("\\D","")); 
					us.setUsLogadouro(tfLogradouro.getText()); 
	
					us.setUsRA(cbRA.getValue()); 
	
					us.setUsCEP(tfCEP.getText()); 
					us.setUsCidade(tfCidade.getText()); 
	
					us.setUsEstado(cbUF.getValue()); 
	
					us.setUsTelefone(tfTelefone.getText());
					us.setUsCelular(tfCelular.getText());
					us.setUsEmail(tfEmail.getText());
					
					us.setUsRepresentante(tfRepresentante.getText());
					us.setUsRepresentanteTelefone(tfRepreTelefone.getText());
					
					
					us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
	
					Endereco end = new Endereco();
					// captura um endereco relacionado
					end = endereco;
					// adiciona neste endereco o id usuario selecionado
					end.setEndUsuarioFK(us);
					// adiciona este endereco no setEnderecos do usuario
					us.getEnderecos().add(end);
	
					UsuarioDao usDao = new UsuarioDao();
	
					usDao.mergeUsuario(us);
	
					obsList.remove(us);
					obsList.add(us);
	
					modularBotoes();
	
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro editado com sucesso!!!", ButtonType.OK));
					
				}
				
				catch (Exception e ) {
					
					Alerta a = new Alerta();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Erro ao salvar ou editar!!!", new ButtonType[] { ButtonType.OK }));
					
					System.out.println(e);
				}

			}

		}

	}

	public void excluirEndereco () {

		try {
			//-- capturar usuário selecionado --//
			Usuario usuario = tvLista.getSelectionModel().getSelectedItem(); 

			UsuarioDao usDao = new UsuarioDao();

			usDao.removerUsuario(usuario.getUsID());

			obsList.remove(usuario);

			modularBotoes();

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído com sucesso!!!", ButtonType.OK)); 

		}	catch (Exception e) {

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao escluir o cadastro!!!", ButtonType.OK)); 

		}
	}

	public void cancelarEndereco () {

		modularBotoes ();

		cbTipoPessoa.setValue(null);

		tfNome.setText("");
		tfCPFCNPJ.setText("");
		tfLogradouro.setText("");

		cbRA.setValue(null);

		tfCEP.setText("");
		tfCidade.setText("");

		cbUF.setValue(null);

		tfTelefone.setText("");
		tfCelular.setText("");
		tfEmail.setText("");

	}

	//-- botão pesquisar usuário --//
	public void pesquisarEndereco () {

		strPesquisa = tfPesquisar.getText();

		listarUsuarios (strPesquisa);

		modularBotoes ();

	}

	public void imprimirEnderecoEmpreendimento () {

		if (checkEnderecoEmpreendimento.isSelected()) {

			try{tfLogradouro.setText(endereco.getEndLogradouro());}catch (Exception e) {tfLogradouro.setText(null);};
			try{cbRA.setValue(endereco.getEndRAFK().getRaNome());}catch (Exception e) {cbRA.setValue(null);};
			try{tfCEP.setText(endereco.getEndCEP());}catch (Exception e) {tfCEP.setText(null);};
			try{tfCidade.setText(endereco.getEndCidade());}catch (Exception e) {tfCidade.setText(null);};
			try{cbUF.setValue(endereco.getEndUF());}catch (Exception e) {cbUF.setValue(null);};

		}

	}

	@FXML Pane pUsuario;

	Pane p1 = new Pane();
	BorderPane bp1 = new BorderPane();
	BorderPane bp2 = new BorderPane();
	ScrollPane sp = new ScrollPane();
	Pane pMapa = new Pane();

	Label lblDataAtualizacao = new Label();

	ControladorOutorga controladorOutorga;
	ControladorAtendimento controladorAtendimento;
	ControladorFiscalizacao controladorFiscalizacao;

	public TabUsuarioControlador (ControladorOutorga controladorOutorga) {
		this.controladorOutorga = controladorOutorga;

	}
	
	public TabUsuarioControlador (ControladorAtendimento controladorAtendimento) {
		this.controladorAtendimento = controladorAtendimento;

	}
	
	public TabUsuarioControlador (ControladorFiscalizacao controladorFiscalizacao) {
		this.controladorFiscalizacao = controladorFiscalizacao;

	}
	
	public void initialize(URL url, ResourceBundle rb) {

		bp1.minWidthProperty().bind(pUsuario.widthProperty());
		bp1.maxHeightProperty().bind(pUsuario.heightProperty().subtract(60));

		bp1.getStyleClass().add("border-pane");

		bp2.setPrefHeight(800.0D);
		bp2.minWidthProperty().bind(pUsuario.widthProperty());

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		sp.setContent(bp2);

		bp1.setCenter(sp);

		pUsuario.getChildren().add(bp1);

		p1.setMaxSize(980.0, 1000.0);
		p1.setMinSize(980.0, 1000.0);

		bp2.setTop(p1);
		BorderPane.setAlignment(p1, Pos.CENTER);

		inicializarComponentes ();
		acionarBotoes();

		lblDataAtualizacao.setPrefSize(247, 22);
		lblDataAtualizacao.setLayoutX(705);
		lblDataAtualizacao.setLayoutY(670);

		cbTipoPessoa.setValue("Física");
		cbTipoPessoa.setItems(olTipoPessoa);

		cbRA.setItems(ListasComboBox.obsListRA);
	    cbRA.setValue("Plano Piloto");

		cbUF.setItems(olDF);
		cbUF.setValue("DF");

		tcNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usNome"));
		tcCPFCNPJ.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usCPFCNPJ"));
		tcEndereco.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usLogadouro"));

		tcNome.setPrefWidth(410);
		tcCPFCNPJ.setPrefWidth(180);
		tcEndereco.setPrefWidth(320);

		tvLista.setPrefSize(930, 185);
		tvLista.setLayoutX(25);
		tvLista.setLayoutY(475);


		tvLista.getColumns().add(tcNome);
		tvLista.getColumns().add(tcCPFCNPJ);
		tvLista.getColumns().add(tcEndereco);

		tvLista.setItems(obsList);

		p1.getChildren().addAll(
				tvLista, 
				lblDataAtualizacao
				);

		modularBotoes ();

		selecionarUsuario();
		// selecionarInterferencia ();

	}

	Pane pEndereco;
	Label lblEndereco = new Label();
	Button btnEndereco;

	ArrayList<Node> listaComonentesEndereco = new ArrayList<Node>();

	Pane pPersistencia;
	Button btnNovo;
	Button btnSalvar;
	Button btnEditar;
	Button btnExcluir;
	Button btnCancelar;
	Button btnPesquisar;
	TextField tfPesquisar;

	ArrayList<Node> listNodesPersistencia= new ArrayList<Node>();

	Pane pDadosUsuario;
	ComboBox<String> cbTipoPessoa;
	TextField tfNome;
	TextField tfCPFCNPJ;
	CheckBox checkEnderecoEmpreendimento;
	TextField tfLogradouro;
	TextField tfCEP;
	ComboBox<String> cbRA;
	TextField tfCidade;
	ComboBox<String> cbUF;
	TextField tfTelefone;
	TextField tfCelular;
	TextField tfEmail;
	
	TextField tfRepresentante;
	TextField tfRepreTelefone;

	ArrayList<Node> listaComponentesUsuario = new ArrayList<Node>();												


	Pane pInterferencia;
	ComboBox<Endereco> cbEndereco;
	ComboBox<Interferencia> cbInterferencia;
	Button btnRequerimento;

	ArrayList<Node> listaComonentesInterferencia = new ArrayList<Node>();

	Componentes com;
	Double prefSizeWHeLayXY [][];
	
	Pane pNavegadorExterno;
	Button btnBrowser;
	Button btnBrowserEditor;
	ObservableList<Integer> obsListIframe = FXCollections
			.observableArrayList(0,1,2);
	ComboBox<Integer> cbIframe; 
	Button btnInserirHTML;
	Button btnWebDriver;
	
	ArrayList<Node> listaDeComponentes;
	
	NavegadorExterno navExt;
	
	
	/* 
	 * para pesquisar no banco de dados access
	 */
	List<BancoAccess> docList = new ArrayList<>();
	ContextMenu contextMenu  = new ContextMenu();
	
	// formatador para cpf e cnpj
	FormatadorCPFCNPJ ccFormato = new FormatadorCPFCNPJ();
	// string auxiliar na formatacao de cpf e cnpj
	String strCPFCNPJ = null;

	public void inicializarComponentes () {


		listaComonentesEndereco.add(pEndereco = new Pane());
		listaComonentesEndereco.add(new Label("ENDERECO:"));
		listaComonentesEndereco.add(lblEndereco = new Label());
		listaComonentesEndereco.add(btnEndereco = new  Button("<<<"));

		prefSizeWHeLayXY = new Double [][]  { 

			{950.0,60.0,15.0,14.0},
			{95.0,30.0,27.0,15.0},
			{728.0,30.0,121.0,15.0},
			{65.0,25.0,859.0,17.0},

		}; 

		com = new Componentes();
		com.popularTela(listaComonentesEndereco, prefSizeWHeLayXY, p1);

		
		listaComponentesUsuario.add(pDadosUsuario = new Pane());
		
		listaComponentesUsuario.add(new Label("TIPO:"));
		listaComponentesUsuario.add(cbTipoPessoa = new ComboBox<String>());
		
		listaComponentesUsuario.add(new Label("NOME/RAZÃO SOCIAL:"));
		listaComponentesUsuario.add(tfNome = new TextField());
		
		listaComponentesUsuario.add(new Label("CPF/CNPJ: "));
		listaComponentesUsuario.add(tfCPFCNPJ = new TextField());
		
		listaComponentesUsuario.add(checkEnderecoEmpreendimento = new CheckBox("importar endereço do empreendimento. "));
		
		listaComponentesUsuario.add(new Label("ENDERECO:"));
		listaComponentesUsuario.add(tfLogradouro = new TextField());
		listaComponentesUsuario.add(new Label("RA: "));
		listaComponentesUsuario.add(cbRA = new ComboBox<String>());
		listaComponentesUsuario.add(new Label("CEP: "));
		listaComponentesUsuario.add(tfCEP = new TextField());
		listaComponentesUsuario.add(new Label("CIDADE:"));
		listaComponentesUsuario.add(tfCidade = new TextField());
		listaComponentesUsuario.add(new Label("UF: "));
		listaComponentesUsuario.add(cbUF = new ComboBox<String>());
		listaComponentesUsuario.add(new Label("TELEFONE:"));
		listaComponentesUsuario.add(tfTelefone = new TextField());
		listaComponentesUsuario.add(new Label("CELULAR:"));
		listaComponentesUsuario.add(tfCelular = new TextField());
		listaComponentesUsuario.add(new Label("EMAIL:"));
		listaComponentesUsuario.add(tfEmail = new TextField());
		
		listaComponentesUsuario.add(new Label("REPRESENTANTE:"));
		listaComponentesUsuario.add(tfRepresentante = new TextField());
		
		listaComponentesUsuario.add(new Label("TELEFONE:"));
		listaComponentesUsuario.add(tfRepreTelefone = new TextField());

		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,304.0,25.0,85.0},
			{110.0,30.0,10.0,5.0},
			{110.0,30.0,10.0,35.0},
			{563.0,30.0,132.0,5.0},
			{563.0,30.0,130.0,35.0},
			{216.0,30.0,705.0,6.0},
			{216.0,30.0,705.0,35.0},
			{370.0,30.0,10.0,66.0},
			{370.0,30.0,10.0,95.0},
			{447.0,30.0,10.0,125.0},
			{150.0,30.0,466.0,95.0},
			{150.0,30.0,466.0,125.0},
			{85.0,30.0,626.0,95.0},
			{85.0,30.0,626.0,125.0},
			{110.0,30.0,721.0,95.0},
			{110.0,30.0,721.0,125.0},
			{75.0,30.0,841.0,95.0},
			{75.0,30.0,845.0,125.0},
			{170.0,30.0,10.0,155.0},
			{170.0,30.0,10.0,185.0},
			{170.0,30.0,190.0,155.0},
			{170.0,30.0,190.0,185.0},
			{548.0,30.0,370.0,155.0},
			{548.0,30.0,371.0,185.0},
			{728.0,30.0,10.0,215.0},
			{728.0,30.0,10.0,245.0},
			{170.0,30.0,747.0,215.0},
			{170.0,30.0,749.0,245.0},

		}; 

		com = new Componentes();
		com.popularTela(listaComponentesUsuario, prefSizeWHeLayXY, p1);
		

		 tfCPFCNPJ.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
					
					if (newValue.intValue() > oldValue.intValue()) {
						
						strCPFCNPJ = tfCPFCNPJ.getText().replaceAll("\\D","");
			
						if (cbTipoPessoa.getValue().equals("Física")) {
						
						//  MASCARA CPF
						
						if ( strCPFCNPJ.length() == 11 ) {
						
							try {
							
								tfCPFCNPJ.setText(ccFormato.formatCnpj(cbTipoPessoa.getValue(), tfCPFCNPJ.getText())
												
							);
								
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
							
							
						} // fim if ( (Integer )newValue == 11 ) 
						
						// LIMITE CPF
						if ( (Integer ) newValue > 14 ) {
							
							tfCPFCNPJ.setText(tfCPFCNPJ.getText().substring(0, 14));
							
						}
						
						} // fim if pessoa física
						
						if (cbTipoPessoa.getValue().equals("Jurídica")) {
							
							//  MASCARA CPF
							if ( strCPFCNPJ.length() == 14 ) {
							
								try {
									tfCPFCNPJ.setText(ccFormato.formatCnpj(cbTipoPessoa.getValue(), tfCPFCNPJ.getText())
													
								);
									
								} catch (ParseException e) {
								
									e.printStackTrace();
								}
								
								
							} // fim if ( (Integer )newValue == 11 ) 
							
							// LIMITE CPF
							if ( (Integer ) newValue > 18 ) {
								
								tfCPFCNPJ.setText(tfCPFCNPJ.getText().substring(0, 18));
								
							}
							
							} // fim if pessoa física
				
						
					} // fim if newValue > 
				}
			}); //fim listerner textfield
		
		BuscadorBancos bd = new BuscadorBancos(tfNome, contextMenu);
		bd.buscar();
		
		listNodesPersistencia.add(pPersistencia = new Pane());
		listNodesPersistencia.add(btnNovo = new Button("NOVO"));
		listNodesPersistencia.add(btnSalvar = new Button("SALVAR"));
		listNodesPersistencia.add(btnEditar = new Button("EDITAR"));
		listNodesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
		listNodesPersistencia.add(btnCancelar = new Button("CANCELAR"));

		listNodesPersistencia.add(tfPesquisar = new TextField());
		listNodesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,60.0,25.0,402.0},
			{95.0,25.0,18.0,18.0},
			{95.0,25.0,123.0,18.0},
			{95.0,25.0,228.0,18.0},
			{95.0,25.0,333.0,18.0},
			{95.0,25.0,438.0,18.0},
			{265.0,25.0,543.0,18.0},
			{95.0,25.0,818.0,18.0},

		}; 

		com = new Componentes();
		com.popularTela(listNodesPersistencia, prefSizeWHeLayXY, p1);
		
		
		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});


		listaComonentesInterferencia.add(pInterferencia = new Pane());
		listaComonentesInterferencia.add(new Label("Endereço:"));
		listaComonentesInterferencia.add(cbEndereco = new ComboBox<Endereco>());
		listaComonentesInterferencia.add(new Label("Interferência:"));
		listaComonentesInterferencia.add(cbInterferencia = new ComboBox<Interferencia>());
		listaComonentesInterferencia.add(btnRequerimento = new  Button("GERAR REQUERIMENTO"));

		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,72.0,25.0,700.0},
			{350.0,30.0,18.0,0.0},
			{350.0,30.0,18.0,31.0},
			{350.0,30.0,378.0,0.0},
			{350.0,30.0,378.0,31.0},
			{175.0,25.0,738.0,34.0},

		}; 

		com = new Componentes();
		com.popularTela(listaComonentesInterferencia, prefSizeWHeLayXY, p1);

		cbEndereco.setItems(obsListEnderecoEmpreendimento);
		cbInterferencia.setItems(obsListInterferencia);
		
		/*
		listaDeComponentes = new ArrayList<Node>();
		
		listaDeComponentes.add(pNavegadorExterno = new Pane());
		
		listaDeComponentes.add(btnBrowser = new Button("Chrome"));
		listaDeComponentes.add(btnBrowserEditor = new Button("Atualizar"));
		listaDeComponentes.add(cbIframe = new ComboBox<Integer>(obsListIframe));
		listaDeComponentes.add(btnInserirHTML = new Button("Inserir"));
		listaDeComponentes.add(btnWebDriver = new Button("Driver"));
		
		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,70.0,25.0,772.0},
			{100.0,30.0,213.0,20.0},
			{100.0,30.0,348.0,20.0},
			{100.0,30.0,483.0,20.0},
			{100.0,30.0,618.0,20.0},
			{100.0,30.0,830.0,0.0},
		}; 
		
		//posicao do pane 25.0,772.0},
		// string html
		
		
		com = new Componentes();
		com.popularTela(listaDeComponentes, prefSizeWHeLayXY, p1);

		cbIframe.setValue(2);
*/
		
		navExt = new NavegadorExterno(p1);
		
		navExt.inicializarNavegadorExterno(25.0, 772.0);
		
	}
	
	// Navegador Externo
	WebDriver driver;
	// String com o link do webdriver
	String strWebDriver;
	// Classe de salvamento das preferencias do usuario,como a strWebDriver
    Registro r;
    

	WebView webTermo;
	WebEngine engTermo;
	
	List<Object[][]> listaMalaDireta = new ArrayList<>();
	
	public void gerarDocumento (List<Object[][]> listaMalaDireta) {
		
		
		// buscar tipo de documento 
		HTMLEditor htmlEditor = new HTMLEditor();

		ModelosDao modDao = new ModelosDao();

		List<ModelosHTML> listaModelosHTMLRequerimentos = null;

		/*
		 * Escolher o modelo html - se o modelo de requerimento superficial ou subterraneo, porem desta forma se eu mudar o medelo ou adicionar um diferente
		 * nao vou conseguir encontrar
		 */
		if ( 	((Interferencia)listaMalaDireta.get(0)[0][2]).getInterTipoInterferenciaFK().getTipoInterDescricao().equals("Subterrânea")	) {

			listaModelosHTMLRequerimentos = modDao.listarModelo("Requerimento de Outorga Subterrânea");
		} 

		if (	((Interferencia)listaMalaDireta.get(0)[0][2]).getInterTipoInterferenciaFK().getTipoInterDescricao().equals("Superficial")	) {

			listaModelosHTMLRequerimentos = modDao.listarModelo("Requerimento de Outorga Superficial");
		} 

		MalaDiretaUnica mlu = new MalaDiretaUnica(listaMalaDireta, listaModelosHTMLRequerimentos.get(0).getModConteudo());
		
		strHTML = mlu.criarDocumento();
		
		/*
		 * Enviar o html preenchido para o navegador externo (webdrive do chrome)
		 */
		if (!(navExt == null)) {
			navExt.setarStringHTML(strHTML);
		}
		

		try { ControladorNavegacao.conNav.setHTML(strHTML); } 
		
			catch (Exception e) {

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Inicialize o navegador SEI !!!", ButtonType.OK));
		}


		htmlEditor.setHtmlText(strHTML);

		// adicionar um novo botao ao htmlEditor //
		final String TOP_TOOLBAR = ".top-toolbar";

		ToolBar tooImprimir = new ToolBar();
		Button btnImprimir = new Button("Imprimir");

		Node nod;

		nod = htmlEditor.lookup(TOP_TOOLBAR);
		if (nod instanceof ToolBar) {
			tooImprimir = (ToolBar) nod;
		}

		tooImprimir.getItems().add(btnImprimir);
		tooImprimir.getItems().add(new Separator(Orientation.VERTICAL));

		// imprimir o requerimento //
		btnImprimir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				ChoiceDialog<Printer> dialog = new ChoiceDialog<Printer>(Printer.getDefaultPrinter(), Printer.getAllPrinters());
				dialog.setHeaderText("Escolha a impressora!");
				dialog.setContentText("Impressoras disponíveis...");
				dialog.setTitle("Printer Choice");
				Optional<Printer> opt = dialog.showAndWait();
				if (opt.isPresent()) {
					//Printer printer = opt.get();

					PrinterJob job = PrinterJob.createPrinterJob();
					if (job != null) {
						boolean success = true;
						if (success) {
							htmlEditor.print(job);
							job.endJob();
						}
					}
				}



			}
		});

		Scene scene = new Scene(htmlEditor);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setWidth(1150);
		stage.setHeight(750);
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);

		stage.show();
		
		
		
	}

	public void gerarRequerimento (Usuario us, Interferencia inter) {

		// buscar tipo de documento 
		HTMLEditor htmlEditor = new HTMLEditor();

		ModelosDao modDao = new ModelosDao();

		List<ModelosHTML> listRequerimento = null;


		if (inter.getInterTipoInterferenciaFK().getTipoInterDescricao().equals("Subterrânea")) {

			listRequerimento = modDao.listarModelo("Requerimento de Outorga Subterrânea");
		} 

		if (inter.getInterTipoInterferenciaFK().getTipoInterDescricao().equals("Superficial")) {

			listRequerimento = modDao.listarModelo("Requerimento de Outorga Superficial");
		} 


		// editar tipo de documento com dados do usuario, interferencia etc
		MalaDireta ml = new MalaDireta(endereco, interferencia, usuario);

		ml.setHtmlRel(listRequerimento.get(0).getModConteudo());

		strHTML = ml.criarDocumento();
		
		/*
		 * Enviar o html preenchido para o navegador externo (webdrive do chrome)
		 */
		if (!(navExt == null)) {
			navExt.setarStringHTML(strHTML);
		}
		

		try { ControladorNavegacao.conNav.setHTML(strHTML); } 
		
			catch (Exception e) {

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Inicialize o navegador SEI !!!", ButtonType.OK));
		}


		htmlEditor.setHtmlText(strHTML);

		// adicionar um novo botao ao htmlEditor //
		final String TOP_TOOLBAR = ".top-toolbar";

		ToolBar tooImprimir = new ToolBar();
		Button btnImprimir = new Button("Imprimir");

		Node nod;

		nod = htmlEditor.lookup(TOP_TOOLBAR);
		if (nod instanceof ToolBar) {
			tooImprimir = (ToolBar) nod;
		}

		tooImprimir.getItems().add(btnImprimir);
		tooImprimir.getItems().add(new Separator(Orientation.VERTICAL));

		// imprimir o requerimento //
		btnImprimir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				ChoiceDialog<Printer> dialog = new ChoiceDialog<Printer>(Printer.getDefaultPrinter(), Printer.getAllPrinters());
				dialog.setHeaderText("Escolha a impressora!");
				dialog.setContentText("Impressoras disponíveis...");
				dialog.setTitle("Printer Choice");
				Optional<Printer> opt = dialog.showAndWait();
				if (opt.isPresent()) {
					//Printer printer = opt.get();

					PrinterJob job = PrinterJob.createPrinterJob();
					if (job != null) {
						boolean success = true;
						if (success) {
							htmlEditor.print(job);
							job.endJob();
						}
					}
				}



			}
		});

		Scene scene = new Scene(htmlEditor);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setWidth(1150);
		stage.setHeight(750);
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);

		stage.show();


		//WebView webRequerimento = new WebView();
		//WebEngine engReq = webRequerimento.getEngine();

		//engReq.load(getClass().getResource("/html/termoNotificacao.html").toExternalForm()); 

		/*
      		//engReq.load(getClass().getResource(listRequerimento.get(0).getModConteudo()); 
      		engReq.loadContent(listRequerimento.get(0).getModConteudo());

      		engReq.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){ 

                   public void changed(final ObservableValue<? extends Worker.State> observableValue, 

                                       final Worker.State oldState, 
                                       final Worker.State newState) 

                   { 
                   	if (newState == Worker.State.SUCCEEDED){  
                   		String strRequerimento = (String) engReq.executeScript("document.documentElement.outerHTML"); 

                   		System.out.println("string \n " + strRequerimento);

                   		editor.setHtmlText(strRequerimento);

                   		Scene scene = new Scene(editor);

              			Stage stage = new Stage(StageStyle.UTILITY);
              			stage.setWidth(1150);
              			stage.setHeight(750);
              	        stage.setScene(scene);
              	        stage.setMaximized(false);
              	        stage.setResizable(false);

              	        stage.show();

                   	} 

                  } 
      		}); 

		 */


	}

	public void acionarBotoes () {

		btnNovo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				habilitarEndereco();
			}
		});

		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				salvarEndereco();
			}
		});

		btnEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				editarEndereco();
			}
		});

		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cancelarEndereco();
			}
		});

		btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pesquisarEndereco();
			}
		});

		checkEnderecoEmpreendimento.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean old_val, Boolean new_val) {
				if(new_val == true) {
					imprimirEnderecoEmpreendimento();
				}
			}
		});

		btnRequerimento.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//gerarRequerimento (usuario, interferencia);
				
				Object[][] dados = new Object [][] {
					{
					null,
					cbEndereco.getSelectionModel().getSelectedItem(),
					cbInterferencia.getSelectionModel().getSelectedItem(),
					usuario,
					
					},
				} ;
		
				listaMalaDireta.clear();
				listaMalaDireta.add(dados);
				
				gerarDocumento (listaMalaDireta);
			
				//  ((Usuario)listaMalaDireta.get(0)[0][3]).getUsNome() 
				
			}
		});

		btnExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirEndereco ();
			}
		});
		
		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

	            inicializarTelaEndereco();
	            telaEnderecoControlador.setObjetoDeEdicao(usuario);
			}
		});
		

		cbEndereco.setConverter(new StringConverter<Endereco>() {

			public String toString(Endereco e) {
				return e.getEndLogradouro() + ", RA: " + e.getEndRAFK().getRaNome();
			}

			public Endereco fromString(String string) {
				return null;
			}
		});

		cbEndereco.valueProperty().addListener(new ChangeListener<Endereco>() {
			
			@Override 
			public void changed(ObservableValue<? extends Endereco> ov, Endereco oldValue, Endereco newValue) {  

				obsListInterferencia.clear();
				
				// para nao repetir valores
				
				if (newValue != null) {
					obsListInterferencia.addAll(newValue.getInterferencias().stream().distinct().collect(Collectors.toList()));
				}
				

				endereco = newValue;

			}    
		});

		cbInterferencia.setConverter(new StringConverter<Interferencia>() {
			public String toString(Interferencia i) {
				return i.getInterTipoInterferenciaFK().getTipoInterDescricao() + " --- " + i.getInterTipoOutorgaFK().getTipoOutorgaDescricao();
			}

			public Interferencia fromString(String string) {
				return null;
			}
		});


		cbInterferencia.valueProperty().addListener(new ChangeListener<Interferencia>() {
			@Override 
			public void changed(ObservableValue<? extends Interferencia> ov, Interferencia oldValue, Interferencia newValue) {  
				if (newValue != null)
					interferencia = newValue;
				
				
				
			}    
		});
		
		/*
		btnBrowser.setOnAction((event) -> {
	        	
	        	if (strWebDriver == null) {
	        		
	        	System.out.println(strWebDriver == null);
	        		
	        		r = new Registro();
		    		List<String> strList = null;
		    		
		    		try {
		    			
						strList = r.lerRegistro();
						
						for (String s : strList) {
							System.out.println("strings " + s);
						}
						
					} catch (IOException e2) {
					
						e2.printStackTrace();
					}
		    		
		    		if(strList.size() != 0) {
		    			strWebDriver = strList.get(0);
		    		}
		    		
		    		System.out.println(strWebDriver);
	        		
	        	} // fim if strWebDriver == null
	        	
	        	
	        	
	        	System.setProperty("webdriver.chrome.driver", strWebDriver);
	        	
	        	ChromeOptions options = new ChromeOptions();
	        	
	            options.addArguments("--disable-infobars");
	            options.addArguments("start-maximized");

	        	driver = new ChromeDriver(options);

	            driver.navigate().to("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
	           
	            
	            driver.findElement(By.id("selOrgao")).sendKeys("a");
	            
	            driver.findElement(By.id("txtUsuario")).sendKeys("fabricio.barrozo");;
	            driver.findElement(By.id("pwdSenha")).sendKeys("polygonnewPolygon");
	           
	            driver.findElement(By.id("sbmLogin")).click();
	            
	        	
		}); // btnBrowser
		
		
		// ação do botão para receber o excel
		btnWebDriver.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				r = new Registro();
				List<String> strList = null;

				try {
					strList = r.lerRegistro();
				} catch (IOException e2) {

					e2.printStackTrace();
				}

				if(strList.size() != 0) {
					strWebDriver = strList.get(1);
				}

				if (strWebDriver == null) {

					// para escolher o arquivo  no computador
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Selecione o Web Driver");
					fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXE" , "*.exe"));        
					File file = fileChooser.showOpenDialog(null);

					strWebDriver = file.toString();

					r = new Registro();

					try {
						r.salvarRegistro(strWebDriver);

					} catch (URISyntaxException e1) {

						e1.printStackTrace();
					} catch (IOException e1) {

						e1.printStackTrace();
					}


				}
			}


		}); // fim btnWebBrowser
		
	
        btnInserirHTML.setOnAction((event) -> {
        	
        	Set<String> s1 = driver.getWindowHandles();

        	List<String> strList = new ArrayList<>();

        	for (String s : s1) {
        		strList.add(s);
        	}

        	driver.switchTo().window(strList.get(1));

        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	
        	System.out.println("cbiframe value " + cbIframe.getValue());
        	
        	System.out.println(strHTML);
        	
        	// retirar os as haspas "
        	strHTML = strHTML.replace("\"", "");
        	// trocar a haspa simples ' por haspas duplas "
        	strHTML = strHTML.replace("'", "\"");

        	js.executeScript("document.getElementsByTagName('iframe')['"+cbIframe.getValue()+"'].contentDocument.body.innerHTML = '"+strHTML+"';");
            
            
        });
*/

	}
	
	String strHTML;

	private void modularBotoes () {

		cbTipoPessoa.setDisable(true);
		tfNome.setDisable(true); 
		tfCPFCNPJ.setDisable(true);

		checkEnderecoEmpreendimento.setDisable(true);

		tfLogradouro.setDisable(true);

		cbRA.setDisable(true); 

		tfCEP.setDisable(true);
		tfCidade.setDisable(true);

		cbUF.setDisable(true);

		tfTelefone.setDisable(true);
		tfCelular.setDisable(true);
		tfEmail.setDisable(true);
		
		tfRepresentante.setDisable(true);
		tfRepreTelefone.setDisable(true);


		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(false);

	}

	//-- método listar usuários --//
	public void listarUsuarios (String strPesquisa) {

		UsuarioDao usDao = new UsuarioDao();
		List<Usuario> usuarioList = usDao.listarUsuario(strPesquisa);
		obsList = FXCollections.observableArrayList();


		if (!obsList.isEmpty()) {
			obsList.clear();
		}

		for (Usuario usuario : usuarioList) {


			usuario.getUsID();
			usuario.getUsTipo();
			usuario.getUsNome();
			usuario.getUsCPFCNPJ();
			usuario.getUsLogadouro();
			usuario.getUsRA();
			usuario.getUsCidade();
			usuario.getUsEstado();
			usuario.getUsCEP();
			usuario.getUsTelefone();
			usuario.getUsCelular();
			usuario.getUsEmail();

			usuario.getUsDataAtualizacao();
			// capturar os enderecos  relacionados
			usuario.getEnderecos();

			obsList.add(usuario);

		}

		tvLista.setItems(obsList);
	}

	public void selecionarUsuario () {

		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {

				Usuario us = (Usuario) newValue;

				if (us == null) {

					cbTipoPessoa.setValue(null);

					tfNome.setText(null);
					tfCPFCNPJ.setText(null);
					tfLogradouro.setText(null);

					cbRA.setValue(null);

					tfCEP.setText(null);
					tfCidade.setText(null);

					cbUF.setValue(null);

					tfTelefone.setText(null);
					tfCelular.setText(null);
					tfEmail.setText(null);
					
					tfRepresentante.setText(null);
					tfRepreTelefone.setText(null);

					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);
					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);

					System.out.println(" usuário null");

				} else {

					// -- preencher os campos -- //
					cbTipoPessoa.setValue(us.getUsTipo());

					tfNome.setText(us.getUsNome());
					tfCPFCNPJ.setText(us.getUsCPFCNPJ());
					tfLogradouro.setText(us.getUsLogadouro());

					cbRA.setValue(us.getUsRA());

					tfCEP.setText(us.getUsCEP());
					tfCidade.setText(us.getUsCidade());

					cbUF.setValue(us.getUsEstado());

					tfTelefone.setText(us.getUsTelefone());
					tfCelular.setText(us.getUsCelular());
					tfEmail.setText(us.getUsEmail());
					
					tfRepresentante.setText(us.getUsRepresentante());
					tfRepreTelefone.setText(us.getUsRepresentanteTelefone());


					// mostrar data de atualizacao //
					FormatoData d = new FormatoData();
					try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(us.getUsDataAtualizacao()));
					lblDataAtualizacao.setTextFill(Color.BLACK);
					}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
					lblDataAtualizacao.setTextFill(Color.RED);}

					usuario = us;

					//enditarEnderecoControlador.setObjetoDeEdicao(us);

					obsListEnderecoEmpreendimento.clear();

					Set<Endereco> setEnderecos = us.getEnderecos();

					if (! us.getEnderecos().isEmpty()) {

						for(Endereco e: setEnderecos) {
					
							obsListEnderecoEmpreendimento.add(e);
							/*
							 * para atualizar o endereço com qualquer um dos  relacionados
							 * 		o objeto endereco não pode ficar vazio
							 */
							endereco = e;

						}

					} else {
						endereco = null;
					}

					obsListInterferencia.clear();

					setEndereco (endereco);

					// copiar cpf do usuario ao selecionar //
					Clipboard clip = Clipboard.getSystemClipboard();
					ClipboardContent conteudo = new ClipboardContent();
					conteudo.putString(us.getUsCPFCNPJ());
					clip.setContent(conteudo);

					// -- habilitar e desabilitar botões -- //
					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);
					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);

				}
			}
		});
	}

	TranslateTransition ttDireita;
	TranslateTransition ttEsquerda;
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
		
			ttEsquerda = new TranslateTransition(new Duration(350.0), pTelaEndereco);
			ttEsquerda.setToX(15.0);
				  
			ttDireita = new TranslateTransition(new Duration(350.0), pTelaEndereco);
			ttDireita.setToX(1300.0);
		  
			pTelaEndereco.setTranslateX(1300.0);
		  
		}
	    
	    ttEsquerda.play();
	    
	  }
	
	public void movimentarTelaEndereco ()	{
		
		if (ttDireita != null)
			ttDireita.play();

	  }
	
	public void abrirPaneEditarEndereco () {

		Pane pEndereco = new Pane();
		//editarEnderecoControlador = new EditarEnderecoControlador();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/EditarEndereco.fxml"));
		loader.setRoot(pEndereco);
		//loader.setController(editarEnderecoControlador);

		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

		Scene scene = new Scene(pEndereco);
		Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
		stage.setWidth(964);
		stage.setHeight(600);
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.setAlwaysOnTop(true); 
		stage.show();
	}

}
