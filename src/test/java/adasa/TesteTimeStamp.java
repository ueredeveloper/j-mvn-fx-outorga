package adasa;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TesteTimeStamp {

	public static void main(String[] args) {


		
		String s = Timestamp.valueOf((LocalDateTime.now())).toString();
		
		System.out.println(s);
		
		s = s.replace(":", "");
		s = s.replace("-", "");
		s = s.replace(".", "");
		s = s.replace(" ", "");
		
		System.out.println(s);

	}

}
