package daodto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beansdomain.TagBean;

public class ArticleDAO {
	private Connection con;

	public ArticleDAO() throws Exception {
		DBManagerAdmin dbManagerAdmin = DBManagerAdmin.getDBManagerAdmin();
		this.con = dbManagerAdmin.getConnection();
	}

	/**
	 * マイリストIDを返す
	 * @param user_id
	 * @return
	 */
	public int getMylistID(int user_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		String id_sql = "SELECT id FROM mylists,users WHERE mylists.user_id = users.user_id AND users.user_id = ? and share_flag = false";

		try {
			pstmt = con.prepareStatement(id_sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mylist_id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist_id;
	}

	/**
	 * フレンドとのマイリストID取得
	 * @param user_id
	 * @param friend_user_id
	 * @return
	 */
	public int getShareMylistID(int user_id, int friend_user_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		String id_sql = "SELECT id FROM friends WHERE own_user_id = ? and friend_user_id = ?";
		try {
			pstmt = con.prepareStatement(id_sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, friend_user_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mylist_id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mylist_id;
	}

	/**
	 * 記事の追加
	 *
	 * @return
	 * @throws Exception
	 */
	public int add(ArticleDTO article, int mylist_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		int article_id = 0;
		int id = 0;

		// サムネイルは
		String hantei_sql = "SELECT * FROM articles WHERE url = ? AND articles.id = ?";
		//String id_sql = "SELECT id FROM mylists,users WHERE mylists.user_id = users.user_id AND users.user_id = ?";
		String sql = "INSERT INTO articles(article_id, title,body,url,created,share_url,share_expior,id,thum,favflag,modified) VALUES(?,?,?,?,now(),null,null,?,?,FALSE,now()) "
				+
				"ON DUPLICATE KEY UPDATE title=VALUES(`title`), body=VALUES(`body`),modified = now()";
		String sql2 = "SELECT LAST_INSERT_ID() AS LAST;";
		//String sql3 = "INSERT INTO article_image(article_id,uri,blob_image) VALUES(?,?,?)";
		try {
			con.setAutoCommit(false);
			//			pstmt = con.prepareStatement(id_sql);
			//			pstmt.setInt(1, user_id);
			//			rs = pstmt.executeQuery();
			//			if (rs.next()) {
			id = mylist_id;//rs.getInt("id");
			//}
			pstmt = con.prepareStatement(hantei_sql);
			pstmt.setString(1, article.getUrl());
			pstmt.setInt(2, id);
			//System.out.println("pstmt2:" + pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				article_id = rs.getInt("article_id");
			}
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, article_id);
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getBody());
			pstmt.setString(4, article.getUrl());
			pstmt.setInt(5, id);
			pstmt.setBytes(6, article.getThum());
			//System.out.println(pstmt);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			//追加した記事のIDを入れる
			if (article_id == 0) {
				pstmt = con.prepareStatement(sql2);
				rs = pstmt.executeQuery();
				rs.next();
				article_id = rs.getInt("LAST");
			}

			con.commit();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);
		}
		return article_id;
	}

	/**
	 * 記事の削除
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean delete(int user_id, int article_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		int hikaku_user_id = 0;
		boolean flag = false;
		boolean hantei_flag = false;
		boolean share_flag = false;
		String getSQL = "select id from articles where article_id = ? ";
		String getART = "select user_id, share_flag from mylists where id = ? ";
		String getSART = "select own_user_id from friends where id = ? ";
		String sql = "DELETE FROM articles WHERE article_id =?";
		try {
			pstmt = con.prepareStatement(getSQL);
			pstmt.setInt(1, article_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mylist_id = rs.getInt("id");
			}

			if (mylist_id != 0) {
				pstmt = con.prepareStatement(getART);
				pstmt.setInt(1, mylist_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					hikaku_user_id = rs.getInt("user_id");
					share_flag = rs.getBoolean("share_flag");
				}

				// シェア記事処理
				if (share_flag) {
					pstmt = con.prepareStatement(getSART);
					pstmt.setInt(1, mylist_id);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						// own_user_idが一致したら
						if (user_id == rs.getInt("own_user_id")) {
							hantei_flag = true;
						}
					}
				} else {
					if (hikaku_user_id == user_id) {
						hantei_flag = true;
					}
				}

				/**
				 * 記事のマイリストIDと、自分のマイリストIDを比較
				 * 正しければ更新処理
				 */

