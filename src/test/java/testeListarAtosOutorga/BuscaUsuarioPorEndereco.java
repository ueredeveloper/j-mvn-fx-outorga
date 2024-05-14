package testeListarAtosOutorga;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import entidades.Documento;
import entidades.Finalidade;
import entidades.FinalidadeAutorizada;
import entidades.HibernateUtil;
import entidades.Interferencia;
import entidades.Subterranea;

public class BuscaUsuarioPorEndereco {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		
		String strPesquisa = "28307414";

		List<Documento> list = new ArrayList<Documento>();
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		Criteria crit = s.createCriteria(Documento.class, "d");
		
		crit.createAlias("d.docEnderecoFK" , "e", JoinType.LEFT_OUTER_JOIN);
		crit.createAlias("d.docProcessoFK", "p", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("e.endRAFK", "ra", JoinType.LEFT_OUTER_JOIN);
		
		crit.createAlias("e.endUsuarioFK", "u", JoinType.LEFT_OUTER_JOIN);
		
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

		
		Criterion docTipo = Restrictions.like("docTipo", '%' + strPesquisa + '%');
		Criterion docNumero = Restrictions.like("docNumeracao", '%' + strPesquisa + '%');
		Criterion docSEI = Restrictions.like("docSEI", '%' + strPesquisa + '%');
		Criterion docProcesso = Restrictions.like("docProcesso", '%' + strPesquisa + '%');
		
		
		Disjunction orExp = Restrictions.or(docTipo, docNumero,docSEI, docProcesso);
		
		// adicionar os critérios e garantir resultados não  repetidos
		crit.add(orExp).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		list.addAll(crit.list());
		
		
		s.getTransaction().commit();
		s.close();
		
		
		for (Documento doc : list) {
			
			
			System.out.println("-------------------------------------------------------------------");
			
			 System.out.println("doc sei " + doc.getDocSEI());
			 
			 System.out.println("doc Processo Principal SEI " + doc.getDocProcessoFK().getProSEI());
			 
			 System.out.println("doc endereco " + doc.getDocEnderecoFK().getEndLogradouro());
			 
			 for (Interferencia i : doc.getDocEnderecoFK().getInterferencias()) {
				 
				 System.out.println("doc get interferencias " + i.getInterDDLatitude() + ", " + i.getInterDDLongitude());
				 
				 for (Finalidade f : i.getFinalidades()) {
					 
					if (f.getClass().getName().equals("entidades.FinalidadeAutorizada")) {
						
						 System.out.println( ((FinalidadeAutorizada) f).getFaFinalidade1() );
					}
					 
				 } // fim for
				 
				 System.out.println(doc.getDocEnderecoFK().getEndUsuarioFK().getUsNome());
			 }
			
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
		} // fim loop for
		
		
		
		
		
		
		

	}

}
