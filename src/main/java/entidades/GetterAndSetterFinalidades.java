package entidades;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GetterAndSetterFinalidades {

	String [] strVariaveisFinalidades;
	String [] strVariaveisSubfinaldades;
	String [] strVariaveisQuantidades;
	String [] strVariaveisConsumo;
	String [] strVariaveisVazao;

	String [] strVariaveisVazaoLD;
	String [] strVariaveisTempoHD;
	String [] strVariaveisPeriodoDM;

	String vazaoTotal;

	String frVazaoTotal = "frVazaoTotal";

	String faVazaoTotal = "faVazaoTotal";

	// variaveis de finalidade e reflexao //
	String strVariaveisFinalidadesRequeridas [] = {"frFinalidade1", "frFinalidade2", "frFinalidade3", "frFinalidade4", "frFinalidade5"};
	String strVariaveisSubfinaldadesRequeridas  [] = {"frSubfinalidade1", "frSubfinalidade2", "frSubfinalidade3", "frSubfinalidade4", "frSubfinalidade5"};
	String strVariaveisQuantidadesRequeridas  [] = {"frQuantidade1", "frQuantidade2", "frQuantidade3", "frQuantidade4", "frQuantidade5"};
	String strVariaveisConsumoRequeridas  [] = {"frConsumo1", "frConsumo2", "frConsumo3", "frConsumo4", "frConsumo5"};
	String strVariaveisVazaoRequeridas  [] = {"frVazao1", "frVazao2", "frVazao3", "frVazao4", "frVazao5"};

	// variaveis de vazao mensal e reflexao //
	String strVariaveisVazaoMesRequeridas  [] = {

			"frQDiaJan","frQDiaFev","frQDiaMar","frQDiaAbr","frQDiaMai","frQDiaJun",
			"frQDiaJul","frQDiaAgo","frQDiaSet","frQDiaOut","frQDiaNov","frQDiaDez",	

	};

	String strVariaveisVazaoHoraRequeridas  [] = {
			"frQHoraJan","frQHoraFev","frQHoraMar","frQHoraAbr","frQHoraMai","frQHoraJun",
			"frQHoraJul","frQHoraAgo","frQHoraSet","frQHoraOut","frQHoraNov","frQHoraDez",

	};

	String strVariaveisTempoRequeridas   [] = {

			"frTempoCapJan","frTempoCapFev","frTempoCapMar","frTempoCapAbr","frTempoCapMai","frTempoCapJun",
			"frTempoCapJul","frTempoCapAgo","frTempoCapSet","frTempoCapOut","frTempoCapNov","frTempoCapDez",

	};

	// variaveis de finalidade e reflexao //
	String strVariaveisFinalidadesAutorizadas [] = {"faFinalidade1", "faFinalidade2", "faFinalidade3", "faFinalidade4", "faFinalidade5"};
	String strVariaveisSubfinaldadesAutorizadas  [] = {"faSubfinalidade1", "faSubfinalidade2", "faSubfinalidade3", "faSubfinalidade4", "faSubfinalidade5"};
	String strVariaveisQuantidadesAutorizadas  [] = {"faQuantidade1", "faQuantidade2", "faQuantidade3", "faQuantidade4", "faQuantidade5"};
	String strVariaveisConsumoAutorizadas  [] = {"faConsumo1", "faConsumo2", "faConsumo3", "faConsumo4", "faConsumo5"};
	String strVariaveisVazaoAutorizadas  [] = {"faVazao1", "faVazao2", "faVazao3", "faVazao4", "faVazao5"};

	// variaveis de vazao mensal e reflexao //
	String strVariaveisVazaoMesAutorizadas  [] = {

			"faQDiaJan","faQDiaFev","faQDiaMar","faQDiaAbr","faQDiaMai","faQDiaJun",
			"faQDiaJul","faQDiaAgo","faQDiaSet","faQDiaOut","faQDiaNov","faQDiaDez",	

	};

	String strVariaveisVazaoHoraAutorizadas  [] = {
			"faQHoraJan","faQHoraFev","faQHoraMar","faQHoraAbr","faQHoraMai","faQHoraJun",
			"faQHoraJul","faQHoraAgo","faQHoraSet","faQHoraOut","faQHoraNov","faQHoraDez",

	};

	String strVariaveisTempoAutorizadas   [] = {

			"faTempoCapJan","faTempoCapFev","faTempoCapMar","faTempoCapAbr","faTempoCapMai","faTempoCapJun",
			"faTempoCapJul","faTempoCapAgo","faTempoCapSet","faTempoCapOut","faTempoCapNov","faTempoCapDez",

	};

	public void inicializarVariaveisFinalidadesRequeridas () {

		strVariaveisFinalidades = this.strVariaveisFinalidadesRequeridas;
		strVariaveisSubfinaldades = this.strVariaveisSubfinaldadesRequeridas;
		strVariaveisQuantidades = this.strVariaveisQuantidadesRequeridas;
		strVariaveisConsumo = this.strVariaveisConsumoRequeridas;
		strVariaveisVazao = this.strVariaveisVazaoRequeridas;

		strVariaveisVazaoLD = this.strVariaveisVazaoMesRequeridas;
		strVariaveisTempoHD = this.strVariaveisVazaoHoraRequeridas;
		strVariaveisPeriodoDM = this.strVariaveisTempoRequeridas;
		vazaoTotal = this.frVazaoTotal;


	}

	public void inicializarVariaveisFinalidadesAutorizadas () {

		strVariaveisFinalidades = this.strVariaveisFinalidadesAutorizadas;
		strVariaveisSubfinaldades = this.strVariaveisSubfinaldadesAutorizadas;
		strVariaveisQuantidades = this.strVariaveisQuantidadesAutorizadas;
		strVariaveisConsumo = this.strVariaveisConsumoAutorizadas;
		strVariaveisVazao = this.strVariaveisVazaoAutorizadas;

		strVariaveisVazaoLD = this.strVariaveisVazaoMesAutorizadas;
		strVariaveisTempoHD = this.strVariaveisVazaoHoraAutorizadas;
		strVariaveisPeriodoDM = this.strVariaveisTempoAutorizadas;
		vazaoTotal = this.faVazaoTotal;


	}


	DecimalFormat df = new DecimalFormat("#,##0.00");  
	
	public void imprimirFinalidade (
			Finalidade finalidade, 
			TextField [] tfListFinalidades, TextField [] tfListSubfinalidades, TextField [] tfListQuantidades, TextField [] tfListConsumo, 
			TextField [] tfListFinVazoes,
			Label lblCalTotal,
			TextField [] tfVazoesLD, TextField [] tfTempoHD, TextField []  tfPeriodoDM

			) {

		// tabela de finalidades e consumo  //

		// Finalidades Sub Quan Cons e Vazao //
		entidades.GetterAndSetter gs  = new entidades.GetterAndSetter();
  
		for (int i = 0; i< strVariaveisFinalidades.length; i++) {

			tfListFinalidades[i].setText(gs.callGetter(finalidade, strVariaveisFinalidades[i]));
			tfListSubfinalidades[i].setText(gs.callGetter(finalidade, strVariaveisSubfinaldades[i]));

			// fazer um parse para os valores double, por ex: 14555.50 parsear para 14.555,50
			try {tfListQuantidades[i].setText(		df.format(	Double.parseDouble(gs.callGetter(finalidade, strVariaveisQuantidades[i])	)	)	);} 
			catch (Exception e) {tfListQuantidades[i].setText("");};

			try {tfListConsumo[i].setText( 			df.format(	Double.parseDouble(gs.callGetter(finalidade, strVariaveisConsumo[i])		)	)	 );} 
			catch (Exception e) {tfListConsumo[i].setText("");};

			try {tfListFinVazoes[i].setText(   		df.format(	 Double.parseDouble( gs.callGetter(finalidade, strVariaveisVazao[i])		)	)	 );} 
			catch (Exception e) {tfListFinVazoes[i].setText("");};


		}

		try {lblCalTotal.setText(  df.format(	Double.parseDouble(    gs.callGetter(finalidade, vazaoTotal    					)	)	)	);    } 

		catch (Exception e) {lblCalTotal.setText("");};

		for (int i = 0; i< strVariaveisVazaoLD.length; i++) {

			try {tfVazoesLD[i].setText( df.format(	 Double.parseDouble( 	gs.callGetter(finalidade, strVariaveisVazaoLD[i])		)	)	 );} 
			catch (Exception e) {tfVazoesLD[i].setText("");};
			//String.valueOf(gs.callGetter(finalidade, strVariaveisVazaoMes[i]).replace('.', ',')));

			tfTempoHD[i].setText(gs.callGetter(finalidade, strVariaveisTempoHD[i]));
			tfPeriodoDM[i].setText(gs.callGetter(finalidade, strVariaveisPeriodoDM[i]));

		}


	}

	public void capturarFinalidade (
			Finalidade finalidade, 
			TextField [] tfListFinalidades, TextField [] tfListSubfinalidades, TextField [] tfListQuantidades, TextField [] tfListConsumo, 
			TextField [] tfListFinVazoes,
			Label lblCalTotal,
			TextField [] tfVazoesLD, TextField [] tfTempoHD, TextField []  tfPeriodoDM
			) {

		// Finalidades Sub Quan Cons e Vazao //
		GetterAndSetter gs  = new GetterAndSetter();
		DecimalFormat df = new DecimalFormat("#,##0.00"); 

		// TABELA FINALIDADES LENGTH = 5
		for (int i = 0; i< strVariaveisFinalidades.length; i++) {

			System.out.println("lista finalidades " + tfListFinalidades[i].getText());

			//FINALIDADE
			gs.callSetter(finalidade, strVariaveisFinalidades[i], tfListFinalidades[i].getText());
			// SUBFINALIDADE
			gs.callSetter(finalidade, strVariaveisSubfinaldades[i], tfListSubfinalidades[i].getText());

			// QUANTIDADE (40 PESSOAS, 10 HECTARES )
			if ( tfListQuantidades[i].getText().isEmpty() ) { 

				gs.callSetter(finalidade, strVariaveisQuantidades[i], 0.0);

			} else {

				try {gs.callSetter(finalidade, strVariaveisQuantidades[i], Double.parseDouble(df.parseObject(tfListQuantidades[i].getText()).toString()));
				} catch (Exception e) {
					gs.callSetter(finalidade, strVariaveisQuantidades[i], 0.0);
				}

			} // fim else

			// CONSUMO (110 LISTROS POR PESSOA, 10 LITROS POR METRO QUADRADO)
			if ( tfListConsumo[i].getText().isEmpty() ) {
				gs.callSetter(finalidade, strVariaveisConsumo[i], 0.0);

			} else {

				try {
					gs.callSetter(finalidade, strVariaveisConsumo[i], Double.parseDouble(df.parseObject(tfListConsumo[i].getText()).toString()));
				} catch (Exception e) {
					gs.callSetter(finalidade, strVariaveisConsumo[i], 0.0);

				}

			}

			// VAZOES POR FINALIDADE (1100 LITROS PARA ABASTECER 10 PESSOAS )
			if (tfListFinVazoes[i].getText().isEmpty()	) {
				gs.callSetter(finalidade, strVariaveisVazao[i], 0.0);
			} else {
				try {
					gs.callSetter(finalidade, strVariaveisVazao[i], Double.parseDouble(df.parseObject(tfListFinVazoes[i].getText()).toString()));
				} catch (Exception e) {

					e.printStackTrace();
					System.out.println(e);

				}

			}


		} // fim loop for variaveis finalidades, subfinalidades, quantidades, consumo


		// VAZAO TOTAL (SOMATORIO DAS FINALIDADES )
		try {
			// formatar 15.555,56 para double 15555.56
			gs.callSetter(finalidade, vazaoTotal, Double.parseDouble(df.parseObject(lblCalTotal.getText()).toString()));
		} catch (Exception e) {
			System.out.println(e);
			gs.callSetter(finalidade, vazaoTotal, 0.0);
		};


		// TABELA DO CONSUMO MES A MES	LENGTH = 12	

		for (int i = 0; i< strVariaveisVazaoLD.length; i++) {

			// VAZAO (L/D) VAZAO USADA DE UM DIA NO MES
			if ( tfVazoesLD[i].getText().isEmpty() ) {
				// Conferir se há letras e mudar por zero
				//strVazoesMes = String.valueOf(tfVazoesLD[i].getText()).replaceAll("[a-zA-Z]", "0");
				// Padronizar a forma de salvar, mudando virgula por ponto

				gs.callSetter(finalidade, strVariaveisVazaoLD[i], 0.0);

			} else {

				try {
					// formatar 15.555,56 para double 15555.56
					gs.callSetter(finalidade, strVariaveisVazaoLD[i], Double.parseDouble(df.parseObject(tfVazoesLD[i].getText()).toString()));
				} catch (Exception e) {
					gs.callSetter(finalidade, strVariaveisVazaoLD[i], 0.0);

				};


			}


			if (tfTempoHD[i].getText().isEmpty() ) {

				gs.callSetter(finalidade, strVariaveisTempoHD[i], 0 );

			}  else {

				try {
					gs.callSetter(finalidade, strVariaveisTempoHD[i], NumberFormat.getInstance().parse(tfTempoHD[i].getText()).intValue() );
				} catch (Exception e) {
					System.out.println(e);
					gs.callSetter(finalidade, strVariaveisTempoHD[i], 0 );
				}


			}

			if (tfPeriodoDM[i].getText().isEmpty() ) {

				gs.callSetter(finalidade, strVariaveisPeriodoDM[i], 0);

				System.out.println("periodo empty ");
			} else {

				try {
					gs.callSetter(finalidade, strVariaveisPeriodoDM[i], NumberFormat.getInstance().parse(tfPeriodoDM[i].getText()).intValue() );
				} catch (Exception e) {
					System.out.println(e);
					gs.callSetter(finalidade, strVariaveisPeriodoDM[i], 0);
				}

			}

		} // fim loop for variaveis vazao mes


	}

}
