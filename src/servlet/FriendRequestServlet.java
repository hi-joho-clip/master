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

@WebServlet("/friendrequest")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FriendRequestServlet() {
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

	// フレンド承認・否認 状態コード:1 を取得
	private void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Friend friendbeans = new Friend();
		ArrayList<Friend> friend_list = new ArrayList<Friend>();
		ArrayList<Friend> request_friend_list = new ArrayList<Friend>();
		HttpSession session = request.getSession(true);

		response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

		String username = (String)session.getAttribute("username");
		if (username != null) {
			try {
				int own_user_id = (int) session.getAttribute("user_id");
				friend_list = friendbeans.getFriendList(own_user_id);
				for (int i = 0; i < friend_list.size(); i++) {
					if (friend_list.get(i).getStatus() == 1) {

						request_friend_list.add(friend_list.get(i));

						// setFriend_id(friend_list.get(i).getFriend_id());
						// request_friend_list.get(i).setOwn_user_id(friend_list.get(i).getOwn_user_id());
						// request_friend_list.get(i).setFriend_user_id(friend_list.get(i).getFriend_user_id());
						// request_friend_list.get(i).setShare_id(friend_list.get(i).getShare_id());
						// request_friend_list.get(i).setStatus(friend_list.get(i).getStatus());
						// request_friend_list.get(i).setModified(friend_list.get(i).getModified());
						// request_friend_list.get(i).setCreated(friend_list.get(i).getCreated());
						// request_friend_list.get(i).setAcceptdate(friend_list.get(i).getAcceptdate());
					}
				}

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				//e.printStackTrace();
			}
			out.println(JSON.encode(request_friend_list, true).toString());
		} else {
			String resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
			out.print(resp);
		}
	}

}
