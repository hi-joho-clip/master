package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mail.SendMail;

import beansdomain.User;
import beansdomain.UserAuth;

@WebServlet("/updatemailaddress")
public class UpdateMailAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateMailAddressServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		boolean flag = true;
		String resp = "{\"state\": \"unknownError\", \"flag\": 0}";
		Nonce nonce = new Nonce(request);
		String ErrorMessage = null;
		User userbean = null;
		UserAuth userauth = new UserAuth();
		// boolean hantei = false;

		String URL = request.getContextPath() + "/login";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();

		if (nonce.isNonce()) {
			if (request.getParameter("newemail") != null
					&& request.getParameter("password") != null) {

				char[] chars = request.getParameter("newemail").toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (String.valueOf(chars[i]).getBytes().length < 2) {
					} else {
						flag = false;
						break;
					}
				}

				if (request.getParameter("newemail").length() < 256
						&& request.getParameter("password").length() < 33
						&& request.getParameter("password").length() > 7) {
					if (flag) {
						try {
							int user_id = (int) session.getAttribute("user_id");

							String inputmail = request.getParameter("newemail");
							inputmail = new String(inputmail.getBytes("UTF-8"),
									"UTF-8");
							String inputpass = request.getParameter("password");
							inputpass = new String(inputpass.getBytes("UTF-8"),
									"UTF-8");
							System.out.println("request受け取った" + inputmail);
							System.out.println("request受け取った" + inputpass);

							userbean = new User(user_id);
							// hantei =
							// userauth.loginUserName(userbean.getUser_name(),inputpass);

							if (userauth.loginUserName(userbean.getUser_name(),
									inputpass)) {
								System.out.println("ユーザ成功");
								userbean.setMailaddress(inputmail);
								userbean.setPassword(inputpass);
								if (userbean.updateMailaddress()) {
									System.out.println("メールアドレス成功");
									// userbean.updateMailaddress();
									resp = "{\"state\": \"更新しました\",  \"flag\": 1}";

									SendMail mail = new SendMail();
									// コンストラクタ替わりに設定必要
									mail.userInfoMail(
											userbean.getMailaddress(),
											userbean.getUser_name(),
											userbean.getNickname());
									// スレッドスタート
									mail.start();

								} else {
									if (userbean.getErrorMessages()
											.containsKey("mailaddress")) {
										System.out.println(userbean
												.getErrorMessages().get(
														"mailaddress"));
										ErrorMessage = userbean
												.getErrorMessages().get(
														"mailaddress");
										resp = "{\"state\":\"" + ErrorMessage
												+ "\",  \"flag\": 0}";

									} else {
										ErrorMessage = userbean
												.getErrorMessages().get(
														"ErrorMessage");
										resp = "{\"state\":\"" + ErrorMessage
												+ "\",  \"flag\": 0}";
									}
								}
							} else {
								System.out.println("パスワード間違ってる");
								// パスワードが一致しなかった処理
								resp = "{\"state\": \"パスワードが間違ってます\",  \"flag\": 0}";

							}

						} catch (Exception e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}else{
						resp = "{\"state\": \"メールアドレスが不適切です\",  \"flag\": 0}";
					}
					out.println(resp);
				} else {
					resp = "{\"state\": \"文字数制限エラー\",  \"flag\": 0}";
					out.println(resp);
				}
			} else {
				// newemail passwordがnullならunknownerror
				out.println(resp);
			}
		} else {
			// 不正アクセス
			resp = "{\"state\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);

		}
		// response.sendRedirect(URL + "/info.html");

	}
}
