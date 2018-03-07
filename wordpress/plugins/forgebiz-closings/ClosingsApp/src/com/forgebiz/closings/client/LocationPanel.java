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


	private ClosingsApp closingsApp;
	Button saveButton = new Button("Save");

	public LocationPanel(ClosingsApp closingsApp) {
		super();
		this.closingsApp = closingsApp;
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
			Location location = (Location) JavaScriptObject.createObject().cast();
			location.setLocationName(locationNameTextBox.getValue());
			location.setNotificationEmailAddresses(notificationsTextarea.getValue());

			saveLocation(location);
		}
	};
	
	
	
	private void saveLocation(Location location) {
		String url = URL
				.encode("http://localhost:8080//wp-json/forgebiz-closings/v1/location/" + location.getId());
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		GWT.log(" JsonUtils.stringify1");
		String postData = JsonUtils.stringify(location);
		GWT.log(" JsonUtils.stringify2");
		closingsApp.displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					
					closingsApp.displayError(
							"Could not save location "
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						closingsApp.displayMessage(response.getText());
					} else {
						GWT.log("bad result " + response.getStatusCode());
						closingsApp.displayMessage(
								"Could not save location "
										+ response.getStatusText() );
					}
				}
			});
		} catch (RequestException e) {
			closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
		}
	}
	


}
