package adasa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entidades.BaciasHidrograficas;
import entidades.Documento;
import entidades.Endereco;
import entidades.FinalidadeAutorizada;
import entidades.Interferencia;
import entidades.RA;
import entidades.Subterranea;
import entidades.Superficial;
import entidades.UnidadeHidrografica;
import entidades.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import principal.MalaDiretaAtosOutorga;

public class TesteCriacaoAtosOutorga extends Application {

	public static void main(String[] args) {
		
		launch(args);
	}
	
	WebEngine we = new WebEngine();
	
	String strHTML = "";
	

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		Documento documento = new Documento();
		
		documento.setDocNumeracao("12345679");
		documento.setDocProcesso("197.555.666/2015");
		
		Endereco endereco = new Endereco();
		
		endereco.setEndLogradouro("Rua das Couves, 300");
		endereco.setEndRAFK(new RA(1, "Plano Piloto"));
		endereco.setEndCEP("20666-444");
		
		
		Usuario usuario = new Usuario();
		
		usuario.setUsNome("Jorge Bernardes");
		usuario.setUsCPFCNPJ("222.555.666-58");
		usuario.setUsTelefone("3355-0056");
		usuario.setUsCelular("9 9955-6688");
		usuario.setUsLogadouro("Rua dos Barris");
		usuario.setUsCEP("72555-666");
		usuario.setUsEmail("jorge@gmail.com");
		
		
	
		
		FinalidadeAutorizada finAut = new FinalidadeAutorizada();
		
		finAut.setFaFinalidade1("Abastecimento Humano");
		finAut.setFaConsumo1(123.0);
		finAut.setFaSubfinalidade1("Rural");
		
		finAut.setFaQDiaJan(66915.0);
		finAut.setFaQHoraJan(8);
		finAut.setFaTempoCapJan(25);
		
		BaciasHidrograficas bacia = new BaciasHidrograficas(1, "Bartolomeu");
		UnidadeHidrografica uh = new UnidadeHidrografica(1, 30);
		
		Subterranea sub = new Subterranea();
		
		sub.setInterDDLatitude(-15.000);
		sub.setInterDDLongitude(-47.0);
		sub.setSubVazaoOutorgada(600.0);
		
		sub.getFinalidades().add(finAut);	
		
		sub.setInterBaciaFK(bacia);
		sub.setInterUHFK(uh);
		
		ObservableList<Interferencia> tvObsListInterferencia = FXCollections.observableArrayList();
		tvObsListInterferencia.add(sub);
		
		
		/*
		Superficial sup = new Superficial();
		sup.setInterDDLatitude(-15.000);
		sup.setInterDDLongitude(-47.0);
		
		sup.getFinalidades().add(finAut);	
		
		sup.setInterBaciaFK(bacia);
		sup.setInterUHFK(uh);
		
		ObservableList<Interferencia> tvObsListInterferencia = FXCollections.observableArrayList();
		tvObsListInterferencia.add(sup);
		*/
		
		

		String modeloTabela = "/testeHTML/tabelaLimitesOutorgados";
		String modeloHTMLRenovacao = "/testeHTML/modeloRenovacaoOutorga";
		
		String modeloHTML = capturarHTML (modeloHTMLRenovacao);
		String modeloTabelaLimitesOutorgados = capturarHTML(modeloTabela);
		
		
		MalaDiretaAtosOutorga mlDoc = new MalaDiretaAtosOutorga(modeloHTML, modeloTabelaLimitesOutorgados, documento, endereco, usuario, tvObsListInterferencia);
		
		String strHTML = mlDoc.criarAtoOutorga();
		
		
		
	}
	
	
	public String capturarHTML (String link) throws FileNotFoundException, IOException {
		
		File file = new File(getClass().getResource(link).getFile());
		
		String strHTML = "";
		
		 try (FileReader reader = new FileReader(file);
	             BufferedReader br = new BufferedReader(reader)) {

	            String line;
	            while ((line = br.readLine()) != null) {
	                strHTML = strHTML + line;
	          }
	     }
	
		return strHTML;
		
	}

		
}

