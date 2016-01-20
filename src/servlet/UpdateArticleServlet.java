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

		String resp = "{\"state\": \"unknownError\",  \"flag\": 0}";

		Nonce nonce = new Nonce(request);

		int user_id = 0;
		int article_id = 0;

		// Nonceの検証
		if (nonce.isNonce()) {

			try {
				article_id = Integer.parseInt(request.getParameter("article_id"));
				user_id = (int) session.getAttribute("user_id");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//String title = request.getParameter("title");//JSON
			String body = (String) request.getParameter("body");//JSON

			body = new String(body.getBytes("UTF-8"), "UTF-8");

			//String url = request.getParameter("url");//JSON

			if (article_id != 0 && user_id != 0) {

				try {
					ArticleBean oldBean = new ArticleBean();
					oldBean.setArticle_id(article_id);
					ArticleBean articlebean = new ArticleBean();
					articlebean = oldBean.viewArticle(user_id, article_id);

					if (articlebean.getArticle_id() != 0) {

						if (articlebean.getTitle() == null) {
							articlebean.setTitle(" ");
						}
						if (articlebean.getUrl() == null) {
							articlebean.setUrl("http://homo/" + article_id);
						}

						articlebean.setArticle_id(article_id);
						// 一時的に無効
						//articlebean.setTitle(title);
						//System.out.println("body:" + body);
						articlebean.setBody(body);

						if (articlebean.updateArticle()) {
							//成功したポップアップを表示
							response.setContentType("application/json;charset=UTF-8");
							response.setHeader("Cache-Control", "private");

							resp = "{\"state\": \"更新しました\", \"flag\": 1}";

							//System.out.println(resp);

						} else {
							response.setContentType("application/json;charset=UTF-8");
							response.setHeader("Cache-Control", "private");

							resp = "{\"state\": \"更新できませんでした\", \"flag\": 0}";

							//失敗したポップアップを表示
						}
					} else {
						response.setContentType("application/json;charset=UTF-8");
						response.setHeader("Cache-Control", "private");

						resp = "{\"state\": \"不正なアクセス\", \"flag\": 0}";
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				response.setContentType("application/json;charset=UTF-8");
				response.setHeader("Cache-Control", "private");

				resp = "{\"state\": \"認証が必要です\", \"flag\": 0}";

			}

			PrintWriter out = response.getWriter();
			out.println(resp);
		} else {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}
	}

}
