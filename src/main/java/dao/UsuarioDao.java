package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinType;

import entidades.HibernateUtil;
import entidades.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import principal.Alerta;

public class UsuarioDao {
	
	public void salvarUsuario (Usuario usuario) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		
		try {
		s.save(usuario);
		}
		catch (ConstraintViolationException e ) {
			System.out.println("salvar usuario " + e);
			
			Alerta a = new Alerta();
			a.alertar(new Alert(Alert.AlertType.INFORMATION, "CPF ou CNPJ duplicado!!!", new ButtonType[] { ButtonType.OK }));
			
		}
		
		s.getTransaction().commit();
		s.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuario(String strPesquisa) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		List<Usuario> list = new ArrayList<Usuario>();
		
		Criteria crit = s.createCriteria(Usuario.class, "usuario");
		
		crit.createAlias("usuario.enderecos", "end", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("end.endRAFK", "endRA", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("end.documentos", "endDoc", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("endDoc.docProcessoFK", "docProcessos", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("end.interferencias", "i", JoinType.LEFT_OUTER_JOIN);
		
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
		crit.createAlias("i.interSubtipoOutorgaFK", "subTipoOutorga", JoinType.LEFT_OUTER_JOIN);
		
		Criterion usNome = Restrictions.like("usNome", '%' + strPesquisa + '%');
		Criterion usCPF = Restrictions.like("usCPFCNPJ", '%' + strPesquisa + '%');
		Criterion usLogradouro = Restrictions.like("usLogadouro", '%' + strPesquisa + '%');
		
		Criterion usEndereco = Restrictions.like("end.endLogradouro", '%' + strPesquisa + '%');
		
		
		Disjunction d = Restrictions.or(usNome, usCPF,usLogradouro, usEndereco);
		
		crit.add(d).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		list = crit.list();
		
		s.beginTransaction();
		
		s.getTransaction().commit();
		s.close();
		
		return list;
		
		
	}
	
	public void removerUsuario(Integer id) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Usuario u = (Usuario) s.load(Usuario.class, id);
		s.delete(u);
		s.getTransaction().commit();
		s.close();
		
	}

	public void editarUsurario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.update(usuario);
		s.getTransaction().commit();
		s.close();
	}
	
	public void mergeUsuario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.merge(usuario);
		s.getTransaction().commit();
		s.close();
	}
	
	public void persistirUsuario(Usuario usuario) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		s.persist(usuario);
		s.getTransaction().commit();
		s.close();
	}

}
