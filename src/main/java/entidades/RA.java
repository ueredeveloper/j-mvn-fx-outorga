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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class RA implements Serializable {
	
	private static final long serialVersionUID = -2072822763926084869L;
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ra_ID")
	private int raID;
	
	@OneToMany (mappedBy = "endRAFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Endereco.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	
	@Column (name="ra_Nome") 
	private String raNome;
	
	@Column (name="ra_Local") 
	private String raLocal;
	
	@Column (name="ra_UF") 
	private String raUF;
	
	@Column (name="ra_Sistema") 
	private int raSistema;
	
	@Column (name="ra_Latitude") 
	private Double raLatitude;
	
	@Column (name="ra_Longitude") 
	private Double raLongitude;
	
	@Column (name="ra_Sigla") 
	private String raSigla;
	
	
	//CONSTRUTOR PADRÃO
		public RA () {
			
		}

		public RA (int raID, String raNome) {
			this.raID = raID;
			this.raNome = raNome;
		}
		
		
		
		public int getRaID() {
			return raID;
		}
		public void setRaID(int raID) {
			this.raID = raID;
		}
		public List<Endereco> getEnderecos() {
			return enderecos;
		}
		public void setEnderecos(List<Endereco> enderecos) {
			this.enderecos = enderecos;
		}
		public String getRaNome() {
			return raNome;
		}

		public void setRaNome(String raNome) {
			this.raNome = raNome;
		}

		public String getRaLocal() {
			return raLocal;
		}


		public void setRaLocal(String raLocal) {
			this.raLocal = raLocal;
		}


		public String getRaUF() {
			return raUF;
		}


		public void setRaUF(String raUF) {
			this.raUF = raUF;
		}


		public int getRaSistema() {
			return raSistema;
		}


		public void setRaSistema(int raSistema) {
			this.raSistema = raSistema;
		}


		public Double getRaLatitude() {
			return raLatitude;
		}


		public void setRaLatitude(Double raLatitude) {
			this.raLatitude = raLatitude;
		}


		public Double getRaLongitude() {
			return raLongitude;
		}


		public void setRaLongitude(Double raLongitude) {
			this.raLongitude = raLongitude;
		}


		public String getRaSigla() {
			return raSigla;
		}


		public void setRaSigla(String raSigla) {
			this.raSigla = raSigla;
		}
		
		

	
}
