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
import beansdomain.User;

@WebServlet("/SearchFriendServlet")
public class SearchFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchFriendServlet() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		Friend friendbeans = new Friend();
		ArrayList<User> friend_list = new ArrayList<User>();

		friendbeans = (Friend)session.getAttribute("");
		int own_user_id = friendbeans.getOwn_user_id();
		String str = request.getParameter("str");

		try {
			friend_list = friendbeans.searchFriend(own_user_id, str);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		session.setAttribute("friendList", friend_list);
		//フレンド検索ページへ
		request.getRequestDispatcher("/.jsp").forward(request, response);
	}
}
