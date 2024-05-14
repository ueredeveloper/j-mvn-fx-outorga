package util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

import entidades.Documento;
import entidades.Endereco;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.FinalidadeRequerida;
import entidades.GetterAndSetter;
import entidades.Interferencia;
import entidades.SubSistema;
import entidades.Subterranea;
import entidades.Superficial;
import entidades.TipoPoco;
import entidades.Usuario;

public class MalaDiretaUnica {
	
	/*ListaMalaDireta:  0 - documento 1 - endereco 2 - interferencia 3 - usuario
	
	  ((Documento)listaMalaDireta.get(0)[0][0]) ((Endereco)listaMalaDireta.get(0)[0][1]) ((Interferencia)listaMalaDireta.get(0)[0][2]) ((Usuario)listaMalaDireta.get(0)[0][3])
	  
	  */
	List<Object[][]> listaMalaDireta;
	
	String strModeloHTML;
	
	// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
	DecimalFormat df = new DecimalFormat("#,##0.00"); 
	
	Documento documento;
	
	
	List<String> listaVariaveisFinalidadesRequeridas = Arrays.asList("frFinalidade1", "frFinalidade2", "frFinalidade3", "frFinalidade4", "frFinalidade5"); // abastecimento humano
	List<String> listaVariaveisSubfinaldadesRequeridas = Arrays.asList("frSubfinalidade1", "frSubfinalidade2", "frSubfinalidade3", "frSubfinalidade4", "frSubfinalidade5"); // rural
	List<String> listaVariaveisQuantidadesRequeridas = Arrays.asList("frQuantidade1", "frQuantidade2", "frQuantidade3", "frQuantidade4", "frQuantidade5"); // 10 pessoas
	List<String> listaVariaveisConsumoRequeridas = Arrays.asList("frConsumo1", "frConsumo2", "frConsumo3", "frConsumo4", "frConsumo5"); // 1.000,00 listros dia por pessoa
	List<String> listaVariaveisVazaoRequeridas = Arrays.asList("frVazao1", "frVazao2", "frVazao3", "frVazao4", "frVazao5"); // total 10.000,00  listros dia

	// variaveis de vazao mensal e reflexao //
	List<String> listaVariaveisVazaoMesRequeridas = Arrays.asList(

			"frQDiaJan","frQDiaFev","frQDiaMar","frQDiaAbr","frQDiaMai","frQDiaJun",
			"frQDiaJul","frQDiaAgo","frQDiaSet","frQDiaOut","frQDiaNov","frQDiaDez"	

			);

	List<String> listaVariaveisTempHoraRequeridas = Arrays.asList(

			"frQHoraJan","frQHoraFev","frQHoraMar","frQHoraAbr","frQHoraMai","frQHoraJun",
			"frQHoraJul","frQHoraAgo","frQHoraSet","frQHoraOut","frQHoraNov","frQHoraDez"

			);

	List<String> listaVariaveisPeriodoDiaRequeridas = Arrays.asList(

			"frTempoCapJan","frTempoCapFev","frTempoCapMar","frTempoCapAbr","frTempoCapMai","frTempoCapJun",
			"frTempoCapJul","frTempoCapAgo","frTempoCapSet","frTempoCapOut","frTempoCapNov","frTempoCapDez"

			);

	List<String> listaVariaveisFinalidadesAutorizadas = Arrays.asList("faFinalidade1", "faFinalidade2", "faFinalidade3", "faFinalidade4", "faFinalidade5");
	List<String> listaVariaveisSubfinaldadesAutorizadas = Arrays.asList("faSubfinalidade1", "faSubfinalidade2", "faSubfinalidade3", "faSubfinalidade4", "faSubfinalidade5");
	List<String> listaVariaveisQuantidadesAutorizadas = Arrays.asList("faQuantidade1", "faQuantidade2", "faQuantidade3", "faQuantidade4", "faQuantidade5");
	List<String> listaVariaveisConsumoAutorizadas = Arrays.asList("faConsumo1", "faConsumo2", "faConsumo3", "faConsumo4", "faConsumo5");
	List<String> listaVariaveisVazaoAutorizadas = Arrays.asList("faVazao1", "faVazao2", "faVazao3", "faVazao4", "faVazao5");

	// variaveis de vazao mensal e reflexao //
	List<String> listaVariaveisVazaoMesAutorizadas = Arrays.asList(

			"faQDiaJan","faQDiaFev","faQDiaMar","faQDiaAbr","faQDiaMai","faQDiaJun",
			"faQDiaJul","faQDiaAgo","faQDiaSet","faQDiaOut","faQDiaNov","faQDiaDez"	

			);

	List<String> listaVariaveisTempoHoraAutorizadas = Arrays.asList(

			"faQHoraJan","faQHoraFev","faQHoraMar","faQHoraAbr","faQHoraMai","faQHoraJun",
			"faQHoraJul","faQHoraAgo","faQHoraSet","faQHoraOut","faQHoraNov","faQHoraDez"

			);

