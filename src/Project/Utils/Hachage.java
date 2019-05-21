package Project.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hachage {

    private static String bytesToHex(byte[] b) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        StringBuilder buffer = new StringBuilder();
        for (byte aB : b) {
            buffer.append(hexDigits[(aB >> 4) & 0x0f]);
            buffer.append(hexDigits[aB & 0x0f]);
        }
        return buffer.toString();
    }

    public static String encrypt(String algorithm, String password) {
        byte[] digest = null;
        try {
            MessageDigest sha = MessageDigest.getInstance(algorithm);
            digest = sha.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (digest != null) {
            return bytesToHex(digest);
        }
        return password;

    }
}
