package util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import controladores.principal.TabUsuarioControlador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import principal.Componentes;
import principal.MalaDiretaAnexoParecer;

public class NavegadorExterno {

	Pane pNavegadorExterno;
	
	Button btnBrowser, btnAtoAdministrativo, btnAtoAdministrativoAnexo, btnCapturaHTML, btnInsereHTML, btnWebDriver;

	ObservableList<Integer> obsListIframe = FXCollections
			.observableArrayList(0,1,2);
	ComboBox<Integer> cbIframe; 

	ComboBox<String> cbUsuarios;
	
	Componentes com;
	ArrayList<Node> listaDeComponentes;
	Double prefSizeWHeLayXY [][];

	String strWebDriver;
	Registro r;

	// Navegador Externo
	//static TabUsuarioControlador tabUs;
	
	String strHTML;
	
	Pane p1;
	
	public NavegadorExterno (Pane p1) {
		this.p1 = p1;
	}

	public void  inicializarNavegadorExterno (Double layoutX, Double layoutY) {

		listaDeComponentes = new ArrayList<Node>();

		listaDeComponentes.add(pNavegadorExterno = new Pane());

		listaDeComponentes.add(btnBrowser = new Button("Chrome"));
		listaDeComponentes.add(btnAtoAdministrativo = new Button("Inserir"));
		listaDeComponentes.add(cbIframe = new ComboBox<Integer>(obsListIframe));
		listaDeComponentes.add(cbUsuarios = new ComboBox<String>(this.obsListUsuariosMalaDireta));
		listaDeComponentes.add(btnAtoAdministrativoAnexo = new Button("Anexo"));
		
		listaDeComponentes.add(btnCapturaHTML = new Button("Capturar"));
		listaDeComponentes.add(btnInsereHTML = new Button("Inserir"));
		
		
		listaDeComponentes.add(btnWebDriver = new Button("..."));
		
		// Layout do Pane layoutX,layoutY

		prefSizeWHeLayXY = new Double [][]  { 

			{930.0,70.0,layoutX,layoutY},
		
			{70.0,25.0,5.0,23.0},
			{70.0,25.0,85.0,23.0},
			{70.0,25.0,165.0,23.0},
			{400.0,25.0,245.0,23.0},
			{70.0,25.0,655.0,23.0},
			{70.0,25.0,735.0,23.0},
			{70.0,25.0,815.0,23.0},
			{30.0,15.0,895.0,23.0},
			
		}; 

		//posicao do pane 25.0,772.0},
		// string html


		com = new Componentes();
		com.popularTela(listaDeComponentes, prefSizeWHeLayXY, p1);

		cbIframe.setValue(2);


		btnBrowser.setOnAction((event) -> {

			Registro registro = new Registro();

			Properties prop = new Properties();
			
			try {
				prop = registro.lerRegistro();
				
				strWebDriver = prop.getProperty("strWebDriver");
			
			} catch (Exception e1) {
				
			}
		
			System.setProperty("webdriver.chrome.driver", strWebDriver);

			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-infobars");
			options.addArguments("start-maximized");

			TabUsuarioControlador.webDriver = new ChromeDriver(options);

			TabUsuarioControlador.webDriver.navigate().to("https://sei.df.gov.br/sip/login.php?sigla_orgao_sistema=GDF&sigla_sistema=SEI");
			
			 
			TabUsuarioControlador.webDriver.findElement(By.id("selOrgao")).sendKeys("a");
            
			//TabUsuarioControlador.webDriver.findElement(By.id("txtUsuario")).sendKeys("fabricio.barrozo");;
			//TabUsuarioControlador.webDriver.findElement(By.id("pwdSenha")).sendKeys("");
           
			//TabUsuarioControlador.webDriver.findElement(By.id("sbmLogin")).click();


		}); // btnBrowser


		// ação do botão para receber o excel
		btnWebDriver.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				
				Registro registro = new Registro();
				
				String strArquivoWebDriver = null;
				
				Properties prop = new Properties();
				
				try {
					prop = registro.lerRegistro();
			
					
				} catch (Exception e1) {
			
					try {
						registro.salvarRegistro(prop);
					} catch (URISyntaxException e2) {
				
						e1.printStackTrace();
					} catch (IOException e2) {
				
						e1.printStackTrace();
					}
					
					//System.out.println("salvar webdriver ");
					
				}
		
				// para escolher o arquivo no computador
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Selecione o arquivo excel");
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter("EXE" , "*.exe"));
				File file = fileChooser.showOpenDialog(null);
				

				//  link do diretorio do web driver 0;  link com diretorio do arquivo excel 1;
				strArquivoWebDriver = file.toString();
			
				prop.setProperty("strWebDriver", strArquivoWebDriver);

				Registro rSalvar = new Registro();

