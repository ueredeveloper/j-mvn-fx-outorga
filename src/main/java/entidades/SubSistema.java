package entidades;

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
public class SubSistema {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="sub_ID")
	private int subID; 
	
	@Column (name="sub_Descricao", columnDefinition="varchar(10)")
	private String subDescricao;
	
	@Column (name="sub_Sistema_ID")
	private int sistemaID;
	
	@Column (name="sub_Reserva_Explotavel")
	private Double subReservaExplotavel;
	
	@Column (name="sub_Dominio_ID")
	private int subDominioID;
	
	@OneToMany (mappedBy = "subSubSistemaFK", cascade = CascadeType.MERGE,
			fetch = FetchType.LAZY, targetEntity = Subterranea.class)
	@Fetch(FetchMode.SUBSELECT) 
	private List<Subterranea> subterranea = new ArrayList<Subterranea>();

	//CONSTRUTOR PADRÃO
		public SubSistema () {
			
		}
		
	public SubSistema(int subID, String subDescricao) {
		super();
		this.subID = subID;
		this.subDescricao = subDescricao;
	}





	public int getSubID() {
		return subID;
	}

	public void setSubID(int subID) {
		this.subID = subID;
	}

	public String getSubDescricao() {
		return subDescricao;
	}

	public void setSubDescricao(String subDescricao) {
		this.subDescricao = subDescricao;
	}
	
	
	

}
