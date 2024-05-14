package principal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entidades.Documento;
import entidades.Endereco;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.FinalidadeRequerida;
import entidades.GetterAndSetter;
import entidades.Subterranea;
import entidades.Superficial;
import entidades.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.FormatadorCPFCNPJ;

/** Classe de preechimento de html 
 * 
 * @author fabricio.barrozo
 *
 */
public class MalaDiretaDocumentos {

	String modeloHTML;

	List<String> listVariaveisFinalidadesRequeridas = Arrays.asList("frFinalidade1", "frFinalidade2", "frFinalidade3", "frFinalidade4", "frFinalidade5"); // abastecimento humano
	List<String> listVariaveisSubfinaldadesRequeridas = Arrays.asList("frSubfinalidade1", "frSubfinalidade2", "frSubfinalidade3", "frSubfinalidade4", "frSubfinalidade5"); // rural
	List<String> listVariaveisQuantidadesRequeridas = Arrays.asList("frQuantidade1", "frQuantidade2", "frQuantidade3", "frQuantidade4", "frQuantidade5"); // 10 pessoas
	List<String> listVariaveisConsumoRequeridas = Arrays.asList("frConsumo1", "frConsumo2", "frConsumo3", "frConsumo4", "frConsumo5"); // 1.000,00 listros dia por pessoa
	List<String> listVariaveisVazaoRequeridas = Arrays.asList("frVazao1", "frVazao2", "frVazao3", "frVazao4", "frVazao5"); // total 10.000,00  listros dia

	// variaveis de vazao mensal e reflexao //
	List<String> listVariaveisVazaoMesRequeridas = Arrays.asList(

			"frQDiaJan","frQDiaFev","frQDiaMar","frQDiaAbr","frQDiaMai","frQDiaJun",
			"frQDiaJul","frQDiaAgo","frQDiaSet","frQDiaOut","frQDiaNov","frQDiaDez"	

			);

	List<String> listVariaveisVazaoHoraRequeridas = Arrays.asList(

			"frQHoraJan","frQHoraFev","frQHoraMar","frQHoraAbr","frQHoraMai","frQHoraJun",
			"frQHoraJul","frQHoraAgo","frQHoraSet","frQHoraOut","frQHoraNov","frQHoraDez"

			);

	List<String> listVariaveisTempoRequeridas = Arrays.asList(

			"frTempoCapJan","frTempoCapFev","frTempoCapMar","frTempoCapAbr","frTempoCapMai","frTempoCapJun",
			"frTempoCapJul","frTempoCapAgo","frTempoCapSet","frTempoCapOut","frTempoCapNov","frTempoCapDez"

			);

	List<String> listVariaveisFinalidadesAutorizadas = Arrays.asList("faFinalidade1", "faFinalidade2", "faFinalidade3", "faFinalidade4", "faFinalidade5");
	List<String> listVariaveisSubfinaldadesAutorizadas = Arrays.asList("faSubfinalidade1", "faSubfinalidade2", "faSubfinalidade3", "faSubfinalidade4", "faSubfinalidade5");
	List<String> listVariaveisQuantidadesAutorizadas = Arrays.asList("faQuantidade1", "faQuantidade2", "faQuantidade3", "faQuantidade4", "faQuantidade5");
	List<String> listVariaveisConsumoAutorizadas = Arrays.asList("faConsumo1", "faConsumo2", "faConsumo3", "faConsumo4", "faConsumo5");
	List<String> listVariaveisVazaoAutorizadas = Arrays.asList("faVazao1", "faVazao2", "faVazao3", "faVazao4", "faVazao5");

	// variaveis de vazao mensal e reflexao //
	List<String> listVariaveisVazaoMesAutorizadas = Arrays.asList(

			"faQDiaJan","faQDiaFev","faQDiaMar","faQDiaAbr","faQDiaMai","faQDiaJun",
			"faQDiaJul","faQDiaAgo","faQDiaSet","faQDiaOut","faQDiaNov","faQDiaDez"	

			);

	List<String> listVariaveisVazaoHoraAutorizadas = Arrays.asList(

			"faQHoraJan","faQHoraFev","faQHoraMar","faQHoraAbr","faQHoraMai","faQHoraJun",
			"faQHoraJul","faQHoraAgo","faQHoraSet","faQHoraOut","faQHoraNov","faQHoraDez"

			);

