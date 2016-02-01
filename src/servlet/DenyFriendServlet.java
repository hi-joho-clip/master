package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.User;

@WebServlet("/denyfriend")
public class DenyFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DenyFriendServlet() {
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

		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);
		User userbean = new User();
		String Message = null;
		HttpSession session = request.getSession(true);
		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		if (nonce.isNonce()) {

			try {
				int user_id = (int) session.getAttribute("user_id");
				userbean.setUser_id(user_id);
				if(userbean.friend_deny()){
					resp = "{\"state\": \"申請拒否しました\",  \"flag\": 1}";
				}else{
					resp = "{\"state\": \"失敗しました\",  \"flag\": 0}";
				}
				//Message = "フレンド申請拒否設定にしました。";

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(resp);
			//out.println(JSON.encode(Message, true).toString());
			//response.sendRedirect(URL + "/UserInfo.html");
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
		}

	}
}