package requerimentos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entidades.Endereco;
import entidades.FinalidadeRequerida;
import entidades.FormaCaptacao;
import entidades.LocalCaptacao;
import entidades.MetodoIrrigacao;
import entidades.RA;
import entidades.SubSistema;
import entidades.Subterranea;
import entidades.SubtipoOutorga;
import entidades.Superficial;
import entidades.TipoInterferencia;
import entidades.TipoOutorga;
import entidades.TipoPoco;
import entidades.Usuario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import util.MalaDiretaUnica;

public class RequerimentoObjeto extends Application {
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		List<Object[][]> listaMalaDireta = new ArrayList<Object[][]>();
		
		RA ra = new RA(1, "Sobradinho");
		Endereco endereco = new Endereco (1, "Colônia Agrícola, 100","72.555-666","Brasília","DF",-15.568922,-48.562356, ra);
		
		Usuario usuario = new Usuario(
			1, "Física", "João Gilberto", "cpf 111222333-56",
			"endCor Rua dos Brejos", "Ra Cor Sobradinho", "Brasília", "DF", "72.500-060","telUs3354-5669",
			"celUs9 9965-5623", "joaogil@gmail.com", "Aqualit Brasil", "telAqualit");
		
		FinalidadeRequerida fr = new FinalidadeRequerida(
				"Abastecimento Humano", "Rural", 10.0, 150.0, 1500.0,1700.0,
				1700.0, 1, 31,1800.0, 2, 32,1900.0, 3, 33, 
				2000.0, 4, 34,2100.0, 5, 35,2200.0, 6, 36,
				2300.0, 7, 37,2400.0, 8, 38,2500.0, 9, 39,
				2600.0, 10, 40,2700.0, 11, 41,2800.0, 12, 42
				);
		
		
		TipoInterferencia tiSUB = new TipoInterferencia(2, "Subterranea");
		
		TipoOutorga to = new TipoOutorga(1, "Outorga");
		SubtipoOutorga so = new SubtipoOutorga(1, "Modificação");
		
		
		TipoPoco tipoPoco = new TipoPoco(1, "Manual");
		SubSistema subSis = new SubSistema(1, "Poroso");
		
		Date date = Date.valueOf("2020-03-11");
		
		// 2020-03-11
		
		/*
		Subterranea sub = new Subterranea(
				"Sim", 
				"180", 
				"170", 
				"9.000",
				"8.000", 
				7000.0, 
				"200",
				"PB_345", date, tipoPoco, subSis);
				
				sub.setInterTipoInterferenciaFK(tiSUB);
				
				*/
		
		FormaCaptacao fa = new FormaCaptacao(1, "Canal");
		LocalCaptacao la = new LocalCaptacao(1, "Rio");
		MetodoIrrigacao mi = new MetodoIrrigacao(1, "Bombeamento");
		
		
		Superficial sup = new Superficial(
				"mb Hering", "pb 3500cv", "ai550",
				" ac 550050", "ap 600", date, "SIM",
				"sim", "rio palmas", fa,
				la, mi); 
		
		TipoInterferencia tiSUP = new TipoInterferencia(1, "Superficial");
		
		sup.setInterTipoInterferenciaFK(tiSUP);
		sup.setInterTipoOutorgaFK(to);
		sup.setInterSubtipoOutorgaFK(so);
		
		sup.getFinalidades().add(fr);
		sup.setInterDDLatitude(-15.565656);
		sup.setInterDDLongitude(-48.565544);
		
		
		Object[][] dados = new Object [][] {
			{
			null,
			endereco,
			sup,
			usuario,
			
			},
		} ;
		
		listaMalaDireta.add(dados);
		
		/*
		BufferedReader html = new BufferedReader(
			     new FileReader("C:\\Users\\fabricio.barrozo\\eclipse-workspace\\adasa6\\src\\test\\java\\requerimentos\\requerimento_subterranea.html"));
	
		System.out.println(html);
		*/
		
		
		BufferedReader br = new BufferedReader( new FileReader("C:\\Users\\fabricio.barrozo\\eclipse-workspace\\adasa6\\src\\test\\java\\requerimentos\\requerimento_subterranea.html"));
		
		BufferedReader brSUP = new BufferedReader( new FileReader("C:\\Users\\fabricio.barrozo\\eclipse-workspace\\adasa6\\src\\test\\java\\requerimentos\\requerimento_superficial.html"));
		
		
		String html = "";

        String s = "";
        while (null != ((s = brSUP.readLine()))) {
            html+=s;
        }
        
        System.out.println(html);
        br.close();
		
		MalaDiretaUnica ml = new MalaDiretaUnica(listaMalaDireta, html);
				
		System.out.println(ml.criarDocumento());
				
			stage.setTitle("Exemplo Leitura HTML");

	        WebView webView = new WebView();

	        webView.getEngine().loadContent(ml.criarDocumento());

	        VBox vBox = new VBox(webView);
	        Scene scene = new Scene(vBox, 960, 600);

	        stage.setScene(scene);
	        stage.show();
		
	}

}
