package entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Finalidade {
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="fin_ID")
	private int finID;

	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinColumn (name = "fin_Interferencia_FK")
	private Interferencia finInterferenciaFK;
	

	public int getFinID() {
		return finID;
	}

	public void setFinID(int finID) {
		this.finID = finID;
	}

	public Interferencia getFinInterferenciaFK() {
		return finInterferenciaFK;
	}

	public void setFinInterferenciaFK(Interferencia finInterferenciaFK) {
		this.finInterferenciaFK = finInterferenciaFK;
	}
	
}
