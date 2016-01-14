package servlet;

import java.io.IOException;

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
		System.out.println("kitaaaa");
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/***************************************
		****URLを入力して保存を決定したとき*****
		****************************************/
		//記事の追加
		//int user_id =1;//sessionからuser_idを取得

		int user_id = (int) session.getAttribute("user_id");
		user_id = 1;

		String url = request.getParameter("url");//JSON
		String mode = request.getParameter("mode");
		mode = "eaaa";

		//		ArticleBean articlebean = new ArticleBean();
		//		ArrayList<String> uri_list = new ArrayList<String>();
		//		ArrayList<byte[]> image_list = new ArrayList<byte[]>();
		/*
		 * 記事のURLを入力して追加ボタンを押すとJSONデータが送られてくる。
		 * object.datename[]でアクセス可能
		 *
		 * */

		/* JSONのデータがある限りArrayListに追加する
		uri_list.add(URI);
		try {
			image_list.add(画像データ));
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		*/

		SaveArticle save = new SaveArticle();

		url = "http://akiba-pc.watch.impress.co.jp/docs/wakiba/find/20160114_738863.html";
		String url2 = "https://www.mtgox.com/img/pdf/20140320-btc-announce.pdf";
		System.out.println(url);
		if (mode.equals("every")) {
			if (save.keep_extractor(user_id, url)) {
				System.out.println("eve.success");
			} else {
				System.out.println("evfaild");
			}
		} else {
			if (save.def_extractor(user_id, url)) {
				System.out.println("success");
			} else {
				System.out.println("faild");
			}
		}
	}

}
