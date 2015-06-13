package com.oil.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringUtils {
	static String charcetName = "gbk";

	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		if (null != is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, Charset.forName(charcetName)));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return sb.toString();
		}
		return "";

	}

	public static InputStream convertStringToIs(String content)
			throws UnsupportedEncodingException {
		return new ByteArrayInputStream(content.getBytes(charcetName));

	}
}
