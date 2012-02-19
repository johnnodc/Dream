package com.dream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageCollection implements Serializable, Iterable<Message>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Message> messages;
	
	public MessageCollection() {
		messages = new ArrayList<Message>();
	}
	
	public Iterator<Message> iterator() {
		// TODO Auto-generated method stub
		return messages.iterator();
	}
}
