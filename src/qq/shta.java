package qq;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class shta {

	public static String sendTransaction(String pXml, String pUsername,
			String pPassword, String pServerUrl) {
		
		StringBuffer wResult = new StringBuffer();
		try {
			URL wUrl;
			wUrl = new URL(pServerUrl);
			HttpURLConnection wConn = (HttpURLConnection) wUrl.openConnection();
			// Authentification
			String wUser = new String(pUsername + ":" + pPassword);
			String wEncodedPassword = URLEncoder.encode(wUser, "UTF-8");
		//	String wEncoding = new sun.misc.BASE64Encoder().encode(wUser
		//			.getBytes());
			wConn.setRequestProperty("Authorization", "Basic " + wEncodedPassword);
//			wConn.setRequestProperty("Content-Type", "text/xml; charset=UTF8");
			//wConn.setRequestProperty("Content-Type", "text/xml; charset=UTF8; application/x-www-form-urlencoded");
			wConn.setDoOutput(true);
			// Write the transaction
			PrintWriter wPw = new PrintWriter(wConn.getOutputStream());
			wPw.println(pXml);
			wPw.close();
			// Read the result
			InputStreamReader wReader = new InputStreamReader(wConn
					.getInputStream());
			BufferedReader wBuff = new BufferedReader(wReader);
			String wUrlContent = null;
			while ((wUrlContent = wBuff.readLine()) != null) {
				wResult.append(wUrlContent);
				wResult.append("\n");
			}
			wBuff.close();
		} catch (IOException ioe) {
			System.out.println("Error reading URL: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("EXCEPTION CAUGHT e=" + e);
			e.printStackTrace();
		}

		return wResult.toString();
	}

	
	
	public static String send_mail(String send_to, String subj, String msgBody){
String s="OK";
try{
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	   

	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("ymdata@gmail.com"));
	        msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(send_to));
	        msg.setSubject(subj);
	        msg.setText(msgBody);
	        Transport.send(msg);
}catch(Exception e){s=e.toString();}
return s;
	   
	}
	

	public static String s = "", s3 = "";

	public static int k = 1, j = 1, j_max = 5;

	public static String set_google_cookie(String s) {
		String sid = s.substring(s.indexOf("SID="));
		sid = sid.substring(0, sid.indexOf(";") + 1);
		String lsid = s.substring(s.lastIndexOf("LSID="));
		lsid = lsid.substring(0, lsid.indexOf(";") + 1);
		return sid + " " + lsid;
	}

	public static String rep(String line, String old_s, String new_s) {
		int index = 0;
		while ((index = line.indexOf(old_s, index)) >= 0) {
			line = line.substring(0, index) + new_s
					+ line.substring(index + old_s.length());
			index += new_s.length();
		}
		return line;
	}

	public static String rt(String s) {
		int i = s.indexOf("<");
		int j = s.indexOf(">");
		while (i >= 0 && j > i) {
			s = s.substring(0, i) + s.substring(j + 1);
			i = s.indexOf("<");
			j = s.indexOf(">");
		}
		return s;
	}

	public static byte[] hs2b(String hex) {
		java.util.Vector<Object> res = new java.util.Vector<Object>();
		String part;
		int pos = 0; // position in hex string
		final int len = 2; // always get 2 items.
		while (pos < hex.length()) {
			part = hex.substring(pos, pos + len);
			pos += len;
			int byteVal = Integer.parseInt(part, 16);
			res.add(new Byte((byte) byteVal));
		}
		if (res.size() > 0) {
			byte[] b = new byte[res.size()];
			for (int i = 0; i < res.size(); i++) {
				Byte a = (Byte) res.elementAt(i);
				b[i] = a.byteValue();
			}
			return b;
		} else {
			return null;
		}
	}

	public static String b2hs(byte[] buf) {
		StringBuffer sbuff = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			int b = buf[i];
			if (b < 0)
				b = b & 0xFF;
			if (b < 16)
				sbuff.append("0");
			sbuff.append(Integer.toHexString(b).toUpperCase());
		}
		return sbuff.toString();
	}

	public static String rfu2(String url, String enc) throws Exception {
		String s = "", str = "";

		// try {
		URL u = new URL(url);

		BufferedReader in = new BufferedReader(new InputStreamReader(u
				.openStream(), enc));// enc - CP1251, UTF-8, so on
		while ((str = in.readLine()) != null) {
			s = s + str + "\r\n";
		}
		in.close();

		// } catch (IOException e) {
		// System.err.println(e);
		// return e.toString();
		// }
		// s = rep(s,"?","-----");

		return s;
	}

	public static String rff(String str)  {
		String s = "";
		try {

			FileReader r = new FileReader(str);
			BufferedReader in = new BufferedReader(r);
			while ((str = in.readLine()) != null) {
				s = s + str + "\r\n";
			}
			in.close();

		} catch (Exception ee) {
			s = ee.toString();

		}
		return s;
	}

	public static String rfu(String url) {
		StringBuffer s = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream in = u.openConnection().getInputStream();
			for (int ch = in.read(); ch > 0; ch = in.read()) {
				s.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
			return e.toString();
		}
		return s.toString();
	}

	public static void tokenize(String s) {
		// s = "9 23 45.4 56.7";
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
			System.out.println(st.nextToken());
		}
		// System.out.println("OK");
	}

	public static String rfu_win(String url) throws Exception {
		String s = "", str = "";

		try {
			URL u = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream(), "CP1251"));// enc - CP1251, UTF-8, so on
			while ((str = in.readLine()) != null) {
				s = s + str;
			}
			in.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return s;
	}

	public static String rfu_utf(String url) throws Exception {
		String s = "", str = "";

		try {
			URL u = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream(), "UTF-8"));// enc - CP1251, UTF-8, so on
			while ((str = in.readLine()) != null) {
				s = s + str;
			}
			in.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return s;
	}

	public static String rsp(String s) {
		Pattern pattern = Pattern.compile("\\s+");
		Matcher matcher = pattern.matcher(s);
		return matcher.replaceAll(" ");
	}

	public static String zir(String zips) throws Exception {
		String s = "", ss = "", sz = "";
		int n = 0;
		File f = new File(zips);
		for (int i = 0; i < f.list().length; i++) {
			s = f.list()[i];
			s = s.substring(s.indexOf("-") + 1, s.indexOf("."));
			n = n + Integer.parseInt(s);
		}
		int j = (int) (n * Math.random());
		n = 0;
		for (int i = 0; i < f.list().length; i++) {
			s = f.list()[i];
			sz = s;
			s = s.substring(s.indexOf("-") + 1, s.indexOf("."));
			n = n + Integer.parseInt(s);
			if (j < n) {
				break;
			}
		}
		// --------------------------------------------------------------
		s = "";
		int i = Integer.parseInt(sz.substring(sz.indexOf("-") + 1, sz
				.indexOf(".")));
		int k = (int) (i * Math.random()) + 1;
		ZipFile zipFile = new ZipFile(new File(zips + "/" + sz));
		ZipEntry entry = zipFile.getEntry(k + ".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(zipFile
				.getInputStream(entry), "UTF-8"));
		while ((ss = br.readLine()) != null) {
			s = s + ss;
		}
		br.close();
		zipFile.close();
		return s;
	}

}
// int n = (int)(10 * Math.random()) + 1;

