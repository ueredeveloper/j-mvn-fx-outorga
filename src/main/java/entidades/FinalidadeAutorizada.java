package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name = "Finalidade_Autorizada")
@PrimaryKeyJoinColumn(name="fa_Finalidade_FK")
public class FinalidadeAutorizada extends Finalidade {

	// 1 //
	@Column (name="fa_Finalidade_1",columnDefinition="varchar(500)")
	private String faFinalidade1;

	@Column (name="fa_Subfinalidade_1",columnDefinition="varchar(500)")
	private String faSubfinalidade1;

	@Column (name="fa_Quantidade_1")
	private Double faQuantidade1;

	@Column (name="fa_Consumo_1")
	private Double faConsumo1;

	@Column (name="fa_Vazao_1")
	private Double faVazao1;

	// 2 //
	@Column (name="fa_Finalidade_2",columnDefinition="varchar(500)")
	private String faFinalidade2;

	@Column (name="fa_Subfinalidade_2",columnDefinition="varchar(500)")
	private String faSubfinalidade2;

	@Column (name="fa_Quantidade_2")
	private Double faQuantidade2;

	@Column (name="fa_Consumo_2")
	private Double faConsumo2;

	@Column (name="fa_Vazao_2")
	private Double faVazao2;

	// 3 //
	@Column (name="fa_Finalidade_3",columnDefinition="varchar(500)")
	private String faFinalidade3;

	@Column (name="fa_Subfinalidade_3",columnDefinition="varchar(500)")
	private String faSubfinalidade3;

	@Column (name="fa_Quantidade_3")
	private Double faQuantidade3;

	@Column (name="fa_Consumo_3")
	private Double faConsumo3;

	@Column (name="fa_Vazao_3")
	private Double faVazao3;

	// 4 //
	@Column (name="fa_Finalidade_4",columnDefinition="varchar(500)")
	private String faFinalidade4;

	@Column (name="fa_Subfinalidade_4",columnDefinition="varchar(500)")
	private String faSubfinalidade4;

	@Column (name="fa_Quantidade_4")
	private Double faQuantidade4;

	@Column (name="fa_Consumo_4")
	private Double faConsumo4;

	@Column (name="fa_Vazao_4")
	private Double faVazao4;

	// 5 //
	@Column (name="fa_Finalidade_5",columnDefinition="varchar(500)")
	private String faFinalidade5;

	@Column (name="fa_Subfinalidade_5",columnDefinition="varchar(500)")
	private String faSubfinalidade5;

	@Column (name="fa_Quantidade_5")
	private Double faQuantidade5;

	@Column (name="fa_Consumo_5")
	private Double faConsumo5;

	@Column (name="fa_Vazao_5")
	private Double faVazao5;


	@Column (name="fa_Vazao_Total")
	private Double faVazaoTotal;

	// JANEIRO //
	@Column (name="fa_Q_Dia_Jan")
	private Double faQDiaJan;

	@Column (name="fa_Q_Hora_Jan")
	private int faQHoraJan;

	@Column (name="fa_Tempo_Cap_Jan")
	private int faTempoCapJan;

	// FEVEREIRO
	@Column (name="fa_Q_Dia_Fev")
	private Double faQDiaFev;

	@Column (name="fa_Q_Hora_Fev")
	private int faQHoraFev;

	@Column (name="fa_Tempo_Cap_Fev")
	private int faTempoCapFev;

	// MARCO //
	@Column (name="fa_Q_Dia_Mar")
	private Double faQDiaMar;

	@Column (name="fa_Q_Hora_Mar")
	private int faQHoraMar;

	@Column (name="fa_Tempo_Cap_Mar")
	private int faTempoCapMar;

	// ABRIL //
	@Column (name="fa_Q_Dia_Abr")
	private Double faQDiaAbr;

	@Column (name="fa_Q_Hora_Abr")
	private int faQHoraAbr;

	@Column (name="fa_Tempo_Cap_Abr")
	private int faTempoCapAbr;

	// MAIO //
	@Column (name="fa_Q_Dia_Mai")
	private Double faQDiaMai;

	@Column (name="fa_Q_Hora_Mai")
	private int faQHoraMai;

	@Column (name="fa_Tempo_Cap_Mai")
	private int faTempoCapMai;

	// JUNHO //
	@Column (name="fa_Q_Dia_Jun")
	private Double faQDiaJun;

	@Column (name="fa_Q_Hora_Jun")
	private int faQHoraJun;

	@Column (name="fa_Tempo_Cap_Jun")
	private int faTempoCapJun;

	// JULHO //
	@Column (name="fa_Q_Dia_Jul")
	private Double faQDiaJul;

