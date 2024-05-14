package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import entidades.Colaborador;
import entidades.HibernateUtil;

public class ColaboradorDao {


	public void salvarColaborador (Colaborador colaborador, String strSenha) {

	Session session = HibernateUtil.getSessionFactory().openSession();
	session.beginTransaction();
	

	/* PWDENCRYPT  
	 * 		Retorna o hash de senha do SQL Server do valor de entrada que usa a versão atual do algoritmo de hash de senha.
	 * 
	 */
	Query query = session.createSQLQuery(
				"declare @pwd varbinary(100);"
			+	" set @pwd = Convert(varbinary(100), pwdEncrypt('" + strSenha + "'));"
			+	"insert into colaborador (col_Nome, col_Nome_Usuario, col_Email, col_Senha_Cript, col_Autorizacao) values (:nome, :nomeUsuario, :email, @pwd, :autorizacao)"
			);

	query.setParameter("nome", colaborador.getColNome());
	query.setParameter("nomeUsuario", colaborador.getColNomeUsuario());
	query.setParameter("email", colaborador.getColEmail()	);
	query.setParameter("autorizacao", "0");
	
	query.executeUpdate();

	session.getTransaction().commit();
	session.close();

	}
	
	
	public void mergeColaborador (Colaborador colaborador, String strSenha) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		/* PWDENCRYPT  
		 * 		Retorna o hash de senha do SQL Server do valor de entrada que usa a versão atual do algoritmo de hash de senha.
		 * 
		 */
		Query query = session.createSQLQuery(
				
				"declare @pwd varbinary(100);"
				+ 	" set @pwd = Convert(varbinary(100), pwdEncrypt('"+strSenha+"'));"
				+ 	" update colaborador "
				+ 	" set col_Nome = :nome, col_Nome_Usuario = :nomeUsuario, col_Email = :email, col_Autorizacao = :autorizacao, col_Senha_Cript = @pwd"
				+ 	" where col_id = :id"
				
				
				);

		query.setParameter("id", colaborador.getColID());
		query.setParameter("nome", colaborador.getColNome());
		query.setParameter("nomeUsuario", colaborador.getColNomeUsuario());
		query.setParameter("email", colaborador.getColEmail()	);
		query.setParameter("autorizacao", "0");
		
		query.executeUpdate();

		session.getTransaction().commit();
		session.close();

	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaborador (String strPesquisa) {

		List<Colaborador> list = new ArrayList<Colaborador>();

		Session s = HibernateUtil.getSessionFactory().openSession();

		s.beginTransaction();

		Criteria crit = s.createCriteria(Colaborador.class, "colaborador");

		Criterion colNome = Restrictions.like("colNome", '%' + strPesquisa + '%');
		Criterion colIdentidade = Restrictions.like("colNomeUsuario", '%' + strPesquisa + '%');

		LogicalExpression orExp = Restrictions.or(colNome, colIdentidade);

		// adicionar os critérios e garantir resultados não  repetidos
		crit.add(orExp).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		list = crit.list();

		s.getTransaction().commit();
		s.close();
		return list;

	}
	
	public void removerColaborador (Integer id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();
		Colaborador c = (Colaborador) s.load(Colaborador.class, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();
	}
	
	public Colaborador verificarExistenciaColaborador (String strNomeUsuario, String strEmailUsuario) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Colaborador colaborador = (Colaborador) session.createCriteria(Colaborador.class)
		
	      .add(Restrictions.eq("colNomeUsuario", strNomeUsuario))
	      .add(Restrictions.eq("colEmail", strEmailUsuario))
	      .uniqueResult();
	
	    return colaborador;
	    
	}
	
	public int verificarSenha (String strNomeUsuario, String strSenha) {
		
		int number  = 0;

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Colaborador colEncontrado = (Colaborador) session.createCriteria(Colaborador.class)
				
			      .add(Restrictions.eq("colNomeUsuario", strNomeUsuario))
			      .add(Restrictions.eq("colAutorizacao", "1"))
			      .uniqueResult();
		
		if (colEncontrado != null) {
			
			String strQuery = 		
					"declare @pwd varbinary(100);"
				+	" select @pwd = col_Senha_Cript from colaborador where col_id = :intID ;"
				+	" select pwdCompare(:strSenha, @pwd, 0)";
		
			Query query = session.createSQLQuery(strQuery);
		
			query.setParameter("intID", colEncontrado.getColID());
			query.setParameter("strSenha", strSenha);
		
			number = (int) query.uniqueResult();
		
		}
		
	    return number;
	    
	}

}
