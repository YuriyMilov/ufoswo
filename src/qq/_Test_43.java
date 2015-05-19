package qq;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class _Test_43 {

	public static void main(String[] args) throws Exception{
		String s="helglo";
		s = URLEncoder.encode("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spellrequest textalreadyclipped=\"0\" ignoredigits=\"1\" ignoreallcaps=\"1\" ignoredups=\"1\"><text>"+s+"</text></spellrequest>", "UTF-8");

	        try {
	            URL url = new URL("https://www.google.com/tbproxy/spell?lang=en&hl=en");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");

	            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            writer.write(s);
	            writer.close();
	    
	            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                // OK
	            	

	            	
	            	
	        		DataInputStream inStream = new DataInputStream(connection.getInputStream());
	        		StringBuffer stb = new StringBuffer();

	        		int ch = ' ';
	        		for (ch = inStream.read(); ch > 0; ch = inStream.read()) {
	        			stb.append((char) ch);
	        			 System.out.print((char) ch);
	        			if (stb.indexOf("\r\n0\r\n\r\n") > -1)
	        				break;
	        		}
	        		inStream.close();
	        		
	            } else {
	                // Server returned HTTP error code.
	            }
	        } catch (MalformedURLException e) {
	            // ...
	        } catch (IOException e) {
	            // ...
	        }
	}

}
