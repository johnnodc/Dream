package com.dream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;

public class Filer{

	public <T> T LoadSettings(FileInputStream fis, Context context) 
    {
		    	    	
		ObjectInputStream is = null;
		try 
		{
			is = new ObjectInputStream(fis);
		} 
		catch (StreamCorruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
		}
		T t = null;
    	try 
    	{
			t = (T) is.readObject();
		} 
    	catch (OptionalDataException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
		} 
    	catch (ClassNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try 
    	{
			is.close();
			
		} 
    	catch (IOException e) 
    	{		
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
		}
    	    		
    	return t;
	}

}
