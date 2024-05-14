package entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Documento implements Serializable {

	private static final long serialVersionUID = -1823535321680411168L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="doc_ID")
	private int docID;
	
	@Column(name="doc_Tipo", columnDefinition="varchar(50)")
	private String docTipo;
		 
	@Column(name="doc_Numeracao", columnDefinition="varchar(50)")
	private String docNumeracao;
	  
	@Column(name="doc_SEI", columnDefinition="varchar(50)")
	private String docSEI;
	  
	@Column(name="doc_Processo", columnDefinition="varchar(50)")
	private String docProcesso;
	 
	@Basic
	@Column(name="doc_Data_Criacao")
	private Date docDataCriacao;  
	
	@Basic
	@Column(name="doc_Data_Distribuicao")
	private Date docDataDistribuicao;
	  
	@Basic
	@Column(name="dem_Recebimento")
	private Date DocDataRecebimento;
	  
	@Basic
	@Column(name="doc_Data_Atualizacao")
	private Timestamp docDataAtualizacao;

	
	@ManyToOne
	@JoinColumn(name="doc_Endereco_FK")
	private Endereco docEnderecoFK;
	
		@ManyToOne
		@JoinColumn(name="doc_Processo_FK")
		private Processo docProcessoFK;
		
			//-- Lista de enderecos vinculados --//
			@OneToMany (mappedBy = "usDocumentoFK", cascade = CascadeType.MERGE,
					fetch = FetchType.LAZY, targetEntity = Usuario.class)
			@Fetch(FetchMode.SUBSELECT)
			private Set<Usuario> usuarios = new HashSet<Usuario>();
	
	// Construtor padrão
	public Documento () {
		
	}
	
	public Documento (String docTipo, String docNumeracao, String docSEI, String docProcesso, Endereco docEnderecoFK) {
		
		this.docTipo = docTipo;
		this.docNumeracao = docNumeracao;
		this.docSEI = docSEI;
		this.docProcesso = docProcesso;
		this.docEnderecoFK = docEnderecoFK;
	
		
	}
				

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public Endereco getDocEnderecoFK() {
		return docEnderecoFK;
	}

	public void setDocEnderecoFK(Endereco docEnderecoFK) {
		this.docEnderecoFK = docEnderecoFK;
	}

	public Processo getDocProcessoFK() {
		return docProcessoFK;
	}

	public void setDocProcessoFK(Processo docProcessoFK) {
		this.docProcessoFK = docProcessoFK;
	}

	public String getDocNumeracao() {
		return docNumeracao;
	}

	public void setDocNumeracao(String docNumeracao) {
		this.docNumeracao = docNumeracao;
	}

	public String getDocSEI() {
		return docSEI;
	}

	public void setDocSEI(String docSEI) {
		this.docSEI = docSEI;
	}

	public String getDocProcesso() {
		return docProcesso;
	}

	public void setDocProcesso(String docProcesso) {
		this.docProcesso = docProcesso;
	}

	public Date getDocDataCriacao() {
		return docDataCriacao;
	}

	public void setDocDataCriacao(Date docDataCriacao) {
		this.docDataCriacao = docDataCriacao;
	}

	public Date getDocDataDistribuicao() {
		return docDataDistribuicao;
	}

	public void setDocDataDistribuicao(Date docDataDistribuicao) {
		this.docDataDistribuicao = docDataDistribuicao;
	}

	public Date getDocDataRecebimento() {
		return DocDataRecebimento;
	}

	public void setDocDataRecebimento(Date docDataRecebimento) {
		DocDataRecebimento = docDataRecebimento;
	}

	public Timestamp getDocDataAtualizacao() {
		return docDataAtualizacao;
	}

	public void setDocDataAtualizacao(Timestamp docDataAtualizacao) {
		this.docDataAtualizacao = docDataAtualizacao;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getDocTipo() {
		return docTipo;
	}

	public void setDocTipo(String docTipo) {
		this.docTipo = docTipo;
	}
	
}
