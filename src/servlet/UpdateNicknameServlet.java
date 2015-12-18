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
import beansdomain.UserAuth;


@WebServlet("/updatenickname")
public class UpdateNicknameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateNicknameServlet() {
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

	private void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User userbean = null;
		UserAuth userauth = new UserAuth();
		boolean hantei = false;

		int user_id = Integer.parseInt(request.getParameter("user_id"));

		String inputname = request.getParameter("newnickname");
		String inputpass = request.getParameter("password");


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();



		try {
			userbean = new User(user_id);
			hantei = userauth.loginUserName(userbean.getUser_name(), inputpass);

			if(hantei){
				userbean.setNickname(inputname);
				userbean.setPassword(inputpass);
				userbean.updateNickname();
			}else{
				//パスワードが一致しなかった処理
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(userbean , true).toString());
		response.sendRedirect("http://localhost:8080/clipMaster/login/UserInfo.html");
	}

}
