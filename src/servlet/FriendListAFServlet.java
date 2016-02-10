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

@WebServlet("/friendlistaf")
public class FriendListAFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FriendListAFServlet() {
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
		String resp = "{\"cnt\": 0,\"size\": 0, \"flag\": 0}";
		int cnt = 0;
		int size = 0;

		if (session != null) {

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int own_user_id = (int) session.getAttribute("user_id");
				friend_list = friendbeans.getFriendList(own_user_id);

				for (int i = 0; i < friend_list.size(); i++) {
					if (friend_list.get(i).getStatus() != 1) {
						friend_list.get(i).setUser_name("");
						request_friend_list.add(friend_list.get(i));
						size +=1;
						if (friend_list.get(i).getStatus() == 2) {
							cnt += 1;
						}
					}
				}

				if(size > 49){
					//フレンド数上限オーバー
					resp = "{\"cnt\":"+ cnt +",\"size\":"+ size +", \"flag\": 2}";
				}else if(cnt > 9){
					//フレンド申請オーバー
					resp = "{\"cnt\":"+ cnt +",\"size\":"+ size +", \"flag\": 1}";
				}else if(size < 50 && cnt < 10){
					//申請可能
					resp = "{\"cnt\":"+ cnt +",\"size\":"+ size +", \"flag\": 0}";
				}
				out = response.getWriter();
				out.println(resp);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				// e.printStackTrace();
			}
		}
	}
}