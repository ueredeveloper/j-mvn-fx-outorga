package adasa;

import java.util.List;

import dao.BancoAccessDao;
import entidades.BancoAccess;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TesteListarBancoAccess extends Application {

	public static void main(String[] args) {

		/*
		BancoAccessDao bad = new BancoAccessDao();
		
		List<BancoAccess> list = bad.listarBancoAccess("");
		
		
		for (BancoAccess b : list) {
			
			System.out.println(b.getBaInteressado());
		}
		*/
		
		 launch(args);
		
		

	}

	@Override
	public void start(Stage stage) throws Exception {


			ComboBox<String> emailComboBox = new ComboBox<String>();
			
			BancoAccessDao bDao = new BancoAccessDao();
		    	List<BancoAccess> docList = bDao.listarBancoAccess("");
			
			ObservableList<String> obsList = FXCollections.observableArrayList();
			
			
	        emailComboBox.setEditable(true);  
	        
			emailComboBox.setItems(obsList);
	        

			if (!obsList.isEmpty()) {
				obsList.clear();

			}

			for (int i = 0; i<obsList.size(); i++) {
				obsList.add(docList.get(i).getBaInteressado());
			}
			

	        emailComboBox.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String t, String t1) {   
	            	
	            	
	                        
	            }    
	        });
	        
	        
	    	
	        
	       Pane p = new Pane ();
	       
	       p.getChildren().add(emailComboBox);
	        
	        Scene scene = new Scene(p, 450, 250);
	 
	        stage.setScene(scene);
	        
	        stage.show();
	        
	        
	        
	        
		
	}

}
