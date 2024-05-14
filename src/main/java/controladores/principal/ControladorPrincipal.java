package controladores.principal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import controladores.modelosHTML.ControladorModelosHTML;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import mapas.GoogleMap;
import principal.ListasComboBox;
import util.Registro;

/** Casse controlador da classe principal, onde serao chamados as telas necessarias para os cadastros
 * 
 * @author fabricio.barrozo
 *
 */

public class ControladorPrincipal {

	@FXML AnchorPane apPrincipal;

	Button btnTerrain;
	Button btnRoadMap;
	Button btnSattelite;
	Button btnHybrid;

	Button btnZoomOut;
	Button btnZoomIn;

	Button btnNightMap;
	Button btnBlueMap;
	Button btnGreenMap;
	Button btnRetroMap;

	CheckBox checkBacia, checkUnidadesHidrograficas, checkRiodDF, checkRiosUniao, checkFraturado, checkPoroso, checkUTM, checkTrafego;
	
	TabPane tpLateralDireita = new TabPane();
	TabPane tpLateralEsquera = new TabPane();

	Tab tabLD1 = new Tab();
	Tab tabLD2 = new Tab();

	Tab tabLE1 = new Tab();
	Tab tabLE2 = new Tab();

	Pane pLD1 = new Pane();
	Pane pLD2 = new Pane();
	Pane pLE1 = new Pane();
	Pane pLE2 = new Pane();

	StackPane spCentroTabPanes = new  StackPane();
	StackPane spConversorCoordenadas = new  StackPane();

	Pane paneConversor = new Pane();
	Pane pConvCentral = new Pane();

	Pane pSuperior = new Pane();

	Pane pNavegador;
	Pane pFiscalizacao;
	Pane pAtendimento;
	Pane pOutorga;
	
	Pane pBancosDiversos;
	

	Button btnHome;
	Button btnMapa;
	Button btnConversor;
	Button btnAtendimento;
	Button btnOutorga;
	Button btnFiscalizacao;
	Button btnNavegador;
	Button btnEditorHTML;
	Button btnBancoAccess;

	static GoogleMap googleMaps;

	ObservableList<String> olcbMainConverteCoord;

	Double dblPesquisa, dblNavegador, dblFiscalizacao, dblAtendimento, dblOutorga, dblBancoAccess;

	TranslateTransition baixarTabPaneFiscalizacao;
	TranslateTransition subirTabPaneFiscalizacao;

	TranslateTransition subirTabPaneAtendimento;
	TranslateTransition baixarTabPaneAtendimento;

	TranslateTransition downSearch;
	TranslateTransition upSearch;

	TranslateTransition baixarNavegador;
	TranslateTransition subirNavegador;

	TranslateTransition baixarTabPaneOutorga;
	TranslateTransition subirTabPaneOutorga;
	
	TranslateTransition ttBaixaBancoAccess;
	TranslateTransition ttSobeBancoAccess;
	
	ListasComboBox listasComboBox;
	

	/** Metodo para capturar as coordenadas clicadas no mapa principal
	 * 
	 * @return Double latitude (capturarGoogleMaps().getLat()) e Double longitude (capturarGoogleMaps().getLat())
	 */
	public static GoogleMap capturarGoogleMaps () {
		return googleMaps;
	}

	TextField tfLatDD;
	TextField tfLonDD;
	TextField tfZonaUTM;

	/** Labels staticos pois serao utilizados pela classe GoogleMap
	 * 
	 */
	public static Label lblCoord1;
	public static Label lblCoord2;

	public static Label lblDD;
	public static Label lblDMS;
	public static Label lblUTM;

	ComboBox<String> cbConverterCoord = new ComboBox<>();

	Button btnConverteCoord = new Button("BUSCAR");

	ComboBox<String> cbNorteSul;
	ObservableList<String> cbNorteSulOpcoes;

	ComboBox<String> cbLesteOeste;
	ObservableList<String> cbLesteOesteOpcoes;

	Pane pWebMap = new Pane();

	ScrollPane spWebBrowser;
	WebView wBrowser;
	
	ControladorOutorga controladorOutorga;
	ControladorAtendimento controladorAtendimento;
	ControladorFiscalizacao controladorFiscalizacao;


