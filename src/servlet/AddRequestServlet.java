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

@WebServlet("/addrequest")
public class AddRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddRequestServlet() {
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
		Nonce nonce = new Nonce(request);
		PrintWriter out = response.getWriter();
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		ArrayList<Friend> friend_list = new ArrayList<Friend>();
		int friend_user_id = 0;
		int own_user_id = 0;
		int cnt = 0;
		int friend = 0;
		int sum = 0;
		if (nonce.isNonce()) {
			if (request.getParameter("friend_user_id") != null) {
				try {
					own_user_id = (int) session.getAttribute("user_id");
					friend_user_id = Integer.parseInt(request
							.getParameter("friend_user_id"));
					friend_list = friendbeans.getFriendList(own_user_id);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				for (int i = 0; friend_list.size() > i; i++) {
					if (friend_list.get(i).getStatus() != 1) {
						sum += 1;
						if (friend_list.get(i).getStatus() == 2) {
							cnt += 1;
						} else {
							friend += 1;
						}
					}

				}
				if (friend < 50) {
					if (sum < 50) {
						if (cnt < 10) {
							User get_nickname = new User(friend_user_id);
							String nickname = get_nickname.getNickname();
							// flag = friendbeans.addRequest(own_user_id,
							// friend_user_id);
							try {
								if (friendbeans.addRequest(own_user_id,
										friend_user_id)) {
									resp = "{\"state\": \"成功しました\", \"flag\": 1,\"nickname\" : \""
											+ nickname + "\"}";
								} else {
									resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
								}
							} catch (Exception e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}
						} else {
							resp = "{\"state\": \"申請数オーバー\", \"flag\": 0}";
						}
					}else{
						resp = "{\"state\": \"申請削除する必要あり\", \"flag\": 0}";
					}
				} else {
					resp = "{\"state\": \"フレンド数オーバー\", \"flag\": 0}";
				}
				out.println(resp);
			} else {
				// friend_user_idがない場合のメソッドunknownError
				out.println(resp);
			}
			// out.println(JSON.encode(flag, true).toString());
		} else {
			// nonceがない場合のメソッド
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out.println(resp);
		}
	}
}
