package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.User;
import beansdomain.UserAuth;

@WebServlet("/updatenickname")
public class UpdateNicknameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateNicknameServlet() {
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

		HttpSession session = request.getSession(true);

		User userbean = null;
		UserAuth userauth = new UserAuth();
		// boolean hantei = false;
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);

		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		System.out.println("かと");

		if (nonce.isNonce()) {
			if (request.getParameter("newnickname") != null
					&& request.getParameter("password") != null) {
				if (request.getParameter("newnickname").length() < 33
						&& request.getParameter("newnickname").length() > 0
						&& request.getParameter("password").length() < 33
						&& request.getParameter("password").length() > 7) {
					try {
						int user_id = (int) session.getAttribute("user_id");

						String inputname = request.getParameter("newnickname");
						inputname = new String(inputname.getBytes("UTF-8"),
								"UTF-8");
						String inputpass = request.getParameter("password");
						inputpass = new String(inputpass.getBytes("UTF-8"),
								"UTF-8");
						System.out.println("request受け取った" + inputname);
						System.out.println("request受け取った" + inputpass);

						userbean = new User(user_id);

						if (userauth.loginUserName(userbean.getUser_name(),
								inputpass)) {
							userbean.setNickname(inputname);
							userbean.setPassword(inputpass);
							userbean.updateNickname();
							resp = "{\"state\": \"更新しました\",  \"flag\": 1}";

						} else {
							System.out.println("間違ってる");
							// パスワードが一致しなかった処理
							resp = "{\"state\": \"パスワードが間違ってます\",  \"flag\": 0}";
							// resp =
							// "{\"state\": \"パスワードが間違ってます\",  \"flag\": 2}";

						}

					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					out = response.getWriter();
					out.println(resp);
				}else{
					resp = "{\"state\": \"文字制限エラー\",  \"flag\": 0}";
				}
			} else {
				// newnickname passwordがnullならunknownerror
				out.println(resp);
			}
		} else {
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
		}
		// response.sendRedirect(URL + "/info.html");
	}

}
