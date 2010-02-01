package se.vgr.crawler.status;

import java.util.Hashtable;

/**
 * <p>Http status code</p>
 * @author Johan S�ll Larsson
 */

public class StatusCode {

	public static Hashtable<Integer, String> statusCodes;
	
	static {
		
		statusCodes = new Hashtable<Integer, String>();
		statusCodes.put(100, "Forts�tt");
		statusCodes.put(101, "Byter Protokoll");
		statusCodes.put(200, "OK");
		statusCodes.put(201, "Skapad");
		statusCodes.put(202, "Accepterad");
		statusCodes.put(203, "Icke-aktorit�r Information");
		statusCodes.put(204, "Inget inneh�ll");
		statusCodes.put(205, "Nollst�llt inneh�ll");
		statusCodes.put(206, "Delvist inneh�ll");
		statusCodes.put(300, "Flerval");
		statusCodes.put(301, "Flyttad");
		statusCodes.put(302, "Hittad");
		statusCodes.put(303, "Se Annan");
		statusCodes.put(304, "Icke Modifierad");
		statusCodes.put(305, "Anv�nd Proxy");
		statusCodes.put(307, "Tempor�r Redirect");
		statusCodes.put(400, "D�lig F�rfr�gan");
		statusCodes.put(401, "Obeh�rig");
		statusCodes.put(403, "F�rbjuden");
		statusCodes.put(404, "Ej hittad");
		statusCodes.put(405, "Metod Ej Beviljad");
		statusCodes.put(406, "Ej Acceptabel");
		statusCodes.put(407, "Proxy Verifiering Erfordras");
		statusCodes.put(408, "Request Timeout");
		statusCodes.put(409, "Konflikt");
		statusCodes.put(410, "Borta");
		statusCodes.put(411, "L�ngd Erfordras");
		statusCodes.put(412, "F�rhandsvillkor Misslyckades");
		statusCodes.put(413, "Request Entitet F�r Stor");
		statusCodes.put(414, "Request URI F�r L�ng");
		statusCodes.put(415, "Ogrundad Media Typ");
		statusCodes.put(416, "Fr�gad Omfattning Ej Uppfylld");
		statusCodes.put(417, "Expectation Misslyckat");
		statusCodes.put(500, "Internt Server Fel");
		statusCodes.put(501, "Ej Implementerad");
		statusCodes.put(502, "Bad Gateway");
		statusCodes.put(503, "Service Ej Tillg�nglig");
		statusCodes.put(504, "Gateway Timeout");
		statusCodes.put(505, "HTTP Version St�ds Ej");
		
	}
	
}
