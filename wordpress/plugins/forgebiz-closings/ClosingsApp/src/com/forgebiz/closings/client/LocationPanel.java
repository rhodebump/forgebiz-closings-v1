package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationPanel extends VerticalPanel {
	

	Label locationNameLabel = new Label("Location Name");
	TextBox locationNameTextBox = new TextBox();

	Label notificationsLabel = new Label("Notification Email Addresses");
	TextBox notificationsTextarea = new TextBox();

	Location location = (Location) JavaScriptObject.createObject().cast();
	Button saveButton = new Button("Save");

	public LocationPanel() {
		super();
		add(locationNameLabel);
		add(locationNameTextBox);
		add(notificationsLabel);
		add(notificationsTextarea);
		add(saveButton);
		saveButton.addClickHandler(this.saveHandler);
	}
	
	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");
			
			location.setLocationName(locationNameTextBox.getValue());
			location.setNotificationEmailAddresses(notificationsTextarea.getValue());

			saveLocation(location);
		}
	};
	
	public void setLocation(Location location) {
		this.location = location;
		locationNameTextBox.setValue(location.getLocationName());
		notificationsTextarea.setValue(location.getNotificationEmailAddresses());
		
	}
	
	
	
	private void saveLocation(Location location) {
		String base = ClosingsApp.getURL("/location/save");
		
		String url = URL
				.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		String postData = JsonUtils.stringify(location);
		GWT.log("postData: " + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					
					ClosingsApp.getInstance().displayError(
							"Could not save location "
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						
						ClosingsApp.getInstance().displayMessage("Location was saved");
					} else {
						GWT.log("bad result " + response.getStatusCode());
						ClosingsApp.getInstance().displayMessage(
								"Could not save location "
										+ response.getStatusText() );
					}
				}
			});
		} catch (RequestException e) {
			ClosingsApp.getInstance().displayError("Could not save location:"+ e.getMessage());
		}
	}
	


}
