package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Colaborador implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8114960696128357409L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="col_ID")
	private int colID;
	
	@Column(name="col_Nome", columnDefinition="varchar(100)")
	private String colNome;
	
	@Column(name="col_Nome_Usuario", columnDefinition="varchar(50)")
	private String colNomeUsuario;
	
	@Column(name="col_Email", columnDefinition="varchar(100)")
	private String colEmail;
	
	@Column(name="col_Autorizacao", columnDefinition="varchar(10)")
	private String colAutorizacao;
	
	@Column(name="col_Senha_Cript", columnDefinition="varbinary(100)")
	private byte[] colSenhaCript;


	public Colaborador () {
		
	}
	
	public Colaborador(int colID, String colNome, String colNomeUsuario, String colEmail, String colAutorizacao) {
		super();
		this.colID = colID;
		this.colNome = colNome;
		this.colNomeUsuario = colNomeUsuario;
		this.colEmail = colEmail;
		this.colAutorizacao = colAutorizacao;

	}

	

	public Colaborador(String colNome, String colNomeUsuario, String colEmail, String colAutorizacao) {
		super();
		this.colNome = colNome;
		this.colNomeUsuario = colNomeUsuario;
		this.colEmail = colEmail;
		this.colAutorizacao = colAutorizacao;
	}

	public int getColID() {
		return colID;
	}

	public void setColID(int colID) {
		this.colID = colID;
	}

	public String getColNome() {
		return colNome;
	}

	public void setColNome(String colNome) {
		this.colNome = colNome;
	}

	public String getColNomeUsuario() {
		return colNomeUsuario;
	}

	public void setColNomeUsuario(String colNomeUsuario) {
		this.colNomeUsuario = colNomeUsuario;
	}
	
	public String getColEmail() {
		return colEmail;
	}

	public void setColEmail(String colEmail) {
		this.colEmail = colEmail;
	}

	public String getColAutorizacao() {
		return colAutorizacao;
	}

	public void setColAutorizacao(String colAutorizacao) {
		this.colAutorizacao = colAutorizacao;
	}

	public byte[] getColSenhaCript() {
		return colSenhaCript;
	}

	public void setColSenhaCript(byte[] colSenhaCript) {
		this.colSenhaCript = colSenhaCript;
	}

}
