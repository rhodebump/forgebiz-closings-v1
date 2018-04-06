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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ClosingsApp implements EntryPoint {

	private static ClosingsApp closingsApp = null;

	public static String getURL(String val) {

		GWT.log(" GWT.getModuleBaseURL() =" + GWT.getModuleBaseURL());
		GWT.log(" GWT.getHostPageBaseURL() =" + GWT.getHostPageBaseURL());

		GWT.log(GWT.getHostPageBaseURL() + "../wp-json/forgebiz-closings/v1" + val);
		return GWT.getHostPageBaseURL() + "../wp-json/forgebiz-closings/v1" + val;

	}

	public static ClosingsApp getInstance() {

		return closingsApp;

	}

	public static double getDoubleValue(TextBox textBox) {
		try {
			return Double.parseDouble(textBox.getValue());
		} catch (Exception e) {
			//GWT.log("returning 0 for " + textBox.getName());
		}
		return 0.0D;
	}

	public static void setDouble(TextBox textBox, Double d, Closing closing) {
		if (d == null) {
			setString(textBox, "", closing);
		} else if (d.doubleValue() == 0.0D) {
			setString(textBox, "", closing);
		} else {
			setString(textBox, new Double(d).toString(), closing);
		}

	}

	public static void setString(TextBox textBox, String val, Closing closing) {
		textBox.setValue(val);
		if (closing.getSubmitted()  ==1 ) {
			textBox.setEnabled(false);
		}
	}

	public static void setString(TextArea textBox, String val, Closing closing) {
		textBox.setValue(val);
		if (closing.getSubmitted()   ==1) {
			textBox.setEnabled(false);
		}
	}

	private SimplePanel closingsMain = new SimplePanel();

	private FlowPanel messagesPanel = new FlowPanel();

	public void clearMessages() {
		messagesPanel.clear();
	}

	public void displayMessage(String message) {
		com.google.gwt.user.client.Window.scrollTo(0, 0);

		Label label = new Label();
		label.setStyleName("alert alert-success");
		label.setText(message);
		messagesPanel.add(label);

	}

	public void displayError(String error) {
		com.google.gwt.user.client.Window.scrollTo(0, 0);
		Label label = new Label();
		label.setStyleName("alert alert-danger");
		label.setText(error);
		messagesPanel.add(label);

	}

	private static String NONCE;
	private static String APP_MODE;

	public void initSettings() {
		Dictionary wps = Dictionary.getDictionary("WordpressForgebizSettings");

		NONCE = wps.get("nonce");
		APP_MODE = wps.get("app_mode");

	}

	public static void setNonce(RequestBuilder rb) {

		rb.setHeader("X-WP-Nonce", NONCE);
	}

	public static void fetchClosingSettings(final AsyncCallback callback) {
		String base = ClosingsApp.getURL("/closing-settings/1");
		String url = URL.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		setNonce(builder);

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						JsArray<ClosingSettings> records = JsonUtils
								.<JsArray<ClosingSettings>>safeEval(response.getText());
						ClosingSettings closingSettings = records.get(0);
						callback.onSuccess(closingSettings);

					} else {
						callback.onFailure(new Exception(response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}

	}
	public static void refreshNonce(final AsyncCallback callback) {
		String base = ClosingsApp.getURL("/refresh_nonce");
		String url = URL.encode(base);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		setNonce(builder);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						callback.onSuccess(response);
					} else {
						callback.onFailure(new Exception(response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}

		
	}
	public static void fetchLocations(final AsyncCallback callback) {
		String base = ClosingsApp.getURL("/location/search");
		String url = URL.encode(base);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		setNonce(builder);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						JsArray<Location> records = JsonUtils.<JsArray<Location>>safeEval(response.getText());
						callback.onSuccess(records);
					} else {
						callback.onFailure(new Exception(response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}

	}

	Button searchClosingsButton = new Button("Closings");

	public ClickHandler searchClosingsHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			swapMain(closingsIndexPanel);
		}
	};

	public ClosingsApp() {
		ClosingsApp.closingsApp = this;
	}

	public void onModuleLoad() {
		initSettings();
		RootPanel.get("messagesPanel").add(messagesPanel);
		FlowPanel navigationPanel = new FlowPanel();
		navigationPanel.setStyleName("row");
		RootPanel.get("closingsNav").add(navigationPanel);
		RootPanel.get("closingsMain").add(closingsMain);
		if (APP_MODE.equals("setup")) {

			navigationPanel.add(locationsButton);
			navigationPanel.add(settingButton);
			locationsButton.setStyleName("btn btn-primary");
			locationsButton.addClickHandler(locationsHandler);
			settingButton.addClickHandler(settingHandler);
			settingButton.setStyleName("btn btn-primary");

		} else {
			searchClosingsButton.setStyleName("btn btn-primary");
			this.searchClosingsButton.addClickHandler(searchClosingsHandler);
			navigationPanel.add(this.searchClosingsButton);
			searchClosingsButton.click();
		}

		//refresh nonce hourly
		
		
	    Timer t = new Timer() {
	        @Override
	        public void run() {
	        	refreshNonce(refreshNonceCallback);
	        }
	      };
	      // Schedule the timer to run once in 5 seconds.
	      //t.schedule(5000);
	      //schedule every 60 minutes
	     // t.schedule(60 * 1000 * 60);
	      //testing one a minute
	      t.schedule(60 * 1000);
	      
	}

	Button locationsButton = new Button("Locations");
	private ClickHandler locationsHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			LocationIndexPanel locationIndexPanel = new LocationIndexPanel();
			swapMain(locationIndexPanel);

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

	public void clearMain() {
		closingsMain.clear();
		messagesPanel.clear();
	}

	public void swapMain(Widget panel) {

		clearMain();
		closingsMain.add(panel);

	}
	
	AsyncCallback refreshNonceCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			ClosingsApp.getInstance().displayError("Security token refresh failure: " + throwable.getMessage());
		}

		public void onSuccess(Object response) {
			GWT.log("refreshNonceCallback.onSuccess" + response.toString());
			GWT.log("UPdating NONCE");
			ClosingsApp.NONCE =  response.toString();
		}
	};
	
	

}
