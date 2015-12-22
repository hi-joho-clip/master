package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beansdomain.ArticleBean;
import daodto.ImageDTO;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		/*if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}*/
		/***************************************
		****URLを入力して保存を決定したとき*****
		****************************************/
		//記事の追加
		int user_id =0;//sessionからuser_idを取得
		String title = request.getParameter("article_json.article.title");//JSON
		String body = request.getParameter("article_json.article.body");//JSON
		String url = request.getParameter("article_json.article.url");//JSON
		ArticleBean articlebean = new ArticleBean();
		ArrayList<String> uri_list = new ArrayList<String>();
		ArrayList<byte[]> image_list = new ArrayList<byte[]>();
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

		articlebean.setTitle(title);
		articlebean.setBody(body);
		articlebean.setUrl(url);
		ArrayList<ImageDTO> image = new ArrayList<ImageDTO>();
		for(int i=0;i<uri_list.size();i++){
			articlebean.setUri(uri_list.get(i));
			articlebean.setBlob_image(image_list.get(i));
			image.add(articlebean.getImageDTO());
		}
		try {
			if(articlebean.addArticle(user_id,image)){
				//成功したポップアップを表示
			}else{
				//失敗したポップアップを表示
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
