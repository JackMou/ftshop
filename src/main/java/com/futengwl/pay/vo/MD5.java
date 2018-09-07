package com.futengwl.pay.vo;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

public class MD5 {
	/**
	 * 16进制数据
	 */

	private MD5() {}
	
	public final static String getMessageDigest(byte[] buffer) {
		char [] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char [] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				str[k++] = HEX_DIGITS[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符数组转换为 String
	 *
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte [] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	/**
	 * 字符转换为 string
	 *
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}
	/**
	 * MD5 摘要获取
	 *
	 * @param origin
	 * @param charsetname 字符集,可以不传
	 * @return
	 */
	public static String MD5Encode(String origin, String charsetname) {
		Preconditions.checkNotNull(origin, "MD5_SOURCE_NULL");
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (StringUtils.isBlank(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			}
			else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}
	/**
	 * 16进制数据
	 */
	private static final String [] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
