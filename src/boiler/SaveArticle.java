package boiler;

import java.net.URL;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import beansdomain.ArticleBean;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.ImageExtractor;

public class SaveArticle {

	/**
	 * 処理
	 * 記事本体のみを保存（戻り値：Article_id）
	 * 画像変換をスレッドで実行（一括処理）
	 * 更新処理（Article_idでUpdateArticle）
	 */

	public static void main(String[] args) {
		try {
			def_extractor(2, "http://itpro.nikkeibp.co.jp/atcl/column/15/040800083/010400037/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 普通のエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public static void def_extractor (int user_id, String str_url) {
		try {
			BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
			article_extractor(user_id, str_url, extractor);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * とりあえずすべて取得するエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public void keep_extractor (int user_id, String str_url) {
		try {
			BoilerpipeExtractor extractor = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR;
			article_extractor(user_id, str_url, extractor);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	static private void article_extractor(int user_id, String str_url, BoilerpipeExtractor extra) throws Exception {
		//			URL url = new URL(
		//					"http://gigazine.net/news/20151201-mofur/");

		URL url = new URL(str_url);
		final BoilerpipeExtractor extractor = extra;

		final CommonExtractors htmlExtr;
		ImageExtractor imageExtr = ImageExtractor.INSTANCE;

		String text = CommonExtractors.ARTICLE_EXTRACTOR.getText(url);
		List<Image> image = imageExtr.process(url, extractor);

		// title 処理
		DOMParser parser = new DOMParser();
		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		System.out.println("SOURCE URL: " + str_url); //urlStrを表示

		parser.parse(str_url);
		Document document = parser.getDocument();
		NodeList nodeList = document.getElementsByTagName("title");

		String title = url.getHost();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			title = new String(element.getTextContent().toString().getBytes("UTF-8"), "UTF-8");
			System.out.println(title);
		}

		ArticleBean artBean = new ArticleBean();
		artBean.setUrl(str_url);
		artBean.setTitle(title);
		artBean.setBody(text);

		// これは本来いらないけど仕様上致し方ない
		int article_id = artBean.addArticle(user_id);

		// 画像のスレッド起動
		imageTrans it = new imageTrans(article_id, url, image);
		it.start();
		System.out.println(text);
	}
}
