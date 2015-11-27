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

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleServlet() {
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
		String inputbutton = request.getParameter("button");
		if(セッション情報があるなら){
			//何もしない
		}else if(セッション情報がないなら){
			//ログイン画面に戻る
		}

		//一覧表示、記事表示、追加、削除、付与

			/***************************************
			*サイドメニューのマイリストを押したとき*
			****************************************/
		if(inputbutton.equals("マイリスト")){
			//記事一覧表示

			int user_id =0;//ログインして格納してあるsessionからuser_idを取得
			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			try {
				article_list=articlebean.viewArticleList(user_id);
				session.setAttribute("articlelist", article_list);
				request.getRequestDispatcher("/").forward(request, response);//リンク先で値をGETして記事一覧表示
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			/***************************************
			*サイドメニューのお気に入りを押したとき*
			****************************************/
		}else if(inputbutton.equals("お気に入り")){
			//お気に入りの記事一覧表示

			int user_id =0;//ログインして格納してあるsessionからuser_idを取得
			ArticleBean articlebean = new ArticleBean();
			ArrayList<ArticleBean> article_list = new ArrayList<ArticleBean>();
			try {
				article_list=articlebean.viewFavArticleList(user_id);
				session.setAttribute("articlelist", article_list);
				request.getRequestDispatcher("/").forward(request, response);//リンク先で値をGETして記事一覧表示
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			/***************************************
			****URLを入力して保存を決定したとき*****
			****************************************/
		}else if(inputbutton.equals("記事の追加")){
			//ユーザID、記事のタイトル、記事のURL、記事の内容、画像のURI、記事の画像が引数
			//test_add(1);//記事の追加○
			/***************************************
			****ある記事の削除ボタンを押したとき****
			****************************************/
		}else if(inputbutton.equals("記事の削除")){//ある記事の削除ボタンを押したとき
			//記事の削除

			int article_id = Integer.parseInt(request.getParameter("article_id"));
			ArticleBean articlebean = new ArticleBean();
			articlebean.setArticle_id(article_id);
			try {
				if(articlebean.deleteArticle()){
					//成功したポップアップを表示
				}else{
					//失敗したポップアップを表示
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			/*************************************************************
			****あるタグの記事一覧の検索欄からお気に入りを検索するとき****
			**************************************************************/
		}else if(inputbutton.equals("タグ内お気に入り検索")){
			//ArrayList<String>のタグリストとuser_idが引数
			//test_tag_fav_list(1);//タグ内でお気に入りした記事一覧○
			/*****************************************
			****記事を編集して変更ボタンを押した際****
			*****************************************/
		}else if(inputbutton.equals("記事変更")){
			//article_id,記事のタイトル、記事の
			//test_update_article(1);//記事の更新○

			/***************************************
			*ある記事にお気に入りボタンを押したとき*
			****************************************/
		}else if(inputbutton.equals("お気に入り追加")){
			//test_add_fav_article(1,1);//記事にお気に入りとして追加○
			/*************************************************
			****ある記事のお気に入り解除ボタンを押したとき****
			*************************************************/
		}else if(inputbutton.equals("お気に入り解除")){
			//test_fav_delete(1,1);//お気に入りの解除○
			/*****************************************************
			****ある記事をフレンドと共有するボタンを押したとき****
			*****************************************************/
		}else if(inputbutton.equals("")){
			//test_share_add("http://share",2);//記事にシェアURLを追加○
			/***************************************
			****あるシェアした記事を削除するとき****
			****************************************/
		}else if(inputbutton.equals("")){
			//test_share_delete(1);//シェア記事の削除○
			/***************************************
			**********フレンドを選択した際**********
			****************************************/
		}else if(inputbutton.equals("")){
			//test_share_article_list(25,18);//シェアしている記事一覧表示○
			/***************************************
			**記事にタグを追加するボタンを押した際**
			****************************************/
		}else if(inputbutton.equals("")){
			//test_add_tag(21,1);//記事にタグを追加○
			/***************************************
			****タグを検索欄に入力して検索した際****
			****************************************/
		}else if(inputbutton.equals("タグ検索")){
			//test_tag_search(1)//特定のタグの記事一覧表示○
			/***************************************
			******ある記事を選択して表示した際******
			****************************************/
		}else if(inputbutton.equals("記事を表示")){
			//test_article_view(10);//記事を表示○
		}





	}

}
