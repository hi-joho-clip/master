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


@WebServlet("/UpdateMailAddressServlet")
public class UpdateMailAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateMailAddressServlet() {
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

		User userbean = new User();
		String ErrorMessage = null;
		int user_id = 2;

		String inputmail = request.getParameter("mailaddress");
		String inputpass = request.getParameter("password");


		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		userbean.setMailaddress(inputmail);



		try {
			if(inputpass.equals(userbean.getPassword())){
				userbean.updateMailaddress();
			}

			if (userbean.getErrorMessages().containsKey("mailaddress")) {
				System.out.println(userbean.getErrorMessages().get("mailaddress"));
				ErrorMessage = userbean.getErrorMessages().get("mailaddress");
			}else {
				ErrorMessage = "無事新規作成できました。";
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println(JSON.encode(ErrorMessage , true).toString());
	}

}