	List<String> listVariaveisTempoAutorizadas = Arrays.asList(

			"faTempoCapJan","faTempoCapFev","faTempoCapMar","faTempoCapAbr","faTempoCapMai","faTempoCapJun",
			"faTempoCapJul","faTempoCapAgo","faTempoCapSet","faTempoCapOut","faTempoCapNov","faTempoCapDez"

			);


	List<Object[][]> listaMalaDireta = new ArrayList<>();

	Documento documento = new Documento();
	
	DecimalFormat df = new DecimalFormat("#,##0.00"); 
	
	//Elements do Jsoup para retirar tags desnecessarias do html criado
	Elements elem;
	
	// formatador de cpf e cnpj
	FormatadorCPFCNPJ ccFormato = new FormatadorCPFCNPJ();

	/**
	 * Construtor
	 * @param modeloHTML
	 * @param documento
	 * @param listaMalaDireta
	 */
	public MalaDiretaDocumentos (String modeloHTML, Documento documento, List<Object[][]> listaMalaDireta) {

		this.modeloHTML = modeloHTML;
		this.documento = documento;
		this.listaMalaDireta = listaMalaDireta;
		
	}

	FinalidadeRequerida finSubReq;
	FinalidadeRequerida finSupReq;

	FinalidadeAutorizada finSubAut;
	FinalidadeAutorizada finSupAut;

