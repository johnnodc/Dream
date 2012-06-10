package com.dream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WebAddressBuilder
{
	
    static String IPNumber;
    
    static String PortNumber;

    private static String basicURL;
    
    private static int randomMessageNumber = 0;
    
    private static String randomMessage[] = new String[] {"Is your android wifi or 3G switched on?",
    										"Is your sat box switched on?",
    										"Does your sat box web interface switched on?",
	  										"Are you using the right web address and port to match the web interface?"};
    
    private static Document xmlDocument = null;

    public static void SetIPAddress(String ipAddress, String port)
    {
    	IPNumber = ipAddress;
    	PortNumber = port;
    }
    
    private static String BasicURL()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://");
        stringBuilder.append(IPNumber);
        stringBuilder.append(":");
        stringBuilder.append(PortNumber);
        stringBuilder.append("/web/");

        basicURL = stringBuilder.toString();                     
        
        return basicURL;        
    }
    
    public static String SendMessage(String message, long infoType, long timeout)
    {
    	String checkIPMessage = CheckIPSettings();
		if(checkIPMessage != null)
		{
			return checkIPMessage;
		}	
		
		message = ConvertURLMessage(message);
    	
    	String formURL = "message?text=" + message + "&type=" + infoType + "&timeout=" + timeout;
    	
    	String result = Send(formURL);
    	
        String outputMessage;
    	if (result.equals("OK"))
        {
            outputMessage = "Message Sent OK";
        }
        else
        {
        	outputMessage = "Oops failed to send message\nCheck your settings are ok";
        }
    	
    	return outputMessage;    	
    }

	public static String TestConnection()
    {
		String checkIPMessage = CheckIPSettings();
		if(checkIPMessage != null)
		{
			return checkIPMessage;
		}
		
    	String formURL = "about";
    	
    	String result = Send(formURL);
    	
    	  
    	   	 
    	if (result.equals("OK")) 
        {
            return "Connection OK";
        }
        else
        {
        	return "Connection failed\n\n" + randomMessage[GetNextRandomNumber()];        	
        }    	    	
    }
	
	private static int GetNextRandomNumber()
	{
		randomMessageNumber++;
		
		if(randomMessageNumber > randomMessage.length)
		{
			randomMessageNumber = 0;
		}
		
		return randomMessageNumber;
	}
	
	private static String ConvertURLMessage(String message)
	{
		return message.replaceAll(" ", "%20");
	}
    
    private static String CheckIPSettings() {
		
    	if(IPNumber == null || IPNumber.length() == 0)
    	{
    		return "IP address settings are empty";    	
    	}
    	
   		return null;
	}

	private static String Send(String URL)
    {
    	URL url = null;
    	try 
    	{
    		url = new URL(WebAddressBuilder.BasicURL() + URL);
    	} 
    	catch (MalformedURLException e) 
    	{
    		e.printStackTrace();
    	}
    	
    	String result = "";
    	HttpURLConnection urlConnection = null;
		try 
		{
			urlConnection = (HttpURLConnection)url.openConnection();
		  
			urlConnection.setConnectTimeout(5000);
			
			try 
			{
				result = urlConnection.getResponseMessage();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}	
			if(result != null && result.equals("OK"))
			{
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder parser = null;
				
				try {
					parser = factory.newDocumentBuilder();
				} catch (ParserConfigurationException e1) {
					
					e1.printStackTrace();
				}
				try {
					xmlDocument = parser.parse(urlConnection.getInputStream());
				} catch (SAXException e) {
					
					e.printStackTrace();
				}
				
			}
			
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}		            
        finally
        {
            urlConnection.disconnect();
        }
		return result; 
    }

	public static String ResponseMessage() {
		// TODO Auto-generated method stub
		
		String result = Send("messageanswer?getanswer=now");
		
		NodeList list = xmlDocument.getElementsByTagName("e2statetext");
		 if(list.getLength() != 0){
			  Node node = list.item(0);
			  result = node.getFirstChild().getNodeValue();
		 }
		
		return result;
	}
}