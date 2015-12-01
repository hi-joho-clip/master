package TestServlet;

import java.net.URL;
import java.util.List;

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
		sub2();

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

	static public void sub2() throws Exception {
		URL url = new URL(
				"http://gigazine.net/news/20151201-mofur/");
		final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

		final CommonExtractors htmlExtr ;
		ImageExtractor imageExtr = ImageExtractor.INSTANCE;

		String text = CommonExtractors.ARTICLE_EXTRACTOR.getText(url);
		List<Image> image = imageExtr.process(url, extractor);

		for (Image img : image) {
			System.out.println(img);
		}
		System.out.println(text);
	}
}
