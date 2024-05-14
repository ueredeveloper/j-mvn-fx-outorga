package RetirarTagsEstranhasAoHTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AdicionarAlgoAntesDeFecharTagFinalHTML {

	public static void main(String[] args) {


		String strHTML = " <!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<title>Page Title</title>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"\r\n" + 
					"<h1>This is a Heading</h1>\r\n" + 
					"<p>This is a paragraph.</p>\r\n" + 
					"\r\n" + 
					"</body>\r\n" + 
					"</html>\r\n" 
		
			;
		
		Document docHTML = Jsoup.parse(strHTML);
		
		Element htmlTag = docHTML.select("body").last();
		
		htmlTag.append("<b>Vida</b>");
		
		System.out.println(docHTML.html());
		
		
		
		
	}

}
