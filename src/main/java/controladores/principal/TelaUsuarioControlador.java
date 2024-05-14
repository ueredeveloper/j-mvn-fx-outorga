package controladores.principal;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.exception.ConstraintViolationException;

import dao.UsuarioDao;
import entidades.BancoAccess;
import entidades.Documento;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.Usuario;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
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
import principal.ListasComboBox;

public class TelaUsuarioControlador implements Initializable {
	
	int intControlador;
	
	Usuario usuario = new Usuario();
	
	TabParecerControlador tabParCon;
	
	
	public TelaUsuarioControlador (TabParecerControlador tabParCon) {
		this.tabParCon = tabParCon;
	}
	
	Documento documento;
	
	public void setDocumento (Documento documento) {
		
		this.documento = documento;
		
		if (documento.getDocNumeracao() != null) {
		
			lblDocumento.setText(
					documento.getDocTipo() + " Nº "
					+ documento.getDocNumeracao()
					+ ", SEI: " + documento.getDocSEI()
					+ ", PROCESSO: " + documento.getDocProcesso()
					);
		
			lblDocumento.setStyle("-fx-text-fill: #4A4A4A;"); 
		
		} else {
			
			lblDocumento.setText("Não há documento relacionado!");
			lblDocumento.setStyle("-fx-text-fill: #FF0000;");
		}
		
	}
	
	//-- TableView Endereço --//
	private TableView <Usuario> tvLista = new TableView<>();
	
	TableColumn<Usuario, String> tcNome = new TableColumn<>("Nome");
	TableColumn<Usuario, String> tcCPFCNPJ = new TableColumn<>("CPF/CNPJ");
	TableColumn<Usuario, String> tcEndereco = new TableColumn<>("Endereço de Correpondência");
	ObservableList<Usuario> obsList = FXCollections.observableArrayList();
	
	public static TelaUsuarioControlador telaUsCon;
	
	@FXML Pane pTelaUsuario;
	
	ObservableList<Endereco> obsListEnderecoEmpreendimento = FXCollections.observableArrayList();

	ObservableList<Interferencia> obsListInterferencia = FXCollections.observableArrayList();
	
	ObservableList<String> olTipoPessoa = FXCollections
		.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica


	ObservableList<String> olDF = FXCollections
		.observableArrayList("DF" , "GO", "Outro"); // box - seleção pessoa físcia ou jurídica


	/* 
	 * para pesquisar no banco de dados access
	 */
	List<BancoAccess> docList = new ArrayList<>();
	ContextMenu contextMenu  = new ContextMenu();

	   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		telaUsCon = this;
		
		System.out.println("tela usuario inicializada");
		
		tcNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usNome"));
		tcCPFCNPJ.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usCPFCNPJ"));
		tcEndereco.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usLogadouro"));
		
		tcNome.setPrefWidth(340);
		tcCPFCNPJ.setPrefWidth(150);
		tcEndereco.setPrefWidth(330);
		
		tvLista.setPrefSize(930, 185);
		tvLista.setLayoutX(25);
		tvLista.setLayoutY(395);
		
		tvLista.setPrefSize(840.0, 160.0);
    	tvLista.setLayoutX(25.0);
    		tvLista.setLayoutY(395.0);
		
		tvLista.getColumns().add(tcNome);
		tvLista.getColumns().add(tcCPFCNPJ);
		tvLista.getColumns().add(tcEndereco);
		
		tvLista.setItems(obsList);
		
		lblDataAtualizacao.setPrefSize(247, 22);
	    lblDataAtualizacao.setLayoutX(615);
	    lblDataAtualizacao.setLayoutY(565);
	    
