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
 * Servlet implementation class ArticleDeleteServlet
 */
@WebServlet("/deletearticle")
public class DeleteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteArticleServlet() {
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
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		String resp = "{\"state\": \"unknownError\",  \"flag\": 0}";
		PrintWriter out = response.getWriter();
		Nonce nonce = new Nonce(request);
		int article_id = 0;
		int user_id = 0;

		if(nonce.isNonce()){
			if (request.getParameter("article_id") != null) {
				try {
					article_id = Integer.parseInt(request.getParameter("article_id"));
					user_id = (int)session.getAttribute("user_id");
				} catch (Exception e) {
					e.printStackTrace();
				}

				ArticleBean articlebean = new ArticleBean();
				articlebean.setArticle_id(article_id);
				System.out.println("delete:");

				try {
					if (articlebean.deleteArticle(user_id, article_id)) {
						//成功したポップアップを表示
						resp = "{\"state\": \"削除しました\", \"flag\": 1, \"article_id\" : " + article_id + "}";
					} else {
						//失敗したポップアップを表示
						resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				// article_IDのエラー
				resp = "{\"state\": \"記事がありません\", \"flag\": 0}";
			}
			out.println(resp);
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out.println(resp);
		}
	}

}
