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

import beansdomain.Friend;
import beansdomain.User;

@WebServlet("/searchfriend")
public class SearchFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchFriendServlet() {
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
		ArrayList<User> friend_list = new ArrayList<User>();
		HttpSession session = request.getSession(true);

		if (session != null) {

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int own_user_id = (int) session.getAttribute("user_id");
				String str = request.getParameter("nickname");
				if (str.length() > 0) {
					friend_list = friendbeans.searchFriend(own_user_id, str);
					out.println(JSON.encode(friend_list, true).toString());
				} else {
					out.println(JSON.encode(friend_list, true).toString());
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}
	}
}
