package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import entidades.BancoCaesb;
import entidades.HibernateUtil;

public class BancoCaesbDao {
	
	@SuppressWarnings("unchecked")
	public List<BancoCaesb> listarBancoCaesb (String strPesquisa) {
		
		List<BancoCaesb> list = new ArrayList<BancoCaesb>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
			s.beginTransaction();
		
		
		Criteria crit = s.createCriteria(BancoCaesb.class, "bc");

		Criterion bcInscricao = Restrictions.like("bc.bcInscricao", '%' + strPesquisa + '%');
		Criterion bcEndereco = Restrictions.like("bc.bcEndereco", '%' + strPesquisa + '%');
		Criterion bcUsuario = Restrictions.like("bc.bcUsuario", '%' + strPesquisa + '%');
		Criterion bcCPFCNPJ = Restrictions.like("bc.bcCPFCNPJ", '%' + strPesquisa + '%');
		
		Disjunction orExp = Restrictions.or(bcInscricao, bcEndereco, bcUsuario, bcCPFCNPJ);
		
		// adicionar os critérios e garantir resultados não  repetidos
		crit.add(orExp).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
		
	}

}
