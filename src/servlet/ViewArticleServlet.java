package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;
import beansdomain.ArticleBean;
import beansdomain.TagBean;

/**
 * Servlet implementation class ViewArticleServlet
 */
@WebServlet("/viewarticle")
public class ViewArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewArticleServlet() {
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

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		/***************************************
		******ある記事を選択して表示した際******
		****************************************/

		int user_id = 0;
		if (session.getAttribute("user_id") != null) {
			user_id = (int) session.getAttribute("user_id");
		}

		int article_id = 0;
		try {
			if (request.getParameter("article_id") != null) {

				article_id =Integer.parseInt(request.getParameter("article_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(article_id + "," +user_id);

		if (article_id != 0 && user_id != 0) {
			ArticleBean articlebean = new ArticleBean();
			ArticleBean article = new ArticleBean();
			TagBean tagbean = new TagBean();

			try {
				articlebean.setArticle_id(article_id);
				article = articlebean.viewArticle(user_id, article_id);
				article.setTagBeans(tagbean.viewExistingTag(user_id, article_id));
				// ユーザ名も欲しい
				article.setUsername((String) session.getAttribute("username"));


				//System.out.println(article.getBody());
				response.setContentType("application/json;charset=UTF-8");
				response.setHeader("Cache-Control", "private");

				PrintWriter out = response.getWriter();
				out.println(JSON.encode(article, true).toString());

			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");

			String resp = "{\"Error\": \"認証できてない\"}";
			PrintWriter out = response.getWriter();
			out.println(resp);
			// ログインへリダイレクト
			String URL = "/clipMaster/login";
			response.sendRedirect(URL + "/Login.html");
		}

	}
}
