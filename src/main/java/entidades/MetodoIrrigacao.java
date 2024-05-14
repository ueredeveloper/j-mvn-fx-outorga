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
@Table (name="Metodo_Irrigacao")
public class MetodoIrrigacao implements Serializable  {

	
	private static final long serialVersionUID = -54268796715136117L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="mi_ID")
	private int metodoIrrigacaoID; 


	@Column (name="mi_Descricao", columnDefinition="varchar(250)")
	private String metodoIrrigacaoDescricao;
	
	    @OneToMany (mappedBy = "supMetodoIrrigacaoFK", cascade = CascadeType.MERGE,
					fetch = FetchType.LAZY, targetEntity = Superficial.class)
			@Fetch(FetchMode.SUBSELECT) 
	    	private List<Superficial> superficial = new ArrayList<Superficial>();
	

	public MetodoIrrigacao () {
		
	}

	public MetodoIrrigacao(int metodoIrrigacaoID, String metodoIrrigacaoDescricao) {
		super();
		this.metodoIrrigacaoID = metodoIrrigacaoID;
		this.metodoIrrigacaoDescricao = metodoIrrigacaoDescricao;
	}

	public int getMetodoIrrigacaoID() {
		return metodoIrrigacaoID;
	}



	public void setMetodoIrrigacaoID(int metodoIrrigacaoID) {
		this.metodoIrrigacaoID = metodoIrrigacaoID;
	}



	public String getMetodoIrrigacaoDescricao() {
		return metodoIrrigacaoDescricao;
	}



	public void setMetodoIrrigacaoDescricao(String metodoIrrigacaoDescricao) {
		this.metodoIrrigacaoDescricao = metodoIrrigacaoDescricao;
	}
	
	

}
