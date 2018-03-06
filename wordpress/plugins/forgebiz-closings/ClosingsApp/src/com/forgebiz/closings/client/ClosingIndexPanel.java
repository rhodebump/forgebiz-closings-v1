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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	private void displayMessage(String error) {
		this.errorLabel.setText(this.errorLabel.getText() + " " + error);
	}

	private void displayError(String error) {
		displayMessage(error);
	}

	@UiField
	Label errorLabel;

	@UiField
	DateBox startDatePicker = new DateBox();

	@UiField
	DateBox endDatePicker = new DateBox();

	@UiField
	Button searchButton = new Button("Search");

	public ClickHandler searchHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("searchHandler.onClick");
			final String url = URL.encode("http://localhost:8080/wp-json/forgebiz-closings/v1/closing/search");
			GWT.log("url = " + url);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
			ClosingsApp.setNonce(builder);
			try {
				builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						ClosingIndexPanel.this.displayError("Couldn't retrieve JSON : " + url + exception.getMessage());
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							GWT.log("good result " + response.getStatusText());

							ClosingIndexPanel.this.displayError(response.getText());
						} else {
							GWT.log("bad result " + response.getStatusCode());
							ClosingIndexPanel.this
									.displayError("Couldn't retrieve JSON (" + url + response.getStatusText() + ")");
						}
					}
				});
			} catch (RequestException e) {
				ClosingIndexPanel.this.displayError("Couldn't retrieve JSON : " + e.getMessage());
			}
		}
	};

	public ClosingIndexPanel() {
		initWidget((Widget) binder.createAndBindUi(this));
		this.startDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
		this.endDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));

		this.searchButton.addClickHandler(this.searchHandler);
	}

	@UiTemplate("ClosingIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingIndexPanel> {
	}
}
