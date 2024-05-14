package RetirarTagsEstranhasAoHTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ReplaceJSoup {

	public static void main(String[] args) {


		String html = "<font>fsdfs<font>dfsdf</font>dasdasd</font>";
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("font");


		// rename all 'font'-tags to 'span'-tags, will also keep attributs etc.
		elements.tagName("span");

		System.out.println(doc.html());

	}

}
