package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Nonce {

	private HttpServletRequest request = null;

	public Nonce(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @param args
	 */
	public boolean isNonce() {

		HttpSession session = request.getSession(true);
		Boolean ses_flag = false;
		//nonceの検証を行う
		String s_nonce = (String) session.getAttribute("nonce");
		String nonce = request.getParameter("nonce");

		// Nullでもなく空でもない
		if (nonce != null && s_nonce != null) {
			// nonceがない
			if (s_nonce.equals(nonce)) {
				// nonceが同一の場合
				ses_flag = true;
			}
		}

		return ses_flag;

	}

}
