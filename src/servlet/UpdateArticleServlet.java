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
 * Servlet implementation class UpdateArticleServlet
 */
@WebServlet("/UpdateArticleServlet")
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/*****************************************
		****記事を編集して変更ボタンを押した際****
		*****************************************/
		//記事の更新
		int article_id = Integer.parseInt(request.getParameter("article_id"));
		String title = request.getParameter("title");//JSON
		String body = request.getParameter("body");//JSON
		String url = request.getParameter("url");//JSON
		ArticleBean articlebean = new ArticleBean();
		articlebean.setArticle_id(article_id);
		articlebean.setTitle(title);
		articlebean.setBody(body);
		articlebean.setUrl(url);
		try {
			if(articlebean.updateArticle()){
				//成功したポップアップを表示
			}else{
				//失敗したポップアップを表示
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
