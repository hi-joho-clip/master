package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ExampleFilter
 * 全URLのパターンを登録するか、サーブレットを登録するか
 * 404だとサーバ判断になるのでサーブレットの方が良い。
 * 1222現在はまだ使わないがきちんと動きます
 */
@WebFilter()
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
			String target = ((HttpServletRequest) request).getRequestURI();
			String loginURL = "/clipMaster/login/index.html";
			String logoutURL = "//clipMaster/login/logout.html";

			HttpSession session = ((HttpServletRequest) request).getSession();
			//String servletName = ((HttpServletRequest) request).getServletPath();

			// ログインとログアウトのURLパターン以外全部
			if (!(target.equals(loginURL) || target.equals(logoutURL))) {

				if (session == null) {
					/* まだ認証されていない */
					session = ((HttpServletRequest) request).getSession(true);
					session.setAttribute("target", target);

					((HttpServletResponse) response).sendRedirect(loginURL);
				} else {
					Object loginCheck = session.getAttribute("login");
					if (loginCheck == null) {
						/* まだ認証されていない */
						session.setAttribute("target", target);
						((HttpServletResponse) response).sendRedirect(loginURL);
					}
				}
				// pass the request along the filter chain
				chain.doFilter(request, response);
			} else {
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}
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
