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
import beansdomain.TagBean;

/**
 * Servlet implementation class TagListServlet
 */
@WebServlet("/taglist")
public class TagListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TagListServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		perform(request, response);
	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/*********************************************
		****サイドメニューのタグボタンを押したとき****
		*********************************************/
		// タグの一覧表示
		int user_id = 0;//sessionからuser_idを取得

		try {
			user_id = (int) session.getAttribute("user_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user_id != 0) {
			TagBean tagbean = new TagBean();
			ArrayList<TagBean> tag_list = new ArrayList<TagBean>();
			try {
				tag_list = tagbean.viewTagList(user_id);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();
			out.println(JSON.encode(tag_list, true).toString());
		}
	}

}
