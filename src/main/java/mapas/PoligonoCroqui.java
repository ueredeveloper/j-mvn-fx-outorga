package mapas;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import entidades.Endereco;

public class PoligonoCroqui {
	
	public Polygon obterPoligonoCroqui (String strCroquiEndereco) {
		
		// separar as coordenadas vidas do google api
		String [] arrayLatLon = strCroquiEndereco.split(";");

		// criacao de uma fabrica de coordenadas geometry
		GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4674);
		// array de pontos do poligono
	    ArrayList<Coordinate> points = new ArrayList<Coordinate>();
	  
		// capturar o primeiro ponto do poligono e repetir no final
	    String [] strPoligonoUltimoPonto;

	 		strPoligonoUltimoPonto = arrayLatLon[1].split(",");
	 		
	 		System.out.println(arrayLatLon[0]);
	 		
	 		System.out.println(arrayLatLon[1]);
	 		

	 	// o loop comeca 1 pois a arrayLatLon tem o indice zero nulo por causa do split
		for (int i = 1; i<arrayLatLon.length; i++) {
		
				String [] str = arrayLatLon[i].split(",");
				
				System.out.println("coordenadas for");
				System.out.println(str[1] + "," + str[0]);
				
				// preencher os pontos do poligono           1 - longitude               0 - latitude
				points.add(new Coordinate(Double.parseDouble(str[1]), Double.parseDouble(str[0])));
				
		}
		System.out.println(strPoligonoUltimoPonto[1] + "," + strPoligonoUltimoPonto[0]);
		
		// adicionar o ultimo ponto do poligono que e o mesmo que o primeiro
		points.add(new Coordinate(Double.parseDouble(strPoligonoUltimoPonto[1]), Double.parseDouble(strPoligonoUltimoPonto[0])));
		// criacao do poligono
		Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
		        .toArray(new Coordinate[points.size()])), gf), null);
		
		
		return polygon;
	}
	

	public String setarPoligono (Endereco endereco) {

		Polygon pol = (Polygon) endereco.getEndCroqui();

		Coordinate[] l = pol.getCoordinates();

		StringBuilder strShapeEndereco = new StringBuilder();

		for (Coordinate c : l) {

			//System.out.println("lon " + c.x);
			//System.out.println(" lat " + c.y);

			strShapeEndereco.append("|" + c.y + "," + c.x);


		}

		return strShapeEndereco.toString();

	}


}