				if (hantei_flag) {
					con.setAutoCommit(false);
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, article_id);
					pstmt.executeUpdate();
					con.commit();
					flag = true;
				}
			}
		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);
		}
		return flag;
	}

	/**
	 * 記事の更新
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean update(ArticleDTO article) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "UPDATE articles SET title = ?,body = ?,url = ?,modified = now() ,share_url = null,share_expior = null WHERE article_id = ?";
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getTitle());
			pstmt.setString(2, article.getBody());
			pstmt.setString(3, article.getUrl());
			pstmt.setInt(4, article.getArticle_id());
			//System.out.println("upate:" + pstmt);
			if (pstmt.executeUpdate() == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return flag;
	}

	/**
	 * 画像の更新
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean updateImage(int article_id, ArrayList<ImageDTO> image) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into article_image values(?, ?, ?, ? ,?) "
				+ "ON DUPLICATE KEY UPDATE blob_image=VALUES(`blob_image`)";
		String select = "select id from article_image where article_id = ? and uri = ?";
		boolean flag = false;
		try {
			int img_id = 0;

			con.setAutoCommit(false);
			for (ImageDTO imgDTO : image) {

				// 該当するImageIDを取得しておく
				pstmt = con.prepareStatement(select);
				pstmt.setInt(1, article_id);
				pstmt.setString(2, imgDTO.getUri());

				rs = pstmt.executeQuery();
				while (rs.next()) {
					img_id = rs.getInt("id");
				}

				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, img_id);
				pstmt.setInt(2, article_id);
				pstmt.setString(3, imgDTO.getUri());
				pstmt.setString(4, imgDTO.getExtenstion());
				pstmt.setBytes(5, imgDTO.getBlob_image());
				pstmt.executeUpdate();
				img_id = 0;
			}
			con.commit();
			con.setAutoCommit(true);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		}
		return flag;
	}

	/**
	 * シェア記事の削除
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean deleteShare(int user_id, int article_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		boolean hantei_flag = false;
		String getSQL = "select id from articles where article_id = ? ";
		String getART = "select own_user_id from friends where id = ? ";
		String sql = "UPDATE articles SET share_url = null,share_expior = null WHERE article_id = ? and id = ?";
		boolean flag = false;
		try {

			pstmt = con.prepareStatement(getSQL);
			pstmt.setInt(1, article_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mylist_id = rs.getInt("id");
			}

			if (mylist_id != 0) {
				pstmt = con.prepareStatement(getART);
				pstmt.setInt(1, mylist_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// own_user_idが一致したら
					if (user_id == rs.getInt("own_user_id")) {
						hantei_flag = true;
					}
				}

				if (hantei_flag) {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, article_id);
					pstmt.setInt(2, mylist_id);
					pstmt.executeUpdate();
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return flag;
	}


	/**
	 * 記事IDからシェア記事かを判定する
	 * @param article_id
	 * @return
	 * @throws Exception
	 */
	public boolean isShare(int article_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		boolean hantei_flag = false;
		String getSQL = "select id from articles where article_id = ? ";
		String getART = "select share_flag from mylists where id = ? ";

		try {
			pstmt = con.prepareStatement(getSQL);
			pstmt.setInt(1, article_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mylist_id = rs.getInt("id");
			}
			if (mylist_id != 0) {
				pstmt = con.prepareStatement(getART);
				pstmt.setInt(1, mylist_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// own_user_idが一致したら
					hantei_flag = rs.getBoolean("share_flag");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return hantei_flag;


	}

	/**
	 * シェア記事の場合、Article_IDとUser_IDから
	 * そのユーザのマイリスト内にある記事かを示す
	 * @return
	 * @throws Exception
	 */
	public int getShareMylistIDArt(int user_id, int article_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;

		String getSQL = "select id from articles where article_id = ? ";
		String getART = "select own_user_id, id from friends where id = ? ";

		try {
			pstmt = con.prepareStatement(getSQL);
			pstmt.setInt(1, article_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mylist_id = rs.getInt("id");
			}
			if (mylist_id != 0) {
				pstmt = con.prepareStatement(getART);
				pstmt.setInt(1, mylist_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// own_user_idが一致したら
					if (user_id == rs.getInt("own_user_id")) {
						mylist_id = rs.getInt("id");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return mylist_id;
	}

	/**
	 * 記事の場合、Article_IDとUser_IDから
	 * そのユーザのマイリスト内にある記事かを示す
	 * @return
	 * @throws Exception
	 */
	public int getMylistIDArt(int user_id, int article_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mylist_id = 0;
		int ret_id = 0;

		String getSQL = "select id from articles where article_id = ? ";
		String getART = "select id from mylists where user_id = ? and share_flag = false";

		try {
			pstmt = con.prepareStatement(getSQL);
			pstmt.setInt(1, article_id);
			//System.out.println(pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mylist_id = rs.getInt("id");
			}
			if (mylist_id != 0) {
				pstmt = con.prepareStatement(getART);
				pstmt.setInt(1, user_id);
				//System.out.println(pstmt);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// own_user_idが一致したら
					if (mylist_id == rs.getInt("id")) {
						ret_id = rs.getInt("id");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return ret_id;
	}

	/**
	 * 記事にお気に入りとして追加
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean addFavorite(int article_id, int user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		String hantei_sql = "select * from article_tag at where at.article_id = ? AND at.tag_id = " +
				"(SELECT tag_id FROM tags WHERE tags.user_id = ? AND tags.tag_body = 'お気に入り')";
		String sql = "INSERT INTO article_tag(article_id,tag_id) VALUES(?,"
				+ " (SELECT tag_id FROM tags " + " WHERE user_id = ? "
				+ " AND tag_body = 'お気に入り'))";
		String article_fav = "update articles set favflag = TRUE where article_id = ?";

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(hantei_sql);
			pstmt.setInt(1, article_id);
			pstmt.setInt(2, user_id);
			rs = pstmt.executeQuery();
			// 検索結果がない場合インサート可能
			if (!rs.next()) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, article_id);
				pstmt.setInt(2, user_id);
				//System.out.println("pstmt:" + pstmt);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(article_fav);
				pstmt.setInt(1, article_id);
				pstmt.executeUpdate();
				con.commit();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);
		}
		return flag;
	}

	/**
	 * お気に入りの解除
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFavorite(int article_id, int user_id) throws Exception {
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = "DELETE FROM article_tag WHERE article_id = ? AND tag_id = "
				+ " (SELECT tag_id FROM tags WHERE user_id = ? AND tag_body = 'お気に入り')";
		String article_fav = "update articles set favflag = FALSE where article_id = ?";

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, article_id);
			pstmt.setInt(2, user_id);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(article_fav);
			pstmt.setInt(1, article_id);
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);

		}
		return flag;
	}

	/**
	 * お気に入りの記事一覧表示
	 * paginator
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> FavoriteLists(int user_id, int page) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int def_page = 20 * (page - 1);
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();
		String sql = "SELECT * FROM articles WHERE article_id IN"
				+ " (SELECT article_id FROM article_tag WHERE tag_id ="
				+ " (SELECT tag_id FROM tags WHERE tag_body = 'お気に入り' AND user_id = ?)) limit 20 offset ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, def_page);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setArticle_id(rs.getInt("article_id"));
				article.setTitle(rs.getString("title"));
				article.setUrl(rs.getString("url"));
				article.setCreated(DateEncode.toDate(rs.getString("created")));
				article.setModified(DateEncode.toDate(rs.getString("modified")));
				article.setFavflag(rs.getBoolean("favflag"));
				article.setShare_url(rs.getString("share_url"));
				article.setShare_expior(rs.getDate("share_expior"));
				article.setThum(rs.getBytes("thum"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * タグ内でお気に入り検索
	 * paginator(OK)
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> searchFavoriteTagLists(int user_id,
			ArrayList<String> tag_body_list, int page) throws Exception {

		ArrayList<Integer> fav_id_list = new ArrayList<Integer>();
		ArrayList<Integer> search_id_list = new ArrayList<Integer>();

		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();

		try {
			ArrayList<String> favorite = new ArrayList<String>();
			favorite.add("お気に入り");
			fav_id_list = getArticleId(favorite, user_id, page);
			search_id_list = getArticleId(tag_body_list, user_id, page);

			for (int fav_id : fav_id_list) {
				// System.out.println("fav:" + fav_id);
				for (int search_id : search_id_list) {
					// System.out.println("search_id:" + search_id);
					if (fav_id == search_id) {
						articleList.add(view(fav_id, getMylistID(user_id)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * タグ名から重複しない記事IDを取得
	 *
	 * @return
	 * @throws Exception
	 */
	private ArrayList<Integer> getArticleId(ArrayList<String> tag_body_list,
			int user_id, int page) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int def_page = 20 * (page - 1);
		String tag_sql = "select tag_id from tags where tag_body like ? and user_id = ?";
		String article_id_sql = "select article_id from article_tag where tag_id = ? limit 20 offset ?";

		ArrayList<Integer> tag_id_list = new ArrayList<Integer>();
		ArrayList<Integer> article_id_list = new ArrayList<Integer>();

		try {
			// 複数タグのタグID取得
			for (String tag_body : tag_body_list) {
				pstmt = con.prepareStatement(tag_sql);
				pstmt.setString(1, tag_body);
				pstmt.setInt(2, user_id);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					tag_id_list.add(rs.getInt("tag_id"));
					// System.out.println("tag:" + tag_id_list.size());
				}
			}
			// 該当タグがある場合
			for (int tag_id : tag_id_list) {
				// タグのついたArticle_id取得
				pstmt = con.prepareStatement(article_id_sql);
				pstmt.setInt(1, tag_id);
				pstmt.setInt(2, def_page);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					article_id_list.add(rs.getInt("article_id"));
					// System.out.println("article:" + article_id_list.size());
				}
			}
			// タグのついた記事がある場合
			if (article_id_list != null) {
				// Article_idの重複を排除
				article_id_list = Unique.unique(article_id_list);
				// System.out.println("newarticle:" + article_id_list.size());

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return article_id_list;
	}

	/**
	 * 特定のタグの記事一覧表示
	 * paginator
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> viewTagArticle(ArrayList<String> tag_body_list, int user_id, int page)
			throws Exception {
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();

		ArrayList<Integer> article_id_list = new ArrayList<Integer>();

		try {
			article_id_list = getArticleId(tag_body_list, user_id, page);

			// 記事リストがあるだけ記事のデータを取得
			for (int article_id : article_id_list) {
				articleList.add(view(article_id, getMylistID(user_id)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * 記事にシェアURLを追加
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean addShareURL(String share_url, int article_id) throws Exception {
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = "UPDATE articles SET share_url = ?, share_expior = now() + INTERVAL  1 WEEK WHERE article_id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, share_url);// シェアURLを入れる
			pstmt.setInt(2, article_id);
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return flag;
	}

	/**
	 * 記事一覧表示
	 * paginator(OK)
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> lists(int user_id, int page) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int def_page = 20 * (page - 1);
		//System.out.println(def_page);
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();
		String sql = "SELECT * FROM articles WHERE id = ANY (SELECT id FROM mylists WHERE user_id = ?) limit 20 offset ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, def_page);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setArticle_id(rs.getInt("article_id"));
				article.setTitle(rs.getString("title"));
				article.setUrl(rs.getString("url"));
				article.setCreated(DateEncode.toDate(rs.getString("created")));
				article.setModified(DateEncode.toDate(rs.getString("modified")));
				article.setShare_url(rs.getString("share_url"));
				article.setShare_expior(rs.getDate("share_expior"));
				article.setFavflag(rs.getBoolean("favflag"));
				article.setThum(rs.getBytes("thum"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * 記事全件一覧表示
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> all_lists(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();
		String sql = "SELECT * FROM articles WHERE id = ANY (SELECT id FROM mylists WHERE user_id = ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setArticle_id(rs.getInt("article_id"));
				article.setTitle(rs.getString("title"));
				article.setUrl(rs.getString("url"));
				article.setCreated(DateEncode.toDate(rs.getString("created")));
				article.setModified(DateEncode.toDate(rs.getString("modified")));
				article.setShare_url(rs.getString("share_url"));
				article.setShare_expior(rs.getDate("share_expior"));
				article.setFavflag(rs.getBoolean("favflag"));
				article.setThum(rs.getBytes("thum"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * 記事を表示
	 *
	 * @return
	 * @throws Exception
	 */
	public ArticleDTO view(int article_id, int mylist_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArticleDTO articleDTO = new ArticleDTO();
		ArrayList<ImageDTO> image_list = new ArrayList<ImageDTO>();
		String sql = "SELECT * FROM articles where article_id = ? and id = ? ";
		String image_sql = "select * from article_image where article_id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, article_id);
			pstmt.setInt(2, mylist_id);
			//System.out.println("view:" + pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				articleDTO.setArticle_id(rs.getInt("article_id"));
				articleDTO.setTitle(rs.getString("title"));
				articleDTO.setBody(rs.getString("body"));
				articleDTO.setUrl(rs.getString("url"));
				articleDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				articleDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				articleDTO.setShare_url(rs.getString("share_url"));
				articleDTO.setFavflag(rs.getBoolean("favflag"));
				articleDTO.setThum(rs.getBytes("thum"));
				articleDTO.setShare_expior(rs.getDate("share_expior"));
				articleDTO.setMylist_id(rs.getInt("id"));
			}

			// 記事IDがセットされてないと処理しない
			if (articleDTO.getArticle_id() != 0) {
				pstmt = con.prepareStatement(image_sql);
				pstmt.setInt(1, article_id);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					ImageDTO imageDTO = new ImageDTO();
					imageDTO.setImage_id(rs.getInt("id"));
					imageDTO.setArticle_id(rs.getInt("article_id"));
					imageDTO.setUri(rs.getString("uri"));
					imageDTO.setExtenstion(rs.getString("extension"));
					imageDTO.setBlob_image(rs.getBytes("blob_image"));
					image_list.add(imageDTO);
				}
				articleDTO.setImageDTO(image_list);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleDTO;
	}

	/**
	 * シェアしている記事一覧表示
	 * paginator(OK)
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> viewShareArticle(int user_id, int friend_user_id, int page)
			throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int def_page = 20 * (page - 1);
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();
		String sql = "SELECT * FROM articles WHERE articles.id = " +
				"(SELECT M.id FROM friends F,mylists M WHERE F.own_user_id = ? AND F.friend_user_id = ? " +
				"AND M.id = F.id AND M.share_flag=1) limit 20 offset ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, friend_user_id);
			pstmt.setInt(3, def_page);
			//System.out.println(pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setArticle_id(rs.getInt("article_id"));
				article.setTitle(rs.getString("title"));
				article.setUrl(rs.getString("url"));
				article.setCreated(DateEncode.toDate(rs.getString("created")));
				article.setModified(DateEncode.toDate(rs.getString("created")));
				article.setShare_url(rs.getString("share_url"));
				article.setFavflag(rs.getBoolean("favflag"));
				article.setShare_expior(rs.getDate("share_expior"));
				article.setThum(rs.getBytes("thum"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}

	/**
	 * 記事にタグを追加
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean addTag(int article_id, int user_id, ArrayList<String> tag_body_list) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		ArrayList<Integer> tag_id_list = new ArrayList<Integer>();
		String hantei_sql1 = "SELECT * FROM tags WHERE tag_body = ? AND user_id = ?";
		String hantei_sql2 = "SELECT * FROM article_tag WHERE article_id = ? AND tag_id = ?";
		String get_tag_sql = "SELECT tag_id FROM tags WHERE tag_body = ? AND user_id = ?";
		String sql1 = "INSERT INTO tags(tag_body,created,modified,lastest,user_id) values(?,now(),now(),now(),?)";
		String sql2 = "INSERT INTO article_tag(article_id, tag_id) VALUES(?,?)";
		String update_sql = "UPDATE tags SET lastest = now() WHERE tag_id = ?";
		try {
			con.setAutoCommit(false);
			//tag_bodyがある限り
			for (String tag_body : tag_body_list) {
				pstmt = con.prepareStatement(hantei_sql1);//送られてきたタグリストがあるならとってくる。
				pstmt.setString(1, tag_body);
				pstmt.setInt(2, user_id);
				rs = pstmt.executeQuery();
				//検索結果がない場合、新規タグを登録する(tags)
				if (!rs.next()) {
					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, tag_body);
					pstmt.setInt(2, user_id);
					pstmt.executeUpdate();
				}
				pstmt = con.prepareStatement(get_tag_sql);//送られてきたタグリストのタグIDを取得する
				pstmt.setString(1, tag_body);
				pstmt.setInt(2, user_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					tag_id_list.add(rs.getInt("tag_id"));
				}
			}
			//tag_idがある限り
			for (int tag_id : tag_id_list) {
				pstmt = con.prepareStatement(hantei_sql2);//その記事についてるタグがあればとってくる
				pstmt.setInt(1, article_id);
				pstmt.setInt(2, tag_id);
				rs = pstmt.executeQuery();
				//検索結果がない場合、記事にタグを付与する(article_tag)
				if (!rs.next()) {
					pstmt = con.prepareStatement(sql2);
					pstmt.setInt(1, article_id);
					pstmt.setInt(2, tag_id);
					pstmt.executeUpdate();
					//タグを更新する
					pstmt = con.prepareStatement(update_sql);
					pstmt.setInt(1, tag_id);
					pstmt.executeUpdate();
				}
			}

			String deletesql = "delete FROM article_tag WHERE id = ?";
			String getid = "SELECT id FROM article_tag WHERE tag_id = ? AND article_id = ?;";
			String deletetagsql = "delete FROM tags WHERE tag_id = ?";
			String get_id = "SELECT id FROM article_tag WHERE tag_id = ? ";

			TagBean tagbean = new TagBean();
			ArrayList<TagBean> tag_list = new ArrayList<TagBean>();
			tag_list = tagbean.viewExistingTag(user_id, article_id);

			//データベースに存在するタグリストがある限り
			for (int i = 0; i < tag_list.size(); i++) {
				boolean deleteflag = true;
				//送られてきたタグのIDがある限り
				for (int tag_id : tag_id_list) {
					//同じであれば該当するのでフラグがfalseになる
					if (tag_id == tag_list.get(i).getTag_id()) {
						deleteflag = false;
						break;
					} else {

					}
				}
				//trueのままであればクライアントから送られたデータの中に該当しないので削除する
				if (deleteflag) {
					pstmt = con.prepareStatement(getid);
					pstmt.setInt(1, tag_list.get(i).getTag_id());
					pstmt.setInt(2, article_id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						pstmt = con.prepareStatement(deletesql);//該当しないIDを削除
						pstmt.setInt(1, rs.getInt("id"));
						pstmt.executeUpdate();
					}
					//更にこのユーザがそのタグを一つも使っていなければtagsの中のタグを削除する
					pstmt = con.prepareStatement(get_id);
					pstmt.setInt(1, tag_list.get(i).getTag_id());
					System.out.println("消したいタグID" + tag_list.get(i).getTag_id());
					rs = pstmt.executeQuery();
					//検索結果がなければ削除
					if (!rs.next()) {
						pstmt = con.prepareStatement(deletetagsql);//該当しないIDを削除
						pstmt.setInt(1, tag_list.get(i).getTag_id());
						pstmt.executeUpdate();
					}
				}
			}
			con.commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);
		}
		return flag;
	}


	/**
	 * マイリスト内検索
	 * * paginator(OK)
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArticleDTO> mylist_search(int user_id,ArrayList<String> text_list,int page) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int def_page = 20 * (page - 1);
		System.out.println(def_page);
		ArrayList<ArticleDTO> articleList = new ArrayList<ArticleDTO>();
		String sql = "";
		String like = "";
		int count = 0;//値をセットするために必要なカウント変数

		//text_list.size()-1なのは最後のANDを取り除きたいから
		for(int i=0;i<text_list.size()-1;i++){
			if(text_list.size()==1){
				like += "title LIKE ?";
			}else{
				like += "title LIKE ? AND ";
			}
			count++;
		}
		if(text_list.size()==1){
		}else{
			like += "title LIKE ?";
			count++;
		}
		System.out.println(like);

		sql = "SELECT * FROM articles WHERE "+like+" AND id = ANY (SELECT id FROM mylists WHERE user_id = ?) limit 20 offset ?";


		try {
			pstmt = con.prepareStatement(sql);

			for(int i=1; i<=count;i++){
				pstmt.setString(i, "%"+text_list.get(i-1)+"%");
			}
			pstmt.setInt(count+1, user_id);
			pstmt.setInt(count+2, def_page);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArticleDTO article = new ArticleDTO();
				article.setArticle_id(rs.getInt("article_id"));
				article.setTitle(rs.getString("title"));
				article.setUrl(rs.getString("url"));
				article.setCreated(DateEncode.toDate(rs.getString("created")));
				article.setModified(DateEncode.toDate(rs.getString("modified")));
				article.setShare_url(rs.getString("share_url"));
				article.setShare_expior(rs.getDate("share_expior"));
				article.setFavflag(rs.getBoolean("favflag"));
				article.setThum(rs.getBytes("thum"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return articleList;
	}
	//

}
