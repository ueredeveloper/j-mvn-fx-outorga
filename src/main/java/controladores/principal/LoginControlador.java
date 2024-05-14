package controladores.principal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.ColaboradorDao;
import entidades.Colaborador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import principal.Alerta;
import principal.Componentes;

public class LoginControlador implements Initializable {

	
	@FXML AnchorPane paPrincipalLogin;
	Pane p;
	TabPane tpLogin;
	
	
	ArrayList<Node> listComponentes;
	Componentes com;
	Double[][] prefSizeWHeLayXY;
	
	Pane pLogin;
	TextField tfNomeUsuario;
	PasswordField  pfSenha;
	Button btnEntrar;
	
	Pane pCadastro;
	TextField tfNome;
	TextField tfID;
	TextField tfEmail;
	PasswordField  pfSenhaCadastro;
	PasswordField  pfConfirmarSenhaCadastro;
	Button btnCadastroUsuario;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
        TabPane tabpane = new TabPane(); 
  
            Tab tabLogin = new Tab("   Login    "); 
            	tabLogin.setClosable(false);
            	Pane pTabLogin = new Pane();
            	tabLogin.setContent(pTabLogin);
            Tab tabCadastro = new Tab("Cadastro"); 
            	tabCadastro.setClosable(false);
            	Pane pTabCadastro= new Pane();
            	tabCadastro.setContent(pTabCadastro);

            listComponentes = new ArrayList<Node>();
            
           		listComponentes.add(p = new Pane());	
            	listComponentes.add(tfNomeUsuario = new TextField(System.getProperty("user.name")));
            	listComponentes.add(pfSenha = new PasswordField());
            		pfSenha.setPromptText("Senha");
            	listComponentes.add(btnEntrar = new Button("Entrar"));
            
            	prefSizeWHeLayXY = new Double[][] { 

	    			{350.0,300.0,125.0,36.0},
	    			{300.0,40.0,25.0,80.0},
	    			{300.0,40.0,25.0,130.0},
	    			{300.0,40.0,25.0,180.0},
            	};

	    		com = new Componentes();
	    		com.popularTela(listComponentes, prefSizeWHeLayXY, pTabLogin);
	    		
	    		
	    		listComponentes = new ArrayList<Node>();
	            
           		listComponentes.add(p = new Pane());	
            	listComponentes.add(tfNome = new TextField());
            		tfNome.setPromptText("Nome");
            	listComponentes.add(tfID = new TextField(System.getProperty("user.name")));

            	listComponentes.add(tfEmail = new TextField());
            		tfEmail.setPromptText("Email");
            	listComponentes.add(pfSenhaCadastro = new PasswordField());
            		pfSenhaCadastro.setPromptText("Senha");
            	listComponentes.add(pfConfirmarSenhaCadastro = new PasswordField());
            		pfConfirmarSenhaCadastro.setPromptText("Confirmar Senha");
            	listComponentes.add(btnCadastroUsuario = new Button("Cadastrar"));
            	
            	prefSizeWHeLayXY = new Double[][] { 

	    			{350.0,320.0,125.0,26.0},
	    			{300.0,40.0,25.0,16.0},
	    			{300.0,40.0,25.0,66.0},
	    			{300.0,40.0,25.0,116.0},
	    			{300.0,40.0,25.0,166.0},
	    			{300.0,40.0,25.0,216.0},
	    			{300.0,40.0,25.0,266.0},
            	};

	    		com = new Componentes();
	    		com.popularTela(listComponentes, prefSizeWHeLayXY, pTabCadastro);
	    		
	    		
	    		/*
	    		 * Buscar apenas clicando no enter do teclado
	    		 */
	    		pfSenha.setOnKeyReleased(event -> {
	    			if (event.getCode() == KeyCode.ENTER){
	    				btnEntrar.fire();
	    			}
	    		});
	    		
	    		
	    		/*
	    		 * Buscar apenas clicando no enter do teclado
	    		 */
	    		pfConfirmarSenhaCadastro.setOnKeyReleased(event -> {
	    			if (event.getCode() == KeyCode.ENTER){
	    				btnCadastroUsuario.fire();
	    			}
	    		});
	    		
	    		
        
            tabpane.getTabs().addAll(tabLogin , tabCadastro ); 
            
        listComponentes = new ArrayList<Node>();

		listComponentes.add(p = new Pane());
		listComponentes.add(tabpane);
	
		prefSizeWHeLayXY = new Double[][] { 

			{600.0,400.0,0.0,0.0},
			{600.0,400.0,0.0,0.0},
			
		};

