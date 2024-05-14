package adasa;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import dao.InterferenciaDao;
import entidades.BaciasHidrograficas;
import entidades.Interferencia;

public class TesteGeometry {

	public static void main(String[] args) throws Exception {
		
		BaciasHidrograficas bacia = new BaciasHidrograficas();
		
		InterferenciaDao interDao = new InterferenciaDao();
		
		List<Interferencia> list = interDao.listInterferencia("");
		
		for (Interferencia i : list) {
			System.out.println(i.getInterBaciaFK().getBaciaShape());
		}
		
		System.out.println("bacia " + list.get(0).getInterBaciaFK().getBaciaNome());
		System.out.println("ponto " + list.get(0).getInterGeom());
		
		Geometry shape = list.get(0).getInterBaciaFK().getBaciaShape();
		
		//Geometry point = list.get(0).getInterGeom();
		
		GeometryFactory geoFac;
		Point p;
		
		// DESCOBERTO -15.7706185,-48.1551585
		geoFac = new GeometryFactory();
				
				p = geoFac.createPoint(new Coordinate(
						Double.parseDouble("-48.1551585"),
						Double.parseDouble("-15.7706185")
						));
				
				//p.setSRID(4674);
				
				// 
		System.out.println("ponto no descoberto");	
		System.out.println(p.intersects(shape));
		
		// CORUMBÁ -15.8975944,-48.1063675
		geoFac = new GeometryFactory();
		
		p = geoFac.createPoint(new Coordinate(
				Double.parseDouble("-48.1063675"),
				Double.parseDouble("-15.8975944")
				));
		
		p.setSRID(4674);
		
		System.out.println("ponto no corumbá");	
		System.out.println(p.intersects(shape));
		
		// PARANOÁ -15.7375541,-48.0510238
		geoFac = new GeometryFactory();
		
		p = geoFac.createPoint(new Coordinate(
				Double.parseDouble("-48.0510238"), 
				Double.parseDouble("-15.7375541")
				));
		
			p.setSRID(4674);
		
		// 
		System.out.println("ponto no paranoá");	
		System.out.println(p.intersects(shape));
		
		
		

	}

}
