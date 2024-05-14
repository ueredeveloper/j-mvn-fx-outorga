package controladores.principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.BancoCaesbDao;
import entidades.BancoCaesb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import principal.Componentes;

public class ControladorBancoCaesb implements Initializable {
	
	
	@FXML Pane pBancoCaesb;
	
	TableView<BancoCaesb> tvLista = new TableView<>();
	TableColumn<BancoCaesb, String> tcUsuario = new TableColumn<>("Usuário");
	TableColumn<BancoCaesb, String> tcInscricao = new TableColumn<>("Inscrição");
	TableColumn<BancoCaesb, String> tcEndereco = new TableColumn<>("Endereço");
	TableColumn<BancoCaesb, String> tcCPFCNPJ = new TableColumn<>("CPF/CNPJ");
	TableColumn<BancoCaesb, String> tcHidrometro = new TableColumn<>("Hidrômetro");
	
	ObservableList<BancoCaesb> obsList = FXCollections.observableArrayList();

	Pane p1 = new Pane();
	BorderPane bp1 = new BorderPane();
	BorderPane bp2 = new BorderPane();
	ScrollPane sp = new ScrollPane();
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		bp1.minWidthProperty().bind(pBancoCaesb.widthProperty());
		bp1.maxHeightProperty().bind(pBancoCaesb.heightProperty().subtract(35));

		bp1.getStyleClass().add("border-pane");

		bp2.setPrefHeight(800.0D);
		bp2.minWidthProperty().bind(pBancoCaesb.widthProperty());

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		sp.setContent(bp2);

		bp1.setCenter(sp);

		pBancoCaesb.getChildren().add(bp1);

		p1.setMaxSize(980.0, 1000.0);
		p1.setMinSize(980.0, 1000.0);

		bp2.setTop(p1);
		BorderPane.setAlignment(p1, Pos.CENTER);
	
		System.out.println("banco CAESB inicializado");
		
		inicializarComponentes ();
		
		//acionarBotoes ();
		
		tcUsuario.setCellValueFactory(new PropertyValueFactory<BancoCaesb, String>("bcUsuario"));
		tcEndereco.setCellValueFactory(new PropertyValueFactory<BancoCaesb, String>("bcEndereco"));
		tcCPFCNPJ.setCellValueFactory(new PropertyValueFactory<BancoCaesb, String>("bcCPFCNPJ"));
		tcInscricao.setCellValueFactory(new PropertyValueFactory<BancoCaesb, String>("bcInscricao"));
		tcHidrometro.setCellValueFactory(new PropertyValueFactory<BancoCaesb, String>("bcHidrometro"));
		
		tcUsuario.setPrefWidth(270);
		tcEndereco.setPrefWidth(250);
		tcCPFCNPJ.setPrefWidth(130);
		tcInscricao.setPrefWidth(130);
		tcHidrometro.setPrefWidth(130);

		tvLista.setPrefSize(930, 500);
		tvLista.setLayoutX(25);
		tvLista.setLayoutY(103);

		tvLista.getColumns().add(tcUsuario);
		tvLista.getColumns().add(tcEndereco);
		tvLista.getColumns().add(tcCPFCNPJ);
		tvLista.getColumns().add(tcInscricao);
		tvLista.getColumns().add(tcHidrometro);

		tvLista.setItems(obsList);
		
		p1.getChildren().add(tvLista);
		
		acionarBotoes ();
		
	}

	public void acionarBotoes () {
		
		btnPesquisar.setOnAction((ActionEvent evt)->{

			BancoCaesbDao bDao = new BancoCaesbDao();
			
			List<BancoCaesb> docList = bDao.listarBancoCaesb(tfPesquisar.getText());

			if (!obsList.isEmpty()) {
				obsList.clear();

			}

			obsList.addAll(docList);

			tvLista.setItems(obsList);
			
			
		});

	}
	
	Pane pPersistencia;
	Button btnNovo;
	Button btnSalvar;
	Button btnEditar;
	Button btnExcluir;
	Button btnCancelar;
	Button btnPesquisar;
	TextField tfPesquisar;

	ArrayList<Node> listNodesPersistencia= new ArrayList<Node>();
	
	Componentes com;
	Double prefSizeWHeLayXY [][];
	
	public void inicializarComponentes () {
		
		listNodesPersistencia.add(pPersistencia = new Pane());
		listNodesPersistencia.add(btnNovo = new Button("NOVO"));
		listNodesPersistencia.add(btnSalvar = new Button("SALVAR"));
		listNodesPersistencia.add(btnEditar = new Button("EDITAR"));
		listNodesPersistencia.add(btnExcluir = new Button("EXCLUIR"));
		listNodesPersistencia.add(btnCancelar = new Button("CANCELAR"));

		listNodesPersistencia.add(tfPesquisar = new TextField());
		listNodesPersistencia.add(btnPesquisar = new Button("PESQUISAR"));

		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,60.0,25.0,28.0},
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
		
	}

	

}
