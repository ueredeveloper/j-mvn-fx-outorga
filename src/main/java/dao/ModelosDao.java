package dao;

import java.util.List;

import org.hibernate.Session;

import entidades.HibernateUtil;
import entidades.ModelosHTML;

public class ModelosDao {
	
	
	public ModelosHTML obterModeloHTMLPorID (Integer modeloID) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		ModelosHTML modeloHTMl = s.get(ModelosHTML.class, modeloID);
		
		return modeloHTMl;
		
	}
	
	
	public void salvarModelo (ModelosHTML mod) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(mod);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ModelosHTML> listarModelo (String strPesquisa) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		List<ModelosHTML> list = s.createQuery(
				"SELECT mod FROM ModelosHTML AS mod "
						+ "WHERE ( mod.modIdentificacao LIKE '%"+strPesquisa+"%')" 
				).list();


		s.getTransaction().commit();
		s.close();
		return list;
	}

	public void removerModelo(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		ModelosHTML m = (ModelosHTML) s.load(ModelosHTML.class, id);
		s.delete(m);
		s.getTransaction().commit();
		s.close();
	}

	public void editarModelo(ModelosHTML mod) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(mod);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeModelo(ModelosHTML mod) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(mod);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistModelo(ModelosHTML mod) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(mod);
		s.getTransaction().commit();
		s.close();
	}

}
