package entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
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
public class Processo
  implements Serializable
{
  private static final long serialVersionUID = 8270253030770436947L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="pro_ID")
  private int proID;
  
  @Column(name="pro_SEI", columnDefinition="varchar(50)")
  String proSEI;
  
  @Column(name="pro_Interessado", columnDefinition="varchar (120)")
  private String proInteressado;
 
  @Basic
  @Column(name="pro_Data_Criacao")
  private Date proDataCriacao;
  
  @Basic
  @Column(name="pro_Atualizacao")
  private Timestamp proAtualizacao;
  
 
  @OneToMany(mappedBy="docProcessoFK", cascade= CascadeType.MERGE, fetch=FetchType.LAZY, targetEntity=Documento.class)
  @Fetch(FetchMode.SUBSELECT)
  private Set<Documento> documentos  = new HashSet<>();
  
  public Processo() {}
  
  public int getProID()
  {
    return this.proID;
  }
  
  public void setProID(int proID)
  {
    this.proID = proID;
  }
  
  public String getProSEI()
  {
    return this.proSEI;
  }
  
  public void setProSEI(String proSEI)
  {
    this.proSEI = proSEI;
  }
  
  public Timestamp getProAtualizacao()
  {
    return this.proAtualizacao;
  }
  
  public void setProAtualizacao(Timestamp proAtualizacao)
  {
    this.proAtualizacao = proAtualizacao;
  }
  
  /*
  public Set<Demanda> getDemandas()
  {
    return this.demandas;
  }
  
  public void setDemandas(Set<Demanda> demandas)
  {
    this.demandas = demandas;
  }
  */
  public String getProInteressado()
  {
    return this.proInteressado;
  }
  
  public void setProInteressado(String proInteressado)
  {
    this.proInteressado = proInteressado;
  }
  
  public Date getProDataCriacao()
  {
    return this.proDataCriacao;
  }
  
  public void setProDataCriacao(Date proDataCriacao)
  {
    this.proDataCriacao = proDataCriacao;
  }

public Set<Documento> getDocumentos() {
	return documentos;
}

public void setDocumentos(Set<Documento> documentos) {
	this.documentos = documentos;
}

  
  
}
