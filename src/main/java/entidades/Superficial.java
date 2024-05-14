package entidades;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="sup_Interferencia_FK")
public class Superficial extends Interferencia {

	private static final long serialVersionUID = 8310916470642159612L;

	@Column (name="sup_Marca_Bomba", columnDefinition="varchar(30)")
	private String supMarcaBomba; // marca

	@Column (name="sup_Potencia_Bomba", columnDefinition="varchar(10)")
	private String supPotenciaBomba; // em cv - cavalos

	@Column (name="sup_Area_Irrigada", columnDefinition="varchar(10)")
	private String supAreaIrrigada;

	@Column (name="sup_Area_Contribuicao", columnDefinition="varchar(10)")
	private String supAreaContribuicao;

	@Column (name="sup_Area_Propriedade", columnDefinition="varchar(10)")
	private String supAreaPropriedade;

	@Basic
	@Column (name="sup_Data_Operacao")
	private java.sql.Date supDataOperacao;

	@Column (name="sup_Caesb", columnDefinition="varchar(3)")
	private String supCaesb;  // tem caesb () sim () não

	@Column (name="sup_Barramento", columnDefinition="varchar(3)")
	private String supBarramento; 

	@Column (name="sup_Corpo_Hidrico", columnDefinition="varchar (50)")
	private String supCorpoHidrico;

	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "sup_Forma_Captacao_FK")
	private FormaCaptacao supFormaCaptacaoFK; // Bombeamento Gravidade

	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "sup_Local_Captacao_FK")
	private LocalCaptacao supLocalCaptacaoFK; //-- () canal () rio () reservatório () lago natural () nascente

	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "sup_Metodo_Irrigacao_FK")
	private MetodoIrrigacao supMetodoIrrigacaoFK; //-- () canal () rio () reservatório () lago natural () nascente

	public Superficial (){
	}
	
	

	public Superficial(String supMarcaBomba, String supPotenciaBomba, String supAreaIrrigada,
			String supAreaContribuicao, String supAreaPropriedade, Date supDataOperacao, String supCaesb,
			String supBarramento, String supCorpoHidrico, FormaCaptacao supFormaCaptacaoFK,
			LocalCaptacao supLocalCaptacaoFK, MetodoIrrigacao supMetodoIrrigacaoFK) {
		super();
		this.supMarcaBomba = supMarcaBomba;
		this.supPotenciaBomba = supPotenciaBomba;
		this.supAreaIrrigada = supAreaIrrigada;
		this.supAreaContribuicao = supAreaContribuicao;
		this.supAreaPropriedade = supAreaPropriedade;
		this.supDataOperacao = supDataOperacao;
		this.supCaesb = supCaesb;
		this.supBarramento = supBarramento;
		this.supCorpoHidrico = supCorpoHidrico;
		this.supFormaCaptacaoFK = supFormaCaptacaoFK;
		this.supLocalCaptacaoFK = supLocalCaptacaoFK;
		this.supMetodoIrrigacaoFK = supMetodoIrrigacaoFK;
	}



	//-- getters and setters --//
	public java.sql.Date getSupDataOperacao() {
		return supDataOperacao;
	}

	public void setSupDataOperacao(java.sql.Date supDataOperacao) {
		this.supDataOperacao = supDataOperacao;
	}

	public String getSupCaesb() {
		return supCaesb;
	}
	
	public void setSupCaesb(String supCaesb) {
		this.supCaesb = supCaesb;
	}

	public FormaCaptacao getSupFormaCaptacaoFK() {
		return supFormaCaptacaoFK;
	}

	public void setSupFormaCaptacaoFK(FormaCaptacao supFormaCaptacaoFK) {
		this.supFormaCaptacaoFK = supFormaCaptacaoFK;
	}

	public LocalCaptacao getSupLocalCaptacaoFK() {
		return supLocalCaptacaoFK;
	}

	public void setSupLocalCaptacaoFK(LocalCaptacao supLocalCaptacaoFK) {
		this.supLocalCaptacaoFK = supLocalCaptacaoFK;
	}

	public String getSupMarcaBomba() {
		return supMarcaBomba;
	}

	public void setSupMarcaBomba(String supMarcaBomba) {
		this.supMarcaBomba = supMarcaBomba;
	}

	public String getSupPotenciaBomba() {
		return supPotenciaBomba;
	}

	public void setSupPotenciaBomba(String supPotenciaBomba) {
		this.supPotenciaBomba = supPotenciaBomba;
	}

	public String getSupAreaIrrigada() {
		return supAreaIrrigada;
	}

	public void setSupAreaIrrigada(String supAreaIrrigada) {
		this.supAreaIrrigada = supAreaIrrigada;
	}

	public String getSupAreaContribuicao() {
		return supAreaContribuicao;
	}

	public void setSupAreaContribuicao(String supAreaContribuicao) {
		this.supAreaContribuicao = supAreaContribuicao;
	}

	public String getSupAreaPropriedade() {
		return supAreaPropriedade;
	}

	public void setSupAreaPropriedade(String supAreaPropriedade) {
		this.supAreaPropriedade = supAreaPropriedade;
	}

	public String getSupBarramento() {
		return supBarramento;
	}

	public void setSupBarramento(String supBarramento) {
		this.supBarramento = supBarramento;
	}

	public MetodoIrrigacao getSupMetodoIrrigacaoFK() {
		return supMetodoIrrigacaoFK;
	}

	public void setSupMetodoIrrigacaoFK(MetodoIrrigacao supMetodoIrrigacaoFK) {
		this.supMetodoIrrigacaoFK = supMetodoIrrigacaoFK;
	}

	public String getSupCorpoHidrico() {
		return supCorpoHidrico;
	}

	public void setSupCorpoHidrico(String supCorpoHidrico) {
		this.supCorpoHidrico = supCorpoHidrico;
	}

}
