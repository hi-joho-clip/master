package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;

import beansdomain.User;

@WebServlet("/fogotpassword")
public class FogotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FogotPasswordServlet() {
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

		System.out.println(session.getAttribute("nonce").toString());
		System.out.println(request.getParameter("nonce").toString());
		// nonceの検証が必要です。
		Nonce nonce = new Nonce(request);
		// url =
		// "https://ja.wikipedia.org/wiki/%E3%81%84%E3%81%A1%E3%81%94100%25";
		if (nonce.isNonce()) {
			User userbean = new User();
			boolean hantei = false;
			String URL = request.getContextPath() + "/login";

			String inputmail = request.getParameter("email");
			String inputuser_name = request.getParameter("username");
			String inputbirth = request.getParameter("birth");

			String pass = session.getAttribute("nonce").toString().substring(0,8);
			System.out.println(pass);

			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Cache-Control", "private");
			PrintWriter out = response.getWriter();

			try {
				userbean.setMailaddress(inputmail);
				userbean.setUser_name(inputuser_name);
				userbean.setBirth(inputbirth);

				hantei = userbean.searchPass();

				if (hantei) {
					userbean.setPassword(pass);
					userbean.reissuePass();
				} else {
					// 一致しなかった処理
					response.sendRedirect(URL + "/ForgotPassword.html");
					return;
				}

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			out.println(JSON.encode(pass, true).toString());
			response.sendRedirect(URL + "/Login.html");
		}
	}
}
