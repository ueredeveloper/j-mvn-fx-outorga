package adasa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TesteContextMenuETextField extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {


		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("showing");
		    }
		});
		contextMenu.setOnShown(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("shown");
		    }
		});

		MenuItem item1 = new MenuItem("About");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("About");
		    }
		});
		MenuItem item2 = new MenuItem("Preferences");
		item2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Preferences");
		    }
		});
		contextMenu.getItems().addAll(item1, item2);

		final TextField textField = new TextField("Type Something");
		textField.setContextMenu(contextMenu);
		//Control.setContextMenu(javafx.scene.control.ContextMenu);
		
		//O método de conveniência pode ser usado para definir um menu de contexto em qualquer controle. O exemplo acima resulta no menu de contexto sendo exibido à direita Side do TextField. Como alternativa, um manipulador de eventos também pode ser definido no controle para chamar o menu de contexto, conforme mostrado abaixo.


		/*
		textField.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        contextMenu.show(textField, Side.LEFT, 0, 0);
		    }
		});
		*/


		Group root = new Group();
		
		root.getChildren().add(textField);
		
		

        Scene scene = new Scene(root, 400, 400);
 
        stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();
        
        
		
	}

}
