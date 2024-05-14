package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name = "Finalidade_Requerida")
@PrimaryKeyJoinColumn(name="fr_Finalidade_FK")
public class FinalidadeRequerida extends Finalidade {

	// 1 //
	@Column (name="fr_Finalidade_1",columnDefinition="varchar(500)")
	private String frFinalidade1;

	@Column (name="fr_Subfinalidade_1",columnDefinition="varchar(500)")
	private String frSubfinalidade1;

	@Column (name="fr_Quantidade_1")
	private Double frQuantidade1;

	@Column (name="fr_Consumo_1")
	private Double frConsumo1;

	@Column (name="fr_Vazao_1")
	private Double frVazao1;

	// 2 //
	@Column (name="fr_Finalidade_2",columnDefinition="varchar(500)")
	private String frFinalidade2;

	@Column (name="fr_Subfinalidade_2",columnDefinition="varchar(500)")
	private String frSubfinalidade2;

	@Column (name="fr_Quantidade_2")
	private Double frQuantidade2;

	@Column (name="fr_Consumo_2")
	private Double frConsumo2;

	@Column (name="fr_Vazao_2")
	private Double frVazao2;

	// 3 //
	@Column (name="fr_Finalidade_3",columnDefinition="varchar(500)")
	private String frFinalidade3;

	@Column (name="fr_Subfinalidade_3",columnDefinition="varchar(500)")
	private String frSubfinalidade3;

	@Column (name="fr_Quantidade_3")
	private Double frQuantidade3;

	@Column (name="fr_Consumo_3")
	private Double frConsumo3;

	@Column (name="fr_Vazao_3")
	private Double frVazao3;

	// 4 //
	@Column (name="fr_Finalidade_4",columnDefinition="varchar(500)")
	private String frFinalidade4;

	@Column (name="fr_Subfinalidade_4",columnDefinition="varchar(500)")
	private String frSubfinalidade4;

	@Column (name="fr_Quantidade_4")
	private Double frQuantidade4;

	@Column (name="fr_Consumo_4")
	private Double frConsumo4;

	@Column (name="fr_Vazao_4")
	private Double frVazao4;

	// 5 //
	@Column (name="fr_Finalidade_5",columnDefinition="varchar(500)")
	private String frFinalidade5;

	@Column (name="fr_Subfinalidade_5",columnDefinition="varchar(500)")
	private String frSubfinalidade5;

	@Column (name="fr_Quantidade_5")
	private Double frQuantidade5;

	@Column (name="fr_Consumo_5")
	private Double frConsumo5;

	@Column (name="fr_Vazao_5")
	private Double frVazao5;


	@Column (name="fr_Vazao_Total")
	private Double frVazaoTotal;

	// JANEIRO //
	@Column (name="fr_Q_Dia_Jan")
	private Double frQDiaJan;

	@Column (name="fr_Q_Hora_Jan")
	private int frQHoraJan;

	@Column (name="fr_Tempo_Cap_Jan")
	private int frTempoCapJan;

	// FEVEREIRO
	@Column (name="fr_Q_Dia_Fev")
	private Double frQDiaFev;

	@Column (name="fr_Q_Hora_Fev")
	private int frQHoraFev;

	@Column (name="fr_Tempo_Cap_Fev")
	private int frTempoCapFev;

	// MARCO //
	@Column (name="fr_Q_Dia_Mar")
	private Double frQDiaMar;

	@Column (name="fr_Q_Hora_Mar")
	private int frQHoraMar;

	@Column (name="fr_Tempo_Cap_Mar")
	private int frTempoCapMar;


	// ABRIL //
	@Column (name="fr_Q_Dia_Abr")
	private Double frQDiaAbr;

	@Column (name="fr_Q_Hora_Abr")
	private int frQHoraAbr;

	@Column (name="fr_Tempo_Cap_Abr")
	private int frTempoCapAbr;

	// MAIO //

	@Column (name="fr_Q_Dia_Mai")
	private Double frQDiaMai;

	@Column (name="fr_Q_Hora_Mai")
	private int frQHoraMai;

	@Column (name="fr_Tempo_Cap_Mai")
	private int frTempoCapMai;

	// JUNHO //

	@Column (name="fr_Q_Dia_Jun")
	private Double frQDiaJun;

	@Column (name="fr_Q_Hora_Jun")
	private int frQHoraJun;

