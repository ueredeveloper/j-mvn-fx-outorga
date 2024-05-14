package principal;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Alerta {
	

	public void alertar (Alert a) {
		
		//Alert a = new Alert(Alert.AlertType.CONFIRMATION, ContentText, ButtonType.OK, ButtonType.CANCEL);
		
		a.setHeaderText(null);
		
		DialogPane root = a.getDialogPane();
		Stage dialogStage = new Stage(StageStyle.UTILITY);
		root.getScene().setRoot(new Group());
		root.setPadding(new Insets(0, 0, 0, 0));
		root.getStylesheets().add(
				   getClass().getResource("/css/alerta.css").toExternalForm());
		root.getStyleClass().add("dialogoPrincipal");
		
		for (ButtonType buttonType : root.getButtonTypes()) {
		    ButtonBase button = (ButtonBase) root.lookupButton(buttonType);
		    button.setOnAction(evt -> {
		        root.setUserData(buttonType);
		        dialogStage.close();
		    });
		}
		
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.setAlwaysOnTop(true);
		dialogStage.setResizable(false);
		dialogStage.showAndWait();
		
	}

}
