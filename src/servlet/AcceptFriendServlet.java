package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;
import beansdomain.User;

@WebServlet("/acceptfriend")
public class AcceptFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AcceptFriendServlet() {
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
		String Message = null;
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		HttpSession session = request.getSession(true);
		//String URL = request.getContextPath() + "/login";
		int user_id=0;
		Nonce nonce = new Nonce(request);

		if(nonce.isNonce()){
			try {
				user_id = (int) session.getAttribute("user_id");
				userbean.setUser_id(user_id);
				if(userbean.friend_accept()){
					resp = "{\"state\": \"拒否にしました\", \"flag\": 1}";
				}else{
					resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
				}
				//Message = "フレンド申請拒否設定にしました。";
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(JSON.encode(Message, true).toString());
			//response.sendRedirect(URL + "/info.html");
		}else{
			//nonceがない場合のメソッド
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}

	}
}