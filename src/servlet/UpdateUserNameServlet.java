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


@WebServlet("/updateusername")
public class UpdateUserNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateUserNameServlet() {
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
		HttpSession session = request.getSession(true);
		User userbean = null;
		//String ErrorMessage = null;
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);

		//本来では、セッション情報のユーザIDを取得
		int user_id = 0;

		String inputname = request.getParameter("newusername");
		String inputpass = request.getParameter("password");


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		if (nonce.isNonce()) {
			if(request.getParameter("newusername")!=null &&request.getParameter("password")!=null){

				try {


				    user_id = (int) session.getAttribute("user_id");
					userbean = new User(user_id);
					System.out.println("変更前の名前" + userbean.getUser_name());
					System.out.println("変更後の名前" + inputname );

					if(inputpass.equals(userbean.getPassword())){
						userbean.setUser_name(inputname);
						userbean.updateUserName();
						resp = "{\"state\": \"成功しました\",  \"flag\": 0}";
					}else{
						//パスワードが一致しなかった処理
						resp = "{\"state\": \"パスワードが間違ってます\",  \"flag\": 0}";
					}


					//メッセージ処理
					if (userbean.getErrorMessages().containsKey("user_name")) {
						System.out.println(userbean.getErrorMessages().get("user_name"));
						//ErrorMessage = userbean.getErrorMessages().get("user_name");
					}else {

						//ErrorMessage = "無事,ユーザーネームを更新することができました。";
					}

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				out.println(resp);
			}else{
				//newusername passwordがnullならunknownerror
				out.println(resp);
			}
		}else{
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
		}
			//out.println(JSON.encode(ErrorMessage , true).toString());
	}

}