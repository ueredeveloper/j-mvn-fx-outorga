package requerimentos;

import java.util.ArrayList;
import java.util.List;

public class Piscicultura {

	public static void main(String[] args) {

		List<String> listaFinalidadesCadastradas = new ArrayList<String>();
		List<String> listaSubfinalidadesCadastradas = new ArrayList<String>();
		List<String> listaQuantidadesCadastradas = new ArrayList<String>();
		List<String> listaConsumosCadastrados = new ArrayList<String>();
		List<String> listaVazoesCadastradas = new ArrayList<String>();
		
		
		listaFinalidadesCadastradas.add("Piscicultura");
		listaSubfinalidadesCadastradas.add("Tilápia");
		listaQuantidadesCadastradas.add("12");
		listaConsumosCadastrados.add("45");
		listaVazoesCadastradas.add("1245");
		

	
		
		for(int i=0;i<listaFinalidadesCadastradas.size();i++) {
			
			StringBuilder strOutrasFinalidades= new StringBuilder();

			/*
			strOutrasFinalidades
			.append("<strong style='font-style: italic; font-size: 12px;'>- " 
					+ listaFinalidadesCadastradas.get(i).toUpperCase() + "</strong>" + 
					"<table border='1' cellspacing='0' style='width: 800px;'>")
			.append("<tbody><tr><td colspan='2'>Produto:&nbsp;") //  produto
			.append(listaSubfinalidadesCadastradas.get(i))
			.append("</td><td rowspan='3' width='20%'><span style='text-align: justify;'>Total:&nbsp;") // total
			.append(listaVazoesCadastradas.get(i))
			.append("</span></td></tr><tr><td colspan='1'>Quantidade:&nbsp;") // quantidade
			.append(listaQuantidadesCadastradas.get(i))
			.append("</td><td colspan='1'>Consumo:&nbsp;") // consumo
			.append(listaConsumosCadastrados.get(i))
			.append("</td></tr></tbody></table>");
			*/
			
			strOutrasFinalidades
			.append("<p><strong style='font-style: italic; font-size: 12px;'>- "+ listaFinalidadesCadastradas.get(i).toUpperCase() + "</strong></p>")		
			.append(" <table border='1' cellspacing='0' style='width: 800px;'>")
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


			
			
			System.out.println(strOutrasFinalidades);
			
		}

	
		
		
		

	}

}
