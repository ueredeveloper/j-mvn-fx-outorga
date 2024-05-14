package principal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entidades.Endereco;
import entidades.Finalidade;
import entidades.GetterAndSetter;
import entidades.Interferencia;
import entidades.Subterranea;
import entidades.Usuario;
import util.FormatadorCPFCNPJ;

public class MalaDiretaAnexoParecer {
	
	

	/*
	 * tags anexo
	 * 
	 * <us_nome_tag></us_nome_tag>
	 * <us_cpfcnpj_tag></us_cpfcnpj_tag>
	 * 
	 * <end_log_tag></end_log_tag>
	 * 
	 * <finalidades_tag></finalidades_tag>
	 * 
	 * <inter_tipo_poco_tag></inter_tipo_poco_tag>
	 * <inter_lat_tag></inter_lat_tag>,<inter_lon_tag></inter_lon_tag></li>
	 * 
	 * 
	 * 	<inter_tipo_poco_tag></inter_tipo_poco_tag>
	  	<inter_prof_tag></inter_prof_tag>
		<inter_nivel_est_tag></inter_nivel_est_tag>
		<inter_niv_din_tag></inter_niv_din_tag>
		<inter_vazao_tag></inter_vazao_tag>
		
		tabelas
		
		<div align="justify"><inter_dados_basicos_tag></inter_dados_basicos_tag></div>
	 * 
	 */

	List<Object[][]> listMalaDireta = new ArrayList<>();
	String strAnexoParecer;
	String strTabela1, strTabela2;
	
	List<String> listVariaveisFinalidadesAutorizadas = Arrays.asList("faFinalidade1", "faFinalidade2", "faFinalidade3", "faFinalidade4", "faFinalidade5");
	
	GetterAndSetter gs  = new GetterAndSetter();
	
	
	String q_litros_hora_tag [] = {
			
			"q_litros_hora_jan_tag",
			"q_litros_hora_fev_tag",
			"q_litros_hora_mar_tag",
			"q_litros_hora_abr_tag",
			"q_litros_hora_mai_tag",
			"q_litros_hora_jun_tag",
			"q_litros_hora_jul_tag",
			"q_litros_hora_ago_tag",
			"q_litros_hora_set_tag",
			"q_litros_hora_out_tag",
			"q_litros_hora_nov_tag",
			"q_litros_hora_dez_tag"
			
	};
	
	String q_metros_hora_tag [] = {
			
			"q_metros_hora_jan_tag",
			"q_metros_hora_fev_tag",
			"q_metros_hora_mar_tag",
			"q_metros_hora_abr_tag",
			"q_metros_hora_mai_tag",
			"q_metros_hora_jun_tag",
			"q_metros_hora_jul_tag",
			"q_metros_hora_ago_tag",
			"q_metros_hora_set_tag",
			"q_metros_hora_out_tag",
			"q_metros_hora_nov_tag",
			"q_metros_hora_dez_tag"
};
	
	
	String t_dias_mes_tag [] = {
			
			"t_dias_mes_jan_tag",
			"t_dias_mes_fev_tag",
			"t_dias_mes_mar_tag",
			"t_dias_mes_abr_tag",
			"t_dias_mes_mai_tag",
			"t_dias_mes_jun_tag",
			"t_dias_mes_jul_tag",
			"t_dias_mes_ago_tag",
			"t_dias_mes_set_tag",
			"t_dias_mes_out_tag",
			"t_dias_mes_nov_tag",
			"t_dias_mes_dez_tag",
			
	};
	
	String q_metros_mes_tag [] = {
			
		"q_metros_mes_jan_tag",
		"q_metros_mes_fev_tag",
		"q_metros_mes_mar_tag",
		"q_metros_mes_abr_tag",
		"q_metros_mes_mai_tag",
		"q_metros_mes_jun_tag",
		"q_metros_mes_jul_tag",
		"q_metros_mes_ago_tag",
		"q_metros_mes_set_tag",
		"q_metros_mes_out_tag",
		"q_metros_mes_nov_tag",
		"q_metros_mes_dez_tag",
	
};
	
	
	
	String q_metros_dia_tag [] = {
			
			"q_metros_dia_jan_tag",
			"q_metros_dia_fev_tag",
			"q_metros_dia_mar_tag",
			"q_metros_dia_abr_tag",
			"q_metros_dia_mai_tag",
			"q_metros_dia_jun_tag",
			"q_metros_dia_jul_tag",
			"q_metros_dia_ago_tag",
			"q_metros_dia_set_tag",
			"q_metros_dia_out_tag",
			"q_metros_dia_nov_tag",
			"q_metros_dia_dez_tag",
	
};
	
	
	
