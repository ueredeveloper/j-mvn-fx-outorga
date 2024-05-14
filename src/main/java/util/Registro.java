package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class Registro {


	public Properties lerRegistro () throws IOException {

    	File file =  new File ("user.properties");
    	
    	InputStream inputStream = null;
    	
		inputStream = new FileInputStream(file);
           
		Properties prop = new Properties();
		    prop.load(inputStream);

		inputStream.close();
		
		return prop;

	}
	
	public void salvarRegistro (Properties pro) throws URISyntaxException, IOException {

		OutputStream output = null;
	
        try {
 
            output = new FileOutputStream("user.properties");

            pro.store(output, null);
          
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
        }
       

	}

}
