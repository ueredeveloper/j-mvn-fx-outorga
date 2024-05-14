package entidades;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Demanda
  implements Serializable
{
  private static final long serialVersionUID = 3416480064860473637L;
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="dem_ID")
  private int demID;
  
  
  @ManyToOne (fetch = FetchType.LAZY) 
  @JoinColumn(name="dem_Endereco_FK")
  private Endereco demEnderecoFK;
  
  @ManyToOne (fetch = FetchType.LAZY) 
  @JoinColumn(name="dem_Processo_FK")
  private Processo demProcessoFK;
  
  @Column(name="dem_Tipo", columnDefinition="varchar(40)")
  private String demTipo;
 
  @Column(name="dem_Numero", columnDefinition="varchar(40)")
  private String demNumero;
  
  @Column(name="dem_Numero_SEI", columnDefinition="varchar(25)")
  private String demNumeroSEI;
  
  @Column(name="dem_Processo", columnDefinition="varchar(25)" )
  private String demProcesso;
 
  @Basic
  @Column(name="dem_Distribuicao")
  private Date demDistribuicao;
  
  @Basic
  @Column(name="dem_Recebimento")
  private Date demRecebimento;
  
  @Basic
  @Column(name="dem_Atualizacao")
  private Timestamp demAtualizacao;
  
  public int getDemID()
  {
    return this.demID;
  }
  
  public void setDemID(int demID)
  {
    this.demID = demID;
  }
  
  public Endereco getDemEnderecoFK()
  {
    return this.demEnderecoFK;
  }
  
  public void setDemEnderecoFK(Endereco demEnderecoFK)
  {
    this.demEnderecoFK = demEnderecoFK;
  }
  
  
  public Date getDemDistribuicao()
  {
    return this.demDistribuicao;
  }
  
  public void setDemDistribuicao(Date demDistribuicao)
  {
    this.demDistribuicao = demDistribuicao;
  }
  
  public Date getDemRecebimento()
  {
    return this.demRecebimento;
  }
  
  public Processo getDemProcessoFK()
  {
    return this.demProcessoFK;
  }
  
  public void setDemProcessoFK(Processo demProcessoFK)
  {
    this.demProcessoFK = demProcessoFK;
  }
  
  public void setDemRecebimento(Date demRecebimento)
  {
    this.demRecebimento = demRecebimento;
  }
  
  public Timestamp getDemAtualizacao()
  {
    return this.demAtualizacao;
  }
  
  public void setDemAtualizacao(Timestamp demAtualizacao)
  {
    this.demAtualizacao = demAtualizacao;
  }

	public String getDemTipo() {
		return demTipo;
	}
	
	public void setDemTipo(String demTipo) {
		this.demTipo = demTipo;
	}
	
	public String getDemNumero() {
		return demNumero;
	}
	
	public void setDemNumero(String demNumero) {
		this.demNumero = demNumero;
	}
	
	public String getDemNumeroSEI() {
		return demNumeroSEI;
	}
	
	public void setDemNumeroSEI(String demNumeroSEI) {
		this.demNumeroSEI = demNumeroSEI;
	}
	
	public String getDemProcesso() {
		return demProcesso;
	}
	
	public void setDemProcesso(String demProcesso) {
		this.demProcesso = demProcesso;
	}
	  
  
  
}