	@Column (name="fa_Q_Hora_Jul")
	private int faQHoraJul;

	@Column (name="fa_Tempo_Cap_Jul")
	private int faTempoCapJul;

	// AGOSTO //
	@Column (name="fa_Q_Dia_Ago")
	private Double faQDiaAgo;

	@Column (name="fa_Q_Hora_Ago")
	private int faQHoraAgo;

	@Column (name="fa_Tempo_Cap_Ago")
	private int faTempoCapAgo;

	// SETEMBRO //
	@Column (name="fa_Q_Dia_Set")
	private Double faQDiaSet;

	@Column (name="fa_Q_Hora_Set")
	private int faQHoraSet;

	@Column (name="fa_Tempo_Cap_Set")
	private int faTempoCapSet;

	// OUTUBRO //
	@Column (name="fa_Q_Dia_Out")
	private Double faQDiaOut;

	@Column (name="fa_Q_Hora_Out")
	private int faQHoraOut;

	@Column (name="fa_Tempo_Cap_Out")
	private int faTempoCapOut;

	// NOVEMBRO //
	@Column (name="fa_Q_Dia_Nov")
	private Double faQDiaNov;

	@Column (name="fa_Q_Hora_Nov")
	private int faQHoraNov;

	@Column (name="fa_Tempo_Cap_Nov")
	private int faTempoCapNov;

	// DEZEMBRO //
	@Column (name="fa_Q_Dia_Dez")
	private Double faQDiaDez;

	@Column (name="fa_Q_Hora_Dez")
	private int faQHoraDez;

	@Column (name="fa_Tempo_Cap_Dez")
	private int faTempoCapDez;

	public String getFaFinalidade1() {
		return faFinalidade1;
	}

	public void setFaFinalidade1(String faFinalidade1) {
		this.faFinalidade1 = faFinalidade1;
	}

	public String getFaSubfinalidade1() {
		return faSubfinalidade1;
	}

	public void setFaSubfinalidade1(String faSubfinalidade1) {
		this.faSubfinalidade1 = faSubfinalidade1;
	}

	public Double getFaQuantidade1() {
		return faQuantidade1;
	}

	public void setFaQuantidade1(Double faQuantidade1) {
		this.faQuantidade1 = faQuantidade1;
	}

	public Double getFaConsumo1() {
		return faConsumo1;
	}

	public void setFaConsumo1(Double faConsumo1) {
		this.faConsumo1 = faConsumo1;
	}

	public Double getFaVazao1() {
		return faVazao1;
	}

	public void setFaVazao1(Double faVazao1) {
		this.faVazao1 = faVazao1;
	}

	public String getFaFinalidade2() {
		return faFinalidade2;
	}

	public void setFaFinalidade2(String faFinalidade2) {
		this.faFinalidade2 = faFinalidade2;
	}

	public String getFaSubfinalidade2() {
		return faSubfinalidade2;
	}

	public void setFaSubfinalidade2(String faSubfinalidade2) {
		this.faSubfinalidade2 = faSubfinalidade2;
	}

	public Double getFaQuantidade2() {
		return faQuantidade2;
	}

	public void setFaQuantidade2(Double faQuantidade2) {
		this.faQuantidade2 = faQuantidade2;
	}

	public Double getFaConsumo2() {
		return faConsumo2;
	}

	public void setFaConsumo2(Double faConsumo2) {
		this.faConsumo2 = faConsumo2;
	}

	public Double getFaVazao2() {
		return faVazao2;
	}

	public void setFaVazao2(Double faVazao2) {
		this.faVazao2 = faVazao2;
	}

	public String getFaFinalidade3() {
		return faFinalidade3;
	}

	public void setFaFinalidade3(String faFinalidade3) {
		this.faFinalidade3 = faFinalidade3;
	}

	public String getFaSubfinalidade3() {
		return faSubfinalidade3;
	}

	public void setFaSubfinalidade3(String faSubfinalidade3) {
		this.faSubfinalidade3 = faSubfinalidade3;
	}

	public Double getFaQuantidade3() {
		return faQuantidade3;
	}

	public void setFaQuantidade3(Double faQuantidade3) {
		this.faQuantidade3 = faQuantidade3;
	}

	public Double getFaConsumo3() {
		return faConsumo3;
	}

	public void setFaConsumo3(Double faConsumo3) {
		this.faConsumo3 = faConsumo3;
	}

	public Double getFaVazao3() {
		return faVazao3;
	}

	public void setFaVazao3(Double faVazao3) {
		this.faVazao3 = faVazao3;
	}

	public String getFaFinalidade4() {
		return faFinalidade4;
	}

