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
@PrimaryKeyJoinColumn(name="sub_Interferencia_FK")
public class Subterranea extends Interferencia {

	private static final long serialVersionUID = 7955498803306632784L;

	@Column (name="sub_Caesb", columnDefinition="varchar(3)")
	private String subCaesb;  // tem caesb () sim () não

	@Column (name="sub_Estatico", columnDefinition="varchar(20)")
	private String subEstatico;  // em metros

	@Column (name="sub_Dinamico", columnDefinition="varchar(20)")
	private String subDinamico;  // em metros

	@Column (name="sub_Vazao_Teste",columnDefinition="varchar(20)")
	private String subVazaoTeste;  
	
	@Column (name="sub_Vazao_Subsistema",columnDefinition="varchar(20)")
	private String subVazaoSubsistema;
	
	@Column (name="sub_Vazao_Outorgada")
	private Double subVazaoOutorgada;  // em l/h - litros por hora
	
	@Column (name="sub_Profundidade",columnDefinition="varchar(20)")
	private String subProfundidade;  // em metros
	
	@Column (name="sub_Cod_plan",columnDefinition="varchar(20)")
	private String subCod_plan;  // em metros
	
	@Basic
	@Column (name="sub_Data_Operacao")
	private java.sql.Date subDataOperacao;

	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "sub_Tipo_Poco_FK")
	private TipoPoco subTipoPocoFK; // Manual Tubular

	@ManyToOne (fetch = FetchType.EAGER) 
	@JoinColumn (name = "sub_Subsistema_FK")
	private SubSistema subSubSistemaFK;

	public Subterranea () {

	}
	
	public Subterranea(String subCaesb, String subEstatico, String subDinamico, String subVazaoTeste,
			String subVazaoSubsistema, Double subVazaoOutorgada, String subProfundidade, String subCod_plan,
			Date subDataOperacao, TipoPoco subTipoPocoFK, SubSistema subSubSistemaFK) {
		super();
		this.subCaesb = subCaesb;
		this.subEstatico = subEstatico;
		this.subDinamico = subDinamico;
		this.subVazaoTeste = subVazaoTeste;
		this.subVazaoSubsistema = subVazaoSubsistema;
		this.subVazaoOutorgada = subVazaoOutorgada;
		this.subProfundidade = subProfundidade;
		this.subCod_plan = subCod_plan;
		this.subDataOperacao = subDataOperacao;
		this.subTipoPocoFK = subTipoPocoFK;
		this.subSubSistemaFK = subSubSistemaFK;
	}



	//-- getters and setters --//
	public SubSistema getSubSubSistemaFK() {
		return subSubSistemaFK;
	}

	public java.sql.Date getSubDataOperacao() {
		return subDataOperacao;
	}


	public void setSubDataOperacao(java.sql.Date subDataOperacao) {
		this.subDataOperacao = subDataOperacao;
	}


	public void setSubSubSistemaFK(SubSistema subSubSistemaFK) {
		this.subSubSistemaFK = subSubSistemaFK;
	}

	public String getSubCaesb() {
		return subCaesb;
	}

	public void setSubCaesb(String subCaesb) {
		this.subCaesb = subCaesb;
	}

	public String getSubEstatico() {
		return subEstatico;
	}

	public void setSubEstatico(String subEstatico) {
		this.subEstatico = subEstatico;
	}

	public String getSubDinamico() {
		return subDinamico;
	}

	public void setSubDinamico(String subDinamico) {
		this.subDinamico = subDinamico;
	}

	
	public String getSubProfundidade() {
		return subProfundidade;
	}

	public void setSubProfundidade(String subProfundidade) {
		this.subProfundidade = subProfundidade;
	}

	public TipoPoco getSubTipoPocoFK() {
		return subTipoPocoFK;
	}

	public void setSubTipoPocoFK(TipoPoco subTipoPocoFK) {
		this.subTipoPocoFK = subTipoPocoFK;
	}

	public String getSubVazaoTeste() {
		return subVazaoTeste;
	}

	public void setSubVazaoTeste(String subVazaoTeste) {
		this.subVazaoTeste = subVazaoTeste;
	}

	public String getSubVazaoSubsistema() {
		return subVazaoSubsistema;
	}

	public void setSubVazaoSubsistema(String subVazaoSubsistema) {
		this.subVazaoSubsistema = subVazaoSubsistema;
	}

	public Double getSubVazaoOutorgada() {
		return subVazaoOutorgada;
	}

	public void setSubVazaoOutorgada(Double subVazaoOutorgada) {
		this.subVazaoOutorgada = subVazaoOutorgada;
	}

	public String getSubCod_plan() {
		return subCod_plan;
	}

	public void setSubCod_plan(String subCod_plan) {
		this.subCod_plan = subCod_plan;
	}

	@Override
	public String toString() {
		return "Subterranea [subCaesb=" + subCaesb + ", subEstatico=" + subEstatico + ", subDinamico=" + subDinamico
				+ ", subVazaoTeste=" + subVazaoTeste + ", subVazaoSubsistema=" + subVazaoSubsistema
				+ ", subVazaoOutorgada=" + subVazaoOutorgada + ", subProfundidade=" + subProfundidade + ", subCod_plan="
				+ subCod_plan + ", subDataOperacao=" + subDataOperacao + ", subTipoPocoFK={" + subTipoPocoFK.getTipoPocoID()
				+ subTipoPocoFK.getTipoPocoDescricao() + "}, subSubSistemaFK={" + subSubSistemaFK.getSubID() + "," +  subSubSistemaFK.getSubDescricao() + "}]";
	}

	
	
	
}
