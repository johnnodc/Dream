package com.dream.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dream.R;
import com.dream.WebAddressBuilder;
import com.dream.MessengerActivity;

public class MessageActivityTest extends ActivityInstrumentationTestCase2<MessengerActivity> {
	
	private MessengerActivity mActivity;  // the activity under test
    
	private Button sendButton;
	private EditText messageText;
	private Spinner infoSpinner;
	private EditText timeoutEdit;

    private String resourceString;
	
	public MessageActivityTest() {
	      super("com.dream", MessengerActivity.class);
	    }
		
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        
        sendButton = (Button) mActivity.findViewById(R.id.SendMessageButton);
        messageText = (EditText) mActivity.findViewById(R.id.SendMessageEditText);        
        infoSpinner = (Spinner) mActivity.findViewById(R.id.SpinnerInfoType);
        timeoutEdit  = (EditText) mActivity.findViewById(R.id.timeoutEdit); 
        
        resourceString = mActivity.getString(R.string.message);
    }
	
    public void testPreconditions() 
    {
      assertNotNull(sendButton);
      assertNotNull(messageText);
      assertNotNull(infoSpinner);
      assertNotNull(timeoutEdit);            
    }
        	
}
