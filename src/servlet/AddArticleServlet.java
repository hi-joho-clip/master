package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boiler.SaveArticle;

/**
 * Servlet implementation class AddArticleServlet
 */
@WebServlet("/addarticle")
public class AddArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddArticleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		String resp = "{\"state\": \"unknownError\",  \"flag\": 0}";

		int user_id = 0;
		String url = "";
		// NORMAL, FULL
		String mode = "NORMAL";

		// nonceの検証が必要です。

		Nonce nonce = new Nonce(request);
		//url = "https://ja.wikipedia.org/wiki/%E3%81%84%E3%81%A1%E3%81%94100%25";

		if (nonce.isNonce()) {
			// urlはUTF-8でエンコして送ろう
			if (request.getParameter("url") != null) {

				try {
					url = request.getParameter("url");//JSON
					url = new String(url.getBytes("UTF-8"), "UTF-8");
					user_id = (int) session.getAttribute("user_id");
					mode = request.getParameter("mode");
				} catch (Exception e) {
					e.printStackTrace();
				}
				mode = ("NORMAL");

				System.out.println("URL:" + url);

				// http://か,https://以外はダメ
				if (url.startsWith("http://") || url.startsWith("https://")) {
					SaveArticle save = new SaveArticle();
					if (mode.equals("FULL")) {
						if (save.keep_extractor(user_id, url)) {

							resp = "{\"state\": \"追加しました\", \"flag\": 1}";
						} else {
							resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
						}
					} else if (mode.equals("NORMAL")) {
						if (save.def_extractor(user_id, url)) {
							resp = "{\"state\": \"追加しました\", \"flag\": 1}";
						} else {
							resp = "{\"state\": \"失敗しました\", \"flag\": 0}";
						}

					}
				} else {
					resp = "{\"state\": \"正しいURLではありません。\", \"flag\": 0}";
				}

				PrintWriter out = response.getWriter();
				out.println(resp);

			} else {
				resp = "{\"state\": \"URLエラー\",  \"flag\": 0}";
				PrintWriter out = response.getWriter();
				out.println(resp);
			}
		} else {
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			PrintWriter out = response.getWriter();
			out.println(resp);
		}

	}
}
