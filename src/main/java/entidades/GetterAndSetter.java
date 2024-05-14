package entidades;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class GetterAndSetter {
	
	/*
	 * Classe que utiliza a propriedade reflexion para os getters e setters semelhantes , como na entidade subterranea:
	 * 
	 * 	getSubSubfinCon1(), 
	 * 		getSubSubfinCon2(), 
	 * 			getSubSubfinCon3(),
	 * 				getSubSubfinCon4()...
	 */
	
	public void callSetter(Object obj, String fieldName, Object value){
		  
		PropertyDescriptor pd;
		
		try {
		pd = new PropertyDescriptor(fieldName, obj.getClass());
		pd.getWriteMethod().invoke(obj, value);
	
		//System.out.println("valor: " + value + " " + pd.getWriteMethod());
		
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		 
		
		
	} // fim metodo call Setter
	
	public String callGetter(Object obj, String fieldName){
		
		PropertyDescriptor pd;
		String s = null;
		try {
			pd = new PropertyDescriptor(fieldName, obj.getClass());
			s = "" + pd.getReadMethod().invoke(obj);
			//System.out.println("" + pd.getReadMethod().invoke(obj));
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		
			e.printStackTrace();
			
			System.out.println(e.toString());
		}
		
		return s;
		  
	} // fim metodo call Getter
	
}
