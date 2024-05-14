package entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = -7277735746662728331L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name = "us_ID")
	private int usID;
	
		//-- Lista de enderecos vinculados --//
		@OneToMany (mappedBy = "endUsuarioFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Endereco.class)
		@Fetch(FetchMode.SUBSELECT)
		private Set<Endereco> enderecos = new HashSet<Endereco>();
		
		@ManyToOne
		@JoinColumn(name="us_Documento_FK")
		private Documento usDocumentoFK;

	@Column (name = "us_Tipo", columnDefinition="varchar(8)")
	private String usTipo;
	
	@Column (name = "us_Nome", columnDefinition="varchar(150)")
	private String usNome;
	
	@Column (name = "us_CPF_CNPJ", columnDefinition="varchar(20)")
	private String usCPFCNPJ;
	

	@Column (name = "us_Logadouro", columnDefinition="varchar (80)")
	private String usLogadouro;
	
	@Column (name = "us_RA", columnDefinition="varchar (20)")
	private String usRA;
	
	@Column (name = "us_Cidade", columnDefinition="varchar (20)")
	private String usCidade;
	
	@Column (name = "us_Estado", columnDefinition="varchar (2)")
	private String usEstado;
	
	@Column (name = "us_CEP", columnDefinition="varchar (20)")
	private String usCEP;
	
	@Column (name = "us_Telefone", columnDefinition="varchar (20)")
	private String usTelefone;
	
	@Column (name = "us_Celular", columnDefinition="varchar (20)")
	private String usCelular;
	
	@Column (name = "us_Email", columnDefinition="varchar (70)")
	private String usEmail;
	
	@Column (name = "us_Representante", columnDefinition="varchar (90)")
	private String usRepresentante;
	
	@Column (name = "us_Representante_Telefone", columnDefinition="varchar (20)")
	private String usRepresentanteTelefone;
	
	
	
	@Basic
	@Column (name="us_Data_Atualizacao")
	private java.sql.Timestamp usDataAtualizacao;
	
	//-- construtor padrão --//
	public Usuario () {
	}
	
	public Usuario(int usID, String usTipo, String usNome, String usCPFCNPJ,
			String usLogadouro, String usRA, String usCidade, String usEstado, String usCEP, String usTelefone,
			String usCelular, String usEmail, String usRepresentante, String usRepresentanteTelefone) {
		super();
		this.usID = usID;
		this.usTipo = usTipo;
		this.usNome = usNome;
		this.usCPFCNPJ = usCPFCNPJ;
		this.usLogadouro = usLogadouro;
		this.usRA = usRA;
		this.usCidade = usCidade;
		this.usEstado = usEstado;
		this.usCEP = usCEP;
		this.usTelefone = usTelefone;
		this.usCelular = usCelular;
		this.usEmail = usEmail;
		this.usRepresentante = usRepresentante;
		this.usRepresentanteTelefone = usRepresentanteTelefone;
	}

	public int getUsID() {
		return usID;
	}

	public void setUsID(int usID) {
		this.usID = usID;
	}

	
	
	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getUsTipo() {
		return usTipo;
	}

	public void setUsTipo(String usTipo) {
		this.usTipo = usTipo;
	}

	public String getUsNome() {
		return usNome;
	}

	public void setUsNome(String usNome) {
		this.usNome = usNome;
	}

	public String getUsCPFCNPJ() {
		return usCPFCNPJ;
	}

	public void setUsCPFCNPJ(String usCPFCNPJ) {
		this.usCPFCNPJ = usCPFCNPJ;
	}

	public String getUsLogadouro() {
		return usLogadouro;
	}

	public void setUsLogadouro(String usLogadouro) {
		this.usLogadouro = usLogadouro;
	}

	public String getUsRA() {
		return usRA;
	}

	public void setUsRA(String usRA) {
		this.usRA = usRA;
	}

	public String getUsCidade() {
		return usCidade;
	}

	public void setUsCidade(String usCidade) {
		this.usCidade = usCidade;
	}

	public String getUsEstado() {
		return usEstado;
	}

	public void setUsEstado(String usEstado) {
		this.usEstado = usEstado;
	}

	public String getUsCEP() {
		return usCEP;
	}

	public void setUsCEP(String usCEP) {
		this.usCEP = usCEP;
	}

	public String getUsTelefone() {
		return usTelefone;
	}

	public void setUsTelefone(String usTelefone) {
		this.usTelefone = usTelefone;
	}

	public String getUsCelular() {
		return usCelular;
	}

	public void setUsCelular(String usCelular) {
		this.usCelular = usCelular;
	}

	public String getUsEmail() {
		return usEmail;
	}

	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}

	public java.sql.Timestamp getUsDataAtualizacao() {
		return usDataAtualizacao;
	}

	public void setUsDataAtualizacao(java.sql.Timestamp usDataAtualizacao) {
		this.usDataAtualizacao = usDataAtualizacao;
	}

	public Documento getUsDocumentoFK() {
		return usDocumentoFK;
	}

	public void setUsDocumentoFK(Documento usDocumentoFK) {
		this.usDocumentoFK = usDocumentoFK;
	}

	public String getUsRepresentante() {
		return usRepresentante;
	}

	public void setUsRepresentante(String usRepresentante) {
		this.usRepresentante = usRepresentante;
	}

	public String getUsRepresentanteTelefone() {
		return usRepresentanteTelefone;
	}

	public void setUsRepresentanteTelefone(String usRepresentanteTelefone) {
		this.usRepresentanteTelefone = usRepresentanteTelefone;
	}


	
}
