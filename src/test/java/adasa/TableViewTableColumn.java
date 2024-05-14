package adasa;

import java.util.ArrayList;
import java.util.List;

//import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;

import dao.DocumentoDao;
import entidades.Documento;
import entidades.Endereco;
import entidades.Interferencia;
import entidades.Usuario;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TableViewTableColumn extends Application {

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TableView<Usuario> table = new TableView<Usuario>();
		
        // Editable
        table.setEditable(true);
 
        TableColumn<Usuario, String> tcUsuario //
                = new TableColumn<Usuario, String>("Nome");
 
        TableColumn<Usuario, Endereco> tcEndereco//
                = new TableColumn<Usuario, Endereco>("Endereços");
 
        TableColumn<Usuario, Interferencia> tcInterferencia//
                = new TableColumn<Usuario, Interferencia>("Interferências");
    
        tcUsuario.setMinWidth(200);
        tcEndereco.setMinWidth(200);
        tcInterferencia.setMinWidth(200);
        
        		ObservableList<Usuario> obsListUsuario = FXCollections.observableArrayList();
        		ObservableList<Endereco> obsListEndereco = FXCollections.observableArrayList();
        		ObservableList<Interferencia> obsListInterferencia = FXCollections.observableArrayList();
        	 
        		table.setItems(obsListUsuario);
        		
        	     	tcUsuario.setCellValueFactory(new PropertyValueFactory<>("usNome"));
        	     	
        	     	/*
        	     		tcEndereco.setCellValueFactory(new Callback<CellDataFeatures<Usuario, Endereco>, ObservableValue<Endereco>>() {
        	     			 
        	                @Override
        	                public ObservableValue<Endereco> call(CellDataFeatures<Usuario, Endereco> param) {
        	                	
        	                	Usuario us = param.getValue();
        	                    // F,M
        	                    //String genderCode = person.getGender();
        	                    Endereco end = us.getEnderecos().iterator().next();
        	                    return new SimpleObjectProperty<Endereco>(end);
        	                }
        	            });
        	            */
        	     		
        	     	//	tcEndereco.setCellFactory(ComboBoxTableCell.forTableColumn(obsListEndereco));
        	     			//tcInterferencia.setCellFactory(ComboBoxTableCell.forTableColumn(obsListInterferencia));

        	     
        	        
        	     	/*
        	     	tcEndereco.setOnEditCommit(
        	                new EventHandler<CellEditEvent<Usuario, Endereco>>() {

								@Override
								public void handle(CellEditEvent<Usuario, Endereco> u) {
									
									obsListEndereco.clear();
									obsListEndereco.addAll(u.getNewValue().getEndUsuarioFK().getEnderecos());
									System.out.println( u.getNewValue().getEndUsuarioFK().getUsLogadouro());
								}
        	                   
        	                }
        	            );
        	            */
        	    
        	     	tcEndereco.setCellValueFactory(new Callback<CellDataFeatures<Usuario, Endereco>, ObservableValue<Endereco>>() {
        	     		 
        	            @Override
        	            public ObservableValue<Endereco> call(CellDataFeatures<Usuario, Endereco> param) {
        	            	
        	            	Usuario us = param.getValue();
        	            	
        	            	//Endereco end = us.getEnderecos().iterator().next();
        	            	
        	          	   	obsListEndereco.clear();
        	          	  
         	     	    	obsListEndereco.addAll(us.getEnderecos());
         	     	
         	     	    	
         	     	    	return null;
         	     	    	//return new SimpleObjectProperty<Endereco>(us.getEnderecos().iterator().next());
        	           
        	            }
        	        });
        	     	
        	     	 tcEndereco.setCellFactory(ComboBoxTableCell.forTableColumn(obsListEndereco));
        	     	 
        	     	 /*
        	     	tcEndereco.setCellFactory(ComboBoxTableCell.<Usuario, Endereco> forTableColumn(new StringConverter<Endereco>() {

        	            @Override
        	            public String toString(Endereco e) {
        	                if (e == null) {
        	                    return null;
        	                }
        	                return e.getEndLogradouro();
        	            }

						@Override
						public Endereco fromString(String string) {
							return null;
						}

        	        }) ); 
        	        */
        	     
        	       
        	  
        	     	/*
        	     	tcEndereco.setCellValueFactory(new Callback<CellDataFeatures<Usuario, Endereco>, ObservableValue<Endereco>>() {
        	     	     public ObservableValue<Endereco> call(CellDataFeatures<Usuario, Endereco> u) {
							
        	     	    	 
        	     	    	 obsListEndereco.clear();

        	     	    	 for (Endereco e : u.getValue().getEnderecos()) {
        	     	    		System.out.println(contador++ + e.getEndLogradouro());
        	     	    		 obsListEndereco.add(e);
        	     	    	 }
        	     	    	 
        	     	    	 return null;
        	     	       
        	     	     
        	     	    	 
        	     	     }
        	     	     
        	     	     
        	     	  });
        	     	  */
        	        
        	        table.getColumns().add(tcUsuario);
        	        table.getColumns().add(tcEndereco);
        	        table.getColumns().add(tcInterferencia);
        	        
        	        DocumentoDao docDao = new DocumentoDao();
        	        
        	        	List<Documento> listDoc = docDao.listarDocumentos("");
        	        
        	        	List<Usuario> listUsuarios = new ArrayList<>();
	        	       
	        	        for (Documento d : listDoc ) {
	        	        	listUsuarios.addAll(d.getUsuarios());
	        	        
	        	        //System.out.println("loop for doc");
	        	        }
        	        
	        	        for (Usuario u : listUsuarios ) {
	        	  
	        	        	obsListUsuario.add(u);
	        	        	//System.out.println(u.getUsNome());
	        	        	// System.out.println("loop for usuario");
	        	        	//obsListEndereco.addAll(u.getEnderecos());
	        	        }
        	     
        	        Scene scene = new Scene(table);
        	        primaryStage.setTitle("Table View Sample");
        	        primaryStage.setWidth(800);
        	        primaryStage.setHeight(800);	
        	        
        	        
        	        primaryStage.setScene(scene);
        	        primaryStage.show();
		
	}
	
   	int contador = 1;
}
