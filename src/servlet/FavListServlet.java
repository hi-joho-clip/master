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
 * Servlet implementation class FavListServlet
 */
@WebServlet("/favlist")
public class FavListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavListServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		perform(request, response);
	}
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/***************************************
		*サイドメニューのお気に入りを押したとき*
		****************************************/
		//お気に入りの記事一覧表示
		int user_id =0;//sessionからuser_idを取得
		int page = 1; // パラメータからページ番号取得初期値1
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
		ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
		try {
			article_list=articlebean.viewFavArticleList(user_id, page,start_article_id);
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
