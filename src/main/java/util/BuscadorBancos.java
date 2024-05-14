package util;

import java.util.ArrayList;
import java.util.List;

import dao.BancoAccessDao;
import entidades.BancoAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class BuscadorBancos {
	
	
	
	/* 
	 * para pesquisar no banco de dados access
	 */
	List<BancoAccess> docList = new ArrayList<>();
	ContextMenu contextMenu;
	
	TextField textField;

	
	public BuscadorBancos (TextField textField, ContextMenu contextMenu) {
		this.textField = textField;
		this.contextMenu = contextMenu;
	}

	public void buscar () {
		
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			
			BancoAccessDao bDao = new BancoAccessDao();
			
			if (textField.getText() != null && textField.getText().length() > 2) {
				
				if (docList.size() == 0) {
					docList = bDao.listarBancoAccess("");
				}
				
				contextMenu.hide();

				contextMenu = new ContextMenu();
				contextMenu.setMaxWidth(300);
				
				textField.setContextMenu(contextMenu);
			
				for (BancoAccess b : docList) {
					
					if (
							
							(b.getBaInteressado() + "\n  | " + b.getBaNumeroProcesso() + "\n    | " + b.getBaEnderecoEmpreendimento()).toLowerCase()
							
							.indexOf(textField.getText().toLowerCase()) != -1) {
						
					Label lbl = new Label(b.getBaInteressado() + "\n  | " + b.getBaNumeroProcesso() + "\n    | " + b.getBaEnderecoEmpreendimento());
					lbl.setPrefWidth(400);
					lbl.setWrapText(true);
					
					MenuItem item = new MenuItem();
					item.setGraphic(lbl);
					
					item.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							textField.setText(b.getBaInteressado());
						}

					});

					// Add MenuItem to ContextMenu
					contextMenu.getItems().add(item);
					
					}

				} // fim loop for

				contextMenu.show(textField, Side.RIGHT, 0, 0);
		
			
			} // fim if (tf.getText().length() > 3

		});
		
		
	}

}
