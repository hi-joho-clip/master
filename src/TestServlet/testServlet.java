package TestServlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import servlet.AddArticleServlet;

public class testServlet {

//	@Test
//	public void testUpdateArticle() {
//
//		GetUpdateArticle testUpdate = new GetUpdateArticle();
//
//		MockHttpServletRequest req = new MockHttpServletRequest();
//		MockHttpServletResponse resp = new MockHttpServletResponse();
//
//		/*
//		 *
//		 */
//		String param = "{article_id :1,modified:149810990000}" +
//				",{\"article_id\" :\"2\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"3\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"4\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"5\",\"modified\":1449725590000}";
//
//
//		req.setParameter("json", "[" + param + "]");
//		//req.setParameter("json", "[" + param + "]");
//		//req.setParameter( "args2", "2" );
//
//		HttpSession session = req.getSession();
//		session.setAttribute("user_id",1);
//
//		try {
//			//testUpdate.service(req, resp);
//			/*
//			 * PrivateやProtectedなクラスはこうやるんだ
//			 * method →"doPost"：呼び出すメソッド, 引数のクラス、引数のクラス
//			 * method.invoke→（テストしたいクラスのインスタンス、引数、引数）
//			 */
//
//			Method method = GetUpdateArticle.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
//	        method.setAccessible(true);
//	        method.invoke(testUpdate,req,resp);
//	        System.out.println("response:" + resp.getContentAsString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//
//		// ここから結果をassertで確認
//		// assetってのは期待される返り値と比較するみたいなもの
//		// 失敗の場合などいろいろなメソッドがある
//
//	}

//	@Test
//	public void testViewArticle() {
//
//		ViewArticleServlet testUpdate = new ViewArticleServlet();
//
//		MockHttpServletRequest req = new MockHttpServletRequest();
//		MockHttpServletResponse resp = new MockHttpServletResponse();
//
//		/*
//		 *
//		 */
//		String param = "2";
//
//		req.setParameter("article_id", param);
//		req.setParameter("guid", "aaaa");
//		//req.setParameter("json", "[" + param + "]");
//		//req.setParameter( "args2", "2" );
//
//		HttpSession session = req.getSession();
//		session.setAttribute("user_id",1);
//
//		try {
//			//testUpdate.service(req, resp);
//			/*
//			 * PrivateやProtectedなクラスはこうやるんだ
//			 * method →"doPost"：呼び出すメソッド, 引数のクラス、引数のクラス
//			 * method.invoke→（テストしたいクラスのインスタンス、引数、引数）
//			 */
//
//			Method method = ViewArticleServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
//	        method.setAccessible(true);
//	        method.invoke(testUpdate,req,resp);
//	        System.out.println("response:" + resp.getContentAsString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//
//		// ここから結果をassertで確認
//		// assetってのは期待される返り値と比較するみたいなもの
//		// 失敗の場合などいろいろなメソッドがある
//
//	}


//	@Test
//	public void testDeleteArticle() {
//
//		GetDeleteArticle testUpdate = new GetDeleteArticle();
//
//		MockHttpServletRequest req = new MockHttpServletRequest();
//		MockHttpServletResponse resp = new MockHttpServletResponse();
//
//		/*
//		 *
//		 */
//		String param = "{article_id :1,modified:149810990000}" +
//				",{\"article_id\" :\"2\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"42\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"4\",\"modified\":1449729990000}" +
//				",{\"article_id\" :\"5\",\"modified\":1449725590000}";
//
//
//		//req.setParameter("json", "");
//		req.setParameter("json", "[" + param + "]");
//		//req.setParameter("json", "[" + param + "]");
//		//req.setParameter( "args2", "2" );
//
//		HttpSession session = req.getSession();
//		session.setAttribute("user_id",1);
//
//		try {
//			//testUpdate.service(req, resp);
//			/*
//			 * PrivateやProtectedなクラスはこうやるんだ
//			 * method →"doPost"：呼び出すメソッド, 引数のクラス、引数のクラス
//			 * method.invoke→（テストしたいクラスのインスタンス、引数、引数）
//			 */
//
//			Method method = GetDeleteArticle.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
//	        method.setAccessible(true);
//	        method.invoke(testUpdate,req,resp);
//	        System.out.println("response:" + resp.getContentAsString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// ここから結果をassertで確認
//		// assetってのは期待される返り値と比較するみたいなもの
//		// 失敗の場合などいろいろなメソッドがある
//
//	}


	@Test
	public void testDeleteArticle() {

		AddArticleServlet testUpdate = new AddArticleServlet();

		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();

		/*
		 *
		 */
		String param = "{article_id :1,modified:149810990000}" +
				",{\"article_id\" :\"2\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"42\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"4\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"5\",\"modified\":1449725590000}";


		//req.setParameter("json", "");
		//req.setParameter("json", "[" + param + "]");
		//req.setParameter("json", "[" + param + "]");
		//req.setParameter( "args2", "2" );

		req.setParameter("url", "http://pc.watch.impress.co.jp/docs/news/20160113_738654.html");
		req.setParameter("mode", "eveaaary");

		HttpSession session = req.getSession();
		session.setAttribute("user_id",1);

		try {
			//testUpdate.service(req, resp);
			/*
			 * PrivateやProtectedなクラスはこうやるんだ
			 * method →"doPost"：呼び出すメソッド, 引数のクラス、引数のクラス
			 * method.invoke→（テストしたいクラスのインスタンス、引数、引数）
			 */

			Method method = AddArticleServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
	        method.setAccessible(true);
	        method.invoke(testUpdate,req,resp);
	        System.out.println("response:" + resp.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ここから結果をassertで確認
		// assetってのは期待される返り値と比較するみたいなもの
		// 失敗の場合などいろいろなメソッドがある

	}
}
