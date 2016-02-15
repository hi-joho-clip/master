package daodto;

import org.apache.commons.codec.digest.DigestUtils;

public class ToSHA2 {

	static public String getDigest(String before) {
		String after = null;

		//SHA256でハッシュ
		try {
			System.out.println(before);
			for (int i = 0; i < 1024; i++) {
				after = DigestUtils.sha256Hex(before);
				before = after;
			}
			System.out.println(after);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return after;
	}

}
