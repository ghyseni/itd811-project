package application.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String hashPassword(String plainPassword) throws NoSuchAlgorithmException {

		String hash = null;

		MessageDigest messageDigest;
		messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(plainPassword.getBytes());

		byte byteData[] = messageDigest.digest();

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		hash = stringBuffer.toString();

		return hash;

	}
}
