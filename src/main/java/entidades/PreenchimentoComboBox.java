package entidades;

import java.util.ArrayList;

import dao.EnderecoDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class PreenchimentoComboBox {
	
	/**
	 * 
	 * @param endDao
	 * @param obsList
	 * @param list
	 * @param strVarivel
	 * @param obj
	 * @param cb
	 * 
	 * Com a classe EnderecoDao, preenchemos a ObservableList de um ComboBox, passando uma ArrayList e o nome da Variável da Tabela que busca buscar um getter específico desta tabela
	 * 
	 */
	public void preencherComboBox (
			EnderecoDao endDao, ObservableList<String> obsList, ArrayList<Object> list, 
			String intVariavelTabelaID, String strVarivelTabelaDescricao, Object obj, ComboBox<String> cb

			) {
	
		if (!obsList.isEmpty()) {
			obsList.clear();
		}
		
		list = (ArrayList<Object>) endDao.listarObjeto(obj);
		
		GetterAndSetter gs  = new GetterAndSetter();
		
		for (Object o: list) {
			
			obsList.add(gs.callGetter(o, strVarivelTabelaDescricao));
			
		}
		
		cb.setItems(obsList);
		
		cb.getSelectionModel()
	    	.selectedItemProperty()
	    	.addListener( 
	    	(ObservableValue<? extends String> observable, String old_value, String new_value) ->
	    	
	    	gs.callSetter(obj, strVarivelTabelaDescricao, new_value)
	    	 
	    );
		
		cb.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	    	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
	    		Number old_value, Number new_value) {
	    		
	    		gs.callSetter(obj, intVariavelTabelaID, ((Integer) new_value + 1));
	    		
            }
		 });
			
	} // fim metodo preenchimento combobox
	

}
