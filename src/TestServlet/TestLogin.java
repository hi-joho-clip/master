package TestServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * アクセスするだけで無条件でユーザ1でログインしたことになる
 * @author Takuya
 *
 */
@WebServlet("/autologin")
public class TestLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestLogin() {
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



//		User userbeans = new User(1);
//
//		UUID guid = UUID.randomUUID();
//		UUID nonce = UUID.randomUUID();
//		Calendar cal = Calendar.getInstance();
//
//		System.out.println("testlogin");
//
//		HttpSession loginsession = request.getSession(true);
//		loginsession.setAttribute("username", userbeans.getUser_name());
//		loginsession.setAttribute("user_id", userbeans.getUser_id());
//		loginsession.setAttribute("nonce", nonce.toString());
//
//		/**
//		 * CSRF対策
//		 */
//		Cookie c_guid = new Cookie("guid", guid.toString());
//		Cookie username = new Cookie("username", userbeans.getUser_name());
//		Cookie c_nonce = new Cookie("nonce", nonce.toString());
//		Cookie c_start_time = new Cookie("start_time", Long.toString(cal.getTimeInMillis()));
//		response.addCookie(c_guid);
//		response.addCookie(c_nonce);
//		response.addCookie(c_start_time);
//		response.addCookie(username);
//		loginsession.setAttribute("guid", guid.toString());
//
//
//		// セッションの有効期限を2日にする
//		loginsession.setMaxInactiveInterval(172800);

		//response.sendRedirect(request.getContextPath() + "/login/index.html");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");

		String resp = "{\"redirect\": \"true\", \"redirect_url\": \"/login/index.html\"}";
		PrintWriter out = response.getWriter();
		out.println(resp);



	}
}
