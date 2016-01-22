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

/**
 * Servlet implementation class ShareListServlet
 */
@WebServlet("/sharelist")
public class ShareListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShareListServlet() {
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

		int user_id = 0;
		int friend_user_id = 0;
		int page = 1; // パラメータからページ番号取得初期値1

		if (request.getParameter("page") != null && request.getParameter("friend_user_id") != null) {
			try {
				user_id = (int) session.getAttribute("user_id");
				page = Integer.parseInt(request.getParameter("page"));
				friend_user_id = Integer.parseInt(request.getParameter("friend_user_id"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(page);

			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			try {
				article_list = articlebean.viewShareArticleList(user_id, friend_user_id, page);
				//System.out.println(article_list.size());
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();
			out.println(JSON.encode(article_list, true).toString());
		}
	}

}
