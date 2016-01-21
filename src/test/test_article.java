package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import beansdomain.ArticleBean;
import daodto.ImageDTO;
import daodto.Unique;

public class test_article {
	public static void main(String[] args) {
		//100% aaa  aaaaaaa        ffff　　　ああ_あ  　　  fshgjsrhgsdg
		ArrayList<String> text_list = new ArrayList<String>();
		text_list = escapekai("");
		//test_add(1);//記事の追加○
		//test_delete(54);//記事の削除○
		//test_view_article_list(1,2);//記事一覧表示○
		//test_view_mylist_search(1,text_list,1);//マイリスト検索の記事一覧表示
		//test_view_fav_list(1);//お気に入りの記事一覧表示○
		//test_view_favlist_search(1,text_list,1);//お気に入り検索の記事一覧表示
		//test_view_tag_search(1,text_list,"aaa",1);//タグ検索の記事一覧表示
		test_view_sharelist_search(1,text_list,2,1);//シェア検索の記事一覧表示
		//test_tag_fav_list(1);//タグ内でお気に入りした記事一覧○
		//test_update_article(1);//記事の更新○
		//test_add_fav_article(2,1);//記事にお気に入りとして追加○
		//test_fav_delete(2,1);//お気に入りの解除○
		//test_share_add("http://sharesan",16);//記事にシェアURLを追加○
		//test_share_delete(18);//シェア記事の削除○
		//test_share_article_list(25,18);//シェアしている記事一覧表示○
		//test_add_tag(21,1);//記事にタグを追加○
		//test_tag_search(1)//特定のタグの記事一覧表示○
		//test_article_view(10);//記事を表示○
		String aaa = "aaa";

		if(aaa != null) {
			System.out.println("yes");
		}

		//test_addImage();
	}
	static ArrayList<String> escapekai(String text){
		ArrayList<String> text_list = new ArrayList<String>();
		// _ %が含まれる文字列のエスケープ
		//全角の空白文字をひとつの半角空白に置換
		text=text.replaceAll("_","\\\\_").replaceAll("%", "\\\\%").replaceAll("　"," ");
		//空白があるなら分割
		String[] text_body = text.split("[\\s]+");
		if(text_body.length!=0){
			for(int i=0;i<text_body.length;i++){
				text_list.add(i, text_body[i]);
				System.out.println(text_list.get(i));
			}
		}else{
			text_list.add(0," ");//空白が送られてきた場合
		}
		return text_list;
	}
	static void test_addImage() {

		try {
			ArrayList<ImageDTO> imgs = new ArrayList<ImageDTO>();
			ImageDTO img = new ImageDTO();
			ImageDTO img2 = new ImageDTO();
			img.setUri("http://aaaa.jpg");
			img.setBlob_image("aaabbbbbbaaaaaaaaadfasdfasd".getBytes("UTF-8"));
			imgs.add(img);
			img2.setUri("http://aaaa2.jpg");
			img2.setBlob_image("aaaa2".getBytes("UTF-8"));
			imgs.add(img2);

			ArticleBean ab = new ArticleBean();

			ab.addImage(21, imgs);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//記事の追加(テスト済）
	static void test_add(int user_id) {
		ArticleBean ab = new ArticleBean();
		ArrayList<String> uri_list = new ArrayList<String>();
		ArrayList<byte[]> image_list = new ArrayList<byte[]>();
		uri_list.add("http1");
		uri_list.add("http2");
		uri_list.add("http3");
		uri_list.add("http4");
		try {
			image_list.add("test".getBytes("UTF-8"));
			image_list.add("test2".getBytes("UTF-8"));
			image_list.add("test3".getBytes("UTF-8"));
			image_list.add("test4".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		ab.setTitle("testadd");
		ab.setBody("aaadd");
		ab.setUrl("httpdddddd");
		ArrayList<ImageDTO> image = new ArrayList<ImageDTO>();
		for (int i = 0; i < uri_list.size(); i++) {
			ab.setUri(uri_list.get(i));
			ab.setBlob_image(image_list.get(i));
			image.add(ab.getImageDTO());
		}

		try {
			ab.addArticle(user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/*//記事の削除（テスト済）
	static void test_delete(int article_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		try {
			ab.deleteArticle();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}*/

	//記事一覧表示(テスト済)
	static void test_view_article_list(int user_id, int page) {
		ArticleBean ab = new ArticleBean();
		ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
		try {
			a = ab.viewArticleList(user_id, page);//修正する
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).getTitle());
			System.out.println(a.get(i).isFavflag());
		}
	}

	//マイリスト検索の記事一覧表示(テスト済)
		static void test_view_mylist_search(int user_id,ArrayList<String> text_list, int page) {
			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			try {
				a = ab.viewMyListSearch(user_id,text_list, page);//修正する
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getTitle());
			}
		}

	//お気に入りの記事一覧表示（テスト済）
	static void test_view_fav_list(int user_id, int page) {
		ArticleBean ab = new ArticleBean();
		ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
		try {
			a = ab.viewFavArticleList(user_id ,page);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).getTitle());
		}
	}

	//お気に入り検索の記事一覧表示(テスト済)
	static void test_view_favlist_search(int user_id,ArrayList<String> text_list, int page) {
		ArticleBean ab = new ArticleBean();
		ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
		try {
			a = ab.viewFavListSearch(user_id,text_list, page);//修正する
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).getTitle());
		}
	}

