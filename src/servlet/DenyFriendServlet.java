package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;
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

		User userbean = new User();
		String Message = null;
		HttpSession session = request.getSession(false);
		String URL = "/clipMaster/login";

		if (session != null) {

			int user_id = (int) session.getAttribute("user_id");

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				userbean.setUser_id(user_id);
				userbean.friend_deny();
				Message = "フレンド申請拒否設定にしました。";
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(JSON.encode(Message, true).toString());
			response.sendRedirect(URL + "/UserInfo.html");
		}
	}
}