	@Column (name="fr_Tempo_Cap_Jun")
	private int frTempoCapJun;

	// JULHO //
	@Column (name="fr_Q_Dia_Jul")
	private Double frQDiaJul;

	@Column (name="fr_Q_Hora_Jul")
	private int frQHoraJul;

	@Column (name="fr_Tempo_Cap_Jul")
	private int frTempoCapJul;


	// AGOSTO //
	@Column (name="fr_Q_Dia_Ago")
	private Double frQDiaAgo;

	@Column (name="fr_Q_Hora_Ago")
	private int frQHoraAgo;

	@Column (name="fr_Tempo_Cap_Ago")
	private int frTempoCapAgo;


	// SETEMBRO //
	@Column (name="fr_Q_Dia_Set")
	private Double frQDiaSet;

	@Column (name="fr_Q_Hora_Set")
	private int frQHoraSet;

	@Column (name="fr_Tempo_Cap_Set")
	private int frTempoCapSet;

	// OUTUBRO //
	@Column (name="fr_Q_Dia_Out")
	private Double frQDiaOut;

	@Column (name="fr_Q_Hora_Out")
	private int frQHoraOut;

	@Column (name="fr_Tempo_Cap_Out")
	private int frTempoCapOut;


	// NOVEMBRO //
	@Column (name="fr_Q_Dia_Nov")
	private Double frQDiaNov;

	@Column (name="fr_Q_Hora_Nov")
	private int frQHoraNov;

	@Column (name="fr_Tempo_Cap_Nov")
	private int frTempoCapNov;

	// DEZEMBRO //
	@Column (name="fr_Q_Dia_Dez")
	private Double frQDiaDez;

	@Column (name="fr_Q_Hora_Dez")
	private int frQHoraDez;

	@Column (name="fr_Tempo_Cap_Dez")
	private int frTempoCapDez;
	
	public FinalidadeRequerida () {
		
	}
	
	
	

	public FinalidadeRequerida(String frFinalidade1, String frSubfinalidade1, Double frQuantidade1, Double frConsumo1,
			Double frVazao1, Double frVazaoTotal, Double frQDiaJan, int frQHoraJan, int frTempoCapJan, Double frQDiaFev,
			int frQHoraFev, int frTempoCapFev, Double frQDiaMar, int frQHoraMar, int frTempoCapMar, Double frQDiaAbr,
			int frQHoraAbr, int frTempoCapAbr, Double frQDiaMai, int frQHoraMai, int frTempoCapMai, Double frQDiaJun,
			int frQHoraJun, int frTempoCapJun, Double frQDiaJul, int frQHoraJul, int frTempoCapJul, Double frQDiaAgo,
			int frQHoraAgo, int frTempoCapAgo, Double frQDiaSet, int frQHoraSet, int frTempoCapSet, Double frQDiaOut,
			int frQHoraOut, int frTempoCapOut, Double frQDiaNov, int frQHoraNov, int frTempoCapNov, Double frQDiaDez,
			int frQHoraDez, int frTempoCapDez) {
		super();
		this.frFinalidade1 = frFinalidade1;
		this.frSubfinalidade1 = frSubfinalidade1;
		this.frQuantidade1 = frQuantidade1;
		this.frConsumo1 = frConsumo1;
		this.frVazao1 = frVazao1;
		this.frVazaoTotal = frVazaoTotal;
		this.frQDiaJan = frQDiaJan;
		this.frQHoraJan = frQHoraJan;
		this.frTempoCapJan = frTempoCapJan;
		this.frQDiaFev = frQDiaFev;
		this.frQHoraFev = frQHoraFev;
		this.frTempoCapFev = frTempoCapFev;
		this.frQDiaMar = frQDiaMar;
		this.frQHoraMar = frQHoraMar;
		this.frTempoCapMar = frTempoCapMar;
		this.frQDiaAbr = frQDiaAbr;
		this.frQHoraAbr = frQHoraAbr;
		this.frTempoCapAbr = frTempoCapAbr;
		this.frQDiaMai = frQDiaMai;
		this.frQHoraMai = frQHoraMai;
		this.frTempoCapMai = frTempoCapMai;
		this.frQDiaJun = frQDiaJun;
		this.frQHoraJun = frQHoraJun;
		this.frTempoCapJun = frTempoCapJun;
		this.frQDiaJul = frQDiaJul;
		this.frQHoraJul = frQHoraJul;
		this.frTempoCapJul = frTempoCapJul;
		this.frQDiaAgo = frQDiaAgo;
		this.frQHoraAgo = frQHoraAgo;
		this.frTempoCapAgo = frTempoCapAgo;
		this.frQDiaSet = frQDiaSet;
		this.frQHoraSet = frQHoraSet;
		this.frTempoCapSet = frTempoCapSet;
		this.frQDiaOut = frQDiaOut;
		this.frQHoraOut = frQHoraOut;
		this.frTempoCapOut = frTempoCapOut;
		this.frQDiaNov = frQDiaNov;
		this.frQHoraNov = frQHoraNov;
		this.frTempoCapNov = frTempoCapNov;
		this.frQDiaDez = frQDiaDez;
		this.frQHoraDez = frQHoraDez;
		this.frTempoCapDez = frTempoCapDez;
	}




