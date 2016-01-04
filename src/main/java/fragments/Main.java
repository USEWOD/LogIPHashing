package fragments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(args[0]));
			File file = new File(args[0]);
			String fileName = file.getName();
			PrintWriter writer = new PrintWriter("hashed_" + fileName, "UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");

			Pattern patter = Pattern.compile("^([0-9.]+)");

			int lineCounter = 0;
			int processedCounter = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				lineCounter++;
				Matcher matcher = patter.matcher(sCurrentLine);
				if (matcher.find()) {
					String ip = matcher.group(0);
					// Integer hash = matcher.group(0).hashCode();
					md.update(ip.getBytes());
					byte[] thedigest = md.digest();
					StringBuffer sb = new StringBuffer();
					for (byte b : thedigest) {
						sb.append(String.format("%02x", b & 0xff));
					}
					String repalced = sCurrentLine.replaceFirst("^[0-9.]+", sb.toString());
					writer.println(repalced);
					processedCounter++;
				}
			}
			writer.close();
			System.out.println("line counter " + lineCounter);
			System.out.println("Line being processed " + processedCounter);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
