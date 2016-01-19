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

		int user_id = 2;

		int article_id = 63;
		int friend_id = 1;
		if (request.getParameter("article_id") != null && request.getParameter("friend_id") != null) {
			try {

				user_id = (int)session.getAttribute("user_id");
				article_id = Integer.parseInt(request.getParameter("article_id"));
				friend_id = Integer.parseInt(request.getParameter("friend_id"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("aaaa" + article_id);
			System.out.println(article_id + friend_id);
			artbean.setArticle_id(article_id);
			newbean = artbean.viewArticle();
			System.out.println(newbean.getTitle());
			newbean.setArticle_id(0);
			newbean.addShareArticle(user_id, friend_id);
		} catch (Exception e) {

		}

	}

}
