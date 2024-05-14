package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entidades.UnidadeHidrografica;
import entidades.HibernateUtil;

public class UnidadeHidrograficaDao {
	
	@SuppressWarnings("unchecked")
	public List<UnidadeHidrografica> listarUnidadesHidrograficas(String strPesquisa) {
		
		List<UnidadeHidrografica> list = new ArrayList<UnidadeHidrografica>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		/*
		List<UnidadeHidrografica> list = s.createQuery(
				"SELECT d FROM UnidadeHidrografica AS d "
				+ "LEFT OUTER JOIN FETCH d.demEnderecoFK "
				+ "WHERE (d.demDocumento LIKE '%"+strPesquisa+"%' "
						+ "OR d.demDocumentoSEI LIKE '%"+strPesquisa+"%' OR d.demProcessoSEI LIKE '%"+strPesquisa+"%')"
				).list();
		
		
		*/
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(UnidadeHidrografica.class, "uh");
		
		crit.add(Restrictions.like("uhBaciaNome", '%' + strPesquisa + '%'))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//crit.add(Restrictions.like("demDocumento", '%' + strPesquisa + '%'));
		list = crit.list();
		
		
		// SQL list = s.createSQLQuery("SELECT * FROM UnidadeHidrografica WHERE Documento_Denuncia LIKE '%strPesquisa%'").list();
		//list = s.createQuery("from UnidadeHidrografica d where d.Documento_Denuncia= : strPesquisa").setString("strPesquisa",strPesquisa).list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}

}
