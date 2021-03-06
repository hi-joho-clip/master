package beansdomain;

import java.util.ArrayList;
import java.util.Date;

import daodto.ArticleDAO;
import daodto.ArticleDTO;
import daodto.ImageDTO;

public class ArticleBean {
	private int article_id;
	private String username;
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
	private String extension;
	private ImageDTO imageDTO;
	private boolean favflag;
	private int share_id;
	private ArrayList<ImageDTO> imageListDTO;
	private ArrayList<TagBean> tagBeans;

	/**
	 * 記事の追加
	 * @return
	 * @throws Exception
	 */
	public int addArticle(int user_id) throws Exception {
		this.articleDAO = new ArticleDAO();
		setArticleDTO();

		int mylist_id = this.articleDAO.getMylistID(user_id);

		if (mylist_id != 0) {
			System.out.println("MYLISTID:" + mylist_id);
			return this.articleDAO.add(this.articleDTO, mylist_id);
		}

		// user_idからマイリストIDを引っ張ってくる
		return 0;
	}

	/**
	 *
	 * Viewで記事のデータをセットする必要があるよ
	 * @param user_id, friend_user_id,
	 * @return
	 * @throws Exception
	 */
	public int addShareArticle(int user_id, int friend_user_id) throws Exception {
		int artid = 0;
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		// たぶん、記事IDは初期化しないでも動く。

		int mylist_id = this.articleDAO.getShareMylistID(user_id, friend_user_id);

		// 記事の追加 セットされてない場合は行わない
		// マイリストIDが0だと処理しないよ→Not Friend
		if (mylist_id != 0) {

			artid = this.articleDAO.add(this.articleDTO, mylist_id);
			//System.out.println(mylist_id);
			// 画像の追加(画像リストをそのまま追加する
			// たぶん、article_idが変更されてるので更新かかるはず。
			if (artid != 0) {
				// お気に入り、シェアURL、シェアURL期限
				this.articleDTO.setFavflag(false);
				this.articleDTO.setShare_expior(null);
				this.articleDTO.setShare_url(null);
				this.articleDAO.updateImage(artid, this.articleDTO.getImageDTO());
			}
		}

		// 追加した記事IDを返す
		return artid;
	}

	/**
	 * 画像の追加
	 * @return
	 * @throws Exception
	 */
	public boolean addImage(int article_id, ArrayList<ImageDTO> img) throws Exception {
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		return this.articleDAO.updateImage(article_id, img);
	}

	/**
	 * 記事の削除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteArticle(int user_id, int article_id) throws Exception {
		this.articleDAO = new ArticleDAO();

		int mylist_id = 0;
		// マイリスト取得
		if (this.articleDAO.isShare(article_id)) {
			// user_idとarticle_idからその人のもつシェアマイリストだった場合
			// 記事のマイリストIDは正しい。
			mylist_id = this.articleDAO.getShareMylistIDArt(user_id, article_id);

		} else {
			mylist_id = this.articleDAO.getMylistIDArt(user_id, article_id);
		}
		if (mylist_id != 0) {
			return this.articleDAO.delete(user_id, article_id);
		} else {
			return false;
		}

	}

	/**
	 * 記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewArticleList(int user_id, int page, int start_article_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.lists(user_id, page, start_article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * 記事全件一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewALLArticleList(int user_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.all_lists(user_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			//articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * お気に入りの記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewFavArticleList(int user_id, int page, int article_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.FavoriteLists(user_id, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/*	*//**
			* タグ内でお気に入りした記事一覧
			* @return
			* @throws Exception
			*/
	/*
	public ArrayList<ArticleBean> viewTagFavArticleList(int user_id, ArrayList<String> tag_body_list, int page)
		throws Exception {
	ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
	this.articleDAO = new ArticleDAO();
	article = articleDAO.searchFavoriteTagLists(user_id, tag_body_list, page);
	for (int i = 0; i < article.size(); i++) {
		ArticleBean articleBean = new ArticleBean();
		articleBean.setArticle_id(article.get(i).getArticle_id());
		articleBean.setTitle(article.get(i).getTitle());
		articleBean.setUrl(article.get(i).getUrl());
		articleBean.setCreated(article.get(i).getCreated());
		articleBean.setModified(article.get(i).getModified());
		articleBean.setShare_url(article.get(i).getShare_url());
		articleBean.setShare_expior(article.get(i).getShare_expior());
		articleBean.setFavflag(article.get(i).isFavflag());
		articleBean.setMylist_id(article.get(i).getMylist_id());
		articleBean.setThum(article.get(i).getThum());
		articleList.add(articleBean);
	}
	return articleList;
	}*/

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
	 * Thumの更新
	 * @return
	 * @throws Exception
	 */
	public boolean updateThum() throws Exception {
		this.articleDAO = new ArticleDAO();
		setArticleDTO();
		return this.articleDAO.updateThum(this.articleDTO);
	}

