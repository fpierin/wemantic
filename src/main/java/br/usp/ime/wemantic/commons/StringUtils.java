package br.usp.ime.wemantic.commons;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class StringUtils {

	public static boolean isNotBlank(final String keyString) {
		return keyString != null && !keyString.isEmpty();
	}

	public static boolean isBlank(final String keyString) {
		return !isNotBlank(keyString);
	}

	public static String convertStreamToString(final InputStream is, final String encoding) {
		@SuppressWarnings("resource")
		final Scanner s = new Scanner(is, encoding).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static String md5(final String string) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		final MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(string.getBytes(), 0, string.length());
		String md5Hash = new BigInteger(1, digest.digest()).toString(16);
		while (md5Hash.length() < 32) {
			md5Hash = "0" + md5Hash;
		}
		return md5Hash;
	}

}