	//タグ検索の記事一覧表示(テスト済)
		static void test_view_tag_search(int user_id,ArrayList<String> text_list,String tag, int page) {
			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			try {
				a = ab.viewTagSearch(user_id,text_list,tag, page);//修正する
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getTitle());
			}
		}

	//シェア検索の記事一覧表示(テスト済)
		static void test_view_sharelist_search(int user_id,ArrayList<String> text_list,int friend_user_id, int page) {
			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			try {
				a = ab.viewShareListSearch(user_id,text_list,friend_user_id, page);//修正する
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getTitle());
			}
		}


	//タグ内でお気に入りした記事一覧(テスト済）
	static void test_tag_fav_list(int user_id, int page) {
		try {
			ArrayList<String> tag_body_list = new ArrayList<String>();
			tag_body_list.add("aiueo");
			tag_body_list.add("gorigorigori");

			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			a = ab.viewTagFavArticleList(user_id, tag_body_list, page);
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getArticle_id());
				System.out.println(a.get(i).getTitle());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	//記事の更新(テスト済)
	static void test_update_article(int article_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		ab.setTitle("testgdfg");
		ab.setBody("aaaagraa");
		ab.setUrl("httpddsgrgSfsafasfef");
		try {
			ab.updateArticle();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ab.setArticle_id(7);
	}

	//記事にお気に入りとして追加(テスト済）
	static void test_add_fav_article(int article_id, int user_id) {
		ArticleBean ab = new ArticleBean();
		System.out.println(article_id);
		ab.setArticle_id(article_id);
		try {
			ab.addFavorite(user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
/*
	//お気に入りの解除(テスト済)
	static void test_fav_delete(int article_id, int user_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		try {
			ab.deleteFavorite(user_id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//シェア記事の削除(テスト済)
	static void test_share_delete(int article_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		try {
			ab.deleteShareArticle();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}*/

	//記事にシェアURLを追加(テスト済)
	static void test_share_add(String share_url, int article_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		ab.setShare_url(share_url);
		try {
			ab.addShareArticle();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//シェアしている記事一覧表示（テスト済）
	static void test_share_article_list(int user_id, int friend_user_id, int page) {
		ArticleBean ab = new ArticleBean();
		ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
		try {
			a = ab.viewShareArticleList(user_id, friend_user_id, page);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).getTitle());
		}
	}

/*	//記事にタグを追加（テスト済）
	static void test_add_tag(int article_id, int user_id) {
		ArticleBean ab = new ArticleBean();
		ab.setArticle_id(article_id);
		try {
			ArrayList<String> tag_body_list = new ArrayList<String>();
			tag_body_list.add("aiueo");
			tag_body_list.add("gorigorigori");
			tag_body_list.add("わっしょい");
			tag_body_list.add("GORIMA");
			ab.addArticleTag(user_id, tag_body_list);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}*/

	//特定のタグの記事一覧表示（テスト済）
	static void test_tag_search(int user_id, int page) {

		try {
			ArrayList<String> tag_body_list = new ArrayList<String>();
			//tag_body_list.add("aiueo");
			tag_body_list.add("gorigorigori");

			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			a = ab.viewTag(tag_body_list, user_id, page);

			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getArticle_id());
				System.out.println(a.get(i).getTitle());
				System.out.println(a.get(i).getBody());
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	//記事を表示（テスト済）
	static void test_article_view(int article_id) {

		try {

			ArticleBean ab = new ArticleBean();
			ArrayList<ArticleBean> a = new ArrayList<ArticleBean>();
			ab.setArticle_id(article_id);
			//a=ab.viewArticle();
			System.out.println(ab.getArticle_id());
			System.out.println(ab.getTitle());
			System.out.println(ab.getBody());
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i).getUri());
				System.out.println(a.get(i).getBlob_image());
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/*//タグ内でお気に入り検索
	static void test_favorite(int user_id) {

		try {
			ArrayList<String> tag_body_list = new ArrayList<String>();
			tag_body_list.add("aiueo");
			tag_body_list.add("gorigorigori");

			ArticleDAO articleDAO = new ArticleDAO();

			ArrayList<ArticleDTO> articleDTO = new ArrayList<ArticleDTO>();
			articleDTO = articleDAO.searchFavoriteTagLists(user_id, tag_body_list);

			for (ArticleDTO artDTO : articleDTO) {
				System.out.println(artDTO.getTitle());
				System.out.println("BODY:" + artDTO.getBody());
				for (ImageDTO imgDTO : artDTO.getImageDTO()) {
					System.out.println(imgDTO.getUri());
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}*/
	static void test_unique() {
		ArrayList<Integer> test_list = new ArrayList<Integer>();

		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 10; i++) {
				test_list.add(i);
				System.out.println(i);
			}
		}
		test_list = Unique.unique(test_list);

		for (int test : test_list) {
			System.out.println(test);
		}
	}
}
