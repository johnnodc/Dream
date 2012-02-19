package com.dream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagePicker extends ListActivity {

	private final String MESSAGE_FILENAME = "DMessage";
	
	static final int GET_CODE = 0;
	
	private MessageCollection messageCollection = null;
	
	private boolean saveSelected;
	private boolean deleteCategorySelected;
	private boolean addCategorySelected;
	private boolean deleteMessageSelected;
	
	private String messageToBeSaved;
	private String categoryToBeAdded;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	
	    //getApplicationContext().deleteFile(MESSAGE_FILENAME);
	    //getApplicationContext().deleteFile(MESSAGE_FILENAME);
	    LoadMessageCollection();
	    
	    String categories[] = GetCategories();
	    
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, categories);
		
		setListAdapter(adapter);	
		
		Bundle b = getIntent().getExtras();
                
		setSaveSelected(b.getBoolean("SaveSelected"));
		setDeleteCategorySelected(b.getBoolean("DeleteCategory"));
		setAddCategorySelected(b.getBoolean("AddCategory"));
		if(isAddCategorySelected())
		{
			setCategoryToBeAdded(b.getString("Category"));
			AddNewCategory();
			finish();
		}
		
		setMessageToBeSaved(b.getString("Message"));
		
		setDeleteMessageSelected(b.getBoolean("DeleteMessage"));
		
		ShowStartMessage();
	}

	private void ShowStartMessage() {
		// TODO Auto-generated method stub
		
		if(isDeleteMessageSelected() || isSaveSelected())
		{
			ToastMessage.Show("Select a category", getApplicationContext());
		}
		else if(isDeleteCategorySelected())
		{
			ToastMessage.Show("Select category to be deleted!!!", getApplicationContext());
		}
		
	}

	private void AddNewCategory() 
	{
		 if(isAddCategorySelected())
		 {
			 Message message = new Message();
			 message.Category = getCategoryToBeAdded();
			 getMessageCollection().messages.add(message);
			 ToastMessage.Show("Category added", getApplication());
			 SaveMessageCollection(false);
		 }	 	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		
		 String category = (String) getListAdapter().getItem(position);
		
		 Intent intent = new Intent();
		 
		 if(isSaveSelected())
		 {
			 Message message = new Message();
			 message.Category = category;
			 message.Message = getMessageToBeSaved();
			 getMessageCollection().messages.add(message);
			 
			 SaveMessageCollection(true);
			 
			 setResult(RESULT_OK, intent);
			 finish();
		 }
		 else if(isDeleteCategorySelected())
		 {
			 
			 Iterator<Message> messageIteractor = getMessageCollection().iterator();
			 
			 for(;messageIteractor.hasNext();)
			 {
				 if (messageIteractor.next().Category.equals(category))
				 {
					 messageIteractor.remove();
					 ToastMessage.Show("Category deleted", getApplicationContext());
				 }
			 }
			 SaveMessageCollection(false);
			 finish();
		 }
		 else if(isDeleteMessageSelected())
		 {
			 String[] messages = GetMessages(category);
			 
			 intent.putExtra("Messages", messages);
			 intent.putExtra("Category", category);
			 intent.setClass(getApplicationContext(), MessagesActivity.class);
			 startActivityForResult(intent, GET_CODE);
		 }
		 
		 // Get message
		 else
		 { 
			 String[] messages = GetMessages(category);
			 
			 intent.putExtra("Messages", messages);
			 intent.setClass(getApplicationContext(), MessagesActivity.class);
			 startActivityForResult(intent, GET_CODE);				
		 }
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  
	  Intent intent = new Intent();
	  
	  if (requestCode == GET_CODE){
	   if (resultCode == RESULT_OK) {
		   String messageSelected = data.getStringExtra("MessageSelected");
		   String category = data.getStringExtra("Category");
		
		   if(isDeleteMessageSelected())
		   {
			   Iterator<Message> messageIteractor = getMessageCollection().iterator();
				 
				 for(;messageIteractor.hasNext();)
				 {
					 Message message = messageIteractor.next();
					 if (message.Category.equals(category) && message.Message != null && message.Message.equals(messageSelected))
					 {
						 messageIteractor.remove();
						 ToastMessage.Show("Message deleted", getApplicationContext());
						 break;
					 }
				 }		
				 SaveMessageCollection(false);
				 finish();
		   }
		   else
		   {
			   intent.putExtra("MessageSelected", messageSelected);
			   setResult(RESULT_OK, intent);
			   finish();
		   }
		   
	   }
	   else{
		   //ToastMessage.Show("cancelled", getApplicationContext());
	   }
	  }
	}
	
	private void LoadMessageCollection()
	{
		
    	FileInputStream fis = null;
		try 
		{	
			fis = openFileInput(MESSAGE_FILENAME);
		} 
		catch (FileNotFoundException e) 
		{
			
			CreateDefaultMessages();
			SaveMessageCollection(false);
					
			return;
		}
    			
		MessageCollection messageCollection = (MessageCollection)Filer.Load(fis, getApplicationContext());			
		
		if(messageCollection == null)
		{
			CreateDefaultMessages();
		}
		else
		{
			setMessageCollection(messageCollection);
		}
	}
	
	private void SaveMessageCollection(boolean showMessage)
	{
		FileOutputStream fos = null;
		try 
		{
			fos = openFileOutput(MESSAGE_FILENAME, Context.MODE_PRIVATE);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ToastMessage.Show("Failed to open file for saving " + e.getMessage(), getApplicationContext());
			return;
		}
		
		boolean result = Filer.Save(getMessageCollection(), fos, getApplicationContext());
		
		if(!result && showMessage)
		{
			ToastMessage.Show("Saved", getApplicationContext());
		}
	}
	
	private void CreateDefaultMessages()
	{
		MessageCollection messageCollection = new MessageCollection();
		
		messageCollection.messages.add(CreateDefaultMessage("For Partner", "Put the kettle on love"));
		messageCollection.messages.add(CreateDefaultMessage("For Partner", "It's my turn for the remote control"));
		messageCollection.messages.add(CreateDefaultMessage("For Partner",  "What is on the other side?"));
		messageCollection.messages.add(CreateDefaultMessage("System Error", "This system will meltdown"));
		messageCollection.messages.add(CreateDefaultMessage("For Kids", "It's time for bed")); 
		messageCollection.messages.add(CreateDefaultMessage("For Kids", "It's time for homework"));
		messageCollection.messages.add(CreateDefaultMessage("For Kids", "It's dinner time"));
		
		setMessageCollection(messageCollection);
	}

	private Message CreateDefaultMessage(String Category, String Message) 
	{
		
		Message message = new Message();
		
		message.Category = Category;
		message.Message = Message;
		
		return message;
	}
	
	private String[] GetCategories()
	{
		
		List<String> categoriesList = new ArrayList<String>();
		
		
		Iterator<Message> messageIterator = getMessageCollection().iterator();
		
		for(;messageIterator.hasNext();)
		{
			String cat = messageIterator.next().Category;
			if(!categoriesList.contains(cat))
			{
				categoriesList.add(cat);
			}
		}
		
		String[] categoriesListArray = (String[]) categoriesList.toArray(new String[categoriesList.size()]);
		
		if(categoriesListArray!= null && categoriesListArray.length > 1)
		{
			Arrays.sort(categoriesListArray);
		}
		
		return categoriesListArray;
	}
	
	private String[] GetMessages(String category) {
		
		List<String> messagesList = new ArrayList<String>();
		
		Iterator<Message> messageIterator = getMessageCollection().iterator();
		
		for(;messageIterator.hasNext();)
		{
			Message message = messageIterator.next();
			String cat = message.Category;
			String mess = message.Message;
			
			if(cat !=null && mess != null && cat.equals(category))
			{
				messagesList.add(message.Message);
			}
		}
		
		String[] messagesListArray = (String[]) messagesList.toArray(new String[messagesList.size()]);
		
		if(messagesListArray != null && messagesListArray.length > 1)
		{
			Arrays.sort(messagesListArray);
		}
		
		return messagesListArray;
	}


	MessageCollection getMessageCollection() {
		return messageCollection;
	}

	void setMessageCollection(MessageCollection messageCollection) {
		this.messageCollection = messageCollection;
	}

	private boolean isSaveSelected() {
		return saveSelected;
	}

	private void setSaveSelected(boolean saveSelected) {
		this.saveSelected = saveSelected;
	}

	private String getMessageToBeSaved() {
		return messageToBeSaved;
	}

	private void setMessageToBeSaved(String messageToBeSaved) {
		this.messageToBeSaved = messageToBeSaved;
	}

	private boolean isDeleteCategorySelected() {
		return deleteCategorySelected;
	}

	private void setDeleteCategorySelected(boolean deleteCategorySelected) {
		this.deleteCategorySelected = deleteCategorySelected;
	}

	private boolean isAddCategorySelected() {
		return addCategorySelected;
	}

	private void setAddCategorySelected(boolean addCategorySelected) {
		this.addCategorySelected = addCategorySelected;
	}

	private String getCategoryToBeAdded() {
		return categoryToBeAdded;
	}

	private void setCategoryToBeAdded(String categoryToBeAdded) {
		this.categoryToBeAdded = categoryToBeAdded;
	}

	private boolean isDeleteMessageSelected() {
		return deleteMessageSelected;
	}

	private void setDeleteMessageSelected(boolean deleteMessageSelected) {
		this.deleteMessageSelected = deleteMessageSelected;
	}		
}
