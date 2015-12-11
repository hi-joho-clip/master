package TestServlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import sync_servlet.GetUpdateArticle;

public class testServlet {

	@Test
	public void testUpdateArticle() {

		GetUpdateArticle testUpdate = new GetUpdateArticle();

		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse resp = new MockHttpServletResponse();


		/*
		 *
		 */
		String param = "{\"article_id\" :\"1\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"2\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"3\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"4\",\"modified\":1449729990000}" +
				",{\"article_id\" :\"5\",\"modified\":1449725590000}";



		req.setParameter("json", "[" + param + "]");
		//req.setParameter( "args2", "2" );

		try {
			//testUpdate.service(req, resp);
			/*
			 * PrivateやProtectedなクラスはこうやるんだ
			 * method →"doPost"：呼び出すメソッド, 引数のクラス、引数のクラス
			 * method.invoke→（テストしたいクラスのインスタンス、引数、引数）
			 */

			Method method = GetUpdateArticle.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
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
