package com.forgebiz.closings.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Location extends JavaScriptObject
{
    protected Location() {
    }
    
	/*
	 * 		location_name varchar(100) NOT NULL,
		notification_email_addresses varchar(255) NOT NULL,		
		
	 */
    
    public final native String getLocationName()/*-{
  	return this.location_name;
  	}-*/;
    
    public final native void setLocationName(String location_name) /*-{
    this.location_name = location_name;
    }-*/;
    
    
    public final native String getNotificationEmailAddresses()/*-{
  	return this.notification_email_addresses;
  	}-*/;
    
    public final native void setNotificationEmailAddresses(String notification_email_addresses) /*-{
    this.notification_email_addresses = notification_email_addresses;
    }-*/;
    
    
    public final native int getId()/*-{
  	return this.id;
  	}-*/;
  	

}
