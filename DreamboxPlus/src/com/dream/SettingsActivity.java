package com.dream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.text.*;

public class SettingsActivity extends Activity {

	EditText connectionAddressEditText1;	
	EditText connectionAddressEditText2;
	EditText portNumberEditText;
	RadioButton connectionSelectionRadio1;
	RadioButton connectionSelectionRadio2;
	
	private Settings settings;
	
	final static String SAVE_SETTINGS_FILENAME = "DSettings";
	final static String RESTORE_SETTING_ADDRESS1 = "connectAddress1String";
	final static String RESTORE_SETTING_ADDRESS2 = "connectAddress1String";
	final static String RESTORE_SETTING_PORT = "connectPortString";
	final static String RESTORE_SETTING_CONNECTION_SELECTION = "connectAddressSelection";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.settings);
	    
	    connectionSelectionRadio1 = (RadioButton) findViewById(R.id.connectionAddressRadioButton1);
	    connectionSelectionRadio2 = (RadioButton) findViewById(R.id.connectionAddressRadioButton2);
	    	    
	    connectionSelectionRadio1.setChecked(true);
	    
	    connectionSelectionRadio1.setOnClickListener(ConnectionRadio1Selected());
	    connectionSelectionRadio2.setOnClickListener(ConnectionRadio2Selected());
	    	    	    	    	    
	    connectionAddressEditText1 = (EditText) findViewById(R.id.connectionAddressEditText1);	    	    	    
	    connectionAddressEditText1.addTextChangedListener(ConnectionAddressEditText1Listner());
	    
	    connectionAddressEditText2 = (EditText) findViewById(R.id.connectionAddressEditText2);
	    connectionAddressEditText2.addTextChangedListener(ConnectionAddressEditText2Listner());
	    
	    //port number
	    portNumberEditText = (EditText) findViewById(R.id.portNumberEditText);
	    
	    // Save button
	    Button saveButton = (Button) findViewById(R.id.saveSettingsButton);	    
	    saveButton.setOnClickListener(SaveButtonSelected());
		  	    		
	    // test connection
	    Button testConnectionButton = (Button) findViewById(R.id.testConnectionButton);
	    testConnectionButton.setOnClickListener(TestButtonSelected());
	    
	    setSettings(new Settings());
	    
	    LoadSettings();
	    
	    UpdateUI();
	}
	
	
    private TextWatcher ConnectionAddressEditText1Listner() {
		// TODO Auto-generated method stub
		return new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub				
			}
			
			public void afterTextChanged(Editable s) {
				if(connectionSelectionRadio1.isChecked())
				{
					SetIPAddress();
				}
			}
		};
	}

 
	private TextWatcher ConnectionAddressEditText2Listner() {
		// TODO Auto-generated method stub
		return new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub				
			}
			
			public void afterTextChanged(Editable s) {
				if(connectionSelectionRadio2.isChecked())
				{
					SetIPAddress();
				}
			}
		};
	}


	private OnClickListener ConnectionRadio1Selected() 
    {
    	return new OnClickListener() 
    	{			
			public void onClick(View v) 
			{								
				connectionSelectionRadio1.setChecked(true);
				connectionSelectionRadio2.setChecked(false);
				SetIPAddress();
			}
    	};
	};
	
	private OnClickListener ConnectionRadio2Selected() 
    {
    	return new OnClickListener() 
    	{			
			public void onClick(View v) 
			{
				connectionSelectionRadio1.setChecked(false);
				connectionSelectionRadio2.setChecked(true);
				SetIPAddress();
			}
    	};
	};	
	
	private void SetIPAddress()
	{
		String IPAddress;
		if(connectionSelectionRadio1.isChecked())
		{
			IPAddress = connectionAddressEditText1.getText().toString();
		}
		else
		{
			IPAddress = connectionAddressEditText2.getText().toString();
		}
		
		WebAddressBuilder.SetIPAddress(IPAddress, portNumberEditText.getText().toString());		
	}

	private void LoadSettings() 
    {
		
    	FileInputStream fis = null;
		try {
			fis = openFileInput(SAVE_SETTINGS_FILENAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return;
		}
    			
		setSettings((Settings)Filer.Load(fis, getApplicationContext()));		
	}

	private void UpdateUI()
	{
		if(getSettings() != null)
		{
	    	if(getSettings().connectionSelectionRadio1)
			{
				connectionSelectionRadio1.setChecked(true);
				connectionSelectionRadio2.setChecked(false);			
			}
			else
			{
				connectionSelectionRadio1.setChecked(false);
				connectionSelectionRadio2.setChecked(true);			
			}
	    	
	    	connectionAddressEditText1.setText(getSettings().IPConnectionAddress1);
			connectionAddressEditText2.setText(getSettings().IPConnectionAddress2);
			portNumberEditText.setText(getSettings().portNumber);
		}
	}
	
	private OnClickListener TestButtonSelected()
    {
    	return new OnClickListener() {			
			public void onClick(View v) 
			{
				String message = WebAddressBuilder.TestConnection();				
				ToastMessage.Show(message, getApplicationContext());							
			}
		};    
	}	
	    			    			
	private OnClickListener SaveButtonSelected() {

		return new OnClickListener() 
		{		
			public void onClick(View v) 
			{																
				getSettings().IPConnectionAddress1 = connectionAddressEditText1.getText().toString();
				getSettings().IPConnectionAddress2 = connectionAddressEditText2.getText().toString();
				getSettings().portNumber = portNumberEditText.getText().toString();		
				getSettings().connectionSelectionRadio1 = connectionSelectionRadio1.isChecked();

				FileOutputStream fos = null;
				try 
				{
					fos = openFileOutput(SAVE_SETTINGS_FILENAME, Context.MODE_PRIVATE);
				} 
				catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastMessage.Show("Failed to open file for saving " + e.getMessage(), getApplicationContext());
					return;
				}
				
				boolean result;
				result = Filer.Save(getSettings(), fos, getApplicationContext());
				
				if(!result)
				{
					ToastMessage.Show("Saved settings", getApplicationContext());
				}
			}
		};
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
				
		savedInstanceState.putString(RESTORE_SETTING_ADDRESS1, connectionAddressEditText1.getText().toString());
		savedInstanceState.putString(RESTORE_SETTING_ADDRESS2, connectionAddressEditText2.getText().toString());
		savedInstanceState.putString(RESTORE_SETTING_PORT, portNumberEditText.getText().toString());
		savedInstanceState.putBoolean(RESTORE_SETTING_CONNECTION_SELECTION, connectionSelectionRadio1.isChecked());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		  super.onRestoreInstanceState(savedInstanceState);
		  // Restore UI state from the savedInstanceState.
		  // This bundle has also been passed to onCreate.
		 
		connectionAddressEditText1.setText(savedInstanceState.getString(RESTORE_SETTING_ADDRESS1));
		connectionAddressEditText2.setText(savedInstanceState.getString(RESTORE_SETTING_ADDRESS2));
		portNumberEditText.setText(savedInstanceState.getString(RESTORE_SETTING_PORT));
		connectionSelectionRadio1.setChecked(savedInstanceState.getBoolean(RESTORE_SETTING_CONNECTION_SELECTION));
	}

	private Settings getSettings() 
	{
		return settings;
	}

	private void setSettings(Settings settings) 
	{
		if(settings != null)
		{
			this.settings = settings;
		}
	}
}
