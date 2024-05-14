package entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="par_Documento_FK")
public class Parecer extends Documento {

	private static final long serialVersionUID = -4286660176210957954L;
	
	
	
}
