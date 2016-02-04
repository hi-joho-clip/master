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

@WebServlet("/viewuser")
public class ViewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewUserServlet() {
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

		User userbeans = null;
		User n_user = new User();

		HttpSession session = request.getSession(true);

		if (session != null) {

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				int user_id = (int) session.getAttribute("user_id");
				userbeans = new User(user_id);
				n_user.setNickname(userbeans.getNickname());
				n_user.setMailaddress(userbeans.getMailaddress());

			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(JSON.encode(n_user, true).toString());
		}
	}

}
