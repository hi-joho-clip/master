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
 * Servlet implementation class DeleteShareServlet
 */
@WebServlet("/deleteshare")
public class DeleteShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteShareServlet() {
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

		/**
		 * NONCEの検証が必要です。
		 */
//		Boolean ses_flag = false;
//		//nonceの検証を行う
//		String s_nonce = (String) session.getAttribute("nonce");
//		int user_id = 0;
//		int article_id = 0;
//		String nonce = request.getParameter("nonce");
//
//		// Nullでもなく空でもない
//		if (nonce != null && s_nonce != null) {
//			// nonceがない
//			if (s_nonce.equals(nonce)) {
//				// nonceが同一の場合
//				ses_flag = true;
//			}
//		}


		int article_id = 0;
		int user_id = 0;


		if (request.getParameter("article_id") != null) {
			try {
				article_id = Integer.parseInt(request.getParameter("article_id"));
				user_id = (int) session.getAttribute("user_id");
			} catch (Exception e) {
				e.printStackTrace();
			}

			ArticleBean articlebean = new ArticleBean();
			try {
				if (articlebean.deleteShareArticle(user_id, article_id)) {
					//成功したポップアップを表示
				} else {
					//失敗したポップアップを表示
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
