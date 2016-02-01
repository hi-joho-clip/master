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

@WebServlet("/updatemailaddress")
public class UpdateMailAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateMailAddressServlet() {
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

		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);

		User userbean = null;
		UserAuth userauth = new UserAuth();
		//boolean hantei = false;

		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();



		if (nonce.isNonce()) {
			if(request.getParameter("newemail")!=null &&request.getParameter("password")!=null){
				try {
					int user_id = (int) session.getAttribute("user_id");

					String inputmail = request.getParameter("newemail");
					String inputpass = request.getParameter("password");
					System.out.println("request受け取った" + inputmail);
					System.out.println("request受け取った" + inputpass);

					userbean = new User(user_id);
					//hantei = userauth.loginUserName(userbean.getUser_name(),inputpass);

					if (userauth.loginUserName(userbean.getUser_name(),inputpass)) {
						userbean.setMailaddress(inputmail);
						userbean.setPassword(inputpass);
						userbean.updateMailaddress();
						resp = "{\"state\": \"更新しました\",  \"flag\": 1}";
					} else {
						System.out.println("間違ってる");
						// パスワードが一致しなかった処理
						resp = "{\"state\": \"パスワードが間違ってます\",  \"flag\": 0}";
						//resp = "{\"state\": \"パスワードが間違ってます\",  \"flag\": 2}";
						//out.println(resp);
						//return;
					}
					out.println(resp);
					/*
					 * //メッセージ処理 if
					 * (userbean.getErrorMessages().containsKey("mailaddress")) {
					 * //メールアドレスが既に存在していたのでメッセージを出す
					 * System.out.println(userbean.getErrorMessages
					 * ().get("mailaddress")); }else { }
					 */

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}else{
				//newemail passwordがnullならunknownerror
				out.println(resp);
			}
		} else {
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);

		}
		//response.sendRedirect(URL + "/info.html");

	}
}