				try {
					rSalvar.salvarRegistro(prop);
					
				} catch (URISyntaxException e3) {

					e3.printStackTrace();
				} catch (IOException e3) {

					e3.printStackTrace();
				}


			}



		}); // fim btnWebBrowser


		btnAtoAdministrativo.setOnAction((event) -> {

			Set<String> s1 = TabUsuarioControlador.webDriver.getWindowHandles();

			List<String> strList = new ArrayList<>();

			for (String s : s1) {
				strList.add(s);
			}

			TabUsuarioControlador.webDriver.switchTo().window(strList.get(1));

			JavascriptExecutor js = (JavascriptExecutor) TabUsuarioControlador.webDriver;

			//System.out.println("cbiframe value " + cbIframe.getValue());

			//System.out.println(strHTML);

			// retirar todas as haspas duplas "
			strHTML = strHTML.replace("\"", "");
			// trocar todas a haspa simples ' por haspas duplas "
			strHTML = strHTML.replace("'", "\"");

			js.executeScript("document.getElementsByTagName('iframe')['"+cbIframe.getValue()+"'].contentDocument.body.innerHTML = '"+strHTML+"';");
		

		});
		
		// selecionar o usuario que será impresso no anexo do paracer coletivo
		cbUsuarios.getSelectionModel().selectedIndexProperty().addListener(new
 	            ChangeListener<Number>() {
 	    	public void changed(@SuppressWarnings("rawtypes") ObservableValue ov,
 	    		Number value, Number new_value) {
 	    		
 	    		if ( (Integer) new_value !=  -1)
 	    		int_interferencia_selecionada = (int) new_value;
 	    		
             }
 	    });
		
		
		btnAtoAdministrativoAnexo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	MalaDiretaAnexoParecer anexo = new MalaDiretaAnexoParecer(listMalaDireta, strAnexoParecer, strTabela1, strTabela2);
            	String strAnexo = anexo.criarAnexoParecer(int_interferencia_selecionada);
            	
            	//System.out.println("anexo criar anexo " + strAnexo);
            	
    			// retirar todas as haspas duplas "
            	strAnexo = strAnexo.replace("\"", "");
    			// trocar todas a haspa simples ' por haspas duplas "
            	strAnexo = strAnexo.replace("'", "\"");
          
            	
            	Set<String> s1 = TabUsuarioControlador.webDriver.getWindowHandles();

    			List<String> strList = new ArrayList<>();

    			for (String s : s1) {
    				strList.add(s);
    			}

    			TabUsuarioControlador.webDriver.switchTo().window(strList.get(1));

    			JavascriptExecutor js = (JavascriptExecutor) TabUsuarioControlador.webDriver;
    			
    			//System.out.println(strAnexo);
    			
        		//-- imprimir o relatório ou tn no editor do SEI --//
    			js.executeScript("document.getElementsByTagName('iframe')[2].contentDocument.body.lastElementChild.innerHTML = '" + strAnexo + "';");
					
            }
        });
		
		
		/**
		 * Capturar o ato editado e inserir em outros locais necessarios
		 */
		btnCapturaHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	Set<String> s1 = TabUsuarioControlador.webDriver.getWindowHandles();

    			List<String> strList = new ArrayList<>();

    			for (String s : s1) {
    				strList.add(s);
    			}
    			
    			TabUsuarioControlador.webDriver.switchTo().window(strList.get(1));
            	
            	JavascriptExecutor js = (JavascriptExecutor) TabUsuarioControlador.webDriver;
            
            	strParecerCapturado = (String) js.executeScript("return document.getElementsByTagName('iframe')['"+cbIframe.getValue()+"'].contentDocument.body.innerHTML.toString()");
            	
            }
        });
		
		/**
		 * Inserir o ato capturado onde  for necessario. O tecnico edita um parecer e quer inseri-lo em outros documentos
		 */
		btnInsereHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	strParecerCapturado = strParecerCapturado.replace("\"", "'");
            	strParecerCapturado = strParecerCapturado.replace("\n", "");

            	strParecerCapturado =  "\"" + strParecerCapturado + "\"";
            	
            	Set<String> s1 = TabUsuarioControlador.webDriver.getWindowHandles();

    			List<String> strList = new ArrayList<>();

    			for (String s : s1) {
    				strList.add(s);
    			}

    			TabUsuarioControlador.webDriver.switchTo().window(strList.get(1));
            	
            	
            	JavascriptExecutor js = (JavascriptExecutor) TabUsuarioControlador.webDriver;
                
            	js.executeScript("document.getElementsByTagName('iframe')[2].contentDocument.body.innerHTML = " + strParecerCapturado + ";");
     
            	/*
        			//-- imprimir o relatório ou tn no editor do SEI --//
            		webViewPopUp.getEngine().executeScript(
            				"document.getElementsByTagName('iframe')[2].contentDocument.body.innerHTML = " + strParecerCapturado + ";"
	            			);
					
					*/
            }
        });
		
		

	}
	
	
	// endereco da pasta onde está o link do webdriver chrome
	String strArquivoWebDriver = "";
	String strParecerCapturado = "";
	
	public void setarStringHTML (String strHTML) {
		this.strHTML = strHTML;
	}
	
	String strAnexoParecer;
	
	String strTabela1;
	String strTabela2;
	
	ObservableList<String> obsListUsuariosMalaDireta;
	
	String strAnexo;
	
	List<Object[][]> listMalaDireta = new ArrayList<>();
	
	// selecionar qual anexo ira para o parecer coletivo
	int int_interferencia_selecionada = 0;
	
	public void setObjetosAnexo (List<Object[][]> listMalaDireta, ObservableList<String>  obsListUsuariosMalaDireta, String strAnexoParecer, String strTabela1, String strTabela2) {
		this.listMalaDireta = listMalaDireta;
		this.obsListUsuariosMalaDireta = obsListUsuariosMalaDireta;
		this.strAnexoParecer = strAnexoParecer;
		this.strTabela1 = strTabela1;
		this.strTabela2 = strTabela2;
		
		cbUsuarios.setItems(this.obsListUsuariosMalaDireta);
		
	}
	

	
}
