package TestServlet;

import java.net.URL;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.ImageExtractor;



public class BoilerPipeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		//article_extractor();
		keep_everything_extractor();

	}

//	static public void sub1() throws Exception{
//		URL url = new URL(
//				"http://www.spiegel.de/wissenschaft/natur/0,1518,789176,00.html");
//		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//
//		//final HtmlArticleExtractor htmlExtr = HtmlArticleExtractor.INSTANCE;
//
//		String html = htmlExtr.process(extractor, url);
//
//		System.out.println(html);
//	}

	static public void article_extractor() throws Exception {
//		URL url = new URL(
//				"http://gigazine.net/news/20151201-mofur/");
		String str_url = "https://getpocket.com/a/read/1159948009";
		URL url = new URL(str_url);
		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

		System.out.println(url.getHost());
		final CommonExtractors htmlExtr ;
		ImageExtractor imageExtr = ImageExtractor.INSTANCE;

		String text = CommonExtractors.ARTICLE_EXTRACTOR.getText(url);

		DOMParser parser = new DOMParser();
		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		System.out.println("SOURCE URL: " + url); //urlStrを表示

		parser.parse(str_url);
		Document document =parser.getDocument();
		NodeList nodeList = document.getElementsByTagName("title");


		for(int i=0; i < nodeList.getLength(); i++){
		    Element element = (Element)nodeList.item(i);
		    String eucjpStr = new String(element.getTextContent().toString().getBytes("UTF-8"), "UTF-8");
		    System.out.println(eucjpStr);
		}


		List<Image> image = imageExtr.process(url, extractor);

		for (Image img : image) {
			if (img.getSrc().startsWith("http://")) {
				System.out.println("IMG:" + img.getSrc());
			} else {
				System.out.println("IMG2:" + "http://" + url.getHost() + img.getSrc());
			}
		}
		System.out.println(text);
	}

	static public void keep_everything_extractor() throws Exception {
		URL url = new URL(
				"https://getpocket.com/a/read/1159948009");
		final BoilerpipeExtractor extractor = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR;

		final CommonExtractors htmlExtr ;
		ImageExtractor imageExtr = ImageExtractor.INSTANCE;

		String text = CommonExtractors.ARTICLE_EXTRACTOR.getText(url);
		List<Image> image = imageExtr.process(url, extractor);

		for (Image img : image) {
			System.out.println("IMG:" + img.getSrc());
		}
		System.out.println(text);
	}
}
