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
 * Servlet implementation class TagArticleListServlet
 */
@WebServlet("/TagArticleListServlet")
public class TagArticleListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagArticleListServlet() {
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
		****タグを検索欄に入力して検索した際****
		****************************************/
		//特定のタグの記事一覧表示
		ArrayList<String> tag_body_list = new ArrayList<String>();
		int user_id =0;//sessionからuser_idを取得
		tag_body_list = null; //クライアントからタグリストをもらう
		//タグ内でお気に入りした記事一覧
		try {
			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			article_list=articlebean.viewTag(tag_body_list,user_id);
			session.setAttribute("articlelist", article_list);
			request.getRequestDispatcher("/").forward(request, response);//リンク先で値をGETして記事一覧表示
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
