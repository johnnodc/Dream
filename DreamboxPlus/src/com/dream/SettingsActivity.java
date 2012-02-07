package com.dream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.text.*;

public class SettingsActivity extends Activity implements TextWatcher {

	EditText connectionEdit;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.settings);
	    
	    connectionEdit = (EditText) findViewById(R.id.connectionAddressEditText);	 
	    
	    Button testConnectionButton = (Button) findViewById(R.id.testConnectionButton);
		  
	    WebAddressBuilder.SetIPAddress(connectionEdit.getText().toString());	
	    
	    connectionEdit.addTextChangedListener(this);
	    
	    testConnectionButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				String message = WebAddressBuilder.TestConnection();				
				ToastMessage.Show(message, getApplicationContext());							
			}
		});    
	}	
		
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		
		
		String FILENAME = "DSettings";
		String string = connectionEdit.getText().toString();

		FileOutputStream fos = null;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			fos.write(string.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		finally {
			if(fos != null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//savedInstanceState.putString("connectString", connectionEdit.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		  super.onRestoreInstanceState(savedInstanceState);
		  // Restore UI state from the savedInstanceState.
		  // This bundle has also been passed to onCreate.
		  
		  String FILENAME = "DSettings";
			String string = connectionEdit.getText().toString();

		  connectionEdit.setText(savedInstanceState.getString("connectString"));
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		WebAddressBuilder.SetIPAddress(connectionEdit.getText().toString());
	}

}
