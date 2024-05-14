package controladores.principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.BancoAccessDao;
import entidades.BancoAccess;
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

public class ControladorBancoAccess implements Initializable {
	
	@FXML Pane pBancoAccess;
	
	TableView<BancoAccess> tvLista = new TableView<>();
	TableColumn<BancoAccess, String> tcProcesso = new TableColumn<>("Processo");
	TableColumn<BancoAccess, String> tcInteressado = new TableColumn<>("Usuário");
	
	ObservableList<BancoAccess> obsList = FXCollections.observableArrayList();

	Pane p1 = new Pane();
	BorderPane bp1 = new BorderPane();
	BorderPane bp2 = new BorderPane();
	ScrollPane sp = new ScrollPane();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		bp1.minWidthProperty().bind(pBancoAccess.widthProperty());
		bp1.maxHeightProperty().bind(pBancoAccess.heightProperty().subtract(35));

		bp1.getStyleClass().add("border-pane");

		bp2.setPrefHeight(800.0D);
		bp2.minWidthProperty().bind(pBancoAccess.widthProperty());

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		sp.setContent(bp2);

		bp1.setCenter(sp);

		pBancoAccess.getChildren().add(bp1);

		p1.setMaxSize(980.0, 1000.0);
		p1.setMinSize(980.0, 1000.0);

		bp2.setTop(p1);
		BorderPane.setAlignment(p1, Pos.CENTER);
	
		System.out.println("banco access inicializado");
		
		inicializarComponentes ();
		
		//acionarBotoes ();
		
		tcProcesso.setCellValueFactory(new PropertyValueFactory<BancoAccess, String>("baNumeroProcesso"));
		tcInteressado.setCellValueFactory(new PropertyValueFactory<BancoAccess, String>("baInteressado"));
		
		tcProcesso.setPrefWidth(300);
		tcInteressado.setPrefWidth(580);

		tvLista.setPrefSize(930, 500);
		tvLista.setLayoutX(25);
		tvLista.setLayoutY(103);

		tvLista.getColumns().add(tcProcesso);
		tvLista.getColumns().add(tcInteressado);

		tvLista.setItems(obsList);
		
		p1.getChildren().add(tvLista);
		
		acionarBotoes ();
		 
	}
	
	public void acionarBotoes () {
		
		
		btnPesquisar.setOnAction((ActionEvent evt)->{

			BancoAccessDao bDao = new BancoAccessDao();
			
			List<BancoAccess> docList = bDao.listarBancoAccess("");

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
