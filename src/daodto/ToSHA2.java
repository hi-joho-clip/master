package daodto;
import org.apache.commons.codec.digest.DigestUtils;

public class ToSHA2 {

    static public String getDigest(String before) {
        String after = null;

        //SHA256でハッシュ
        try {
        	after = DigestUtils.sha256Hex(before);
        } catch (Exception e) {
        	e.printStackTrace();
        }


        return after;
    }

}