	/**
	 * Metodo de criacao do documento html
	 * @return String strhtml com o html pronto para inserir no editor do site SEI
	 */
	public String criarDocumento () {


		Document docHtml = null;

		docHtml = Jsoup.parse(modeloHTML, "UTF-8").clone();
		
		String strTagsDocumento [] = {
				
				"doc_tipo_tag",
				"doc_numeracao_tag",
				"doc_numeracao_sei_tag",
				"doc_processo_tag",
				"doc_data_recebimento_tag", // <doc_data_recebimento_tag></doc_data_recebimento_tag>
				"doc_data_distribuicao_tag",
				"doc_processo_principal_tag",
		};
		
		String strTagsInterferencia [] = {
				
				"inter_tipo_outorga_tag",
				"inter_tipo_poco_tag",
				"inter_subsistema_tag",
				"inter_uh_tag",
				"inter_bacia_tag",
				"inter_lat_tag",
				"inter_lon_tag",
				"inter_prof_tag",
				"inter_nivel_est_tag",
				"inter_niv_din_tag",
				"inter_vazao_tag",
				"inter_vazao_teste_tag",
				"inter_vazao_subsistema_tag"
				
		};
		
		// tempo litros dia litros_hora_abr_tag listros_dia_abr_tag inter_subsistema_tag 
		
		String strTagsUsuario [] = {
				
				"us_nome_tag", // <us_nome_tag></us_nome_tag>
				"us_cpfcnpj_tag", //<us_cpfcnpj_tag></us_cpfcnpj_tag>
				"us_end_cor_tag",  // <us_end_cor_tag></us_end_cor_tag>
				"us_ra_tag",
				"us_cep_tag", // <us_cep_tag></us_cep_tag> 
				"us_cidade_tag", // <us_cep_tag></us_cep_tag> 
				"us_uf_tag", // <us_cep_tag></us_cep_tag> 
				"us_tel_tag", // <us_tel_tag></us_tel_tag>
				"us_cel_tag", // <us_cel_tag></us_cel_tag>
				"us_email_tag", // <us_email_tag></us_email_tag>
				"us_representante_tag", // <us_email_tag></us_email_tag>
				"us_representante_telefone_tag", // <us_email_tag></us_email_tag>
		};
		
				
		String strTagsEnderecoEmpreendimento [] = {
				
				"end_empreendimento_logradouro_tag", // <end_empreendimento_logradouro_tag></end_empreendimento_logradouro_tag>
				"end_empreendimento_ra_tag", // <end_empreendimento_ra_tag></end_empreendimento_ra_tag>
				"end_empreendimento_cep_tag",
				"end_empreendimento_cidade_tag",
				"end_empreendimento_uf_tag",
				
				"end_empreendimento_lat_tag",
				"end_empreendimento_lon_tag",
		};
		

		// formatar data 2018-05-12 para 12/05/2018
		SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

		String strDataRecebimento, strDataDistribuicao, strProcessoPrincipal, strTipoSubOutorga, strCPFCNPJ = "";

		// data de recebimento do documento
		try {strDataRecebimento = formatadorData.format(((Documento)listaMalaDireta.get(0)[0][0]).getDocDataRecebimento());} 
		catch (Exception e) {strDataRecebimento = "";}
		// data de distribuicao
		try {strDataDistribuicao = formatadorData.format(((Documento)listaMalaDireta.get(0)[0][0]).getDocDataDistribuicao());} 
		catch (Exception e) {strDataDistribuicao = "";}
		// processo principal
		try {strProcessoPrincipal = ((Documento)listaMalaDireta.get(0)[0][0]).getDocProcessoFK().getProSEI();} 
		catch (Exception e) {strProcessoPrincipal = "";}

		// imprimir Outorga, ou se o subtipo outorga for modificacao, renovacao, imprimir Modificacao de Outorga
		if (((Subterranea)listaMalaDireta.get(0)[0][2]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao().equals("")) {
			strTipoSubOutorga = ((Subterranea)listaMalaDireta.get(0)[0][2]).getInterTipoOutorgaFK().getTipoOutorgaDescricao().toLowerCase();
		} else {
			strTipoSubOutorga = ((Subterranea)listaMalaDireta.get(0)[0][2]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao().toLowerCase()  + " de " 
					+ ((Subterranea)listaMalaDireta.get(0)[0][2]).getInterTipoOutorgaFK().getTipoOutorgaDescricao().toLowerCase(); 
		}
			
		String strDocumento [] = {

				((Documento)listaMalaDireta.get(0)[0][0]).getDocTipo(),
				((Documento)listaMalaDireta.get(0)[0][0]).getDocNumeracao(),
				((Documento)listaMalaDireta.get(0)[0][0]).getDocSEI(),
				((Documento)listaMalaDireta.get(0)[0][0]).getDocProcesso(),
				strDataRecebimento,
				strDataDistribuicao,
				strProcessoPrincipal,

		};

		// 2 - Interferencia no listaMalaDireta
		String strInterferencia [] = {

				strTipoSubOutorga,
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubTipoPocoFK().getTipoPocoDescricao().toLowerCase(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubSubSistemaFK().getSubDescricao(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getInterUHFK().getUhNome(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getInterBaciaFK().getBaciaNome(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getInterDDLatitude().toString(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getInterDDLongitude().toString(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubProfundidade(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubEstatico(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubDinamico(),
				df.format((((Subterranea) listaMalaDireta.get(0)[0][2])).getSubVazaoOutorgada()) .replaceAll(",00", ""),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubVazaoTeste(),
				((Subterranea)listaMalaDireta.get(0)[0][2]).getSubVazaoSubsistema(),
				

		};

		 
		// formatacao do cpf e cnpj
		try {
			strCPFCNPJ = ccFormato.formatCnpj(((Usuario)listaMalaDireta.get(0)[0][1]).getUsTipo(),((Usuario)listaMalaDireta.get(0)[0][1]).getUsCPFCNPJ());
		} catch (ParseException e1) {
			
			Alerta a = new Alerta ();
			a.alertar(new Alert(Alert.AlertType.ERROR, "erro na formatação - CPF ou CNPJ !!!", ButtonType.OK));
	
			e1.printStackTrace();
		}

		// 1 - Usuario no listaMalaDireta
		String strUsuario [] = {
				
				
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsNome(),
				strCPFCNPJ,
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsLogadouro(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsRA(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsCEP(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsCidade(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsEstado(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsTelefone(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsCelular(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsEmail(),

				((Usuario)listaMalaDireta.get(0)[0][1]).getUsRepresentante(),
				((Usuario)listaMalaDireta.get(0)[0][1]).getUsRepresentanteTelefone(),


		};

		// 3 - Endereco do Empreendimento no listaMalaDireta
		String strEnderecoEmpreendimento [] = {
				
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndLogradouro(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndRAFK().getRaNome(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndCEP(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndCidade(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndUF(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndDDLatitude().toString(),
			((Endereco)listaMalaDireta.get(0)[0][3]).getEndDDLongitude().toString(),

		};
		
		
		for (int i  = 0; i<strTagsDocumento.length; i++) {
			try { docHtml.select(strTagsDocumento[i]).prepend(strDocumento[i]);
			
			// limpar o html de tags desnecessarias
			elem = docHtml.select(strTagsDocumento[i]);
			elem.tagName("span");
			
			} 
				catch (Exception e) {docHtml.select(strTagsDocumento[i]).prepend("");
				
				// limpar o html de tags desnecessarias
				elem = docHtml.select(strTagsDocumento[i]);
				elem.tagName("span");
				};
		}
	
		
		for (int i  = 0; i<strTagsInterferencia.length; i++) {
			try { docHtml.select(strTagsInterferencia[i]).prepend(strInterferencia[i]);

			// limpar o html de tags desnecessarias
						elem = docHtml.select(strTagsInterferencia[i]);
						elem.tagName("span");
			
			} 
				catch (Exception e) {docHtml.select(strTagsInterferencia[i]).prepend("");
				
				
				// limpar o html de tags desnecessarias
				elem = docHtml.select(strTagsInterferencia[i]);
				elem.tagName("span");
				
				
				};
		}
	
		
		for (int i  = 0; i<strTagsUsuario.length; i++) {
			try { docHtml.select(strTagsUsuario[i]).prepend(strUsuario[i]);
			
			// limpar o html de tags desnecessarias
			elem = docHtml.select(strTagsUsuario[i]);
			elem.tagName("span");
			
			} 
				catch (Exception e) {docHtml.select(strTagsUsuario[i]).prepend("");
				
				
				// limpar o html de tags desnecessarias
				elem = docHtml.select(strTagsUsuario[i]);
				elem.tagName("span");
				
				};
		}
		
		for (int i  = 0; i<strTagsEnderecoEmpreendimento.length; i++) {
			try { docHtml.select(strTagsEnderecoEmpreendimento[i]).prepend(strEnderecoEmpreendimento[i]);
			// limpar o html de tags desnecessarias
						elem = docHtml.select(strTagsEnderecoEmpreendimento[i]);
						elem.tagName("span");
			
			
			} 
				catch (Exception e) {docHtml.select(strTagsEnderecoEmpreendimento[i]).prepend("");
				
				
				// limpar o html de tags desnecessarias
				elem = docHtml.select(strTagsEnderecoEmpreendimento[i]);
				elem.tagName("span");
				
				};
		}
		
		GetterAndSetter gs  = new GetterAndSetter();
		
		// imprimir finalidades autorizadas
		for (Finalidade f : ((Subterranea)listaMalaDireta.get(0)[0][2]).getFinalidades()) {
        	
        	if ( f.getClass().getName() == "entidades.FinalidadeAutorizada") {
				
        		for (int i = 0; i<5; i++) {
				
        			try { docHtml.select("finalidades_tag").append((gs.callGetter(f, listVariaveisFinalidadesAutorizadas.get(i)) + ", ").toLowerCase());
        			
        			} catch (Exception e) {	docHtml.select("finalidades_tag").prepend("");	};
        		
				}
        	}
		} // fim for finalidade
		
		// limpar o html de tags desnecessarias
		elem = docHtml.select("finalidades_tag");
		elem.tagName("span");
		
		
		// dados documento
		if (!(((Documento)listaMalaDireta.get(0)[0][0]) == null)) {

			finSubReq = new FinalidadeRequerida();
			finSupReq = new FinalidadeRequerida();

			inserirFinalidade (
					docHtml, 
					finSubReq, finSupReq, 
					"entidades.FinalidadeRequerida", "tfr_tag", "frVazaoTotal",
					listVariaveisFinalidadesRequeridas,listVariaveisSubfinaldadesRequeridas,listVariaveisQuantidadesRequeridas,listVariaveisConsumoRequeridas,
					listVariaveisVazaoRequeridas,
					listVariaveisVazaoMesRequeridas,listVariaveisVazaoHoraRequeridas,listVariaveisTempoRequeridas

					);

			finSubAut = new FinalidadeAutorizada();
			finSupAut = new FinalidadeAutorizada();

			
			inserirFinalidadeAutorizada (docHtml, 
					finSubAut, finSupAut, 
					"entidades.FinalidadeAutorizada", "tfa_tag", "faVazaoTotal",
					listVariaveisFinalidadesAutorizadas,listVariaveisSubfinaldadesAutorizadas,listVariaveisQuantidadesAutorizadas,listVariaveisConsumoAutorizadas,
					listVariaveisVazaoAutorizadas,
					listVariaveisVazaoMesAutorizadas,listVariaveisVazaoHoraAutorizadas,listVariaveisTempoAutorizadas

					);


		} // fim if documento null

		
		String html = new String ();

		html = docHtml.toString();

		html = html.replace("\"", "'");
		html = html.replace("\n", "");

		html =  "\"" + html + "\"";

		//-- webview do relat�rio --//

		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.loadContent(html);

		Scene scene = new Scene(browser);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.setWidth(1000);
		stage.setHeight(650);
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);

		stage.show();

		return html;

	}	

	/** Metodo de inserir as finalidades no parecer coletivo
	 * 
	 * @param docHtml
	 * @param finSub
	 * @param finSup
	 * @param strFinalidade
	 * @param strTag
	 * @param vazaoTotal
	 * @param listVariaveisFinalidades
	 * @param listVariaveisSubfinaldades
	 * @param listVariaveisQuantidades
	 * @param listVariaveisConsumo
	 * @param listVariaveisVazao
	 * @param listVariaveisVazaoMes
	 * @param listVariaveisVazaoHora
	 * @param listVariaveisTempo
	 */
	public void inserirFinalidade (
			Document docHtml, 
			Finalidade finSub, 
			Finalidade finSup, 
			String strFinalidade, String strTag, String vazaoTotal,
			List<String> listVariaveisFinalidades,List<String> listVariaveisSubfinaldades,List<String> listVariaveisQuantidades,List<String> listVariaveisConsumo,
			List<String> listVariaveisVazao,
			List<String> listVariaveisVazaoMes,List<String> listVariaveisVazaoHora,List<String> listVariaveisTempo

			) {

		StringBuilder strTable = new StringBuilder();

		GetterAndSetter gs  = new GetterAndSetter();

		strTable.append(
					  "<div style='overflow: auto; margin-left: auto;margin-right: auto;max-width:800px'>"
					+ "<table border='1' cellspacing='0' style='margin-left: auto; margin-right: auto;max-width:800px'>"
					+ "<tbody><tr><td>Processo</td><td>Requerente</td>"
					+ "<td>Solicita&ccedil;&atilde;o</td><td>Finalidade</td><td>Quantidade</td>"
					+ "<td>Demanda (L/dia)</td><td>Demanda Total (L/dia)</td></tr>");

		for (int i = 0; i<listaMalaDireta.size(); i++) {

			strTable.append("<tr>");

			for (int ii=0; ii < listaMalaDireta.get(i)[0].length; ii++) {

				switch (listaMalaDireta.get(i)[0][ii].getClass().getName()) {
				case "entidades.Documento":
					strTable.append("<td>" + ((Documento)listaMalaDireta.get(i)[0][ii]).getDocProcessoFK().getProSEI()  + "</td>");
					break;
				case "entidades.Usuario":
					strTable.append("<td>" + ((Usuario)listaMalaDireta.get(i)[0][ii]).getUsNome() + "</td>");
					break;
					
				case "entidades.Subterranea":

					for (Finalidade fSub : ((Subterranea)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

						if (fSub.getClass().getName() == strFinalidade) {
							finSub = fSub;

						}

					}
					
					// TIPO E SUBTIPO
					strTable.append("<td>" + 
							((Subterranea)listaMalaDireta.get(i)[0][ii]).getInterTipoOutorgaFK().getTipoOutorgaDescricao()  // tipo outorga: Outorga, Previa Registro
							+ 	"<p>" +  ((Subterranea)listaMalaDireta.get(i)[0][ii]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao() // sub tipo outorga: renovacao, modificacao
							+ 	"</td>");

					strTable.append("<td width='200'>");

					
					for (int a = 0; a<5; a++) {

						strTable.append(
								"<p>" 
								+ (gs.callGetter(finSub,listVariaveisFinalidades.get(a)))
								+ " - "
								+ (gs.callGetter(finSub,listVariaveisSubfinaldades.get(a)))
								+ "</p>"
								); // abastecimento humano, irrigacao, uso industrial...
					}

					strTable.append("</td>");

					strTable.append("<td>");
					// QUANTIDADES
					for (int a = 0; a<5; a++) {
						// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
						try { strTable.append("<p>" + df.format(	Double.parseDouble((gs.callGetter(finSub, listVariaveisQuantidades.get(a))))).replaceAll(",00", "")		+ "</p>");
					} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
					}

					strTable.append("</td>");

					strTable.append("<td>");

					// CONSUMO
					for (int a = 0; a<5; a++) {
						// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
						try{strTable.append("<p>" + df.format(	Double.parseDouble(		(gs.callGetter(finSub, listVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "")	+ "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					try{strTable.append("<td>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub, vazaoTotal)))).replaceAll(",00", "")	+ "</td>");
					} catch (Exception e) {strTable.append("<td>" + "" + "</td>");};

					break;
				case "entidades.Superficial":

					for (Finalidade fSup : ((Superficial)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

						if (fSup.getClass().getName() == strFinalidade) {
							finSup = fSup;

						}

					}
					// TIPO
					strTable.append("<td>" + 
							((Superficial)listaMalaDireta.get(i)[0][ii]).getInterTipoOutorgaFK().getTipoOutorgaDescricao()  
							+ 	"<p>" +  ((Superficial)listaMalaDireta.get(i)[0][ii]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao()
							+ 	"</td>");

					strTable.append("<td width='200'>");

					// FINALIDADES E SUBFINALIDADES
					for (int a = 0; a<5; a++) {

						strTable.append(
								"<p>" 
								+ (gs.callGetter(finSub,listVariaveisFinalidades.get(a)))
								+ " - "
								+ (gs.callGetter(finSub,listVariaveisSubfinaldades.get(a)))
								+ "</p>"
								); // abastecimento humano, irrigacao, uso industrial...
						
					}

					strTable.append("</td>");

					strTable.append("<td>");

					// QUANTIDADE
					for (int a = 0; a<5; a++) {

						try{	strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listVariaveisQuantidades.get(a))))).replaceAll(",00", "") + "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					strTable.append("<td>");

					// VAZOES REQUERIDAS
					for (int a = 0; a<5; a++) {

						try{	strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "")	+ "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					try{ strTable.append("<td>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup, vazaoTotal)))).replaceAll(",00", "")	+ "</td>");
					} catch (Exception e) {strTable.append("<td>" + "" + "</td>");};
					break;

				default:
					break;
				} // fim while

			}

			strTable.append("</tr>");

		} // fim loop for

		strTable.append("</tbody></table></div>"); // adicionei </div>

		docHtml.select(strTag).append(String.valueOf(strTable));
		
		// limpar o html de tags desnecessarias
		elem = docHtml.select(strTag);
		elem.tagName("span");

	} // fim metodo inserirfinalidade
	
	
	public void inserirFinalidadeAutorizada (
			Document docHtml, 
			Finalidade finSub, 
			Finalidade finSup, 
			String strFinalidade, String strTag, String vazaoTotal,
			List<String> listVariaveisFinalidades,List<String> listVariaveisSubfinaldades,List<String> listVariaveisQuantidades,List<String> listVariaveisConsumo,
			List<String> listVariaveisVazao,
			List<String> listVariaveisVazaoMes,List<String> listVariaveisVazaoHora,List<String> listVariaveisTempo

			) {
		
		StringBuilder strTable = new StringBuilder();

		GetterAndSetter gs  = new GetterAndSetter();

		strTable.append(
	              			  	"<div style='overflow: auto; margin-left: auto;margin-right: auto;max-width:800px'>"				
	            		  	+ 	"<table border='1' cellspacing='0' style='margin-left: auto; margin-right: auto;max-width:800px'>"
							+	"<tbody>"
							+	"<tr>"
							+	"<td style='text-align: center;'>Processo</td>"
							+	"<td style='text-align: center;'>Requerente</td>"
							+	"<td style='text-align: center;'>Solicita&ccedil;&atilde;o</td>"
							+	"<td style='text-align: center;'>Finalidade</td>"
							+	"<td style='text-align: center;'>Quantidade</td>"
							+	"<td style='text-align: center;'>Demanda Solicitada (L/dia)</td>"
							+	"<td style='text-align: center;'>Valor de refer&ecirc;ncia Resolu&ccedil;&atilde;o n&ordm; 18/2020 (L/dia)</td>"
							+	"<td style='text-align: center;'>Demanda &nbsp;Resolu&ccedil;&atilde;o n&ordm; 18/2020 (L/dia)</td>"
							+	"<td style='text-align: center;'>Demanda Total ajustada &nbsp;Resolu&ccedil;&atilde;o n&ordm; 18/2020 (L/dia)</td></tr>"
						
					);

		for (int i = 0; i<listaMalaDireta.size(); i++) {

			strTable.append("<tr>");

			for (int ii=0; ii < listaMalaDireta.get(i)[0].length; ii++) {

				switch (listaMalaDireta.get(i)[0][ii].getClass().getName()) {
				
				//PROCESSO
				case "entidades.Documento":
					strTable.append("<td>" + ((Documento)listaMalaDireta.get(i)[0][ii]).getDocProcessoFK().getProSEI()  + "</td>");
					break;
					
				// INTERESSADO
				case "entidades.Usuario":
					strTable.append("<td>" + ((Usuario)listaMalaDireta.get(i)[0][ii]).getUsNome() + "</td>");
					break;
					
				// SUBTERRANEA	
				case "entidades.Subterranea":
					
					FinalidadeRequerida f = new FinalidadeRequerida();
				

					for (Finalidade fSub : ((Subterranea)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

						if (fSub.getClass().getName() == "entidades.FinalidadeAutorizada") {
							
							finSub = fSub;
				
							// Trazer o a vazao em abril e horas de captacao em abril para o parecer individual
							docHtml.select("litros_hora_abr_tag").append( df.format( ((FinalidadeAutorizada) fSub).getFaQHoraAbr() ).replaceAll(",00", "") );
						
							// limpar o html de tags desnecessarias
							elem = docHtml.select("litros_hora_abr_tag");
							elem.tagName("span");
						
						
							docHtml.select("listros_dia_abr_tag").append( df.format( ((FinalidadeAutorizada) fSub).getFaQDiaAbr()).replaceAll(",00", "") );
							
							// limpar o html de tags desnecessarias
							elem =  docHtml.select("listros_dia_abr_tag");
							elem.tagName("span");
							

						} else {
							 f = (FinalidadeRequerida) fSub;
						}

					}
					// TIPO E SUBTIPO
					strTable.append("<td>" + 
							((Subterranea)listaMalaDireta.get(i)[0][ii]).getInterTipoOutorgaFK().getTipoOutorgaDescricao()  // tipo outorga: Outorga, Previa Registro
							+ 	"<p>" +  ((Subterranea)listaMalaDireta.get(i)[0][ii]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao() // sub tipo outorga: renovacao, modificacao
							+ 	"</td>");

					strTable.append("<td width='200'>");
					
					// SOLICITACAO - FINALIDADES
					for (int a = 0; a<5; a++) {

						strTable.append(
								"<p>" 
								+ (gs.callGetter(finSub,listVariaveisFinalidades.get(a)))
								+ " - "
								+ (gs.callGetter(finSub,listVariaveisSubfinaldades.get(a)))
								+ "</p>"
								); // abastecimento humano, irrigacao, uso industrial...
					}

					strTable.append("</td>");

					strTable.append("<td>");

					// QUANTIDADES
					for (int a = 0; a<5; a++) {

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub, listVariaveisQuantidades.get(a))))).replaceAll(",00", "")	+ "</p>");  
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");}; // 16 pessoas, 10 cavalos
					}

					strTable.append("</td>");

					strTable.append("<td>");
					
					// FINALIDADE REQUERIDA
					for (int a = 0; a<5; a++) {

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(f,listVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "") + "</p>"); 
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");}; //10.000,50 litros dia para 10 pessoas
						}

					strTable.append("</td>");

					strTable.append("<td>");
					
					// VALOR REFERÊNCIA IN
					for (int a = 0; a<5; a++) {

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub,listVariaveisConsumo.get(a))))).replaceAll(",00", "") + "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					strTable.append("<td>");
					
					
					// VAZAO AUTORIZADA
					for (int a = 0; a<5; a++) {

						try {strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub,listVariaveisVazaoAutorizadas.get(a))))).replaceAll(",00", "") + "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					try{strTable.append("<td>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub, vazaoTotal)))).replaceAll(",00", "") + "</td>");
					} catch (Exception e) {strTable.append("<td>" + "" + "</td>");};

					break;
					
				case "entidades.Superficial":

					for (Finalidade fSup : ((Superficial)listaMalaDireta.get(i)[0][ii]).getFinalidades() ) {

						if (fSup.getClass().getName() == strFinalidade) {
							finSup = fSup;

						}

					}

					strTable.append("<td>" + 
							((Superficial)listaMalaDireta.get(i)[0][ii]).getInterTipoOutorgaFK().getTipoOutorgaDescricao()  
							+ 	"<p>" +  ((Superficial)listaMalaDireta.get(i)[0][ii]).getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao()
							+ 	"</td>");

					strTable.append("<td width='200'>");

					for (int a = 0; a<5; a++) {

						strTable.append(
								"<p>" 
								+ (gs.callGetter(finSub,listVariaveisFinalidades.get(a)))
								+ " - "
								+ (gs.callGetter(finSub,listVariaveisSubfinaldades.get(a)))
								+ "</p>"
								); // abastecimento humano, irrigacao, uso industrial...
					}

					strTable.append("</td>");

					strTable.append("<td>");

					for (int a = 0; a<5; a++) {

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listVariaveisQuantidades.get(a))))).replaceAll(",00", "")	+ "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					strTable.append("<td>");

					for (int a = 0; a<5; a++) {

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listVariaveisConsumo.get(a))))) .replaceAll(",00", "") + "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					try{strTable.append("<td>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup, vazaoTotal)))).replaceAll(",00", "")	+ "</td>");
					} catch (Exception e) {strTable.append("<td>" + "" + "</td>");};
					break;

				default:
					break;
				} // fim while

			}

			strTable.append("</tr>");

		} // fim loop for

		strTable.append("</tbody></table></div>"); // adicionei </div>

		docHtml.select(strTag).append(String.valueOf(strTable));
		
		// limpar o html de tags desnecessarias
		elem = docHtml.select(strTag);
		elem.tagName("span");
		
		// a tag title confunde o navegador retirando a tag table  de lugar, entao e preciso retirar a tag title
		elem = docHtml.select("title");
		elem.tagName("span");
				
		
		// tempo litros dia litros_hora_abr_tag listros_dia_abr_tag inter_subsistema_tag 
		

	} // fim metodo inserirfinalidade
	
	
}