		com = new Componentes();
		com.popularTela(listComponentes, prefSizeWHeLayXY, paPrincipalLogin);
		
	
		btnEntrar.setOnAction(new EventHandler<ActionEvent>() {
           
			@Override
			public void handle(ActionEvent event) {
				
				if (logarColaborador().equals("Success")) {

				 try {

	                    //add you loading or delays - ;-)
	                    Node node = (Node) event.getSource();
	                    Stage stage = (Stage) node.getScene().getWindow();
	                    //stage.setMaximized(true);
	                    stage.close();
	                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/principal/Principal.fxml")));
	                    stage.setScene(scene);
	                    
	                    // Redimensionamento da do stage de acordo com as dimensões do monitor
	                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	                    
	                    stage.setX(primaryScreenBounds.getMinX());
	                    stage.setY(primaryScreenBounds.getMinY());
	                    stage.setWidth(primaryScreenBounds.getWidth());
	                    stage.setHeight(primaryScreenBounds.getHeight());
	                    
	                    stage.setMinHeight(668);
	                    stage.setMinWidth(1266);
	                    
	                    stage.setTitle("20/09/2021!!!"); 
	                    
	                    stage.show();

	                } catch (IOException ex) {
	                    System.err.println(ex.getMessage());
	                    System.out.println(ex);
	                }
				 
				}
				
				else {
					
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.CONFIRMATION, "Login não encontrado!!!", ButtonType.OK));
					
					
				}

			}
        });
	
		btnCadastroUsuario.setOnAction(new EventHandler<ActionEvent>() {
	           
			@Override
			public void handle(ActionEvent event) {
				
				if (! pfConfirmarSenhaCadastro.getText().equals(pfSenhaCadastro.getText())) {
					
					Alerta a = new Alerta ();
					a.alertar(new Alert(Alert.AlertType.CONFIRMATION, "Senhas não correspondem!!!", ButtonType.OK));
					
					pfConfirmarSenhaCadastro.setText("");
					
				} else {
					
					cadastrarColaborador();
					
				}
			
			}
        });
   
		
	} // fim initialize

	// metodo de de login verificando se usuario existe
    public  String logarColaborador () {
    	
        String status = "Exception";
        
        String strNomeUsuario = tfNomeUsuario.getText();
        String strSenha = pfSenha.getText();
 
        if(strNomeUsuario.isEmpty() || strSenha.isEmpty()) {
        	
        	Alerta a = new Alerta();
			a.alertar(new Alert(Alert.AlertType.ERROR, "Nome de Usuário ou Senha Inválidos !!!", new ButtonType[] { ButtonType.OK }));
			
            status = "Error";
            
        } else {
            
        	ColaboradorDao colDao = new ColaboradorDao();
        
	            try {
	            	
	            	if (colDao.verificarSenha(strNomeUsuario, strSenha) ==  1 ) {
	            		
	            		status = "Success";
	            	}
	            	
	            } catch (Exception ex) {
	                System.err.println(ex.getMessage());
	                
	                status = "Exception";
	            }
        	
        }
  
        return status;
    }
    
    
	// metodo de de login verificando se usuario existe
    public void cadastrarColaborador () {
   
        if(tfNome.getText().isEmpty() || tfID.getText().isEmpty()
        		
        		 || tfEmail.getText().isEmpty() || pfSenhaCadastro.getText().isEmpty() 
        		 
        		 || pfConfirmarSenhaCadastro.getText().isEmpty()
        		
        		) {
        	
        	Alerta a = new Alerta();
			a.alertar(new Alert(Alert.AlertType.ERROR, " Dados Inválidos !!!", new ButtonType[] { ButtonType.OK }));
			
        } else {
            
        	ColaboradorDao colDao = new ColaboradorDao();
        	
            try {
            	
            	Colaborador colaborador = colDao.verificarExistenciaColaborador(tfID.getText(),  tfEmail.getText());
            
            	if (colaborador != null ) {
            		
            		Colaborador col = new Colaborador(
            				colaborador.getColID(), 
            				colaborador.getColNome(), 
            				colaborador.getColNomeUsuario(), 
            				colaborador.getColEmail(), 
            				"0" 
            				);
            		
            		try {
            			
            			colDao.mergeColaborador(col, pfSenhaCadastro.getText());
	            		
		    			
		    				Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.CONFIRMATION, "Colaborador: "+col.getColNome()+", editado!!!", ButtonType.OK));
						;
							tfNome.setText("");
							tfID.setText("");
		    				tfEmail.setText("");
		    				pfSenhaCadastro.setText("");
		    				pfConfirmarSenhaCadastro.setText("");
		    			
            		}catch (Exception e) {
            			
	            			Alerta a = new Alerta ();
	    					a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar!!!", ButtonType.OK));
            			
            		}
            		
          
            		
            	} else {
            		
            		Colaborador col = new Colaborador(
            				
            				tfNome.getText(), 
            				tfID.getText(), 
            				tfEmail.getText(),  
            				"0"
            				
            		);
            		
            		
            		try {
	
            			colDao.salvarColaborador(col, pfSenhaCadastro.getText());
	            		
		    				
		    				Alerta a = new Alerta ();
							a.alertar(new Alert(Alert.AlertType.CONFIRMATION, "Colaborador: "+col.getColNome()+", cadastrado!!!", ButtonType.OK));
							
							tfNome.setText("");
							tfID.setText("");
		    				tfEmail.setText("");
		    				pfSenhaCadastro.setText("");
		    				pfConfirmarSenhaCadastro.setText("");;
						
            		}catch (Exception e) {
            			
	            			Alerta a = new Alerta ();
	    					a.alertar(new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar!!!", ButtonType.OK));
            			
            		}
            		
            	}
            	
            	
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
           
            }
        }
   
    }
    
    

	 
}
