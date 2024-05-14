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
@Table (name="Tipo_Ato")
public class TipoAto implements Serializable {


	private static final long serialVersionUID = 5104003410946932930L;


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ta_ID")
	private int tipoAtoID; 


	@Column (name="ta_Descricao", columnDefinition="varchar(250)")
	private String tipoAtoDescricao;
	
		@OneToMany (mappedBy = "interTipoAtoFK", cascade = CascadeType.MERGE,
				fetch = FetchType.LAZY, targetEntity = Interferencia.class)
		@Fetch(FetchMode.SUBSELECT) 
		private List<Interferencia> interferencias = new ArrayList<Interferencia>();
		

	public TipoAto () {
		
	}

	public int getTipoAtoID() {
		return tipoAtoID;
	}

	public void setTipoAtoID(int tipoAtoID) {
		this.tipoAtoID = tipoAtoID;
	}

	public String getTipoAtoDescricao() {
		return tipoAtoDescricao;
	}

	public void setTipoAtoDescricao(String tipoAtoDescricao) {
		this.tipoAtoDescricao = tipoAtoDescricao;
	}
	
}
