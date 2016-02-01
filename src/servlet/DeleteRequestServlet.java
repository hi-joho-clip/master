package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.Friend;

@WebServlet("/deleterequest")
public class DeleteRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteRequestServlet() {
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
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		String resp = "{\"state\": \"unknownError\",  \"flag\": 0}";
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Nonce nonce = new Nonce(request);

		int own_user_id = 0;
		int friend_user_id = 0;

		if (nonce.isNonce()) {

			try {
				if (session.getAttribute("user_id") != null) {
					own_user_id = (int) session.getAttribute("user_id");
				}
				if (request.getParameter("friend_user_id") != null) {
					friend_user_id = Integer.parseInt(request
							.getParameter("friend_user_id"));
				}

				if (own_user_id != 0 && friend_user_id != 0) {
					if (friendbeans.deleteRequest(own_user_id, friend_user_id)) {
						// 成功
						resp = "{\"state\": \"削除しました\",  \"flag\": 1, \"friend_user_id\": " + friend_user_id + "}";

					} else {
						// 失敗
						resp = "{\"state\": \"失敗しました\",  \"flag\": 0}";

					}
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(resp);
		} else {
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out.println(resp);
		}
	}
}
