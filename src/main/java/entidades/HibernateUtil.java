package entidades;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory factory = null;
	
	private static Configuration conf;
	
	private static String newUserName = "coutAdasa";
	private static String newPassword = "@d@s@Cou1";
	
	public static void getUserPass ( String newUserName, String newPassword) {
		
		HibernateUtil.newUserName = newUserName;
		HibernateUtil.newPassword = newPassword;
	}
	
	private static SessionFactory buildSessionFactory () {
		try {
		conf = new Configuration();
		conf.configure("/Hibernate.cfg.xml");
		
			conf.getProperties().setProperty("hibernate.connection.username",newUserName);
			conf.getProperties().setProperty("hibernate.connection.password",newPassword);
		
			System.out.println("Configurou!");
		
		factory = conf.buildSessionFactory();
		
			System.out.println("Construiu a fabrica de sessoes");
		
		return factory;
		
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory () {
		if (factory == null) 
			factory = buildSessionFactory();
		
		return factory;
	}
}