	@FXML 
	private void initialize() {

		Platform.runLater(new Runnable(){

			public void run() {

				googleMaps = new GoogleMap();


				pWebMap.getChildren().add(googleMaps); 

				pWebMap.widthProperty().addListener(
						(observable, oldValue, newValue) -> {
							googleMaps.resizeWidthMap ((Double)newValue);

						}
						);

				pWebMap.heightProperty().addListener(
						(observable, oldValue, newValue) -> {
							googleMaps.resizeHeightMap((Double)newValue) ;

						}
						);

				AnchorPane.setTopAnchor(pWebMap, 0.0);
				AnchorPane.setLeftAnchor(pWebMap , 0.0);
				AnchorPane.setRightAnchor(pWebMap , 0.0);
				AnchorPane.setBottomAnchor(pWebMap, 0.0);
				
				// inicializa o estilo do mapa de acordo com o que ler no arquivo properties
				apPrincipal.getStylesheets().add(estilizarMapa());

			}

		});

		pLE1.setPrefSize(140, 370);
		pLE2.setPrefSize(140, 370);

		tabLE1.setContent(pLE1);
		tabLE2.setContent(pLE2);

		pLD1.setPrefSize(140, 370);
		pLD2.setPrefSize(140, 370);


		tabLD1.setContent(pLD1);
		tabLD2.setContent(pLD2);

		tpLateralEsquera.getTabs().addAll(tabLE1, tabLE2);
		tpLateralDireita.getTabs().addAll(tabLD1, tabLD2);

		AnchorPane.setTopAnchor(tpLateralEsquera, 200.0);
		AnchorPane.setLeftAnchor(tpLateralEsquera, 10.0);

		AnchorPane.setTopAnchor(tpLateralDireita, 200.0);
		AnchorPane.setRightAnchor(tpLateralDireita, 10.0);

		pSuperior.setPrefSize(600, 25);
		pSuperior.getStyleClass().add("pane-superior");

		AnchorPane.setTopAnchor(pSuperior, 0.0);
		AnchorPane.setRightAnchor(pSuperior, 0.0);
		AnchorPane.setLeftAnchor(pSuperior, 0.0);


		AnchorPane.setTopAnchor(spCentroTabPanes, 100.0);
		AnchorPane.setRightAnchor(spCentroTabPanes, 110.0);
		AnchorPane.setLeftAnchor(spCentroTabPanes, 110.0);
		AnchorPane.setBottomAnchor(spCentroTabPanes, 105.0);

		spCentroTabPanes.setDisable(true);
		StackPane.setAlignment(spCentroTabPanes, Pos.TOP_CENTER);

		paneConversor.setMinSize(1050, 100);
		paneConversor.setMaxSize(1050, 100);

		cbConverterCoord.setPrefSize(100, 30);
		cbConverterCoord.setLayoutX(11);
		cbConverterCoord.setLayoutY(53);
		cbConverterCoord.getStyleClass().add("classePressedHover");

		pConvCentral.setPrefSize(829, 40);
		pConvCentral.setLayoutX(108);
		pConvCentral.setLayoutY(48);

		btnConverteCoord.setPrefSize(100, 30);
		btnConverteCoord.setLayoutX(937);
		btnConverteCoord.setLayoutY(53);
		btnConverteCoord.getStyleClass().add("classePressedHover");


		paneConversor.getChildren().addAll(cbConverterCoord, pConvCentral, btnConverteCoord); 

		olcbMainConverteCoord = FXCollections.observableArrayList(
				" DD ",
				" DMS ",
				" UTM "
				); 

		AnchorPane.setRightAnchor(spConversorCoordenadas, 0.0);
		AnchorPane.setLeftAnchor(spConversorCoordenadas, 0.0);
		AnchorPane.setBottomAnchor(spConversorCoordenadas, 15.0);

		spConversorCoordenadas.getChildren().add(paneConversor);
		spConversorCoordenadas.setDisable(false);

		StackPane.setAlignment(paneConversor, Pos.TOP_CENTER);


		inicializarConversorCoordenadas();
		inicializarTabLateralEsquerda();
		inicializarTabLateralDireita();

		apPrincipal.getChildren().addAll( pWebMap, tpLateralEsquera, tpLateralDireita, pSuperior,spCentroTabPanes, spConversorCoordenadas);



		
	}
	
