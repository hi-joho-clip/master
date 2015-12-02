package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
						System.out.println("メールアドレス入力された" + inputid);
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					if (hantei) { // ログイン成功
						// ユーザ情報を格納するセッションを生成
						HttpSession sessionuser = request.getSession(true);
						// そのセッションに従業員情報のオブジェクトを格納
						sessionuser.setAttribute("User", userbeans);

						// ログイン後の宛先
						/*
						 * request.getRequestDispatcher("/MyListServlet").forward
						 * (request, response);
						 */
						request.getRequestDispatcher("login/Articletest.html").forward(request, response);
					} else {
						// パスワードが一致しなかったので再入力させる。
						request.getRequestDispatcher("login/Login.html").forward(request, response);
						System.out.println("記入ミス(メール)" + inputpass);
					}

				} else { // ユーザ名の場合
					try {
						hantei = userauth.loginUserName(inputid, inputpass);
						user_id = userauth.getUser_id();
						userbeans = new User(user_id);
						System.out.println("ユーザ名入力された" + inputid);
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					if (hantei) { // ログイン成功
						// ユーザ情報を格納するセッションを生成
						HttpSession sessionuser = request.getSession(true);
						// そのセッションに従業員情報のオブジェクトを格納
						sessionuser.setAttribute("User", userbeans);

						// ログイン後の宛先
						/*
						 * request.getRequestDispatcher("/MyListServlet").forward
						 * (request, response);
						 */
						request.getRequestDispatcher("login/Articletest.html").forward(request, response);
					} else {
						// パスワードが一致しなかったので再入力させる。
						request.getRequestDispatcher("login/Login.html").forward(request, response);
						System.out.println("記入ミス(ユーザ)" + inputpass);
					}
				}
			} else {
				// IDが入力されなかったので再入力させる。
				request.getRequestDispatcher("login/Login.html").forward(request, response);
			}
	}
}