	public String getFrFinalidade1() {
		return frFinalidade1;
	}

	public void setFrFinalidade1(String frFinalidade1) {
		this.frFinalidade1 = frFinalidade1;
	}

	public String getFrSubfinalidade1() {
		return frSubfinalidade1;
	}

	public void setFrSubfinalidade1(String frSubfinalidade1) {
		this.frSubfinalidade1 = frSubfinalidade1;
	}

	public Double getFrQuantidade1() {
		return frQuantidade1;
	}

	public void setFrQuantidade1(Double frQuantidade1) {
		this.frQuantidade1 = frQuantidade1;
	}

	public Double getFrConsumo1() {
		return frConsumo1;
	}

	public void setFrConsumo1(Double frConsumo1) {
		this.frConsumo1 = frConsumo1;
	}

	public Double getFrVazao1() {
		return frVazao1;
	}

	public void setFrVazao1(Double frVazao1) {
		this.frVazao1 = frVazao1;
	}

	public String getFrFinalidade2() {
		return frFinalidade2;
	}

	public void setFrFinalidade2(String frFinalidade2) {
		this.frFinalidade2 = frFinalidade2;
	}

	public String getFrSubfinalidade2() {
		return frSubfinalidade2;
	}

	public void setFrSubfinalidade2(String frSubfinalidade2) {
		this.frSubfinalidade2 = frSubfinalidade2;
	}

	public Double getFrQuantidade2() {
		return frQuantidade2;
	}

	public void setFrQuantidade2(Double frQuantidade2) {
		this.frQuantidade2 = frQuantidade2;
	}

	public Double getFrConsumo2() {
		return frConsumo2;
	}

	public void setFrConsumo2(Double frConsumo2) {
		this.frConsumo2 = frConsumo2;
	}

	public Double getFrVazao2() {
		return frVazao2;
	}

	public void setFrVazao2(Double frVazao2) {
		this.frVazao2 = frVazao2;
	}

	public String getFrFinalidade3() {
		return frFinalidade3;
	}

	public void setFrFinalidade3(String frFinalidade3) {
		this.frFinalidade3 = frFinalidade3;
	}

	public String getFrSubfinalidade3() {
		return frSubfinalidade3;
	}

	public void setFrSubfinalidade3(String frSubfinalidade3) {
		this.frSubfinalidade3 = frSubfinalidade3;
	}

	public Double getFrQuantidade3() {
		return frQuantidade3;
	}

	public void setFrQuantidade3(Double frQuantidade3) {
		this.frQuantidade3 = frQuantidade3;
	}

	public Double getFrConsumo3() {
		return frConsumo3;
	}

	public void setFrConsumo3(Double frConsumo3) {
		this.frConsumo3 = frConsumo3;
	}

	public Double getFrVazao3() {
		return frVazao3;
	}

	public void setFrVazao3(Double frVazao3) {
		this.frVazao3 = frVazao3;
	}

	public String getFrFinalidade4() {
		return frFinalidade4;
	}

	public void setFrFinalidade4(String frFinalidade4) {
		this.frFinalidade4 = frFinalidade4;
	}

	public String getFrSubfinalidade4() {
		return frSubfinalidade4;
	}

	public void setFrSubfinalidade4(String frSubfinalidade4) {
		this.frSubfinalidade4 = frSubfinalidade4;
	}

	public Double getFrQuantidade4() {
		return frQuantidade4;
	}

	public void setFrQuantidade4(Double frQuantidade4) {
		this.frQuantidade4 = frQuantidade4;
	}

