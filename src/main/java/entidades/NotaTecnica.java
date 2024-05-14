package entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name = "Nota_Tecnica")
@PrimaryKeyJoinColumn(name="not_Documento_FK")
public class NotaTecnica extends Documento {

	private static final long serialVersionUID = -1867476160081580759L;

}
