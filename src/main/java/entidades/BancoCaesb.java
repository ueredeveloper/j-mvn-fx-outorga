package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="Banco_Caesb")
public class BancoCaesb {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="bc_id")
	private int bcID; 
	
	@Column (name="bc_ra", columnDefinition="varchar(30)")
	private String bcRA;
	
	@Column (name="bc_cep", columnDefinition="varchar(8)")
	private String bcCEP;
	
	@Column (name="bc_inscricao", columnDefinition="varchar(7)")
	private String bcInscricao;
	
	@Column (name="bc_endereco", columnDefinition="varchar(31)")
	private String bcEndereco;
	
	@Column (name="bc_usuario", columnDefinition="varchar(39)")
	private String bcUsuario;
	
	@Column (name="bc_cpf_cnpj", columnDefinition="varchar(14)")
	private String bcCPFCNPJ;
	
	@Column (name="bc_situacao", columnDefinition="varchar(7)")
	private String bcSitucao;

	@Column (name="bc_hidrometro", columnDefinition="varchar(10)")
	private String bcHidrometro;

	public int getBcID() {
		return bcID;
	}

	public void setBcID(int bcID) {
		this.bcID = bcID;
	}

	public String getBcRA() {
		return bcRA;
	}

	public void setBcRA(String bcRA) {
		this.bcRA = bcRA;
	}

	public String getBcCEP() {
		return bcCEP;
	}

	public void setBcCEP(String bcCEP) {
		this.bcCEP = bcCEP;
	}

	public String getBcInscricao() {
		return bcInscricao;
	}

	public void setBcInscricao(String bcInscricao) {
		this.bcInscricao = bcInscricao;
	}

	public String getBcEndereco() {
		return bcEndereco;
	}

	public void setBcEndereco(String bcEndereco) {
		this.bcEndereco = bcEndereco;
	}

	public String getBcUsuario() {
		return bcUsuario;
	}

	public void setBcUsuario(String bcUsuario) {
		this.bcUsuario = bcUsuario;
	}

	public String getBcCPFCNPJ() {
		return bcCPFCNPJ;
	}

	public void setBcCPFCNPJ(String bcCPFCNPJ) {
		this.bcCPFCNPJ = bcCPFCNPJ;
	}

	public String getBcSitucao() {
		return bcSitucao;
	}

	public void setBcSitucao(String bcSitucao) {
		this.bcSitucao = bcSitucao;
	}

	public String getBcHidrometro() {
		return bcHidrometro;
	}

	public void setBcHidrometro(String bcHidrometro) {
		this.bcHidrometro = bcHidrometro;
	}


	
}
