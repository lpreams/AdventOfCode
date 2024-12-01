package aoc2015;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		final String s = IO.string(2015, 4);
		int num = 1;
		while (!md5(s + ++num, false));
		System.out.println(num);
		num = 1;
		while (!md5(s + ++num, true));
		System.out.println(num);
	}
	
	private static boolean md5(String str, boolean six) throws NoSuchAlgorithmException {
		final byte[] md5 = MessageDigest.getInstance("MD5").digest(str.getBytes(StandardCharsets.UTF_8));
		if (six) return md5[0] == 0 && md5[1] == 0 && md5[2] == 0;
		if (md5[0] != 0 || md5[1] != 0) return false;
		return String.format("%02X", md5[2]).startsWith("0");
	}
}