	String variaveis_litros_dia [] = {
	
				"faQDiaJan","faQDiaFev","faQDiaMar","faQDiaAbr","faQDiaMai","faQDiaJun",
				"faQDiaJul","faQDiaAgo","faQDiaSet","faQDiaOut","faQDiaNov","faQDiaDez"	

	};
	
	String t_horas_dia_tag [] = {
	
			"t_horas_dia_jan_tag",
			"t_horas_dia_fev_tag",
			"t_horas_dia_mar_tag",
			"t_horas_dia_abr_tag",
			"t_horas_dia_mai_tag",
			"t_horas_dia_jun_tag",
			"t_horas_dia_jul_tag",
			"t_horas_dia_ago_tag",
			"t_horas_dia_set_tag",
			"t_horas_dia_out_tag",
			"t_horas_dia_nov_tag",
			"t_horas_dia_dez_tag"

	};
	
	
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

	
	// formatador de cpf e cnpj
	FormatadorCPFCNPJ ccFormato = new FormatadorCPFCNPJ();
	
	//Elements do Jsoup para retirar tags desnecessarias do html criado
	Elements elem;
	
	public MalaDiretaAnexoParecer (List<Object[][]> listMalaDireta, String strAnexoParecer, String strTabela1, String strTabela2) {
		
		this.listMalaDireta = listMalaDireta;
		this.strAnexoParecer = strAnexoParecer;
		this.strTabela1 = strTabela1;
		this.strTabela2 = strTabela2;
	}
	
	// formatar 1000.50 para 1.000,50 e retirar zeros irrelevantes como ,00 - 15.00 fica 15
	DecimalFormat df = new DecimalFormat("#,##0.00"); 
	
	// trazer um numero que informe qual é o usuario que será anexado pela interferencia
	
	// trazer duas tabelas para o anexo
	
