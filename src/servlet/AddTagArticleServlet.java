package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.ArticleBean;

/**
 * Servlet implementation class AddTagArticleServlet
 */
@WebServlet("/addtagarticle")
public class AddTagArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddTagArticleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);
		boolean flag = true;
		// ***&nonce=nonnsutati

		int user_id = 0;//sessionからuser_idを取得
		int article_id = 0;
		if(nonce.isNonce()){

			if (request.getParameter("tag_list") != null) {
				String tag_list = request.getParameter("tag_list");
				tag_list = new String(tag_list.getBytes("UTF-8"), "UTF-8");
				System.out.println(tag_list);
				String[] tag_body = tag_list.split(",", 0);
				ArrayList<String> tag_body_list = new ArrayList<String>();
				try {
					user_id = (int)session.getAttribute("user_id");
					article_id = Integer.parseInt(tag_body[0]);
				} catch (Exception e) {

				}
				System.out.println("article_id:" + article_id);
				for (int i = 0; i < tag_body.length-1; i++) {
					if(tag_body[i+1].length()<=15){
						System.out.println(tag_body[i].length());
						tag_body[i+1]=tag_body[i+1].replaceAll("お気に入り","").replaceAll("<","＜").replaceAll(">","＞");
						System.out.println("tag_bodykun:"+tag_body[i+1]);
						tag_body_list.add(i, tag_body[i+1]);
						System.out.println("tagbody:" + tag_body_list.get(i) + "&user_id:" + user_id);
					}else{
						resp = "{\"state\": \"文字数オーバー\", \"flag\": 0}";
						flag=false;
						break;
					}
				}
				if(flag){
					try {
						ArticleBean articlebean = new ArticleBean();

						if (articlebean.addArticleTag(user_id, tag_body_list, article_id)) {
							//成功したポップアップを表示
							resp = "{\"state\": \"タグを更新しました\", \"flag\": 1}";
						} else {
							//失敗したポップアップを表示
							resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
						}
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
				PrintWriter out = response.getWriter();
				out.println(resp);
			}else{
				//タグリストがnullな場合
				PrintWriter out = response.getWriter();
				out.println(resp);
			}
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}

	}
}
