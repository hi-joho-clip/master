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
 * Servlet implementation class TagArticleListServlet
 */
@WebServlet("/tagarticlelist")
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
		****タグを検索欄に入力して検索した際****
		****************************************/
		//特定のタグの記事一覧表示
		String tag_list = "";
		ArrayList<String> tag_body_list = new ArrayList<String>();
		int user_id =0;//sessionからuser_idを取得
		int page = 1; // パラメータからページ番号取得初期値1
		if(request.getParameter("tag_list") != null){
			try {
				tag_list = request.getParameter("tag_list");
				user_id = (int) session.getAttribute("user_id");
				if (request.getParameter("page") != null) {
					page = Integer.parseInt(request.getParameter("page"));
					System.out.println("page:" + page);
				}

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] tag_body = tag_list.split(",", 0);
			for(int i=0; i<tag_body.length; i++){
			    tag_body_list.add(i,tag_body[i]);
			   System.out.println("tagbody:"+tag_body_list.get(i));
			}
			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			try {
				article_list=articlebean.viewTag(tag_body_list,user_id, page);
			} catch (Exception e) {
				e.getStackTrace();
			}
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();
			out.println(JSON.encode(article_list, true).toString());
		}
	}

}
