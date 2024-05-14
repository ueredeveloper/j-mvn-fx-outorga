package dao;

import java.util.List;

import org.hibernate.Session;

import entidades.Ato;
import entidades.HibernateUtil;

public class AtoDao {
	
public void salvarAto (Ato ato) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(ato);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Ato> listAto(String strPesquisa) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		List<Ato> list = s.createQuery(
				"SELECT a FROM Ato AS a "
				+ "JOIN FETCH a.atoVistoriaFK av "
				+ "JOIN FETCH av.visEnderecoFK "
				+ "WHERE ( a.atoIdentificacao LIKE '%"+strPesquisa+"%' "
						+ "OR a.atoSEI LIKE '%"+strPesquisa+"%') "
				).list();
		
		s.beginTransaction();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerAto(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Ato i = (Ato) s.load(Ato.class, id);
		s.delete(i);
		s.getTransaction().commit();
		s.close();
	}

	public void editarAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(ato);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(ato);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistAto(Ato ato) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(ato);
		s.getTransaction().commit();
		s.close();
	}

}
