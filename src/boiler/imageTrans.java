package boiler;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import beansdomain.ArticleBean;
import daodto.ImageDTO;
import de.l3s.boilerpipe.document.Image;

public class imageTrans extends Thread{

	private int article_id;
	private ArrayList<ImageDTO> imageDTO = new ArrayList<ImageDTO>();
	private URL url;
	private List<Image> image;

	public imageTrans(int article_id, URL url, List<Image> image) {
		this.article_id = article_id;
		this.url = url;
		this.image = image;
	}

	/**
	 * 画像を保存する。
	 * @param args
	 */
	public void run(){

		ArticleBean artBean = new ArticleBean();
		ImageDTO imDTO = null;

		String image_url = null;
		for (Image img : image) {
			imDTO = new ImageDTO();
			if (img.getSrc().startsWith("http://")) {
				image_url = img.getSrc();
				System.out.println("IMG:" + img.getSrc());
			} else {
				image_url = "http://" + url.getHost() + img.getSrc();
				System.out.println("IMG2:" + "http://" + url.getHost() + img.getSrc());
			}
			imDTO.setArticle_id(this.article_id);
			imDTO.setBlob_image(getImage(image_url));
			imDTO.setUri(image_url);
			this.imageDTO.add(imDTO);
		}
		// 更新処理

		try {
			artBean.addImage(this.article_id, this.imageDTO);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 画像のURLからイメージを縮小してイメージバッファーを返す
	 * @param str_url
	 * @return
	 */
	private byte[] getImage(String str_url) {

		byte[] new_ByteImage = null;
		try {
			URI uri = new URI(str_url);
			URL url = uri.toURL();

			BufferedImage buf_img = ImageIO.read(url);
			float hiritu = (float) buf_img.getHeight() / (float) buf_img.getWidth();
			int width = 800;
			int height = Math.round((float) width * hiritu);

			System.out.println("takasa:" + height + "hiritu:" + hiritu);

			BufferedImage new_img = rescale(buf_img, width, height);

			System.out.println("img:" + buf_img.getWidth());
			System.out.println("new_img:" + new_img.getWidth());
			System.out.println("new_img:" + new_img.getHeight());

			//ImageIO.write(new_img, "jpg", new FileOutputStream("C:\\Users\\121013\\Pictures\\test.jpg"));
			new_ByteImage = getBytesFromImage(new_img, "jpg");

		} catch (URISyntaxException e) {
			System.err.println(e);
		} catch (MalformedURLException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		return new_ByteImage;
	}

	/**
	* イメージ→バイト列に変換
	* @param img イメージデータ
	* @param format フォーマット名
	* @return バイト列
	*/
	public static byte[] getBytesFromImage(BufferedImage img, String format) throws IOException {

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
	public static BufferedImage getImageFromBytes(byte[] bytes) throws IOException {
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
	private BufferedImage rescale(BufferedImage srcImage, int nw, int nh) {
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
