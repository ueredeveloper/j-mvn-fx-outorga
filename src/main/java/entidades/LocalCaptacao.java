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
@Table (name="Local_Captacao")
public class LocalCaptacao implements Serializable {

	private static final long serialVersionUID = -4409321472744016999L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="lc_ID")
	private int localCatacaoID; 
	
	@Column (name="lc_Descricao", columnDefinition="varchar(250)")
	private String localCaptacaoDescricao;
	
	
		@OneToMany (mappedBy = "supLocalCaptacaoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Superficial.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Superficial> superficial = new ArrayList<Superficial>();
	
	public LocalCaptacao () {
		
	}


	public LocalCaptacao(int localCatacaoID, String localCaptacaoDescricao) {
		super();
		this.localCatacaoID = localCatacaoID;
		this.localCaptacaoDescricao = localCaptacaoDescricao;
	}


	public int getLocalCatacaoID() {
		return localCatacaoID;
	}

	public void setLocalCatacaoID(int localCatacaoID) {
		this.localCatacaoID = localCatacaoID;
	}

	public String getLocalCaptacaoDescricao() {
		return localCaptacaoDescricao;
	}

	public void setLocalCaptacaoDescricao(String localCaptacaoDescricao) {
		this.localCaptacaoDescricao = localCaptacaoDescricao;
	}
	
	
	
}
