package com.dream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ManageMessages extends Activity {

	Button deleteCategoryButton;
	Button addCategoryButton;
	Button addMessageButton;
	Button deleteMessageButton;
	
	EditText addCategoryEditText;
	EditText addMessageEditText;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.managemessagesview);
	    
	    addCategoryEditText = (EditText) findViewById(R.id.addCategoryEditText);
	    addMessageEditText = (EditText) findViewById(R.id.addMessageEditText);
	    
	    deleteCategoryButton = (Button) findViewById(R.id.deleteCategoryButton);
	    deleteMessageButton = (Button) findViewById(R.id.deleteMessagesButton);
	    addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
	    addMessageButton = (Button) findViewById(R.id.addMessageButton);
	    	    
	    deleteCategoryButton.setOnClickListener(DeleteCategorySelected());
	    addCategoryButton.setOnClickListener(AddCategorySelected());
	    addMessageButton.setOnClickListener(AddMessageSelected());
	    deleteMessageButton.setOnClickListener(DeleteMessageButtonSelected());
	    
	}
	
	private OnClickListener DeleteMessageButtonSelected() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("DeleteMessage", true);
				intent.setClass(getApplicationContext(), MessagePicker.class);
				startActivity(intent);
				finish();
			}
		};
	}

	private OnClickListener AddMessageSelected() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(addMessageEditText.getText().toString().length() == 0)
				{
					ToastMessage.Show("Message text missing", getApplicationContext());
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("SaveSelected", true);
				intent.putExtra("Message", addMessageEditText.getText().toString());
				intent.setClass(getApplicationContext(), MessagePicker.class);
				startActivity(intent);				
				finish();
				addMessageEditText.setText("");
			}
		};
	}

	private OnClickListener AddCategorySelected() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			public void onClick(View v) 
			{
				if(addCategoryEditText.getText().toString().length() == 0)
				{
					ToastMessage.Show("Category missing", getApplicationContext());
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("AddCategory", true);
				intent.putExtra("Category", addCategoryEditText.getText().toString());
				intent.setClass(getApplicationContext(), MessagePicker.class);
				startActivity(intent);			
				finish();
				addCategoryEditText.setText("");
			}
		};
	}

	private OnClickListener DeleteCategorySelected() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("DeleteCategory", true);
				intent.setClass(getApplicationContext(), MessagePicker.class);
				startActivity(intent);
				finish();
			}
		};
	}
}