	public Double getFrConsumo4() {
		return frConsumo4;
	}

	public void setFrConsumo4(Double frConsumo4) {
		this.frConsumo4 = frConsumo4;
	}

	public Double getFrVazao4() {
		return frVazao4;
	}

	public void setFrVazao4(Double frVazao4) {
		this.frVazao4 = frVazao4;
	}

	public String getFrFinalidade5() {
		return frFinalidade5;
	}

	public void setFrFinalidade5(String frFinalidade5) {
		this.frFinalidade5 = frFinalidade5;
	}

	public String getFrSubfinalidade5() {
		return frSubfinalidade5;
	}

	public void setFrSubfinalidade5(String frSubfinalidade5) {
		this.frSubfinalidade5 = frSubfinalidade5;
	}

	public Double getFrQuantidade5() {
		return frQuantidade5;
	}

	public void setFrQuantidade5(Double frQuantidade5) {
		this.frQuantidade5 = frQuantidade5;
	}

	public Double getFrConsumo5() {
		return frConsumo5;
	}

	public void setFrConsumo5(Double frConsumo5) {
		this.frConsumo5 = frConsumo5;
	}

	public Double getFrVazao5() {
		return frVazao5;
	}

	public void setFrVazao5(Double frVazao5) {
		this.frVazao5 = frVazao5;
	}

	public Double getFrVazaoTotal() {
		return frVazaoTotal;
	}

	public void setFrVazaoTotal(Double frVazaoTotal) {
		this.frVazaoTotal = frVazaoTotal;
	}

	public Double getFrQDiaJan() {
		return frQDiaJan;
	}

	public void setFrQDiaJan(Double frQDiaJan) {
		this.frQDiaJan = frQDiaJan;
	}

	public int getFrQHoraJan() {
		return frQHoraJan;
	}

	public void setFrQHoraJan(int frQHoraJan) {
		this.frQHoraJan = frQHoraJan;
	}

	public int getFrTempoCapJan() {
		return frTempoCapJan;
	}

	public void setFrTempoCapJan(int frTempoCapJan) {
		this.frTempoCapJan = frTempoCapJan;
	}

	public Double getFrQDiaFev() {
		return frQDiaFev;
	}

	public void setFrQDiaFev(Double frQDiaFev) {
		this.frQDiaFev = frQDiaFev;
	}

	public int getFrQHoraFev() {
		return frQHoraFev;
	}

	public void setFrQHoraFev(int frQHoraFev) {
		this.frQHoraFev = frQHoraFev;
	}

	public int getFrTempoCapFev() {
		return frTempoCapFev;
	}

	public void setFrTempoCapFev(int frTempoCapFev) {
		this.frTempoCapFev = frTempoCapFev;
	}

	public Double getFrQDiaMar() {
		return frQDiaMar;
	}

	public void setFrQDiaMar(Double frQDiaMar) {
		this.frQDiaMar = frQDiaMar;
	}

	public int getFrQHoraMar() {
		return frQHoraMar;
	}

	public void setFrQHoraMar(int frQHoraMar) {
		this.frQHoraMar = frQHoraMar;
	}

	public int getFrTempoCapMar() {
		return frTempoCapMar;
	}

	public void setFrTempoCapMar(int frTempoCapMar) {
		this.frTempoCapMar = frTempoCapMar;
	}

	public Double getFrQDiaAbr() {
		return frQDiaAbr;
	}

	public void setFrQDiaAbr(Double frQDiaAbr) {
		this.frQDiaAbr = frQDiaAbr;
	}

	public int getFrQHoraAbr() {
		return frQHoraAbr;
	}

	public void setFrQHoraAbr(int frQHoraAbr) {
		this.frQHoraAbr = frQHoraAbr;
	}

	public int getFrTempoCapAbr() {
		return frTempoCapAbr;
	}

	public void setFrTempoCapAbr(int frTempoCapAbr) {
		this.frTempoCapAbr = frTempoCapAbr;
	}

	public Double getFrQDiaMai() {
		return frQDiaMai;
	}

	public void setFrQDiaMai(Double frQDiaMai) {
		this.frQDiaMai = frQDiaMai;
	}

	public int getFrQHoraMai() {
		return frQHoraMai;
	}

	public void setFrQHoraMai(int frQHoraMai) {
		this.frQHoraMai = frQHoraMai;
	}

