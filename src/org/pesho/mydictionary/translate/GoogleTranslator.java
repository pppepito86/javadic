package org.pesho.mydictionary.translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.pesho.mydictionary.exceptions.MyDictionaryException;
import org.pesho.mydictionary.log.Logger;

public class GoogleTranslator {

	private static final String URL_FORMAT = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=bg&dt=t&q=%s";

	public static String translate(String word) {
		try {
			String response = sendRequest(word);
			String[] split = response.split("\"");
			if (split.length > 1) {
				return split[1];
			}
		} catch (Exception e) {
			Logger.error(e);
		}
		return null;
	}

	private static String sendRequest(String word) throws Exception {
		URL url = new URL(String.format(URL_FORMAT, word));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		if (responseCode != 200) {
			throw new MyDictionaryException(
					"request failed to translate word " + word + "with status code " + responseCode);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			return response.toString();
		}
	}

}
