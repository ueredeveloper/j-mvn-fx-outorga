package controladores.principal;

import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import entidades.BaciasHidrograficas;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.FinalidadeRequerida;
import entidades.GetterAndSetterFinalidades;
import entidades.SubSistema;
import entidades.Subterranea;
import entidades.TipoPoco;
import entidades.UnidadeHidrografica;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import principal.Alerta;
import principal.Componentes;
import principal.ListasComboBox;

public class TabSubterraneaController implements Initializable {

	public Subterranea subterranea = new Subterranea();
	
	BaciasHidrograficas bacia_hidrografica = new  BaciasHidrograficas();
	UnidadeHidrografica unidade_hidrografica = new UnidadeHidrografica();
	TipoPoco tipo_poco = new TipoPoco();
	SubSistema subsistema = new SubSistema();
	

	// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
	DecimalFormat df = new DecimalFormat("#,##0.00"); 

	public Subterranea capturarSubterranea () {

		Subterranea sub = subterranea;

		// capturar coordenadas, caso esteja vazia de mensagem de erro
		try {
		// valores double de coordenadas - latitude e longitude
		sub.setInterDDLatitude(Double.parseDouble(tfLatitude.getText()));
		sub.setInterDDLongitude(Double.parseDouble(tfLongitude.getText()));
		
		} catch (Exception e) {
			
			// buscar letras entre os numeros
			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Coordenadas inválidas!!!", ButtonType.OK));
			
			e.printStackTrace();
			
		}

		// valor geometry de  coordenadas latitude longitude
		GeometryFactory geoFac = new GeometryFactory();

			Point p = geoFac.createPoint(new Coordinate(
					Double.parseDouble(tfLongitude.getText()),
					Double.parseDouble(tfLatitude.getText()
							)));

			p.setSRID(4674);

			sub.setInterGeom(p);

		sub.setInterBaciaFK(bacia_hidrografica);
		sub.setInterUHFK(unidade_hidrografica);
		sub.setSubTipoPocoFK(tipo_poco);
		sub.setSubSubSistemaFK(subsistema);
		sub.setSubCod_plan(tfCod_plan.getText());
		
		sub.setSubCaesb(cbSubCaesb.getValue());
		
		sub.setSubVazaoTeste(tfVazaoTeste.getText());
		sub.setSubVazaoSubsistema(tfVazaoSubsistema.getText());
		
		// se o textfield estiver vazaio, seta 0.0
		if ( tfVazaoOutorgada.getText().isEmpty() ) { 
			sub.setSubVazaoOutorgada(0.0);
		// se nao, tente formatar			
		} else {
			try {sub.setSubVazaoOutorgada(Double.parseDouble(df.parseObject(tfVazaoOutorgada.getText()).toString()));
			} catch (Exception e) {
				sub.setSubVazaoOutorgada(0.0);
			}
			
		}
		
		sub.setSubEstatico(tfEstatico.getText());
		sub.setSubDinamico(tfDinamico.getText());
		sub.setSubProfundidade(tfProfundidade.getText());
		

		if (dpDataOperacao.getValue() == null) {

			sub.setSubDataOperacao(null);}
		else {

			sub.setSubDataOperacao(Date.valueOf(dpDataOperacao.getValue()));
			
			System.out.println(dpDataOperacao.getValue());
			
			System.out.println(Date.valueOf(dpDataOperacao.getValue()));
			
		}
		
		// FINALIDADE REQUERIDA //
		FinalidadeRequerida fr = new FinalidadeRequerida();

		for (Finalidade f : sub.getFinalidades() ) {

			if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
				fr = (FinalidadeRequerida) f;
				//System.out.println("sub - finalidade requerida ID " + fr.getFinID());
			}

		}

		GetterAndSetterFinalidades gsRequerida = new GetterAndSetterFinalidades();
		
		gsRequerida.inicializarVariaveisFinalidadesRequeridas();
		
		gsRequerida.capturarFinalidade(
				fr, 
				tfListFinReq, tfListSubfinReq, tfListQuanReq, tfListConReq, tfListVazoesReq, 
				lblCalTotalReq, 
				tfVazoesLDReq, tfVazoesHDReq, tfPeriodoDMReq);

		fr.setFinInterferenciaFK(sub);
		sub.getFinalidades().add(fr);
		
		// FINALIDADE AUTORIZADA //
		FinalidadeAutorizada fa = new FinalidadeAutorizada();

