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

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteUserServlet() {
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
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);
		HttpSession session = request.getSession(true);
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		if (nonce.isNonce()) {


			try {
				int user_id = (int) session.getAttribute("user_id");
				userbean.setUser_id(user_id);
				if(userbean.deleteUser()){
					//削除リダイレクト
				}else{
					resp = "{\"state\": \"失敗しました\",  \"flag\": 0}";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(resp);
			//out.println(JSON.encode(ErrorMessage, true).toString());
			//response.sendRedirect(URL + "/Login.html");
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
		}
	}
}
