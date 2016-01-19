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
 * Servlet implementation class UpdateArticleServlet
 */
@WebServlet("/updatearticle")
public class UpdateArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateArticleServlet() {
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
		Boolean ses_flag = false;
		String resp = "{\"state\": \"unknownError\"}";

		//nonceの検証を行う
		String s_nonce = (String) session.getAttribute("nonce");
		String nonce = request.getParameter("nonce");

		// Nullでもなく空でもない
		if (nonce != null && s_nonce != null) {
			// nonceがない
			if (s_nonce.equals(nonce)) {
				// nonceが同一の場合
				ses_flag = true;
			}
		}

		if (ses_flag) {
			int article_id = 0;

			try {
				article_id = Integer.parseInt(request.getParameter("article_id"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//String title = request.getParameter("title");//JSON
			String body = (String)request.getParameter("body");//JSON
			System.out.println("body:" + body);


			body = new String(body.getBytes("UTF-8"), "UTF-8");

			//String url = request.getParameter("url");//JSON

			if (article_id != 0) {

				try {
					ArticleBean oldBean = new ArticleBean();
					oldBean.setArticle_id(article_id);
					ArticleBean articlebean = new ArticleBean();
					articlebean = oldBean.viewArticle();
					articlebean.setArticle_id(article_id);
					// 一時的に無効
					//articlebean.setTitle(title);
					System.out.println("body:" + body);
					articlebean.setBody(body);


					if (articlebean.updateArticle()) {
						//成功したポップアップを表示
						response.setContentType("application/json;charset=UTF-8");
						response.setHeader("Cache-Control", "private");

						resp = "{\"state\": \"更新しました\"}";

						System.out.println(resp);

					} else {
						response.setContentType("application/json;charset=UTF-8");
						response.setHeader("Cache-Control", "private");

						resp = "{\"state\": \"更新できませんでした\"}";

						//失敗したポップアップを表示
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				response.setContentType("application/json;charset=UTF-8");
				response.setHeader("Cache-Control", "private");

				resp = "{\"state\": \"認証が必要です\"}";

			}

			PrintWriter out = response.getWriter();
			out.println(resp);
		} else {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			resp = "{\"state\": \"不正なアクセス\"}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}
	}

}