	/**
	 * 記事にお気に入りとして追加
	 * @return
	 * @throws Exception
	 */
	public boolean addFavorite(int user_id) throws Exception {
		this.articleDAO = new ArticleDAO();

		int mylist_id = 0;
		// マイリスト取得
		if (this.articleDAO.isShare(article_id)) {
			// user_idとarticle_idからその人のもつシェアマイリストだった場合
			// 記事のマイリストIDは正しい。
			mylist_id = this.articleDAO.getShareMylistIDArt(user_id, article_id);

		} else {
			mylist_id = this.articleDAO.getMylistIDArt(user_id, article_id);
		}
		if (mylist_id != 0) {
			return this.articleDAO.addFavorite(this.article_id, user_id);//true=成功、false=失敗
		} else {
			return false;
		}
	}

	/**
	 * お気に入りの解除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFavorite(int user_id, int article_id) throws Exception {
		this.articleDAO = new ArticleDAO();

		int mylist_id = 0;
		// マイリスト取得
		if (this.articleDAO.isShare(article_id)) {
			// user_idとarticle_idからその人のもつシェアマイリストだった場合
			// 記事のマイリストIDは正しい。
			mylist_id = this.articleDAO.getShareMylistIDArt(user_id, article_id);

		} else {
			mylist_id = this.articleDAO.getMylistIDArt(user_id, article_id);
		}
		if (mylist_id != 0) {
			return this.articleDAO.deleteFavorite(article_id, user_id);
		} else {
			return false;
		}

	}

	/**
	 * シェア記事の削除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteShareArticle(int user_id, int article_id) throws Exception {
		this.articleDAO = new ArticleDAO();

		return this.articleDAO.deleteShare(user_id, article_id);
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
	public ArticleBean viewArticle(int user_id, int article_id) throws Exception {
		ArticleBean articleBean = new ArticleBean();
		this.articleDAO = new ArticleDAO();
		setArticleDTO();

		int mylist_id = 0;
		int share_id = 0;
		// マイリスト取得
		if (this.articleDAO.isShare(article_id)) {
			// user_idとarticle_idからその人のもつシェアマイリストだった場合
			// 記事のマイリストIDは正しい。
			mylist_id = this.articleDAO.getShareMylistIDArt(user_id, article_id);
			share_id = mylist_id;
		} else {
			mylist_id = this.articleDAO.getMylistIDArt(user_id, article_id);
		}

		System.out.println("mylist_id:" + mylist_id);
		System.out.println("share_id:" + share_id);

		if (mylist_id != 0) {
			articleDTO = articleDAO.view(this.articleDTO.getArticle_id(), mylist_id);
			// 記事共通部分
			articleBean.setArticle_id(articleDTO.getArticle_id());
			articleBean.setBody(articleDTO.getBody());
			articleBean.setTitle(articleDTO.getTitle());
			articleBean.setUrl(articleDTO.getUrl());
			articleBean.setCreated(articleDTO.getCreated());
			articleBean.setModified(articleDTO.getModified());
			// サムネイル
			// シェアリストのIDを追加
			articleBean.setShare_id(share_id);
			articleBean.setFavflag(articleDTO.isFavflag());
			articleBean.setThum(articleDTO.getThum());
			articleBean.setShare_url(articleDTO.getShare_url());
			articleBean.setShare_expior(articleDTO.getShare_expior());
			articleBean.setMylist_id(articleDTO.getMylist_id());
			articleBean.setImageListDTO(articleDTO.getImageDTO());

			//画像のリスト
		} else {
			articleBean.setArticle_id(0);
		}

		return articleBean;
	}

	/**
	 * サムネイル更新用
	 * 1221修正完了
	 * @return
	 * @throws Exception
	 **/
	public ArticleBean viewForThum(int article_id) throws Exception {
		ArticleBean articleBean = new ArticleBean();
		this.articleDAO = new ArticleDAO();
		setArticleDTO();

		articleDTO = articleDAO.viewForThum(this.articleDTO.getArticle_id());

		// 記事共通部分
		articleBean.setArticle_id(articleDTO.getArticle_id());
		articleBean.setBody(articleDTO.getBody());
		articleBean.setTitle(articleDTO.getTitle());
		articleBean.setUrl(articleDTO.getUrl());
		articleBean.setCreated(articleDTO.getCreated());
		articleBean.setModified(articleDTO.getModified());
		// サムネイル
		// シェアリストのIDを追加
		articleBean.setShare_id(share_id);
		articleBean.setFavflag(articleDTO.isFavflag());
		articleBean.setThum(articleDTO.getThum());
		articleBean.setShare_url(articleDTO.getShare_url());
		articleBean.setShare_expior(articleDTO.getShare_expior());
		articleBean.setMylist_id(articleDTO.getMylist_id());
		articleBean.setImageListDTO(articleDTO.getImageDTO());

		return articleBean;
	}

