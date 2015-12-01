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

@WebServlet("/deleterequest")
public class DeleteRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteRequestServlet() {
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
		boolean flag = false;

		/*int own_user_id = Integer.parseInt(request.getParameter("own_user_id"));
		int friend_user_id = Integer.parseInt(request.getParameter("friend_user_id"));*/

		int own_user_id = 4;
		int friend_user_id = 17;


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		try {
			flag = friendbeans.deleteRequest(own_user_id, friend_user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(flag, true).toString());
	}

}

