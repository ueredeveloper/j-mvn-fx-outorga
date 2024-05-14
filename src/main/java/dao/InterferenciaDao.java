package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import entidades.HibernateUtil;
import entidades.Interferencia;

public class InterferenciaDao {
	
	public void salvaInterferencia (Interferencia interferencia) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		
		try {
		s.save(interferencia);
		}
		catch (HibernateException e ) {
			e.printStackTrace();
		}
		
		s.getTransaction().commit();
		s.close();
		
	}
	
	public Interferencia obterInterferenciaPorID (Integer interID) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Interferencia interferencia = s.get(Interferencia.class, interID);
		
		return interferencia;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Interferencia> listInterferencia(String strPesquisa) throws Exception {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		List<Interferencia> list = new ArrayList<Interferencia>();
		
		Criteria crit = s.createCriteria(Interferencia.class, "i");
		
		crit.createAlias("i.interEnderecoFK", "endereco", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("endereco.endRAFK", "regiaoAdm", JoinType.LEFT_OUTER_JOIN);
	
		
		crit.createAlias("i.interTipoInterferenciaFK", "tipoInter", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interTipoOutorgaFK", "tipoOutorga", JoinType.LEFT_OUTER_JOIN); 
		crit.createAlias("i.interSubtipoOutorgaFK", "SubTipoOutorga", JoinType.LEFT_OUTER_JOIN); 
		crit.createAlias("i.interTipoAtoFK", "tipoAto", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interSituacaoProcessoFK", "situacaoProcesso", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interBaciaFK", "baciaInter", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interUHFK", "unidaHidInter", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.subTipoPocoFK", "tipoPoco", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.subSubSistemaFK", "subSistema", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.supFormaCaptacaoFK", "formaCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supLocalCaptacaoFK", "localCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supMetodoIrrigacaoFK", "metodoIrrigacao", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.finalidades", "finalidades", JoinType.LEFT_OUTER_JOIN);
		
		Criterion c1 = Restrictions.like("interNumeroAto", '%' + strPesquisa + '%');
		Criterion c2 = Restrictions.like("interProcRenovacao", '%' + strPesquisa + '%');
		Criterion c3 = Restrictions.like("interDespachoRenovacao", '%' + strPesquisa + '%');
		Criterion c4 = Restrictions.like("endereco.endLogradouro", '%' + strPesquisa + '%');
		
		
		Disjunction orExp = Restrictions.or(c1,c2, c3, c4);
		
		// adicionar os critérios e garantir resultados não  repetidos
		crit.add(orExp).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//crit.add(Restrictions.like("interNumeroAto", '%' + strPesquisa + '%'));//.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//crit.add(Restrictions.like("endereco.endLogradouro", '%' + strPesquisa + '%')).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removeInterferencia(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Interferencia i = (Interferencia) s.load(Interferencia.class, id);
		s.delete(i);
		s.getTransaction().commit();
		s.close();
	}

	public void editarInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistInterferencia(Interferencia interferencia) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(interferencia);
		s.getTransaction().commit();
		s.close();
	}
	
}



/*
List<Interferencia> list = new ArrayList<Interferencia>();

Session s = HibernateUtil.getSessionFactory().openSession();

s.beginTransaction();

Criteria crit = s.createCriteria(Interferencia.class);
crit.add(Restrictions.like("inter_Desc_Endereco", '%' + strPesquisaInterferencia + '%'));
list = crit.list();
*/

//JOIN FETCH i.sub_Interferencia_Codigo JOIN FETCH i.super_Interferencia_Codigo 
// sub_Interferencia_Codigo
//super_Interferencia_Codigo
//inter_End_CodigoFK


/*
List<Interferencia> listSub = s.createQuery(
		"SELECT i FROM Interferencia AS i "
		+ "JOIN FETCH i.inter_End_CodigoFK JOIN FETCH i.sub_Interferencia_Codigo "
		+ "WHERE ( i.inter_Desc_Endereco LIKE '%"+strPesquisa+"%' "
				+ "OR i.inter_Tipo LIKE '%"+strPesquisa+"%')"
		).list();

List<Interferencia> listSup = s.createQuery(
		"SELECT i FROM Interferencia AS i "
		+ "JOIN FETCH i.inter_End_CodigoFK JOIN FETCH i.super_Interferencia_Codigo "
		+ "WHERE ( i.inter_Desc_Endereco LIKE '%"+strPesquisa+"%' "
				+ "OR i.inter_Tipo LIKE '%"+strPesquisa+"%')"
		).list();
		*/
//try {

/*
List<Interferencia> listOutros = s.createQuery(
			"SELECT i FROM Interferencia AS i "
		+ 	"JOIN FETCH i.interEnderecoFK AS e "
					
		+ 	"LEFT OUTER JOIN FETCH e.endRAFK "
		+ 	"LEFT OUTER JOIN FETCH e.demandas "
		
		+	"LEFT OUTER JOIN FETCH i.intSubFK AS sub "
		+	"LEFT OUTER JOIN FETCH sub.subSubSistemaFK "
		
		+	"LEFT OUTER JOIN FETCH i.intSupFK "
		+	"LEFT OUTER JOIN FETCH i.interTipoInterferenciaFK "
		+	"LEFT OUTER JOIN FETCH i.interBaciaFK "
		+	"LEFT OUTER JOIN FETCH i.interUHFK "
		
		+ 	"WHERE ( e.endLogradouro LIKE '%"+strPesquisa+"%')" // OR i.inter_Tipo LIKE '%"+strPesquisa+"%'   // i.interLogadouro
		).list();
		
		// esta maneira está duplicando resultados na lista
*/
