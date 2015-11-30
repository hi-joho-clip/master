package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.Friend;

@WebServlet("/FriendListServlet")
public class FriendListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FriendListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int own_user_id = Integer.parseInt(request.getParameter("own_user_id"));

		Friend friendbeans = new Friend();
		ArrayList<Friend> friend_list = new ArrayList<Friend>();

		try {
			friend_list = friendbeans.getFriendList(own_user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		session.setAttribute("friendList", friend_list);
		//フレンド一覧するページへ
		request.getRequestDispatcher("/.jsp").forward(request, response);

	}
}