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

import beansdomain.Friend;
import beansdomain.User;

@WebServlet("/acceptrequest")
public class AcceptRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AcceptRequestServlet() {
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
		// boolean flag = false;
		HttpSession session = request.getSession(true);
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		ArrayList<Friend> friend_list = new ArrayList<Friend>();
		int own_user_id = 0;
		int cnt = 0; // フレンド数
		Nonce nonce = new Nonce(request);
		PrintWriter out = response.getWriter();
		if (nonce.isNonce()) {
			if (request.getParameter("friend_user_id") != null) {
				try {
					own_user_id = (int) session.getAttribute("user_id");
					int friend_user_id = Integer.parseInt(request
							.getParameter("friend_user_id"));
					// flag = friendbeans.acceptRequest(own_user_id,
					// friend_user_id);

					for (int i = 0; friend_list.size() > i; i++) {
						if (friend_list.get(i).getStatus() == 3) {
							cnt += 1;
						}
					}
					if (cnt < 50) {

						User get_nickname = new User(friend_user_id);
						String nickname = get_nickname.getNickname();
						if (friendbeans.acceptRequest(own_user_id,
								friend_user_id)) {
							resp = "{\"state\": \"成功しました\", \"flag\": 1,\"nickname\" : \""
									+ nickname + "\"}";
							// out.println(JSON.encode(flag, true).toString());
							// response.sendRedirect("http://localhost:8080/clipMaster/login/FriendBox.html");
						} else {
							resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
						}
					} else {
						resp = "{\"state\": \"フレンド数オーバー\", \"flag\": 0}";
					}
					// out.println(JSON.encode(Message, true).toString());
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				out.println(resp);
			} else {
				// friend_user_idがない場合のメソッドunknownError
				out.println(resp);
			}
		} else {
			// nonceがない場合のメソッド
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out.println(resp);
		}
	}
}