package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.User;
import beansdomain.UserAuth;

@WebServlet("/login")
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

		String inputid = request.getParameter("user_name");
		String inputmailaddress = request.getParameter("mailaddress");
		String inputpassword = request.getParameter("password");

		UserAuth userauth = new UserAuth();
		User userbeans = null;
		int user_id = -1;


		if(inputid != null){
			if(inputid.matches(".*@.*")){      //メールアドレスの場合



			}else{                             //ユーザIDの場合
				try {
					user_id = Integer.parseInt(inputid);
					userbeans = new User(user_id);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				if(userauth.login()){         //ログイン成功
					//ユーザ情報を格納するセッションを生成
					HttpSession sessionuser = request.getSession(true);
					//そのセッションに従業員情報のオブジェクトを格納
					sessionuser.setAttribute("User", userbeans);

					//ログイン後の宛先
					request.getRequestDispatcher("/.jsp").forward(request, response);
				}else{
					//パスワードが一致しなかったので再入力させる。
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			}
		}else{
			//IDが入力されなかったので再入力させる。
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

	}
}
