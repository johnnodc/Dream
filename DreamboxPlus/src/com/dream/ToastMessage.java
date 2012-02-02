package com.dream;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {
	
	public static void Show(String message, Context context)
	{
		Show(message, context, false);
	}
	
	public static void Show(String message, Context context, boolean shortMessage)
	{
		
		int duration;
		
		if(shortMessage == true)
		{
			duration = Toast.LENGTH_SHORT;
		}
		else
		{
			duration = Toast.LENGTH_LONG;
		}
			
        Toast.makeText(context, message, duration).show();
	}

}
