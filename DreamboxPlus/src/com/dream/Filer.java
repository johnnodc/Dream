package com.dream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;

public class Filer
{

	public static <T> T Load(FileInputStream fis, Context context) 
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
			return null;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file " + e.getMessage(), context);
			return null;
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
			ToastMessage.Show("Failed to read file " + e.getMessage(), context);
			return null;
		} 
    	catch (ClassNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to read file " + e.getMessage(), context);
			return null;
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to read file " + e.getMessage(), context);
			return null;
		}
    	finally
    	{
			try 
			{
				if(is != null)
				{
					is.close();
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				ToastMessage.Show("Failed to close file " + e.getMessage(), context);
				return null;
			}			
		}     	    		
    	return t;
	}
	
	public static <T> boolean Save(T t, FileOutputStream fos, Context context)
	{
		
		ObjectOutputStream os = null;
		try 
		{
			os = new ObjectOutputStream(fos);
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ToastMessage.Show("Failed to create steam for saving " + e1.getMessage(), context);
			return true;
		}
		try 
		{
			os.writeObject(t);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to save data " + e.getMessage(), context);
			return true;
		}
		try 
		{
			os.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to close file " + e.getMessage(), context);
			return true;
		}
		
		return false;	
	}
}
