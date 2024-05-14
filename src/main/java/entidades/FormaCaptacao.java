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
@Table (name="Forma_Captacao")
public class FormaCaptacao implements Serializable {

	private static final long serialVersionUID = 3980811660724691365L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="fc_ID")
	private int formaCaptacaoID; 


	@Column (name="fc_Descricao", columnDefinition="varchar(250)")
	private String formaCaptacaoDescricao;
	
		@OneToMany (mappedBy = "supFormaCaptacaoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Superficial.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Superficial> superficial = new ArrayList<Superficial>();

		
	public FormaCaptacao () {
		
	}


	public FormaCaptacao(int formaCaptacaoID, String formaCaptacaoDescricao) {
		super();
		this.formaCaptacaoID = formaCaptacaoID;
		this.formaCaptacaoDescricao = formaCaptacaoDescricao;
	}


	public int getFormaCaptacaoID() {
		return formaCaptacaoID;
	}


	public void setFormaCaptacaoID(int formaCaptacaoID) {
		this.formaCaptacaoID = formaCaptacaoID;
	}


	public String getFormaCaptacaoDescricao() {
		return formaCaptacaoDescricao;
	}


	public void setFormaCaptacaoDescricao(String formaCaptacaoDescricao) {
		this.formaCaptacaoDescricao = formaCaptacaoDescricao;
	}
	
	
	

}
