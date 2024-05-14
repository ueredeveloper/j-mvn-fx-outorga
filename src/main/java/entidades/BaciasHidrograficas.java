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
@Table (name="Bacias_Hidrograficas")
public class BaciasHidrograficas implements Serializable {

	private static final long serialVersionUID = -82059288880931358L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="bacia_ID")
	private int baciaID; 
	
	@Column (name="bacia_Nome", columnDefinition="varchar(70)")
	private String baciaNome;
	
	@Column (name="bacia_Shape_Leng")
	private Double baciaShapeLeng;
	
	@Column (name="Shape", columnDefinition="org.hibernate.spatial.GeometryType") // , columnDefinition = "Geometry"  ver se precisa do column Definition
	private  Geometry baciaShape;  // ver com a biblioteca vividsoluctions
	
	@Column (name="GDB_GEOMATTR_DATA")
	private Double gbd;
	
	public Geometry getBaciaShape() {
		return baciaShape;
	}

	public void setBaciaShape(Geometry baciaShape) {
		this.baciaShape = baciaShape;
	}

	@Column (name="bacia_Cod")
	private int baciaCod;
	
	@OneToMany (mappedBy = "interBaciaFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Interferencia.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Interferencia> interferencias = new ArrayList<Interferencia>();
	
		// CONSTRUTOR PADRÃO //
	public BaciasHidrograficas () {
		
	}
	
		// CONSTRUTOR PADRÃO //
	public BaciasHidrograficas (int baciaID, String baciaNome) {
		this.baciaID = baciaID;
		this.baciaNome = baciaNome;
	}


	public int getBaciaID() {
		return baciaID;
	}

	public void setBaciaID(int baciaID) {
		this.baciaID = baciaID;
	}

	public String getBaciaNome() {
		return baciaNome;
	}

	public void setBaciaNome(String baciaNome) {
		this.baciaNome = baciaNome;
	}
	
	
	
	
}
