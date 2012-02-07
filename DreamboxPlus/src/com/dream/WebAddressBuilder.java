package com.dream;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebAddressBuilder
{
	
    static String IPNumber;
    
    static String PortNumber;

    private static String basicURL;

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
    	
    	String formURL = "message?text=" + message + "&type=" + infoType + "&timeout=" + timeout;
    	
    	String result = Send(formURL);
    	
        String outputMessage;
    	if (result.equals("OK"))
        {
            outputMessage = "Message Sent OK";
        }
        else
        {
        	outputMessage = "Oops failed to send message " + result;
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
        	return "Connection failed";
        }    	    	
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
}