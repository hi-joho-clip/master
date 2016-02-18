package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ExampleFilter
 * 全URLのパターンを登録するか、サーブレットを登録するか
 * 404だとサーバ判断になるのでサーブレットの方が良い。
 * 1222現在はまだ使わないがきちんと動きます
 */
@WebFilter(urlPatterns = { "/taglist",
		"/mylist",
		"/sharelist",
		"/viewarticle",
		"/favlist",
		"/viewuser",
		"/autologin"})
public class LoginFilter implements Filter {

	private String encoding = "UTF-8";

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		// セッションからログインを判定する
		//セッション維持のためのセッションを生成
		try {
			//System.out.println("fileter on");
			//String target = ((HttpServletRequest) request).getRequestURI();
			//System.out.println("target:" + target);
			String loginURL = "/login/login.html";
			String logoutURL = "/login/logout.html";

			HttpSession session = ((HttpServletRequest) request).getSession();
			//String servletName = ((HttpServletRequest) request).getServletPath();
			String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();

			// ログインとログアウトのURLパターン以外全部
			//if (!(target.equals(loginURL) || target.equals(logoutURL))) {
			//System.out.println("not Authorized0:" + resp);


			if (session == null) {
				/* まだ認証されていない */
				// セッション自体がない場合
				session = ((HttpServletRequest) request).getSession(true);
				//session.setAttribute("target", target);

				//System.out.println("non session");
				// 通常リダイレクトできないのでパラメータで送りJSでハンドリングする
				resp = "{\"redirect\": \"true\", \"redirect_url\": \"" + loginURL + "\"}";
				out.print(resp);
				//System.out.println("not Authorized1:" + resp);
				//((HttpServletResponse) response).sendRedirect(loginURL);
			} else {
				String username = (String) session.getAttribute("username");
				// User_idがない場合認証されていない
				if (username == null) {
					/* まだ認証されていない */
					resp = "{\"redirect\": \"true\", \"redirect_url\": \"" + loginURL + "\"}";
					//System.out.println("not Authorized:" + resp);
					out.print(resp);

					//session.setAttribute("target", target);
					//((HttpServletResponse) response).sendRedirect(loginURL);
				} else {
					// ここは認証成功
					// pass the request along the filter chain
					//System.out.println("認証成功");
					chain.doFilter(request, response);
				}
			}
			//			} else {
			//				// pass the request along the filter chain
			//				System.out.println("not need Auth");
			//				chain.doFilter(request, response);
			//			}
		} catch (ServletException se) {
		} catch (IOException e) {
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
