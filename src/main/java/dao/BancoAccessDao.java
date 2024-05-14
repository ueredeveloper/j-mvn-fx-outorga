package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import entidades.BancoAccess;
import entidades.HibernateUtil;

// BancoAccessDao

public class BancoAccessDao {
	
public void salvarBancoAccess (BancoAccess bancoAccess) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(bancoAccess);
		s.getTransaction().commit();
		s.flush();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BancoAccess> listarBancoAccess (String strPesquisa) {
		
		List<BancoAccess> list = new ArrayList<BancoAccess>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
			s.beginTransaction();
		
		
		Criteria crit = s.createCriteria(BancoAccess.class, "ba");

		Criterion baProcesso = Restrictions.like("ba.baNumeroProcesso", '%' + strPesquisa + '%');
		Criterion baInteressado = Restrictions.like("ba.baInteressado", '%' + strPesquisa + '%');
	
		LogicalExpression orExp = Restrictions.or(baProcesso, baInteressado);
		
		// adicionar os critérios e garantir resultados não  repetidos
		crit.add(orExp).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
		
	}
	
	/*
	public void removerDocumento(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Documento c = (Documento) s.load(Documento.class, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();
	}

	public void editarDocumento(Documento documento) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(documento);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeDocumento(Documento documento) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(documento);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistDocumento(Documento documento) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(documento);
		s.getTransaction().commit();
		s.close();
	}
*/


}
