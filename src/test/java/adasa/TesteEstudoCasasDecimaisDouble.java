package adasa;

public class TesteEstudoCasasDecimaisDouble {

	public static void main(String[] args) {


		double x = 123.123;
		//System.out.printf( "%.0f", x ); // limtar double a nao mostrar casas decimais
		
		
		String s = "2.3234375";
		
		
		
		String output = String.format("%.0f", 2.3234375);
		
		System.out.println(output);

	}

}
