package com.dream.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dream.MessengerActivity;
import com.dream.R;
import com.dream.SettingsActivity;

public class SettingsActivityTest  extends ActivityInstrumentationTestCase2<SettingsActivity>
{

	private SettingsActivity mActivity;  // the activity under test
	
	private EditText connectionEdit;
	
	public SettingsActivityTest() 
	{
      super("com.dream", SettingsActivity.class);
	}

	@Override
    protected void setUp() throws Exception 
    {
        super.setUp();
        mActivity = this.getActivity();
        
        connectionEdit = (EditText) mActivity.findViewById(R.id.connectionAddressEditText1);               
    }
	
	public void testPreconditions() 
    {
		assertNotNull(connectionEdit);                        
    }
}
