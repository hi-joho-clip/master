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

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteUserServlet() {
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


		//本来では、セッション情報のユーザIDを取得
		int user_id = 2;

		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();


		try {
			userbean.setUser_id(user_id);
			userbean.deleteUser();


			//メッセージを出す処理
			if (userbean.getErrorMessages().containsKey("user_name")) {
				System.out.println(userbean.getErrorMessages().get("user_name"));
				ErrorMessage = userbean.getErrorMessages().get("user_name");
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(ErrorMessage , true).toString());
	}

}
