package com.dream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MessengerActivity extends Activity {

	static final int GET_CODE = 0;
	EditText messageText;
	EditText timeoutEdit;
	Spinner infoSpinner;
	Button sendButton;
	Button messagePickerButton;
	Button saveMessageButton;
	Button manageMessagesButton;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.messenger);	    	    

	    sendButton = (Button) findViewById(R.id.SendMessageButton);
        messageText = (EditText)findViewById(R.id.SendMessageEditText);        
        infoSpinner = (Spinner) findViewById(R.id.SpinnerInfoType);
        timeoutEdit = (EditText) findViewById(R.id.timeoutEdit);
        messagePickerButton = (Button) findViewById(R.id.messagePickerButton);
        saveMessageButton = (Button) findViewById(R.id.saveMessageButton);
        manageMessagesButton = (Button) findViewById(R.id.manageMessagesButton);
                      
        timeoutEdit.setText("10");
        infoSpinner.setSelection(1);
        
        messagePickerButton.setOnClickListener(MessagePickerButtonSelected());
        
        sendButton.setOnClickListener(SendButtonSelected());
        
        saveMessageButton.setOnClickListener(SaveMessageSelected());
        
        manageMessagesButton.setOnClickListener(ManageMessagesSelected());
        
   	}
	
	private OnClickListener ManageMessagesSelected() {
		return new OnClickListener() {
			
			public void onClick(View arg0) 
			{
				Intent intent = new Intent();
			    intent.setClass(getApplicationContext(), ManageMessages.class);
			    startActivityForResult(intent, GET_CODE);
			}
		};
	}

	private OnClickListener SaveMessageSelected() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			public void onClick(View arg0) {
				if(messageText.getText().toString().length() == 0)
            	{
            		ToastMessage.Show("Message text is missing", getApplicationContext());
            		return;
            	}
				Intent intent = new Intent();
				intent.putExtra("SaveSelected", true);
				intent.putExtra("Message", messageText.getText().toString());
			    intent.setClass(getApplicationContext(), MessagePicker.class);
			    startActivityForResult(intent, GET_CODE);						
			}
		};
	}

	private OnClickListener SendButtonSelected() 
	{
		return new View.OnClickListener() 
        {
            public void onClick(View v) 
            {

            	if(messageText.getText().toString().length() == 0)
            	{
            		ToastMessage.Show("Message text is missing", getApplicationContext());
            		return;
            	}
            	
               	long infoType = infoSpinner.getSelectedItemId();
               	
               	String timeoutString = timeoutEdit.getText().toString();
               	
               	long timeout = Long.parseLong(timeoutString);               	               
               	
            	String outputMessage = WebAddressBuilder.SendMessage(messageText.getText().toString(), infoType, timeout);
                
            	ToastMessage.Show(outputMessage, getApplicationContext());                 
             }
        };
	}

	private OnClickListener MessagePickerButtonSelected() 
	{
		return new OnClickListener() 
		{			
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				intent.putExtra("SaveSelected", false);
			    intent.setClass(getApplicationContext(), MessagePicker.class);
			    startActivityForResult(intent, GET_CODE);				
			}
		};
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  
	  if (requestCode == GET_CODE){
	   if (resultCode == RESULT_OK) {
		   String message = data.getStringExtra("MessageSelected");
		   messageText.setText(message);		   
	   }
	   else{
		   //ToastMessage.Show("cancelled", getApplicationContext());
	   }
	  }
	}
}
