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
		boolean hantei = false;
		String ErrorMessage = "{\"ErrorMessage\": 0}";;
		String resp = "{\"state\": \"unknown\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);

		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		System.out.println("加藤");


		if (session != null) {

			System.out.println("かと");

			if(nonce.isNonce()){

				try {
					int user_id = (int) session.getAttribute("user_id");

					String inputname = request.getParameter("newnickname");
					String inputpass = request.getParameter("password");
					System.out.println("request受け取った" + inputname);
					System.out.println("request受け取った" + inputpass);

					userbean = new User(user_id);
					hantei = userauth.loginUserName(userbean.getUser_name(),
							inputpass);

					if (hantei) {
						userbean.setNickname(inputname);
						userbean.setPassword(inputpass);
						userbean.updateNickname();
						resp = "{\"state\": \"更新しました\",  \"flag\": 1}";
					} else {
						System.out.println("間違ってる");
						// パスワードが一致しなかった処理
						resp = "{\"ErrorMessage\": \"パスワードが間違ってます\",  \"flag\": 2}";
						out.println(resp);
						return;
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}else{
				// 不正アクセス
				resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
				out = response.getWriter();
				out.println(resp);
			}

			response.sendRedirect(URL + "/UserInfo.html");
		}
	}

}
