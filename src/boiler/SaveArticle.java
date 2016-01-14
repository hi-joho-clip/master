package boiler;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
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

	//	public static void main(String[] args) {
	//		try {
	//			def_extractor(2, "http://itpro.nikkeibp.co.jp/atcl/column/15/040800083/010400037/");
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 普通のエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public boolean def_extractor(int user_id, String str_url) {
		boolean flag = false;
		try {
			if (isConAndTimeOut(str_url)) {
				BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
				if (article_extractor(user_id, str_url, extractor) >= 1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * とりあえずすべて取得するエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public boolean keep_extractor(int user_id, String str_url) {
		boolean flag = false;

		try {
			if (isConAndTimeOut(str_url)) {
				BoilerpipeExtractor extractor = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR;
				if (article_extractor(user_id, str_url, extractor) >= 1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private int article_extractor(int user_id, String str_url, BoilerpipeExtractor extra) throws Exception {
		//			URL url = new URL(
		//					"http://gigazine.net/news/20151201-mofur/");

		URL url = new URL(str_url);
		final BoilerpipeExtractor extractor = extra;

		final CommonExtractors htmlExtr;
		ImageExtractor imageExtr = ImageExtractor.INSTANCE;

		String text = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR.getText(url);
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

		for (Image img : image) {
			System.out.println("sa:" + img.getSrc());
		}

		// 画像のスレッド起動
		System.out.println(image.size());
		imageTrans it = new imageTrans(article_id, url, image);

		it.start();
		System.out.println(text);

		return article_id;
	}

	/**
	 * UTF-8判定とタイムアウト設定
	 * バイナリ型のファイル排除とサイズの大きいサイト用
	 * 1ページ60MBまでで、タイムアウト10秒、コネクション10秒、
	 * @param url
	 */
	public static boolean isConAndTimeOut(String str_url) {

		boolean flag = false;
		try {
			// 60MBまで
			System.out.println("conURL:" + str_url);
			byte[] bytes = new byte[62914560];

			URL url = new URL(str_url);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// タイムアウトは10秒
			urlConnection.setConnectTimeout(10 * 1000); // 追加
			urlConnection.setReadTimeout(10 * 1000); // 追加
			System.out.println(urlConnection.getContentType());

			if (urlConnection.getContentType().indexOf("text/html") != -1) {
				flag = true;
			}

			InputStream in = urlConnection.getInputStream();

			int readBytes = 0;
			while ((readBytes = in.read(bytes, 0, bytes.length)) > 0) {
				// BOF対策
				if (readBytes > 600000) {
					flag = false;
					break;
				}
			}

			// 読み込みバイト数が1バイト以上かつ、UTF-8の文字コードの場合
			if (readBytes > 0 && isUTF8(bytes)) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * UTF-8の判定
	 * @param src
	 * @return
	 */
	public static boolean isUTF8(byte[] src)
	{
		try {
			byte[] tmp = new String(src, "UTF8").getBytes("UTF8");
			return Arrays.equals(tmp, src);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}

	public static boolean isSJIS(byte[] src)
	{
		try {
			byte[] tmp = new String(src, "Shift_JIS").getBytes("Shift_JIS");
			return Arrays.equals(tmp, src);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
}