	/**
	 * 
	 * @param in = interferencia selecionada no combobox na tabNavegador
	 * @return
	 */
	public String criarAnexoParecer (int in) {
		
		Document docHtml= null;
		Document docHtmlTabelasLimitesOutorgados = null;
		Document docHTMLTabelaPontoCaptacao = null;

		docHtml = Jsoup.parse(strAnexoParecer, "UTF-8").clone();
		
		//<us_nome_tag></us_nome_tag>
		 //* <us_cpfcnpj_tag></us_cpfcnpj_tag>

		try { docHtml.select("us_nome_tag").prepend(((Usuario)listMalaDireta.get(in)[0][1]).getUsNome());
		
			// limpar o html de tags desnecessarias
			elem = docHtml.select("us_nome_tag");
			elem.tagName("span");
		} 
			catch (Exception e) {docHtml.select("us_nome_tag").prepend("");
		
		// limpar o html de tags desnecessarias
		elem = docHtml.select("us_nome_tag");
		elem.tagName("span");
		
		};
	
		// formatador de cpf e cnpj - ccFormato (tipo pessoa - fisica ou jurica e cpf ou cnpj )	
		try { docHtml.select("us_cpfcnpj_tag").prepend(ccFormato.formatCnpj(((Usuario)listMalaDireta.get(in)[0][1]).getUsTipo(),((Usuario)listMalaDireta.get(in)[0][1]).getUsCPFCNPJ()));
		
		// limpar o html de tags desnecessarias
					elem = docHtml.select("us_cpfcnpj_tag");
					elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("us_cpfcnpj_tag").prepend("");
			
			// limpar o html de tags desnecessarias
			elem = docHtml.select("us_cpfcnpj_tag");
			elem.tagName("span");
			};

		
		/*
		 * Logradouro (Tabela Endereco) concatenando Regiao Adminstrativa (Tabela Endereco) e " - Distrito Federal"
		 */
		try { docHtml.select("end_log_tag").prepend( 	
				((Endereco)listMalaDireta.get(in)[0][3]).getEndLogradouro() 
				+ ", " 
				+ ((Endereco)listMalaDireta.get(in)[0][3]).getEndRAFK().getRaNome()	
				+ " - Distrito Federal."	
				);
	
		elem = docHtml.select("end_log_tag");
		elem.tagName("span");
		
		} 
		
		catch (Exception e) {docHtml.select("end_log_tag").prepend("");
		
		elem = docHtml.select("end_log_tag");
		elem.tagName("span");
		
		
		};
		
		//  * <finalidades_tag></finalidades_tag>
		
		for (Finalidade f : ((Interferencia) listMalaDireta.get(in)[0][2]).getFinalidades()) {
        	
        	if ( f.getClass().getName() == "entidades.FinalidadeAutorizada") {
				
        		for (int i = 0; i<5; i++) {
				
        			
        			try { docHtml.select("finalidades_tag").append((gs.callGetter(f, listVariaveisFinalidadesAutorizadas.get(i)) + ", ").toLowerCase());
        			
        			
        			} 
        			catch (Exception e) {docHtml.select("finalidades_tag").prepend("");
        			
        			
        			};
					
				}
        	}
		} // fim for finalidade

		elem = docHtml.select("finalidades_tag");
		elem.tagName("span");
		
		
		
		try { docHtml.select("inter_lat_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getInterDDLatitude()
				+ "," +
				((Subterranea)listMalaDireta.get(in)[0][2]).getInterDDLongitude())
			;
		
		elem = docHtml.select("inter_lat_tag");
		elem.tagName("span");
		
		} catch (Exception e) {docHtml.select("inter_lat_tag").prepend("");
		

		elem = docHtml.select("inter_lat_tag");
		elem.tagName("span");
		
		};
		
		try { docHtml.select("inter_tipo_poco_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubTipoPocoFK().getTipoPocoDescricao());
		
		elem = docHtml.select("inter_tipo_poco_tag");
		elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("inter_tipo_poco_tag").prepend("");
			
			elem = docHtml.select("inter_tipo_poco_tag");
			elem.tagName("span");
			
			};
			
			
		try { docHtml.select("inter_prof_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubProfundidade());
		
		elem = docHtml.select("inter_prof_tag");
		elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("inter_prof_tag").prepend("");
			
			elem = docHtml.select("inter_prof_tag");
			elem.tagName("span");
			
			};
			
			
		try { docHtml.select("inter_nivel_est_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubEstatico());
		
		elem = docHtml.select("inter_nivel_est_tag");
		elem.tagName("span");
		
		
		} 
			catch (Exception e) {docHtml.select("inter_nivel_est_tag").prepend("");
			
			elem = docHtml.select("inter_nivel_est_tag");
			elem.tagName("span");
			
			
			};
		try { docHtml.select("inter_niv_din_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubDinamico());

		elem = docHtml.select("inter_niv_din_tag");
		elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("inter_niv_din_tag").prepend("");
			
			elem = docHtml.select("inter_niv_din_tag");
			elem.tagName("span");
			
			
			};
			
			// vazao teste
			try { docHtml.select("inter_vazao_teste_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubVazaoTeste());
			
			elem = docHtml.select("inter_vazao_teste_tag");
			elem.tagName("span");
			
			
			} 
				catch (Exception e) {docHtml.select("inter_vazao_teste_tag").prepend("");
				
				elem = docHtml.select("inter_vazao_teste_tag");
				elem.tagName("span");
				
				
				};
				
				// vazao subsistema
				try { docHtml.select("inter_vazao_subsistema_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getSubVazaoSubsistema());
				
				elem = docHtml.select("inter_vazao_subsistema_tag");
				elem.tagName("span");
				
				
				} 
					catch (Exception e) {docHtml.select("inter_vazao_subsistema_tag").prepend("");
					
					elem = docHtml.select("inter_vazao_subsistema_tag");
					elem.tagName("span");
					
					
					};
					
		// vazao outorgada	
		try { docHtml.select("inter_vazao_tag").prepend(df.format(((Subterranea)listMalaDireta.get(in)[0][2]).getSubVazaoOutorgada()).replaceAll(",00", ""));
		
		elem = docHtml.select("inter_vazao_tag");
		elem.tagName("span");
		
		
		} 
			catch (Exception e) {docHtml.select("inter_vazao_tag").prepend("");
			
			elem = docHtml.select("inter_vazao_tag");
			elem.tagName("span");
			
			
			};
		
		/*
		 * <li>Coordenadas SIRGAS 2000: <inter_lat_tag></inter_lat_tag>,<inter_lon_tag></inter_lon_tag></li>
				<li>Tipo de Poço: <inter_tipo_poco_tag></inter_tipo_poco_tag></li>
				<li>Profundidade: <inter_prof_tag></inter_prof_tag>.</li>
				<li>Nível Estático N.E: <inter_nivel_est_tag></inter_nivel_est_tag>.</li>
				<li>Nível Dinâmico N.D: <inter_niv_din_tag></inter_niv_din_tag>.</li>
				<li>Vazão média do Subsistema (L/h):</li>
				<li>Vazão teste (L/h): <inter_vazao_tag></inter_vazao_tag>.</li>
		 */
		
		
		docHTMLTabelaPontoCaptacao = Jsoup.parse(strTabela1, "UTF-8").clone();
		
		
		try { docHTMLTabelaPontoCaptacao.select("inter_bacia_tag").prepend(((Subterranea)listMalaDireta.get(in)[0][2]).getInterBaciaFK().getBaciaNome());
		elem = docHtml.select("inter_bacia_tag");
		elem.tagName("span");
		
		
		} 
			catch (Exception e) {docHtml.select("inter_bacia_tag").prepend("");
			
			
			elem = docHtml.select("inter_bacia_tag");
			elem.tagName("span");
			};
		//unidade hidrografica
		try { docHTMLTabelaPontoCaptacao.select("inter_uh_tag").prepend(String.valueOf(((Subterranea)listMalaDireta.get(in)[0][2]).getInterUHFK().getUhNome()));
		
		elem = docHtml.select("inter_uh_tag");
		elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("inter_uh_tag").prepend("");
			
			elem = docHtml.select("inter_uh_tag");
			elem.tagName("span");
			};
		try { docHTMLTabelaPontoCaptacao.select("inter_lat_tag").prepend(String.valueOf(((Subterranea)listMalaDireta.get(in)[0][2]).getInterDDLatitude()));
		
		elem = docHtml.select("inter_lat_tag");
		elem.tagName("span");
		
		} 
			catch (Exception e) {docHtml.select("inter_lat_tag").prepend("");
			elem = docHtml.select("inter_lat_tag");
			elem.tagName("span");
			
			};
		try { docHTMLTabelaPontoCaptacao.select("inter_lon_tag").prepend(String.valueOf(((Subterranea)listMalaDireta.get(in)[0][2]).getInterDDLongitude()));
		
		elem = docHtml.select("inter_lon_tag");
		elem.tagName("span");
		} 
			catch (Exception e) {docHtml.select("inter_lon_tag").prepend("");
			elem = docHtml.select("inter_lon_tag");
			elem.tagName("span");
			
			};
		
		
		docHtmlTabelasLimitesOutorgados = Jsoup.parse(strTabela2, "UTF-8").clone();
	
		
		for (Finalidade f : ((Interferencia) listMalaDireta.get(in)[0][2]).getFinalidades() ) {
			
			if (f.getClass().getName() == "entidades.FinalidadeAutorizada") {
				
				double dbl_q_metros_hora;
				int int_t_horas_dia;
				int int_t_dias_mes;
			
				for (int i = 0; i<12; i++) {
		
					
					//metro cubicos hora
					try {dbl_q_metros_hora = (((Subterranea) listMalaDireta.get(in)[0][2]).getSubVazaoOutorgada())/1000;} 
						catch (Exception e ) {
						dbl_q_metros_hora = 0.0;
					
					}
					
					// horas dia
					try {int_t_horas_dia = Integer.parseInt(	gs.callGetter(f, listVariaveisVazaoHoraAutorizadas.get(i))	);} 
						catch (Exception e ) {
						int_t_horas_dia = 0;
						
					}
					
					// dias por mes
					try {int_t_dias_mes = Integer.parseInt(		gs.callGetter(f,listVariaveisTempoAutorizadas.get(i))	);} 
						catch (Exception e ) {
						int_t_dias_mes = 0;
		
					}
				
					// litros hora l/h
					try { docHtmlTabelasLimitesOutorgados.select(q_litros_hora_tag [i]).prepend(df.format((((Subterranea) listMalaDireta.get(in)[0][2])).getSubVazaoOutorgada()) .replaceAll(",00", ""));
					
					elem = docHtmlTabelasLimitesOutorgados.select(q_litros_hora_tag [i]);
					elem.tagName("span");
					
					} 
						
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(q_litros_hora_tag[i]).prepend("");
						elem = docHtmlTabelasLimitesOutorgados.select(q_litros_hora_tag [i]);
						elem.tagName("span");
						
						
						};
					
					// metros hora m/h
					try { docHtmlTabelasLimitesOutorgados.select(q_metros_hora_tag [i]).prepend(  df.format(	dbl_q_metros_hora ) .replaceAll(",00", "") );
					elem = docHtmlTabelasLimitesOutorgados.select(q_metros_hora_tag [i]);
					elem.tagName("span");
					
					} 
					
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(q_metros_hora_tag[i]).prepend("");
						
						elem = docHtmlTabelasLimitesOutorgados.select(q_metros_hora_tag [i]);
						elem.tagName("span");
						
						};
					
					// horas por dia h/h	
					try { docHtmlTabelasLimitesOutorgados.select(t_horas_dia_tag [i]).prepend( String.valueOf(int_t_horas_dia) );
					
					elem = docHtmlTabelasLimitesOutorgados.select(t_horas_dia_tag [i]);
					elem.tagName("span");
					} 
					
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(t_horas_dia_tag[i]).prepend("");
						
						elem = docHtmlTabelasLimitesOutorgados.select(t_horas_dia_tag [i]);
						elem.tagName("span");
						
						};
					
					// metros cubicos dia m/d	
					try { docHtmlTabelasLimitesOutorgados.select(q_metros_dia_tag[i]).prepend( df.format(		 dbl_q_metros_hora*int_t_horas_dia).replaceAll(",00", "") );
					elem = docHtmlTabelasLimitesOutorgados.select(q_metros_dia_tag [i]);
					elem.tagName("span");
					
					} 
					
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(q_metros_dia_tag[i]).prepend("");
						
						elem = docHtmlTabelasLimitesOutorgados.select(q_metros_dia_tag [i]);
						elem.tagName("span");
						
						
						};
							
						
					try { docHtmlTabelasLimitesOutorgados.select(t_dias_mes_tag [i]).prepend( String.valueOf(int_t_dias_mes) );
					
					elem = docHtmlTabelasLimitesOutorgados.select(t_dias_mes_tag [i]);
					elem.tagName("span");
					
					} 
						
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(t_dias_mes_tag[i]).prepend("");
						
						elem = docHtmlTabelasLimitesOutorgados.select(t_dias_mes_tag [i]);
						elem.tagName("span");
						
						};
						
						
					try { docHtmlTabelasLimitesOutorgados.select(q_metros_mes_tag [i]).prepend( df.format(		 dbl_q_metros_hora*int_t_horas_dia*int_t_dias_mes) .replaceAll(",00", ""));
					
					elem = docHtmlTabelasLimitesOutorgados.select(q_metros_mes_tag [i]);
					elem.tagName("span");
					
					} 
			
						catch (Exception e) {docHtmlTabelasLimitesOutorgados.select(q_metros_mes_tag[i]).prepend("");
						
						elem = docHtmlTabelasLimitesOutorgados.select(q_metros_mes_tag [i]);
						elem.tagName("span");
						};
						
				}		
					
			} // fim if finalidade autorizada
					
		
		} // fim loop tabela 2	
		
		
		docHtml.select("tabela_ponto_captacao_tag").append(String.valueOf(docHTMLTabelaPontoCaptacao));
		
		elem = docHtml.select("tabela_ponto_captacao_tag");
		elem.tagName("span");
		
		
		docHtml.select("tabela_limites_outorgados_tag").append(String.valueOf(docHtmlTabelasLimitesOutorgados));
		
		elem = docHtml.select("tabela_limites_outorgados_tag");
		elem.tagName("span");
		
		// retirar tag do html - Bacia Hidrografica
		elem = docHtml.select("inter_bacia_tag");
		elem.tagName("span");
		
		elem = docHtml.select("inter_uh_tag");
		elem.tagName("span");
		
		elem = docHtml.select("inter_lat_tag");
		elem.tagName("span");
		
		elem = docHtml.select("inter_lon_tag");
		elem.tagName("span");
		
		// a tag title confunde o navegador retirando a tag table  de lugar, entao e preciso retirar a tag title
		elem = docHtml.select("title");
		elem.tagName("span");
				
		
		
		String html = new String ();
		
		html = docHtml.toString();

		html = html.replace("\"", "'");
		html = html.replace("\n", "");

		html =  "\"" + html + "\"";
		
		
		return html;
		
	}
	
	
	
}
