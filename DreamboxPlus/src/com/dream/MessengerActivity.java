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
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.messenger);	    	    

	    final Button sendButton = (Button) findViewById(R.id.SendMessageButton);
        final EditText messageText = (EditText)findViewById(R.id.SendMessageEditText);        
        final Spinner infoSpinner = (Spinner) findViewById(R.id.SpinnerInfoType);
        final EditText timeoutEdit  = (EditText) findViewById(R.id.timeoutEdit);
        final Button messagePickerButton = (Button) findViewById(R.id.messagePickerButton);
        final Button saveMessageButton = (Button) findViewById(R.id.saveMessageButton);
                      
        timeoutEdit.setText("10");
        infoSpinner.setSelection(1);
        
        messagePickerButton.setOnClickListener(MessagePickerButtonSelected());
        
        sendButton.setOnClickListener(new View.OnClickListener() 
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
        });
	}

	private OnClickListener MessagePickerButtonSelected() {

		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
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
		   ToastMessage.Show(data.getStringExtra("Color"), getApplicationContext());
	   }
	   else{
		   ToastMessage.Show("cancelled", getApplicationContext());
	   }
	  }
	}
}
