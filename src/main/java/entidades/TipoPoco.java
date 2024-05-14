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
@Table (name="Tipo_Poco")
public class TipoPoco implements Serializable  {

	private static final long serialVersionUID = -136381809199976320L;


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="tp_ID")
	private int tipoPocoID; 


    @Column (name="tp_Descricao", columnDefinition="varchar(250)")
	private String tipoPocoDescricao;
    
	    @OneToMany (mappedBy = "subTipoPocoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Subterranea.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Subterranea> subterranea = new ArrayList<Subterranea>();
		
    
    public TipoPoco () {
		
	}



	public TipoPoco(int tipoPocoID, String tipoPocoDescricao) {
		super();
		this.tipoPocoID = tipoPocoID;
		this.tipoPocoDescricao = tipoPocoDescricao;
	}



	public int getTipoPocoID() {
		return tipoPocoID;
	}


	public void setTipoPocoID(int tipoPocoID) {
		this.tipoPocoID = tipoPocoID;
	}


	public String getTipoPocoDescricao() {
		return tipoPocoDescricao;
	}


	public void setTipoPocoDescricao(String tipoPocoDescricao) {
		this.tipoPocoDescricao = tipoPocoDescricao;
	}


	public List<Subterranea> getSubterranea() {
		return subterranea;
	}


	public void setSubterranea(List<Subterranea> subterranea) {
		this.subterranea = subterranea;
	}
    
    
    
    
    
    

}
