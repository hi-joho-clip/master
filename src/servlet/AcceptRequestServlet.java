package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.Friend;

@WebServlet("/AcceptRequestServlet")
public class AcceptRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AcceptRequestServlet() {
        super();
    }


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}
	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int own_user_id = Integer.parseInt(request.getParameter("own_user_id"));
		int friend_user_id = Integer.parseInt(request.getParameter("friend_user_id"));

		Friend friendbeans = new Friend();

		try {
			friendbeans.acceptRequest(own_user_id, friend_user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
