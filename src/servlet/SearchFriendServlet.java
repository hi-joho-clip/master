package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import beansdomain.Friend;
import beansdomain.User;

@WebServlet("/searchfriend")
public class SearchFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchFriendServlet() {
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

		Friend friendbeans = new Friend();
		ArrayList<User> friend_list = new ArrayList<User>();

		int own_user_id = 1;
		String str = "99" ;
		//Integer.parseInt(request.getParameter("user_id"));
		/*String str = request.getParameter("str");*/


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();


		try {
			friend_list = friendbeans.searchFriend(own_user_id, str);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(friend_list, true).toString());
	}
}
