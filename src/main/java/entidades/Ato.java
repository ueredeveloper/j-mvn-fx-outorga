package entidades;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ato implements Serializable {
	
	
	private static final long serialVersionUID = -804426930461307857L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ato_ID")
	private int atoID;
	
		//-- vistoria --//
		@ManyToOne (fetch = FetchType.LAZY) 
		@JoinColumn (name = "ato_Vistoria_FK")
		private Vistoria atoVistoriaFK;
	
	@Column (name="ato_Tipo", columnDefinition="varchar(40)")  // auto de infração de advertência, relatório de vistoria
	private String atoTipo;
	
	@Column (name="ato_Identificacao", columnDefinition="varchar(20)") // 15/2018
	private String atoIdentificacao;
	
	@Column (name="ato_SEI", columnDefinition="varchar(20)") // 3561241
	private String atoSEI;
	
	@Column (name="ato_Caracaterizacao", columnDefinition="varchar(3000)") // texto
	private String atoCaracterizacao;
	
	@Column (name="ato_Recomendacao", columnDefinition="varchar(3000)") // texto
	private String atoRecomendacao;
	
	@Basic
	@Column (name="ato_Data_Fiscalizacao")
	private java.sql.Date atoDataFiscalizacao;
	
	@Basic
	@Column (name="ato_Data_Criacao")
	private java.sql.Date atoDataCriacao;
	
	@Basic
	@Column (name="ato_Atualizacao")
	private java.sql.Timestamp atoAtualizacao;
	
	// construtor padrão //

	public Ato () {
		
	}

	public int getAtoID() {
		return atoID;
	}

	public void setAtoID(int atoID) {
		this.atoID = atoID;
	}

	public Vistoria getAtoVistoriaFK() {
		return atoVistoriaFK;
	}

	public void setAtoVistoriaFK(Vistoria atoVistoriaFK) {
		this.atoVistoriaFK = atoVistoriaFK;
	}

	public String getAtoTipo() {
		return atoTipo;
	}

	public void setAtoTipo(String atoTipo) {
		this.atoTipo = atoTipo;
	}

	public String getAtoIdentificacao() {
		return atoIdentificacao;
	}

	public void setAtoIdentificacao(String atoIdentificacao) {
		this.atoIdentificacao = atoIdentificacao;
	}

	public String getAtoSEI() {
		return atoSEI;
	}

	public void setAtoSEI(String atoSEI) {
		this.atoSEI = atoSEI;
	}

	public String getAtoCaracterizacao() {
		return atoCaracterizacao;
	}

	public void setAtoCaracterizacao(String atoCaracterizacao) {
		this.atoCaracterizacao = atoCaracterizacao;
	}

	public java.sql.Timestamp getAtoAtualizacao() {
		return atoAtualizacao;
	}

	public void setAtoAtualizacao(java.sql.Timestamp atoAtualizacao) {
		this.atoAtualizacao = atoAtualizacao;
	}

	public String getAtoRecomendacao() {
		return atoRecomendacao;
	}

	public void setAtoRecomendacao(String atoRecomendacao) {
		this.atoRecomendacao = atoRecomendacao;
	}

	public java.sql.Date getAtoDataFiscalizacao() {
		return atoDataFiscalizacao;
	}

	public void setAtoDataFiscalizacao(java.sql.Date atoDataFiscalizacao) {
		this.atoDataFiscalizacao = atoDataFiscalizacao;
	}

	public java.sql.Date getAtoDataCriacao() {
		return atoDataCriacao;
	}

	public void setAtoDataCriacao(java.sql.Date atoDataCriacao) {
		this.atoDataCriacao = atoDataCriacao;
	}

	
	
	

}
