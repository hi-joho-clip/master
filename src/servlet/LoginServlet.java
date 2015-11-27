package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beansdomain.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String inputid = request.getParameter("id");
		String inputpassword = request.getParameter("password");

		String hashedpassword = null;
		SaltUserPassword saltuserpassword = new SaltUserPassword();
		// 従業員IDとパスワードからハッシュ値を生成
		hashedpassword = saltuserpassword.getDigest(inputid, inputpassword);

		User userbeans = null;

		if(inputid != null){
			if(inputid.matches(".*@.*")){      //メールアドレスの場合

			}else{                             //ユーザIDの場合

			}
		}

	}
}
