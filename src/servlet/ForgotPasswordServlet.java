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

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForgotPasswordServlet() {
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

		Cookie cookie[] = request.getCookies();
		Cookie visitedCookie = null;
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

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



			String pass = session.getAttribute("nonce").toString().substring(0,8);
			System.out.println(pass);


			if(request.getParameter("email")!=null&&request.getParameter("username")!=null&&request.getParameter("birth")!=null){
				try {
					String inputmail = request.getParameter("email");
					String inputuser_name = request.getParameter("username");
					String inputbirth = request.getParameter("birth");
					userbean.setMailaddress(inputmail);
					userbean.setUser_name(inputuser_name);
					userbean.setBirth(inputbirth);

					hantei = userbean.searchPass();

					if (hantei) {
						userbean.setPassword(pass);
						userbean.reissuePass();

						// 成功した場合、クッキー情報、削除
						for (int i = 0; i < cookie.length; i++) {
							if (cookie[i].getName().equals("ForgotPASS")) {
								visitedCookie = cookie[i];
								visitedCookie.setPath("/");
								visitedCookie.setMaxAge(0);
								response.addCookie(visitedCookie);
							}
						}

					} else {


						if (cookie != null) {
							for (int i = 0; i < cookie.length; i++) {
								if (cookie[i].getName().equals("ForgotPASS")) {
									visitedCookie = cookie[i];
								}
							}
							if (visitedCookie == null) {
								Cookie newCookie = new Cookie("ForgotPASS", "1");
								newCookie.setPath("/");
								newCookie.setMaxAge(300);
								response.addCookie(newCookie);
							} else if (Integer.parseInt(visitedCookie.getValue()) < 4) {
								visitedCookie.setPath("/");
								int visited = Integer.parseInt(visitedCookie.getValue()) + 1;
								visitedCookie.setValue(Integer.toString(visited));
								visitedCookie.setMaxAge(300);
								response.addCookie(visitedCookie);
							} else {
								visitedCookie.setPath("/");
								int visited = Integer.parseInt(visitedCookie.getValue()) + 1;
								visitedCookie.setValue(Integer.toString(visited));
								visitedCookie.setMaxAge(300);
								response.addCookie(visitedCookie);

								Cookie c_lock = new Cookie("lock_pass", "true");
								c_lock.setMaxAge(300);
								response.addCookie(c_lock);
							}
						} else {
							Cookie newCookie = new Cookie("ForgotPASS", "1");
							newCookie.setPath("/");
							newCookie.setMaxAge(300);
							response.addCookie(newCookie);
						}



						// 一致しなかった処理
						response.sendRedirect(URL + "/ForgotPassword.html");
						return;
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				out.println(JSON.encode(pass, true).toString());
				response.sendRedirect(URL + "/login.html");
			}else{
				out.println(JSON.encode("unknownError" , true).toString());
				System.out.println("unknownerror");
			}
		}else{
			// 不正アクセス
			out.println(JSON.encode("不正なアクセスです" , true).toString());
			System.out.println("husei");
		}
	}
}
