package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.ArticleBean;

/**
 * Servlet implementation class AddShareServlet
 */
@WebServlet("/addshare")
public class AddShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddShareServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/*****************************************************
		****ある記事をフレンドと共有するボタンを押したとき****
		*****************************************************/
		//記事にシェアURLを追加

		ArticleBean artbean = new ArticleBean();
		ArticleBean newbean = new ArticleBean();

		int user_id = 0;
		int article_id = 0;
		int friend_id = 0;
		if(nonce.isNonce()){
			if (request.getParameter("article_id") != null && request.getParameter("friend_id") != null) {
				try {

					user_id = (int)session.getAttribute("user_id");
					article_id = Integer.parseInt(request.getParameter("article_id"));
					friend_id = Integer.parseInt(request.getParameter("friend_id"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					System.out.println("aaaa" + article_id);
					System.out.println(article_id + friend_id);
					artbean.setArticle_id(article_id);
					newbean = artbean.viewArticle(user_id, article_id);
					System.out.println(newbean.getTitle());
					newbean.setArticle_id(0);
					if(newbean.addShareArticle(user_id, friend_id)!=0){
						resp = "{\"state\": \"シェア追加しました\", \"flag\": 1}";
					}else{
						resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
					}
				} catch (Exception e) {

				}
				PrintWriter out = response.getWriter();
				out.println(resp);
			}else{
				//article_idかfriend_idがnullな場合
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
