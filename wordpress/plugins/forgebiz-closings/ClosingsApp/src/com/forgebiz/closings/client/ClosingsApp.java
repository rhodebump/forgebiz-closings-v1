package com.forgebiz.closings.client;
//package com.mycompany.mywebapp.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public class ClosingsApp implements EntryPoint {

	final Label errorLabel = new Label();



	private void displayMessage(String error) {
		errorLabel.setText(error);
	}

	private void displayError(String error) {
		errorLabel.setText(error);

	}

	public void onResponseReceived(Request request, Response response) {
		if (200 == response.getStatusCode()) {
			displayMessage(response.getText());
		} else {
			displayMessage("Couldn't retrieve JSON (" + response.getStatusText() + ")");
		}
	}

	// private static final String JSON_URL = GWT.getModuleBaseURL() + "weather?q=";
	private static final String JSON_URL = GWT.getModuleBaseURL() + "/api/weather?q=";

	private void doTestCall() {

		String url = URL.encode(JSON_URL);
		GWT.log("url = " + url);
		// Send request to server and catch any errors.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		setNonce(builder);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't retrieve JSON : " + JSON_URL + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						displayError(response.getText());
					} else {
						displayError("Couldn't retrieve JSON (" + JSON_URL + response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON : " + e.getMessage());
		}
	}



	public static final String JSON_BASE = "http://localhost:8080/wp-json/forgebiz-closings/v1";

	private static final String JSON_URL2 = JSON_BASE + "/closing-settings/1";

	private static String NONCE;

	public void initSettings() {
		Dictionary wps = Dictionary.getDictionary("WordpressForgebizSettings");

		NONCE = wps.get("nonce");

	}

	public static void setNonce(RequestBuilder rb) {

		rb.setHeader("X-WP-Nonce", NONCE);
	}

	private void fetchClosingSettings(AsyncCallback callback) {
		String url = URL.encode(JSON_URL2);
		GWT.log("url = " + url);
		// http://localhost:8080/forgebiz-closings//wp-json/forgebiz-closings/v1/label/1

		// Send request to server and catch any errors.
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
						JsArray<ClosingSettings> records = JsonUtils.<JsArray<ClosingSettings>>safeEval(response.getText());
						ClosingSettings closingSettings = records.get(0);
						callback.onSuccess(closingSettings);

					} else {
						GWT.log("bad result " + response.getStatusCode());
						//displayError("Couldn't retrieve JSON (" + JSON_URL + response.getStatusText() + ")");
						callback.onFailure(new Exception(response.getStatusText()));
						
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON : " + e.getMessage());
		}

	}

	Button searchClosingsButton = new Button("Search Closing");

	public ClickHandler searchClosingsHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("search handler");
			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			RootPanel.get("closingsMain").clear();
			RootPanel.get("closingsMain").add(closingsIndexPanel);
		}
	};

	Button createClosingButton = new Button("New Closing");

	public ClickHandler createNewClosingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			// new closing panel
			// how to swap out the panel?
			fetchClosingSettings(newClosingCallback);

		}
	};

	Button settingButton = new Button("Settings");

	AsyncCallback newClosingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			ClosingSettings closingSettings = (ClosingSettings) response;
			CashPanel openCashPanel = new CashPanel();
			CashPanel closeCashPanel = new CashPanel();
			SalesPanel salesPanel = new SalesPanel(closingSettings);
			IncomePanel incomePanel = new IncomePanel(closingSettings);
			ClosingPanel closingPanel = new ClosingPanel(closingSettings, openCashPanel, closeCashPanel, salesPanel,
					incomePanel);
			openCashPanel.setClosingPanel(closingPanel);
			closeCashPanel.setClosingPanel(closingPanel);
			RootPanel.get("closingsMain").clear();
			RootPanel.get("closingsMain").add(closingPanel);

		}
	};

	AsyncCallback openSettingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			ClosingSettings closingSettings = (ClosingSettings) response;
			ClosingSettingsPanel csp = new ClosingSettingsPanel(closingSettings);

			RootPanel.get("closingsMain").clear();
			RootPanel.get("closingsMain").add(csp);

		}
	};

	public ClickHandler settingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			fetchClosingSettings(openSettingCallback);

		}
	};

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		initSettings();
		final Button testButton = new Button("Test");

		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		RootPanel.get("sendButtonContainer").add(testButton);
		RootPanel.get("closingsNav").add(createClosingButton);
		createClosingButton.addClickHandler(createNewClosingHandler);

		// ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
		// RootPanel.get("closingsMain").add(createClosingButton);

		RootPanel.get("closingsNav").add(settingButton);
		settingButton.addClickHandler(settingHandler);

		// RootPanel.get("closingsDiv").add(testButton2);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		testButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// do ajax call
				doTestCall();
			}
		});
	}

}
