package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.Friend;

@WebServlet("/deletefriend")
public class DeleteFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteFriendServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Friend friendbeans = new Friend();
		//boolean flag = false;
		HttpSession session = request.getSession(true);
		Nonce nonce = new Nonce(request);
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		if (nonce.isNonce()) {
			if(request.getParameter("friend_user_id")!=null){
				try {
					int own_user_id = (int) session.getAttribute("user_id");
					int friend_user_id = Integer.parseInt(request.getParameter("friend_user_id"));
					//flag = friendbeans.deleteFriend(own_user_id, friend_user_id);
					if(friendbeans.deleteFriend(own_user_id, friend_user_id)){
						resp = "{\"state\": \"削除しました\",  \"flag\": 1}";
					}else{
						resp = "{\"state\": \"失敗しました\",  \"flag\": 0}";
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				out.println(resp);
			}else{
				out.println(resp);
			}
			//out.println(JSON.encode(flag, true).toString());
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
		}
	}
}
