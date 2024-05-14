package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;

import entidades.HibernateUtil;
import entidades.Vistoria;


public class VistoriaDao {
	
public void salvarVistoria (Vistoria vis) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(vis);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Vistoria> listarVistoria (String strPesquisa) {
		
		/*
		List<Vistoria> list = new ArrayList<Vistoria>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Vistoria.class);
		
		Criterion visIden = Restrictions.like("visIdentificacao", '%' + strPesquisa + '%');
		Criterion visSei = Restrictions.like("visSEI", '%' + strPesquisa + '%');
		
		LogicalExpression orExp = Restrictions.or(visIden,visSei);
		
		crit.add(orExp);
		list = crit.list();
		
		*/
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		List<Vistoria> list = s.createQuery(
				"SELECT v FROM Vistoria AS v "
				+ "JOIN FETCH v.visEnderecoFK AS e "
				+ "LEFT JOIN FETCH e.endRAFK "
				+ "LEFT JOIN FETCH e.demandas "
				+ "WHERE ( v.visIdentificacao LIKE '%"+strPesquisa+"%' "
						+ "OR v.visSEI LIKE '%"+strPesquisa+"%')"
				).list();
		
		
		
		s.beginTransaction();
		
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerVistoria(Integer id) throws JDBCConnectionException {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Vistoria vis = (Vistoria) s.load(Vistoria.class, id);
		s.delete(vis);
		s.getTransaction().commit();
		s.close();
	}

	public void editarVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(vis);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(vis);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistirVistoria(Vistoria vis) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(vis);
		s.getTransaction().commit();
		s.close();
	}

}