	public void setFaFinalidade4(String faFinalidade4) {
		this.faFinalidade4 = faFinalidade4;
	}

	public String getFaSubfinalidade4() {
		return faSubfinalidade4;
	}

	public void setFaSubfinalidade4(String faSubfinalidade4) {
		this.faSubfinalidade4 = faSubfinalidade4;
	}

	public Double getFaQuantidade4() {
		return faQuantidade4;
	}

	public void setFaQuantidade4(Double faQuantidade4) {
		this.faQuantidade4 = faQuantidade4;
	}

	public Double getFaConsumo4() {
		return faConsumo4;
	}

	public void setFaConsumo4(Double faConsumo4) {
		this.faConsumo4 = faConsumo4;
	}

	public Double getFaVazao4() {
		return faVazao4;
	}

	public void setFaVazao4(Double faVazao4) {
		this.faVazao4 = faVazao4;
	}

	public String getFaFinalidade5() {
		return faFinalidade5;
	}

	public void setFaFinalidade5(String faFinalidade5) {
		this.faFinalidade5 = faFinalidade5;
	}

	public String getFaSubfinalidade5() {
		return faSubfinalidade5;
	}

	public void setFaSubfinalidade5(String faSubfinalidade5) {
		this.faSubfinalidade5 = faSubfinalidade5;
	}

	public Double getFaQuantidade5() {
		return faQuantidade5;
	}

	public void setFaQuantidade5(Double faQuantidade5) {
		this.faQuantidade5 = faQuantidade5;
	}

	public Double getFaConsumo5() {
		return faConsumo5;
	}

	public void setFaConsumo5(Double faConsumo5) {
		this.faConsumo5 = faConsumo5;
	}

	public Double getFaVazao5() {
		return faVazao5;
	}

	public void setFaVazao5(Double faVazao5) {
		this.faVazao5 = faVazao5;
	}

	public Double getFaVazaoTotal() {
		return faVazaoTotal;
	}

	public void setFaVazaoTotal(Double faVazaoTotal) {
		this.faVazaoTotal = faVazaoTotal;
	}

	public Double getFaQDiaJan() {
		return faQDiaJan;
	}

	public void setFaQDiaJan(Double faQDiaJan) {
		this.faQDiaJan = faQDiaJan;
	}

	public int getFaQHoraJan() {
		return faQHoraJan;
	}

	public void setFaQHoraJan(int faQHoraJan) {
		this.faQHoraJan = faQHoraJan;
	}

	public int getFaTempoCapJan() {
		return faTempoCapJan;
	}

	public void setFaTempoCapJan(int faTempoCapJan) {
		this.faTempoCapJan = faTempoCapJan;
	}

	public Double getFaQDiaFev() {
		return faQDiaFev;
	}

	public void setFaQDiaFev(Double faQDiaFev) {
		this.faQDiaFev = faQDiaFev;
	}

	public int getFaQHoraFev() {
		return faQHoraFev;
	}

	public void setFaQHoraFev(int faQHoraFev) {
		this.faQHoraFev = faQHoraFev;
	}

	public int getFaTempoCapFev() {
		return faTempoCapFev;
	}

	public void setFaTempoCapFev(int faTempoCapFev) {
		this.faTempoCapFev = faTempoCapFev;
	}

	public Double getFaQDiaMar() {
		return faQDiaMar;
	}

	public void setFaQDiaMar(Double faQDiaMar) {
		this.faQDiaMar = faQDiaMar;
	}

	public int getFaQHoraMar() {
		return faQHoraMar;
	}

	public void setFaQHoraMar(int faQHoraMar) {
		this.faQHoraMar = faQHoraMar;
	}

	public int getFaTempoCapMar() {
		return faTempoCapMar;
	}

	public void setFaTempoCapMar(int faTempoCapMar) {
		this.faTempoCapMar = faTempoCapMar;
	}

	public Double getFaQDiaAbr() {
		return faQDiaAbr;
	}

	public void setFaQDiaAbr(Double faQDiaAbr) {
		this.faQDiaAbr = faQDiaAbr;
	}

	public int getFaQHoraAbr() {
		return faQHoraAbr;
	}

	public void setFaQHoraAbr(int faQHoraAbr) {
		this.faQHoraAbr = faQHoraAbr;
	}

	public int getFaTempoCapAbr() {
		return faTempoCapAbr;
	}

	public void setFaTempoCapAbr(int faTempoCapAbr) {
		this.faTempoCapAbr = faTempoCapAbr;
	}

	public Double getFaQDiaMai() {
		return faQDiaMai;
	}

	public void setFaQDiaMai(Double faQDiaMai) {
		this.faQDiaMai = faQDiaMai;
	}

