package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/getnonce")
public class GetNonceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetNonceServlet() {
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

		HttpSession loginsession = request.getSession(true);
		UUID nonce = UUID.randomUUID();
		loginsession.setAttribute("nonce", nonce.toString());

		Cookie c_nonce = new Cookie("nonce", nonce.toString());
		response.addCookie(c_nonce);

		String resp = "{\"state\": \"ok\"}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		out.print(resp);
		out.close();

	}
}
