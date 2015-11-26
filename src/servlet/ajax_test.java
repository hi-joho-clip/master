package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import beansdomain.Friend;

/**
 * Servlet implementation class AminReferServlet
 */
@WebServlet(name = "ajax_test", urlPatterns = { "/ajax_test" })
public class ajax_test extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ajax_test() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String user_id = req.getParameter("user_id");

		System.out.println(user_id);

		res.setContentType("application/json; charset=utf-8");
		res.setHeader("Cache-Control", "private");
		PrintWriter out = res.getWriter();

		Friend friend = new Friend();

		try {
			friend.getFriendList(Integer.parseInt(user_id));
		} catch (Exception e) {
			e.getStackTrace();
		}
		 System.out.println(JSON.encode(friend,true));
			out.println(JSON.encode(friend, true).toString());
	}

}
