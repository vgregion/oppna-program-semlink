package se.vgr.crawler.status;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>Checks the status of an http connection</p>
 * @author Johan Säll Larsson
 */

public class HttpStatus {

	public HttpStatus() {}
	
	public int getResponseCode(String url){
		
		int responseCode = 0;
		HttpURLConnection http = null;
		
		try {
			
			URL u = new URL(url);
			http = (HttpURLConnection) u.openConnection();
			responseCode = http.getResponseCode();
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			
		} finally {
			
			http.disconnect();
			
		}
		
		return responseCode;
		
	}
	
}
