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

		Cookie cookie[] = request.getCookies();
		Cookie visitedCookie = null;

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


				// 成功した場合、クッキー情報、削除
				for (int i = 0; i < cookie.length; i++) {
					if (cookie[i].getName().equals("LOCK_USERID")) {
						visitedCookie = cookie[i];
						visitedCookie.setPath("/");
						visitedCookie.setMaxAge(0);
						response.addCookie(visitedCookie);
					}
				}

			} else {

				if (cookie != null) {
					for (int i = 0; i < cookie.length; i++) {
						if (cookie[i].getName().equals("LOCK_USERID")) {
							visitedCookie = cookie[i];
						}
					}
					if (visitedCookie == null) {
						Cookie newCookie = new Cookie("LOCK_USERID", "1");
						newCookie.setPath("/");
						newCookie.setMaxAge(300);
						response.addCookie(newCookie);
					} else if (Integer.parseInt(visitedCookie.getValue()) < 4) {
						visitedCookie.setPath("/");
						int visited = Integer.parseInt(visitedCookie.getValue()) + 1;
						visitedCookie.setValue(Integer.toString(visited));
						visitedCookie.setMaxAge(300);
						response.addCookie(visitedCookie);
					} else {
						visitedCookie.setPath("/");
						int visited = Integer.parseInt(visitedCookie.getValue()) + 1;
						visitedCookie.setValue(Integer.toString(visited));
						visitedCookie.setMaxAge(300);
						response.addCookie(visitedCookie);

						Cookie c_lock = new Cookie("lock_user", "true");
						c_lock.setMaxAge(300);
						response.addCookie(c_lock);
					}
				} else {
					Cookie newCookie = new Cookie("LOCK_USERID", "1");
					newCookie.setPath("/");
					newCookie.setMaxAge(300);
					response.addCookie(newCookie);
				}


				// 一致しなかった処理
				response.sendRedirect(URL + "/UserID.html");
				return;
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		response.sendRedirect(URL + "/Login.html");
	}
}
