package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Vistoria implements Serializable {

	
	private static final long serialVersionUID = -4465687331826448480L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="vis_ID")
	private int visID;
	
		//-- endereço --//
		@ManyToOne (fetch = FetchType.LAZY) 
		@JoinColumn (name = "vis_Endereco_FK")
		private Endereco visEnderecoFK;
		
		
			//-- Lista de atos vinculados --//
			@OneToMany (mappedBy = "atoVistoriaFK", cascade = CascadeType.MERGE,
					fetch = FetchType.LAZY, targetEntity = Ato.class)
			@Fetch(FetchMode.SUBSELECT) 
			private List<Ato> atos = new ArrayList<Ato>();
		
	
	@Column (name="vis_Identificacao", columnDefinition="varchar(20)") // 15/2018
	private String visIdentificacao;
	
	@Column (name="vis_SEI", columnDefinition="varchar(20)") // 3561241
	private String visSEI;
			
	@Column (name="vis_Infracoes", columnDefinition="varchar(20)") // vai ficar assim no  banco, incisos: 1,2,3,4,5,6,7,8,9
	private String visInfracoes;

	@Column (name="vis_Penalidades", columnDefinition="varchar(20)") // vai ficar assim no  banco, incisos: 1,2,3,4,5,6,7,8,9
	private String  visPenalidades;
	
	@Column (name="vis_Atenuantes", columnDefinition="varchar(20)")
	private String  visAtenuantes;
	
	@Column (name="vis_Agravantes", columnDefinition="varchar(20)")
	private String visAgravantes;
	
	@Column (name="vis_Objecto", columnDefinition="varchar(2500)")
	private String visObjeto;
	
	@Column (name="vis_Apresentacao", columnDefinition="varchar(1100)")
	private String visApresentacao;
	
	@Column (name="vis_Relato", columnDefinition="varchar(3000)")
	private String visRelato;
	
	@Column (name="vis_Recomendacoes", columnDefinition="varchar(3000)")
	private String visRecomendacoes;
	
	@Basic
	@Column (name="vis_Data_Fiscalizacao")
	private java.sql.Date visDataFiscalizacao;
	
	@Basic
	@Column (name="vis_Data_Criacao")
	private java.sql.Date visDataCriacao;
	
	@Basic
	@Column (name="vis_Atualizacao")
	private java.sql.Timestamp visAtualizacao;
	
	//-- construtor vistoria tabela --//
	public Vistoria () {
		
	}

	public int getVisID() {
		return visID;
	}

	public void setVisID(int visID) {
		this.visID = visID;
	}

	public Endereco getVisEnderecoFK() {
		return visEnderecoFK;
	}

	public void setVisEnderecoFK(Endereco visEnderecoFK) {
		this.visEnderecoFK = visEnderecoFK;
	}

	public String getVisIdentificacao() {
		return visIdentificacao;
	}

	public void setVisIdentificacao(String visIdentificacao) {
		this.visIdentificacao = visIdentificacao;
	}

	public String getVisSEI() {
		return visSEI;
	}

	public void setVisSEI(String visSEI) {
		this.visSEI = visSEI;
	}

	public String getVisInfracoes() {
		return visInfracoes;
	}

	public void setVisInfracoes(String visInfracoes) {
		this.visInfracoes = visInfracoes;
	}

	public String getVisPenalidades() {
		return visPenalidades;
	}

	public void setVisPenalidades(String visPenalidades) {
		this.visPenalidades = visPenalidades;
	}

	public String getVisAtenuantes() {
		return visAtenuantes;
	}

	public void setVisAtenuantes(String visAtenuantes) {
		this.visAtenuantes = visAtenuantes;
	}

	public String getVisAgravantes() {
		return visAgravantes;
	}

	public void setVisAgravantes(String visAgravantes) {
		this.visAgravantes = visAgravantes;
	}

	public String getVisObjeto() {
		return visObjeto;
	}

	public void setVisObjeto(String visObjeto) {
		this.visObjeto = visObjeto;
	}

	public String getVisApresentacao() {
		return visApresentacao;
	}

	public void setVisApresentacao(String visApresentacao) {
		this.visApresentacao = visApresentacao;
	}

	public String getVisRelato() {
		return visRelato;
	}

	public void setVisRelato(String visRelato) {
		this.visRelato = visRelato;
	}

	public String getVisRecomendacoes() {
		return visRecomendacoes;
	}

	public void setVisRecomendacoes(String visRecomendacoes) {
		this.visRecomendacoes = visRecomendacoes;
	}

	

	public List<Ato> getAtos() {
		return atos;
	}

	public void setAtos(List<Ato> atos) {
		this.atos = atos;
	}

	public java.sql.Timestamp getVisAtualizacao() {
		return visAtualizacao;
	}

	public void setVisAtualizacao(java.sql.Timestamp visAtualizacao) {
		this.visAtualizacao = visAtualizacao;
	}

	public java.sql.Date getVisDataFiscalizacao() {
		return visDataFiscalizacao;
	}

	public void setVisDataFiscalizacao(java.sql.Date visDataFiscalizacao) {
		this.visDataFiscalizacao = visDataFiscalizacao;
	}

	public java.sql.Date getVisDataCriacao() {
		return visDataCriacao;
	}

	public void setVisDataCriacao(java.sql.Date visDataCriacao) {
		this.visDataCriacao = visDataCriacao;
	}

	

}
