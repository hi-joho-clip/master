package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.Friend;

@WebServlet("/viewfriend")
public class ViewFriendServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ViewFriendServlet(){
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		Friend friendbeans = new Friend();

		//セッション情報の名前は変更する必要あり
		friendbeans = (Friend)session.getAttribute("user");

		int own_user_id = friendbeans.getOwn_user_id();
		int friend_user_id = friendbeans.getFriend_user_id();

		try {
			friendbeans = friendbeans.viewFriend(own_user_id, friend_user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		session.setAttribute("friend", friendbeans);
		//フレンド一覧するページへ
		request.getRequestDispatcher("/.jsp").forward(request, response);

	}

}