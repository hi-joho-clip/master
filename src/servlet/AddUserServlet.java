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

@WebServlet("/adduser")
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
		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		System.out.println("アカウント新規作成");
		Nonce nonce = new Nonce(request);
		if (nonce.isNonce()) {
			if(request.getParameter("username")!=null&&request.getParameter("nickname")!=null&&request.getParameter("birth")!=null&&request.getParameter("email")!=null&&request.getParameter("password")!=null){
				String user_name = request.getParameter("username");
				String nickname = request.getParameter("nickname");
				String birth = request.getParameter("birth");
				String inputmail = request.getParameter("email"); //メールアドレス取得
				String inputpass = request.getParameter("password"); //パスワードを取得



				userbean.setUser_name(user_name);
				userbean.setNickname(nickname);
				userbean.setBirth(birth);
				userbean.setMailaddress(inputmail);
				userbean.setPassword(inputpass);

				try {

					if (userbean.getErrorMessages().containsKey("user_name")) {
						System.out.println(userbean.getErrorMessages().get("user_name"));
						ErrorMessage = userbean.getErrorMessages().get("user_name");
					}
					if (userbean.getErrorMessages().containsKey("mailaddress")) {
						System.out.println(userbean.getErrorMessages().get("mailaddress"));
						ErrorMessage = userbean.getErrorMessages().get("mailaddress");
					}
					if(ErrorMessage == null){
						userbean.addUser();
						ErrorMessage = "登録完了しました。";
						out.println(JSON.encode(ErrorMessage , true).toString());

						//新規登録完了画面
						response.sendRedirect(URL + "/Login.html");
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				out.println(JSON.encode(ErrorMessage , true).toString());
			}else{
				out.println(JSON.encode("unknownError" , true).toString());
			}
		}else{

		}

	}

}
