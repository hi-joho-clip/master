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

		String URL = "/clipMaster";

		System.out.println("login");

		UserAuth userauth = new UserAuth();
		User userbeans = null;
		int user_id = -1;
		boolean hantei = false;

		if (inputid != null) {
			if (inputid.matches(".*@.*")) { // メールアドレスの場合
				try {
					hantei = userauth.loginMailaddress(inputid, inputpass);
					user_id = userauth.getUser_id();
					userbeans = new User(user_id);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else { // ユーザ名の場合
				try {
					hantei = userauth.loginUserName(inputid, inputpass);
					user_id = userauth.getUser_id();
					userbeans = new User(user_id);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
			if (hantei) { // ログイン成功

				UUID guid = UUID.randomUUID();
				UUID nonce = UUID.randomUUID();
				Calendar cal = Calendar.getInstance();

				/**
				 * CSRF対策
				 */
				Cookie c_guid = new Cookie("guid", guid.toString());
				Cookie c_nonce = new Cookie("nonce", nonce.toString());
				Cookie c_start_time = new Cookie("start_time", Long.toString(cal.getTimeInMillis()));
				response.addCookie(c_guid);
				response.addCookie(c_nonce);
				response.addCookie(c_start_time);

				//URLは絶対パスで書かない。
				// 本番環境でURL=nullにすれば簡単に動く
				//マイリスト画面に移動
				response.sendRedirect(URL + "/login.jsp");
			} else {
				// パスワードが一致しなかったので再入力させる。
				response.sendRedirect(URL + "/login.jsp");
			}
		} else {
			// IDが入力されなかったので再入力させる。
			response.sendRedirect(URL + "/login.jsp");
		}
	}
}
