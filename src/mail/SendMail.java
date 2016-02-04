package mail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendInquiry
 */

public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");

		String title = "こんにちは";
		String message = "ほんたいだよ";

		System.out.println("タイトル：" + title);
		System.out.println("メッセージ" + message);

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {
			Properties property = new Properties();

			property.put("mail.smtp.host", "smtp.gmail.com");

			//GmailのSMTPを使う場合
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("clip.spcat", "clipforspacecat");
				}
			});

			/*
			//一般的なSMTPを使う場合

			//ポートが25の場合は省略可能
			property.put("mail.smtp.port", 25);

			Session session =
			        Session.getDefaultInstance(property, null);
			*/

			MimeMessage mimeMessage = new MimeMessage(session);

			InternetAddress toAddress =
					new InternetAddress("shranes00@gmail.com", "nagao");

			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);

			InternetAddress fromAddress =
					new InternetAddress("clip.spcat@gmail.com", "CLIP 宇宙猫");

			mimeMessage.setFrom(fromAddress);

			mimeMessage.setSubject(title, "ISO-2022-JP");

			mimeMessage.setText(message, "ISO-2022-JP");

			Transport.send(mimeMessage);

			out.println("<htm><body>");
			out.println("■お問い合わせ内容を担当者へ送信しました。");
			out.println("<body></html>");
		} catch (Exception e) {
			out.println("<html><body>");
			out.println("■担当者への送信に失敗しました");
			out.println("<br>エラーの内容" + e);
			out.println("</body></html>");
		}

		out.close();
	}

	public boolean addUserMail(String address, String username, String nickname) {

		String message;
		String title = "[CLIP]新規登録が完了しました。";

		message = "<br>★会員登録完了のお知らせ★<br><br><div>CLIPをご登録いただき、ありがとうございます。<br><br>" +
				"ユーザ登録を下記のとおり完了いたしました。<br><br>ご登録内容のご確認ください。<br><br><br>ユーザネーム:" +
				username + "<br>ニックネーム:" + nickname +
				"<br><a href='http://clip-sc.com/login/login.html'>ログインページ</a><br>===========================================================" +
				"<br>　CLIP 宇宙猫<br>　http://clip-sc.com/<br>　Mailto：clip.spcat@gmail.com" +
				"<br>===========================================================<br><br>";

		try {
			createMail(title, message, address, nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;

	}

	private boolean createMail(String title, String message, String address, String username) throws Exception {

		try {
			Properties property = new Properties();

			property.put("mail.smtp.host", "smtp.gmail.com");

			//GmailのSMTPを使う場合
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("clip.spcat", "clipforspacecat");
				}
			});

			/*
			//一般的なSMTPを使う場合

			//ポートが25の場合は省略可能
			property.put("mail.smtp.port", 25);

			Session session =
			        Session.getDefaultInstance(property, null);
			*/

			MimeMessage mimeMessage = new MimeMessage(session);

			InternetAddress toAddress =
					new InternetAddress(address, username);

			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);

			InternetAddress fromAddress =
					new InternetAddress("clip.spcat@gmail.com", "CLIP 宇宙猫");

			mimeMessage.setFrom(fromAddress);

			mimeMessage.setSubject(title, "ISO-2022-JP");

			mimeMessage.setText(message, "ISO-2022-JP");

			Transport.send(mimeMessage);

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}