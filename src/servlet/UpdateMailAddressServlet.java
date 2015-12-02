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


@WebServlet("/updatemailaddress")
public class UpdateMailAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateMailAddressServlet() {
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

		String inputmail = request.getParameter("mailaddress");
		String inputpass = request.getParameter("password");


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();



		try {
			userbean = new User(user_id);

			if(inputpass.equals(userbean.getPassword())){
				userbean.setMailaddress(inputmail);
				userbean.updateMailaddress();
			}else{
				//パスワードが一致しなかった処理
			}


			//メッセージ処理
			if (userbean.getErrorMessages().containsKey("mailaddress")) {
				//メールアドレスが既に存在していたので  エラーメッセージを出す
				System.out.println(userbean.getErrorMessages().get("mailaddress"));
				ErrorMessage = userbean.getErrorMessages().get("mailaddress");
			}else {

				ErrorMessage = "無事、メールアドレスを更新できました。";
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(ErrorMessage , true).toString());
	}

}
