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

@WebServlet("/friendlistff")
public class FriendListFFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FriendListFFServlet() {
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

	// フレンド登録一覧用
	private void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Friend friendbeans = new Friend();
		ArrayList<Friend> friend_list = new ArrayList<Friend>();
		ArrayList<Friend> request_friend_list = new ArrayList<Friend>();
		HttpSession session = request.getSession(true);

		if (session != null) {


			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int own_user_id = (int) session.getAttribute("user_id");
				friend_list = friendbeans.getFriend(own_user_id);

				for (int i = 0; i < friend_list.size(); i++) {
					if (friend_list.get(i).getStatus() != 1) {

						request_friend_list.add(friend_list.get(i));

					}
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(JSON.encode(request_friend_list, true).toString());
		}
	}
}