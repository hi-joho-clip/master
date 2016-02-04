package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


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

		//String inputbutton = request.getParameter("button");
		//セッションを取り出す
		HttpSession sessionadmin = request.getSession(false);

		try {//セッションがnullでないなら（つまりまだログインしているなら）
			if (sessionadmin != null) {
				//セッションを無効にする
				sessionadmin.invalidate();
			}

			// クッキーをすべて削除する
			Cookie cookies[] = ((HttpServletRequest)request).getCookies();
			if(cookies != null) {
				for(int i = 0; i < cookies.length; i++) {

					// クッキーの有効期間を0秒に設定する
					cookies[i].setMaxAge(0);
		                        ((HttpServletResponse)response).addCookie(cookies[i]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}