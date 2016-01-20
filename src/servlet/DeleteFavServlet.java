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
 * Servlet implementation class FavDeleteServlet
 */
@WebServlet("/deletefav")
public class DeleteFavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFavServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);

		String resp = "{\"state\": \"unknown\", \"flag\": 0}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");

		Nonce nonce = new Nonce(request);

		//if (nonce.isNonce())
		//記事にお気に入りとして追加
		int user_id = 0;//sessionからuser_idを取得
		int article_id = 0;

		if (request.getParameter("article_id") != null) {

			try {
				article_id = Integer.parseInt(request.getParameter("article_id"));
				user_id = (int) session.getAttribute("user_id");
			} catch (Exception e) {
				e.printStackTrace();
			}
			ArticleBean articlebean = new ArticleBean();
			articlebean.setArticle_id(article_id);
			try {
				if (articlebean.deleteFavorite(user_id,article_id)) {
					//成功したポップアップを表示
					resp = "{\"state\": \"解除しました\", \"flag\": 1}";
				} else {
					//失敗したポップアップを表示
					resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			PrintWriter out = response.getWriter();
			out.println(resp);
		} else {
			PrintWriter out = response.getWriter();
			out.println(resp);
		}

	}
}
