package daodto;

import java.util.ArrayList;
import java.util.Date;

public class ArticleDTO {
	private int article_id;
	private String title;
	private String body;
	private String url;
	private Date created;
	private String share_url;
	private Date share_expior;
	private int mylist_id;
	private ArrayList<ImageDTO> imageDTO;


	public ArticleDTO(){
		this.article_id=0;
		this.title=null;
		this.body=null;
		this.url=null;
		this.created=null;
		this.share_url=null;
		this.share_expior=null;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public Date getShare_expior() {
		return share_expior;
	}

	public void setShare_expior(Date share_expior) {
		this.share_expior = share_expior;
	}

	public ArrayList<ImageDTO> getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ArrayList<ImageDTO> imageDTO) {
		this.imageDTO = imageDTO;
	}

	public int getMylist_id() {
		return mylist_id;
	}

	public void setMylist_id(int mylist_id) {
		this.mylist_id = mylist_id;
	}

}