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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ClosingsApp implements EntryPoint {

	private static ClosingsApp closingsApp = null;
	// public static final String JSON_BASE =
	// "http://localhost:8080/wp-json/forgebiz-closings/v1";
	// String JSON_URL2 = JSON_BASE + "/closing-settings/1";

	// works: http://localhost:8080/wp-json/forgebiz-closings/v1/closing-settings/1
	// currently generating:
	// http://localhost:8080/forgebiz-closings/wp-json/forgebiz-closings/v1/location/search

	// working? http://localhost:8080/wp-json/forgebiz-closings/v1/closing/search

	public static String getURL(String val) {
		// http://localhost:8080/forgebiz-closings/wp-json/forgebiz-closings/v1/closing-settings/search
		// http://localhost:8080/wp-content/plugins/forgebiz-closings/ClosingsApp/war/closingsapp/wp-json/forgebiz-closings/v1/location/search
		GWT.log(" GWT.getModuleBaseURL() =" + GWT.getModuleBaseURL());
		GWT.log(" GWT.getHostPageBaseURL() =" + GWT.getHostPageBaseURL());

		GWT.log(GWT.getHostPageBaseURL() + "../wp-json/forgebiz-closings/v1" + val);
		return GWT.getHostPageBaseURL() + "../wp-json/forgebiz-closings/v1" + val;
		// return "http://localhost:8080/wp-json/forgebiz-closings/v1/closing/search";

	}

	public static ClosingsApp getInstance() {

		return closingsApp;

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

	private SimplePanel closingsMain = new SimplePanel();

	private VerticalPanel messagesPanel = new VerticalPanel();

	public void displayMessage(String error) {
		messagesPanel.add(new Label(error));
	}

	public void displayError(String error) {
		messagesPanel.add(new Label(error));

	}

	private static String NONCE;

	public void initSettings() {
		Dictionary wps = Dictionary.getDictionary("WordpressForgebizSettings");

		NONCE = wps.get("nonce");

	}

	public static void setNonce(RequestBuilder rb) {

		rb.setHeader("X-WP-Nonce", NONCE);
	}

	public static void fetchClosingSettings(AsyncCallback callback) {
		// TODO, change to search
		String base = ClosingsApp.getURL("/closing-settings/1");

		// String JSON_URL2 = JSON_BASE + "/closing-settings/1";
		String url = URL.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		// config.headers['X-WP-Nonce'] = myLocalized.nonce;
		setNonce(builder);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// displayError("Couldn't retrieve JSON : " + JSON_URL2 +
					// exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						JsArray<ClosingSettings> records = JsonUtils
								.<JsArray<ClosingSettings>>safeEval(response.getText());
						ClosingSettings closingSettings = records.get(0);
						// displayMessage(response.getText());
						callback.onSuccess(closingSettings);

					} else {
						callback.onFailure(new Exception(response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			// displayError("Couldn't retrieve JSON : " + e.getMessage());
			callback.onFailure(e);
		}

	}

	public static void fetchLocations(AsyncCallback callback) {
		// String JSON_URL2 = JSON_BASE + "/location/search";
		String base = ClosingsApp.getURL("/location/search");

		String url = URL.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		// config.headers['X-WP-Nonce'] = myLocalized.nonce;
		setNonce(builder);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// displayError("Couldn't retrieve JSON : " + JSON_URL2 +
					// exception.getMessage());
					GWT.log("here");
					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						GWT.log("results=" + response.getText());
						JsArray<Location> records = JsonUtils.<JsArray<Location>>safeEval(response.getText());

						// ClosingSettings closingSettings = records.get(0);
						// displayMessage(response.getText());
						callback.onSuccess(records);

					} else {
						GWT.log("bad result " + response.getStatusCode());
						callback.onFailure(new Exception(response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			// displayError("Couldn't retrieve JSON : " + e.getMessage());
			callback.onFailure(e);
		}

	}

	Button searchClosingsButton = new Button("Closings");

	public ClickHandler searchClosingsHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("search handler");

			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			swapMain(closingsIndexPanel);
		}
	};

	Button settingButton = new Button("Settings");

	AsyncCallback openSettingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			ClosingsApp.getInstance().displayError("Closing Settings fetch failure: " + throwable.getMessage());
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");
			ClosingSettings closingSettings = (ClosingSettings) response;
			ClosingSettingsPanel closingSettingsPanel = new ClosingSettingsPanel(closingSettings);
			swapMain(closingSettingsPanel);

		}
	};

	public ClickHandler settingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			fetchClosingSettings(openSettingCallback);

		}
	};

	Button locationsButton = new Button("Locations");
	// private ClosingIndexPanel closingsIndexPanel = null;

	public ClosingsApp() {
		ClosingsApp.closingsApp = this;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		initSettings();

		RootPanel.get("messagesPanel").add(messagesPanel);
		// RootPanel.get("closingsNav").add(createClosingButton);

		RootPanel.get("closingsNav").add(settingButton);
		searchClosingsButton.addClickHandler(searchClosingsHandler);
		RootPanel.get("closingsNav").add(searchClosingsButton);
		RootPanel.get("closingsNav").add(locationsButton);
		locationsButton.addClickHandler(locationsHandler);
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

	public void clearMain() {
		closingsMain.clear();
		messagesPanel.clear();
	}

	public void swapMain(Widget panel) {

		clearMain();
		closingsMain.add(panel);

	}

}
