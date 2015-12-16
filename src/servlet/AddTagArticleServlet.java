package servlet;

import java.io.IOException;
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
 * Servlet implementation class AddTagArticleServlet
 */
@WebServlet("/addtagarticle")
public class AddTagArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTagArticleServlet() {
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


		String[] List = request.getParameterValues("tag_list");
		System.out.println(List.length);
		ArrayList<String> tag_body_list = new ArrayList<String>();


		int user_id =1;//sessionからuser_idを取得
		int article_id =1;
		/*for(int i=0; i<list.length; i++){
		    tag_body_list.add(i,list[i]);
		}*/
		//System.out.println(tag_body_list.get(0));
		/*
		try {
			ArticleBean articlebean = new ArticleBean();
			articlebean.setArticle_id(article_id);
			if(articlebean.addArticleTag(user_id,tag_body_list)){
				//成功したポップアップを表示
			}else{
				//失敗したポップアップを表示
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}*/
	}

}
