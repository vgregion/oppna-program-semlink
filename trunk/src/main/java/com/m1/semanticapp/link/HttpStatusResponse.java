package com.m1.semanticapp.link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpStatusResponse {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private Hashtable<Integer,String> statusCodes;
	private URL url;
	
	public HttpStatusResponse(){}
	
	public int getResponseCode(String siteUrl){
		
		int responseCode = 0;
		HttpURLConnection http = null;
		
		try {
			url = new URL(siteUrl);
			http = (HttpURLConnection) url.openConnection();
			responseCode = http.getResponseCode();
			System.out.println(url.getHost());
			url = null;
			
			//responseMessage = http.getResponseMessage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			http.disconnect();
		}
		
		return responseCode;
		
	}
	
	public void pingUrl(String siteUrl) throws HttpException, IOException{

		int timeout = 100;
		HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(timeout);
        
        HttpClient httpClient = new HttpClient(httpClientParams);
        HttpMethod method = new GetMethod(siteUrl);
        int statusCode = httpClient.executeMethod(method);
		System.out.println("statusCode: "+statusCode);
		
	}
	
	/**
	 * Translates the code to Swedish.
	 * @param siteUrl
	 */
	public String getTranslatedResponseMessage(int responseCode){
		if(statusCodes == null){
			statusCodes();
		}
		String responseMessage;
		if(statusCodes.containsKey(responseCode)){
			responseMessage = statusCodes.get(responseCode);
		} else {
			responseMessage = " ";
		}
		return responseMessage;
	}
	
	/*
	 * Translated status codes
	 */
	
	private void statusCodes(){
		statusCodes = new Hashtable<Integer, String>();
		statusCodes.put(100, "Fortsätt");
		statusCodes.put(101, "Byter Protokoll");
		statusCodes.put(200, "OK");
		statusCodes.put(201, "Skapad");
		statusCodes.put(202, "Accepterad");
		statusCodes.put(203, "Icke-aktoritär Information");
		statusCodes.put(204, "Inget innehåll");
		statusCodes.put(205, "Nollställt innehåll");
		statusCodes.put(206, "Delvist innehåll");
		statusCodes.put(300, "Flerval");
		statusCodes.put(301, "Flyttad");
		statusCodes.put(302, "Hittad");
		statusCodes.put(303, "Se Annan");
		statusCodes.put(304, "Icke Modifierad");
		statusCodes.put(305, "Använd Proxy");
		statusCodes.put(307, "Temporär Redirect");
		statusCodes.put(400, "Dålig Förfrågan");
		statusCodes.put(401, "Obehörig");
		statusCodes.put(403, "Förbjuden");
		statusCodes.put(404, "Ej hittad");
		statusCodes.put(405, "Metod Ej Beviljad");
		statusCodes.put(406, "Ej Acceptabel");
		statusCodes.put(407, "Proxy Verifiering Erfordras");
		statusCodes.put(408, "Request Timeout");
		statusCodes.put(409, "Konflikt");
		statusCodes.put(410, "Borta");
		statusCodes.put(411, "Längd Erfordras");
		statusCodes.put(412, "Förhandsvillkor Misslyckades");
		statusCodes.put(413, "Request Entitet För Stor");
		statusCodes.put(414, "Request URI För Lång");
		statusCodes.put(415, "Ogrundad Media Typ");
		statusCodes.put(416, "Frågad Omfattning Ej Uppfylld");
		statusCodes.put(417, "Expectation Misslyckat");
		statusCodes.put(500, "Internt Server Fel");
		statusCodes.put(501, "Ej Implementerad");
		statusCodes.put(502, "Bad Gateway");
		statusCodes.put(503, "Service Ej Tillgänglig");
		statusCodes.put(504, "Gateway Timeout");
		statusCodes.put(505, "HTTP Version Stöds Ej");
	}
	
	/*
	 * Early tests
	 */
	
	public static void main(String args[]) throws HttpException, IOException {
		
		HttpStatusResponse ht = new HttpStatusResponse();
		String a = ht.getTranslatedResponseMessage(404);
		System.out.println("Responsecode: " + a);

	}
}
