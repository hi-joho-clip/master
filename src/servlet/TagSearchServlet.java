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
 * Servlet implementation class MylistServlet
 */
@WebServlet("/tagsearch")
public class TagSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TagSearchServlet() {
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

		//記事一覧表示
		int user_id = 0;//sessionからuser_idを取得
		int page = 1; // パラメータからページ番号取得(デフォルト1）
		ArrayList<String> text_list = new ArrayList<String>();
		String text = "";
		String tag = "";
		try {
			user_id = (int) session.getAttribute("user_id");
			text = request.getParameter("text");
			// _ %が含まれる文字列のエスケープ
			//全角の空白文字をひとつの半角空白に置換
			text=text.replaceAll("_","\\\\_").replaceAll("%", "\\\\%").replaceAll("　"," ");

			//連続した空白も含め分割する
			String[] text_body = text.split("[\\s]+");
			if(text_body.length!=0){
				for(int i=0;i<text_body.length;i++){
					text_list.add(i, text_body[i]);
					System.out.println(text_list.get(i));
				}
			}else{
				text_list.add(0," ");//空白が送られてきた場合
			}
			System.out.println("user_id" + user_id);
			if (request.getParameter("page") != null) {
				tag = request.getParameter("tag");
				page = Integer.parseInt(request.getParameter("page"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArticleBean articlebean = new ArticleBean();
		ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
		try {
			article_list = articlebean.viewTagSearch(user_id,text_list,tag, page);
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
