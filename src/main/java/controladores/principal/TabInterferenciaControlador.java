package controladores.principal;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.InterferenciaDao;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.SituacaoProcesso;
import entidades.Subterranea;
import entidades.SubtipoOutorga;
import entidades.Superficial;
import entidades.TipoAto;
import entidades.TipoInterferencia;
import entidades.TipoOutorga;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import principal.Alerta;
import principal.Componentes;
import principal.FormatoData;
import principal.ListasComboBox;

public class TabInterferenciaControlador  implements Initializable{

	TabSubterraneaController tabSubCon;
	TabSuperficialController tabSupCon;

	Endereco endereco = new Endereco ();
	Interferencia interferencia = new Interferencia();
	
	
	public static TabInterferenciaControlador tabInterferenciaControlador;
	

	/**
	 * Setar o endereco na TabInterferencia
	 * @param endereco
	 */
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

	}

	ObservableList<Interferencia> obsList = FXCollections.observableArrayList();

	//-- TableView Endereco --//
	private TableView <Interferencia> tvLista = new TableView<>();

	TableColumn<Interferencia, String> tcTipoInterferencia  = new TableColumn<>("Tipo de Interferência");
	TableColumn<Interferencia, String> tcLogradouro  = new TableColumn<>("Endereço do Empreendimento");
	TableColumn<Interferencia, String> tcSituacao  = new TableColumn<>("Situação");

	Label lblDataAtualizacao = new Label();

	TipoInterferencia tipoInterferencia = new TipoInterferencia();
	TipoOutorga tipoOutorga = new TipoOutorga();
	// inicializa com o id 5 caso o suptipo de  outorga nao seja escolhido
	SubtipoOutorga subtipoOutorga = new SubtipoOutorga(5);
	TipoAto tipoAto = new TipoAto();
	SituacaoProcesso situacaoProcesso = new  SituacaoProcesso();
	
	/**
	 * Habilitar os botoes para adicionar nova interferencia
	 */
	public void habilitarInterferencia () {

		cbTipoInterferencia.setDisable(false);
		cbTipoOutorga.setDisable(false);
		cbSubtipoOutorga.setDisable(false);
		cbTipoAto.setDisable(false);
		cbSituacao.setDisable(false);

		dpDataPublicacao.setDisable(false);
		dpDataVencimento.setDisable(false);
		tfNumeroAto.setDisable(false);
		tfProcesoOutorga.setDisable(false);
		tfDespachoOutorga.setDisable(false);

		btnNovo.setDisable(true);
		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		tfPesquisar.setDisable(false);

		btnPesquisar.setDisable(false);
		
		
		// limpar campos
		cbTipoOutorga.getSelectionModel().clearSelection();
		cbSubtipoOutorga.getSelectionModel().clearSelection();
		subtipoOutorga.setSubtipoOutorgaID(5);
		
		cbTipoAto.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		
		dpDataPublicacao.setValue(null);
		dpDataVencimento.setValue(null);
		tfNumeroAto.setText("");
		tfProcesoOutorga.setText("");
		tfDespachoOutorga.setText("");

		//cbTipoInterferencia.setItems(olTipoInterferencia);


	}

	public void salvarInterferencia () {


		if (endereco == null) { // colocar na tabela que não pode ser nulo o id do endereco

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR,"Endereço relacionado não selecionado!!!", ButtonType.OK));


		} else {

			if (tipoInterferencia.getTipoInterID() == 2) {

				Subterranea sub = new Subterranea ();
					sub = TabSubterraneaController.tabSubCon.capturarSubterranea();

				if (sub.getSubTipoPocoFK() == null ||
						sub.getSubCaesb() == null ||
						sub.getSubSubSistemaFK() == null
						) {

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR,"Informe: Tipo de Captação (), Área é atendida pela Caesb() e Subsistema()!!!", ButtonType.OK));

				} else {

					sub.setInterTipoInterferenciaFK(tipoInterferencia);

					sub.setInterTipoOutorgaFK(tipoOutorga);
				
					sub.setInterSubtipoOutorgaFK(subtipoOutorga);
					
					sub.setInterTipoAtoFK(tipoAto);
					sub.setInterSituacaoProcessoFK(situacaoProcesso);

					sub.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

					if (dpDataPublicacao.getValue() == null) {
						sub.setInterDataPublicacao(null);}
					else {
						sub.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

					}

					if (dpDataVencimento.getValue() == null) {
						sub.setInterDataVencimento(null);}
					else {
						sub.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

					}

					sub.setInterNumeroAto(tfNumeroAto.getText());

					sub.setInterProcRenovacao(tfProcesoOutorga.getText());
					sub.setInterDespachoRenovacao(tfDespachoOutorga.getText());

					sub.setInterEnderecoFK(endereco);

					InterferenciaDao interferenciaDao = new InterferenciaDao ();

					interferenciaDao.salvaInterferencia(sub);	

					obsList.remove(sub);
					obsList.add(sub);

					modularBotoes ();

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Interferência salva com sucesso!", ButtonType.OK));


				}

			} // fim subterranea


			else if (tipoInterferencia.getTipoInterID() == 1) {

				Superficial sup = TabSuperficialController.tabSupCon.capturarSuperficial();

				if (
						sup == null

						) {

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Informe o Local de Captação e se há Caesb!!!", ButtonType.OK));
					}
					
					else if (sup.getSupLocalCaptacaoFK() == null 	||
								sup.getSupFormaCaptacaoFK() == null 	||
									sup.getSupMetodoIrrigacaoFK() == null  	||
										sup.getSupBarramento() == null			||
											sup.getSupCaesb() == null
							) {

						Alerta a = new Alerta ();
						a.alertar(new Alert(Alert.AlertType.ERROR,"Informe: Local de Captação, Forma de Captação, Método de Irrigação, Barramento e "
								+ "se a área é atendida pela Caesb()!!!", ButtonType.OK));
					
				} else {
			
					sup.setInterTipoInterferenciaFK(tipoInterferencia);
					sup.setInterTipoOutorgaFK(tipoOutorga);
					sup.setInterSubtipoOutorgaFK(subtipoOutorga);
					sup.setInterTipoAtoFK(tipoAto);
					sup.setInterSituacaoProcessoFK(situacaoProcesso);

					sup.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

					if (dpDataPublicacao.getValue() == null) {
						sup.setInterDataPublicacao(null);;}
					else {
						sup.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

					}

					if (dpDataVencimento.getValue() == null) {
						sup.setInterDataVencimento(null);}

					else {
						sup.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

					}

					sup.setInterNumeroAto(tfNumeroAto.getText());

					sup.setInterProcRenovacao(tfProcesoOutorga.getText());
					sup.setInterDespachoRenovacao(tfDespachoOutorga.getText());

					sup.setInterEnderecoFK(endereco);

					InterferenciaDao interferenciaDao = new InterferenciaDao ();
					interferenciaDao.salvaInterferencia(sup);

					obsList.remove(sup);
					obsList.add(sup);

					modularBotoes ();

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Interferência salva com sucesso!", ButtonType.OK));

				}

			} // fim superficial //

			else {

				Interferencia inter = new Interferencia();
		
				inter.setInterTipoInterferenciaFK(tipoInterferencia);
				inter.setInterTipoOutorgaFK(tipoOutorga);
				inter.setInterSubtipoOutorgaFK(subtipoOutorga);
				inter.setInterTipoAtoFK(tipoAto);
				inter.setInterSituacaoProcessoFK(situacaoProcesso);


				if (dpDataPublicacao.getValue() == null) {
					inter.setInterDataPublicacao(null);;}
				else {
					inter.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

				}

				if (dpDataVencimento.getValue() == null) {
					inter.setInterDataVencimento(null);}
				else {
					inter.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

				}

				inter.setInterNumeroAto(tfNumeroAto.getText());

				inter.setInterProcRenovacao(tfProcesoOutorga.getText());
				inter.setInterDespachoRenovacao(tfDespachoOutorga.getText());

				inter.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

				inter.setInterEnderecoFK(endereco);

				InterferenciaDao interferenciaDao = new InterferenciaDao ();
				interferenciaDao.salvaInterferencia(inter);

				obsList.remove(inter);
				obsList.add(inter);

				modularBotoes ();

				//-- Alerta de endereco salvo --//
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Interferência salva com sucesso!", ButtonType.OK));


			} // fim outras interferencias

		}

	}

	//-- botao editar --//
	public void editarInterferencia () {

		// habilitar os campos para edição //
		if (cbTipoOutorga.isDisable()) {

			// O tipo de interferência escolhido não pode ser editado
				//cbTipoInterferencia.setDisable(false);
			cbTipoOutorga.setDisable(false);
			cbSubtipoOutorga.setDisable(false);
			cbTipoAto.setDisable(false);
			cbSituacao.setDisable(false);

			dpDataPublicacao.setDisable(false);
			dpDataVencimento.setDisable(false);
			tfNumeroAto.setDisable(false);
			tfProcesoOutorga.setDisable(false);
			tfDespachoOutorga.setDisable(false);	

		}

		else {

			if (tipoInterferencia.getTipoInterID() == 2) {
				
				Subterranea sub = new Subterranea ();
				sub = TabSubterraneaController.tabSubCon.capturarSubterranea();

				if (sub.getSubTipoPocoFK() == null ||
						sub== null ||
								sub.getSubSubSistemaFK() == null
						) {

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Informe: Tipo de Captação (), Área é atendida pela Caesb() e Subsistema()!!!", ButtonType.OK));

				} else {

					
					sub.setInterTipoInterferenciaFK(tipoInterferencia);

					sub.setInterTipoOutorgaFK(tipoOutorga);
					
					sub.setInterSubtipoOutorgaFK(subtipoOutorga);
					sub.setInterTipoAtoFK(tipoAto);
					sub.setInterSituacaoProcessoFK(situacaoProcesso);


					if (dpDataPublicacao.getValue() == null) {
						sub.setInterDataPublicacao(null);;}
					else {
						sub.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

					}

					if (dpDataVencimento.getValue() == null) {
						sub.setInterDataVencimento(null);}
					else {
						sub.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

					}

					sub.setInterNumeroAto(tfNumeroAto.getText());

					sub.setInterProcRenovacao(tfProcesoOutorga.getText());
					sub.setInterDespachoRenovacao(tfDespachoOutorga.getText());

					sub.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

					if (sub.getInterEnderecoFK() == null) {
						
						sub.setInterEnderecoFK(endereco);
						
					}
					
					InterferenciaDao interferenciaDao = new InterferenciaDao ();

					// merge subterranea //
					interferenciaDao.mergeInterferencia(sub);

					obsList.remove(sub);
					obsList.add(sub);

					modularBotoes ();

					//-- Alerta de endereco salvo --//
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.INFORMATION, "Interferência editada com sucesso!", ButtonType.OK));

				}

			} // fim subterranea


			else if (tipoInterferencia.getTipoInterID() == 1) {
				
				Superficial sup = TabSuperficialController.tabSupCon.capturarSuperficial();

				if (sup.getSupLocalCaptacaoFK() == null // || 
						//	tabSupCon.obterSuperficial().getSupArea() == null

						) {

					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.ERROR, "Informe o Local de Captação e se há Caesb!!!", ButtonType.OK));

				} else {

					sup.setInterTipoInterferenciaFK(tipoInterferencia);

					sup.setInterTipoOutorgaFK(tipoOutorga);
				
					sup.setInterSubtipoOutorgaFK(subtipoOutorga);
					sup.setInterTipoAtoFK(tipoAto);
					sup.setInterSituacaoProcessoFK(situacaoProcesso);

					if (dpDataPublicacao.getValue() == null) {
						sup.setInterDataPublicacao(null);;}
					else {
						sup.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

					}

					if (dpDataVencimento.getValue() == null) {
						sup.setInterDataVencimento(null);}
					else {
						sup.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

					}

					sup.setInterNumeroAto(tfNumeroAto.getText());

					sup.setInterProcRenovacao(tfProcesoOutorga.getText());
					sup.setInterDespachoRenovacao(tfDespachoOutorga.getText());

					sup.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

					sup.setInterEnderecoFK(endereco);

					InterferenciaDao interferenciaDao = new InterferenciaDao ();
				
					// merge superficial e canal //
					interferenciaDao.mergeInterferencia(sup);

					obsList.remove(sup);
					obsList.add(sup);

					modularBotoes ();

					//-- Alerta de endereco salvo --//
					Alert a = new Alert (Alert.AlertType.INFORMATION);
					a.setTitle("Parabéns!");
					a.setContentText("Interferência editada com sucesso!");
					a.setHeaderText(null);
					a.show();

				}


			} // fim superficial //

			else {

				Interferencia inter= tvLista.getSelectionModel().getSelectedItem();

				inter.setInterTipoInterferenciaFK(tipoInterferencia);

				inter.setInterTipoOutorgaFK(tipoOutorga);
				inter.setInterSubtipoOutorgaFK(subtipoOutorga);
				inter.setInterTipoAtoFK(tipoAto);
				inter.setInterSituacaoProcessoFK(situacaoProcesso);

				if (dpDataPublicacao.getValue() == null) {
					inter.setInterDataPublicacao(null);;}
				else {
					inter.setInterDataPublicacao(Date.valueOf(dpDataPublicacao.getValue()));

				}

				if (dpDataVencimento.getValue() == null) {
					inter.setInterDataVencimento(null);}
				else {
					inter.setInterDataVencimento(Date.valueOf(dpDataVencimento.getValue()));

				}

				inter.setInterNumeroAto(tfNumeroAto.getText());

				inter.setInterProcRenovacao(tfProcesoOutorga.getText());
				inter.setInterDespachoRenovacao(tfDespachoOutorga.getText());

				inter.setIntAtualizacao(Timestamp.valueOf((LocalDateTime.now())));

				inter.setInterEnderecoFK(endereco);

				InterferenciaDao interferenciaDao = new InterferenciaDao ();

				//merge outras interferencias //
				interferenciaDao.mergeInterferencia(inter);

				obsList.remove(inter);
				obsList.add(inter);

				modularBotoes ();

				//-- Alerta de endereco salvo --//
				Alerta a = new Alerta ();
				a.alertar(new Alert(Alert.AlertType.INFORMATION, "Interferência editada com sucesso!", ButtonType.OK));


			} // fim outras interferencias

		}

	}

	public void excluirInterferencia () {

		try {

			Interferencia inter = tvLista.getSelectionModel().getSelectedItem();

			InterferenciaDao interferenciaDao = new InterferenciaDao ();

			interferenciaDao.removeInterferencia(inter.getInterID());

			// remover a interferencia da lista //
			obsList.remove(inter);

			modularBotoes ();

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro excluído!", ButtonType.OK));

		}

		catch (Exception e) {

			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao excluir o cadastro!!!", ButtonType.OK));
		}

	}

	String strPesquisa = "";

	public void pesquisarInterferencia () {

		strPesquisa = tfPesquisar.getText();

		listarInterferencias(strPesquisa);

		modularBotoes ();

		cbTipoInterferencia.getSelectionModel().clearSelection();

	}

	@FXML Pane pInterferencia;

	Pane p1 = new Pane();
	BorderPane bp1 = new BorderPane();
	BorderPane bp2 = new BorderPane();
	ScrollPane sp = new ScrollPane();

	/* array de posicoes prefWidth prefHeight Layout Y e X */
	Double prefSizeWHeLayXY [][];

	Componentes com;

	ControladorOutorga controladorOutorga;
	ControladorAtendimento controladorAtendimento;
	ControladorFiscalizacao controladorFiscalizacao;

	public TabInterferenciaControlador (ControladorOutorga controladorOutorga) {
		this.controladorOutorga = controladorOutorga;

	}
	
	public TabInterferenciaControlador (ControladorAtendimento controladorAtendimento) {
		this.controladorAtendimento = controladorAtendimento;

	}
	
	public TabInterferenciaControlador (ControladorFiscalizacao controladorFiscalizacao) {
		this.controladorFiscalizacao = controladorFiscalizacao;

	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		bp1.minWidthProperty().bind(pInterferencia.widthProperty());
		bp1.maxHeightProperty().bind(pInterferencia.heightProperty().subtract(60));

		bp1.getStyleClass().add("border-pane");

		bp2.setPrefHeight(800.0);
		bp2.minWidthProperty().bind(pInterferencia.widthProperty());

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		sp.setContent(bp2);

		bp1.setCenter(sp);

		pInterferencia.getChildren().add(bp1);

		p1.setMaxSize(1030.0, 1240.0);
		p1.setMinSize(1030.0, 1240.0);

		bp2.setTop(p1);
		BorderPane.setAlignment(p1, Pos.CENTER);

		inicializarComponentes ();

		tcLogradouro.setPrefWidth(445);
		tcTipoInterferencia.setPrefWidth(230);
		tcSituacao.setPrefWidth(230);

		tcTipoInterferencia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Interferencia, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Interferencia, String> i) {
				return new SimpleStringProperty(i.getValue().getInterTipoInterferenciaFK().getTipoInterDescricao());

			}
		});

		tcLogradouro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Interferencia, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Interferencia, String> i) {
				return new SimpleStringProperty(i.getValue().getInterEnderecoFK().getEndLogradouro());

			}
		});

		tcSituacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Interferencia, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Interferencia, String> i) {
				return new SimpleStringProperty(i.getValue().getInterSituacaoProcessoFK().getSituacaoProcessoDescricao());

			}
		});

		tvLista.getColumns().add(tcTipoInterferencia);
		tvLista.getColumns().add(tcLogradouro);
		tvLista.getColumns().add(tcSituacao);

		tvLista.setItems(obsList);

		tvLista.setPrefSize(930, 185);
		tvLista.setLayoutX(50);
		tvLista.setLayoutY(335);

		lblDataAtualizacao.setPrefSize(247, 22);
		lblDataAtualizacao.setLayoutX(730);
		lblDataAtualizacao.setLayoutY(530);

		p1.getChildren().addAll(lblDataAtualizacao, tvLista);

		cbTipoInterferencia.setItems(ListasComboBox.obsListTipoInterferencia);
		cbTipoOutorga.setItems(ListasComboBox.obsListTipoOutorga);
		cbSubtipoOutorga.setItems(ListasComboBox.obsListSubtipoOutorga);
		cbTipoAto.setItems(ListasComboBox.obsListTipoAto);
		cbSituacao.setItems(ListasComboBox.obsListSituacao);

		// capturar o id do tipo de interferencia, se 1 - superficial, 2 - subterranea etc 
		cbTipoInterferencia.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				tipoInterferencia.setTipoInterID((Integer) new_value + 1);
				//System.out.println("id " + tipoInterferencia.getTipoInterID() + " descrição " + tipoInterferencia.getTipoInterDescricao());
				
				try {
					abrirTabs(tipoInterferencia.getTipoInterID());
					} catch (IOException e) {
						System.out.println("erro ao abrirTabs" + e);
					}
			}
		});
		
		// capturar a descrição do tipo de interferência, se superficial, subterranea etc 
		cbTipoInterferencia.getSelectionModel().selectedItemProperty().addListener( 

				(ObservableValue<? extends String> observable, String old_value, String new_value) ->

				tipoInterferencia.setTipoInterDescricao(new_value)
				);

		// capturar o id tipo outorga
		cbTipoOutorga.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				tipoOutorga.setTipoOutorgaID((Integer )new_value + 1);

			}
		});
		
		cbTipoOutorga.getSelectionModel()
	    	.selectedItemProperty()
	    	.addListener( 
	    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
	    	// setar nome (descricao) da RA selecinada de acordo com a selecao no ComboBox
	    	tipoOutorga.setTipoOutorgaDescricao(new_value)
	    );
		 

		// capturar o id tipo outorga
		cbSubtipoOutorga.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				subtipoOutorga.setSubtipoOutorgaID((Integer )new_value + 1);
				if (subtipoOutorga.getSubtipoOutorgaID() == 0) 
					subtipoOutorga.setSubtipoOutorgaID(5);

			}
		});
		
		cbSubtipoOutorga.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    		// setar nome (descricao) da Subtipo_Outorga selecinada de acordo com a selecao no ComboBox
    	
    		subtipoOutorga.setSubtipoOutorgaDescricao(new_value)
    	);


		// capturar o id tipo ato 
		cbTipoAto.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {
				tipoAto.setTipoAtoID((Integer )new_value + 1);
		
			}
		});
		
		cbTipoAto.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    		// setar nome (descricao) da Tipo_Ato selecinada de acordo com a selecao no ComboBox
    		tipoAto.setTipoAtoDescricao(new_value)
    	);
		

		// capturar id tipo situação
		cbSituacao.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {
				situacaoProcesso.setSituacaoProcessoID((Integer )new_value + 1);
			}
		});
		
		cbSituacao.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    	// setar nome (descricao) da RA selecinada de acordo com a selecao no ComboBox
    	situacaoProcesso.setSituacaoProcessoDescricao(new_value)
    	);

		modularBotoes ();
		acionarBotoes ();
		selecionarInterferencia();


	} // FIM INITIALIZE 

	public void acionarBotoes () {

		btnNovo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				habilitarInterferencia();
			}
		});

		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				salvarInterferencia();

				//System.out.println("btnSalvar - metodo salvaInterferencia()");
			}
		});

		btnEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				editarInterferencia();
			}
		});

		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				cancelarInterferencia();
				subtipoOutorga.setSubtipoOutorgaID(5);
			}
		});

		btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pesquisarInterferencia();
			}
		});


		btnCoordenadasEndereco.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (endereco.getEndDDLatitude() != null) {
					ControladorPrincipal.capturarGoogleMaps().setMarkerPosition(endereco.getEndDDLatitude().toString(), endereco.getEndDDLongitude().toString());
					ControladorPrincipal.capturarGoogleMaps().setMapCenter(endereco.getEndDDLatitude().toString(), endereco.getEndDDLongitude().toString());
				}

			}
		});

		btnExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirInterferencia();
			}
		});
		

		btnEndereco.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

	            inicializarTelaEndereco();
	            telaEnderecoControlador.setObjetoDeEdicao(interferencia);
			}
		});
		

	}

	
	Pane pEndereco;

	Label lblEndereco;
	Button btnCoordenadasEndereco;
	Button btnEndereco;

	ArrayList<Node> listComponentesEndereco = new ArrayList<Node>();

	Pane pDadosInterferencia;

	ComboBox<String> cbTipoInterferencia;
	ComboBox<String> cbTipoOutorga;
	ComboBox<String> cbSubtipoOutorga;
	ComboBox<String> cbTipoAto;
	ComboBox<String> cbSituacao;

	ArrayList<Node> listComponentesInterferencia = new ArrayList<Node>();

	Pane pDI1;
	DatePicker dpDataPublicacao;
	DatePicker dpDataVencimento;
	TextField tfNumeroAto;

	ArrayList<Node> listComponentesInterferenciaInterno1 = new ArrayList<Node>();

	Pane pDI2;
	TextField tfProcesoOutorga;
	TextField tfDespachoOutorga;

	ArrayList<Node> listComponentesInterferenciaInterno2 = new ArrayList<Node>();


	Pane pPersistencia;
	Button btnNovo;
	Button btnSalvar;
	Button btnEditar;
	Button btnExcluir;
	Button btnCancelar;
	Button btnPesquisar;

	TextField tfPesquisar;

	ArrayList<Node> listComponentesPersistencia = new ArrayList<Node>();

	Pane pTipoInterferencia;  	

	public void inicializarComponentes () {

		listComponentesEndereco.add(pEndereco = new Pane());
		listComponentesEndereco.add(new Label("ENDERECO:"));
		listComponentesEndereco.add(lblEndereco = new Label());
		listComponentesEndereco.add(btnCoordenadasEndereco = new Button());
		listComponentesEndereco.add(btnEndereco = new Button("<<<"));

		prefSizeWHeLayXY = new Double [][] { 
			{950.0,60.0,40.0,10.0},
			{80.0,30.0,35.0,15.0},
			{690.0,30.0,115.0,16.0},
			{25.0,25.0,815.0,18.0},
			{65.0,25.0,850.0,18.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesEndereco, prefSizeWHeLayXY, p1);

		listComponentesInterferencia.add(pDadosInterferencia = new Pane());		    
		listComponentesInterferencia.add(new Label ("Tipo de Interferência:"));
		listComponentesInterferencia.add(cbTipoInterferencia = new ComboBox<>());
		listComponentesInterferencia.add(new Label ("Tipo de Outorga:"));
		listComponentesInterferencia.add(cbTipoOutorga = new ComboBox<>());
		listComponentesInterferencia.add(new Label ("Subtipo de Outorga:"));
		listComponentesInterferencia.add(cbSubtipoOutorga = new ComboBox<>());
		listComponentesInterferencia.add(new Label ("Tipo de Ato:"));
		listComponentesInterferencia.add(cbTipoAto = new ComboBox<>());

		prefSizeWHeLayXY = new Double [][] { 
			{930.0,165.0,50.0,85.0},
			{260.0,30.0,14.0,11.0},
			{260.0,30.0,13.0,41.0},
			{205.0,30.0,283.0,10.0},
			{205.0,30.0,283.0,40.0},
			{205.0,30.0,498.0,10.0},
			{205.0,30.0,498.0,41.0},
			{205.0,30.0,713.0,11.0},
			{205.0,30.0,713.0,41.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesInterferencia, prefSizeWHeLayXY, p1);

		listComponentesInterferenciaInterno1.add(pDI1 = new Pane());
		listComponentesInterferenciaInterno1.add(new Label ("Situação:"));
		listComponentesInterferenciaInterno1.add(cbSituacao = new ComboBox<>());
		listComponentesInterferenciaInterno1.add(new Label ("Data de Publicação:"));
		listComponentesInterferenciaInterno1.add(dpDataPublicacao = new DatePicker());
		listComponentesInterferenciaInterno1.add(new Label ("Data de Vencimento:"));
		listComponentesInterferenciaInterno1.add(dpDataVencimento = new DatePicker());
		listComponentesInterferenciaInterno1.add(new Label ("Número do Ato:"));
		listComponentesInterferenciaInterno1.add(tfNumeroAto = new TextField());

		prefSizeWHeLayXY = new Double [][] { 
			{580.0,80.0,11.0,79.0},
			{170.0,30.0,11.0,10.0},
			{170.0,30.0,11.0,40.0},
			{130.0,30.0,191.0,10.0},
			{130.0,30.0,191.0,40.0},
			{130.0,30.0,331.0,10.0},
			{130.0,30.0,331.0,40.0},
			{100.0,30.0,471.0,10.0},
			{100.0,30.0,471.0,40.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesInterferenciaInterno1, prefSizeWHeLayXY, pDadosInterferencia);

		listComponentesInterferenciaInterno2.add(pDI1 = new Pane());	
		listComponentesInterferenciaInterno2.add(new Label ("Processo de Outorga:"));
		listComponentesInterferenciaInterno2.add(tfProcesoOutorga = new TextField());
		listComponentesInterferenciaInterno2.add(new Label ("Depacho de outorga:"));
		listComponentesInterferenciaInterno2.add(tfDespachoOutorga = new TextField());

		prefSizeWHeLayXY = new Double [][] { 
			{290.0,80.0,628.0,79.0},
			{130.0,30.0,10.0,10.0},
			{130.0,30.0,10.0,40.0},
			{130.0,30.0,150.0,10.0},
			{130.0,30.0,150.0,40.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesInterferenciaInterno2, prefSizeWHeLayXY, pDadosInterferencia);

		listComponentesPersistencia.add(pPersistencia = new Pane());
		listComponentesPersistencia.add(btnNovo = new Button("NOVO"));
		listComponentesPersistencia.add(btnSalvar = new Button("SALVAR"));
		listComponentesPersistencia.add(btnEditar = new Button("EDITAR"));
		listComponentesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
		listComponentesPersistencia.add(btnCancelar = new Button("CANCELAR"));
		listComponentesPersistencia.add(tfPesquisar = new TextField());
		listComponentesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		prefSizeWHeLayXY = new Double[][] { 
			{930.0,60.0,50.0,260.0},
			{95.0,25.0,18.0,18.0},
			{95.0,25.0,123.0,18.0},
			{95.0,25.0,228.0,18.0},
			{95.0,25.0,333.0,18.0},
			{95.0,25.0,438.0,18.0},
			{265.0,25.0,543.0,18.0},
			{95.0,25.0,818.0,18.0},
		};

		com = new Componentes();
		com.popularTela(listComponentesPersistencia, prefSizeWHeLayXY, p1);    
		

		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});
		
		pTipoInterferencia = new Pane();

		pTipoInterferencia.setPrefSize(950, 500);
		pTipoInterferencia.setLayoutX(40.0);
		pTipoInterferencia.setLayoutY(560.0);
	
		p1.getChildren().add(pTipoInterferencia);

	}

	public void cancelarInterferencia () {

		modularBotoes ();

		cbTipoInterferencia.getSelectionModel().clearSelection();
		pTipoInterferencia.getChildren().clear();
		
		cbTipoOutorga.getSelectionModel().clearSelection();
		cbSubtipoOutorga.getSelectionModel().clearSelection();
		cbTipoAto.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		
		dpDataPublicacao.setValue(null);
		dpDataVencimento.setValue(null);
		tfNumeroAto.setText("");
		tfProcesoOutorga.setText("");
		tfDespachoOutorga.setText("");
		
	}

	public void abrirTabs (int ti) throws IOException {

		if (ti == 1) {

			pTipoInterferencia.getChildren().clear();
			Pane pane = new Pane();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TabSuperficial.fxml")); 
			loader.setRoot(pane);
			loader.setController(new TabSuperficialController());
			loader.load();

			pTipoInterferencia.getChildren().add(pane);

		}

		else if (ti == 2) {

			pTipoInterferencia.getChildren().clear();
			Pane pane = new Pane();
			tabSubCon = new TabSubterraneaController();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/TabSubterranea.fxml")); 
			loader.setRoot(pane);
			loader.setController(new TabSubterraneaController());
			loader.load();
			pTipoInterferencia.getChildren().add(pane);

		}

		else {

			pTipoInterferencia.getChildren().clear();

		}

	}
	

	// --- método para listar interferencias --- //
	public void listarInterferencias (String strPesquisa) {

		// --- conexao - listar enderecos --- //
		InterferenciaDao interferenciaDao = new InterferenciaDao();
		List<Interferencia> interferenciaList = null;

		try {
			interferenciaList = interferenciaDao.listInterferencia(strPesquisa);
		} catch (Exception e) {
			System.out.println("erro ao listar as interferências!");
			e.printStackTrace();
		}

		if (!obsList.isEmpty()) {
			obsList.clear();
		}

		// preencher a observable lista para a table view //
		for (Interferencia i : interferenciaList) {

			i.getInterID();

			i.getInterTipoInterferenciaFK();
			i.getInterBaciaFK();
			i.getInterUHFK();
			i.getInterTipoOutorgaFK();
			i.getInterTipoAtoFK();

			i.getInterDDLatitude();
			i.getInterDDLongitude();

			i.getInterSituacaoProcessoFK();
			i.getInterDataPublicacao();
			i.getInterDataVencimento();
			i.getInterNumeroAto();

			obsList.add(i);

		}

		tvLista.setItems(obsList); 

	}

	// metodo selecionar interferencia -- //
	public void selecionarInterferencia () {

		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {

				Interferencia inter = (Interferencia) newValue;

				if (inter == null) {

					btnNovo.setDisable(true);
					btnSalvar.setDisable(true);

					btnEditar.setDisable(false);
					btnExcluir.setDisable(false);
					btnCancelar.setDisable(false);

				} else {


					setEndereco(inter.getInterEnderecoFK());
						
					
					// O tipo de interferência escolhido não pode ser editado
					cbTipoInterferencia.setDisable(true);

					cbTipoInterferencia.setValue(inter.getInterTipoInterferenciaFK().getTipoInterDescricao());

					cbTipoOutorga.setValue(inter.getInterTipoOutorgaFK().getTipoOutorgaDescricao());
					cbSubtipoOutorga.setValue(inter.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao());
					cbTipoAto.setValue(inter.getInterTipoAtoFK().getTipoAtoDescricao());
					cbSituacao.setValue(inter.getInterSituacaoProcessoFK().getSituacaoProcessoDescricao());
					
					// setar as tabelas relacionadas
						tipoInterferencia.setTipoInterID(inter.getInterTipoInterferenciaFK().getTipoInterID());
						tipoInterferencia.setTipoInterDescricao(inter.getInterTipoInterferenciaFK().getTipoInterDescricao());
						
						tipoOutorga.setTipoOutorgaID(inter.getInterTipoOutorgaFK().getTipoOutorgaID());
						tipoOutorga.setTipoOutorgaDescricao(inter.getInterTipoOutorgaFK().getTipoOutorgaDescricao());
						
						subtipoOutorga.setSubtipoOutorgaID(inter.getInterSubtipoOutorgaFK().getSubtipoOutorgaID());
						subtipoOutorga.setSubtipoOutorgaDescricao(inter.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao());
						
						tipoAto.setTipoAtoID(inter.getInterTipoAtoFK().getTipoAtoID());
						tipoAto.setTipoAtoDescricao(inter.getInterTipoAtoFK().getTipoAtoDescricao());
						
						situacaoProcesso.setSituacaoProcessoID(inter.getInterSituacaoProcessoFK().getSituacaoProcessoID());
						situacaoProcesso.setSituacaoProcessoDescricao(inter.getInterSituacaoProcessoFK().getSituacaoProcessoDescricao());

						if (inter.getInterDataPublicacao() == null) {
							dpDataPublicacao.setValue(null);
						} else {
							Date dPub = inter.getInterDataPublicacao();
							dpDataPublicacao.setValue(dPub.toLocalDate());
						}
					
						if (inter.getInterDataVencimento() == null) {
							dpDataVencimento.setValue(null);
						} else {
							Date dVen = inter.getInterDataVencimento();
							dpDataVencimento.setValue(dVen.toLocalDate());
						}

						
					tfNumeroAto.setText(inter.getInterNumeroAto());
					tfProcesoOutorga.setText(inter.getInterProcRenovacao());
					tfDespachoOutorga.setText(inter.getInterDespachoRenovacao());

					// mostrar data de atualizacao //
					FormatoData d = new FormatoData();
					try {lblDataAtualizacao.setText("Data de Atualização: " + d.formatarData(inter.getIntAtualizacao()));
					lblDataAtualizacao.setTextFill(Color.BLACK);
					//System.out.println("teste data atualizacao black");
					}catch (Exception e) {
						lblDataAtualizacao.setText("Não há data de atualização!");
						lblDataAtualizacao.setTextFill(Color.RED);
						//System.out.println("teste data atualizacao red");
					}

					setEndereco(inter.getInterEnderecoFK());
					
					// para exportar esta interferencia para a TelaEndereco
					interferencia = inter;
					
					if (tipoInterferencia.getTipoInterID() == 2) {

						try {
							abrirTabs (tipoInterferencia.getTipoInterID());
						} catch (IOException e) {

							e.printStackTrace();
						}

						TabSubterraneaController.tabSubCon.imprimirSubterranea(((Subterranea) inter));


					}

					if (tipoInterferencia.getTipoInterID() == 1 || tipoInterferencia.getTipoInterID()  == 4) {

						try {
							abrirTabs (tipoInterferencia.getTipoInterID());
						} catch (IOException e) {

							e.printStackTrace();
						}

						TabSuperficialController.tabSupCon.imprimirSuperficial(((Superficial) inter));

					}

				}

				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);

			}
		});
	}

	public void modularBotoes () {

		cbTipoInterferencia.setDisable(true);

		cbTipoOutorga.setDisable(true);
		cbSubtipoOutorga.setDisable(true);
		cbTipoAto.setDisable(true);
		cbSituacao.setDisable(true);

		dpDataPublicacao.setDisable(true);
		dpDataVencimento.setDisable(true);
		tfNumeroAto.setDisable(true);
		tfProcesoOutorga.setDisable(true);
		tfDespachoOutorga.setDisable(true);

		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);

		btnNovo.setDisable(false);
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
			
					// TabDocumento = 0 TabInterferencia = 1
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
	  

}

