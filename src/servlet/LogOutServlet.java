package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String URL = "/clipMaster";

		//String inputbutton = request.getParameter("button");
		//セッションを取り出す
		HttpSession sessionadmin = request.getSession(false);

		try {//セッションがnullでないなら（つまりまだログインしているなら）
			if (sessionadmin != null) {

				//セッションを無効にする
				sessionadmin.invalidate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(URL + "/login/index.html").forward(request, response);

	}

}