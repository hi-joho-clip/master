package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import beansdomain.User;

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUserServlet() {
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
		String ErrorMessage = null;
		String URL = request.getContextPath() + "/login";
		String resp = "{\"ErrorMessage\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		Nonce nonce = new Nonce(request);
		System.out.println(request.getParameter("nonce"));
		if (nonce.isNonce()) {
			if (request.getParameter("username") != null&& request.getParameter("nickname") != null&& request.getParameter("birth") != null&& request.getParameter("email") != null&& request.getParameter("password") != null) {
				String user_name = request.getParameter("username");
				String nickname = request.getParameter("nickname");
				String birth = request.getParameter("birth");
				String inputmail = request.getParameter("email"); // メールアドレス取得
				String inputpass = request.getParameter("password"); // パスワードを取得

				userbean.setUser_name(user_name);
				userbean.setNickname(nickname);
				userbean.setBirth(birth);
				userbean.setMailaddress(inputmail);
				userbean.setPassword(inputpass);


				try {
					if (userbean.addUser()) {
						// 成功処理
						System.out.println("登録した");
						ErrorMessage = "登録完了しました。";
						resp = "{\"ErrorMessage\":\"登録完了 \",  \"flag\": 1}";
						out = response.getWriter();
						out.println(resp);
						//response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
						//response.setContentType("text/html; charset=Shift_JIS");
						//response.setHeader("Location",URL+"/login.html");
						//response.sendRedirect(URL + "/login.html");
					} else {
						if (userbean.getErrorMessages().containsKey("user_name") && userbean.getErrorMessages().containsKey("mailaddress")) {
							System.out.println(userbean.getErrorMessages().get("user_name"));
							System.out.println(userbean.getErrorMessages().get("mailaddress"));
							ErrorMessage = userbean.getErrorMessages().get("user_name") + userbean.getErrorMessages().get("mailaddress");
							resp = "{\"ErrorMessage\":\"" + ErrorMessage + "\",  \"flag\": 0}";
							out = response.getWriter();
							out.println(resp);
						}
						if (userbean.getErrorMessages().containsKey("user_name")) {
							System.out.println(userbean.getErrorMessages().get("user_name"));
							ErrorMessage = userbean.getErrorMessages().get("user_name");
							resp = "{\"ErrorMessage\":\"" + ErrorMessage + "\",  \"flag\": 0}";
							out = response.getWriter();
							out.println(resp);
						}
						if (userbean.getErrorMessages().containsKey("mailaddress")) {
							System.out.println(userbean.getErrorMessages().get("mailaddress"));
							ErrorMessage = userbean.getErrorMessages().get("mailaddress");
							resp = "{\"ErrorMessage\":\"" + ErrorMessage + "\",  \"flag\": 0}";
							out = response.getWriter();
							out.println(resp);
						}
					}


				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				out.println(JSON.encode("unknownError", true).toString());
				System.out.println("unknownerror");
			}
		} else {
			// 不正アクセス
			resp = "{\"ErrorMessage\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
			System.out.println("husei");
		}

	}

}