/*
cbTipoOutorga.setCellFactory(
        new Callback<ListView<String>, ListCell<String>>() {

        	@Override public ListCell<String> call(ListView<String> param) {

                final ListCell<String> cell = new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }    
            @Override public void updateItem(String item, 
                boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);    
                        if (		item.equals("Outorga") 
                        		|| 	item.equals("Renovação de Outorga") 
                        		|| 	item.equals("Transferência de Outorga") 
                        		|| 	item.equals("Revogação de Outorga") 
                        		|| 	item.equals("Suspensão de Outorga")
                        		) {
                        	setTextFill(Color.BLUE);
                        }

                        else if (	item.equals("Outorga Prévia") 
                        		|| 	item.equals("Renovação de Outorga Prévia") 
                        		|| 	item.equals("Transferência de Outorga Prévia") 
                        		|| 	item.equals("Revogação de Outorga Prévia") 
                        		|| 	item.equals("Suspensão de Outorga Prévia")
                        		) {
                            setTextFill(Color.CHOCOLATE);
                        }

                        else {
                            setTextFill(Color.DIMGRAY);
                        }
                    }
                } // fim metodo updateItem
            };

            return cell;

        } // fim metodo call
    });
 */

//Button btnEndCoord;
//Image imgEndCoord = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));

//Button btnEndCoordMap = new Button();
//Image imgEndCoordMap = new Image(TabVistoriaController.class.getResourceAsStream("/images/mapCoord.png"));

//Image imgGetCoord = new Image(TabEnderecoController.class.getResourceAsStream("/images/getCoord.png")); 


