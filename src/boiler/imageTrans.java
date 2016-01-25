package boiler;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import beansdomain.ArticleBean;
import daodto.ImageDTO;
import de.l3s.boilerpipe.document.Image;

public class imageTrans extends Thread {

	private int article_id;
	private int user_id;
	private ArrayList<ImageDTO> imageDTO = new ArrayList<ImageDTO>();
	private URL url;
	private List<Image> image;

	public imageTrans(int article_id, URL url, List<Image> image, int user_id) {
		this.article_id = article_id;
		this.url = url;
		this.image = image;
		this.user_id = user_id;
	}

	public static void main(String[] args) {

		getImage("http://pc.watch.impress.co.jp/img/pcw/docs/738/654/2_s.jpg");
	}

	/**
	 * 画像を保存する。
	 * @param args
	 */
	public void run() {

		ArticleBean artBean = new ArticleBean();
		ImageDTO imDTO = null;

		String image_url = null;
		Boolean flag = true;
		ImageDTO thumDTO = null;
		//System.out.println(image.size());
		for (Image img : image) {
			imDTO = new ImageDTO();
			if (img.getSrc().startsWith("http://")) {
				image_url = img.getSrc();
				//System.out.println("IMG:" + img.getSrc());
			} else {
				image_url = "http://" + url.getHost() + img.getSrc();
				//System.out.println("IMG2:" + "http://" + url.getHost() + img.getSrc());
			}

			//System.out.println(extentHantei("URL:" + image_url));
			if (extentHantei(image_url) != "FALSE") {
				// nullだったら画像が小さすぎる
				imDTO.setBlob_image(getImage(image_url));
				System.out.println("setBlob:" + imDTO.getBlob_image() == null);
				if (imDTO.getBlob_image() != null) {
					imDTO.setArticle_id(this.article_id);
					imDTO.setExtenstion(extentHantei(image_url));
					//imDTO.setBlob_image(getImage(image_url));
					imDTO.setUri(image_url);
					this.imageDTO.add(imDTO);
					if (flag) {
						thumDTO = imDTO;
						flag = false;
					}
				}
			}
			// 拡張子を入れちゃえ
		}
		// 更新処理

		try {
			// サムネイルの保存
			ArticleBean art = new ArticleBean();
			art.setArticle_id(article_id);
			art.setThum(thumDTO.getBlob_image());
			art.updateThum();
			// 画像の保存
			artBean.addImage(this.article_id, this.imageDTO);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 拡張子の対応確認
	 * 対応できる拡張子は、jpg,pngのみ
	 */
	public static String extentHantei(String src) {
		if (src.indexOf(".jpg") != -1) {
			return "jpg";
		} else if (src.indexOf(".JPG") != -1) {
			return "jpg";
		} else if (src.indexOf(".png") != -1) {
			return "png";
		} else if (src.indexOf(".PNG") != -1) {
			return "png";
		} else if (src.indexOf(".jpeg") != -1) {
			return "jpg";
		} else if (src.indexOf(".JPEG") != -1) {
			return "jpg";
		} else if (src.indexOf(".gif") != -1) {
			return "gif";
		} else if (src.indexOf(".GIF") != -1) {
			return "gif";
		} else {
			return "FALSE";
		}
	}

	/**
	 * 画像のURLからイメージを縮小してイメージバッファーを返す
	 * @param str_url
	 * @return
	 */
	public static byte[] getImage(String str_url) {

		byte[] new_ByteImage = null;

		// 未対応の場合は処理せずナルを返す（念のため）
		if (!extentHantei(str_url).equals("FALSE")) {
			try {
				URI uri = new URI(str_url);
				URL url = uri.toURL();
				BufferedImage buf_img = ImageIO.read(url);

				if (buf_img.getHeight() >= 200) {

					float hiritu = (float) buf_img.getHeight() / (float) buf_img.getWidth();
					int width = 800;
					int height = Math.round((float) width * hiritu);

					//	System.out.println("takasa:" + height + "hiritu:" + hiritu);

					BufferedImage new_img = rescale(buf_img, width, height);

					//				System.out.println("img:" + buf_img.getWidth());
					//				System.out.println("new_img:" + new_img.getWidth());
					//				System.out.println("new_img:" + new_img.getHeight());

					//ImageIO.write(new_img, "gif", new FileOutputStream("C:\\Users\\121013\\Pictures\\testunfe.jpg"));
					new_ByteImage = getBytesFromImage(new_img, extentHantei(str_url));

					//			} catch (URISyntaxException e) {
					//				e.printStackTrace();
					//			} catch (MalformedURLException e) {
					//				e.printStackTrace();
					//			} catch (IOException e) {
					//				e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new_ByteImage;
	}

	/**
	* イメージ→バイト列に変換
	* @param img イメージデータ
	* @param format フォーマット名
	* @return バイト列
	*/
	private static byte[] getBytesFromImage(BufferedImage img, String format) throws IOException {

		if (format == null) {
			format = "jpg";
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, format, baos);
		return baos.toByteArray();
	}

	/**
	* バイト列→イメージを作成
	* @param bytes
	* @return イメージデータ
	*/
	private BufferedImage getImageFromBytes(byte[] bytes) throws IOException {
		ByteArrayInputStream baos = new ByteArrayInputStream(bytes);
		BufferedImage img = ImageIO.read(baos);
		return img;
	}

	/**
	 * 画像を縮小（リサイズ）する
	 * @param srcImage
	 * @param nw
	 * @param nh
	 * @return
	 */
	private static BufferedImage rescale(BufferedImage srcImage, int nw, int nh) {
		BufferedImage dstImage = null;
		if (srcImage.getColorModel() instanceof IndexColorModel) {
			dstImage = new BufferedImage(nw, nh, srcImage.getType(), (IndexColorModel) srcImage.getColorModel());
		} else {
			if (srcImage.getType() == 0) {
				dstImage = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR_PRE);
			} else {
				dstImage = new BufferedImage(nw, nh, srcImage.getType());
			}
		}

		double sx = (double) nw / srcImage.getWidth();
		double sy = (double) nh / srcImage.getHeight();
		AffineTransform trans = AffineTransform.getScaleInstance(sx, sy);

		if (dstImage.getColorModel().hasAlpha() && dstImage.getColorModel() instanceof IndexColorModel) {
			int transparentPixel = ((IndexColorModel) dstImage.getColorModel()).getTransparentPixel();
			for (int i = 0; i < dstImage.getWidth(); ++i) {
				for (int j = 0; j < dstImage.getHeight(); ++j) {
					dstImage.setRGB(i, j, transparentPixel);
				}
			}
		}

		Graphics2D g2 = (Graphics2D) dstImage.createGraphics();
		g2.drawImage(srcImage, trans, null);
		g2.dispose();
		return dstImage;
	}

}