	/**
	 * シェアしている記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewShareArticleList(int user_id, int friend_user_id, int page, int article_id)
			throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.viewShareArticle(user_id, friend_user_id, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setFavflag(article.get(i).isFavflag());
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
	public boolean addArticleTag(int user_id, ArrayList<String> tag_body_list, int article_id) throws Exception {
		this.articleDAO = new ArticleDAO();

		int mylist_id = 0;
		// マイリスト取得
		if (this.articleDAO.isShare(article_id)) {
			// user_idとarticle_idからその人のもつシェアマイリストだった場合
			// 記事のマイリストIDは正しい。
			mylist_id = this.articleDAO.getShareMylistIDArt(user_id, article_id);

		} else {
			mylist_id = this.articleDAO.getMylistIDArt(user_id, article_id);
		}

		if (mylist_id != 0) {
			return this.articleDAO.addTag(article_id, user_id, tag_body_list);
		} else {
			return false;
		}
	}

	/**
	 * 特定のタグの記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewTag(ArrayList<String> tag_body_list, int user_id, int page, int article_id)
			throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.viewTagArticle(tag_body_list, user_id, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * マイリスト検索の記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewMyListSearch(int user_id, ArrayList<String> text_list, int page, int article_id)
			throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.mylist_search(user_id, text_list, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * お気に入り検索の記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewFavListSearch(int user_id, ArrayList<String> text_list, int page, int article_id)
			throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.favlist_search(user_id, text_list, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * タグ検索の記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewTagSearch(int user_id, ArrayList<String> text_list, String tag, int page,
			int article_id)
			throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.tag_search(user_id, text_list, tag, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
			articleBean.setThum(article.get(i).getThum());
			articleList.add(articleBean);
		}
		return articleList;
	}

	/**
	 * シェア検索の記事一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleBean> viewShareListSearch(int user_id, ArrayList<String> text_list, int friend_user_id,
			int page, int article_id) throws Exception {
		ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
		this.articleDAO = new ArticleDAO();
		article = articleDAO.sharelist_search(user_id, text_list, friend_user_id, page, article_id);
		for (int i = 0; i < article.size(); i++) {
			ArticleBean articleBean = new ArticleBean();
			articleBean.setArticle_id(article.get(i).getArticle_id());
			articleBean.setTitle(article.get(i).getTitle());
			articleBean.setUrl(article.get(i).getUrl());
			articleBean.setCreated(article.get(i).getCreated());
			articleBean.setModified(article.get(i).getModified());
			articleBean.setShare_url(article.get(i).getShare_url());
			articleBean.setShare_expior(article.get(i).getShare_expior());
			articleBean.setFavflag(article.get(i).isFavflag());
			articleBean.setMylist_id(article.get(i).getMylist_id());
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
		this.articleDTO.setThum(this.thum);
		this.articleDTO.setFavflag(this.favflag);
		this.articleDTO.setImageDTO(this.imageListDTO);
		this.articleDTO.setShare_expior(this.share_expior);
	}

	// なんかあったら戻してください

	public void setImageDTO() {
		this.imageDTO = new ImageDTO();
		this.imageDTO.setImage_id(this.image_id);
		this.imageDTO.setUri(this.uri);
		this.imageDTO.setExtenstion(this.extension);
		this.imageDTO.setBlob_image(this.blob_image);
	}

	public ImageDTO getImageDTO() {
		this.imageDTO = new ImageDTO();
		this.imageDTO.setImage_id(this.image_id);
		this.imageDTO.setUri(this.uri);
		this.imageDTO.setExtenstion(this.extension);
		this.imageDTO.setBlob_image(this.blob_image);
		return imageDTO;
	}

	public void ArticleDelete() {
		this.article_id = 0;
		this.username = null;
		this.title = null;
		this.body = null;
		this.url = null;
		this.created = null;
		this.modified = null;
		this.share_url = null;
		this.share_expior = null;
		this.mylist_id = 0;
		this.articleDTO = null;
		this.articleDAO = null;
		this.article.clear();
		this.image_id = 0;
		this.uri = null;
		this.thum = null;
		this.blob_image = null;
		this.extension = null;
		this.imageDTO = null;
		this.favflag = false;
		this.share_id = 0;
		this.imageListDTO.clear();
		this.tagBeans.clear();
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

	public ArrayList<TagBean> getTagBeans() {
		return tagBeans;
	}

	public void setTagBeans(ArrayList<TagBean> tagBeans) {
		this.tagBeans = tagBeans;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isFavflag() {
		return favflag;
	}

	public void setFavflag(boolean favflag) {
		this.favflag = favflag;
	}

	public int getShare_id() {
		return share_id;
	}

	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}

}
