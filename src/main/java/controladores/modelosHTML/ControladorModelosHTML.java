package controladores.modelosHTML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.ModelosDao;
import entidades.ModelosHTML;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;
import principal.Alerta;

public class ControladorModelosHTML implements Initializable {
	
	@FXML HTMLEditor htmlEditor;
	@FXML TextArea textAreaHTML;
	
	@FXML Button btnNovo;
	@FXML Button btnSalvar;
	@FXML Button btnEditar;
	@FXML Button btnExcluir;
	@FXML Button btnCancelar;
	@FXML Button btnPesquisar;
	@FXML Button btnSair;
	
	@FXML Button btnHTML;
	@FXML Button btnTexto;
	
	@FXML TabPane tabPane;
	@FXML Tab tabTexto;
	@FXML Tab tabHTML;
	
	@FXML TextField tfPesquisar;
	
	@FXML TextField tfIdentificacao;
	
	ObservableList<String> obsListTipoDocumento = FXCollections
			.observableArrayList(
					"Requerimento",
					"Parecer",
					"Registro",
					"Despacho",
					"Anexo"
					
					); 
	
	@FXML ComboBox<String> cbTipoDocumento = new ComboBox<String>();
		
	ObservableList<String> obsListTipoInterferencia = FXCollections
			.observableArrayList(
					"Superficial",
					"Subterrânea",
					"Outros"
					
					); 

	@FXML ComboBox<String> cbTipoInterferencia = new ComboBox<String>();
		
	ObservableList<String> obsListUnidade = FXCollections
			.observableArrayList(
					"SRH",
					"SDU",
					"SEF",
					"SAF",
					"SRS",
					"SAE",
					"SPE"
					); 
	
	@FXML ComboBox<String> cbUnidade = new ComboBox<String>();
	
	
	// -- Tabela --  //
	@FXML private TableView <ModelosHTML> tvLista;
		
		// -- Colunas -- //
		@FXML private TableColumn<ModelosHTML, String> tcIdentificacao;
		@FXML private TableColumn<ModelosHTML, String> tcUnidade;
		
		
	public void btnHTMLHab (ActionEvent event) {
		htmlEditor.setHtmlText(textAreaHTML.getText());
		tabPane.getSelectionModel().select(tabTexto);
	
	}
	public void btnTextoHab (ActionEvent event) {
		textAreaHTML.setText(htmlEditor.getHtmlText());
		tabPane.getSelectionModel().select(tabHTML);
		
	}
	
	public void btnNovoHabilitar (ActionEvent event) {
		
		tfIdentificacao.setText("");
		
		cbTipoDocumento.setValue(null);
		cbTipoInterferencia.setValue(null);
		
		cbUnidade.setValue(null);
		
		tfIdentificacao.setDisable(false);
		
		cbTipoDocumento.setDisable(false);
		cbTipoInterferencia.setDisable(false);
		cbUnidade.setDisable(false);
		
		btnSalvar.setDisable(false);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnEditar.setDisable(true);
		
	}
	
	// -- botão salvar -- //
	public void btnSalvarHabilitar (ActionEvent event) {
		
		ModelosHTML mod = new ModelosHTML();
		
		mod.setModTipoDocumento(cbTipoDocumento.getValue());
		mod.setModTipoInterferencia(cbTipoInterferencia.getValue());
		mod.setModUnidade(cbUnidade.getValue());
		
		mod.setModIdentificacao(tfIdentificacao.getText());
		mod.setModConteudo(htmlEditor.getHtmlText());
		
		
	ModelosDao modDao = new ModelosDao();
	
		modDao.salvarModelo(mod);
		
	}
	
