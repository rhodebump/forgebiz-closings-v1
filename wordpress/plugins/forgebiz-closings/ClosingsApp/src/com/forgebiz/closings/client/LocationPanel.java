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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class LocationPanel extends FlowPanel {
	

	Button deleteButton  = new Button("Delete");
	

	Label locationNameLabel = new Label("Location Name");
	TextBox locationNameTextBox = new TextBox();

	Label notificationsLabel = new Label("Notification Email Addresses");
	TextArea notificationsTextarea = new TextArea();

	
	
	Location location = (Location) JavaScriptObject.createObject().cast();
	Button saveButton = new Button("Save");
	Button cancelButton = new Button("Cancel");	

	
	
	public ClickHandler cancelHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			ClosingsApp.getInstance().clearMain();

		}
	};
	
	AsyncCallback deleteCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			//ClosingsApp.getInstance().displayError("Closing Settings fetch failure: " + throwable.getMessage());
		}

		public void onSuccess(Object response) {
			location.setDeleted(true);
			saveLocation(location, "Location successfully deleted");

		}
	};
	
	
	
	public ClickHandler deleteHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			ConfirmDialogBox cdb = new ConfirmDialogBox("Delete location?", deleteCallback);
			cdb.show();
			cdb.center();


		}
	};
	
	public LocationPanel() {
		super();
		
		add(NumberPanelHelper.getBootStrapPanel(locationNameLabel,locationNameTextBox));
		FlowPanel fp = NumberPanelHelper.getBootStrapPanel(notificationsLabel,notificationsTextarea);
		//Label notificationsHelp = new Label();
		String helptext = "Enter each email address on its own line.  Example:\nuser1@forgebiz.com\nuser2@forgebiz.com\nuser3@forgebiz.com";
		
		//notificationsHelp.setStyleName("text-muted");
		
		
		HTML notificationsHelp = new HTML(new SafeHtmlBuilder().appendEscapedLines(helptext).toSafeHtml());
		notificationsHelp.setStyleName("text-muted");
		
		fp.add(notificationsHelp);
		add(fp);
		
		
		/*
		 *   <small id="passwordHelpInline" class="text-muted">
	      Must be 8-20 characters long.
	    </small>
	    
		 */
		
		FlowPanel buttonsPanel =new FlowPanel();
		add(buttonsPanel);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(deleteButton);
		setupButton(saveButton);
		setupButton(cancelButton);
		setupButton(deleteButton);
		
		cancelButton.addClickHandler(cancelHandler);
		
		saveButton.addClickHandler(this.saveHandler);
		deleteButton.addClickHandler(deleteHandler);
		
	}
	
	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");
			
			location.setLocationName(locationNameTextBox.getValue());
			location.setNotificationEmailAddresses(notificationsTextarea.getValue());

			saveLocation(location, "Location successfully saved");
		}
	};
	
	public void setLocation(Location location) {
		this.location = location;
		locationNameTextBox.setValue(location.getLocationName());
		notificationsTextarea.setValue(location.getNotificationEmailAddresses());
		
	}
	
	private void setupButton(Button button) {
		
		button.setStyleName("btn btn-primary");
		
	}
	
	private void saveLocation(Location location, final String message) {
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
						ClosingsApp.getInstance().clearMain();
						ClosingsApp.getInstance().displayMessage(message);
					} else {
						GWT.log("bad result " + response.getStatusCode());
						ClosingsApp.getInstance().displayMessage(
								"Could not save location:"
										+ response.getText() );
					}
				}
			});
		} catch (RequestException e) {
			ClosingsApp.getInstance().displayError("Could not save location:"+ e.getMessage());
		}
	}
	


}