	/** Metodo de inicializacao do conversor de coordenadas
	 * 
	 */
	private void inicializarConversorCoordenadas () {

		olcbMainConverteCoord = FXCollections.observableArrayList(
				" DD ",
				" DMS ",
				" UTM "
				); 

		cbConverterCoord.setItems(olcbMainConverteCoord);

		cbConverterCoord.setValue(" DD ");

		cbConverterCoord.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {  

				if (newValue == " DD ") {

					tfLatDD = new TextField("-15");
					tfLatDD.setPrefSize(193.0, 30.0);
					tfLatDD.setLayoutX(11.0); 
					tfLatDD.setLayoutY(6.0);

					tfLonDD = new TextField("-47"); 
					tfLonDD.setPrefSize(193.0, 30.0);
					tfLonDD.setLayoutX(216.0); 
					tfLonDD.setLayoutY(6.0);

					lblCoord1 = new Label();
					lblCoord1.setAlignment(Pos.CENTER); 
					lblCoord1.setPrefSize(193.0, 30.0);
					lblCoord1.setLayoutX(421.0);
					lblCoord1.setLayoutY(6.0);
					lblCoord1.setStyle("-fx-background-color: white;");

					lblCoord2 = new Label();
					lblCoord2.setAlignment(Pos.CENTER);
					lblCoord2.setPrefSize(193.0, 30.0);
					lblCoord2.setLayoutX(626.0); 
					lblCoord2.setLayoutY(6.0);
					lblCoord2.setStyle("-fx-background-color: white;");

					pConvCentral.getChildren().clear();
					pConvCentral.getChildren().addAll(tfLatDD, tfLonDD, lblCoord1, lblCoord2 );

					btnConverteCoord.setOnAction((ActionEvent evt)->{

						String typeCoordinate = cbConverterCoord.getValue();

						googleMaps.convDD (typeCoordinate, tfLatDD.getText(), tfLonDD.getText());

						copiarCoord (lblCoord1);
						copiarCoord (lblCoord2);

					});

				}

				if (newValue == " UTM ") {

					cbNorteSulOpcoes = FXCollections.observableArrayList(
							"N",
							"S"
							);

					cbNorteSul = new ComboBox<>(cbNorteSulOpcoes);
					cbNorteSul.setValue("S");

					cbNorteSul.setPrefSize(65.0, 30.0);
					cbNorteSul.setLayoutX(11.0);
					cbNorteSul.setLayoutY(6.0);
					cbNorteSul.getStyleClass().add("classePressedHover");

					tfZonaUTM = new TextField("23"); 
					tfZonaUTM.setPrefSize(65.0, 30.0);
					tfZonaUTM.setLayoutX(88.0); 
					tfZonaUTM.setLayoutY(6.0);

					tfLatDD = new TextField("284947"); 
					tfLatDD.setPrefSize(150.0, 30.0);
					tfLatDD.setLayoutX(165.0); 
					tfLatDD.setLayoutY(6.0);

					tfLonDD = new TextField("8340702"); 
					tfLonDD.setPrefSize(150, 30);
					tfLonDD.setLayoutX(326.0); 
					tfLonDD.setLayoutY(6.0);

					lblCoord1 = new Label(); 
					lblCoord1.setAlignment(Pos.CENTER);
					lblCoord1.setPrefSize(160, 30);
					lblCoord1.setLayoutX(487.0);
					lblCoord1.setLayoutY(6.0);
					lblCoord1.setStyle("-fx-background-color: white;");

					lblCoord2 = new Label();
					lblCoord2.setAlignment(Pos.CENTER);
					lblCoord2.setPrefSize(160, 30);
					lblCoord2.setLayoutX(658.0); 
					lblCoord2.setLayoutY(6.0);
					lblCoord2.setStyle("-fx-background-color: white;");

					pConvCentral.getChildren().clear();
					pConvCentral.getChildren().addAll(cbNorteSul, tfZonaUTM, tfLatDD, tfLonDD, lblCoord1, lblCoord2 );

					btnConverteCoord.setOnAction((ActionEvent evt)->{

						String typeCoordinate = cbConverterCoord.getValue();
						String utmLatLon = tfZonaUTM.getText() + " " + cbNorteSul.getValue() + " " + tfLatDD.getText() + " " + tfLonDD.getText();

						googleMaps.convUTM(typeCoordinate, utmLatLon);
						copiarCoord(lblCoord1);
						copiarCoord(lblCoord2);

					});
				}

				if (newValue == " DMS ") {

					cbNorteSulOpcoes = FXCollections.observableArrayList(
							"N",
							"S"
							);


					cbNorteSul = new ComboBox<>(cbNorteSulOpcoes);
					cbNorteSul.setValue("S");

					cbNorteSul.setPrefSize(65.0, 30.0);
					cbNorteSul.setLayoutX(173.0);
					cbNorteSul.setLayoutY(6.0);
					cbNorteSul.getStyleClass().add("classePressedHover");


					cbLesteOesteOpcoes = FXCollections.observableArrayList(
							"E",
							"W"
							);

					cbLesteOeste = new ComboBox<>(cbLesteOesteOpcoes);
					cbLesteOeste.setValue("W");

					cbLesteOeste.setPrefSize(65.0, 30.0); 
					cbLesteOeste.setLayoutX(410.0);
					cbLesteOeste.setLayoutY(6.0);
					cbLesteOeste.getStyleClass().add("classePressedHover");


					tfLatDD = new TextField("15 00 00");
					tfLatDD.setPrefSize(150.0, 30.0);
					tfLatDD.setLayoutX(12.0); 
					tfLatDD.setLayoutY(6.0);

					tfLonDD = new TextField("47 00 00");
					tfLonDD.setPrefSize(150, 30);
					tfLonDD.setLayoutX(249.0); 
					tfLonDD.setLayoutY(6.0);

					lblCoord1 = new Label(); 
					lblCoord1.setAlignment(Pos.CENTER);
					lblCoord1.setPrefSize(160, 30);
					lblCoord1.setLayoutX(487.0);
					lblCoord1.setLayoutY(6.0);
					lblCoord1.setStyle("-fx-background-color: white;");

					lblCoord2 = new Label();
					lblCoord2.setAlignment(Pos.CENTER);
					lblCoord2.setPrefSize(160, 30);
					lblCoord2.setLayoutX(657.0); 
					lblCoord2.setLayoutY(6.0);
					lblCoord2.setStyle("-fx-background-color: white;");

					pConvCentral.getChildren().clear();
					pConvCentral.getChildren().addAll(tfLatDD, cbNorteSul,tfLonDD, cbLesteOeste, lblCoord1, lblCoord2 );

					btnConverteCoord.setOnAction((ActionEvent evt)->{

						String typeCoordinate = cbConverterCoord.getValue();

						String lat = tfLatDD.getText() + " " + cbNorteSul.getValue();
						String lon = tfLonDD.getText() + " " + cbLesteOeste.getValue();

						googleMaps.convDMS (typeCoordinate, lat, lon);
						copiarCoord(lblCoord1);
						copiarCoord(lblCoord2);

					});
				}


			}    
		});


		tfLatDD = new TextField("-15");
		tfLatDD.setPrefSize(193.0, 30.0);
		tfLatDD.setLayoutX(11.0); 
		tfLatDD.setLayoutY(6.0);

		tfLonDD = new TextField("-47");
		tfLonDD.setPrefSize(193.0, 30.0);
		tfLonDD.setLayoutX(216.0); 
		tfLonDD.setLayoutY(6.0);

		lblCoord1 = new Label();
		lblCoord1.setAlignment(Pos.CENTER);
		lblCoord1.setPrefSize(193.0, 30.0);
		lblCoord1.setLayoutX(421.0);
		lblCoord1.setLayoutY(6.0);
		lblCoord1.setStyle("-fx-background-color: white;");

		lblCoord2 = new Label();
		lblCoord2.setAlignment(Pos.CENTER);
		lblCoord2.setPrefSize(193.0, 30.0);
		lblCoord2.setLayoutX(626.0); 
		lblCoord2.setLayoutY(6.0);
		lblCoord2.setStyle("-fx-background-color: white;");

		pConvCentral.getChildren().clear();
		pConvCentral.getChildren().addAll(tfLatDD, tfLonDD, lblCoord1, lblCoord2 );

		lblDD = new Label();
		lblDD.setPrefSize(246.0, 30.0);
		lblDD.setLayoutX(126.0); 
		lblDD.setLayoutY(13.0);
		lblDD.setAlignment(Pos.CENTER); 
		lblDD.setStyle("-fx-background-color: white;");

		lblDMS = new Label();
		lblDMS.setPrefSize(246.0, 30.0);
		lblDMS.setLayoutX(401.0); 
		lblDMS.setLayoutY(13.0);
		lblDMS.setAlignment(Pos.CENTER); 
		lblDMS.setStyle("-fx-background-color: white;");

		lblUTM = new Label();
		lblUTM.setPrefSize(246.0, 30.0);
		lblUTM.setLayoutX(676.0); 
		lblUTM.setLayoutY(13.0);
		lblUTM.setAlignment(Pos.CENTER); 
		lblUTM.setStyle("-fx-background-color: white;");

		copiarCoord (lblDD);
		copiarCoord (lblDMS);
		copiarCoord (lblUTM);

		paneConversor.getChildren().addAll(lblDD, lblDMS, lblUTM);

		btnConverteCoord.setOnAction((ActionEvent evt)->{

			String typeCoordinate = cbConverterCoord.getValue();

			googleMaps.convDD (typeCoordinate, tfLatDD.getText(), tfLonDD.getText());

			copiarCoord (lblCoord1);
			copiarCoord (lblCoord2);

		});
	}

	/** Metodo de inicalizacao da Tab Lateral Esquerda
	 * 
	 */
	private void inicializarTabLateralEsquerda () {

		btnHome = new Button("HOME");

		btnHome.getStyleClass().add("button-lateral");
		btnHome.setLayoutX(10.0);
		btnHome.setLayoutY(16.0);

		btnNavegador = new Button("NAVEGADOR");

		btnNavegador.getStyleClass().add("button-lateral");
		btnNavegador.setLayoutX(10.0);
		btnNavegador.setLayoutY(55.0);

		btnAtendimento = new Button("ATENDIMENTO");
		btnAtendimento.getStyleClass().add("button-lateral");
		btnAtendimento.setLayoutX(10.0);
		btnAtendimento.setLayoutY(94.0);

		btnOutorga = new Button("OUTORGA");
		btnOutorga.getStyleClass().add("button-lateral");
		btnOutorga.setLayoutX(10.0);
		btnOutorga.setLayoutY(133.0);

		btnFiscalizacao = new Button("FISCALIZACÃO");

		btnFiscalizacao.getStyleClass().add("button-lateral");
		btnFiscalizacao.setLayoutX(10.0);
		btnFiscalizacao.setLayoutY(172.0);

		btnEditorHTML = new Button("EDITOR HTML");

		btnEditorHTML.getStyleClass().add("button-lateral");
		btnEditorHTML.setLayoutX(10.0);
		btnEditorHTML.setLayoutY(211.0);


		btnConversor = new Button("CONVERSOR");

		btnConversor.getStyleClass().add("button-lateral");
		btnConversor.setLayoutX(10.0);
		btnConversor.setLayoutY(250.0);
	
		btnBancoAccess = new Button("BANCOS");

		btnBancoAccess.getStyleClass().add("button-lateral");
		btnBancoAccess.setLayoutX(10.0);
		btnBancoAccess.setLayoutY(289.0);

		Text iconTabHome = GlyphsDude.createIcon(FontAwesomeIcon.HOME, "20px");
		//iconTabHome.getStyleClass().add("classeIconTab");


		tabLE1.setGraphic(iconTabHome);
		tabLE1.setClosable(false);

		Text iconTabHome2 = GlyphsDude.createIcon(FontAwesomeIcon.CROP, "20px");
		//iconTabHome2.getStyleClass().add("classeIconTab");

		tabLE2.setGraphic(iconTabHome2);
		tabLE2.setClosable(false);

		pLE1.getStyleClass().add("pane-lateral");

		pLE2.getStyleClass().add("pane-lateral");

		pLE1.getChildren().addAll(btnHome, btnNavegador, btnAtendimento, btnOutorga, btnFiscalizacao, btnConversor, btnEditorHTML, btnBancoAccess);

		tabLE1.setContent(pLE1);
		tabLE2.setContent(pLE2);
		//aaqq


		// traslalte transition do conversor de coordenadas, para sumir deslizando para baixo
		downSearch = new TranslateTransition(new Duration(350), (spConversorCoordenadas));
		downSearch.setToY(130.0);
		upSearch = new TranslateTransition(new Duration(350), spConversorCoordenadas);
		upSearch.setToY(2.0);

		btnAtendimento.setOnAction((ActionEvent evt)->{
			
			/*
			 * preencher os combobox das tabelas relacionadas
			 */
			if (listasComboBox == null) {
				listasComboBox = new ListasComboBox();
					listasComboBox.preencherListasComboBox();
			}

			if (pAtendimento == null) {

				pAtendimento = new Pane();

				baixarTabPaneAtendimento = new TranslateTransition(new Duration(350), pAtendimento);
				baixarTabPaneAtendimento.setToY(880.0);
				subirTabPaneAtendimento = new TranslateTransition(new Duration(350), pAtendimento);
				subirTabPaneAtendimento.setToY(0.0);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/atendimento/Atendimento.fxml"));

				loader.setRoot(pAtendimento);
				loader.setController(controladorAtendimento = new ControladorAtendimento(this));
				try {
					loader.load();
				} catch (IOException e) {
					System.out.println("erro na abertura do pane atendimento");
					e.printStackTrace();
				}

				AnchorPane.setTopAnchor(pAtendimento, 150.0);
				AnchorPane.setLeftAnchor(pAtendimento, 160.0);
				AnchorPane.setRightAnchor(pAtendimento, 160.0);
				AnchorPane.setBottomAnchor(pAtendimento, 115.0);


				// Para abrir o pane fora do campo de vis�o
				pAtendimento.setTranslateY(880.0);

				apPrincipal.getChildren().add(pAtendimento);


			}

			dblAtendimento =  pAtendimento.getTranslateY();

			if(dblAtendimento.equals(0.0)){

				baixarTabPaneAtendimento.play(); 
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);
				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);
			} 

			else {

				subirTabPaneAtendimento.play();
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);
				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);
			}

		});

		btnOutorga.setOnAction((ActionEvent evt)->{
			
			if (listasComboBox == null) {
				listasComboBox = new ListasComboBox();
					listasComboBox.preencherListasComboBox();
					
			}

			if (pOutorga == null ) {
				

				pOutorga = new Pane();

				baixarTabPaneOutorga = new TranslateTransition(new Duration(350), pOutorga);
				baixarTabPaneOutorga.setToY(880.0);
				subirTabPaneOutorga = new TranslateTransition(new Duration(350), pOutorga);
				subirTabPaneOutorga.setToY(0.0);

				AnchorPane.setTopAnchor(pOutorga, 150.0);
				AnchorPane.setLeftAnchor(pOutorga, 160.0);
				AnchorPane.setRightAnchor(pOutorga, 160.0);
				AnchorPane.setBottomAnchor(pOutorga, 115.0);

				pOutorga.setTranslateY(880.0);

				apPrincipal.getChildren().add(pOutorga);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/Outorga.fxml"));
				Pane p = new  Pane();
				loader.setRoot(p);
				
				loader.setController(controladorOutorga = new ControladorOutorga (this));
				
				try {
					loader.load();
				} catch (IOException e) {
					System.out.println("erro na abertura do pane outorga");
					e.printStackTrace();
				}
				pOutorga.getChildren().add(p);

				p.minWidthProperty().bind(pOutorga.widthProperty());
				p.minHeightProperty().bind(pOutorga.heightProperty());

				p.maxWidthProperty().bind(pOutorga.widthProperty());
				p.maxHeightProperty().bind(pOutorga.heightProperty());

				

			}

			dblOutorga =  pOutorga.getTranslateY();

			if(dblOutorga.equals(0.0)){
				baixarTabPaneOutorga.play();

				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);//downFiscal.play();

					if (pAtendimento != null)
						pAtendimento.setTranslateY(880.0);

					if (pNavegador != null)
						pNavegador.setTranslateY(880.0);
					if (pBancosDiversos != null)
						pBancosDiversos.setTranslateY(880.0);

			} else {

				subirTabPaneOutorga.play();

				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);

			}

		});


		btnFiscalizacao.setOnAction((ActionEvent evt)->{
			
			/*
			 * preencher os combobox das tabelas relacionadas
			 */
			if (listasComboBox == null) {
				listasComboBox = new ListasComboBox();
					listasComboBox.preencherListasComboBox();
					
			}

			if (pFiscalizacao == null) {

				pFiscalizacao = new Pane();

				baixarTabPaneFiscalizacao = new TranslateTransition(new Duration(350), pFiscalizacao);
				baixarTabPaneFiscalizacao.setToY(880.0);
				subirTabPaneFiscalizacao = new TranslateTransition(new Duration(350), pFiscalizacao);
				subirTabPaneFiscalizacao.setToY(0.0);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fiscalizacao/Fiscalizacao.fxml"));

				loader.setRoot(pFiscalizacao);
				loader.setController(controladorFiscalizacao = new ControladorFiscalizacao(this));
				
				try {
					loader.load();
				} catch (IOException e) {
					System.out.println("erro na abertura do pane fiscalizacao");
					e.printStackTrace();
				}

				AnchorPane.setTopAnchor(pFiscalizacao, 150.0);
				AnchorPane.setLeftAnchor(pFiscalizacao, 160.0);
				AnchorPane.setRightAnchor(pFiscalizacao, 160.0);
				AnchorPane.setBottomAnchor(pFiscalizacao, 115.0);

				// Para abrir o pane fora do campo de vis�o
				pFiscalizacao.setTranslateY(880.0);

				apPrincipal.getChildren().add(pFiscalizacao);


			}

			dblFiscalizacao =  pFiscalizacao.getTranslateY();

			if(dblFiscalizacao.equals(0.0)){

				baixarTabPaneFiscalizacao.play(); 
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);

			} 



			else {

				subirTabPaneFiscalizacao.play();
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);

			}


		});

		btnConversor.setOnAction((ActionEvent evt)->{

			dblPesquisa =  spConversorCoordenadas.getTranslateY();

			if(dblPesquisa.equals(0.0) || dblPesquisa.equals(2.0)){

				downSearch.play();

			} else {

				upSearch.play();

			}

		});

		btnNavegador.setOnAction((ActionEvent evt)->{


			if (pNavegador == null ) {

				pNavegador = new Pane();

				baixarNavegador = new TranslateTransition(new Duration(350), pNavegador);
				baixarNavegador.setToY(880.0);
				subirNavegador = new TranslateTransition(new Duration(350), pNavegador);
				subirNavegador.setToY(0.0);

				AnchorPane.setTopAnchor(pNavegador, 150.0);
				AnchorPane.setLeftAnchor(pNavegador, 160.0);
				AnchorPane.setRightAnchor(pNavegador, 160.0);
				AnchorPane.setBottomAnchor(pNavegador, 115.0);

				pNavegador.setTranslateY(880.0);

				apPrincipal.getChildren().add(pNavegador);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/Navegador.fxml"));
				Pane p = new  Pane();
				loader.setRoot(p);
				loader.setController(new ControladorNavegacao ());
				try {
					loader.load();
				} catch (IOException e) {
					System.out.println("erro na abertura do pane fiscalizacao");
					e.printStackTrace();
				}
				pNavegador.getChildren().add(p);

				p.minWidthProperty().bind(pNavegador.widthProperty());
				p.minHeightProperty().bind(pNavegador.heightProperty());

				p.maxWidthProperty().bind(pNavegador.widthProperty());
				p.maxHeightProperty().bind(pNavegador.heightProperty());


			}


			dblNavegador =  pNavegador.getTranslateY();


			if(dblNavegador.equals(0.0)){

				baixarNavegador.play();

				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);//downFiscal.play();
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);


			} else {

				subirNavegador.play();
				
				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pBancosDiversos != null)
					pBancosDiversos.setTranslateY(880.0);

			}

		});

		btnEditorHTML.setOnAction((ActionEvent evt)->{

			Pane pEndereco = new Pane();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modelosHTML/ModelosHTML.fxml"));
			loader.setRoot(pEndereco);
			loader.setController(new ControladorModelosHTML());

			try {
				loader.load();
			} catch (IOException e) {
				System.out.println("erro leitura do pane - chamada legislação");
				e.printStackTrace();
			}

			Scene scene = new Scene(pEndereco);
			Stage stage = new Stage();
			
			/*abrir na  tela em que o usuario colocou o stage, caso tenha sido em uma segunda tela ele abrira na segunda tela*/
			Window w =  btnHome.getScene().getWindow();
			
	            stage.setX(w.getX() + w.getWidth()/5);
	            stage.setY(w.getY() + w.getHeight()/7);
	   
			stage.setWidth(1177);
			stage.setHeight(800);
			stage.setScene(scene);
			stage.setMaximized(false);
			stage.setResizable(false);
			stage.setAlwaysOnTop(true); 
			
			stage.xProperty().addListener((obs, oldVal, newVal) -> {
			     System.out.println("x do stage " + newVal);
			});
			
			
			stage.show();
			
			System.out.println("stage " + stage.getX());

		});
		
		btnBancoAccess.setOnAction((ActionEvent evt)->{

			if (pBancosDiversos == null ) {

				pBancosDiversos = new Pane();
				
				ttBaixaBancoAccess = new TranslateTransition(new Duration(350), pBancosDiversos);
				ttBaixaBancoAccess.setToY(880.0);
				ttSobeBancoAccess = new TranslateTransition(new Duration(350), pBancosDiversos);
				ttSobeBancoAccess.setToY(0.0);

				AnchorPane.setTopAnchor(pBancosDiversos, 150.0);
				AnchorPane.setLeftAnchor(pBancosDiversos, 160.0);
				AnchorPane.setRightAnchor(pBancosDiversos, 160.0);
				AnchorPane.setBottomAnchor(pBancosDiversos, 115.0);

				pBancosDiversos.setTranslateY(880.0);

				apPrincipal.getChildren().add(pBancosDiversos);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bancos_diversos/BancosDiversos.fxml"));
				Pane p = new  Pane();
				loader.setRoot(p);
				loader.setController(new ControladorBancosDiversos ());
				try {
					loader.load();
				} catch (IOException e) {
					System.out.println("erro na abertura do pane bancos diversos");
					e.printStackTrace();
				}
				pBancosDiversos.getChildren().add(p);
				
				p.minWidthProperty().bind(pBancosDiversos.widthProperty());
				p.minHeightProperty().bind(pBancosDiversos.heightProperty());

				p.maxWidthProperty().bind(pBancosDiversos.widthProperty());
				p.maxHeightProperty().bind(pBancosDiversos.heightProperty());
				
				p.setStyle("-fx-background-color: white;");
				
			
			} //  fim if pane null

			dblBancoAccess =  pBancosDiversos.getTranslateY();


			if(dblBancoAccess.equals(0.0)){

				ttBaixaBancoAccess.play();

				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);//downFiscal.play();
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);



			} else {

				ttSobeBancoAccess.play();

				if (pFiscalizacao != null)
					pFiscalizacao.setTranslateY(880.0);
				if (pAtendimento != null)
					pAtendimento.setTranslateY(880.0);
				if (pOutorga != null)
					pOutorga.setTranslateY(880.0);
				if (pNavegador != null)
					pNavegador.setTranslateY(880.0);

			}
			

		});

	}

	/** Metodo de inicializacao da Tab Lateral Direita
	 * 
	 */
	private void inicializarTabLateralDireita () {


		// botao de aumentar o zoom //
		btnZoomIn = GlyphsDude.createIconButton(
				FontAwesomeIcon.PLUS,
				"", 
				"10px", 
				"9px",  
				ContentDisplay.TOP);
		btnZoomIn.getStyleClass().add("button-lateral");

		btnZoomIn.setOnAction((ActionEvent evt)->{
			googleMaps.setZoomIn();
		});

		btnZoomIn.setLayoutX(10.0);
		btnZoomIn.setLayoutY(290.0);

		// botao de diminuir o zoom //
		btnZoomOut = GlyphsDude.createIconButton(
				FontAwesomeIcon.MINUS,
				"", 
				"10px", 
				"9px",  
				ContentDisplay.TOP);
		btnZoomOut.getStyleClass().add("button-lateral");

		btnZoomOut.setOnAction((ActionEvent evt)->{
			googleMaps.setZoomOut();
		});

		btnZoomOut.setLayoutX(10.0);
		btnZoomOut.setLayoutY(327.0);

		Label lblTipoMapa = new Label("Tipo de Mapa");
		lblTipoMapa.setLayoutX(28.0);
		lblTipoMapa.setLayoutY(11.0);
		lblTipoMapa.getStyleClass().add("classeLabel");

		Label lblEstiloMapa = new Label("Estilo de Mapa");
		lblEstiloMapa.setLayoutX(26.0);
		lblEstiloMapa.setLayoutY(189.0);
		lblEstiloMapa.getStyleClass().add("classeLabel");

		btnTerrain = new Button("TERRENO");
		btnRoadMap = new Button("RODOVIAS");
		btnSattelite = new Button("SATÉLITE");
		btnHybrid = new Button("HÍBRIDO");

		btnBlueMap = new Button();
		btnNightMap = new Button();
		btnGreenMap = new Button();
		btnRetroMap = new Button();

		btnNightMap.setId("btnNight");
		btnBlueMap.setId("btnBlueMap");
		btnGreenMap.setId("btnGreenMap");
		btnRetroMap.setId("btnRetroMap");

		btnNightMap.getStyleClass().add("estilo-mapa");
		btnBlueMap.getStyleClass().add("estilo-mapa");
		btnGreenMap.getStyleClass().add("estilo-mapa");
		btnRetroMap.getStyleClass().add("estilo-mapa");


		btnBlueMap.setLayoutX(10.0);
		btnBlueMap.setLayoutY(216.0);

		btnNightMap.setLayoutX(41.0);
		btnNightMap.setLayoutY(216.0);

		btnGreenMap.setLayoutX(72.0);
		btnGreenMap.setLayoutY(216.0);

		btnRetroMap.setLayoutX(103.0);
		btnRetroMap.setLayoutY(216.0);
		
		btnBlueMap.setOnAction((ActionEvent evt)->{
			//googleMaps.mudarEstiloMapa(2);
			apPrincipal.getStylesheets().clear();
			apPrincipal.getStylesheets().add(estilizarMapa(0));

		});	 

		btnNightMap.setOnAction((ActionEvent evt)->{
			//googleMaps.mudarEstiloMapa(1);
			apPrincipal.getStylesheets().clear();
			apPrincipal.getStylesheets().add(estilizarMapa(1));


		});	

		btnGreenMap.setOnAction((ActionEvent evt)->{
			//googleMaps.mudarEstiloMapa(3);

			apPrincipal.getStylesheets().clear();
			apPrincipal.getStylesheets().add(estilizarMapa(2));

		});	    
		btnRetroMap.setOnAction((ActionEvent evt)->{
			//googleMaps.mudarEstiloMapa(4);

			apPrincipal.getStylesheets().clear();
			apPrincipal.getStylesheets().add(estilizarMapa(3));
		});	    	


		btnTerrain.setLayoutX(10.0);
		btnTerrain.setLayoutY(40.0);

		btnTerrain.getStyleClass().add("button-lateral");

		btnRoadMap.setLayoutX(10.0);
		btnRoadMap.setLayoutY(75.0);

		btnRoadMap.getStyleClass().add("button-lateral");

		btnSattelite.setLayoutX(10.0);
		btnSattelite.setLayoutY(110.0);

		btnSattelite.getStyleClass().add("button-lateral");

		btnHybrid.setLayoutX(10.0);
		btnHybrid.setLayoutY(145.0);

		btnHybrid.getStyleClass().add("button-lateral");

		checkBacia = new CheckBox("Bacias");
		checkUnidadesHidrograficas = new CheckBox("Unidades Hid");
		checkRiodDF  = new CheckBox("Rios do DF");
		checkRiosUniao  = new CheckBox("Rios da União");
		checkFraturado  = new CheckBox("Fraturado");
		checkPoroso  = new CheckBox("Poroso");
		checkUTM  = new CheckBox("UTM");
		checkTrafego  = new CheckBox("Tráfego");

		Pane pCheck = new Pane ();
		pCheck.setPrefSize(120, 345);
		pCheck.setLayoutX(10.0);
		pCheck.setLayoutY(10.0);

		pCheck.setStyle("-fx-background-color: white;");
		pCheck.getChildren().addAll(checkBacia, checkUnidadesHidrograficas, checkRiodDF, checkRiosUniao,  checkFraturado, checkPoroso,checkUTM, checkTrafego);

		checkBacia.setLayoutX(5.0);
		checkBacia.setLayoutY(15.0);

		checkUnidadesHidrograficas.setLayoutX(5.0);
		checkUnidadesHidrograficas.setLayoutY(40.0);

		checkRiodDF.setLayoutX(5.0);
		checkRiodDF.setLayoutY(65.0);

		checkRiosUniao.setLayoutX(5.0);
		checkRiosUniao.setLayoutY(90.0);

		checkFraturado.setLayoutX(5.0);
		checkFraturado.setLayoutY(115.0);

		checkPoroso.setLayoutX(5.0);
		checkPoroso.setLayoutY(140.0);

		checkUTM.setLayoutX(5.0);
		checkUTM.setLayoutY(165.0);
		
		checkTrafego.setLayoutX(5.0);
		checkTrafego.setLayoutY(190.0);


		Text iconMap1 = GlyphsDude.createIcon(FontAwesomeIcon.MAP, "20px");
		tabLD1.setGraphic(iconMap1);
		tabLD1.setClosable(false);

		Text iconMap2 = GlyphsDude.createIcon(FontAwesomeIcon.OBJECT_GROUP, "20px");
		tabLD2.setGraphic(iconMap2);
		tabLD2.setClosable(false);

		pLD1.getStyleClass().add("pane-lateral");

		pLD1.getChildren().addAll(
				lblTipoMapa, 
				btnTerrain, btnRoadMap, btnSattelite, btnHybrid, 
				lblEstiloMapa, btnNightMap, btnBlueMap, btnGreenMap, btnRetroMap,
				btnZoomIn, btnZoomOut);

		pLD2.getStyleClass().add("pane-lateral");
		pLD2.getChildren().addAll(pCheck);

		tabLD1.setContent(pLD1);
		tabLD2.setContent(pLD2);

		checkBacia.setOnAction((ActionEvent evt)->{
			//googleMaps.openShape(6);
			
			System.out.println(checkBacia.isSelected());
			googleMaps.abrirShape(checkBacia.isSelected(), "geoJsonBacias", "shapeBacias", "map");
			
			
		});
		
		
		checkUnidadesHidrograficas.setOnAction((ActionEvent evt)->{
			
			googleMaps.abrirShape(checkUnidadesHidrograficas.isSelected(), "geoJsonUnidadesHidrograficas", "shapeUnidadesHidrograficas", "map");
			
		});

		checkRiodDF.setOnAction((ActionEvent evt)->{ 
			
			googleMaps.openShape(1);
		});

		checkRiosUniao.setOnAction((ActionEvent evt)->{
			googleMaps.openShape(4);
		
			
		});

		checkFraturado.setOnAction((ActionEvent evt)->{
			
			googleMaps.abrirShape(checkFraturado.isSelected(), "geoJsonFraturado", "shapeFraturado", "map");
		});

		
		checkPoroso.setOnAction((ActionEvent evt)->{
			
			googleMaps.abrirShape(checkPoroso.isSelected(), "geoJsonFreatico", "shapeFreatico", "map");
			
			
		});

		checkUTM.setOnAction((ActionEvent evt)->{
			googleMaps.openShape(5);
			
		});

		checkTrafego.setOnAction((ActionEvent evt)->{
			googleMaps.openShape(7);
		});


		btnTerrain.setOnAction((ActionEvent evt)->{
			googleMaps.switchTerrain();
		});

		btnRoadMap.setOnAction((ActionEvent evt)->{
			googleMaps.switchRoadmap();
		});

		btnSattelite.setOnAction((ActionEvent evt)->{
			googleMaps.switchSatellite();
		});

		btnHybrid.setOnAction((ActionEvent evt)->{
			googleMaps.switchHybrid();
		});

	}

	/** Metodo que envia para a area de transferencia os valores do label
	 * 
	 * @param lbl
	 */
	public void copiarCoord (Label lbl) {

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 1){

						Clipboard clip = Clipboard.getSystemClipboard();
						ClipboardContent conteudo = new ClipboardContent();
						conteudo.putString(lbl.getText());
						clip.setContent(conteudo);
					}
				}
			}
		});

	}  // fim metodo copiarCoord
	
	
	String strEstiloMapa [] = new String [] {
		"/css/principal_mapa_azul.css", // 0
		"/css/principal_mapa_escuro.css",
		"/css/principal_mapa_verde.css",
		"/css/principal_mapa_salmao.css", //3
			
	};
	
	public String estilizarMapa (int intEstiloMapa) {
		
		Registro registro = new Registro();
		
		String strEstiloCSS = "/css/principal_mapa_azul.css";
		
		Properties prop = new Properties();
		
		try {
			prop = registro.lerRegistro();
			
			strEstiloCSS = prop.getProperty("strEstiloCSS");
			googleMaps.mudarEstiloMapa(intEstiloMapa+1);
			
		} catch (Exception e1) {
			
			strEstiloCSS = strEstiloMapa[intEstiloMapa];
			googleMaps.mudarEstiloMapa(intEstiloMapa+1);
			
		}
		
		try {
			
			prop.setProperty("strEstiloCSS", strEstiloMapa[intEstiloMapa]);
			
			registro.salvarRegistro(prop);
			
			strEstiloCSS = prop.getProperty("strEstiloCSS");
			
		} catch (URISyntaxException e3) {

			e3.printStackTrace();
		} catch (IOException e3) {

			e3.printStackTrace();
		}

		return strEstiloCSS;
		
	}
	
	
	public String estilizarMapa () {
		
		Registro registro = new Registro();
		
		String strEstiloCSS = "/css/principal_mapa_azul.css";
		
		Properties prop = new Properties();
	
			try {
				
				prop = registro.lerRegistro();
				
				strEstiloCSS = prop.getProperty("strEstiloCSS");
				
				/*
				 * comparar a string strEstiloCSS com a array strEstiloMapa e escolher o mapa correto de visualizacao
				 */

				if (strEstiloCSS.equals(strEstiloMapa [0])) {
					googleMaps.mudarEstiloMapa(1);
				} 
				else if (strEstiloCSS.equals(strEstiloMapa [1])) {
					googleMaps.mudarEstiloMapa(2);
				}
				
				else if (strEstiloCSS.equals(strEstiloMapa [2])) {
					googleMaps.mudarEstiloMapa(3);
				}
				else {
					googleMaps.mudarEstiloMapa(4);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				strEstiloCSS = strEstiloMapa[0];
				
			}
		return strEstiloCSS;
	}

}