	public int getFaQHoraMai() {
		return faQHoraMai;
	}

	public void setFaQHoraMai(int faQHoraMai) {
		this.faQHoraMai = faQHoraMai;
	}

	public int getFaTempoCapMai() {
		return faTempoCapMai;
	}

	public void setFaTempoCapMai(int faTempoCapMai) {
		this.faTempoCapMai = faTempoCapMai;
	}

	public Double getFaQDiaJun() {
		return faQDiaJun;
	}

	public void setFaQDiaJun(Double faQDiaJun) {
		this.faQDiaJun = faQDiaJun;
	}

	public int getFaQHoraJun() {
		return faQHoraJun;
	}

	public void setFaQHoraJun(int faQHoraJun) {
		this.faQHoraJun = faQHoraJun;
	}

	public int getFaTempoCapJun() {
		return faTempoCapJun;
	}

	public void setFaTempoCapJun(int faTempoCapJun) {
		this.faTempoCapJun = faTempoCapJun;
	}

	public Double getFaQDiaJul() {
		return faQDiaJul;
	}

	public void setFaQDiaJul(Double faQDiaJul) {
		this.faQDiaJul = faQDiaJul;
	}

	public int getFaQHoraJul() {
		return faQHoraJul;
	}

	public void setFaQHoraJul(int faQHoraJul) {
		this.faQHoraJul = faQHoraJul;
	}

	public int getFaTempoCapJul() {
		return faTempoCapJul;
	}

	public void setFaTempoCapJul(int faTempoCapJul) {
		this.faTempoCapJul = faTempoCapJul;
	}

	public Double getFaQDiaAgo() {
		return faQDiaAgo;
	}

	public void setFaQDiaAgo(Double faQDiaAgo) {
		this.faQDiaAgo = faQDiaAgo;
	}

	public int getFaQHoraAgo() {
		return faQHoraAgo;
	}

	public void setFaQHoraAgo(int faQHoraAgo) {
		this.faQHoraAgo = faQHoraAgo;
	}

	public int getFaTempoCapAgo() {
		return faTempoCapAgo;
	}

	public void setFaTempoCapAgo(int faTempoCapAgo) {
		this.faTempoCapAgo = faTempoCapAgo;
	}

	public Double getFaQDiaSet() {
		return faQDiaSet;
	}

	public void setFaQDiaSet(Double faQDiaSet) {
		this.faQDiaSet = faQDiaSet;
	}

	public int getFaQHoraSet() {
		return faQHoraSet;
	}

	public void setFaQHoraSet(int faQHoraSet) {
		this.faQHoraSet = faQHoraSet;
	}

	public int getFaTempoCapSet() {
		return faTempoCapSet;
	}

	public void setFaTempoCapSet(int faTempoCapSet) {
		this.faTempoCapSet = faTempoCapSet;
	}

	public Double getFaQDiaOut() {
		return faQDiaOut;
	}

	public void setFaQDiaOut(Double faQDiaOut) {
		this.faQDiaOut = faQDiaOut;
	}

	public int getFaQHoraOut() {
		return faQHoraOut;
	}

	public void setFaQHoraOut(int faQHoraOut) {
		this.faQHoraOut = faQHoraOut;
	}

	public int getFaTempoCapOut() {
		return faTempoCapOut;
	}

	public void setFaTempoCapOut(int faTempoCapOut) {
		this.faTempoCapOut = faTempoCapOut;
	}

	public Double getFaQDiaNov() {
		return faQDiaNov;
	}

	public void setFaQDiaNov(Double faQDiaNov) {
		this.faQDiaNov = faQDiaNov;
	}

	public int getFaQHoraNov() {
		return faQHoraNov;
	}

	public void setFaQHoraNov(int faQHoraNov) {
		this.faQHoraNov = faQHoraNov;
	}

	public int getFaTempoCapNov() {
		return faTempoCapNov;
	}

	public void setFaTempoCapNov(int faTempoCapNov) {
		this.faTempoCapNov = faTempoCapNov;
	}

	public Double getFaQDiaDez() {
		return faQDiaDez;
	}

	public void setFaQDiaDez(Double faQDiaDez) {
		this.faQDiaDez = faQDiaDez;
	}

	public int getFaQHoraDez() {
		return faQHoraDez;
	}

	public void setFaQHoraDez(int faQHoraDez) {
		this.faQHoraDez = faQHoraDez;
	}

	public int getFaTempoCapDez() {
		return faTempoCapDez;
	}

	public void setFaTempoCapDez(int faTempoCapDez) {
		this.faTempoCapDez = faTempoCapDez;
	}
	
	
	

}
