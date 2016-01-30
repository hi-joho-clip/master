package boiler;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import beansdomain.ArticleBean;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.ImageExtractor;

public class SaveArticle {

	static byte[] bytes = new byte[31457280];

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

	private String encodeBR(String str) {

		String value = null;

		value = str.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
		value = value.replaceAll("\n", "<br />");
		// OutSupport.outで出力(escapeXml=true でエスケープ)

		System.out.println("nakami:" + value);

		return value;
	}

	/**
	 * 普通のエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public int def_extractor(int user_id, String str_url) {
		int art_id = 0;
		try {
			//if (isConAndTimeOut(str_url)) {
			BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
			art_id = article_extractor(user_id, str_url, extractor);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return art_id;
	}

	/**
	 * とりあえずすべて取得するエクストラクター
	 * @param user_id
	 * @param str_url
	 */
	public int keep_extractor(int user_id, String str_url) {
		int art_id = 0;

		try {
			//if (isConAndTimeOut(str_url)) {
			BoilerpipeExtractor extractor = CommonExtractors.KEEP_EVERYTHING_EXTRACTOR;
			art_id = article_extractor(user_id, str_url, extractor);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return art_id;
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
		//
		//		// title 処理
		//		DOMParser parser = new DOMParser();
		//		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		//		System.out.println("SOURCE URL: " + str_url); //urlStrを表示

		//		parser.parse(str_url);
		System.out.println("タイトル：" + getTitle(str_url));
		//		Document document = parser.getDocument();
		//		NodeList nodeList = document.getElementsByTagName("title");

		//		String title = url.getHost();
		//		for (int i = 0; i < nodeList.getLength(); i++) {
		//			Element element = (Element) nodeList.item(i);
		//			System.out.println(element.getTextContent());
		//			System.out.println("utf8:" + isUTF8(element.getTextContent().toString().getBytes()));
		//			System.out.println("sjis:" + isSJIS(element.getTextContent().toString().getBytes()));
		String title = new String(getTitle(str_url));
		//			System.out.println(title);
		//		}

		ArticleBean artBean = new ArticleBean();
		artBean.setUrl(str_url);
		artBean.setTitle(title);
		artBean.setBody(encodeBR(text));

		if (image.size() == 0) {
			// 画像がなければデフォルト
			//artBean.setThum(createThum());
		}

		// これは本来いらないけど仕様上致し方ない
		int article_id = artBean.addArticle(user_id);

		System.out.println("ガゾウスウ:" + image.size());
		for (Image img : image) {
			System.out.println("sa:" + img.getSrc());
		}

		// 画像のスレッド起動
		System.out.println(image.size());
		if (image.size() != 0) {
			imageTrans it = new imageTrans(article_id, url, image, user_id);

			it.start();
		} else {
			// thum
		}
		System.out.println(text);

		return article_id;
	}

	/**
	 * サムネを作るよ
	 * @return
	 */
	public byte[] createThum() {
		BufferedImage readImage = null;

		ByteArrayOutputStream baos = null;
		try {

			URI uri = new URI("http://localhost:8080/clipMaster/img/thum.jpg");
			URL url = uri.toURL();
			BufferedImage buf_img = ImageIO.read(url);
			baos = new ByteArrayOutputStream();
			//readImage = ImageIO.read(new File("http://localhost:8080/clipMaster/img/thum.jpg"));
			ImageIO.write(buf_img, "jpg", baos);
		} catch (Exception e) {
			e.printStackTrace();
			readImage = null;
		}
		return baos.toByteArray();

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
			// 30MBまで
			System.out.println("conURL:" + str_url);
			//byte[] bytes = new byte[31457280];

			URL url = new URL(str_url);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// タイムアウトは10秒
			urlConnection.setConnectTimeout(10 * 1000); // 追加
			urlConnection.setReadTimeout(10 * 1000); // 追加
			System.out.println(urlConnection.getContentType());

			if (urlConnection.getContentType() != null) {
				if (urlConnection.getContentType().indexOf("text/html") != -1) {
					flag = true;
				}
			}

			InputStream in = urlConnection.getInputStream();

			int readBytes = 0;
			if (in != null) {
				while ((readBytes = in.read(bytes, 0, bytes.length)) > 0) {
					// BOF対策
					if (readBytes > 600000) {
						flag = false;
						break;
					}
				}
			}

			// 読み込みバイト数が1バイト以上かつ、UTF-8の文字コードの場合
			if (readBytes > 0 && isUTF8(bytes)) {
				flag = true;
			}
			// bytesを解放
			bytes = null;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
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

	public static String getTitle(String page_url) throws Exception {
		//アクセスしたいページpage_url
		URL url = new URL(page_url);
		URLConnection conn = url.openConnection();

		System.out.println(isSJIS(conn.getInputStream().toString().getBytes()));

		String charset = "charset=UTF-8";
		System.out.println("getCon:" + conn.getContentType());
		if (conn.getContentType() != null) {
			// スプリット後の配列が1（2個目）以上ある場合
			if (conn.getContentType().split(";").length > 1) {
				charset = Arrays.asList(conn.getContentType().split(";")).get(1);
			} else if (isSJIS(conn.getInputStream().toString().getBytes())) {
				charset = "charset=Shift_JIS";
			}
		} else if (isSJIS(conn.getInputStream().toString().getBytes())) {
			charset = "charset=Shift_JIS";
		}

		String encoding = Arrays.asList(charset.split("=")).get(1);
		System.out.println("splength:" + conn.getContentType().split(";").length);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
		StringBuffer response = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null)
			response.append(line + "\n");
		in.close();

		Pattern title_pattern1 = Pattern.compile("<title>([^<]+)</title>", Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = title_pattern1.matcher(response.toString());
		if (matcher1.find()) {
			return matcher1.group(1);
		}
		return null;
	}

}
