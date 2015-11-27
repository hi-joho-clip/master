package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.ArticleBean;

/**
 * Servlet implementation class ShareListServlet
 */
@WebServlet("/ShareListServlet")
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/***************************************
		**********フレンドを選択した際**********
		****************************************/
		//シェアしている記事一覧表示
		int user_id =0;//sessionからuser_idを取得
		int friend_user_id = Integer.parseInt(request.getParameter("friend_user_id"));
		ArticleBean articlebean = new ArticleBean();
		ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
		try {
			article_list=articlebean.viewShareArticleList(user_id, friend_user_id);
			session.setAttribute("articlelist", article_list);
			request.getRequestDispatcher("/").forward(request, response);//リンク先で値をGETして記事一覧表示
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