/*
 * para o parecer coletivo
 * <tr>
					<td><doc_processo_tag></doc_processo_tag></td>
					<td><inter_tipo_outorga_tag></inter_tipo_outorga_tag> </td>
					<td><us_nome_tag></us_nome_tag></td>
					<td><abast_hum_tag></abast_hum_tag> <cria_anim_tag></cria_anim_tag> <irrig_tag></irrig_tag> <uso_ind_tag></uso_ind_tag> </td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>


					StringBuilder strAbasHum = new StringBuilder();

					strAbasHum
					.append("<div align='justify'><strong style='font-style: italic; font-size: 12px;'>- ABASTECIMENTO HUMANO</strong>"
							+	"<table border='1' cellspacing='0' style='max-width: 800px;'><tbody><tr><td colspan='1'>"
							+ 	"Popula&ccedil;&atilde;o:&nbsp;")    // populacao
					.append(listQuantidadesCadastradas.get(i))
					.append("</td><td colspan='1'>Consumo diário por habitante:&nbsp;") //  consumo
					.append(listConsumosCadastrados.get(i))
					.append("</td><td colspan='1' width='20%'>Total:")              // total
					.append(listVazoesCadastradas.get(i));

		//--------------- conversor de data para fiscar no formato por extenso 	---------------------------------//		

						"doc_data_tag"

						// formatador
		//DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
			// string com a data:  Terça-feira, 2 de Abril de 2019
			//String dataExtenso = formatador.format(documento.getDocDataCriacao());

				//System.out.println(dataExtenso);

					// retirar o dia da semana:  2 de Abril de 2019
					//int index  = dataExtenso.indexOf(","); // inicio da substring

						//System.out.println(dataExtenso.substring(index + 2));
		// -----------------------------------------------------------------------------------------------------//


				// tipo outorga (outorga, outorga prévia, registro)	"inter_tipo_outorga_tag",


 */



/*
 
 MODELO DEMANDA AUTORIZADA
 
<!doctype html>
<html>
<head>
	<title></title>
</head>
<body>
<table border="1" cellspacing="0" style="width: 800px;">
	<tbody>
		<tr>
			<td style="text-align: center;">Processo</td>
			<td style="text-align: center;">Requerente</td>
			<td style="text-align: center;">Solicita&ccedil;&atilde;o</td>
			<td style="text-align: center;">Finalidade</td>
			<td style="text-align: center;">Quantidade</td>
			<td style="text-align: center;">Demanda Solicitada (L/dia)</td>
			<td style="text-align: center;">Valor de refer&ecirc;ncia IN 02 (L/dia)</td>
			<td style="text-align: center;">Demanda IN 02 (L/dia)</td>
			<td style="text-align: center;">Demanda Total ajustada IN 02 (L/dia)/td&gt;</td>
		</tr>
	</tbody>
</table>
</body>
</html>
 
 
 
 
 */
