package sync_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;
import beansdomain.ArticleBean;

/**
 * Servlet implementation class MylistServlet
 * サーバーに有って、ローカルに無い一覧を返すよ（更新する必要のあるリスト）
 */
@WebServlet("/getdeletearticle")
public class GetDeleteArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDeleteArticle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		perform(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		perform(request, response);
	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/

		int user_id = -1;
		if (session.getAttribute("user_id") != null) {
			user_id = (int) session.getAttribute("user_id");
		}
		System.out.println(user_id);

		// ブラウザからのリスト
		String strJson = (String) request.getParameter("json");

		//記事IDと日付を格納
		HashMap<Integer, JsonArticle> server_article_map = new HashMap<Integer, JsonArticle>();

		// 更新の必要がある記事IDとタイトルだけを持ったクラスを返す。
		ArrayList<JsonArticle> update_list = new ArrayList<JsonArticle>();

		JsonArticle[] json_list = null;

		try {
			if (strJson == null) {
				// ゼロで初期化させればオーケー
				json_list = new JsonArticle[0];
			} else {
				// JSONを配列へ変換(ブラウザのリスト）
				json_list = JSON.decode(strJson, JsonArticle[].class);
				System.out.println("reseived: " + json_list.length);
			}

			ArticleBean articlebean = new ArticleBean();
			// サーバのリスト
			ArrayList<ArticleBean> server_list = new ArrayList<ArticleBean>();
			server_list = articlebean.viewArticleList(user_id);

			/*
			 * ハッシュマップ作成（Articleは画像がついてるので高速化を含めて実装
			 */

			// article リスト分ハッシュマップを作成
			for (ArticleBean art : server_list) {

				// JsonArticleオブジェクトをハッシュマップへ登録
				JsonArticle ja = new JsonArticle();
				ja.setArticle_id(art.getArticle_id());
				ja.setTitle(art.getTitle());
				server_article_map.put(art.getArticle_id(), ja);
			}

			if (json_list.length != 0) {

				// ブラウザのデータのハッシュマップ
				HashMap<Integer, JsonArticle> b_json_map = new HashMap<Integer, JsonArticle>();
				for (JsonArticle jmap : json_list) {
					b_json_map.put(jmap.getArticle_id(), jmap);
				}

				// マイリストと更新データを比較
				for (ArticleBean art : server_list) {
					// ブラウザのリストにサーバの項目が存在する場合（日付比較）
					if (b_json_map.containsKey(art.getArticle_id())) {
						// 日付を比較する
						// サーバ＞ブラウザ

						// 存在しなければ新規追加（更新必要）
						JsonArticle new_json = new JsonArticle();
						new_json.setArticle_id(art.getArticle_id());
						new_json.setModified(server_article_map.get(art.getArticle_id()).getModified());
						new_json.setTitle(server_article_map.get(art.getArticle_id()).getTitle());
						update_list.add(new_json);

					} else {
						// 存在しなければ新規追加（更新必要）
						JsonArticle new_json = new JsonArticle();
						new_json.setArticle_id(art.getArticle_id());
						new_json.setModified(server_article_map.get(art.getArticle_id()).getModified());
						new_json.setTitle(server_article_map.get(art.getArticle_id()).getTitle());
						update_list.add(new_json);
					}

				}
			} else {
				// 全件更新の場合はマイリストへ登録されている記事数だけ作成
				for (ArticleBean art : server_list) {
					JsonArticle new_json = new JsonArticle();
					new_json.setArticle_id(art.getArticle_id());
					new_json.setModified(server_article_map.get(art.getArticle_id()).getModified());
					new_json.setTitle(server_article_map.get(art.getArticle_id()).getTitle());
					update_list.add(new_json);
				}
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		System.out.println("updateList:" + update_list.size());

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		out.println(JSON.encode(update_list, true).toString());
	}

	// JSONICで扱うため作成
	public class JsonArticle {

		private int article_id;
		private long modified;
		private String title;

		public long getModified() {
			return modified;
		}

		public void setModified(long modified) {
			this.modified = modified;
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
	}
}
