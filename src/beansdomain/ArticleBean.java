package beansdomain;

import java.util.ArrayList;
import java.util.Date;

import daodto.ArticleDAO;
import daodto.ArticleDTO;
import daodto.ImageDTO;

public class ArticleBean {
	private int article_id;
	private String title;
	private String body;
	private String url;
	private Date created;
	private Date modified;
	private String share_url;
	private Date share_expior;
	private int mylist_id;
	private ArticleDTO articleDTO;
	private ArticleDAO articleDAO;
	private ArrayList<ArticleDTO> article;
	private int image_id;
	private String uri;
	private byte[] thum;
	private byte[] blob_image;
//	private ImageDTO imageDTO;
	private ArrayList<ImageDTO> imageListDTO;

	/**
	 * 記事の追加
	 * @return
	 * @throws Exception
	 */
	public boolean addArticle(int user_id, ArrayList<ImageDTO> img) throws Exception {
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		return this.articleDAO.add(this.articleDTO, img, user_id);
	}

	/**
	 * 記事の削除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteArticle() throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.delete(this.article_id);

	}

	/**
	 * 記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewArticleList(int user_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.lists(user_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * お気に入りの記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewFavArticleList(int user_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.FavoriteLists(user_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * タグ内でお気に入りした記事一覧
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewTagFavArticleList(int user_id, ArrayList<String> tag_body_list) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.searchFavoriteTagLists(user_id, tag_body_list);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * 記事の更新
	 * @return
	 * @throws Exception
	 */
	public boolean updateArticle() throws Exception {
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		return this.articleDAO.update(this.articleDTO);
	}

	/**
	 * 記事にお気に入りとして追加
	 * @return
	 * @throws Exception
	 */
	public boolean addFavorite(int user_id) throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.addFavorite(this.article_id, user_id);//true=成功、false=失敗
	}

	/**
	 * お気に入りの解除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFavorite(int user_id) throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.deleteFavorite(this.article_id, user_id);
	}

	/**
	 * シェア記事の削除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteShareArticle() throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.deleteShare(this.article_id);
	}

	/**
	 * 記事にシェアURLを追加
	 * @return
	 * @throws Exception
	 */
	public boolean addShareArticle() throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.addShareURL(this.share_url, this.article_id);
	}

	/**
	 * 記事を表示（要修正：なぜか画像毎に記事のデータが返される
	 * 1221修正完了
	 * @return
	 * @throws Exception
	 **/
	public ArticleBean viewArticle() throws Exception {
		ArticleBean articleBean = new ArticleBean();
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		articleDTO = articleDAO.view(this.articleDTO.getArticle_id());

		// 記事共通部分
		articleBean.setArticle_id(articleDTO.getArticle_id());
		articleBean.setBody(articleDTO.getBody());
		articleBean.setTitle(articleDTO.getTitle());
		articleBean.setUrl(articleDTO.getUrl());
		articleBean.setCreated(articleDTO.getCreated());
		articleBean.setModified(articleDTO.getModified());
		// サムネイル
		articleBean.setThum(articleDTO.getThum());
		articleBean.setShare_url(articleDTO.getShare_url());
		articleBean.setShare_expior(articleDTO.getShare_expior());
		articleBean.setMylist_id(articleDTO.getMylist_id());
		articleBean.setImageListDTO(articleDTO.getImageDTO());

		//画像のリスト

		return articleBean;
	}

	/**
	 * シェアしている記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewShareArticleList(int user_id, int friend_user_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.viewShareArticle(user_id, friend_user_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * 記事にタグを追加
	 * @return
	 * @throws Exception
	 */
	public boolean addArticleTag(int user_id, ArrayList<String> tag_body_list) throws Exception {
		this.articleDAO = new ArticleDAO();
		return this.articleDAO.addTag(this.article_id, user_id, tag_body_list);
	}

	/**
	 * 特定のタグの記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewTag(ArrayList<String> tag_body_list, int user_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.viewTagArticle(tag_body_list, user_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	public void setArticleDTO() {
		this.articleDTO = new ArticleDTO();
		this.articleDTO.setArticle_id(this.article_id);
		this.articleDTO.setTitle(this.title);
		this.articleDTO.setBody(this.body);
		this.articleDTO.setUrl(this.url);
		this.articleDTO.setCreated(this.created);
		this.articleDTO.setModified(this.modified);
		this.articleDTO.setShare_url(this.share_url);
		this.articleDTO.setMylist_id(this.mylist_id);
		this.articleDTO.setShare_expior(this.share_expior);
	}

	// なんかあったら戻してください

//	public void setImageDTO() {
//		this.imageDTO = new ImageDTO();
//		this.imageDTO.setImage_id(this.image_id);
//		this.imageDTO.setUri(this.uri);
//		this.imageDTO.setBlob_image(this.blob_image);
//	}
//
//	public ImageDTO getImageDTO() {
//		this.imageDTO = new ImageDTO();
//		this.imageDTO.setImage_id(this.image_id);
//		this.imageDTO.setUri(this.uri);
//		this.imageDTO.setBlob_image(this.blob_image);
//		return imageDTO;
//	}

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

	public String getUri() {
		return uri;
	}

	public byte[] getBlob_image() {
		return blob_image;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setBlob_image(byte[] blob_image) {
		this.blob_image = blob_image;
	}

	public byte[] getThum() {
		return thum;
	}

	public void setThum(byte[] thum) {
		this.thum = thum;
	}

	public int getMylist_id() {
		return mylist_id;
	}

	public void setMylist_id(int mylist_id) {
		this.mylist_id = mylist_id;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public ArrayList<ImageDTO> getImageListDTO() {
		return imageListDTO;
	}

	public void setImageListDTO(ArrayList<ImageDTO> imageListDTO) {
		this.imageListDTO = imageListDTO;
	}

}
