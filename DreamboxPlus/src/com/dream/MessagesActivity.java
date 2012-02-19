package com.dream;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagesActivity extends ListActivity {

	/** Called when the activity is first created. */
	
	String Category;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	
	    Bundle b = getIntent().getExtras();
	    
	    Category = b.getString("Category");
	    
	    String[] messages = b.getStringArray("Messages");
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, messages);
		
		setListAdapter(adapter);
		
		ToastMessage.Show("Select a message", getApplicationContext());
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		
		 String message = (String) getListAdapter().getItem(position);
		
		 Intent intent = new Intent();
		 intent.putExtra("MessageSelected", message);
		 intent.putExtra("Category", Category);
		 setResult(RESULT_OK, intent);
		 finish();
		 
	}
	
}
