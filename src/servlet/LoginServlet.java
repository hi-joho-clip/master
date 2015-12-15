package servlet;

import java.io.IOException;
import java.util.Date;
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
				Date date = new Date();

				/**
				 * CSRF対策
				 */
				Cookie c_guid = new Cookie("guid",guid.toString());
				response.addCookie(c_guid);
				Cookie c_nonce = new Cookie("nonce", nonce.toString());
				response.addCookie(c_nonce);
				Cookie c_start_time = new Cookie("start_time",date.toString());
				response.addCookie(c_start_time);

				request.getRequestDispatcher("login/index.html").forward(request, response);
			} else {
				// パスワードが一致しなかったので再入力させる。
				request.getRequestDispatcher("login/Login.html").forward(request, response);
			}
		} else {
			// IDが入力されなかったので再入力させる。
			request.getRequestDispatcher("login/Login.html").forward(request,
					response);
		}
	}
}
