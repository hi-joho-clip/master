package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.TagBean;

/**
 * Servlet implementation class DeleteTagServlet
 */
@WebServlet("/deletetag")
public class DeleteTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTagServlet() {
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
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		String resp = "{\"state\": \"unknownError\",  \"flag\": 0}";
		Nonce nonce = new Nonce(request);

		/*************************************
		****タグ画面からタグを削除したとき****
		**************************************/
		int user_id=0;
		int tag_id=0;
		// タグの削除
		if(nonce.isNonce()){
			if (request.getParameter("tag_id")!=null){
				try {
					tag_id = Integer.parseInt(request.getParameter("tag_id"));
					user_id = (int) session.getAttribute("user_id");
				} catch (Exception e) {
					e.printStackTrace();
				}
				TagBean tagbean = new TagBean();
				tagbean.setTag_id(tag_id);

				try {
					if(tagbean.deleteTag(tag_id,user_id)){
						//成功したポップアップを表示
						resp = "{\"state\": \"削除しました\", \"flag\": 1}";
					}else{
						//失敗したポップアップを表示
						resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				out.println(resp);
			}else{
				//tag_idがnullな場合
				PrintWriter out = response.getWriter();
				out.println(resp);
			}
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}
	}

}
