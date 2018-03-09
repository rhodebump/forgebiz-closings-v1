package com.forgebiz.closings.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClosingsApp implements EntryPoint {

	
	private static ClosingApp closingApp = null;
	
	
	public static ClosingApp getInstance() {

		return closingApp;
		
	}
	
	// final Label errorLabel = new Label();

	public static int getIntValue(TextBox textBox) {
		try {
			return Integer.parseInt(textBox.getValue());
		} catch (Exception e) {
			GWT.log("returning 0 for " + textBox.getName());
		}
		return 0;
	}

	public static double getDoubleValue(TextBox textBox) {
		try {
			return Double.parseDouble(textBox.getValue());
		} catch (Exception e) {
			GWT.log("returning 0 for " + textBox.getName());
		}
		return 0.0D;
	}

	private SimplePanel closingsMain  = new SimplePanel();
	
	private VerticalPanel messagesPanel = new VerticalPanel();

	public void displayMessage(String error) {
		messagesPanel.add(new Label(error));
	}

	public void displayError(String error) {
		messagesPanel.add(new Label(error));

	}

	public void onResponseReceived(Request request, Response response) {
		if (200 == response.getStatusCode()) {
			displayMessage(response.getText());
		} else {
			displayMessage("Couldn't retrieve JSON (" + response.getStatusText() + ")");
		}
	}

	public static final String JSON_BASE = "http://localhost:8080/wp-json/forgebiz-closings/v1";

	

	private static String NONCE;

	public void initSettings() {
		Dictionary wps = Dictionary.getDictionary("WordpressForgebizSettings");

		NONCE = wps.get("nonce");

	}

	public static void setNonce(RequestBuilder rb) {

		rb.setHeader("X-WP-Nonce", NONCE);
	}

	public static void fetchClosingSettings(AsyncCallback callback) {
		String JSON_URL2 = JSON_BASE + "/closing-settings/1";
		String url = URL.encode(JSON_URL2);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		// config.headers['X-WP-Nonce'] = myLocalized.nonce;
		setNonce(builder);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					//displayError("Couldn't retrieve JSON : " + JSON_URL2 + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						JsArray<ClosingSettings> records = JsonUtils
								.<JsArray<ClosingSettings>>safeEval(response.getText());
						ClosingSettings closingSettings = records.get(0);
						//displayMessage(response.getText());
						callback.onSuccess(closingSettings);

					} else {
						//GWT.log("bad result " + response.getStatusCode());
						callback.onFailure(new Exception(response.getStatusText()));

					}
				}
			});
		} catch (RequestException e) {
			//displayError("Couldn't retrieve JSON : " + e.getMessage());
				callback.onFailure(e);
		}

	}

	public void fetchLocations(AsyncCallback callback) {
		String JSON_URL2 = JSON_BASE + "/location/search";
		String url = URL.encode(JSON_URL2);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		// config.headers['X-WP-Nonce'] = myLocalized.nonce;
		setNonce(builder);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't retrieve JSON : " + JSON_URL2 + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						JsArray<Location> records = JsonUtils
								.<JsArray<Location>>safeEval(response.getText());
						//ClosingSettings closingSettings = records.get(0);
						displayMessage(response.getText());
						callback.onSuccess(records);

					} else {
						GWT.log("bad result " + response.getStatusCode());
						callback.onFailure(new Exception(response.getStatusText()));

					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON : " + e.getMessage());
		}

	}
	
	
	Button searchClosingsButton = new Button("Closings");

	public ClickHandler searchClosingsHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("search handler");
			swapMain(closingsIndexPanel);
		}
	};

	Button settingButton = new Button("Settings");

	AsyncCallback openSettingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");
			ClosingSettings closingSettings = (ClosingSettings) response;
			ClosingSettingsPanel closingSettingsPanel = new ClosingSettingsPanel(ClosingsApp.this, closingSettings);
			swapMain(closingSettingsPanel);

		}
	};

	public ClickHandler settingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			fetchClosingSettings(openSettingCallback);

		}
	};

	Button locationsButton = new Button("Locations");
	private ClosingIndexPanel closingsIndexPanel = null;


	
	public ClosingApp() {
		this.closingApp = this;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		initSettings();
		this.closingsIndexPanel = new ClosingIndexPanel(ClosingsApp.this);
			
		RootPanel.get("messagesPanel").add(messagesPanel);
		// RootPanel.get("closingsNav").add(createClosingButton);

		RootPanel.get("closingsNav").add(settingButton);
		searchClosingsButton.addClickHandler(searchClosingsHandler);
		RootPanel.get("closingsNav").add(searchClosingsButton);
		RootPanel.get("closingsNav").add(locationsButton);
		locationsButton..addClickHandler(locationsHandler);
		RootPanel.get("closingsMain").add(closingsMain);

		settingButton.addClickHandler(settingHandler);

	}
	
	private ClickHandler locationsHandler = new ClickHandler() {
	public void onClick(ClickEvent event) {
		// new closing panel
		// how to swap out the panel?
		// fetchClosingSettings(newClosingCallback);
		LocationIndexPanel locationIndexPanel = new LocationIndexPanel();
		swapMain(locationIndexPanel);

	}
	};
	public void swapMain(Panel panel) {
			closingsMain.clear();
			closingsMain.add(panel);
			messagesPanel.clear();
	}

}
