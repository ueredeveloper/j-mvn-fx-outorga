package controladores.principal;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.exception.JDBCConnectionException;

import dao.VistoriaDao;
import entidades.Endereco;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import principal.Alerta;

public class TabVistoriaControlador implements Initializable {
	
TabAtoControlador tabAtoControlador = new TabAtoControlador ();

	Button btnNovo = new Button("Novo");
	Button btnSalvar = new Button("Salvar");
	Button btnEditar = new Button("Editar");
	Button btnExcluir = new Button("Excluir");
	Button btnCancelar = new Button("Cancelar");
	Button btnPesquisar = new Button("Pesquisar");
	TextField tfPesquisar = new TextField();
	
	TextField tfNumVistoria = new TextField();
	TextField tfNumVisSEI = new TextField();
	DatePicker dpDataFiscalizacao  = new DatePicker();
	DatePicker dpDataCriacaoAto  = new DatePicker();
	
	
	Button btnIfracoes;
	Button btnPenalidades;
	Button btnAtenuantes;
	Button btnAgravantes;
	
	Button btnAjudaRelatorio;
	Button btnRecomendacoes;
	
	Button btnPesquisarObjeto;
	Button  btnPesquisarApresentacao;
	Button btnPesquisarRelato;
	Button btnRelatorio;
	
	// capturar o resultados dos metodo de abertura do choicebox e persistir //
	String strInfracoes;
	String strPenalidades;
	String strAgravantes;
	String strAtenuantes;
	
	// retornar o resultados do metodo de abertura dos choicebox //
	String strcbResultado;
	// string de pesquisa //
	String strPesquisa = "";
	
	
	public void btnNovoHab () {
		
		tfNumVistoria.setText(null);
		tfNumVisSEI.setText(null);
		dpDataFiscalizacao.getEditor().clear(); // limpar datepicker
		dpDataCriacaoAto.getEditor().clear();
		
		htmlObjeto.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlApresentacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlRelato.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		htmlRecomendacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		
		tfNumVistoria.setDisable(false);
		tfNumVisSEI.setDisable(false);
		dpDataFiscalizacao.setDisable(false);
		dpDataCriacaoAto.setDisable(false);
		
		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		
		abrirEditorHTML();
		abrirCheckBox();
		LimparCheckBox();
		
		System.out.println("btn novo clicado");
		
	}
	
	public void btnSalvarHab () {
		
		
		if (endereco.getEndLogradouro() == null) {
			
			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Endereço NÃO selecionado!!!", ButtonType.OK));
			
			
		} else {
			
			if (dpDataFiscalizacao.getValue() == null  ||
					dpDataCriacaoAto.getValue() == null  ||
						tfNumVistoria.getText().isEmpty()  ||
						tfNumVisSEI.getText().isEmpty()
					
					) {
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Número do Ato, Data da fiscalização e Data de Criação!!!", ButtonType.OK));
				
			} else {
		
					Vistoria vis = new Vistoria();
					
						vis.setVisIdentificacao(tfNumVistoria.getText());
						vis.setVisSEI(tfNumVisSEI.getText());
						
						if (dpDataFiscalizacao.getValue() == null) {
							vis.setVisDataFiscalizacao(null);}
						else {
							vis.setVisDataFiscalizacao(Date.valueOf(dpDataFiscalizacao.getValue()));
						}
						
						if (dpDataCriacaoAto.getValue() == null) {
							vis.setVisDataCriacao(null);}
						else {
							vis.setVisDataCriacao(Date.valueOf(dpDataCriacaoAto.getValue()));
						}
						
						vis.setVisInfracoes(strInfracoes);
						vis.setVisPenalidades(strPenalidades);
						vis.setVisAgravantes(strAgravantes);
						vis.setVisAtenuantes(strAtenuantes);
						
						vis.setVisObjeto(htmlObjeto.getHtmlText());
						vis.setVisApresentacao(htmlApresentacao.getHtmlText());
						vis.setVisRelato(htmlRelato.getHtmlText());
						vis.setVisRecomendacoes(htmlRecomendacao.getHtmlText());
						
						
						vis.setVisEnderecoFK(endereco);
						
						vis.setVisAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
						
						VistoriaDao visDao = new VistoriaDao();
						
						visDao.salvarVistoria(vis);
						visDao.mergeVistoria(vis);
						
						obsList.add(vis);
						
						tabAtoControlador.setVistoria(vis);
						
						modularBotoes();
						fecharEditorHTML();
						
						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro salvo com sucesso!!!", ButtonType.OK));
					
						
			}
		}
		
	}
	
