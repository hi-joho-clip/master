package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;

import beansdomain.User;

@WebServlet("/viewuserid")
public class ViewUserIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewUserIDServlet() {
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
		boolean hantei = false;
		String URL = request.getContextPath() + "/login";
		int user_id = 0;

		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		try {
			String inputmail = request.getParameter("email");
			String inputpass = request.getParameter("password");
			userbean.setMailaddress(inputmail);
			userbean.setPassword(inputpass);

			System.out.println(userbean.getMailaddress());
			System.out.println(userbean.getPassword());

			hantei = userbean.UserID();

			user_id = userbean.getUser_id();
			System.out.println(user_id);

			if (hantei) {
				// メールを送る処理を書く
			} else {
				// 一致しなかった処理
				response.sendRedirect(URL + "/UserID.html");
				return;
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(user_id, true).toString());
		response.sendRedirect(URL + "/Login.html");
	}
}
