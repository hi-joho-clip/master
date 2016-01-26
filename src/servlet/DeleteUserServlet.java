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

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteUserServlet() {
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
		String ErrorMessage = null;

		HttpSession session = request.getSession(true);

		if (session != null) {

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int user_id = (int) session.getAttribute("user_id");
				userbean.setUser_id(user_id);
				userbean.deleteUser();

			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(JSON.encode(ErrorMessage, true).toString());
		}
	}
}
