package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LocationIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	@UiTemplate("LocationIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, LocationIndexPanel> {
	}

	@UiField
	ClosingsApp closingsApp;

	@UiField
	Button searchButton;
	@UiField
	Button createLocationButton;
	
	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");
			
			JsArray<Location> records =(JsArray<Location>) response;
			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);


			}

		}
	};
	
	

	public ClickHandler createNewLocationHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			LocationPanel locationPanel = new LocationPanel(closingsApp);
			
		}
	};


	public LocationIndexPanel(ClosingsApp closingsApp) {
		initWidget((Widget) binder.createAndBindUi(this));



		createLocationButton.addClickHandler(this.createNewLocationHandler);
		closingsApp.fetchLocations(gotLocationsCallback);
	}

}