		inicializarComponentes ();
		acionarBotoes ();
		selecionarUsuario ();
		
		
	}
	
	Pane pDocumento;
	Label lblDocumento = new Label();
	Button btnDocumento;
			
		ArrayList<Node> componentesDocumento = new ArrayList<Node>();
				
	Pane pPersistencia;
		Button btnNovo;
		Button btnSalvar;
		Button btnEditar;
		Button btnExcluir;
		Button btnCancelar;
		Button btnPesquisar;
		TextField tfPesquisar;
					
		ArrayList<Node> componentesPersistencia = new ArrayList<Node>();
	

		Componentes com;
			Double prefSizeWHeLayXY [][];
			
		Pane pPrincipal = new Pane();
		
		Label lblDataAtualizacao= new Label();
		
		
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
	
		ArrayList<Node> componentesUsuario = new ArrayList<Node>();												
		
	public void inicializarComponentes () {
		
			pPrincipal.setStyle("-fx-background-color: white");
		    pPrincipal.setPrefSize(890.0, 1000.0);
		    pPrincipal.setLayoutX(60.0);
		    pPrincipal.setLayoutY(0.0);
		    
		    pPrincipal.getChildren().addAll(tvLista, lblDataAtualizacao);
		    
			componentesDocumento.add(pDocumento = new Pane());
			componentesDocumento.add(new Label("DOCUMENTO:"));
			componentesDocumento.add(lblDocumento = new Label());
			componentesDocumento.add(btnDocumento = new  Button(">>>"));
			
				prefSizeWHeLayXY = new Double [][]  { 
					
					{850.0,60.0,20.0,10.0},
					{90.0,30.0,15.0,15.0},
					{648.0,30.0,110.0,15.0},
					{65.0,25.0,770.0,19.0},
			    					    	
				}; 
			
				com = new Componentes();
			    com.popularTela(componentesDocumento, prefSizeWHeLayXY, pPrincipal);
				
				
				componentesUsuario.add(pDadosUsuario = new Pane());
				componentesUsuario.add(new Label("TIPO:"));
				componentesUsuario.add(cbTipoPessoa = new ComboBox<String>());
				componentesUsuario.add(new Label("NOME/RAZÃO SOCIAL:"));
				componentesUsuario.add(tfNome = new TextField());
				componentesUsuario.add(new Label("CPF/CNPJ: "));
				componentesUsuario.add(tfCPFCNPJ = new TextField());
				componentesUsuario.add(checkEnderecoEmpreendimento = new CheckBox("importar endereço do empreendimento. "));
				componentesUsuario.add(new Label("ENDERECO:"));
				componentesUsuario.add(tfLogradouro = new TextField());
				componentesUsuario.add(new Label("RA: "));
				componentesUsuario.add(cbRA = new ComboBox<String>());
				componentesUsuario.add(new Label("CEP: "));
				componentesUsuario.add(tfCEP = new TextField());
				componentesUsuario.add(new Label("CIDADE:"));
				componentesUsuario.add(tfCidade = new TextField());
				componentesUsuario.add(new Label("UF: "));
				componentesUsuario.add(cbUF = new ComboBox<String>());
				componentesUsuario.add(new Label("TELEFONE:"));
				componentesUsuario.add(tfTelefone = new TextField());
				componentesUsuario.add(new Label("CELULAR:"));
				componentesUsuario.add(tfCelular = new TextField());
				componentesUsuario.add(new Label("EMAIL:"));
				componentesUsuario.add(tfEmail = new TextField());
				
					prefSizeWHeLayXY = new Double [][]  { 
				    	
						{840.0,230.0,25.0,82.0},
						{110.0,30.0,10.0,5.0},
						{110.0,30.0,10.0,35.0},
						{510.0,30.0,131.0,5.0},
						{490.0,30.0,130.0,35.0},
						{195.0,30.0,632.0,5.0},
						{195.0,30.0,630.0,35.0},
						{370.0,30.0,10.0,66.0},
						{380.0,30.0,10.0,95.0},
						{370.0,30.0,10.0,125.0},
						{150.0,30.0,390.0,95.0},
						{150.0,30.0,390.0,125.0},
						{85.0,30.0,550.0,95.0},
						{85.0,30.0,550.0,125.0},
						{110.0,30.0,645.0,95.0},
						{110.0,30.0,645.0,125.0},
						{60.0,30.0,765.0,95.0},
						{60.0,30.0,765.0,125.0},
						{140.0,30.0,10.0,155.0},
						{140.0,30.0,9.0,185.0},
						{140.0,30.0,160.0,155.0},
						{140.0,30.0,160.0,185.0},
						{535.0,30.0,310.0,155.0},
						{515.0,30.0,310.0,185.0},
										    	
					}; 
				    	
					com = new Componentes();
				    com.popularTela(componentesUsuario, prefSizeWHeLayXY, pPrincipal);		
				
		
	    componentesPersistencia.add(pPersistencia = new Pane());
	    componentesPersistencia.add(btnNovo = new Button("NOVO"));
	    componentesPersistencia.add(btnSalvar = new Button("SALVAR"));
	    componentesPersistencia.add(btnEditar = new Button("EDITAR"));
	    componentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
	    componentesPersistencia.add(btnCancelar = new Button("CANCELAR"));

	    componentesPersistencia.add(tfPesquisar = new TextField());

	    componentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		    prefSizeWHeLayXY = new Double[][] { 
		    	
		    	{840.0,60.0,25.0,322.0},
		    	{95.0,25.0,10.0,18.0},
		    	{95.0,25.0,115.0,18.0},
		    	{95.0,25.0,220.0,18.0},
		    	{95.0,25.0,325.0,18.0},
		    	{95.0,25.0,430.0,18.0},
		    	{190.0,25.0,535.0,18.0},
		    	{95.0,25.0,735.0,18.0},
		    	
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
    
			    
	pTelaUsuario.getChildren().addAll(pPrincipal);
	pTelaUsuario.setStyle("-fx-background-color: rgba(223,226,227, 0.7);");
	
	
	cbTipoPessoa.setValue("Física");
	cbTipoPessoa.setItems(olTipoPessoa);
	
    cbRA.setItems(ListasComboBox.obsListRA);
    cbRA.setValue("Plano Piloto");


	cbUF.setValue("DF");
	cbUF.setItems(olDF);
	
	
	btnDocumento.setOnAction(new EventHandler<ActionEvent>() {
	    	
	        @Override public void handle(ActionEvent e) {
	     
	        	tabParCon.movimentarTelaUsuario();
	        	
	        }
	    });
	
	   
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
						
						btnNovo.setDisable(true);
						btnSalvar.setDisable(true);
						btnEditar.setDisable(false);
						btnExcluir.setDisable(false);
						btnCancelar.setDisable(false);
						
						System.out.println(" selecionar usuario - usuário null");
						
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
		
						
						// mostrar data de atualizacao //
						FormatoData d = new FormatoData();
						try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(us.getUsDataAtualizacao()));
								lblDataAtualizacao.setTextFill(Color.BLACK);
						}catch (Exception e) {lblDataAtualizacao.setText("Não há data de atualização!");
								lblDataAtualizacao.setTextFill(Color.RED);}
						
						usuario = us;
						
						//enditarEnderecoControlador.setObjetoDeEdicao(us);
					
						
						/*
						obsListEnderecoEmpreendimento.clear();
						
						Set<Endereco> setEnderecos = us.getEnderecos();
						
						if (! us.getEnderecos().isEmpty()) {
							
							for(Endereco e: setEnderecos) {
								System.out.println("set<Endereco> " + e.getEndLogradouro());
								
								obsListEnderecoEmpreendimento.add(e);
								/*
								 * para atualizar o endereço com qualquer um dos  relacionados
								 * 		o objeto endereco não pode ficar vazio
								 */
						
						/*
								endereco = e;
								
							}
							
						} else {
							endereco = null;
						}
						
						obsListInterferencia.clear();
						
						setEndereco (endereco);
						*/
						
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
		
	public void habilitarNovoUsuario () {
			
			cbTipoPessoa.setValue(null);
			
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
			
			btnSalvar.setDisable(false);
			btnEditar.setDisable(true);
			btnExcluir.setDisable(true);
			btnEditar.setDisable(true);
			
			
		}
		
	public void salvarUsuario () {
			
			if (documento == null) {
				
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
								us.setUsCPFCNPJ(tfCPFCNPJ.getText()); 
								us.setUsLogadouro(tfLogradouro.getText());
								us.setUsRA(cbRA.getValue());
								us.setUsCidade(tfCidade.getText());
								us.setUsEstado(cbUF.getValue());
								us.setUsCEP(tfCEP.getText());
								us.setUsTelefone(tfTelefone.getText());
								us.setUsCelular(tfCelular.getText());
								us.setUsEmail(tfEmail.getText());
								
								us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
			
							// chama instancia endereco //	
							//Endereco end = new Endereco();
							
								// iguala endereco ao selecionado pelo usuario
								//end = endereco;
								// neste endereco seta o usuario
								//end.setEndUsuarioFK(us);
								// na lista de endereco que  pertence ao usuario adiciona o endereco em questao
								//us.getEnderecos().add(end);
								
								// aquii
								/*
								for(Interferencia i:  end.getInterferencias()) {
									System.out.println(" interferencias - btn salvar " + i.getInterDDLatitude());
								}
								*/
							
							us.setUsDocumentoFK(documento);
								
							UsuarioDao  usDao = new UsuarioDao();
							
								usDao.salvarUsuario(us);
								usDao.mergeUsuario(us);
							
							obsList.add(us);
							
							modularBotoes();
							
							//Alerta //
							Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.INFORMATION, "Informe: Cadastro salvo com sucesso!!!", ButtonType.OK));
							
							}

							catch (ConstraintViolationException e ) {
							
								Alerta a = new Alerta();
								a.alertar(new Alert(Alert.AlertType.INFORMATION, "Número CPF ou CNPJ duplicado!!!", new ButtonType[] { ButtonType.OK }));
							}
							
							catch (Exception e ) {
								
								Alerta a = new Alerta();
								a.alertar(new Alert(Alert.AlertType.INFORMATION, "Erro desconhecido!!!", new ButtonType[] { ButtonType.OK }));
								
								System.out.println(e);
							}
						
					}
			}
				
		}
		
	public void editarUsuario () {
			
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
		
				Usuario us = tvLista.getSelectionModel().getSelectedItem(); 
				
					try {
				
						// -- preencher os campos -- //
						us.setUsTipo(cbTipoPessoa.getValue()); 
						us.setUsNome(tfNome.getText());
						us.setUsCPFCNPJ(tfCPFCNPJ.getText());
						us.setUsLogadouro(tfLogradouro.getText()); 
						
						us.setUsRA(cbRA.getValue()); 
						
						us.setUsCEP(tfCEP.getText()); 
						us.setUsCidade(tfCidade.getText()); 
						
						us.setUsEstado(cbUF.getValue()); 
						
						us.setUsTelefone(tfTelefone.getText());
						us.setUsCelular(tfCelular.getText());
						us.setUsEmail(tfEmail.getText());
						us.setUsDataAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
						
					
						/*
						Endereco end = new Endereco();
						// captura um endereco relacionado
						end = endereco;
						// adiciona neste endereco o id usuario selecionado
						end.setEndUsuarioFK(us);
						// adiciona este endereco no setEnderecos do usuario
						us.getEnderecos().add(end);
						*/
						
						if (!(documento.getDocID() == 0)) {
							usuario.setUsDocumentoFK(documento);
						}
					
						
						/*
						// para não dar repeticao de objetos //
						for (int i = 0 ; i < us.getEnderecos().size(); i++) {
							if (us.getEnderecos().hashCode(i) == (end.getEndID())) {
								us.getEnderecos().remove(us.getEnderecos().hashCode(i));
							}
						}
						*/
						
						UsuarioDao usDao = new UsuarioDao();
						
						usDao.mergeUsuario(us);
						
						obsList.remove(us);
						obsList.add(us);
						
						modularBotoes();
						
						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro editado com sucesso!!!", ButtonType.OK));
				
					}
					
					catch (ConstraintViolationException e ) {
				
						Alerta a = new Alerta();
						a.alertar(new Alert(Alert.AlertType.INFORMATION, "Número CPF ou CNPJ duplicado!!!", new ButtonType[] { ButtonType.OK }));
					}
					
					catch (Exception e ) {
						
						Alerta a = new Alerta();
						a.alertar(new Alert(Alert.AlertType.INFORMATION, "Erro desconhecido!!!", new ButtonType[] { ButtonType.OK }));
						
						System.out.println(e);
					}
				}
				
			}
			
			
		}
		
	public void excluirUsuario () {
		
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
	
	public void cancelarUsuario () {
		
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
	
	String strPesquisa = "";
	//-- botão pesquisar usuário --//
	public void pesquisarUsuario () {
		
		strPesquisa = tfPesquisar.getText();
		
		listarUsuarios (strPesquisa);
		
		modularBotoes();
		
	}
	
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
		
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(false);
		
	}
	
	public void acionarBotoes () {

		btnNovo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				habilitarNovoUsuario();
			}
		});

		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				salvarUsuario();
			}
		});

		btnEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				editarUsuario();
			}
		});

		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cancelarUsuario();
			}
		});

		btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pesquisarUsuario();
			}
		});

		checkEnderecoEmpreendimento.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean old_val, Boolean new_val) {
				if(new_val == true) {
					//imprimirEnderecoEmpreendimento();
				}
			}
		});

		btnExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirUsuario();
			}
		});

	}

}
