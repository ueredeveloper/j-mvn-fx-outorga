package adasa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import dao.BaciasHidrograficasDao;
import dao.EnderecoDao;
import entidades.BaciasHidrograficas;
import entidades.Endereco;

public class PoligonoContendoCroquiEndereco {
	
	
	String strCroquiEndereco;
	
	public static PoligonoContendoCroquiEndereco controladorOutorga;
	
	
	public void capturarCroquiEndereco (String strCroquiEndereco) {
		
		this.strCroquiEndereco = strCroquiEndereco;
		
		System.out.println(strCroquiEndereco);
		
		System.out.println("Classe de Teste - Poligono Contendo Croqui Endereco ");
		
		
		
	}


	public static void main(String[] args) {


		String strCE1 = 
				";-16.034304896061276,-48.10749656109925;-16.035882541161197,-48.10817247777101;-16.034768910621985,-48.1112516537201;-16.033789323060493,-48.11126238255616";
		
		String strCE2 = 
				";-16.033982115287643,-48.105890487876934;-16.034435819159988,-48.10277912541966;-16.035528828794227,-48.10380909368138;-16.035219487052938,-48.10666296407322";
		
		
		String ss = "-16.034304896061276,-48.10749656109925";
		
		
		
		String [] arrayLatLon = strCE1.split(";");
		
		for (String s : arrayLatLon) {
		
			if (! s.isEmpty()) {
				
				String [] ss1 = s.split(",");
				
				System.out.println(ss1[0]);
				System.out.println(ss1[1]);
				
			}
				
		}
		
		EnderecoDao endDao = new EnderecoDao();
		
		
		Endereco end = new Endereco();
		
		GeometryFactory geoFac = new GeometryFactory(new PrecisionModel(), 4674);
		// -16.033982115287643,-48.105890487876934
		Point p = geoFac.createPoint(new Coordinate(
				Double.parseDouble("-48.10749656109925"), 
				Double.parseDouble("-16.033982115287643"
				)));
		

		end.setEndGeom(p);
		end.setEndLogradouro("Bia Kicks");
		
		
		
		endDao.salvarEndereco(end);
		
		
		// df
		
		// ;-15.498005731427963,-48.205610046250854;-15.498005731427963,-47.362409362657104;-16.039862617247497,-47.378888854844604;-16.037222943017177,-48.282514343125854
		// ;-15.772814135425186,-47.94114435493441;-15.773474925646607,-47.9378827887723;-15.78470802998603,-47.942260153884604;-15.78388208763071,-47.94526422798128
		
		
		/*
		 final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4674);
	
		    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
		    
		    points.add(new Coordinate(-48.205610046250854, -15.498005731427963)); //-15.498005731427963,-48.205610046250854
		    points.add(new Coordinate(-47.362409362657104, 15.498005731427963)); // -15.498005731427963,-47.362409362657104
		    points.add(new Coordinate(-47.378888854844604, -16.039862617247497)); // -16.039862617247497,-47.378888854844604;
		    points.add(new Coordinate(-48.282514343125854, -16.037222943017177)); // -16.037222943017177,-48.282514343125854
		   
		    points.add(new Coordinate(-48.205610046250854, -15.498005731427963)); //-15.498005731427963,-48.205610046250854
		    
		    final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
		        .toArray(new Coordinate[points.size()])), gf), null);
		    
		    
		  //  LinearRing ll = new LinearRing(, new PrecisionModel(), srid);

		   // final Coordinate coord = new Coordinate(0, 0);
		    //final Point point = gf.createPoint(coord);

		    //System.out.println(point.within(polygon));
		     
		     */
		   
		    /*
		 Endereco end = new Endereco ();
		 
		 end.setEndCroqui(polygon);
		 
		 
		 EnderecoDao endDao = new EnderecoDao();
		 
		 endDao.salvarEndereco(end);
		 */

		// endDao = new EnderecoDao();
		
		    List<Endereco> list = endDao.listarEndereco("Bia Kicks");
		    
		    for (Endereco e : list) {
		    	
		    	Point point = (Point) e.getEndGeom();
		    	
		    	System.out.println(point.getSRID());
		    	
		    		
		    	/*
			    	 final Polygon pol = (Polygon) e.getEndCroqui();
			    	 
			    	 System.out.println("numero de pontos " + pol.getNumPoints());
			    	 System.out.println("area " + pol.getArea());
			    	 System.out.println(" modelo de precisao " + pol.getPrecisionModel());
			    	 
			    	 System.out.println("srid " + pol.getSRID());
			    	 
			    	 Coordinate[] l = pol.getCoordinates();
			    	 
			    	 for (Coordinate c : l) {
			    		 
			    		 System.out.println("lon " + c.x);
			    		 System.out.println(" lat " + c.y);
			    	 }
			    	 */
			    	
			
		    /*
			    	 BaciasHidrograficasDao baciasDao = new BaciasHidrograficasDao();
					  	List<BaciasHidrograficas> baciasList = baciasDao.listarBaciasHidrograficas("Rio Corumbá");
			    	 
					    
					    for (BaciasHidrograficas b : baciasList) {
					    	
					    		
						    	 final Polygon p = (Polygon) b.getBaciaShape();
						    	 
						    	 System.out.println("numero de pontos " + p.getNumPoints());
						    	 System.out.println("area " + p.getArea());
						    	 System.out.println(" modelo de precisao " + p.getPrecisionModel());
						    	 
						    	 System.out.println("length " + p.getLength());
						    	 
						    	 System.out.println("srid " + p.getSRID());
						    	 
						    	 Coordinate[] l = p.getCoordinates();
						    	 
						    	 for (Coordinate c : l) {
						    		 
						    		 System.out.println("lon " + c.x);
						    		 System.out.println(" lat " + c.y);
						    	 }
			    	
		    	  */
		    }
		  
		   
		 
		 
		 
		    
		
	
	}

}