	public int getFrTempoCapMai() {
		return frTempoCapMai;
	}

	public void setFrTempoCapMai(int frTempoCapMai) {
		this.frTempoCapMai = frTempoCapMai;
	}

	public Double getFrQDiaJun() {
		return frQDiaJun;
	}

	public void setFrQDiaJun(Double frQDiaJun) {
		this.frQDiaJun = frQDiaJun;
	}

	public int getFrQHoraJun() {
		return frQHoraJun;
	}

	public void setFrQHoraJun(int frQHoraJun) {
		this.frQHoraJun = frQHoraJun;
	}

	public int getFrTempoCapJun() {
		return frTempoCapJun;
	}

	public void setFrTempoCapJun(int frTempoCapJun) {
		this.frTempoCapJun = frTempoCapJun;
	}

	public Double getFrQDiaJul() {
		return frQDiaJul;
	}

	public void setFrQDiaJul(Double frQDiaJul) {
		this.frQDiaJul = frQDiaJul;
	}

	public int getFrQHoraJul() {
		return frQHoraJul;
	}

	public void setFrQHoraJul(int frQHoraJul) {
		this.frQHoraJul = frQHoraJul;
	}

	public int getFrTempoCapJul() {
		return frTempoCapJul;
	}

	public void setFrTempoCapJul(int frTempoCapJul) {
		this.frTempoCapJul = frTempoCapJul;
	}

	public Double getFrQDiaAgo() {
		return frQDiaAgo;
	}

	public void setFrQDiaAgo(Double frQDiaAgo) {
		this.frQDiaAgo = frQDiaAgo;
	}

	public int getFrQHoraAgo() {
		return frQHoraAgo;
	}

	public void setFrQHoraAgo(int frQHoraAgo) {
		this.frQHoraAgo = frQHoraAgo;
	}

	public int getFrTempoCapAgo() {
		return frTempoCapAgo;
	}

	public void setFrTempoCapAgo(int frTempoCapAgo) {
		this.frTempoCapAgo = frTempoCapAgo;
	}

	public Double getFrQDiaSet() {
		return frQDiaSet;
	}

	public void setFrQDiaSet(Double frQDiaSet) {
		this.frQDiaSet = frQDiaSet;
	}

	public int getFrQHoraSet() {
		return frQHoraSet;
	}

	public void setFrQHoraSet(int frQHoraSet) {
		this.frQHoraSet = frQHoraSet;
	}

	public int getFrTempoCapSet() {
		return frTempoCapSet;
	}

	public void setFrTempoCapSet(int frTempoCapSet) {
		this.frTempoCapSet = frTempoCapSet;
	}

	public Double getFrQDiaOut() {
		return frQDiaOut;
	}

	public void setFrQDiaOut(Double frQDiaOut) {
		this.frQDiaOut = frQDiaOut;
	}

	public int getFrQHoraOut() {
		return frQHoraOut;
	}

	public void setFrQHoraOut(int frQHoraOut) {
		this.frQHoraOut = frQHoraOut;
	}

	public int getFrTempoCapOut() {
		return frTempoCapOut;
	}

	public void setFrTempoCapOut(int frTempoCapOut) {
		this.frTempoCapOut = frTempoCapOut;
	}

	public Double getFrQDiaNov() {
		return frQDiaNov;
	}

	public void setFrQDiaNov(Double frQDiaNov) {
		this.frQDiaNov = frQDiaNov;
	}

	public int getFrQHoraNov() {
		return frQHoraNov;
	}

	public void setFrQHoraNov(int frQHoraNov) {
		this.frQHoraNov = frQHoraNov;
	}

	public int getFrTempoCapNov() {
		return frTempoCapNov;
	}

	public void setFrTempoCapNov(int frTempoCapNov) {
		this.frTempoCapNov = frTempoCapNov;
	}

	public Double getFrQDiaDez() {
		return frQDiaDez;
	}

	public void setFrQDiaDez(Double frQDiaDez) {
		this.frQDiaDez = frQDiaDez;
	}

	public int getFrQHoraDez() {
		return frQHoraDez;
	}

	public void setFrQHoraDez(int frQHoraDez) {
		this.frQHoraDez = frQHoraDez;
	}

	public int getFrTempoCapDez() {
		return frTempoCapDez;
	}

	public void setFrTempoCapDez(int frTempoCapDez) {
		this.frTempoCapDez = frTempoCapDez;
	}

}
