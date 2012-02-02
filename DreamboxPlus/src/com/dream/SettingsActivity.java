package com.dream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	EditText connectionEdit;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.settings);
	
	    connectionEdit = (EditText)findViewById(R.id.connectionAddressEditText);
	    // TODO Auto-generated method stub
	    
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putString("connectString", connectionEdit.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		  super.onRestoreInstanceState(savedInstanceState);
		  // Restore UI state from the savedInstanceState.
		  // This bundle has also been passed to onCreate.
		  connectionEdit.setText(savedInstanceState.getString("connectString"));
	  
	}

}
