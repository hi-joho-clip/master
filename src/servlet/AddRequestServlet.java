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
import beansdomain.Friend;

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
		boolean flag = false;
		HttpSession session = request.getSession(true);

		if (session != null) {

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int own_user_id = (int) session.getAttribute("user_id");
				int friend_user_id = Integer.parseInt(request
						.getParameter("friend_user_id"));
				flag = friendbeans.addRequest(own_user_id, friend_user_id);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(JSON.encode(flag, true).toString());
		}
	}
}
