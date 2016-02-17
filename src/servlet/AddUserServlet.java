package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.SendMail;
import net.arnx.jsonic.JSON;
import beansdomain.User;

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUserServlet() {
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
		User userbean = new User();
		boolean flag = true;
		boolean flag_user = true;
		boolean flag_birth = true;
		String ErrorMessage = null;
		String URL = request.getContextPath() + "/login";
		String resp = "{\"ErrorMessage\": \"unknownError\", \"flag\": 0}";
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "private");
		PrintWriter out = response.getWriter();
		Nonce nonce = new Nonce(request);
		System.out.println(request.getParameter("nonce"));
		if (nonce.isNonce()) {
			if (request.getParameter("username") != null
					&& request.getParameter("nickname") != null
					&& request.getParameter("birth") != null
					&& request.getParameter("email") != null
					&& request.getParameter("password") != null) {

				char[] chars = request.getParameter("email").toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (String.valueOf(chars[i]).getBytes().length < 2) {
					} else {
						System.out.println("メール全角");
						flag = false;
						break;
					}
				}

				char[] chars_user = request.getParameter("username")
						.toCharArray();
				for (int i = 0; i < chars_user.length; i++) {
					if (String.valueOf(chars_user[i]).getBytes().length < 2) {
					} else {
						System.out.println("ユーザ名全角");
						flag_user = false;
						break;
					}
				}
				String[] str = new String[10];
				char[] chars_birth = request.getParameter("birth").toCharArray();
				for (int i = 0; i < chars_birth.length; i++) {
					if (chars_birth.length == 10) {
						str[i] = String.valueOf(chars_birth[i]);
						if (i == 4 || i == 7) {
							if (chars_birth[i] != '-') {
								System.out.println("生年月日不正あり");
								flag_birth = false;
								break;
							}
						}else{
							System.out.println("生年月日を比較してます" + chars_birth[i]);
							try{
								Integer.parseInt(str[i]);
								//Character.getNumericValue(chars_birth[i]);
							}catch (NumberFormatException e) {
								System.out.println("生年月日(数字判定エラー)");
								flag_birth = false;
								break;
						    }
						}
					} else {
						System.out.println("生年月日(文字制限)");
						flag_birth = false;
						break;
					}
				}

				if (request.getParameter("username").length() < 33
						&& request.getParameter("username").length() > 4
						&& request.getParameter("password").length() < 33
						&& request.getParameter("password").length() > 7
						&& request.getParameter("nickname").length() < 33
						&& request.getParameter("nickname").length() > 0
						&& request.getParameter("email").length() < 256) {
					if (flag) {
						if (flag_user) {
							if (flag_birth) {
								String user_name = request
										.getParameter("username");
								String nickname = request
										.getParameter("nickname");
								nickname = new String(
										nickname.getBytes("UTF-8"), "UTF-8");
								String birth = request.getParameter("birth");
								String inputmail = request
										.getParameter("email"); // メールアドレス取得
								inputmail = new String(
										inputmail.getBytes("UTF-8"), "UTF-8");
								String inputpass = request
										.getParameter("password"); // パスワードを取得
								inputpass = new String(
										inputpass.getBytes("UTF-8"), "UTF-8");
								userbean.setUser_name(user_name);
								userbean.setNickname(nickname);
								userbean.setBirth(birth);
								userbean.setMailaddress(inputmail);
								userbean.setPassword(inputpass);

								try {
									// 0でない限り成功
									int insert_id = userbean.addUser();
									if (insert_id != 0) {
										// 成功処理
										System.out.println("登録した");
										ErrorMessage = "登録完了しました。";
										resp = "{\"ErrorMessage\":\"登録完了 \",  \"flag\": 1}";
										out = response.getWriter();
										out.println(resp);

										// メール送信
										User user = new User(insert_id);
										SendMail mail = new SendMail();
										// コンストラクタ替わりに設定必要
										mail.addUserMail(user.getMailaddress(),
												user.getUser_name(),
												user.getNickname());
										// スレッドスタート
										mail.start();

									} else {
										if (userbean.getErrorMessages()
												.containsKey("user_name")
												&& userbean.getErrorMessages()
														.containsKey(
																"mailaddress")) {
											System.out.println(userbean
													.getErrorMessages().get(
															"user_name"));
											System.out.println(userbean
													.getErrorMessages().get(
															"mailaddress"));
											ErrorMessage = userbean
													.getErrorMessages().get(
															"user_name")
													+ userbean
															.getErrorMessages()
															.get("mailaddress");
											resp = "{\"ErrorMessage\":\""
													+ ErrorMessage
													+ "\",  \"flag\": 0}";
											out = response.getWriter();
											out.println(resp);
										}
										if (userbean.getErrorMessages()
												.containsKey("user_name")) {
											System.out.println(userbean
													.getErrorMessages().get(
															"user_name"));
											ErrorMessage = userbean
													.getErrorMessages().get(
															"user_name");
											resp = "{\"ErrorMessage\":\""
													+ ErrorMessage
													+ "\",  \"flag\": 0}";
											out = response.getWriter();
											out.println(resp);
										}
										if (userbean.getErrorMessages()
												.containsKey("mailaddress")) {
											System.out.println(userbean
													.getErrorMessages().get(
															"mailaddress"));
											ErrorMessage = userbean
													.getErrorMessages().get(
															"mailaddress");
											resp = "{\"ErrorMessage\":\""
													+ ErrorMessage
													+ "\",  \"flag\": 0}";
											out = response.getWriter();
											out.println(resp);
										}
									}

								} catch (Exception e) {
									// TODO 自動生成された catch ブロック
									e.printStackTrace();
								}
							} else {
								resp = "{\"ErrorMessage\": \"生年月日に不正あり\",  \"flag\": 0}";
								out = response.getWriter();
								out.println(resp);
							}
						} else {
							resp = "{\"ErrorMessage\": \"ユーザ名が不適切\",  \"flag\": 0}";
							out = response.getWriter();
							out.println(resp);
						}
					} else {
						resp = "{\"ErrorMessage\": \"メールアドレスが不適切\",  \"flag\": 0}";
						out = response.getWriter();
						out.println(resp);
					}
				} else {
					resp = "{\"ErrorMessage\": \"文字制限エラー\",  \"flag\": 0}";
					out = response.getWriter();
					out.println(resp);
				}
			} else {
				out.println(JSON.encode("unknownError", true).toString());
				System.out.println("unknownerror");
			}
		} else {
			// 不正アクセス
			resp = "{\"ErrorMessage\": \"不正なアクセス\",  \"flag\": 0}";
			out = response.getWriter();
			out.println(resp);
			System.out.println("husei");
		}

	}

}