		for (Finalidade f : sub.getFinalidades() ) {

			if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
				fa = (FinalidadeAutorizada) f;
				//System.out.println("sub - finalidade autorizada ID " + fa.getFinID());
			}

		}

		GetterAndSetterFinalidades gsAutorizada = new GetterAndSetterFinalidades();
		
		gsAutorizada.inicializarVariaveisFinalidadesAutorizadas();
		
		gsAutorizada.capturarFinalidade(
				fa, 
				tfListFinAut, tfListSubfinAut, tfListQuanAut, tfListConAut, tfListVazoesAut, 
				lblCalTotalAut, 
				tfVazoesLDAut, tfVazoesHDAut, tfPeriodoDMAut);

		fa.setFinInterferenciaFK(sub);
		sub.getFinalidades().add(fa);
			
		return sub;

	}

	public void imprimirSubterranea (Subterranea sub) {

		tfLatitude.setText(String.valueOf(sub.getInterDDLatitude()));
		tfLongitude.setText(String.valueOf(sub.getInterDDLongitude()));

		cbBaciaHidrografica.setValue(sub.getInterBaciaFK().getBaciaNome());
		cbUnidadeHidrografica.setValue(String.valueOf(sub.getInterUHFK().getUhCodigo()));

		cbTipoPoco.setValue(sub.getSubTipoPocoFK().getTipoPocoDescricao());
		cbSubsistema.setValue(sub.getSubSubSistemaFK().getSubDescricao());
		
		tfCod_plan.setText(sub.getSubCod_plan());

		cbSubCaesb.setValue(sub.getSubCaesb());
		
		tfVazaoTeste.setText(sub.getSubVazaoTeste());
		tfVazaoSubsistema.setText(sub.getSubVazaoSubsistema());
	
		// tentar imprimir o valor
		try {tfVazaoOutorgada.setText( df.format(	 sub.getSubVazaoOutorgada()	) .replaceAll(",00", "")		 );} 
		// ou imprime vazio
		catch (Exception e) {tfVazaoOutorgada.setText(""); };
		
		tfEstatico.setText(sub.getSubEstatico());
		tfDinamico.setText(sub.getSubDinamico());
		tfProfundidade.setText(sub.getSubProfundidade());

		if (sub.getSubDataOperacao() == null) {
			dpDataOperacao.getEditor().clear();
		} else {
			Date d = sub.getSubDataOperacao();
			dpDataOperacao.setValue(d.toLocalDate());
		}
		
		// FINALIDADES REQUERIDAS
		FinalidadeRequerida fr = new FinalidadeRequerida(); 

		for (Finalidade f : sub.getFinalidades() ) {

			if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
				fr = (FinalidadeRequerida) f;
				//System.out.println("sub - finalidade id " + fr.getFinID());
			}

		}
		
		GetterAndSetterFinalidades gsFinalidades = new GetterAndSetterFinalidades();
		
		gsFinalidades.inicializarVariaveisFinalidadesRequeridas();
		
		gsFinalidades.imprimirFinalidade(
				fr, 
				tfListFinReq, tfListSubfinReq, tfListQuanReq, tfListConReq, tfListVazoesReq, 
				lblCalTotalReq, 
				tfVazoesLDReq, tfVazoesHDReq, tfPeriodoDMReq);
		
		fr.setFinInterferenciaFK(sub);

		//System.out.println("tab SUB antes do iterator " + sub.getFinalidades().size());

		Iterator<Finalidade> it;

		for (it = sub.getFinalidades().iterator(); it.hasNext();)
		{
			Finalidade f = (Finalidade) it.next();
			if (f.getFinID() == fr.getFinID()) {
				it.remove();
				//System.out.println("TabSubterranea - finalidade já existente? Iterator " + ( f.getFinID() == fr.getFinID()));
			}
		}
		//System.out.println("tab SUB depois antes de adicionar fr " + sub.getFinalidades().size());

		sub.getFinalidades().add(fr);

		//System.out.println("tab SUB depois " + sub.getFinalidades().size());
		
		
		// FINALIDADES AUTORIZADAS
		FinalidadeAutorizada fa = new FinalidadeAutorizada();

		for (Finalidade f : sub.getFinalidades() ) {

			if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
				fa = (FinalidadeAutorizada) f;
				//System.out.println("sub - finalidade id - autorizada " + fr.getFinID());
			}

		}
		
		GetterAndSetterFinalidades gsAutorizada = new GetterAndSetterFinalidades();
		
		gsAutorizada.inicializarVariaveisFinalidadesAutorizadas();
		
		gsAutorizada.imprimirFinalidade(
				fa, 
				tfListFinAut, tfListSubfinAut, tfListQuanAut, tfListConAut, tfListVazoesAut, 
				lblCalTotalAut, 
				tfVazoesLDAut, tfVazoesHDAut, tfPeriodoDMAut);
		
		fa.setFinInterferenciaFK(sub);

		//System.out.println("tab SUB antes do iterator " + sub.getFinalidades().size());

		Iterator<Finalidade> itAut;

		for (itAut = sub.getFinalidades().iterator(); itAut.hasNext();)
		{
			Finalidade f = (Finalidade) itAut.next();
			if (f.getFinID() == fa.getFinID()) {
				itAut.remove();
				//System.out.println("TabSubterranea - finalidade autorizada já existente? Iterator " + ( f.getFinID() == fa.getFinID()));
			}
		}
		//System.out.println("tab SUB depois antes de adicionar fa " + sub.getFinalidades().size());

		sub.getFinalidades().add(fa);

		//System.out.println("tab SUB depois finalida autorizada " + sub.getFinalidades().size());
		
		this.subterranea = sub;	

	}

	ObservableList<String> olFinalidades = FXCollections.observableArrayList(

			"Abastecimento Humano"	,
			"Criação De Animais"	,
			"Irrigação"				,

			"Uso Comercial"			,
			"Uso Industrial"		,

			"Piscicultura"			,
			"Lazer"					,

			"Outras Finalidades"	

			);

	ObservableList<String> olSubFinalidades = FXCollections.observableArrayList(

			"Área Rural"	,
			"Área Urbana"	,
			""	,
			"Aves"	,
			"Bovinos"	,
			"Bubalino"	,
			"Caprinos"	,
			"Equinos"	,
			"Ovinos"	,
			"Piscicultura"	,
			"Suínos"	,
			""	,
			"Abacaxi"	,
			"Abóbora"	,
			"Agrião"	,
			"Agrofloresta"	,
			"Agroindústria"	,
			"Agroturismo"	,
			""	,
			"Alface"	,
			"Alho"	,
			"Bambu"	,
			"Banana"	,
			"Café"	,
			"Feijão"	,
			"Flores"	,
			"Flores"	,
			"Frutífera"	,
			"Frutífera"	,
			"Gramínea"	,
			"Grãos"	,
			"Hortaliças"	,
			"Mandioca"	,
			"Milho"	,
			"Mogno"	,
			"Paisagismo"	,
			"Reflorestamento"	,
			"Tomate",
			""	,
			"Lavagem De Veículo"	,
			"Concreto"	,
			"Construção Civil"	,
			"Fabricação De Gelo"	,
			"Farmacêutica"	,
			""	,
			"Tanque Escavado Não Revestido"	,
			"Tanque Escavado Revestido"	,


			"Água Mineral"



			);

	ObservableList<String> olSubCaesb = FXCollections
			.observableArrayList(
					"Sim", 
					"Não"
					); 

	@FXML Pane pSubterranea;

	public static TabSubterraneaController tabSubCon;

	//-- INITIALIZE --//
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		tabSubCon = this;

		inicializarComponentes();

		cbBaciaHidrografica.setItems(ListasComboBox.obsListBacia);
		cbUnidadeHidrografica.setItems(ListasComboBox.obsListUH);

		cbTipoPoco.setItems(ListasComboBox.obsListTipoPoco);
		cbSubsistema.setItems(ListasComboBox.obsListSubsistema);
		
		cbSubCaesb.setItems(olSubCaesb);

		cbBaciaHidrografica.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				bacia_hidrografica.setBaciaID((Integer) new_value + 1); 
				//System.out.println("sub =  bacia hidrografica id " +  bacia_hidrografica.getBaciaID());

			}
		});
		
		cbBaciaHidrografica.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->

    		bacia_hidrografica.setBaciaNome(new_value)
    		//System.out.println("sub = bacia hidrografica nome " + new_value)
    	);

		cbUnidadeHidrografica.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number old_value, Number new_value) {

				unidade_hidrografica.setUhID((Integer) new_value + 1);
				//System.out.println("sub = id unidade hidrografica " + unidade_hidrografica.getUhID());

			}
		});
		
		cbUnidadeHidrografica.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    
    		unidade_hidrografica.setUhCodigo(Integer.parseInt(new_value))
    	//System.out.println("sub = uh código " + new_value)
    	);
		
		/**
		 * mudar o valor da string strOnMouse para MOUSE_PRESSED caso o combobox cbTipo seja clicado
		 */
		cbTipoPoco.setOnMousePressed(new EventHandler<MouseEvent>(){

	          @Override
	          public void handle(MouseEvent m) {
	        
	        	  strOnMouse = m.getEventType().toString();
	        	
	          }

	      });

		/**
		 * Adicinar valor do id (tp_ID) ao objeto tipo_poco e, caso a strOnMouse seja igual a MOUSE_PRESSED, chamar metodo buscarPropriedadeShape()
		 */
		cbTipoPoco.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				tipo_poco.setTipoPocoID((Integer) new_value + 1); 
				
				if (strOnMouse.equals("MOUSE_PRESSED")) {
					

					if (tipo_poco.getTipoPocoDescricao().equals("Tubular")) {
						ControladorPrincipal.googleMaps.buscarPropriedadeShape(true, "geoJsonFraturado", "shapeFraturado", "null", tfLatitude.getText(), tfLongitude.getText());
						
					} else {
						ControladorPrincipal.googleMaps.buscarPropriedadeShape(true, "geoJsonFreatico", "shapeFreatico", "null", tfLatitude.getText(), tfLongitude.getText());
						
					}
					
					strOnMouse = "";;
					
				}
	
			}
		});
		
		/**
		 * adicionar valor tp_Descricao ao objeto tipo_poco
		 */
		cbTipoPoco.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    
    	 tipo_poco.setTipoPocoDescricao(new_value)
   
    	);

		/**
		 * adicionar valor do id ao objeto subsistema
		 */
		cbSubsistema.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
					Number value, Number new_value) {

				subsistema.setSubID((Integer) new_value + 1);
				
			}
		});
		/**
		 * adicionar valor ao objeto susbisistema (ss_descricao_)
		 */
		cbSubsistema.getSelectionModel()
    	.selectedItemProperty()
    	.addListener( 
    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
    
    		subsistema.setSubDescricao(new_value)
 
    	);
		
		tfEstatico.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfEstatico.getText().length() >= 15) {

						// if it's 11th character then just setText to previous
						// one
						tfEstatico.setText(tfEstatico.getText().substring(0, 15));
					}
				}
			}
		});

		tfDinamico.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfDinamico.getText().length() >= 15) {

						// if it's 11th character then just setText to previous
						// one
						tfDinamico.setText(tfDinamico.getText().substring(0, 15));
					}
				}
			}
		});

		tfProfundidade.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfProfundidade.getText().length() >= 15) {

						// if it's 11th character then just setText to previous
						// one
						tfProfundidade.setText(tfProfundidade.getText().substring(0, 15));

					}

				} // fim if length

			}
		});

	} // FIM INITIALIZE

	Componentes com;

	Pane pDadosSubterranea;

	TextField tfLatitude, tfLongitude;

	Button btnLatLon;

	ComboBox<String> cbBaciaHidrografica, cbUnidadeHidrografica, cbTipoPoco, cbSubsistema, cbSubCaesb;

	TextField tfCod_plan, tfVazaoSubsistema, tfVazaoTeste, tfVazaoOutorgada;
	TextField tfEstatico, tfDinamico, tfProfundidade;
	CheckBox cbAut;

	DatePicker dpDataOperacao;

	ArrayList<Node> listaComponentes = new ArrayList<Node>();

	Pane pFinalidades;
	Pane pVazoes;

	GridPane gpFinalidades;
	GridPane gpVazoes;

	TextField[] tfListFinReq = new TextField[5];
	TextField[] tfListSubfinReq = new TextField[5];
	TextField[] tfListQuanReq = new TextField[5];
	TextField[] tfListConReq = new TextField[5];
	TextField[] tfListVazoesReq = new TextField[5];

	TextField[] tfListFinAut = new TextField[5];
	TextField[] tfListSubfinAut = new TextField[5];
	TextField[] tfListQuanAut = new TextField[5];
	TextField[] tfListConAut = new TextField[5];
	TextField[] tfListVazoesAut = new TextField[5];

	Button [] btnLisCalReq = new Button[6];
	Button [] btnLisCalAut = new Button[6];

	Label lblCalTotalReq = new Label();
	Label lblCalTotalAut = new Label();

	ChoiceBox<String>[] listCbFinReq = new ChoiceBox[5];
	ChoiceBox<String>[] listCbSubfinReq = new ChoiceBox[5];

	ChoiceBox<String>[] listCbFinAut = new ChoiceBox[5];
	ChoiceBox<String>[] listCbSubfinAut = new ChoiceBox[5];

	TextField[] tfVazoesLDReq = new TextField[12];
	TextField[] tfVazoesHDReq = new TextField[12]; //  
	TextField[] tfPeriodoDMReq = new TextField[12];
	Button [] btnListCalMesesReq = new Button[3];

	TextField[] tfVazoesLDAut = new TextField[12];
	TextField[] tfVazoesHDAut = new TextField[12]; //  
	TextField[] tfPeriodoDMAut = new TextField[12];
	Button [] btnListCalMesesAut = new Button[3];
	
	Button btnCapturaFinalidadeRequerida;
	
	int intContadorBtnCapFin = 0;
	
	String strOnMouse = "";
	

	public void inicializarComponentes () {

		listaComponentes.add(pDadosSubterranea = new Pane());		    
		listaComponentes.add(new Label ("Latitude (Y):"));
		listaComponentes.add(tfLatitude = new TextField());
		listaComponentes.add(new Label ("Longitude (X):"));
		listaComponentes.add(tfLongitude = new TextField());
		listaComponentes.add(btnLatLon = new Button());

		listaComponentes.add(new Label ("Bacia: "));
		listaComponentes.add(cbBaciaHidrografica = new ComboBox<>());
		listaComponentes.add(new Label ("UH: "));
		listaComponentes.add(cbUnidadeHidrografica = new ComboBox<>());

		listaComponentes.add(new Label ("Tipo de Poço: "));
		listaComponentes.add(cbTipoPoco = new ComboBox<>());
		listaComponentes.add(new Label ("Subsistema: "));
		listaComponentes.add(cbSubsistema = new ComboBox<>());
		listaComponentes.add(new Label ("Código: "));
		listaComponentes.add(tfCod_plan = new TextField());
		listaComponentes.add(new Label ("Área atendida (Caesb): "));
		listaComponentes.add(cbSubCaesb = new ComboBox<>());

		listaComponentes.add(new Label ("Vazão Subsistema (L/h): "));
		listaComponentes.add(tfVazaoSubsistema = new TextField());
		
		listaComponentes.add(new Label ("Vazão Teste (L/h): "));
		listaComponentes.add(tfVazaoTeste = new TextField());
		
		listaComponentes.add(new Label ("Vazão Outorgada (L/h): "));
		listaComponentes.add(tfVazaoOutorgada = new TextField());
		
		listaComponentes.add(new Label ("Nível Estático (m): "));
		listaComponentes.add(tfEstatico = new TextField());
		listaComponentes.add(new Label ("Nível Dinâmico (m): "));
		listaComponentes.add(tfDinamico = new TextField());
		listaComponentes.add(new Label ("Prof. (m): "));
		listaComponentes.add(tfProfundidade = new TextField());
		listaComponentes.add(new Label ("Em operação desde: "));
		listaComponentes.add(dpDataOperacao = new DatePicker());

		Double  prefSizeWHeLayXY  [][] = { 
				
				{950.0,170.0,0.0,0.0},
				{95.0,30.0,166.0,5.0},
				{140.0,30.0,261.0,5.0},
				{95.0,30.0,411.0,5.0},
				{140.0,30.0,506.0,5.0},
				{25.0,25.0,658.0,8.0},
				{160.0,30.0,46.0,33.0},
				{160.0,30.0,46.0,63.0},
				{60.0,30.0,216.0,33.0},
				{60.0,30.0,216.0,63.0},
				{150.0,30.0,285.0,33.0},
				{150.0,30.0,285.0,63.0},
				{150.0,30.0,445.0,33.0},
				{150.0,30.0,445.0,63.0},
				{140.0,30.0,605.0,33.0},
				{140.0,30.0,605.0,63.0},
				{150.0,30.0,755.0,33.0},
				{150.0,30.0,755.0,63.0},
				{150.0,30.0,15.0,94.0},
				{150.0,30.0,15.0,124.0},
				{140.0,30.0,168.0,93.0},
				{140.0,30.0,175.0,123.0},
				{140.0,30.0,325.0,93.0},
				{140.0,30.0,325.0,123.0},
				{110.0,30.0,475.0,93.0},
				{110.0,30.0,475.0,123.0},
				{120.0,30.0,596.0,93.0},
				{120.0,30.0,595.0,123.0},
				{70.0,30.0,725.0,93.0},
				{70.0,30.0,725.0,123.0},
				{130.0,30.0,805.0,93.0},
				{130.0,30.0,805.0,123.0},
		};

		com = new Componentes();
		com.popularTela(listaComponentes, prefSizeWHeLayXY, pSubterranea);
		
		
		tfVazaoOutorgada.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {

				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfVazaoOutorgada.getText().length() >= 0) {

						/*  Nao permitir letras - variavel double, somente numeros com ponto ou virgula
						 */
						if ( tfVazaoOutorgada.getText().matches("(.*)[a-zA-Z](.*)") == true ) {
							// buscar letras entre os numeros
							Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

							// retirar caracter errado, como letra, virgula etc
							tfVazaoOutorgada.setText(tfVazaoOutorgada.getText().substring(0, tfVazaoOutorgada.getText().length() - 1));

						}

					}

				} // fim if length
			}
		});

		TabPane tp = new TabPane();
		
		tp.setPrefSize(950, 320);
		tp.setLayoutX(0.0);
		tp.setLayoutY(170.0);

		Tab tabFinalidadeRequerida = new Tab("Finalidade Requerida");
		Tab tabFinalidadeAutorizada = new Tab("Finalidade Autorizada");
		
		tabFinalidadeRequerida.setClosable(false);
		tabFinalidadeAutorizada.setClosable(false);
		
		
		tp.getTabs().addAll(tabFinalidadeRequerida, tabFinalidadeAutorizada);

		pFinalidades = new Pane();
		pFinalidades.setPrefSize(910, 150);
		pFinalidades.setLayoutX(15);
		pFinalidades.setLayoutY(15);

		pVazoes = new Pane();
		pVazoes.setPrefSize(910, 120);
		pVazoes.setLayoutX(15);
		pVazoes.setLayoutY(180);

			Pane pFinReq = new  Pane();
			pFinReq.setPrefSize(940, 300);
			pFinReq.getChildren().addAll(pFinalidades, pVazoes);

			tabFinalidadeRequerida.setContent(pFinReq);
		
		gpFinalidades = new GridPane();
		gpVazoes = new GridPane();
		
		gpFinalidades.setMinSize(910, 140); 
		gpFinalidades.setMaxSize(910, 140); 
		gpFinalidades.setLayoutX(20);

		gpVazoes.setMinSize(910, 100); 
		gpVazoes.setMaxSize(910, 100); 
		gpVazoes.setLayoutX(20);

			pFinalidades.getChildren().add(gpFinalidades);
			pVazoes.getChildren().add(gpVazoes);

		
			inicializarFinalidades(
					gpFinalidades, gpVazoes,
					tfListFinReq,tfListSubfinReq,tfListQuanReq,tfListConReq,tfListVazoesReq,
					btnLisCalReq,lblCalTotalReq,listCbFinReq,listCbSubfinReq,
					tfVazoesLDReq,tfVazoesHDReq,tfPeriodoDMReq,btnListCalMesesReq
					);
			
		pFinalidades = new Pane();
		pFinalidades.setPrefSize(910, 150);
		pFinalidades.setLayoutX(15);
		pFinalidades.setLayoutY(15);

		pVazoes = new Pane();
		pVazoes.setPrefSize(910, 120);
		pVazoes.setLayoutX(15);
		pVazoes.setLayoutY(180);
	
		Pane pFinAut = new  Pane();
		pFinAut.setPrefSize(940, 300);
		pFinAut.getChildren().addAll(pFinalidades, pVazoes);
		
		tabFinalidadeAutorizada.setContent(pFinAut);
		
			gpFinalidades = new GridPane();
			gpVazoes = new GridPane();
			
			gpFinalidades.setMinSize(910, 140); 
			gpFinalidades.setMaxSize(910, 140); 
			gpFinalidades.setLayoutX(20);
	
			gpVazoes.setMinSize(910, 100); 
			gpVazoes.setMaxSize(910, 100);  
			gpVazoes.setLayoutX(20);

			pFinalidades.getChildren().add(gpFinalidades);
			pVazoes.getChildren().add(gpVazoes);
		
			inicializarFinalidades(
					gpFinalidades, gpVazoes,
					tfListFinAut,tfListSubfinAut,tfListQuanAut,tfListConAut,tfListVazoesAut,
					btnLisCalAut,lblCalTotalAut,listCbFinAut,listCbSubfinAut,
					tfVazoesLDAut,tfVazoesHDAut,tfPeriodoDMAut,btnListCalMesesAut
					);

		
		pSubterranea.getChildren().add(tp);
		
		
		btnCapturaFinalidadeRequerida = new Button("Fin Req");
		btnCapturaFinalidadeRequerida.setPrefSize(70, 20);
		btnCapturaFinalidadeRequerida.setLayoutX(860);
		btnCapturaFinalidadeRequerida.setLayoutY(5);
		
		pFinAut.getChildren().add(btnCapturaFinalidadeRequerida);
		 /*
		  * capturar os valores da tab finalidade requerida
		  */
		btnCapturaFinalidadeRequerida.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("btn finalidade requerida clicado");
			
				if (intContadorBtnCapFin%2==0) {
					
					for (int i=0;i<5;i++) {
						tfListFinAut[i].setText(tfListFinReq[i].getText());
						tfListSubfinAut[i].setText(tfListSubfinReq[i].getText());
						tfListQuanAut[i].setText(tfListQuanReq[i].getText());
						tfListConAut[i].setText(tfListConReq[i].getText());
						tfListVazoesAut[i].setText(tfListVazoesReq[i].getText());
					}
					
					for (int i=0;i<12;i++) {
						tfVazoesLDAut[i].setText(tfVazoesLDReq[i].getText());
						tfVazoesHDAut[i].setText(tfVazoesHDReq[i].getText());
						tfPeriodoDMAut[i].setText(tfPeriodoDMReq[i].getText());
					}
					
					lblCalTotalAut.setText(lblCalTotalReq.getText());
					
				} else {
					
					for (int i=0;i<5;i++) {
						tfListFinAut[i].setText("");
						tfListSubfinAut[i].setText("");
						tfListQuanAut[i].setText("");
						tfListConAut[i].setText("");
						tfListVazoesAut[i].setText("");
					}
					
					for (int i=0;i<12;i++) {
						tfVazoesLDAut[i].setText("");
						tfVazoesHDAut[i].setText("");
						tfPeriodoDMAut[i].setText("");
					}
					
					lblCalTotalAut.setText("");
					
				} // fim else
				
				
				intContadorBtnCapFin++;
			
				
			}
		});
		
	}

	
	// contador de cliques no botao btnListCalMeses[0]
	int c, d, e = 0;
	
	public void inicializarFinalidades (
			GridPane gpFinalidades, GridPane gpVazoes,
			TextField[] tfFinalidade,TextField[] tfSubfinalidade,TextField[] tfQuantidade,TextField[] tfConsumo,TextField[] tfVazoes,
			Button [] btnCalculo,Label lblCalculoTotal,ChoiceBox<String>[] cbListFinalidade,ChoiceBox<String>[] cbListSubfinalidade,
			TextField[] tfVazoesLD,TextField[] tfVazoesHD,TextField[] tfPeriodoDM,Button [] btnListCalMeses
			) {


		String[] strLabelFinalidade = {
				"Finalidade", " ","Subfinalidade", " ", "Quant (unid)", "Consumo(L/dia)", "TOTAL", " "
		};

		Label lbl; 

		for (int i = 0; i<8; i++ ) {
			gpFinalidades.add(lbl = new Label(strLabelFinalidade[i]), i, 0); // child, columnIndex, rowIndex
			lbl.setAlignment( Pos.CENTER );
			lbl.setMinSize(50, 20);
		}

		for (int i = 0; i<5; i++ ) {

			TextField tfFin = tfFinalidade [i] = new TextField();
			TextField tfSub = tfSubfinalidade [i] = new TextField();
			TextField tfQuant = tfQuantidade [i] = new TextField();
			TextField tfCon = tfConsumo [i] = new TextField();
			TextField tfVaz = tfVazoes [i] = new TextField();

			Button btnCal = btnCalculo [i] = new Button();

			btnCal.setPrefSize(25, 25);

			ChoiceBox<String> cbFin =  cbListFinalidade [i] = new ChoiceBox<String>();
			cbFin.setItems(olFinalidades);
			cbFin.setPrefSize(50, 20);

			ChoiceBox<String> cbSub =  cbListSubfinalidade [i] = new ChoiceBox<String>();
			cbSub.setItems(olSubFinalidades);
			cbSub.setPrefSize(50, 20);

			gpFinalidades.add(tfFin, 0, i+1); // child, columnIndex, rowIndex
			gpFinalidades.add(cbFin, 1, i+1);

			gpFinalidades.add(tfSub, 2, i+1);
			gpFinalidades.add(cbSub, 3, i+1);

			gpFinalidades.add(tfQuant, 4, i+1); 
			gpFinalidades.add(tfCon, 5, i+1); 

			gpFinalidades.add(tfVaz, 6, i+1);

			gpFinalidades.add(btnCal, 7, i+1);

			tfQuant.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {
						// Check if the new character is greater than LIMIT
						if (tfQuant.getText().length() >= 0) {

							/*   Nao permitir letras - variavel double, somente numeros com ponto ou virgula
							 */
							if ( tfQuant.getText().matches("(.*)[a-zA-Z](.*)") == true ) {
								// buscar letras entre os numeros
								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								// retirar caracter errado, como letra, virgula etc
								tfQuant.setText(tfQuant.getText().substring(0, tfQuant.getText().length() - 1));

							}

						}

					} // fim if length
				}
			});

			// AÇOES DOS BOTOES

			tfCon.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {
						// Check if the new character is greater than LIMIT
						if (tfCon.getText().length() >= 0) {

							/*  Nao permitir letras - variavel double, somente numeros com ponto ou virgula
							 */
							if ( tfCon.getText().matches("(.*)[a-zA-Z](.*)") == true ) {
								// buscar letras entre os numeros
								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								// retirar caracter errado, como letra, virgula etc
								tfCon.setText(tfCon.getText().substring(0, tfCon.getText().length() - 1));

							}

						}

					} // fim if length
				}
			});

			cbFin.getSelectionModel().selectedItemProperty().addListener( 

					(ObservableValue<? extends String> observable, String oldValue, String newValue) ->

					tfFin.setText(newValue)
					);

			cbSub.getSelectionModel().selectedItemProperty().addListener( 

					(ObservableValue<? extends String> observable, String oldValue, String newValue) ->

					tfSub.setText(newValue)
					);
			
			DecimalFormat df = new DecimalFormat("#,##0.00"); 

			btnCal.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					// formatar 15.000,56 para double 15000.56
					Double resultado = 0.0;
			
					
					try {
						resultado = (Double.parseDouble(df.parseObject(tfQuant.getText()).toString())) * (Double.parseDouble(df.parseObject(tfCon.getText()).toString()));
					} catch (NumberFormatException e) {
				
						e.printStackTrace();
					} catch (ParseException e) {
					
						e.printStackTrace();
					}
					
					// formatar 15000.5 para string 15.000,56
					tfVaz.setText(df.format(resultado));
				}
			});

		}

		String[] lblVazoesMeses = {
				"Mês","JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ", " ",
		};

		for (int i = 0; i<14; i++ ) {
			gpVazoes.add(lbl = new Label(lblVazoesMeses[i]), i, 0); // child, columnIndex, rowIndex
			lbl.setMinSize(50, 20);
		}


		for (int i = 0; i<12; i++ ) {

			TextField tfVazLD = tfVazoesLD [i] = new TextField();
			gpVazoes.add(tfVazLD, i+1, 1); // child, columnIndex, rowIndex
			
			tfVazLD.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {
					
							/*  Nao permitir letras, permitir ponto e virgula [^.,] 
							 */
						
							if ( tfVazLD.getText().matches("(.*)[a-zA-Z](.*)") == true ) {
								// "(.*)\\D(.*)" buscar qualquer digito diferente de numero, ponto e virgula pode 
								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								// retirar caracter errado, como letra, virgula etc
								tfVazLD.setText(tfVazLD.getText().substring(0, tfVazLD.getText().length() - 1));

							}


					} // fim if length
				}
			});

		}

		for (int i = 0; i<12; i++ ) {

			TextField tfVazHD = tfVazoesHD [i] = new TextField();
			gpVazoes.add(tfVazHD, i+1, 2);

			tfVazHD.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {
						// Check if the new character is greater than LIMIT
						if (tfVazHD.getText().length() >= 0) {

							/*  Nao permitir virgula e ponto no tempo de captacao e nos dias 
							 *  por mes que sempre e um numero inteiro
							 */
							if ( tfVazHD.getText().matches("(.*)\\D(.*)") == true ) {
								// "(.*)\\D(.*)" buscar qualquer digito diferente de numero no meio do que foi digitado
								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								// retirar caracter errado, como letra, virgula etc
								tfVazHD.setText(tfVazHD.getText().substring(0, tfVazHD.getText().length() - 1));

							}

						}

					} // fim if length
				}
			});

		}

		for (int i = 0; i<12; i++ ) {

			TextField tfPerDM = tfPeriodoDM [i] = new TextField();
			gpVazoes.add(tfPerDM, i+1, 3);

			tfPerDM.lengthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {

					if (newValue.intValue() > oldValue.intValue()) {
						// Check if the new character is greater than LIMIT
						if (tfPerDM.getText().length() >= 0) {

							/*  Nao permitir virgula e ponto no tempo de captacao e nos dias 
							 *  por mes que sempre e um numero inteiro
							 */
							if (tfPerDM.getText().matches("(.*)\\D(.*)") == true ) { 
								// "(.*)\\D(.*)" buscar qualquer digito diferente de numero no meio do que foi digitado

								Alerta a = new Alerta ();
								a.alertar(new Alert(Alert.AlertType.ERROR, "Somente números!!!", ButtonType.OK));

								tfPerDM.setText(tfPerDM.getText().substring(0, tfPerDM.getText().length() - 1));

							}

						}

					} // fim if length
				}
			});

		}

		String[] lblMesVazaoTempoPeriodo = {
				"Vazão (l/dia) (*)","Tempo (h/dia)", "Período (dia/mês)",
		};

		for (int i = 0; i<3; i++ ) {

			Button btnCalMes = btnListCalMeses[i] = new Button();
			btnCalMes.setPrefSize(25, 25);
			gpVazoes.add(btnCalMes, 13, i+1);
			gpVazoes.add(lbl = new Label(lblMesVazaoTempoPeriodo[i]), 0, i+1);  // child, columnIndex, rowIndex
			lbl.setMinSize(110, 20);
			lbl.setAlignment( Pos.CENTER );
		} // fim loop for


		// botao para calcular o valor total de TODAS as finalidades
		Button btnCal6 = btnCalculo[5] = new Button();
		btnCal6.setPrefSize(25, 25);
		gpFinalidades.add(btnCal6, 7, 6);

		// adicionar label com resultado total no gridpane e centralizar o texto
		lblCalculoTotal.setText("0.0");
		lblCalculoTotal.setMaxWidth(Double.MAX_VALUE);
		lblCalculoTotal.setAlignment(Pos.CENTER);
		gpFinalidades.add(lblCalculoTotal, 6, 6);

		// calcular o valor total das finalidades
		btnCal6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				DecimalFormat df = new DecimalFormat("#,##0.00");  
				// formatar 15.000,56 para double 15000.56
				Double resultado = 0.0;

				for (int i = 0; i<5;i++) {
					// capturar os resultados e, caso o usuario digite com virgula, ex: 13,34, mudar para double 13.34
					if (! tfVazoes[i].getText().isEmpty())
						
						try {
							resultado += Double.parseDouble(df.parseObject(tfVazoes[i].getText()).toString());
						} catch (NumberFormatException e) {
							
							e.printStackTrace();
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
		
				}
				// formatar double 15000.56 para 15.000,56
				lblCalculoTotal.setText(df.format(resultado));

			}
		});
		
		// facilitar o cadastro dos meses 
		btnListCalMeses[0].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				for (int i = 0; i<12;i++) {
					
					// preencher todos os meses com valores iguais
					if (c%2==0) {
						tfVazoesLD [i].setText(lblCalculoTotal.getText());
					}
					
					// preencher retirando vazao dos meses de jan fev mar nov dez
					else {
						
						if (i == 0 || i == 1 || i == 2 || i == 10 || i == 11) {
							tfVazoesLD [i].setText("0");
				
						} else {
							tfVazoesLD [i].setText(lblCalculoTotal.getText());
						}
					}
			
				} // fim for 12
				
				c++; //contador btnListCalMeses
				
			}
		});
		
		

		// facilitar o cadastro dos meses 
		btnListCalMeses[1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (int i = 0; i<12;i++) {

					// preencher todos os meses com valores iguais
					if (d%2==0) {
						tfVazoesHD [i].setText(tfVazoesHD[3].getText());
					}

					// preencher retirando vazao dos meses de jan fev mar nov dez
					else {

						if (i == 0 || i == 1 || i == 2 || i == 10 || i == 11) {
							tfVazoesHD [i].setText("0");

						} else {
							tfVazoesHD [i].setText(tfVazoesHD[3].getText());
						}
					}

				} // fim for 12

				d++; // contador btnListCalMeses

			}
		});
		
		

		// facilitar o cadastro dos meses 
		btnListCalMeses[2].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				int meses [] =  {31,28,31,30,31,30,31,31,30,31,30,31};
				
				for (int i = 0; i<12;i++) {
					
					if (e == 0) {
						tfPeriodoDM [i].setText(String.valueOf(meses[i]));
					}
					
					if (e == 1) {
						
						tfPeriodoDM [i].setText(tfPeriodoDM[3].getText());
						
						
					}
					
					if (e == 3) {
						
						if (i == 0 || i == 1 || i == 2 || i == 10 || i == 11) {
							tfPeriodoDM [i].setText("0");

						} else {
							tfPeriodoDM [i].setText(tfPeriodoDM[3].getText());
						}
						
					}
					
				} // fim for 12
				
				e++;
				
				if (e==4) {
					e=0;
				}
					
				
				

			}
		});


		// facilitar o cadastro dos meses 
		btnLatLon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				capturarCoordenadas();
				
			}
		});
		
		
		

	} // fim inicializarFinalidades

	public void capturarCoordenadas () {

		String lat = ControladorPrincipal.capturarGoogleMaps().getLat();
		String lon = ControladorPrincipal.capturarGoogleMaps().getLon();

		tfLatitude.setText(lat);
		tfLongitude.setText(lon);

		GeometryFactory geoFac;
		Point p;

		geoFac = new GeometryFactory();

		p = geoFac.createPoint(new Coordinate(
				Double.parseDouble(lon),
				Double.parseDouble(lat)
				));

		p.setSRID(4674);

		for (BaciasHidrograficas b : ListasComboBox.listaBaciasHidrograficas) {

			if (p.intersects(b.getBaciaShape())) {

				cbBaciaHidrografica.setValue(String.valueOf(b.getBaciaNome()));

			}

		} // fim loop bacias hidrograficas

		for (UnidadeHidrografica u : ListasComboBox.listaUnidadesHidrograficas) {


			if (p.intersects(u.getShape())) {

				cbUnidadeHidrografica.setValue(String.valueOf(u.getUhCodigo()));

			}

		} // fim loop unidades hidrograficas
		
		
	} // fim metodo capturar coordenadas

	public void retornarCodigoSubsistema (String strSubsistema, String strCodigoSubsistema, String strVazaoMedia) {
		/*converter vazao em m³ para litros*/
		Double dblVazao = Double.valueOf(strVazaoMedia)*1000;
		/*formatar 1000.0 como 1.000,00 e retirar virgula e retirar valores decimais (,00)*/
		df.format(dblVazao).replace(",00", "");
	
			/*comparar strSubsistema com a lista de subsistemas e preencher o combobox cbSubsistema*/
			ListasComboBox.obsListSubsistema.forEach(item -> {
			
				/* replace: retirar o hifem do subsistema bambuí (Bambuí­) sem ele vem 007_03_Bambuí­*/
				if (item.replace(" ", "").equals(strSubsistema.toUpperCase().replaceAll("­", ""))) {
					
					cbSubsistema.setValue(item);
				}
					
		    });
			/* replace: retirar o hifem do subsistema bambuí - sem ele vem 007_03_Bambuí­*/
			tfCod_plan.setText(strCodigoSubsistema.replaceAll("­", ""));
			/*retirar casas decimais  1.000,00 = 1.000*/
			tfVazaoSubsistema.setText(df.format(dblVazao).replace(",00", ""));
			
	}
	
}


//Image imgSub = new Image(TabSubterraneaController.class.getResourceAsStream("/images/subterranea.png"));
//@FXML ImageView	iVewSubt = new ImageView();


//iVewSubt.setImage(imgSub);

/*
dpDataSubterranea.setConverter(new StringConverter<LocalDate>() {

	@Override
	public String toString(LocalDate t) {
		if (t != null) {
			return formatter.format(t);
		}
		return null;
	}

	@Override
	public LocalDate fromString(String string) {
		if (string != null && !string.trim().isEmpty()) {
			return LocalDate.parse(string, formatter);
		}
		return null;
	}

});
 */

