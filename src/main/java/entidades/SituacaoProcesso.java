package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table (name="Situacao_Processo")
public class SituacaoProcesso implements Serializable {

	private static final long serialVersionUID = 570368864297601566L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="sp_ID")
	private int situacaoProcessoID; 


	@Column (name="sp_Descricao", columnDefinition="varchar(250)")
	private String situacaoProcessoDescricao;

    	@OneToMany (mappedBy = "interSituacaoProcessoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Interferencia.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Interferencia> interferencias = new ArrayList<Interferencia>();

	
	public SituacaoProcesso () {
		
	}



	public int getSituacaoProcessoID() {
		return situacaoProcessoID;
	}



	public void setSituacaoProcessoID(int situacaoProcessoID) {
		this.situacaoProcessoID = situacaoProcessoID;
	}



	public String getSituacaoProcessoDescricao() {
		return situacaoProcessoDescricao;
	}



	public void setSituacaoProcessoDescricao(String situacaoProcessoDescricao) {
		this.situacaoProcessoDescricao = situacaoProcessoDescricao;
	}
	
	
}
