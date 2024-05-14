package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entidades.BaciasHidrograficas;
import entidades.HibernateUtil;
import entidades.SubSistema;

public class BaciasHidrograficasDao {
	
	@SuppressWarnings("unchecked")
	public List<BaciasHidrograficas> listarBaciasHidrograficas(String strPesquisa) {
		
		List<BaciasHidrograficas> list = new ArrayList<BaciasHidrograficas>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		/*
		List<BaciasHidrograficas> list = s.createQuery(
				"SELECT d FROM BaciasHidrograficas AS d "
				+ "LEFT OUTER JOIN FETCH d.demEnderecoFK "
				+ "WHERE (d.demDocumento LIKE '%"+strPesquisa+"%' "
						+ "OR d.demDocumentoSEI LIKE '%"+strPesquisa+"%' OR d.demProcessoSEI LIKE '%"+strPesquisa+"%')"
				).list();
		
		
		*/
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(BaciasHidrograficas.class, "bacias");
		
		crit.add(Restrictions.like("baciaNome", '%' + strPesquisa + '%'))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//crit.add(Restrictions.like("demDocumento", '%' + strPesquisa + '%'));
		list = crit.list();
		
		
		// SQL list = s.createSQLQuery("SELECT * FROM BaciasHidrograficas WHERE Documento_Denuncia LIKE '%strPesquisa%'").list();
		//list = s.createQuery("from BaciasHidrograficas d where d.Documento_Denuncia= : strPesquisa").setString("strPesquisa",strPesquisa).list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SubSistema> listarSubsistema (String strPesquisa) {
		
		List<SubSistema> list = new ArrayList<SubSistema>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(SubSistema.class, "subsistema");
		
		crit.add(Restrictions.like("subDescricao", '%' + strPesquisa + '%'))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		list = crit.list();

		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	

}
