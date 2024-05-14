package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModelosHTML implements Serializable {
	
	
	private static final long serialVersionUID = 3746926833426506331L;
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="mod_ID")
	private int modID;
	
	@Column (name="mod_Unidade", columnDefinition="varchar(10)") // texto
	private String modUnidade;
	
	@Column (name="mod_Identificacao", columnDefinition="varchar(90)") // texto
	private String modIdentificacao;
	
	@Column (name="mod_Tipo_Interferencia", columnDefinition="varchar(20)") // texto
	private String modTipoInterferencia;
	
	@Column (name="mod_Tipo_Documento", columnDefinition="varchar(20)") // texto
	private String modTipoDocumento;
	
	@Column (name="mod_Conteudo", columnDefinition="varchar(max)") // texto
	private String modConteudo;
	
	
	
	// construtor padrao //
	public ModelosHTML () {
		
	}

	public int getModID() {
		return modID;
	}

	public void setModID(int modID) {
		this.modID = modID;
	}

	public String getModUnidade() {
		return modUnidade;
	}

	public void setModUnidade(String modUnidade) {
		this.modUnidade = modUnidade;
	}

	public String getModIdentificacao() {
		return modIdentificacao;
	}

	public void setModIdentificacao(String modIdentificacao) {
		this.modIdentificacao = modIdentificacao;
	}

	public String getModConteudo() {
		return modConteudo;
	}

	public void setModConteudo(String modConteudo) {
		this.modConteudo = modConteudo;
	}

	public String getModTipoInterferencia() {
		return modTipoInterferencia;
	}

	public void setModTipoInterferencia(String modTipoInterferencia) {
		this.modTipoInterferencia = modTipoInterferencia;
	}

	public String getModTipoDocumento() {
		return modTipoDocumento;
	}

	public void setModTipoDocumento(String modTipoDocumento) {
		this.modTipoDocumento = modTipoDocumento;
	}

	
	
	
	
}
