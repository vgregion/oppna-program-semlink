package se.vgr.crawler.status;

import java.util.Hashtable;

/**
 * <p>Http status code</p>
 * @author Johan Säll Larsson
 */

public class StatusCode {

	public static Hashtable<Integer, String> statusCodes;
	
	static {
		
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
	
}
