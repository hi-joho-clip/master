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


@WebServlet("/updatepassword")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdatePasswordServlet() {
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
		String ErrorMessage = null;

		//本来では、セッション情報のユーザIDを取得
		int user_id = 2;

		String inputpass = request.getParameter("pass");
		String newpass = request.getParameter("newpass");


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();



		try {
			userbean = new User(user_id);

			if(inputpass.equals(userbean.getPassword())){
				userbean.setPassword(newpass);
				userbean.updatePassword();
			}else{
				//パスワードが一致しなかった処理
			}


			//メッセージ処理
			if (userbean.getErrorMessages().containsKey("password")) {
				System.out.println(userbean.getErrorMessages().get("password"));
				ErrorMessage = userbean.getErrorMessages().get("password");
			}else {

				ErrorMessage = "無事,パスワードを更新できました。";
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(ErrorMessage , true).toString());
	}

}