package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseHttpServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public BaseHttpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//perform(request, response);
	}



}
