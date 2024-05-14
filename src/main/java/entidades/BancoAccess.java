package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="Banco_Access")
public class BancoAccess {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ba_id")
	private int baID; 
	
	@Column (name="ba_numero_processo", columnDefinition="varchar(17)")
	private String baNumeroProcesso;
	
	@Column (name="ba_interessado ", columnDefinition="varchar(116)")
	private String baInteressado;
	
	@Column (name="ba_endereco_empreendimento", columnDefinition="varchar(181)")
	private String baEnderecoEmpreendimento;
	
	
	public int getBaID() {
		return baID;
	}

	public void setBaID(int baID) {
		this.baID = baID;
	}

	public String getBaNumeroProcesso() {
		return baNumeroProcesso;
	}

	public void setBaNumeroProcesso(String baNumeroProcesso) {
		this.baNumeroProcesso = baNumeroProcesso;
	}

	public String getBaInteressado() {
		return baInteressado;
	}

	public void setBaInteressado(String baInteressado) {
		this.baInteressado = baInteressado;
	}

	public String getBaEnderecoEmpreendimento() {
		return baEnderecoEmpreendimento;
	}

	public void setBaEnderecoEmpreendimento(String baEnderecoEmpreendimento) {
		this.baEnderecoEmpreendimento = baEnderecoEmpreendimento;
	}
	
	
	
	
	/*
	ba_id                       INTEGER  NOT NULL PRIMARY KEY IDENTITY(1,1)
	  ,ba_numero_processo          NVARCHAR(17) NOT NULL
	  ,ba_ano                      INTEGER 
	  ,ba_interessado              NVARCHAR(116) NOT NULL
	  ,ba_endereco_empreendimento  NVARCHAR(181) NOT NULL
	  ,ba_regiao_administrativa    NVARCHAR(25)
	  ,ba_vencimento               VARCHAR(10)
	  ,ba_tipo_captacao            NVARCHAR(23)
	  ,ba_tipo_requerimento        NVARCHAR(28) NOT NULL
	  ,ba_bacia_hidrografica       NVARCHAR(49) NOT NULL
	  ,ba_situacao_processo        NVARCHAR(10) NOT NULL
	  ,ba_data_publicacao          VARCHAR(10)
	  ,ba_quantidade_requerimentos NVARCHAR(7)
	  ,ba_motivo                   NVARCHAR(71)
	  ,ba_ano_requerimento         VARCHAR(10)
	  */
	
	
	

}
