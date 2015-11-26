package daodto;

public class ImageDTO {
	private int image_id;
	private int article_id;
	private String uri;
	private byte[] blob_image;

	public ImageDTO(){
		this.image_id=0;
		this.article_id=0;
		this.uri=null;
		this.blob_image=null;

	}

	public int getImage_id() {
		return image_id;
	}

	public String getUri() {
		return uri;
	}

	public byte[] getBlob_image() {
		return blob_image;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setBlob_image(byte[] blob_image) {
		this.blob_image = blob_image;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}


}