	List<String> listaVariaveisPeriodoDiaAutorizadas = Arrays.asList(

			"faTempoCapJan","faTempoCapFev","faTempoCapMar","faTempoCapAbr","faTempoCapMai","faTempoCapJun",
			"faTempoCapJul","faTempoCapAgo","faTempoCapSet","faTempoCapOut","faTempoCapNov","faTempoCapDez"

			);
	
	List<String> listaFinalidadesCadastradas = new ArrayList<String>();
	List<String> listaSubfinalidadesCadastradas = new ArrayList<String>();
	List<String> listaQuantidadesCadastradas = new ArrayList<String>();
	List<String> listaConsumosCadastrados = new ArrayList<String>();
	List<String> listaVazoesCadastradas = new ArrayList<String>();

	/*
	 * capturar as vazoes l/dia l/hora e periodo mes
	 */

	List<String> listaVazoesDia = new ArrayList<String>();
	List<String> listaTempoHora = new ArrayList<String>();
	List<String> listaPeriodos = new ArrayList<String>();
	
	
	public MalaDiretaUnica (List<Object[][]> listaMalaDireta, String strModeloHTML) {
		
		this.listaMalaDireta = listaMalaDireta;
		this.strModeloHTML = strModeloHTML;
		
	}
	
	// formatar data 2018-05-12 para 12/05/2018
	SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
	// formatador de CPF e CNPJ
	FormatadorCPFCNPJ ccFormato = new FormatadorCPFCNPJ();
	
	FinalidadeRequerida finSubReq;
	FinalidadeRequerida finSupReq;

	FinalidadeAutorizada finSubAut;
	FinalidadeAutorizada finSupAut;
	
