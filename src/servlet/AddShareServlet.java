package servlet;

import java.io.IOException;

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
		System.out.println("addshare");
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/

		/*****************************************************
		****ある記事をフレンドと共有するボタンを押したとき****
		*****************************************************/
		//記事にシェアURLを追加
		System.out.println(request.getParameter("id"));

		int user_id = 0;
		int friend_user_id = 0;
		int article_id = 0;

		ArticleBean articlebean = new ArticleBean();
		ArticleBean newBean = new ArticleBean();


		if (request.getParameter("article_id") != null) {
			try {
				article_id = Integer.parseInt(request.getParameter("article_id"));
				friend_user_id = Integer.parseInt(request.getParameter("friend_id"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		article_id = 78;

		if (article_id != 0) {
			try {
				// 記事情報を取得（画像を含めて）
				articlebean.setArticle_id(article_id);
				newBean = articlebean.viewArticle();
				newBean.addShareArticle(user_id, friend_user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
