package com.app.notepadapp;

public class ContentHandler {
	
	//private variables
	int _id;
	private String _title;
    private String _content;
    private String _created;
    private String _modified;
	
	// Empty constructor
	public ContentHandler(){
		
	}
	// constructor
	public ContentHandler(int id, String title, String content, String created, String modified){
		this._id = id;
		this._title = title;
		this._content = content;
		this._created = created;
		this._modified = modified;
	}
	
	// constructor
	public ContentHandler(String title, String content, String created, String modified){
		this._title = title;
		this._content = content;
		this._created = created;
		this._modified = modified;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting title
	public String getTitle(){
		return this._title;
	}
	
	// setting title
	public void setTitle(String title){
		this._title = title;
	}
	
	// getting content
	public String getContent(){
		return this._content;
	}
	
	// setting content
	public void setContent(String content){
		this._content = content;
	}
	// getting created
	public String getCreated(){
		return this._created;
	}
		
	// setting created
	public void setCreated(String created){
		this._created = created;
	}
	// getting modified
	public String getModified(){
		return this._modified;
	}
		
	// setting modified
	public void setModified(String modified){
		this._modified = modified;
	}
}