package requerimentos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class CheckListMain {

	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		CheckList cl = new CheckList();
		
		cl.setClTipoOutorga("Outorga");
		cl.setClSubtipoOutorga("");
		
		//List<String> listHTML = new ArrayList<>();
		
		cl.getClItens().add(
					"<tr>"
				+ 	"<td colspan='5'>I . Requerimento de Outorga de Direito de Uso de &Aacute;gua Subterr&acirc;nea preenchido e assinado <span style='text-align: justify;'></span></td>"
				+	"<td style='text-align: center;vertical-align: middle;'><input style='width: 20px;height: 20px;' type='checkbox' /></td>" 
				+	"</tr>");
		
		
		
		Gson gson;
		
		gson = new GsonBuilder().setPrettyPrinting().create();
	
		String jsonInString = gson.toJson(cl);
		
		System.out.println(jsonInString);
		
		gson = new Gson();

		// 1. JSON file to Java object
		CheckList ch = gson.fromJson(new FileReader("C:\\Users\\fabricio.barrozo\\eclipse-workspace\\adasa6\\src\\test\\java\\checklist\\check.json"), CheckList.class);
		
		ch.getClItens().forEach(i-> System.out.println(i));

	}

}
