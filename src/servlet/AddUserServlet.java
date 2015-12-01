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

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddUserServlet() {
        super();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userbean = new User();
		String ErrorMessage = null;

		String user_name = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String inputpass = request.getParameter("password"); //パスワードを取得
		String inputmail = request.getParameter("mailaddress"); //メールアドレス取得


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		userbean.setUser_name(user_name);
		userbean.setNickname(nickname);
		userbean.setPassword(inputpass);
		userbean.setMailaddress(inputmail);


		try {
			userbean.addUser();

			if (userbean.getErrorMessages().containsKey("user_name")) {
				System.out.println(userbean.getErrorMessages().get("user_name"));
				ErrorMessage = userbean.getErrorMessages().get("user_name");
			}
			if (userbean.getErrorMessages().containsKey("mailaddress")) {
				System.out.println(userbean.getErrorMessages().get("mailaddress"));
				ErrorMessage = userbean.getErrorMessages().get("mailaddress");
			}
			if (userbean.getErrorMessages().containsKey("nickname")) {
				System.out.println(userbean.getErrorMessages().get("nickname"));
				ErrorMessage = userbean.getErrorMessages().get("nickname");
			}
			if(ErrorMessage == null){
				ErrorMessage = "無事新規作成できました。";
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(ErrorMessage , true).toString());
	}

}
