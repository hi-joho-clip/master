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

/**
 * Servlet implementation class MylistServlet
 */
@WebServlet("/mylist")
public class MyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyListServlet() {
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

		//記事一覧表示
		int user_id = 0;//sessionからuser_idを取得
		int page = 1; // パラメータからページ番号取得(デフォルト1）
		int start_article_id = 0;


		try {
			user_id = (int) session.getAttribute("user_id");
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			if (request.getParameter("article_id") != null) {
				start_article_id = Integer.parseInt(request.getParameter("article_id"));//記事一覧表示の一番後ろのIDを持つ。
			}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}


		System.out.println("user_id:" + user_id + "page:" + page + "start_article_id:" + start_article_id);
		ArticleBean articlebean = new ArticleBean();
		//ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
		try {
			//article_list = articlebean.viewArticleList(user_id, page,start_article_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		// 最大深度5階層以下
		try {
			JSON json = new JSON();
			json.setMaxDepth(3);
			out.println(json.encode(articlebean.viewArticleList(user_id, page,start_article_id)).toString());
			//out.println("{\"state\" : 0}");
			out.close();
			articlebean.ArticleDelete();
			articlebean = null;
			json = null;
		} catch (Exception e) {

		}
		//article_list.clear();

		Runtime rt = Runtime.getRuntime();
		rt.gc();
	}

}
