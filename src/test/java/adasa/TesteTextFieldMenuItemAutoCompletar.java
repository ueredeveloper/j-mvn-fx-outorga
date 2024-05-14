package adasa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import dao.BancoAccessDao;
import entidades.BancoAccess;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TesteTextFieldMenuItemAutoCompletar extends Application{

	public static void main(String[] args) {

		launch(args);
	}
	
	   List<BancoAccess> docList = new ArrayList<>();
	   
	   ContextMenu contextMenu  = new ContextMenu();

	@Override
	public void start(Stage stage) throws Exception {

	    Label label = new Label();
	    
        Circle circle = new Circle();
        circle.setRadius(80);
        circle.setFill(Color.AQUA);
        
        TextField tf = new TextField();
        tf.setPrefSize(400, 20);
        
        TextField tf2 = new TextField();
        tf.setPrefSize(400, 20);
        tf.setLayoutY(500);
        
        Rectangle rec = new Rectangle();
        rec.setWidth(400);
        tf.setLayoutY(500);
  
        
 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.setSpacing(5);
 
        root.getChildren().addAll(label, circle, tf, tf2);
        
        BancoAccess b1 = new BancoAccess();
        b1.setBaInteressado("Jorge");
    
        
        BancoAccess b2 = new BancoAccess();
        b2.setBaInteressado("João");
       
    	BancoAccessDao bDao = new BancoAccessDao();

    	//docList = bDao.listarBancoAccess(tf.getText());
    	int i = 0;
    	
    	contextMenu.setMaxWidth(300);
    	
    	
    	BancoAccess b2283 = new BancoAccess();b2283.setBaInteressado("JOSIMAR ARAUJO BARBOSA");b2283.setBaNumeroProcesso("197001333/2017");b2283.setBaEnderecoEmpreendimento("COLONIA AGRICOLA BURITI VERMELHO LOTE 29");
    	BancoAccess b2284 = new BancoAccess();b2284.setBaInteressado("ITAMAR JULIO DE REZENDE");b2284.setBaNumeroProcesso("197001334/2011");b2284.setBaEnderecoEmpreendimento("NUCLEO RURAL TAQUARA, CHACARA 28, PLANALTINA/DF");
    	BancoAccess b2285 = new BancoAccess();b2285.setBaInteressado("REGINALDO CALIMAN");b2285.setBaNumeroProcesso("197001334/2012");b2285.setBaEnderecoEmpreendimento("LOTE 18 TABATINGA, PLANALTINA");
    	BancoAccess b2286 = new BancoAccess();b2286.setBaInteressado("REGINA MARIA DE HOLANDA DA COSTA RIBEIRO");b2286.setBaNumeroProcesso("197001335/2015");b2286.setBaEnderecoEmpreendimento("SHIS QI 23 CONJUNTO 15 CASA 31 LAGO SUL-DF");
    	BancoAccess b2287 = new BancoAccess();b2287.setBaInteressado("ANGELA MARIA FERNANDES");b2287.setBaNumeroProcesso("197001336/2016");b2287.setBaEnderecoEmpreendimento("RODOVIA D 345, KM 4/5, NUCLEO RURAL PIPIRIPAU 2, CHACARA Nº2 - NOSSA SENHORA APARECIDA, PLANALTINA/DF");
    	BancoAccess b2288 = new BancoAccess();b2288.setBaInteressado("MANOEL FELIZARDO FILHO");b2288.setBaNumeroProcesso("197001337/2009");b2288.setBaEnderecoEmpreendimento("ASSENTAMENTO RECANTO DA CONQUISTA, CHÁCARA 14");

    	
    	BancoAccess b2289 = new BancoAccess();b2289.setBaInteressado("DALMIR ALVES DA SILVA");b2289.setBaNumeroProcesso("197001339/2012");b2289.setBaEnderecoEmpreendimento("SETOR DE MANSÕES PARAÍSO, CHÁCARA NO FINAL DO CONJUNTO L, GAMA/DF");
    	BancoAccess b2290 = new BancoAccess();b2290.setBaInteressado("NOVACAP");b2290.setBaNumeroProcesso("197001342/2012");b2290.setBaEnderecoEmpreendimento("CÓRREGO CORTADO BARRAGEM I E II, TAGUATINGA/DF");
    	BancoAccess b2291 = new BancoAccess();b2291.setBaInteressado("SÓLON LOPES PEREIRA");b2291.setBaNumeroProcesso("197001343/2009");b2291.setBaEnderecoEmpreendimento("SHIN QI 10 CONJUNTO 3 CASA 7");
    	BancoAccess b2292 = new BancoAccess();b2292.setBaInteressado("SUELI DE FÁTIMA MACIEL SANTOS FERREIRA");b2292.setBaNumeroProcesso("197001343/2011");b2292.setBaEnderecoEmpreendimento("CH VEREDA, N° 24, MORRO DA CRUZ, SÃO SEBASTIÃO /DF");
    	BancoAccess b2293 = new BancoAccess();b2293.setBaInteressado("LAURO THOMAS");b2293.setBaNumeroProcesso("197001343/2012");b2293.setBaEnderecoEmpreendimento("ÁREA F MODULO 17 PAD/DF");
    	BancoAccess b2294 = new BancoAccess();b2294.setBaInteressado("FUNDAÇÃO UNIVERSIDADE DE BRASÍLIA - UNB");b2294.setBaNumeroProcesso("197001345/2015");b2294.setBaEnderecoEmpreendimento("CAMPUS UNIVERSITÁRIO DARCY RIBEIRO GLEBA C ESTAÇÃO EXPERIMENTAL DE BIOLOGIA, ASA NORTE, BRASÍLIA-DF");
    	BancoAccess b2295 = new BancoAccess();b2295.setBaInteressado("DOUGLAS PAULINO LOPES FERNANDES");b2295.setBaNumeroProcesso("197001346/2017");b2295.setBaEnderecoEmpreendimento("CORREGO DO MEIO GLEBA 0231");
    	BancoAccess b2296 = new BancoAccess();b2296.setBaInteressado("ASSOCIAÇÃO DOS SERVIDORES DA SECRETARIA DE AGRICULTURA DO DF - ARCEF");b2296.setBaNumeroProcesso("197001348/2012");b2296.setBaEnderecoEmpreendimento("SAIN PARQUE RURAL - ASA NORTE/DF");
    	BancoAccess b2297 = new BancoAccess();b2297.setBaInteressado("COOPER MONTE VERDE COOPERATIVA HABITACIONAL");b2297.setBaNumeroProcesso("197001351/2012");b2297.setBaEnderecoEmpreendimento("GLEBA 04 LOTE 494, KM 18 BR 070 - PICAG, CEILÂNDIA/DF");
    	BancoAccess b2298 = new BancoAccess();b2298.setBaInteressado("JUSSANAN PORTELA DOS SANTOS");b2298.setBaNumeroProcesso("197001355/2010");b2298.setBaEnderecoEmpreendimento("BR 251 MODULO 1/10 AI CAVA DE CIMA");
    	BancoAccess b2299 = new BancoAccess();b2299.setBaInteressado("RAIMUNDO NONATO BRASIL");b2299.setBaNumeroProcesso("197001355/2015");b2299.setBaEnderecoEmpreendimento("SHIN QL 07 CONJUNTO 1 CASA 19");
    	BancoAccess b2300 = new BancoAccess();b2300.setBaInteressado("RAIMUNDO PEREIRA DA COSTA");b2300.setBaNumeroProcesso("197001363/2011");b2300.setBaEnderecoEmpreendimento("NR CÓRREGO ATOLEIRO, CH 001, SANTA BÁRBARA - PLANALTINA/DF");
    	BancoAccess b2301 = new BancoAccess();b2301.setBaInteressado("OSVALDO ERGANG");b2301.setBaNumeroProcesso("197001363/2016");b2301.setBaEnderecoEmpreendimento("NUCLEO RURAL TABATINGA CHACARA Nº 112");
    	BancoAccess b2302 = new BancoAccess();b2302.setBaInteressado("NIVALDO RIBEIRO DA SILVA");b2302.setBaNumeroProcesso("197001365/2011");b2302.setBaEnderecoEmpreendimento("CH 02, CAPÃO DA ONÇA -BRAZLÂNDIA/DF");
    	BancoAccess b2303 = new BancoAccess();b2303.setBaInteressado("SANDRA MARIA MARQUES SAMPAIO SILVA");b2303.setBaNumeroProcesso("197001367/2016");b2303.setBaEnderecoEmpreendimento("SMLN TRECHO 9, CONJUNTO 2, CASA 7-B, LAGO NORTE");
    	BancoAccess b2304 = new BancoAccess();b2304.setBaInteressado("CODHAB");b2304.setBaNumeroProcesso("197001368/2012");b2304.setBaEnderecoEmpreendimento("PARANOÁ PARQUE - PARANOÁ/DF");
    	BancoAccess b2305 = new BancoAccess();b2305.setBaInteressado("ROBERTO NOGUEIRA");b2305.setBaNumeroProcesso("197001369/2010");b2305.setBaEnderecoEmpreendimento("SMPW QD 29 CONJUNTO 4 LT 3 FRAÇÃO E");
    	BancoAccess b2306 = new BancoAccess();b2306.setBaInteressado("SECRETARIA DE ESTADO DE TRANSPORTES");b2306.setBaNumeroProcesso("197001372/2009");b2306.setBaEnderecoEmpreendimento("MARGENS DA ROD. EPTG-DF 085");
    	BancoAccess b2307 = new BancoAccess();b2307.setBaInteressado("SUPERINTENDÊNCIA REGIONAL DO INCRA DO DF");b2307.setBaNumeroProcesso("197001372/2012");b2307.setBaEnderecoEmpreendimento("SETOR DE GARAGENS E OFICINAS NORTE (SGON), QUADRA 05, LOTE 01, VIA 60 A, BRASÍLIA/DF");
    	BancoAccess b2308 = new BancoAccess();b2308.setBaInteressado("NATFRUIT AGRO INDUSTRIAL DE ALIMENTOS LTDA ME");b2308.setBaNumeroProcesso("197001372/2016");b2308.setBaEnderecoEmpreendimento("NUCLEO RURAL ALEXANDRE GUSMÃO, INCRA 6, GLEBA 2, CHACARA 234-A");
    	BancoAccess b2309 = new BancoAccess();b2309.setBaInteressado("JOSITA DE SOUZA ARAUJO");b2309.setBaNumeroProcesso("197001372/2017");b2309.setBaEnderecoEmpreendimento("ASSENTAMENTO CHAPADINHA, PARCELA Nº 17");
    	BancoAccess b2310 = new BancoAccess();b2310.setBaInteressado("ANTONIO LOPES MONTEIRO");b2310.setBaNumeroProcesso("197001373/2016");b2310.setBaEnderecoEmpreendimento("QUADRA L, CHACARA 28, MARANATA, CAPÃO II");
    	BancoAccess b2311 = new BancoAccess();b2311.setBaInteressado("CELENITA ANSELMO DE SIQUEIRA");b2311.setBaNumeroProcesso("197001374/2010");b2311.setBaEnderecoEmpreendimento("SHIN QL 03 CONJUNTO 05 CASA 05");
    	BancoAccess b2312 = new BancoAccess();b2312.setBaInteressado("GERALDO PIQUET SOUTO MAIOR");b2312.setBaNumeroProcesso("197001375/2012");b2312.setBaEnderecoEmpreendimento("FAZENDA PIQUET, DF 001, KM 26, BRASÍLIA/DF");
    	BancoAccess b2313 = new BancoAccess();b2313.setBaInteressado("MARIA LUZINETE DA SILVA CIPRIAN");b2313.setBaNumeroProcesso("197001377/2009");b2313.setBaEnderecoEmpreendimento("UNID. C, LT 03, CONJ 4, QD 01");
    	BancoAccess b2314 = new BancoAccess();b2314.setBaInteressado("ELEUSINA RODRIGUES SAMPAIO DE SOUZA");b2314.setBaNumeroProcesso("197001379/2011");b2314.setBaEnderecoEmpreendimento("CHÁCARA 29, RODOVIA DF - 130, 26.6 -  QUEBRA DOS GUIMARÃES, PERÍMETRO DF");
    	BancoAccess b2315 = new BancoAccess();b2315.setBaInteressado("IATE CLUBE DE BRASILIA");b2315.setBaNumeroProcesso("197001382/2015");b2315.setBaEnderecoEmpreendimento("SCEN TRECHO 02, CONJUNTO 04, ASA NORTE");
    	BancoAccess b2316 = new BancoAccess();b2316.setBaInteressado("IVO ILÁRIO RIEDI");b2316.setBaNumeroProcesso("197001383/2012");b2316.setBaEnderecoEmpreendimento("CAPTAÇÃO FEDERAL - BR 020, KM 52, DF 105 - PLANALTINA/DF");
    	BancoAccess b2317 = new BancoAccess();b2317.setBaInteressado("SUPER VAREJO COMÉRCIO DE ALIMENTOS EPP");b2317.setBaNumeroProcesso("197001384/2010");b2317.setBaEnderecoEmpreendimento("COMÉRCIO LOCAL QD 202  LT D1/D2/D3/D4 CEP 72502220");
    	BancoAccess b2318 = new BancoAccess();b2318.setBaInteressado("NELSON JOSÉ HUBNER MOREIRA");b2318.setBaNumeroProcesso("197001386/2009");b2318.setBaEnderecoEmpreendimento("NUCLEO RURAL LAGO OESTE RUA 5 CHÁCARA Nº 17 (ANTIGA CHÁCARA Nº 84)");
    	BancoAccess b2319 = new BancoAccess();b2319.setBaInteressado("MEIRE REJANE ANTUNES PEREIRA");b2319.setBaNumeroProcesso("197001387/2010");b2319.setBaEnderecoEmpreendimento("NUCLEO RURAL ALXANDRE GUSMÃO, INCRA 7, RESERVA F GLEBA 02");
    	BancoAccess b2320 = new BancoAccess();b2320.setBaInteressado("MARIA DO SOCORRO LIMA MARTINS");b2320.setBaNumeroProcesso("197001388/2009");b2320.setBaEnderecoEmpreendimento("N. R. TAQUARA, CHÁCARA N° 85");
    	BancoAccess b2321 = new BancoAccess();b2321.setBaInteressado("ANTONIO AMERICANO DO BRASIL");b2321.setBaNumeroProcesso("197001388/2012");b2321.setBaEnderecoEmpreendimento("NUCLEO RURAL COLOMBO CERQUEIRA, ESTRADA CACHOEIRINHA Nº 510");

    	
    	

    	docList.add(b2283);
    	docList.add(b2284);
    	docList.add(b2285);
    	docList.add(b2286);
    	docList.add(b2287);
    	docList.add(b2288);
    
    
    docList.add(b2289);
    docList.add(b2290);
    docList.add(b2291);
    docList.add(b2292);
    docList.add(b2293);
    docList.add(b2294);
    docList.add(b2295);
    docList.add(b2296);
    docList.add(b2297);
    docList.add(b2298);
    docList.add(b2299);
    docList.add(b2300);
    docList.add(b2301);
    docList.add(b2302);
    docList.add(b2303);
    docList.add(b2304);
    docList.add(b2305);
    docList.add(b2306);
    docList.add(b2307);
    docList.add(b2308);
    docList.add(b2309);
    docList.add(b2310);
    docList.add(b2311);
    docList.add(b2312);
    docList.add(b2313);
    docList.add(b2314);
    docList.add(b2315);
    docList.add(b2316);
    docList.add(b2317);
    docList.add(b2318);
    docList.add(b2319);
    docList.add(b2320);
    docList.add(b2321);

  
         
    	tf.textProperty().addListener((observable, oldValue, newValue) -> {

    		if (tf.getText().length() > 2) {

    			contextMenu.hide();

    			contextMenu = new ContextMenu();
    			contextMenu.setMaxWidth(300);
    			
    			tf.setContextMenu(contextMenu);
    		
    			for (BancoAccess b : docList) {
    				
    				if (
    						
    						(b.getBaInteressado() + "\n  | " + b.getBaNumeroProcesso() + "\n    | " + b.getBaEnderecoEmpreendimento()).toLowerCase()
    						
    						.indexOf(tf.getText().toLowerCase()) != -1) {
    					
    					System.out.println(b.getBaInteressado());
    					
    				Label lbl = new Label(b.getBaInteressado() + "\n  | " + b.getBaNumeroProcesso() + "\n    | " + b.getBaEnderecoEmpreendimento());
    				lbl.setPrefWidth(400);
    				lbl.setWrapText(true);
    				
    				MenuItem item = new MenuItem();
    				item.setGraphic(lbl);
    				
    				item.setOnAction(new EventHandler<ActionEvent>() {

    					@Override
    					public void handle(ActionEvent event) {

    						tf.setText(b.getBaInteressado());
    					}

    				});

    				// Add MenuItem to ContextMenu
    				contextMenu.getItems().add(item);
    				
    				}

    			} // fim loop for

    			contextMenu.show(tf, Side.RIGHT, 0, 0);
    			
    			//contextMenu.show(tf, tf2.getScene().getWindow().getX() + 300, tf2.getScene().getWindow().getY() + 250);
    		
    		} // fim if (tf.getText().length() > 3

    	});
    
 
        Scene scene = new Scene(root, 400, 400);
 
        stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();
		
	}

}



/* criacao de objetos para testar banco sem conexao

for (BancoAccess b : docList) {
	
	
	
	System.out.println(
			
				"BancoAccess b" + i + " = new BancoAccess();"
			+ 	"b" + i + ".setBaInteressado(\"" + b.getBaInteressado() + "\");"
			+  	"b" + i + ".setBaNumeroProcesso(\"" + b.getBaNumeroProcesso() +"\");"
			+ 	"b" + i + ".setBaEnderecoEmpreendimento(\"" + b.getBaEnderecoEmpreendimento() +"\");"
			
			);
	
	i++;
	
	
}
*/
