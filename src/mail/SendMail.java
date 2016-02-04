package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class SendInquiry
 */

public class SendMail extends Thread {

	private String address;
	private String username;
	private String nickname;
	private String title;
	private String message;
	private String header = "<br><br>===========================================================" +
			"<br>　CLIP 宇宙猫<br>　http://clip-sc.com/<br>　Mailto：clip.spcat@gmail.com" +
			"<br>===========================================================<br><br>";

	public boolean addUserMail(String address, String username, String nickname) {
		this.address = address;
		this.username = username;
		this.nickname = nickname;

		this.title = "[CLIP]新規登録が完了しました。";

		this.message = "<br>★会員登録完了のお知らせ★<br><br><div>CLIPをご登録いただき、ありがとうございます。<br><br>" +
				"ユーザ登録を下記のとおり完了いたしました。<br><br>ご登録内容のご確認ください。<br><br><br>ユーザネーム:" +
				username + "<br>ニックネーム:" + nickname +
				"<br><br><br><a href='http://clip-sc.com/login/login.html'>ログインページ</a>" + header;
		return true;

	}

	public boolean showUserId(String address, String username, String nickname) {

		return true;
	}

	public void run() {

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

			// HTML設定
			mimeMessage.setContent(message, "text/html;charset=shift-jis");

			Transport.send(mimeMessage);

		} catch (Exception e) {

		}
	}
}
