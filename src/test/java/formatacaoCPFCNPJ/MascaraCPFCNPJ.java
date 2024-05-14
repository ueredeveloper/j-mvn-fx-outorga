package formatacaoCPFCNPJ;

import java.text.ParseException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MascaraCPFCNPJ extends Application {

	public static void main(String[] args) {

		
		launch(args);
	}
	
	 
    CPFCNPJFormat ccFormato = new CPFCNPJFormat();
    String strCPFCNPJ = null;

	@Override
	public void start(Stage s) throws Exception {
		
		
		ObservableList<String> olTipoPessoa = FXCollections
				.observableArrayList("Física" , "Jurídica"); // box - seleção pessoa físcia ou jurídica

		ComboBox<String> cbTipoPessoa  = new ComboBox<String>(olTipoPessoa);

        TextField tfCPFCNPJ = new TextField(); 
        
        tfCPFCNPJ.setPrefSize(150, 30);
        tfCPFCNPJ.setLayoutX(120);
        tfCPFCNPJ.setLayoutY(70);
        
        cbTipoPessoa.setPrefSize(200, 30);
        cbTipoPessoa.setLayoutX(280);
        cbTipoPessoa.setLayoutY(70);
        
        cbTipoPessoa.setValue("Física");

        // create a stack pane 
        Pane p = new Pane();
  
        // add textfield 
        p.getChildren().addAll(cbTipoPessoa, tfCPFCNPJ ); 
  
        // create a scene 
        Scene sc = new Scene(p, 600, 200); 
  
        // set the scene 
        s.setScene(sc); 
  
        s.show(); 
       

        tfCPFCNPJ.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				
				if (newValue.intValue() > oldValue.intValue()) {
					
					strCPFCNPJ = tfCPFCNPJ.getText().replaceAll("\\D","");
		
					if (cbTipoPessoa.getValue().equals("Física")) {
					
					//  MASCARA CPF
					
					if ( strCPFCNPJ.length() == 11 ) {
					
						try {
							
							System.out.println("tf valor formatado " + ccFormato.formatCnpj(cbTipoPessoa.getValue(), tfCPFCNPJ.getText()));
							
							tfCPFCNPJ.setText(ccFormato.formatCnpj(cbTipoPessoa.getValue(), tfCPFCNPJ.getText())
											
						);
							
						} catch (ParseException e) {
						
							e.printStackTrace();
						}
						
						
					} // fim if ( (Integer )newValue == 11 ) 
					
					// LIMITE CPF
					if ( (Integer ) newValue > 14 ) {
						
						tfCPFCNPJ.setText(tfCPFCNPJ.getText().substring(0, 14));
						
					}
					
					} // fim if pessoa física
					
					if (cbTipoPessoa.getValue().equals("Jurídica")) {
						
						//  MASCARA CPF
						if ( strCPFCNPJ.length() == 14 ) {
						
							try {
								tfCPFCNPJ.setText(ccFormato.formatCnpj(cbTipoPessoa.getValue(), tfCPFCNPJ.getText())
												
							);
								
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
							
							
						} // fim if ( (Integer )newValue == 11 ) 
						
						// LIMITE CPF
						if ( (Integer ) newValue > 18 ) {
							
							tfCPFCNPJ.setText(tfCPFCNPJ.getText().substring(0, 18));
							
						}
						
						} // fim if pessoa física
			
					
				} // fim if newValue > 
			}
		}); //fim listerner textfield
        
	}
	
	
}
