package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.TagBean;

/**
 * Servlet implementation class UpdateTagServlet
 */
@WebServlet("/UpdateTagServlet")
public class UpdateTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTagServlet() {
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
		/*************************************
		****タグ画面からタグを更新したとき****
		**************************************/
		// タグの更新
		int user_id =0;//sessionからuser_idを取得
		int tag_id = Integer.parseInt(request.getParameter("tag_id"));
		String tag_body = request.getParameter("tag_body");
		TagBean tagbean = new TagBean();
		tagbean.setTag_id(tag_id);
		tagbean.setTag_body(tag_body);
		tagbean.setUser_id(user_id);
		try {
			if(tagbean.updateTag()){
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
