package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.JSON;

import beansdomain.Friend;

@WebServlet("/friendlist")
public class FriendListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FriendListServlet() {
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

		HttpSession session = request.getSession(true);
		Friend friendbeans = new Friend();
		ArrayList<Friend> friend_list = new ArrayList<Friend>();


		//セッション情報の名前は変更する必要あり
		int own_user_id = own_user_id= 2;
		//Integer.parseInt(request.getParameter("user_id"));


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();


		try {
			friend_list = friendbeans.getFriendList(own_user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(friend_list, true).toString());
		//フレンド一覧するページへ

	}
}