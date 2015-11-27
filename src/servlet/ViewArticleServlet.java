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
 * Servlet implementation class ViewArticleServlet
 */
@WebServlet("/ViewArticleServlet")
public class ViewArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewArticleServlet() {
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
		******ある記事を選択して表示した際******
		****************************************/
		//記事を表示
		int article_id = Integer.parseInt(request.getParameter("article_id"));
		try {

			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			articlebean.setArticle_id(article_id);
			article_list=articlebean.viewArticle();
			session.setAttribute("articlelist", article_list);
			request.getRequestDispatcher("/").forward(request, response);//リンク先で値をGETして記事一覧表示

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