	// -- botão editar -- //
	public void btnEditarHabilitar (ActionEvent event) {
		
		if (tfIdentificacao.isDisable()) {
			
			cbTipoDocumento.setDisable(false);
			cbTipoInterferencia.setDisable(false);
			cbUnidade.setDisable(false);
			tfIdentificacao.setDisable(false);
			htmlEditor.setDisable(false);
			textAreaHTML.setDisable(false);
			
		} else {
		
		
			ModelosHTML mod = tvLista.getSelectionModel().getSelectedItem();
			
				mod.setModTipoDocumento(cbTipoDocumento.getValue());
				mod.setModTipoInterferencia(cbTipoInterferencia.getValue());
				mod.setModUnidade(cbUnidade.getValue());
				mod.setModIdentificacao(tfIdentificacao.getText());
				mod.setModConteudo(htmlEditor.getHtmlText());
				
				
			ModelosDao modDao = new ModelosDao();
			
				modDao.mergeModelo(mod);
				
			obsList.remove(mod);
			obsList.add(mod);
			
			modularBotoesInicial();
			
			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.INFORMATION, "Cadastro editado com sucesso!!!", ButtonType.OK));
				
		}
	
	}
	
	// -- botão excluir -- //
	public void btnExcluirHabilitar (ActionEvent event) {
		
		ModelosHTML mod = tvLista.getSelectionModel().getSelectedItem();
		
		int id = mod.getModID();
		
		ModelosDao modDao = new ModelosDao();
		
				modDao.removerModelo(id);
				
			
				obsList.remove(mod);
				
				modularBotoesInicial();
				
			
	}
	
	// -- botão cancelar -- //
	public void btnCancelarHabilitar (ActionEvent event) {
		
		modularBotoesInicial ();
		
		tfIdentificacao.setText("");
		htmlEditor.setHtmlText("");
		textAreaHTML.setText("");
		
		cbTipoDocumento.setValue(null);
		cbTipoInterferencia.setValue(null);
		
		cbUnidade.setValue(null);
		
	
	}
	
	// -- botão pesquisar  -- //
	public void btnPesquisarHabilitar (ActionEvent event) {
		
		strPesquisar = tfPesquisar.getText();
		
		listarModelos (strPesquisar);
		
	}
	
	
	// string de pesquisa //
	String strPesquisar = "";
	
	// observable lista para a table view e table columns //
	ObservableList<ModelosHTML> obsList = FXCollections.observableArrayList();
	
	public void initialize(URL url, ResourceBundle rb) {
		
		System.out.println("inicializado");
	
		cbTipoDocumento.setItems(obsListTipoDocumento);
		cbTipoInterferencia.setItems(obsListTipoInterferencia);
		cbUnidade.setItems(obsListUnidade);
		
		tvLista.setItems(obsList);
		
		tcIdentificacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModelosHTML, String>, ObservableValue<String>>() {
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<ModelosHTML, String> mod) {
		    	return new SimpleStringProperty(mod.getValue().getModIdentificacao());
		       
		    }
		});
		
		tcUnidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModelosHTML, String>, ObservableValue<String>>() {
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<ModelosHTML, String> mod) {
		    	return new SimpleStringProperty(mod.getValue().getModUnidade());
		       
		    }
		});
		
		modularBotoesInicial();
		
		
		/*
		 * Buscar apenas clicando no enter do teclado
		 */
		tfPesquisar.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER){
				btnPesquisar.fire();
			}
		});
	
	}
	
	// --- metodo para listar endereco --- //
	public void listarModelos (String strPesquisa) {
		 		
	 	// --- conexao - listar enderecos --- //
		ModelosDao modDao = new ModelosDao();
		List<ModelosHTML> modLista = modDao.listarModelo(strPesquisa);

		
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
		
    	for (ModelosHTML m : modLista) {
    		
    		m.getModID();
    		m.getModUnidade();
    		m.getModIdentificacao();
    		m.getModConteudo();
    		
    		obsList.add(m);
    		
    		textAreaHTML.setFont(new Font(14));
    		textAreaHTML.setWrapText(true);
    		
	    		
		}
				
		tvLista.setItems(obsList); 
		
		selecionarModelos ();
			
	}
	
	// método selecionar endereço -- //
	public void selecionarModelos () {
			
		tvLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			public void changed(ObservableValue<?> observable , Object oldValue, Object newValue) {
			
			ModelosHTML mod = (ModelosHTML) newValue;
			
			if (mod == null) {
				
				cbTipoDocumento.setValue(null);
				cbTipoInterferencia.setValue(null);
				cbUnidade.setValue(null);
				
				tfIdentificacao.setText("");
				htmlEditor.setHtmlText("");
				textAreaHTML.setText("");
				
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			} else {

				// -- preencher os campos -- //
				
				cbTipoDocumento.setValue(mod.getModTipoDocumento());
				cbTipoInterferencia.setValue(mod.getModTipoInterferencia());
				
				cbUnidade.setValue(mod.getModUnidade());
				tfIdentificacao.setText(mod.getModIdentificacao());
				htmlEditor.setHtmlText(mod.getModConteudo());
				
				textAreaHTML.setText(htmlEditor.getHtmlText());
				
				// -- habilitar e desabilitar botoes -- //
				btnNovo.setDisable(true);
				btnSalvar.setDisable(true);
				btnEditar.setDisable(false);
				btnExcluir.setDisable(false);
				btnCancelar.setDisable(false);
				
			}
			
			}
			
		});
				
	}
		 	
		 	//-- Modular os botoes na inicializacao do programa --//
	private void modularBotoesInicial () {
				
		tfIdentificacao.setDisable(true);
		
		cbTipoDocumento.setDisable(true);
		cbTipoInterferencia.setDisable(true);
		
		cbUnidade.setDisable(true);
		
		btnSalvar.setDisable(true);
		btnEditar.setDisable(true);
		btnExcluir.setDisable(true);
		btnNovo.setDisable(false);
		
	}
		
}
