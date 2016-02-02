package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.User;
import beansdomain.UserAuth;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
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

		String inputid = request.getParameter("mail_or_name");
		String inputpass = request.getParameter("password");
		System.out.println("inputid:"+inputid+"inputpass:"+inputpass);
		String URL = request.getContextPath() + "/login";

		UserAuth userauth = new UserAuth();
		User userbeans = null;
		int user_id = -1;
		boolean hantei = false;
		HttpSession loginsession = request.getSession(true);

		Cookie cookie[] = request.getCookies();
		Cookie visitedCookie = null;

		if (inputid.matches(".*@.*")) { // メールアドレスの場合
			try {
				hantei = userauth.loginMailaddress(inputid, inputpass);
				if (hantei) {
					user_id = userauth.getUser_id();
					userbeans = new User(user_id);
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} else { // ユーザ名の場合
			try {
				hantei = userauth.loginUserName(inputid, inputpass);
				System.out.println(userauth.loginUserName(inputid, inputpass));
				if (hantei) {
					user_id = userauth.getUser_id();
					userbeans = new User(user_id);
				}

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		if (hantei) { // ログイン成功
			System.out.println("login sita");
			UUID guid = UUID.randomUUID();
			UUID nonce = UUID.randomUUID();
			Calendar cal = Calendar.getInstance();

			loginsession.setAttribute("username", userbeans.getUser_name());
			loginsession.setAttribute("user_id", userbeans.getUser_id());
			loginsession.setAttribute("nonce", nonce.toString());

			/**
			 * CSRF対策
			 */
			Cookie c_guid = new Cookie("guid", guid.toString());
			Cookie username = new Cookie("username", userbeans.getUser_name());
			Cookie c_nonce = new Cookie("nonce", nonce.toString());
			Cookie c_start_time = new Cookie("start_time", Long.toString(cal
					.getTimeInMillis()));

			response.addCookie(c_guid);
			response.addCookie(c_nonce);
			response.addCookie(c_start_time);
			response.addCookie(username);
			loginsession.setAttribute("guid", guid.toString());

			// 成功した場合、クッキー情報、削除
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("visited")) {
					visitedCookie = cookie[i];
					visitedCookie.setPath("/");
					visitedCookie.setMaxAge(0);
					response.addCookie(visitedCookie);
				}

				if (cookie[i].getName().equals("LOCK_USERID")) {
					visitedCookie = cookie[i];
					visitedCookie.setPath("/");
					visitedCookie.setMaxAge(0);
					response.addCookie(visitedCookie);
				}

				if (cookie[i].getName().equals("ForgotPASS")) {
					visitedCookie = cookie[i];
					visitedCookie.setPath("/");
					visitedCookie.setMaxAge(0);
					response.addCookie(visitedCookie);
				}

				if (cookie[i].getName().equals("lock_pass")) {
					visitedCookie = cookie[i];
					visitedCookie.setPath("/");
					visitedCookie.setMaxAge(0);
					response.addCookie(visitedCookie);
				}

				if (cookie[i].getName().equals("lock_user")) {
					visitedCookie = cookie[i];
					visitedCookie.setPath("/");
					visitedCookie.setMaxAge(0);
					response.addCookie(visitedCookie);
				}
			}


			// URLは絶対パスで書かない。
			// 本番環境でURL=nullにすれば簡単に動く
			// マイリスト画面に移動
			System.out.println("ログインできた");
			response.sendRedirect(URL + "/index.html");
		} else {
			System.out.println("dame");
			if (cookie != null) {
				for (int i = 0; i < cookie.length; i++) {
					if (cookie[i].getName().equals("visited")) {
						visitedCookie = cookie[i];
					}
				}
				if (visitedCookie == null) {
					Cookie newCookie = new Cookie("visited", "1");
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

					Cookie c_lock = new Cookie("lock", "true");
					c_lock.setMaxAge(300);
					response.addCookie(c_lock);
				}
			} else {
				Cookie newCookie = new Cookie("visited", "1");
				newCookie.setPath("/");
				newCookie.setMaxAge(300);
				response.addCookie(newCookie);
			}

			// IDとパスワードが一致しなかったので再入力させる。
			response.sendRedirect(URL + "/login.html");
		}
	}
}
