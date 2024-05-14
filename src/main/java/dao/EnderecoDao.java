package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import entidades.Endereco;
import entidades.HibernateUtil;
import entidades.RA;


public class EnderecoDao {
	
	
	public void salvarEndereco (Endereco endereco) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.save(endereco);
		s.getTransaction().commit();
		s.close();
		
	}
	

	/*
	public Endereco obterEnderecoPorID (Integer endID) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Endereco endereco = s.get(Endereco.class, endID);
		
		return endereco;
		
	}
	*/
	
	public Endereco obterEnderecoPorID (Integer endID) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		//Endereco endereco = s.get(Endereco.class, endID);
		
		Endereco endereco = null;
		
		Criteria crit = s.createCriteria(Endereco.class, "e");
		crit.createAlias("e.documentos", "documentos", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("documentos.docProcessoFK", "processos", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("e.endRAFK", "ra", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("e.interferencias", "i", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.subTipoPocoFK", "tipoPoco", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.subSubSistemaFK", "subSistema", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.supFormaCaptacaoFK", "formaCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supLocalCaptacaoFK", "localCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supMetodoIrrigacaoFK", "metodoIrrigacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interSituacaoProcessoFK", "situacaoProcesso", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interTipoAtoFK", "tipoAto", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interTipoInterferenciaFK", "tipoInter", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interBaciaFK", "baciaInter", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interUHFK", "unidaHidInter", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interTipoOutorgaFK", "tipoOutorga", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interSubtipoOutorgaFK", "subtipoOutorga", JoinType.LEFT_OUTER_JOIN);
		
		crit.add(Restrictions.idEq(endID));
		
		endereco = (Endereco) crit.list().get(0);
		
		
		return endereco;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Endereco> listarEndereco(String strPesquisa) {
	
		List<Endereco> list = new ArrayList<Endereco>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Endereco.class, "e");
			crit.createAlias("e.documentos", "documentos", JoinType.LEFT_OUTER_JOIN);
			crit.createAlias("documentos.docProcessoFK", "processos", JoinType.LEFT_OUTER_JOIN);
			
		crit.createAlias("e.endRAFK", "ra", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("e.interferencias", "i", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.subTipoPocoFK", "tipoPoco", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.subSubSistemaFK", "subSistema", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.supFormaCaptacaoFK", "formaCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supLocalCaptacaoFK", "localCaptacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.supMetodoIrrigacaoFK", "metodoIrrigacao", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interSituacaoProcessoFK", "situacaoProcesso", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interTipoAtoFK", "tipoAto", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interTipoInterferenciaFK", "tipoInter", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interBaciaFK", "baciaInter", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interUHFK", "unidaHidInter", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("i.interTipoOutorgaFK", "tipoOutorga", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("i.interSubtipoOutorgaFK", "subtipoOutorga", JoinType.LEFT_OUTER_JOIN);
		
		crit.add(Restrictions.like("endLogradouro", '%' + strPesquisa + '%'))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}
	
	public void removerEndereco(Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Endereco e = (Endereco) s.load(Endereco.class, id);
		s.delete(e);
		s.getTransaction().commit();
		s.close();
	}

	public void editarEndereco(Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(endereco);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeEndereco (Endereco endereco) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(endereco);
		s.getTransaction().commit();
		//s.flush(); // para retornar o id do objeto gravado
		s.close();
	}
	
	

	@SuppressWarnings("unchecked")
	public List<RA> listarRA() {
	
		List<RA> list = new ArrayList<RA>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(RA.class, "ra");

		list = crit.list();
		
		s.getTransaction().commit();
		s.close();
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<Object> listarObjeto(Object o) {
	
		List<Object> list = new ArrayList<Object>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
	
		Criteria crit = s.createCriteria(o.getClass(), "obj");

		list = crit.list();

		s.getTransaction().commit();
		s.close();

		return list;
	}
	
	
}
