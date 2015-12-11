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
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		System.out.println("article_id" + Integer.parseInt(request.getParameter("article_id")) + "の記事");
		/***************************************
		******ある記事を選択して表示した際******
		****************************************/
		//記事を表示
		int user_id = 1;//sessionからuser_idを取得
		//int article_id = Integer.parseInt(request.getParameter("article_id"));
		int article_id = 1;
		try {
			if (request.getParameter("article_id").equals(null)) {
				article_id = Integer.parseInt(request.getParameter("article_id"));
			}
			System.out.println("guid:" + request.getParameter("guid"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArticleBean articlebean = new ArticleBean();
		ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
		TagBean tagbean = new TagBean();
		ArrayList<TagBean> tag_list = new ArrayList<TagBean>();
		try {
			articlebean.setArticle_id(article_id);
			article_list = articlebean.viewArticle();
			tag_list = tagbean.viewExistingTag(user_id, article_id);
		} catch (Exception e) {
			e.getStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		out.println(JSON.encode(article_list, true).toString() + JSON.encode(tag_list, true).toString());
	}
}