	public String criarDocumento () {
		
		GetterAndSetter gs  = new GetterAndSetter();

		Document docHtml = null;
		
		docHtml = Jsoup.parse(strModeloHTML, "UTF-8").clone();
		
		String strDataRecebimento, strDataDistribuicao, strProcessoPrincipal, strSubtipoETipoOutorga, strCPFCNPJ = "";
		
		if(!(documento==null)) {
		
		Documento doc = ((Documento)listaMalaDireta.get(0)[0][0]);
		
		if (!	(documento==null)	) {
			
			String strTagsDocumento [] = {
					
					"doc_tipo_tag",
					"doc_numeracao_tag",
					"doc_numeracao_sei_tag",
					"doc_processo_tag",
					"doc_data_recebimento_tag", // <doc_data_recebimento_tag></doc_data_recebimento_tag>
					"doc_data_distribuicao_tag",
					"doc_processo_principal_tag",
			};
			
			// data de recebimento do documento
			try {strDataRecebimento = formatadorData.format(doc.getDocDataRecebimento());} 
			catch (Exception e) {strDataRecebimento = "";}
			// data de distribuicao
			try {strDataDistribuicao = formatadorData.format(doc.getDocDataDistribuicao());} 
			catch (Exception e) {strDataDistribuicao = "";}
			// processo principal
			try {strProcessoPrincipal = doc.getDocProcessoFK().getProSEI();} 
			catch (Exception e) {strProcessoPrincipal = "";}
	
			
			String strDocumento [] = {
	
					doc.getDocTipo(),
					doc.getDocNumeracao(),
					doc.getDocSEI(),
					doc.getDocProcesso(),
					strDataRecebimento,
					strDataDistribuicao,
					strProcessoPrincipal,
	
			};
			
			for (int i  = 0; i<strTagsDocumento.length; i++) {
				try { docHtml.select(strTagsDocumento[i]).prepend(strDocumento[i]);} catch (Exception e) {docHtml.select(strTagsDocumento[i]).prepend("");};
			}
		
			} // fim if doc null
			
			
			finSubReq = new FinalidadeRequerida();
			finSupReq = new FinalidadeRequerida();
	
			inserirFinalidadeRequerida (
					docHtml, 
					finSubReq, finSupReq, 
					"entidades.FinalidadeRequerida", "tfr_tag", "frVazaoTotal",
					listaVariaveisFinalidadesRequeridas,listaVariaveisSubfinaldadesRequeridas,listaVariaveisQuantidadesRequeridas,listaVariaveisConsumoRequeridas,
					listaVariaveisVazaoRequeridas,
					listaVariaveisVazaoMesRequeridas,listaVariaveisTempHoraRequeridas,listaVariaveisPeriodoDiaRequeridas
	
					);
	
			finSubAut = new FinalidadeAutorizada();
			finSupAut = new FinalidadeAutorizada();
	
			
			inserirFinalidadeAutorizada (docHtml, 
					finSubAut, finSupAut, 
					"entidades.FinalidadeAutorizada", "tfa_tag", "faVazaoTotal",
					listaVariaveisFinalidadesAutorizadas,listaVariaveisSubfinaldadesAutorizadas,listaVariaveisQuantidadesAutorizadas,listaVariaveisConsumoAutorizadas,
					listaVariaveisVazaoAutorizadas,
					listaVariaveisVazaoMesAutorizadas,listaVariaveisTempoHoraAutorizadas,listaVariaveisPeriodoDiaAutorizadas
	
					);
		}

		Usuario usuario = ((Usuario)listaMalaDireta.get(0)[0][3]);
		

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
		
		// formatacao do cpf e cnpj
		try {	strCPFCNPJ = ccFormato.formatCnpj(usuario.getUsTipo(), usuario.getUsCPFCNPJ());	} catch (ParseException e) {e.printStackTrace();	}
		
		String strUsuario [] = {
				
			usuario.getUsNome(),
			strCPFCNPJ,
			usuario.getUsLogadouro(),
			usuario.getUsRA(),
			usuario.getUsCEP(),
			usuario.getUsCidade(),
			usuario.getUsEstado(),
			usuario.getUsTelefone(),
			usuario.getUsCelular(),
			usuario.getUsEmail(),

			usuario.getUsRepresentante(),
			usuario.getUsRepresentanteTelefone(),

		};
	
		for (int i  = 0; i<strTagsUsuario.length; i++) {
			try { docHtml.select(strTagsUsuario[i]).prepend(strUsuario[i]);} catch (Exception e) {docHtml.select(strTagsUsuario[i]).prepend("");	};
		}


		/*
		 * dados do endereco do empreendimento
		 */

		Endereco endereco =  ((Endereco)listaMalaDireta.get(0)[0][1]);

			String strTagsEndereco [] = {
							
							"end_empreendimento_logradouro_tag", // <end_empreendimento_logradouro_tag></end_empreendimento_logradouro_tag>
							"end_empreendimento_ra_tag", // <end_empreendimento_ra_tag></end_empreendimento_ra_tag>
							"end_empreendimento_cep_tag",
							"end_empreendimento_cidade_tag",
							"end_empreendimento_uf_tag",
							
							"end_empreendimento_lat_tag",
							"end_empreendimento_lon_tag",
					};
			
			// 3 - Endereco do Empreendimento no ListMalaDireta
			String strEndereco [] = {
					
				endereco.getEndLogradouro(),
				endereco.getEndRAFK().getRaNome(),
				endereco.getEndCEP(),
				endereco.getEndCidade(),
				endereco.getEndUF(),
				endereco.getEndDDLatitude().toString(),
				endereco.getEndDDLongitude().toString(),

			};

			for (int i  = 0; i<strTagsEndereco.length; i++) {
				try { docHtml.select(strTagsEndereco[i]).prepend(strEndereco[i]);} catch (Exception e) {docHtml.select(strTagsEndereco[i]).prepend("");};
			}


		/*
		 * dados da interferencia
		 */
		
		Interferencia interferencia = ((Interferencia)listaMalaDireta.get(0)[0][2]);
		
		
		// inter_tipo_outorga_LOW_tag inter_tipo_outorga_UPPER_tag
		// imprimir Outorga, ou se o subtipo outorga for modificacao, renovacao, imprimir Modificacao de Outorga
		
		
		String strTipo = interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao();
		String strSubTipo = interferencia.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao();
		String strTipoUPPERCASE = "";
		String strSubTipoLOWERCASE = "";
		
		if (strSubTipo.equals("")) {
			
			if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Outorga")) {
				
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " de direito de uso de recursos hídricos";
			}
			else if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Registro")) {
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " de uso";
			}
			else {
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " PARA PERFURAÇÃO DE POÇO";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " para perfuração";
			}
			
		} else {
			
			if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Outorga")) {
				
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " de direito de uso de recursos hídricos";

			}
			else if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Registro")) {
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " de uso";
			}
			else {
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " PARA PERFURAÇÃO DE POÇO";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " para perfuração de poço";
			}
		}
		
		
		// SUPERFICIAL //
		if ( interferencia.getInterTipoInterferenciaFK().getTipoInterID() == 1) {

			// FINALIDADE REQUERIDA //
			FinalidadeRequerida fr = new FinalidadeRequerida();

			for (Finalidade f : ((Superficial) interferencia).getFinalidades() ) {

				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					fr = (FinalidadeRequerida) f;
					//System.out.println("MALA DIRETA superfi - finalidade requerida ID " + fr.getFinID());
				}

			}

			for (int i = 0; i<5; i++) {

				listaFinalidadesCadastradas.add(gs.callGetter(fr, listaVariaveisFinalidadesRequeridas.get(i)));
				listaSubfinalidadesCadastradas.add(gs.callGetter(fr, listaVariaveisSubfinaldadesRequeridas.get(i)));
				
				// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
				try{	listaQuantidadesCadastradas.add(	df.format(	Double.parseDouble((gs.callGetter(fr, listaVariaveisQuantidadesRequeridas.get(i))))).replaceAll(",00", "")		);} 
					catch (Exception e) {listaQuantidadesCadastradas.add(null);};
				try{	listaConsumosCadastrados.add(	df.format(	Double.parseDouble((gs.callGetter(fr, listaVariaveisConsumoRequeridas.get(i))))).replaceAll(",00", "")		);} 
					catch (Exception e) {listaConsumosCadastrados.add(null);};
				try{	listaVazoesCadastradas.add(		df.format(	Double.parseDouble((gs.callGetter(fr, listaVariaveisVazaoRequeridas.get(i))))).replaceAll(",00", "")		);} 
					catch (Exception e) {listaVazoesCadastradas.add(null);};

			}

			for (int i = 0; i<12; i++) {

				// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
				try{	listaVazoesDia.add( 	df.format(	Double.parseDouble((gs.callGetter(fr, listaVariaveisVazaoMesRequeridas.get(i))))).replaceAll(",00", "") ); } 
					catch (Exception e) {listaVazoesDia.add(null);};
					
				listaTempoHora.add(gs.callGetter(fr, listaVariaveisTempHoraRequeridas.get(i)));
				listaPeriodos.add(gs.callGetter(fr, listaVariaveisPeriodoDiaRequeridas.get(i)));

			}

			String strTagsRequerimentoSuperificial  [] = {

					"inter_tipo_outorga_UPPER_tag", //1
					"inter_tipo_outorga_LOW_tag",
					"inter_lat_tag", 
					"inter_lon_tag",

					"inter_caesb_tag", //4
					"inter_local_cap_tag", 
					"inter_cor_hid_tag", 

					"inter_forma_capt_tag", //7
					"inter_marca_bomba_tag",
					"inter_potencia_bomba_tag",

					"inter_tempo_cap_tag", // 10
					"inter_vazao_bomba_tag",
					"inter_data_oper_tag",
					"inter_area_propriedade_tag"

			};

			String strDataOperação;
			try {
				strDataOperação = formatadorData.format(((Superficial) interferencia).getSupDataOperacao());
			}
			catch (Exception e) {strDataOperação = null;
			};
		
			String strInterferenciaSuperficial [] = {

					strTipoUPPERCASE ,  // REQUERIMENTO DE OUTORGA...
					strSubTipoLOWERCASE ,  // solicitar outorga de direito
					interferencia.getInterDDLatitude().toString() + ",",
					interferencia.getInterDDLongitude().toString(),

					((Superficial) interferencia).getSupCaesb(), //4
					((Superficial) interferencia).getSupLocalCaptacaoFK().getLocalCaptacaoDescricao(),
					((Superficial) interferencia).getSupCorpoHidrico(),

					((Superficial) interferencia).getSupFormaCaptacaoFK().getFormaCaptacaoDescricao(), //7
					((Superficial) interferencia).getSupMarcaBomba(),
					((Superficial) interferencia).getSupPotenciaBomba(),
					"1", //10 tempo captacao
					"1", // vazao da bomba
					strDataOperação,
					((Superficial) interferencia).getSupAreaPropriedade()
				
			};

			for (int i  = 0; i<strTagsRequerimentoSuperificial.length; i++) {

				try { docHtml.select(strTagsRequerimentoSuperificial[i]).prepend(strInterferenciaSuperficial[i]);} 
				catch (Exception e) {docHtml.select(strTagsRequerimentoSuperificial[i]).prepend("");};
			}

			try{ df.format(	((fr.getFrVazaoTotal()))).replaceAll(",00", "");} catch (Exception e) { e.printStackTrace();};
					
			// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15		
			try { docHtml.select("vazaototaltag").prepend( df.format(	((fr.getFrVazaoTotal()))).replaceAll(",00", "") );} 
				catch (Exception e) {docHtml.select("vazaototaltag").prepend("");};	
		
		} // FIM IF SUPERFICIAL

		// SUBTERRANEA //
		if ( interferencia.getInterTipoInterferenciaFK().getTipoInterID() == 2) {


			// FINALIDADE REQUERIDA //
			FinalidadeRequerida fr = new FinalidadeRequerida();

			for (Finalidade f : ((Subterranea) interferencia).getFinalidades() ) {

				if (f.getClass().getName() == "entidades.FinalidadeRequerida") {
					fr = (FinalidadeRequerida) f;
					//System.out.println("MALA DIRETA - sub - finalidade requerida ID " + fr.getFinID());
				}

			}

			for (int i = 0; i<5; i++) {

				listaFinalidadesCadastradas.add(gs.callGetter(fr, listaVariaveisFinalidadesRequeridas.get(i))); 
				listaSubfinalidadesCadastradas.add(gs.callGetter(fr, listaVariaveisSubfinaldadesRequeridas.get(i)));
				// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
			
				try{	listaQuantidadesCadastradas.add(df.format(		Double.parseDouble(		(gs.callGetter(fr, listaVariaveisQuantidadesRequeridas.get(i))))).replaceAll(",00", "")	);} 
					catch (Exception e) {listaQuantidadesCadastradas.add(null);};
				try{	listaConsumosCadastrados.add(df.format(			Double.parseDouble(		(gs.callGetter(fr, listaVariaveisConsumoRequeridas.get(i))))).replaceAll(",00", "")	);} 
					catch (Exception e) {listaConsumosCadastrados.add(null);};
				try{ 	listaVazoesCadastradas.add(df.format(			Double.parseDouble(		(gs.callGetter(fr, listaVariaveisVazaoRequeridas.get(i))))).replaceAll(",00", "")	);} 
					catch (Exception e) {listaVazoesCadastradas.add(null);};

			}


			for (int i = 0; i<12; i++) {

				// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
				listaVazoesDia.add(df.format(	Double.parseDouble(		(gs.callGetter(fr, listaVariaveisVazaoMesRequeridas.get(i))))).replaceAll(",00", ""));

				listaTempoHora.add(df.format(	Double.parseDouble(		(gs.callGetter(fr, listaVariaveisTempHoraRequeridas.get(i))))).replaceAll(",00", ""));

				listaPeriodos.add(df.format(		Double.parseDouble(		(gs.callGetter(fr, listaVariaveisPeriodoDiaRequeridas.get(i))))).replaceAll(",00", ""));

			}

			String strPosicoesRequerimentoSubterranea  [] = {

					"inter_tipo_outorga_UPPER_tag", //1
					"inter_tipo_outorga_LOW_tag",
					"inter_tipo_poco_tag", //  <inter_tipo_poco_tag></inter_tipo_poco_tag>
					"inter_caesb_tag", 
					"inter_vazao_tag", 
					"inter_nivel_est_tag", 
					"inter_niv_din_tag", 
					"inter_prof_tag", 
					"inter_lat_tag", 
					"inter_lon_tag", 
					"inter_data_oper_tag",

			};

			String strDataOperação;
			try {
				strDataOperação = formatadorData.format(((Subterranea) interferencia).getSubDataOperacao());}
			catch (Exception e) {strDataOperação = null;};
			
			String vazaoPoco = "";
			// tentar imprimir o valor
			try { vazaoPoco = 	df.format(	((Subterranea) interferencia).getSubVazaoOutorgada()	).replaceAll(",00", "")  ;} 
			// ou imprime vazio
			catch (Exception e) {vazaoPoco = "0";};

			String strInterferenciaSubterranea [] = {
					
					strTipoUPPERCASE ,  // REQUERIMENTO DE OUTORGA...
					strSubTipoLOWERCASE ,  // solicitar outorga de direito
					((Subterranea) interferencia).getSubTipoPocoFK().getTipoPocoDescricao(), 
					((Subterranea) interferencia).getSubCaesb(),
					vazaoPoco,
					((Subterranea) interferencia).getSubEstatico(),
					((Subterranea) interferencia).getSubDinamico(),
					((Subterranea) interferencia).getSubProfundidade(),
					interferencia.getInterDDLatitude().toString() + ",",
					interferencia.getInterDDLongitude().toString(),
					strDataOperação


			};

			for (int i  = 0; i<strPosicoesRequerimentoSubterranea.length; i++) {

				try { docHtml.select(strPosicoesRequerimentoSubterranea[i]).prepend(strInterferenciaSubterranea[i]);} 
				catch (Exception e) {docHtml.select(strPosicoesRequerimentoSubterranea[i]).prepend("");};
			}

			try { docHtml.select("vazaototaltag").prepend( df.format(	((fr.getFrVazaoTotal()))).replaceAll(",00", "") );} 
				catch (Exception e) {docHtml.select("vazaototaltag").prepend("");};

		} // FIM IF SUBTERRANEA


			// dados da captacao - vazao listros dia
		String strListrosDiaTag [] = {

				"listros_dia_jan_tag",
				"listros_dia_fev_tag",
				"listros_dia_mar_tag",
				"listros_dia_abr_tag",
				"listros_dia_mai_tag",
				"listros_dia_jun_tag",
				"listros_dia_jul_tag",
				"listros_dia_ago_tag",
				"listros_dia_set_tag",
				"listros_dia_out_tag",
				"listros_dia_nov_tag",
				"listros_dia_dez_tag"

		};

		String strListrosHoraTag [] = {
				
				// <litros_hora_jan_tag></litros_hora_jan_tag>

				"litros_hora_jan_tag", 
				"litros_hora_fev_tag",
				"litros_hora_mar_tag",
				"litros_hora_abr_tag",
				"litros_hora_mai_tag",
				"litros_hora_jun_tag",
				"litros_hora_jul_tag",
				"litros_hora_ago_tag",
				"litros_hora_set_tag",
				"litros_hora_out_tag",
				"litros_hora_nov_tag",
				"litros_hora_dez_tag"

		};

		String strPeriodoMesTag [] = {

				// <periodo_mes_jan_tag></periodo_mes_jan_tag>
				
				"periodo_mes_jan_tag",
				"periodo_mes_fev_tag",
				"periodo_mes_mar_tag",
				"periodo_mes_abr_tag",
				"periodo_mes_mai_tag",
				"periodo_mes_jun_tag",
				"periodo_mes_jul_tag",
				"periodo_mes_ago_tag",
				"periodo_mes_set_tag",
				"periodo_mes_out_tag",
				"periodo_mes_nov_tag",
				"periodo_mes_dez_tag"

		};

		for (int i = 0; i<5; i++) {

			switch (listaFinalidadesCadastradas.get(i)) {

			case "Abastecimento Humano": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strAbasHum = new StringBuilder();

				strAbasHum
				.append("<div align='justify'><strong style='font-style: italic; font-size: 12px;'>- ABASTECIMENTO HUMANO</strong>"
						+	"<table border='1' cellspacing='0' style='max-width: 800px;'><tbody><tr><td colspan='1'>"
						+ 	"Popula&ccedil;&atilde;o:&nbsp;")    // populacao
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo diário por habitante:&nbsp;") //  consumo
				.append(listaConsumosCadastrados.get(i))
				.append("</td><td colspan='1' width='20%'>Total:")              // total
				.append(listaVazoesCadastradas.get(i));

				//System.out.println(strAbasHum);

				try { docHtml.select("abast_hum_tag").prepend(String.valueOf(strAbasHum));} 
				catch (Exception e) {docHtml.select("abast_hum_tag").prepend("erro");};


				break;

			case "Criação De Animais": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strCriacaoAnimais = new StringBuilder();

				strCriacaoAnimais
				.append("<strong style='font-style: italic; font-size: 12px;'>- CRIA&Ccedil;&Atilde;O DE ANIMAIS</strong>"
						+ "<table border='1' cellspacing='0' style='max-width: 800px;'>"
						+ "<tbody><tr><td colspan='2'>Esp&eacute;cie:&nbsp;")                         // especie
				.append(listaFinalidadesCadastradas.get(i))
				.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;")   // total
				.append(listaVazoesCadastradas.get(i))
				.append("</span></td></tr><tr><td colspan='1'>Quantidade:&nbsp;")          // quantidade
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo:&nbsp;")                    // consumo
				.append(listaConsumosCadastrados.get(i));

				//System.out.println(strCriacaoAnimais);

				try { docHtml.select("cria_anim_tag").prepend(String.valueOf(strCriacaoAnimais));} 
				catch (Exception e) {docHtml.select("cria_anim_tag").prepend("erro");};

				break;	


			case "Irrigação": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strIrrigacao = new StringBuilder();

				strIrrigacao
				.append("<strong style='font-style: italic; font-size: 12px;'>- IRRIGA&Ccedil;&Atilde;O</strong>" + 
						"<table border='1' cellspacing='0' style='max-width: 800px;'>")
				.append("<tbody><tr><td colspan='2'>Esp&eacute;cie:&nbsp;") //  cultura
				.append(listaSubfinalidadesCadastradas.get(i))
				.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;") // total
				.append(listaVazoesCadastradas.get(i))
				.append("</span></td></tr><tr><td colspan='1'>Quantidade:&nbsp;") // quantidade
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo:&nbsp;") // consumo
				.append(listaConsumosCadastrados.get(i))
				.append("</td></tr></tbody></table>");


				try { docHtml.select("irrig_tag").prepend(String.valueOf(strIrrigacao));} 
				catch (Exception e) {docHtml.select("irrig_tag").prepend("erro");};

				break;	
				
				
			case "Uso Cormercial": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strUsoComercial = new StringBuilder();

				strUsoComercial
				.append("<strong style='font-style: italic; font-size: 12px;'>- USO COMERCIAL</strong>" + 
						"<table border='1' cellspacing='0' style='max-width: 800px;'>")
				.append("<tbody><tr><td colspan='2'>Produto:&nbsp;") //  produto
				.append(listaSubfinalidadesCadastradas.get(i))
				.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;") // total
				.append(listaVazoesCadastradas.get(i))
				.append("</span></td></tr><tr><td colspan='1'>Produ&ccedil;&atilde;o:&nbsp;&nbsp;") // producao
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo:&nbsp;") // consumo
				.append(listaConsumosCadastrados.get(i))
				.append("</td></tr></tbody></table>");

				try { docHtml.select("uso_ind_tag").prepend(String.valueOf(strUsoComercial));} 
				catch (Exception e) {docHtml.select("uso_ind_tag").prepend("erro");};

				break;	

			case "Uso Industrial": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strUsoIndustrial = new StringBuilder();

				strUsoIndustrial
				.append("<strong style='font-style: italic; font-size: 12px;'>- IND&Uacute;STRIA</strong>" + 
						"<table border='1' cellspacing='0' style='max-width: 800px;'>")
				.append("<tbody><tr><td colspan='2'>Produto:&nbsp;") //  produto
				.append(listaSubfinalidadesCadastradas.get(i))
				.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;") // total
				.append(listaVazoesCadastradas.get(i))
				.append("</span></td></tr><tr><td colspan='1'>Produ&ccedil;&atilde;o:&nbsp;&nbsp;") // producao
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo:&nbsp;") // consumo
				.append(listaConsumosCadastrados.get(i))
				.append("</td></tr></tbody></table>");

				try { docHtml.select("uso_ind_tag").prepend(String.valueOf(strUsoIndustrial));} 
				catch (Exception e) {docHtml.select("uso_ind_tag").prepend("erro");};

				break;	
				
			case "Aquicultura": 
			case "Piscicultura": 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */

				StringBuilder strUsoAquicultura = new StringBuilder();

				strUsoAquicultura
				.append("<strong style='font-style: italic; font-size: 12px;'>- "+ listaFinalidadesCadastradas.get(i).toUpperCase() + "</strong>")		
				.append(" <table border='1' cellspacing='0' style='max-width: 800px;'>")
				.append("	<tbody>")
				.append("		<tr>")
				.append("			<td colspan='1'>Existe lan&ccedil;amento de efluentes&nbsp; &nbsp; &nbsp; Sim&nbsp;&nbsp;<input type='checkbox' />&nbsp; &nbsp; N&atilde;o&nbsp;&nbsp;<input type='checkbox' /></td>")
				.append("			<td colspan='3'><input type='checkbox' /> <label> Revestido</label>&nbsp; &nbsp; &nbsp; &nbsp;<input type='checkbox' /> <label> N&atilde;o Revestido</label></td>")
				.append("		</tr>")
				.append("		<tr>")
				.append("		<td colspan='2'>Produto:&nbsp;"+ listaSubfinalidadesCadastradas.get(i) +"</td>")
				.append("		<td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp; " + listaVazoesCadastradas.get(i) + "</span></td>")
				.append("		</tr>")
				.append("		<tr>")
				.append("			<td colspan='1'>Quantidade:&nbsp;"+ listaQuantidadesCadastradas.get(i) +"</td>")
				.append("			<td colspan='1'>Consumo:&nbsp; " + listaConsumosCadastrados.get(i) + "</td>")
				.append("		</tr>")
				.append("	</tbody>")
				.append(" </table>");


				try { docHtml.select("uso_ind_tag").prepend(String.valueOf(strUsoAquicultura));} 
				catch (Exception e) {docHtml.select("uso_ind_tag").prepend("erro");};

				break;		
				

			default: 

				/*
				 * unir tags html e informacoes do banco para  preencher o formulario 'requerimento de outorga'
				 */
				
				// se não houver cadastro de finalidade não imprimir no  html
				if (! listaFinalidadesCadastradas.get(i).isEmpty()) {
				
				StringBuilder strOutrasFinalidades= new StringBuilder();

				strOutrasFinalidades
				.append("<strong style='font-style: italic; font-size: 12px;'>- " + listaFinalidadesCadastradas.get(i).toUpperCase() + "</strong>" + 
						"<table border='1' cellspacing='0' style='max-width: 800px;'>")
				.append("<tbody><tr><td colspan='2'>Produto:&nbsp;") //  produto
				.append(listaSubfinalidadesCadastradas.get(i))
				.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;") // total
				.append(listaVazoesCadastradas.get(i))
				.append("</span></td></tr><tr><td colspan='1'>Quantidade:&nbsp;") // quantidade
				.append(listaQuantidadesCadastradas.get(i))
				.append("</td><td colspan='1'>Consumo:&nbsp;") // consumo
				.append(listaConsumosCadastrados.get(i))
				.append("</td></tr></tbody></table>");


				try { docHtml.select("outras_fin_tag").prepend(String.valueOf(strOutrasFinalidades));} 
				catch (Exception e) {docHtml.select("outras_fin_tag").prepend("erro");};
				
				}
				
				break;	
			
			}

		}



		/*
		 * preenchimento das vazoes litros/dia litros/hora e periodos/mes
		 */
		for (int i = 0; i<12; i++) {

			try { docHtml.select(strListrosDiaTag[i]).prepend(String.valueOf(listaVazoesDia.get(i)));} catch (Exception e) {docHtml.select(strListrosDiaTag[i]).prepend("");};
			try { docHtml.select(strListrosHoraTag[i]).prepend(String.valueOf(listaTempoHora.get(i)));} catch (Exception e) {docHtml.select(strListrosHoraTag [i]).prepend("");};
			try { docHtml.select(strPeriodoMesTag[i]).prepend(String.valueOf(listaPeriodos.get(i)));} catch (Exception e) {docHtml.select(strPeriodoMesTag [i]).prepend("");};

		}

		String html = new String ();

		html = docHtml.toString();

		html = html.replace("\"", "'");
		html = html.replace("\n", "");

		html =  "\"" + html + "\"";

		return html;

	} // fim metodo criar documento
	
	public void inserirFinalidadeRequerida (
			Document docHtml, 
			Finalidade finSub, 
			Finalidade finSup, 
			String strFinalidade, String strTagFinalidadeRequerida, String vazaoTotal,
			List<String> listVariaveisFinalidades,List<String> listVariaveisSubfinaldades,List<String> listVariaveisQuantidades,List<String> listVariaveisConsumo,
			List<String> listVariaveisVazao,
			List<String> listVariaveisVazaoMes,List<String> listVariaveisVazaoHora,List<String> listVariaveisTempo

			) {

		StringBuilder strTable = new StringBuilder();

		GetterAndSetter gs  = new GetterAndSetter();

		strTable.append("<table border='1' cellspacing='0' style='max-width: 800px;'>"
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
						try{strTable.append("<p>" + df.format(	Double.parseDouble(		(gs.callGetter(finSub, listaVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "")	+ "</p>");
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

						//System.out.println("quantidade md documentos " + gs.callGetter(finSup,listVariaveisQuantidades.get(a)));
						try{	strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listVariaveisQuantidades.get(a))))).replaceAll(",00", "") + "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					strTable.append("<td>");

					// VAZOES REQUERIDAS
					for (int a = 0; a<5; a++) {

						try{	strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup,listaVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "")	+ "</p>");
						} catch (Exception e) {strTable.append("<p>" + "" + "</p>");};
						}

					strTable.append("</td>");

					try{ strTable.append("<td>" + df.format(		Double.parseDouble(		(gs.callGetter(finSup, vazaoTotal)))).replaceAll(",00", "")	+ "</td>");
					} catch (Exception e) {strTable.append("<td>" + "" + "</td>");};
					break;

				default:
					break;
				} // fim while

				//System.out.println(listaMalaDireta.get(i)[0][ii].getClass().getName());

			}

			strTable.append("</tr>");

		} // fim loop for

		strTable.append("</tbody></table>");

		docHtml.select(strTagFinalidadeRequerida).append(String.valueOf(strTable));

	} // fim metodo inserirfinalidade

	public void inserirFinalidadeAutorizada (
			Document docHtml, 
			Finalidade finSub, 
			Finalidade finSup, 
			String strFinalidade, String strTagFinalidadeAutorizada, String vazaoTotal,
			List<String> listVariaveisFinalidades,List<String> listVariaveisSubfinaldades,List<String> listVariaveisQuantidades,List<String> listVariaveisConsumo,
			List<String> listVariaveisVazao,
			List<String> listVariaveisVazaoMes,List<String> listVariaveisVazaoHora,List<String> listVariaveisTempo

			) {
		
		StringBuilder strTable = new StringBuilder();

		GetterAndSetter gs  = new GetterAndSetter();

		strTable.append(
				
				"<table border='1' cellspacing='0' style='max-width: 800px;'>"
						+	"<tbody>"
						+	"<tr>"
						+	"<td style='text-align: center;'>Processo</td>"
						+	"<td style='text-align: center;'>Requerente</td>"
						+	"<td style='text-align: center;'>Solicita&ccedil;&atilde;o</td>"
						+	"<td style='text-align: center;'>Finalidade</td>"
						+	"<td style='text-align: center;'>Quantidade</td>"
						+	"<td style='text-align: center;'>Demanda Solicitada (L/dia)</td>"
						+	"<td style='text-align: center;'>Valor de refer&ecirc;ncia IN 02 (L/dia)</td>"
						+	"<td style='text-align: center;'>Demanda IN 02 (L/dia)</td>"
						+	"<td style='text-align: center;'>Demanda Total ajustada IN 02 (L/dia)</td></tr>"
					
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

						try{strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(f,listaVariaveisVazaoRequeridas.get(a))))).replaceAll(",00", "") + "</p>"); 
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

						try {strTable.append("<p>" + df.format(		Double.parseDouble(		(gs.callGetter(finSub,listaVariaveisVazaoAutorizadas.get(a))))).replaceAll(",00", "") + "</p>");
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

				//System.out.println(listaMalaDireta.get(i)[0][ii].getClass().getName());

			}

			strTable.append("</tr>");

		} // fim loop for

		strTable.append("</tbody></table>");

		docHtml.select(strTagFinalidadeAutorizada).append(String.valueOf(strTable));
		
		
	} // fim metodo inserirfinalidade
	


}
