package principal;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoData {
	
	public String formatarData (Timestamp timestamp) {
		
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);

	}
	
    public String formatarSomenteData (Date date) {
		
		return new SimpleDateFormat("dd/MM/yyyy").format(date);

	}
	
}
