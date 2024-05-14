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
@Table(name="TIPO_INTERFERENCIA")
public class TipoInterferencia implements Serializable {

	private static final long serialVersionUID = -8628868832732571370L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_TIPO_INTERFERENCIA") 
	private int tipoInterID; 
	
	@Column (name="DESCRICAO", columnDefinition="varchar(95)")
	private String tipoInterDescricao;
	
	@OneToMany (mappedBy = "interTipoInterferenciaFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Interferencia.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Interferencia> interferencias = new ArrayList<Interferencia>();

	//CONSTRUTOR PADRÃO
	public TipoInterferencia () {
			
	}
		
	public TipoInterferencia(int tipoInterID, String tipoInterDescricao) {
		super();
		this.tipoInterID = tipoInterID;
		this.tipoInterDescricao = tipoInterDescricao;
	}



	public int getTipoInterID() {
		return tipoInterID;
	}

	public void setTipoInterID(int tipoInterID) {
		this.tipoInterID = tipoInterID;
	}

	public String getTipoInterDescricao() {
		return tipoInterDescricao;
	}

	public void setTipoInterDescricao(String tipoInterDescricao) {
		this.tipoInterDescricao = tipoInterDescricao;
	}

	public List<Interferencia> getInterferencias() {
		return interferencias;
	}

	public void setInterferencias(List<Interferencia> interferencias) {
		this.interferencias = interferencias;
	}
	
	
	
	
}
