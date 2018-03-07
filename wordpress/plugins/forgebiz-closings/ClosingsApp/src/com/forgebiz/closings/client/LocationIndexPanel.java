package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
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

	public ClickHandler createNewLocationHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			// new closing panel
			// how to swap out the panel?
			// fetchClosingSettings(newClosingCallback);

		}
	};
	public ClickHandler searchHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("searchHandler.onClick");
			final String url = URL.encode("http://localhost:8080/wp-json/forgebiz-closings/v1/location/search");
			GWT.log("url = " + url);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
			ClosingsApp.setNonce(builder);
			try {
				builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						closingsApp.displayError("Couldn't retrieve JSON : " + url + exception.getMessage());
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							GWT.log("good result " + response.getStatusText());

							closingsApp.displayError(response.getText());
						} else {
							GWT.log("bad result " + response.getStatusCode());
							closingsApp.displayError("Couldn't retrieve JSON (" + url + response.getStatusText() + ")");
						}
					}
				});
			} catch (RequestException e) {
				closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
			}
		}
	};

	public LocationIndexPanel(ClosingsApp closingsApp) {
		initWidget((Widget) binder.createAndBindUi(this));

		this.searchButton.addClickHandler(this.searchHandler);

		createLocationButton.addClickHandler(this.createNewLocationHandler);

	}

}