	public void btnEditarHab () {
		
		if (tfNumVistoria.isDisable()) {
			
			tfNumVistoria.setDisable(false);
			tfNumVisSEI.setDisable(false);
			dpDataFiscalizacao.setDisable(false);
			dpDataCriacaoAto.setDisable(false);
			
			htmlObjeto.setDisable(false);
			htmlApresentacao.setDisable(false);
			htmlRelato.setDisable(false);
			htmlRecomendacao.setDisable(false);
			
			abrirEditorHTML();
			abrirCheckBox();
			
		} else {
			
			if (dpDataFiscalizacao == null  ||
					dpDataCriacaoAto == null
					
					) {
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Data da Fiscalização e Data de Criação do Ato!!!", ButtonType.OK));
				
			} else {
		
				try {
				
					Vistoria vis  = tvVistoria.getSelectionModel().getSelectedItem();
				
					//-- capturar endereço relacionado --//
					vis.setVisEnderecoFK(endereco);
				
					
					vis.setVisIdentificacao(tfNumVistoria.getText());
					vis.setVisSEI(tfNumVisSEI.getText());
						
					if (dpDataFiscalizacao.getValue() == null) {
						vis.setVisDataFiscalizacao(null);}
					else {
						vis.setVisDataFiscalizacao(Date.valueOf(dpDataFiscalizacao.getValue()));
						
					}
					
					if (dpDataCriacaoAto.getValue() == null) {
						vis.setVisDataCriacao(null);}
					else {
						vis.setVisDataCriacao(Date.valueOf(dpDataCriacaoAto.getValue()));
					}
					
					vis.setVisInfracoes(strInfracoes);
					vis.setVisPenalidades(strPenalidades);
					vis.setVisAgravantes(strAgravantes);
					vis.setVisAtenuantes(strAtenuantes);
					
					vis.setVisObjeto(htmlObjeto.getHtmlText());
					vis.setVisApresentacao(htmlApresentacao.getHtmlText());
					vis.setVisRelato(htmlRelato.getHtmlText());
					vis.setVisRecomendacoes(htmlRecomendacao.getHtmlText());
					
					vis.setVisAtualizacao(Timestamp.valueOf((LocalDateTime.now())));
					
							VistoriaDao visDao = new VistoriaDao();
							
							visDao.mergeVistoria(vis);
							
							obsList.remove(vis);
							obsList.add(vis);
							
							tabAtoControlador.setVistoria(vis);
							
							modularBotoes();
							fecharEditorHTML();
							
							Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.INFORMATION, "Vistoria editada!", ButtonType.OK));
							
				
						} catch (Exception e) {
							
							Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao editar vistoria!", ButtonType.OK));
							
						}
			
			}
			
		}
		
	}

	public void btnExcluirHab () {
		
		try {
		
			Vistoria vis = tvVistoria.getSelectionModel().getSelectedItem();
			
			VistoriaDao visDao = new VistoriaDao();
			
				try {
					
					visDao.removerVistoria(vis.getVisID());
					obsList.remove(vis);
				
					modularBotoes();
					fecharEditorHTML();
					LimparCheckBox();
					
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído!", ButtonType.OK));
					
					}	catch (JDBCConnectionException eJDBC) {
							
							System.out.println("erro jdbc " +  eJDBC.toString());
							Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.ERROR, "erro ao excluir o cadastro!!!", ButtonType.OK));
					}
				
			}	catch (Exception e) {
					
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "erro ao excluir o cadastro!!!", ButtonType.OK));
			}
			
}

	public void btnCancelarHab () {
		
		modularBotoes();
		fecharEditorHTML();
		LimparCheckBox();
		
	}
	
	public void btnPesquisarHab () {
		
		try {
			
		strPesquisa = tfPesquisar.getText();
		listarVistorias(strPesquisa);

		modularBotoes();
		fecharEditorHTML();
		LimparCheckBox();
		
		
		
		}
		
			catch (Exception e) {
				
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.ERROR, "Erro de conexão!!!" + "[ " + e + " ]", ButtonType.OK));
			}
		
	}
	
	/*
	String strData;
	String strEndereco;
	
	public void btnPesqObjHab (ActionEvent event) {
		
		/*
		try {
			strData = formatter.format(dpDataFiscalizacao.getValue());
		} catch (Exception e) {
			strData = "DATA";
		}
		*/
		
		/*
		try {
			strEndereco = endereco.getEndLogadouro();
		} catch (Exception e) {
			strEndereco = "ENDEREÇO";
		}
		
		//e se a vistoria for salva pela primeira vez? Ainda  não há as informaçoes visGeral
				
		String objeto = "<p>Em atendimento ao MEMORANDO... foi realizada vistoria no dia "
				+ strData 
				+ ", para verifica&ccedil;&atilde;o de DESCRIÇÃO DENÚNCIA, no endereço: " + strEndereco + ".";
				;
		
		htmlObjeto.setHtmlText(objeto);
		
	}
	
	public void btnPesApHab (ActionEvent event) {
		
		String apresentacao = "A vistoria ocorreu em " + strData + ", por volta das HORAS, "
				+ "e contou com a presen&ccedil;a do(s) t&eacute;cnico(s) "
				+ "TECNICO e do respons&aacute;vel pela propriedade USUÁRIO.";
		
		htmlApresentacao.setHtmlText(apresentacao);
		
	}
	
*/

	static Endereco endereco = new Endereco ();

	public void setEndereco (Endereco endereco) {
	
		TabVistoriaControlador.endereco = endereco;
	
		TabVistoriaControlador.lblEndereco.setText(
				endereco.getEndLogradouro() 
				+ ", Cidade: " + endereco.getEndCidade()
				+ ", CEP: " + endereco.getEndCEP()
				);
		
	}

	public static Endereco getEndereco () {
		return endereco;
	}

	static Label lblEndereco = new Label();
	
	@FXML Pane pVistoria;
	AnchorPane apPrincipal = new AnchorPane();
	BorderPane bpPrincipal = new BorderPane();
	ScrollPane spPrincipal = new ScrollPane();
	Pane p1 = new Pane ();

	Pane pEndereco = new Pane();
	Pane pDadosBasicos = new Pane();
	Pane pPersistencia = new Pane();
	
	Pane pInfracao = new Pane();
	Pane pPenalidade = new Pane();
	Pane pAtenuantes = new Pane();
	Pane pAgravantes = new Pane();
	
	HTMLEditor htmlObjeto = new HTMLEditor();
	HTMLEditor htmlApresentacao = new HTMLEditor();
	HTMLEditor htmlRelato = new HTMLEditor();
	HTMLEditor htmlRecomendacao = new HTMLEditor();
	
	// TableView Endereço //
	TableView <Vistoria> tvVistoria = new TableView<>();
			
	TableColumn<Vistoria, String> tcNumero = new TableColumn<>();
	TableColumn<Vistoria, String> tcSEI = new TableColumn<>();
	TableColumn<Vistoria, String> tcData = new TableColumn<>();
	ObservableList<Vistoria> obsList = FXCollections.observableArrayList();
	
	@SuppressWarnings("unchecked")
	public void initialize(URL url, ResourceBundle rb) {
		
		pVistoria.getChildren().add(apPrincipal);
		
		apPrincipal.minWidthProperty().bind(pVistoria.widthProperty());
		apPrincipal.minHeightProperty().bind(pVistoria.heightProperty());
		
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
	    
	    p1.setMaxSize(1140, 2084);
	    p1.setMinSize(1140, 2084);
	    
	    tcNumero.setCellValueFactory(new PropertyValueFactory<Vistoria, String>("visIdentificacao"));
		tcSEI.setCellValueFactory(new PropertyValueFactory<Vistoria, String>("visSEI"));  
		tcData.setCellValueFactory(new PropertyValueFactory<Vistoria, String>("visDataFiscalizacao")); 
		
		tcNumero.setPrefWidth(409);
		tcSEI.setPrefWidth(232);
		tcData.setPrefWidth(232);
		
		tcNumero.setText("Número da Vistoria");
		tcSEI.setText("SEI");
		tcData.setText("Data da Fiscalizacão");
		
		tvVistoria.setPrefSize(900, 185);
		tvVistoria.setLayoutX(120);
		tvVistoria.setLayoutY(205);
		
		tvVistoria.getColumns().addAll(tcNumero, tcSEI, tcData);
		tvVistoria.setItems(obsList);
		
	    bpPrincipal.setTop(p1);
	    BorderPane.setAlignment(p1, Pos.CENTER);
	    
	    chamarEditoresHTML ();
	    chamarLegislacao ();
	    chamarEndereco ();
	    chamarDadosBasicos ();
		chamarPersistencia ();
		chamarCheckBox ();
		
		Pane pBotoesLeg = new Pane ();
		Button bInfra = new Button("?");
		Button bPena = new Button("?");
		Button bAten = new Button("?");
		Button bAgra = new Button("?");
		
		pBotoesLeg.setPrefSize(32, 144);
		pBotoesLeg.setLayoutX(1044);
		pBotoesLeg.setLayoutY(396);
		
		bInfra.setPrefSize(25, 25);
		bInfra.setLayoutX(4);
		bInfra.setLayoutY(7);
		
		bPena.setPrefSize(25, 25);
		bPena.setLayoutX(4);
		bPena.setLayoutY(42);
		
		bAten.setPrefSize(25, 25);
		bAten.setLayoutX(4);
		bAten.setLayoutY(77);
		
		bAgra.setPrefSize(25, 25);
		bAgra.setLayoutX(4);
		bAgra.setLayoutY(112);
		
		pBotoesLeg.getChildren().addAll(bInfra, bPena, bAten, bAgra);
		
		
	    p1.getChildren().addAll(
	    		
	    		pEndereco, pDadosBasicos, pPersistencia, tvVistoria,
	    		pBotoesLeg
	    		);
	   
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
	    
	    btnExcluir.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	           btnExcluirHab();
	        }
	    });
	    
	    btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnCancelarHab();
	        }
	    });
	    
	    btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	            btnPesquisarHab();
	        }
	    });
	    
	    ObterArtigos ();
	    
	    bInfra.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	lerIncisosDoCheckBox(infraIncisos);
	        }
	    });
	    
	    bPena.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	lerIncisosDoCheckBox(penaIncisos);
	        }
	    });
	    
	    bAten.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	lerIncisosDoCheckBox(atenIncisos);
	        }
	    });
	    
	    bAgra.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	lerIncisosDoCheckBox(agraIncisos);
	        }
	    });
	    
	    selecionarVistoria();
	    
	    
	}
	
	final String[] incisos = new String[]{
			
			"Inciso I",
			"Inciso II", 
			"Inciso III",
			"Inciso IV",
			"Inciso V",
			"Inciso VI",
			"Inciso VII",
	};
	final String[] incisosAten = new String[]{
			
			"Inciso I",
			"Inciso II", 
			"Inciso III",
			"Inciso IV",
			"Inciso V",
			"Inciso VI",
			"Inciso VII",
			"Inciso VIII",
			"Inciso IX",
	};
	final String[] incisosAgra = new String[]{
			
			"a);",
			"b);",
			"c);",
			"d);",
			"e);",
			"f);",
			"g);",
			"h);",
			"i);",
			"j);",
			"k);",
			"l);",
	};
	
	final CheckBox[] cbInfracoes = new CheckBox[incisos.length];
	final CheckBox[] cbPenalidades = new CheckBox[incisos.length];
	final CheckBox[] cbAtenuantes = new CheckBox[incisosAten.length];
	final CheckBox[] cbAgravantes = new CheckBox[incisosAgra.length];
	
	final CheckBox[] cbInfraBox = new CheckBox[incisos.length];
	
	final int [] layoutXcb = new int[] {
			196,
			272,
			354,
			439,
			526,
			610,
			696
	};
	final int [] layoutXcbAtenuantes = new int[] {
			116,
			196,
			277,
			360,
			445,
			528,
			615,
			704,
			797
	};
	final int [] layoutXcbAgravantes = new int[] {
			211,
			260,
			
			309,
			357,
			
			406,
			455,
			
			501,
			550,
			
			599,
			644,
			
			689,
			737
	};
	
	final int [] numIncisos = new int[] {
			1,
			2,
			3,
			4,
			5,
			6,
			7
	};
	final int [] numIncisosAten = new int[] {
			1,
			2,
			3,
			4,
			5,
			6,
			7,
			8,
			9
	};
	final String[] letraIncisosAgra = new String[]{
			
			"a",
			"b",
			"c",
			"d",
			"e",
			"f",
			"g",
			"h",
			"i",
			"j",
			"k",
			"l",
	};

	public void chamarCheckBox () {
		
		Label lblInfra = new Label("Infrações:");
		
		lblInfra.setLayoutX(126);
		lblInfra.setLayoutY(3);
		
		for (int i = 0; i < incisos.length; i++) {
		    
		final CheckBox cb = cbInfracoes[i] = new CheckBox(incisos[i]);

		    cb.setLayoutX(layoutXcb[i]);
		    cb.setLayoutY(3);
		    
		    cb.setAccessibleText(String.valueOf(numIncisos[i]));
		    
		    cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    	
		        public void changed(ObservableValue<? extends Boolean> ov,
		            Boolean old_val, Boolean new_val) {
		               
		                String strCheck = "";
		                
		                for (int i = 0; i < incisos.length; i++) {
		              
		                	if (cbInfracoes[i].isSelected()) {
		                		
		                		strCheck += cbInfracoes[i].getAccessibleText();
					             
		                	}
		                	
		                }
		                strInfracoes = strCheck;
		                System.out.println(" listener do cbInfracoes, infrações escolhidas " + strInfracoes);
		                
		        }
		    });
		    
		    pInfracao.getChildren().add(cb);
		}
		
		pInfracao.getChildren().add(lblInfra);
		
		
		Label lblPena = new Label("Penalidades:");
		
		lblPena.setLayoutX(112);
		lblPena.setLayoutY(5);
		
		for (int i = 0; i < incisos.length; i++) {
		    
			final CheckBox cb = cbPenalidades [i] = new CheckBox(incisos[i]);

			cb.setLayoutX(layoutXcb[i]);
			cb.setLayoutY(3);
			    
			cb.setAccessibleText(String.valueOf(numIncisos[i]));
			    
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
			    	
			        public void changed(ObservableValue<? extends Boolean> ov,
			            Boolean old_val, Boolean new_val) {
			               
			                String strCheck = "";
			                
			                for (int i = 0; i < incisos.length; i++) {
			              
			                	if (cbPenalidades[i].isSelected()) {
			                		
			                		strCheck += cbPenalidades[i].getAccessibleText();
						             
			                	}
			                	
			                }
			                strPenalidades = strCheck;
			                System.out.println(" listener do cbPenalidades, penalidades escolhidas " + strPenalidades);
			                
			        }
			    });
			    
			    pPenalidade.getChildren().add(cb);
			}
			
			pPenalidade.getChildren().add(lblPena);
		

			Label lblAten = new Label("Atenuantes:");
			
			lblAten.setLayoutX(26);
			lblAten.setLayoutY(5);
			
			for (int i = 0; i < incisosAten.length; i++) {
			    
				final CheckBox cb = cbAtenuantes [i] = new CheckBox(incisosAten[i]);

				cb.setLayoutX(layoutXcbAtenuantes[i]);
				cb.setLayoutY(3);
				    
				cb.setAccessibleText(String.valueOf(numIncisosAten[i]));
				    
				cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				    	
				        public void changed(ObservableValue<? extends Boolean> ov,
				            Boolean old_val, Boolean new_val) {
				               
				                String strCheck = "";
				                
				                for (int i = 0; i < incisosAten.length; i++) {
				              
				                	if (cbAtenuantes[i].isSelected()) {
				                		
				                		strCheck += cbAtenuantes[i].getAccessibleText();
							             
				                	}
				                	
				                }
				                strAtenuantes = strCheck;
				                System.out.println(" listener do cbAtenuantes, atenuantes escolhidos " + strAtenuantes);
				                
				        }
				    });
				    
				    pAtenuantes.getChildren().add(cb);
				}
				
				pAtenuantes.getChildren().add(lblAten);
			

				Label lblAgra = new Label("Agravantes:");
				
				lblAgra.setLayoutX(124);
				lblAgra.setLayoutY(5);
				
				for (int i = 0; i < incisosAgra.length; i++) {
				    
					final CheckBox cb = cbAgravantes [i] = new CheckBox(incisosAgra[i]);

					cb.setLayoutX(layoutXcbAgravantes[i]);
					cb.setLayoutY(3);
					    
					cb.setAccessibleText(letraIncisosAgra[i]);
					    
					cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
					    	
					        public void changed(ObservableValue<? extends Boolean> ov,
					            Boolean old_val, Boolean new_val) {
					               
					                String strCheck = "";
					                
					                for (int i = 0; i < incisosAgra.length; i++) {
					              
					                	if (cbAgravantes[i].isSelected()) {
					                		
					                		strCheck += cbAgravantes[i].getAccessibleText();
								             
					                	}
					                	
					                }
					                strAgravantes = strCheck;
					                System.out.println(" listener do cbAgravantes, agravantes escolhidos " + strAgravantes);
					                
					        }
					    });
					    
					    pAgravantes.getChildren().add(cb);
					}
					
					pAgravantes.getChildren().add(lblAgra);
					
		
		
	}
	
	public void chamarEditoresHTML () {
		
		Label lblObjeto = new Label ("OBJETO: ");
		lblObjeto.setLayoutX(120);
		lblObjeto.setLayoutY(547);
		
		htmlObjeto.setPrefSize(820, 200);
	    htmlObjeto.setLayoutX(160);
	    htmlObjeto.setLayoutY(573);
	    
	    Label lblApre = new Label ("APRESENTAÇÃO: ");
	    lblApre.setLayoutX(120);
	    lblApre.setLayoutY(783);
	    
	    htmlApresentacao.setPrefSize(820, 200);
	    htmlApresentacao.setLayoutX(160);
	    htmlApresentacao.setLayoutY(811);
		 
	    Label lblRel= new Label ("RELATO: ");
	    lblRel.setLayoutX(120);
	    lblRel.setLayoutY(1022);
	    
	    htmlRelato.setPrefSize(820, 673);
	    htmlRelato.setLayoutX(160);
	    htmlRelato.setLayoutY(1047);
	    
	    Label lblRecom = new Label ("RECOMENDAÇÕES: ");
	    lblRecom.setLayoutX(120);
	    lblRecom.setLayoutY(1728);
	    
	    htmlRecomendacao.setPrefSize(820, 200);
	    htmlRecomendacao.setLayoutX(160);
	    htmlRecomendacao.setLayoutY(1754);
	    
		htmlObjeto.setOnKeyPressed(event -> {
			    if (event.getCode() == KeyCode.SPACE  
			            || event.getCode() == KeyCode.TAB ) {
			        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
			        event.consume();
			    }
			});
		
		htmlApresentacao.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.SPACE  
		            || event.getCode() == KeyCode.TAB ) {
		        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
		        event.consume();
		    }
		});
		
		htmlRelato.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.SPACE  
		            || event.getCode() == KeyCode.TAB ) {
		        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
		        event.consume();
		    }
		});
		
		htmlRecomendacao.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.SPACE  
		            || event.getCode() == KeyCode.TAB ) {
		        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
		        event.consume();
		    }
		});
		
	    
	    p1.getChildren().addAll(
	    		
	    		lblObjeto, lblApre, lblRecom, lblRel,
	    		htmlObjeto, htmlApresentacao, htmlRelato, htmlRecomendacao
	    		
	    		);
	    
	}
	
	Button btnBuscarEnd = new Button();
	
	public void chamarEndereco () {
		
		pEndereco = new Pane();
		pEndereco.setStyle("-fx-background-color: #E9E9E9;");
		
		pEndereco.setPrefSize(900, 50);
		pEndereco.setLayoutX(120);
		pEndereco.setLayoutY(20);
		
		Label lblEnd = new Label("Endereco: ");
		lblEnd.setLayoutX(23);
		lblEnd.setLayoutY(16);
		 
		lblEndereco.setPrefSize(750, 25);
		lblEndereco.setLayoutX(93);
		lblEndereco.setLayoutY(13);
		lblEndereco.setStyle("-fx-font-weight: bold;");

		btnBuscarEnd.setPrefSize(25, 25);
		btnBuscarEnd.setLayoutX(852);
		btnBuscarEnd.setLayoutY(13);
		
		pEndereco.getChildren().addAll(lblEnd, lblEndereco, btnBuscarEnd);

	}
	
	public void chamarLegislacao () {
		
		pInfracao.setPrefSize(900, 25);
		pInfracao.setLayoutX(120);
		pInfracao.setLayoutY(404);
		
		pPenalidade.setPrefSize(900, 25);
		pPenalidade.setLayoutX(120);
		pPenalidade.setLayoutY(439);
		
		pAtenuantes.setPrefSize(900, 25);
		pAtenuantes.setLayoutX(120);
		pAtenuantes.setLayoutY(474);
		
		pAgravantes.setPrefSize(900, 25);
		pAgravantes.setLayoutX(120);
		pAgravantes.setLayoutY(509);
		
		
		pInfracao.setStyle("-fx-background-color:  EFEFEF;");
		pPenalidade.setStyle("-fx-background-color:  EFEFEF;");
		pAtenuantes.setStyle("-fx-background-color:  EFEFEF;");
		pAgravantes.setStyle("-fx-background-color:  EFEFEF;");
		
		p1.getChildren().addAll(
	    		
	    		pInfracao, pPenalidade, pAtenuantes, pAgravantes
	    		
	    		);
		
	}
	
	public void chamarDadosBasicos () {
		
	 	pDadosBasicos.setStyle("-fx-background-color: #E9E9E9;");
	    pDadosBasicos.setPrefSize(900, 50);
	    pDadosBasicos.setLayoutX(120);
	    pDadosBasicos.setLayoutY(82);
	   
	    Label lblNumVistoria= new Label("Número do Ato: ");
	    lblNumVistoria.setLayoutX(35);
	    lblNumVistoria.setLayoutY(17);
	    
	    tfNumVistoria.setPrefSize(100, 25);
	    tfNumVistoria.setLayoutX(135);
	    tfNumVistoria.setLayoutY(12);
	    
	    Label lblNumVisSEI = new Label("SEI: ");

	    lblNumVisSEI.setLayoutX(245);
	    lblNumVisSEI.setLayoutY(17);
	    
	    tfNumVisSEI.setPrefSize(100, 25);
	    tfNumVisSEI.setLayoutX(275);
	    tfNumVisSEI.setLayoutY(12);
	    
	    Label lblFiscalizacao = new Label("Data da Fiscalização:");
	    lblFiscalizacao.setLayoutX(385);
	    lblFiscalizacao.setLayoutY(17);

	    dpDataFiscalizacao.setPrefSize(120, 25);
	    dpDataFiscalizacao.setLayoutX(515); //504
	    dpDataFiscalizacao.setLayoutY(12);

	    Label lblCriacao = new Label("Data de Criação: ");
	    lblCriacao.setLayoutX(645); //632
	    lblCriacao.setLayoutY(17);

	    dpDataCriacaoAto.setPrefSize(120, 25);
	    dpDataCriacaoAto.setLayoutX(750); // 735
	    dpDataCriacaoAto.setLayoutY(12);

	    pDadosBasicos.getChildren().addAll( 
	    		
	    		lblNumVistoria, tfNumVistoria,
				lblNumVisSEI, tfNumVisSEI,
				lblFiscalizacao, dpDataFiscalizacao,
				lblCriacao, dpDataCriacaoAto
				
				);
	    
	}
	 
    public void chamarPersistencia () {
    	
    	pPersistencia  = new Pane();
   	    pPersistencia.setPrefSize(900, 50);
   	    pPersistencia.setLayoutX(120);
   	    pPersistencia.setLayoutY(145);
   
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
	
	
	public void listarVistorias (String strPesquisa) {
 		
	 	// --- conexão - listar endereços --- //
		VistoriaDao visDao = new VistoriaDao();
		List<Vistoria> visList = visDao.listarVistoria(strPesquisa);
		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
			
			for (Vistoria vis : visList) {
				
				vis.getVisID();
				vis.getVisEnderecoFK();
				vis.getVisObjeto();
				vis.getVisApresentacao();
				vis.getVisRelato();
				vis.getVisRecomendacoes();
				vis.getVisInfracoes();
				vis.getVisPenalidades();
				vis.getVisAtenuantes();
				vis.getVisAgravantes();
				vis.getVisIdentificacao();
				vis.getVisSEI();
				vis.getVisDataFiscalizacao();
				vis.getVisDataCriacao();
				
				obsList.add(vis);
				
			}
			
				
					
	}

	String infrArray [];
	String penaArray [];
	String agraArray [];
	String atenArray [];
	
	// método selecionar vistoria -- //
	public void selecionarVistoria () {
		
		tvVistoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			Vistoria vis = (Vistoria) newValue;
			
			if (vis == null) {
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			} else {

				tfNumVistoria.setText(vis.getVisIdentificacao());
				tfNumVisSEI.setText(vis.getVisSEI());
				
				if (vis.getVisDataFiscalizacao() == null) {
					dpDataFiscalizacao.setValue(null);
	 				} else {
	 					Date dataFis = vis.getVisDataFiscalizacao();
	 					dpDataFiscalizacao.setValue(dataFis.toLocalDate());
	 				}
 				
 				if (vis.getVisDataCriacao() == null) {
 					dpDataCriacaoAto.setValue(null);
	 				} else {
	 					Date dataCri = vis.getVisDataCriacao();
	 					dpDataCriacaoAto.setValue(dataCri.toLocalDate());
	 				}
 				
 				
				String infr =  vis.getVisInfracoes();
				String pena = vis.getVisPenalidades();
				String agra = vis.getVisAgravantes();
				String aten = vis.getVisAtenuantes();
				
				LimparCheckBox();
				
				//-- infrações --//
				
				if (infr != null) {
					
					infrArray = infr.split("");
					
					for (int i = 0; i<infrArray.length; i++) {
						
						for (int z = 0; z<cbInfracoes.length; z++) {
							
							if (infrArray[i].equals(cbInfracoes[z].getAccessibleText()) ) {
								
								cbInfracoes[z].setSelected(true);
							
							}}}} // fim do if infraArray ! null
							
					if (pena != null) {
						
						penaArray = pena.split("");
						
						for (int i = 0; i<penaArray.length; i++) {
							
							for (int z = 0; z<cbPenalidades.length; z++) {
								
								if (penaArray[i].equals(cbPenalidades[z].getAccessibleText()) ) {
									
									cbPenalidades[z].setSelected(true);
								
								}}}} // fim do if infraPena ! null		
				
							if (aten != null) {
								
								atenArray = aten.split("");
								
								for (int i = 0; i<atenArray.length; i++) {
									
									for (int z = 0; z<cbAtenuantes.length; z++) {
										
										if (atenArray[i].equals(cbAtenuantes[z].getAccessibleText()) ) {
											
											cbAtenuantes[z].setSelected(true);
										
										}}}} // fim do if atenuantes ! null	

								if (agra != null) {
					
									agraArray = agra.split("");
									
									for (int i = 0; i<agraArray.length; i++) {
										
										for (int z = 0; z<cbAgravantes.length; z++) {
											
											if (agraArray[i].equals(cbAgravantes[z].getAccessibleText()) ) {
												
												cbAgravantes[z].setSelected(true);
							
											}}}} // fim do if agravantes ! null	
						
				htmlObjeto.setHtmlText(vis.getVisObjeto());
				htmlApresentacao.setHtmlText(vis.getVisApresentacao());
				htmlRelato.setHtmlText(vis.getVisRelato());
				htmlRecomendacao.setHtmlText(vis.getVisRecomendacoes());
				
				
				setEndereco(vis.getVisEnderecoFK());
				
				tabAtoControlador.setVistoria(vis);
				
				//-- pegar a vistoria selecionada --//
				//Vistoria visG = new Vistoria(vis);
				
				
				//visGeral = visG;
				//main.pegarVistoria(visGeral);
				
				//-- mudar o endereço de acordo com a seleção --//
				//eGeralVis = visTab.getVisEndCodigoFK();
				//lblVisEnd.setText(eGeralVis.getDesc_Endereco() + " |  RA: "  + eGeralVis.getRA_Endereco() );
				
				// copiar número da vistoria no sei ao selecionar //
				Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                conteudo.putString(vis.getVisSEI());
                clip.setContent(conteudo);
                
				//-- modular botões --//
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			}
			}
		});
	}
	 	
	
	 public void fecharEditorHTML () {
		 htmlObjeto.setDisable(true);
		 htmlApresentacao.setDisable(true);
		 htmlRelato.setDisable(true);
		 htmlRecomendacao.setDisable(true);
	 }
	 
	 public void abrirEditorHTML () {
		 htmlObjeto.setDisable(false);
		 htmlApresentacao.setDisable(false);
		 htmlRelato.setDisable(false);
		 htmlRecomendacao.setDisable(false);
	 }
	 	
	 public void modularBotoes () {
		 
		 tfNumVistoria.setDisable(true);
		 tfNumVisSEI.setDisable(true);
		 dpDataFiscalizacao.setDisable(true);
		 dpDataCriacaoAto.setDisable(true);
		 
		 btnSalvar.setDisable(true);
		 btnEditar.setDisable(true);
		 btnExcluir.setDisable(true);
		 
		 btnNovo.setDisable(false);
		 
		 //fecharCheckBox ();
	 }
	 
	 
	 public void fecharCheckBox () {
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbInfracoes[i].setDisable(true);
		 }
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbPenalidades[i].setDisable(true);
		 }
		 
		 for (int i = 0; i < incisosAten.length; i++) {
			 cbAtenuantes[i].setDisable(true);
		 }
		 
		 for (int i = 0; i < incisosAgra.length; i++) {
			 cbAgravantes[i].setDisable(true);
		 }
		
	 }
	 
	 public void abrirCheckBox () {
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbInfracoes[i].setDisable(false);
		 }
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbPenalidades[i].setDisable(false);
		 }
		 
		 for (int i = 0; i < incisosAten.length; i++) {
			 cbAtenuantes[i].setDisable(false);
		 }
		 
		 for (int i = 0; i < incisosAgra.length; i++) {
			 cbAgravantes[i].setDisable(false);
		 }
		 
	 }

	 public void LimparCheckBox () {
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbInfracoes[i].setSelected(false);
		 }
		 
		 for (int i = 0; i < incisos.length; i++) {
			 cbPenalidades[i].setSelected(false);
		 }
		 
		 for (int i = 0; i < incisosAten.length; i++) {
			 cbAtenuantes[i].setSelected(false);
		 }
		 
		 for (int i = 0; i < incisosAgra.length; i++) {
			 cbAgravantes[i].setSelected(false);
		 }
		 
		 
	 }

	 public void lerIncisosDoCheckBox (String [] s) {
		 
			VBox vBoxInfra = new VBox();
			vBoxInfra.setPrefWidth(915);
			vBoxInfra.setPrefHeight(176);
			vBoxInfra.setLayoutX(25);
		 
				ObservableList<String> obs = FXCollections.observableArrayList(s);
				ListView<String> list = new ListView<String>(obs);
				vBoxInfra.getChildren().add(list);
			
				Group g = new Group(vBoxInfra);
				
				Scene scene = new Scene(g);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(215);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			   
	 }
	 
	String infraIncisos [];
	String atenIncisos [];
	String agraIncisos [];
	String penaIncisos [];
		
	 public void ObterArtigos () {
		 
		 	//-- infrações  --//
			infraIncisos = new String [7];
			
		
			infraIncisos [0] = "I - derivar ou utilizar recursos hídricos para qualquer finalidade, sem a respectiva " + 
					"outorga de direito de uso;";
			
			
			infraIncisos [1] = "II - implantar ou iniciar a implantação de empreendimento que exija derivação ou " + 
					"utilização de recursos hídricos, superficiais ou subterrâneos que implique alterações no regime," + 
					"quantidade ou qualidade dos mesmos, sem a autorização dos órgãos ou entidades competentes;";
			
			infraIncisos [2] = "III - utilizar-se de recursos hídricos ou executar obras ou serviços relacionados com os " + 
					"mesmos em desacordo com as condições estabelecidas na outorga;";
			
			infraIncisos [3] = "IV - perfurar poços para extração de água subterrânea ou operá-los sem a devida " + 
					"autorização;";
			
			infraIncisos [4] = "V - fraudar as medições dos volumes d’água utilizados ou declarar valores diferentes " + 
					"dos medidos;";
			
			infraIncisos [5] = "VI - infringir normas estabelecidas nos regulamentos da legislação vigente e " + 
					"superveniente e nos regulamentos administrativos, inclusive em resoluções, instruções e procedimentos " + 
					"fixados pelos órgãos ou entidades competentes;";
			
			infraIncisos [6] = "VII - obstar ou dificultar a ação fiscalizadora das autoridades competentes, no exercício " + 
					"de suas funções;";
			
			penaIncisos = new String [7];
			
			penaIncisos [0] = "Infração LEVE: Multa no valor base de R$ 400,00 (quatrocentos reais)";
					
			penaIncisos [1] = "Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)";
			
			penaIncisos [2] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
			
			penaIncisos [3] = "Infração LEVE: Multa no valor base de R$ 1.000,00 (um mil reais)";
			
			penaIncisos [4] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
											
			penaIncisos [5] = "Infração GRAVE: Multa no valor base de R$ 10.001 (dez mil e um reais)";
													
			penaIncisos [6] = "Infração LEVE: Multa no valor base de R$ 600,00 (seiscentos reais)";
			
			//-- atenuantes --//
			atenIncisos = new String [9];
			
			
			atenIncisos [0] = "I - baixo grau de instrução ou escolaridade do usuário dos recursos hídricos;";
			
			atenIncisos [1] = "II - arrependimento do usuário, manifestado pela espontânea reparação do dano ou pela " + 
					"mitigação significativa da degradação causada aos recursos hídricos;";
			
			atenIncisos [2] = "III - comunicação prévia, pelo usuário, de perigo iminente de degradação dos recursos " + 
					"hídricos;";
			
			atenIncisos [3] = "IV - oficialização do comprometimento do usuário em sanar as irregularidades e reparar " + 
					"os danos delas decorrentes;";
			
			atenIncisos [4] = "V - colaboração explícita com a fiscalização;";
			
			atenIncisos [5] = "VI - tratando-se de usuário não outorgado, haver espontaneamente procurado a Agência " + 
					"para regularização do uso dos recursos hídricos;";
			
			atenIncisos [6] = "VII - atendimento a todas as recomendações e exigências, nos prazos fixados pela " + 
					"Agência;";
			
			atenIncisos [7] = "VIII - reconstituição dos recursos hídricos degradados ou sua recomposição na forma " + 
					"exigida;";
			
			atenIncisos [8] = "IX - não ter sido autuado por infração nos últimos 5 (cinco) anos anteriores ao fato.";
			
			
			//-- agravantes --//
			agraIncisos = new String [12];
			
			agraIncisos [0] = "a) para obter vantagem pecuniária;";
			
			agraIncisos [1] = "b) mediante coação de outrem para a sua execução material;";
			
			agraIncisos [2] = "c) com implicações graves à saúde pública ou ao meio ambiente, em especial aos " + 
					"recursos hídricos;";
			
			agraIncisos [3] = "d) que atinja áreas de unidades de conservação ou áreas sujeitas, por ato do Poder " + 
					"Público, a regime especial de uso;";
			
			agraIncisos [4] = "e) que atinja áreas urbanas ou quaisquer assentamentos humanos;";
			
			agraIncisos [5] = "f) em época de racionamento do uso de água ou em condições sazonais adversas ao seu " + 
					"uso;";
			
			agraIncisos [6] = "g) mediante fraude ou abuso de confiança;";
			
			agraIncisos [7] = "h) mediante abuso do direito de uso do recurso hídrico;";
			
			agraIncisos [8] = "i) em favor do interesse de pessoa jurídica mantida total ou parcialmente por recursos " + 
					"públicos ou beneficiada por incentivos fiscais;";
			
			agraIncisos [9] = "j) sem proceder à reparação integral dos danos causados;";
			
			agraIncisos [10] = "k) que tenha sido facilitada por funcionário público no exercício de suas funções;";
			
			agraIncisos [11] = "l) mediante fraude documental;";
		 
		 
	 }
	 
	 
	 /*
	 public void btnPenalidadesHab (ActionEvent event) {
		 
		 CheckBox cp1 = new CheckBox();
			CheckBox cp2 = new CheckBox();
			CheckBox cp3 = new CheckBox();
			CheckBox cp4 = new CheckBox();
			CheckBox cp5 = new CheckBox();
			CheckBox cp6 = new CheckBox();
			CheckBox cp7 = new CheckBox();
			
			cp1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
		        checkPena1.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp2.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena2.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp3.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena3.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp4.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena4.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp5.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena5.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp6.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena6.setSelected(newVal);
		        checkPenaHab(null);
		    });
			cp7.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkPena7.setSelected(newVal);
		        checkPenaHab(null);
		    });
			
			VBox vBoxCheck = new VBox();
			vBoxCheck.setSpacing(6);
			vBoxCheck.setLayoutY(3);
			vBoxCheck.setLayoutX(3);
			vBoxCheck.getChildren().addAll(cp1, cp2,cp3,cp4,cp5,cp6,cp7);
			
			VBox vBoxPena = new VBox();
			vBoxPena.setPrefWidth(915);
			vBoxPena.setPrefHeight(170);
			vBoxPena.setLayoutX(25);

			//ObservableList<String> obs = FXCollections.observableArrayList(penaIncisos);
			//ListView<String> list = new ListView<String>(obs);
			
			//vBoxPena.getChildren().add(list);
			
			Group g = new Group(vBoxCheck, vBoxPena);
			
				Scene scene = new Scene(g);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(210);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    /*
			    list.getSelectionModel().selectedItemProperty().addListener(
			    		new ChangeListener<String>() {
			    			public void changed(ObservableValue<? extends String> 
			    				ov, String old_val, String new_val) {
         
                Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent conteudo = new ClipboardContent();
                conteudo.putHtml(new_val);
                String artigo = (String) conteudo.getString();
                conteudo.putString(artigo);
                clip.setContent(conteudo);
                
           }
       });
       
       */
		/*	    
	 }
	 
	 public void btnAtenuantesHab (ActionEvent event) {
		 
		 	CheckBox ca1 = new CheckBox();
			CheckBox ca2 = new CheckBox();
			CheckBox ca3 = new CheckBox();
			CheckBox ca4 = new CheckBox();
			CheckBox ca5 = new CheckBox();
			CheckBox ca6 = new CheckBox();
			CheckBox ca7 = new CheckBox();
			CheckBox ca8 = new CheckBox();
			CheckBox ca9 = new CheckBox();
			
			ca1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
		        checkAten1.setSelected(newVal);
		        checkAtenHab(null);
		    });
			ca2.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten2.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca3.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten3.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca4.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten4.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca5.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten5.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca6.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten6.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca7.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten7.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca8.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten8.setSelected(newVal);
				checkAtenHab(null);
		    });
			ca9.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAten9.setSelected(newVal);
				checkAtenHab(null);
		    });
			
			VBox vBoxCheck = new VBox();
			vBoxCheck.setSpacing(6);
			vBoxCheck.setLayoutY(3);
			vBoxCheck.setLayoutX(3);
			vBoxCheck.getChildren().addAll(ca1, ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9);
			
			VBox vBoxPena = new VBox();
			vBoxPena.setPrefWidth(915);
			vBoxPena.setPrefHeight(210);
			vBoxPena.setLayoutX(25);

			
			//ObservableList<String> obs = FXCollections.observableArrayList(atenIncisos);
			//ListView<String> list = new ListView<String>(obs);
			
			//vBoxPena.getChildren().add(list);
			
			
			Group g = new Group(vBoxCheck, vBoxPena);
		 
		
				Scene scene = new Scene(g);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(250);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    
			    
			    /*
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    list.getSelectionModel().selectedItemProperty().addListener(
			    		new ChangeListener<String>() {
			    			public void changed(ObservableValue<? extends String> 
			    				ov, String old_val, String new_val) {
         
         	  Clipboard clip = Clipboard.getSystemClipboard();
               ClipboardContent conteudo = new ClipboardContent();
               conteudo.putHtml(new_val);
               String artigo = (String) conteudo.getString();
               conteudo.putString(artigo);
               clip.setContent(conteudo);
           }
       });
       
       */
	/*
     
	 }
	 
	 public void btnAgravantesHab (ActionEvent event) {
		 
		 	CheckBox chA = new CheckBox();
			CheckBox chB = new CheckBox();
			CheckBox chC = new CheckBox();
			CheckBox chD = new CheckBox();
			CheckBox chE = new CheckBox();
			CheckBox chF = new CheckBox();
			CheckBox chG = new CheckBox();
			CheckBox chH = new CheckBox();
			CheckBox chI = new CheckBox();
			CheckBox chJ = new CheckBox();
			CheckBox chK = new CheckBox();
			CheckBox chL = new CheckBox();
			
			chA.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
		        checkAgra1.setSelected(newVal);
		        checkAgraHab(null);
		    });
			chB.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra2.setSelected(newVal);
				checkAgraHab(null);
		    });
			chC.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra3.setSelected(newVal);
				checkAgraHab(null);
		    });
			chD.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra4.setSelected(newVal);
				checkAgraHab(null);
		    });
			chE.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra5.setSelected(newVal);
				checkAgraHab(null);
		    });
			chF.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra6.setSelected(newVal);
				checkAgraHab(null);
		    });
			chG.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra7.setSelected(newVal);
				checkAgraHab(null);
		    });
			chH.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra8.setSelected(newVal);
				checkAgraHab(null);
		    });
			chI.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra9.setSelected(newVal);
				checkAgraHab(null);
		    });
			chJ.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra10.setSelected(newVal);
				checkAgraHab(null);
		    });
			chK.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra11.setSelected(newVal);
				checkAgraHab(null);
		    });
			chL.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				checkAgra12.setSelected(newVal);
				checkAgraHab(null);
		    });
			
			VBox vBoxCheck = new VBox();
			vBoxCheck.setSpacing(6);
			vBoxCheck.setLayoutY(3);
			vBoxCheck.setLayoutX(3);
			vBoxCheck.getChildren().addAll(
					chA, chB, chC, chD, chE, chF,
					chG, chH, chI, chJ, chK, chL);
			
			VBox vBoxPena = new VBox();
			vBoxPena.setPrefWidth(915);
			vBoxPena.setPrefHeight(278);
			vBoxPena.setLayoutX(25);

			//ObservableList<String> obs = FXCollections.observableArrayList(agraIncisos);
			//ListView<String> list = new ListView<String>(obs);
			
			//vBoxPena.getChildren().add(list);
			
			Group g = new Group(vBoxCheck, vBoxPena);
		
				
				Scene scene = new Scene(g);
				Stage stage = new Stage(); // StageStyle.UTILITY - tirei para ver como fica, se aparece o minimizar
				stage.setWidth(964);
				stage.setHeight(320);
			    stage.setScene(scene);
			    stage.setMaximized(false);
			    stage.setResizable(false);
			    stage.setX(425.0);
			    stage.setY(410.0);
			   
			    stage.setAlwaysOnTop(true); 
			    stage.show();
			
			    //--  https://docs.oracle.com/javafx/2/ui_controls/ListViewSample.java.html  --// 
			    /*
			    list.getSelectionModel().selectedItemProperty().addListener(
			    		new ChangeListener<String>() {
			    			public void changed(ObservableValue<? extends String> 
			    				ov, String old_val, String new_val) {
         
         	  
         	  Clipboard clip = Clipboard.getSystemClipboard();
               ClipboardContent conteudo = new ClipboardContent();
               conteudo.putHtml(new_val);
               String artigo = (String) conteudo.getString();
               conteudo.putString(artigo);
               clip.setContent(conteudo);
         	  
           }
       });*/
      /*
	 }
	 
	 public void btnAjudaRelatorioHab (ActionEvent event) {
		 
	 }
	 
	 public void btnRecomendacoesHab (ActionEvent event) {
		 
	 }
	 
	 public void btnRelatorioHab (ActionEvent event) {
		 
	 }
	
	 
	 public void relatarHTML () {
			
			
		  		htmlObjeto = new HTMLEditor();
		  		
		  			htmlObjeto.setHtmlText("<p><font face='Times New Roman'> </font></p>");
		  			
		  			/*
					htmlObjeto.setOnKeyPressed(event -> {
					    if (event.getCode() == KeyCode.SPACE  
					            || event.getCode() == KeyCode.TAB ) {
					        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
					        event.consume();
					    }
					});
					
					
		  			htmlObjeto.setPrefSize(800.0, 200.0);
		  		
		  			paneObjeto.getChildren().add(htmlObjeto);
					
				htmlApresentacao  = new HTMLEditor();
			
					htmlApresentacao.setPrefSize(800, 200);
					
					htmlApresentacao.setOnKeyPressed(event -> {
					    if (event.getCode() == KeyCode.SPACE  
					            || event.getCode() == KeyCode.TAB ) {
					        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
					        event.consume();
					    }
					});
					htmlApresentacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
					
					StackPane rootAp = new StackPane();
					rootAp.getChildren().add(htmlApresentacao);
				    paneApresentacao.getChildren().add(htmlApresentacao);
		    
		    
			    htmlRelato  = new HTMLEditor();
			
					htmlRelato.setPrefSize(800, 673);
					
					htmlRelato.setOnKeyPressed(event -> {
					    if (event.getCode() == KeyCode.SPACE  
					            || event.getCode() == KeyCode.TAB ) {
					        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
					        event.consume();
					    }
					});
					htmlRelato.setHtmlText("<p><font face='Times New Roman'> </font></p>");
				
				
				StackPane rootRel = new StackPane();
				rootRel.getChildren().add(htmlRelato);
				paneRelato.getChildren().add(htmlRelato);
				
			    
			htmlRecomendacao  = new HTMLEditor();
			
				htmlRecomendacao.setPrefSize(800, 200);
				
				htmlRecomendacao.setOnKeyPressed(event -> {
				    if (event.getCode() == KeyCode.SPACE  
				            || event.getCode() == KeyCode.TAB ) {
				        // Consume Event before Bubbling Phase, -> otherwise Scrollpane scrolls
				        event.consume();
				    }
				});
				htmlRecomendacao.setHtmlText("<p><font face='Times New Roman'> </font></p>");
				
				StackPane rootReco = new StackPane();
				rootReco.getChildren().add(htmlRecomendacao);
				paneRecomendacao.getChildren().add(htmlRecomendacao);

		}

	
		 */
	
	 
	 
}
