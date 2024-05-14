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

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name="UNIDADE_HIDROGRAFICA")
public class UnidadeHidrografica implements Serializable{

	private static final long serialVersionUID = -6207059484986075380L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="OBJECTID_1")
	private int uhID; 
	
	@Column (name="OBJECTID")
	private int objectID;

	@Column (name="uh_nome", columnDefinition="varchar(70)")
	private String uhNome;
	
	@Column (name="uh_codigo")
	private int uhCodigo;
	
	@Column (name="subbacia_n", columnDefinition="varchar(150)")
	private String uhBaciaNome;
	
	@Column (name="Shape", columnDefinition="org.hibernate.spatial.GeometryType") // , columnDefinition = "Geometry"  ver se precisa do column Definition
	private  Geometry Shape;  // ver com a biblioteca vividsoluctions

	@OneToMany (mappedBy = "interUHFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Interferencia.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Interferencia> interferencias = new ArrayList<Interferencia>();
	
	public Geometry getShape() {
		return Shape;
	}

	public void setShape(Geometry shape) {
		Shape = shape;
	}

	//CONSTRUTOR PADRÃO
	public UnidadeHidrografica () {
		
	}
	
	
	public UnidadeHidrografica (int uhID, int uhCodigo) {
		this.uhID = uhID;
		this.uhCodigo = uhCodigo;
	}
	
	

	public int getUhID() {
		return uhID;
	}

	public void setUhID(int uhID) {
		this.uhID = uhID;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	public String getUhNome() {
		return uhNome;
	}

	public void setUhNome(String uhNome) {
		this.uhNome = uhNome;
	}

	public int getUhCodigo() {
		return uhCodigo;
	}

	public void setUhCodigo(int uhCodigo) {
		this.uhCodigo = uhCodigo;
	}

	public String getUhBaciaNome() {
		return uhBaciaNome;
	}

	public void setUhBaciaNome(String uhBaciaNome) {
		this.uhBaciaNome = uhBaciaNome;
	}

	public List<Interferencia> getInterferencias() {
		return interferencias;
	}

	public void setInterferencias(List<Interferencia> interferencias) {
		this.interferencias = interferencias;
	}
	
	
	
	
	
}
