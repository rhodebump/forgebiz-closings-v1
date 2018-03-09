package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingPanel extends Composite {

	private Closing closing = null;
	/*
	 * <g:Button ui:field="saveButton">Save</g:Button> <g:Button
	 * ui:field="submitButton">Submit</g:Button>
	 * 
	 * <g:Button ui:field="deleteButton">Delete</g:Button>
	 */

	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	@UiField
	Button saveButton;

	@UiField
	Button submitButton;

	@UiField
	Button deleteButton;

	@UiField
	TextBox openerNameTextBox;
	@UiField
	TextBox closerNameTextBox;

	@UiField
	TextArea notesTextArea;

	private ClosingSettings closingSettings = null;
	@UiField
	CashPanel openCashPanel;
	@UiField
	CashPanel closeCashPanel;
	@UiField
	SalesPanel salesPanel;
	@UiField
	IncomePanel incomePanel;

	@UiField
	DateBox closingDateBox;

	ClosingsApp closingsApp = null;

	@UiField
	ListBox locationListBox = new ListBox();

	/*
	 * ListBox lb = new ListBox(); lb.addItem("foo"); lb.addItem("bar");
	 * lb.addItem("baz"); lb.addItem("toto"); lb.addItem("tintin");
	 * 
	 * // Make enough room for all five items (setting this value to 1 turns it //
	 * into a drop-down list). lb.setVisibleItemCount(5);
	 */
	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");

			JsArray<Location> records = (JsArray<Location>) response;
			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);

				locationListBox.addItem(location.getLocationName(), new Integer(location.getId()).toString());

			}

		}
	};

	/*
	 * ClosingsApp closingsApp, ClosingSettings closingSettings, CashPanel
	 * openCashPanel, CashPanel closeCashPanel, SalesPanel salesPanel, IncomePanel
	 * incomePanel, Button saveButton, DateBox closingDateBox, ListBox
	 * locationListBox, Closing closing
	 * 
	 */
	public ClosingPanel() {

		this.saveButton.addClickHandler(this.saveHandler);
		this.submitButton.addClickHandler(submitHandler);
		this.deleteButton.addClickHandler(deleteHandler);

		initWidget((Widget) binder.createAndBindUi(this));
		GWT.log("closingpanel");
		closingsApp.fetchLocations(gotLocationsCallback);

	}

	public void calculateAll() {
		GWT.log("calculateAll");
		this.openCashPanel.calculateAll();
		double openCashTotal = this.openCashPanel.getCashTotal();
		GWT.log("openCashTotal " + openCashTotal);
		this.closeCashPanel.calculateAll();
		double closeCashTotal = this.closeCashPanel.getCashTotal();
		GWT.log("closeCashTotal " + closeCashTotal);
		double totalCash = closeCashTotal - openCashTotal;
		GWT.log("totalCash " + totalCash);

		// total a is sales
		// total b is income

		// this.incomePanel.setCashTotalCash(totalCash);
		incomePanel.calculateAll();
		// closing.totalB = closing.incomeTotal;
		double income_total = incomePanel.getTotal();

		closing.setIncomeTotal(income_total);
		
		salesPanel.calculateAll();
		double sales_total = salesPanel.getTotal();
		closing.setSalesTotal(sales_total);
		double difference = income_total - sales_total;
		closing.setDifference(difference);

		/*
		 * double difference = 0.0D; closing.setDifference(difference);
		 * closing.setGrossSales(); closing.setIncomeCash(); closing.setIncomeTotal();
		 * closing.setOpenTotal(); closing.setTotalSales();
		 * 
		 */
		/*
		 * 
		 * calculateOpenTotals(closing); calculateClosingTotals(closing);
		 * calculateIncomeTotals(closing); calculateSalesTotals(closing);
		 * calculateDiffTotal(closing);
		 * 
		 * function calculateDiffTotal(closing) { console.log("calculateDiffTotal"); var
		 * totala, totalb, total, difference; totala = parseFloat(closing.totalA);
		 * console.log("calculateDiffTotal totala=" + totala); totalb =
		 * parseFloat(closing.totalB); console.log("calculateDiffTotal totalB=" +
		 * totalb); total = totala - totalb; console.log("calculateDiffTotal total=" +
		 * total); difference = total.toFixed(2);
		 * console.log("calculateDiffTotal difference=" + difference);
		 * closing.difference = difference; }
		 * 
		 * 
		 * 
		 */

	}

	@UiTemplate("ClosingPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingPanel> {
	}

	public ClickHandler submitHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			closing.setSubmitted(true);
			saveClosing(closing, null);

		}
	};

	public ClickHandler deleteHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			closing.setDeleted(true);
			saveClosing(closing, null);

		}
	};

	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");

			/*
			 * 
			 * 
			 * close_total decimal(15,2) NOT NULL, closing_date datetime NOT NULL,
			 * date_created datetime NOT NULL,
			 * 
			 * difference decimal(15,2) NOT NULL, gross_sales decimal(15,2) NOT NULL,
			 * 
			 * income_cash_store decimal(15,2) NOT NULL, income_total decimal(15,2) NOT
			 * NULL, last_update datetime default NULL, location_id bigint(20) NOT NULL,
			 * 
			 * 
			 * open_total decimal(15,2) NOT NULL, sub_total_sales decimal(15,2) NOT NULL,
			 * totala decimal(15,2) NOT NULL, totalb decimal(15,2) NOT NULL, total_tips
			 * decimal(15,2) NOT NULL,
			 */

			// JsDate closingDate = JsDate.create((double) closingDateBox.getValue());

			closing.setClosingDate(closingDateBox.getValue());

			closing.setLocationName(locationListBox.getSelectedValue());

			// Closing closing = (Closing) JavaScriptObject.createObject().cast();
			closing.setIncome1(ClosingsApp.getDoubleValue(incomePanel.income1TextBox));
			closing.setIncome2(ClosingsApp.getDoubleValue(incomePanel.income2TextBox));
			closing.setIncome3(ClosingsApp.getDoubleValue(incomePanel.income3TextBox));
			closing.setIncome4(ClosingsApp.getDoubleValue(incomePanel.income4TextBox));
			closing.setIncome5(ClosingsApp.getDoubleValue(incomePanel.income5TextBox));
			closing.setIncome6(ClosingsApp.getDoubleValue(incomePanel.income6TextBox));
			closing.setIncome7(ClosingsApp.getDoubleValue(incomePanel.income7TextBox));
			closing.setIncome8(ClosingsApp.getDoubleValue(incomePanel.income8TextBox));
			closing.setIncome9(ClosingsApp.getDoubleValue(incomePanel.income9TextBox));

			closing.setSales1(ClosingsApp.getDoubleValue(salesPanel.income1TextBox));
			closing.setSales2(ClosingsApp.getDoubleValue(salesPanel.income2TextBox));
			closing.setSales3(ClosingsApp.getDoubleValue(salesPanel.income3TextBox));
			closing.setSales4(ClosingsApp.getDoubleValue(salesPanel.income4TextBox));
			closing.setSales5(ClosingsApp.getDoubleValue(salesPanel.income5TextBox));
			closing.setSales6(ClosingsApp.getDoubleValue(salesPanel.income6TextBox));
			closing.setSales7(ClosingsApp.getDoubleValue(salesPanel.income7TextBox));
			closing.setSales8(ClosingsApp.getDoubleValue(salesPanel.income8TextBox));
			closing.setSales9(ClosingsApp.getDoubleValue(salesPanel.income9TextBox));

			/*
			 * close_1_cent decimal(15,2) NOT NULL, close_5_cents decimal(15,2) NOT NULL,
			 * close_10_cents decimal(15,2) NOT NULL, close_25_cents decimal(15,2) NOT NULL,
			 * close_1_dollar decimal(15,2) NOT NULL, close_5_dollars decimal(15,2) NOT
			 * NULL, close_20_dollars decimal(15,2) NOT NULL, close_50_dollars decimal(15,2)
			 * NOT NULL, close_100_dollars decimal(15,2) NOT NULL,
			 * 
			 * open_100_dollars decimal(15,2) NOT NULL, open_10_cents decimal(15,2) NOT
			 * NULL, open_10_dollars decimal(15,2) NOT NULL, open_1_cent decimal(15,2) NOT
			 * NULL, open_1_dollar decimal(15,2) NOT NULL, open_20_dollars decimal(15,2) NOT
			 * NULL, open_25_cents decimal(15,2) NOT NULL, open_50_dollars decimal(15,2) NOT
			 * NULL, open_5_cents decimal(15,2) NOT NULL, open_5_dollars decimal(15,2) NOT
			 * NULL,
			 * 
			 * 
			 */
			// openCashPanel

			closing.setOpen1Cent(ClosingsApp.getDoubleValue(openCashPanel.open1Cent));
			closing.setOpen5Cents(ClosingsApp.getDoubleValue(openCashPanel.open5Cents));
			closing.setOpen25Cents(ClosingsApp.getDoubleValue(openCashPanel.open25Cents));
			closing.setOpen1Dollar(ClosingsApp.getDoubleValue(openCashPanel.open1Dollar));
			closing.setOpen5Dollars(ClosingsApp.getDoubleValue(openCashPanel.open5Dollars));
			closing.setOpen10Dollars(ClosingsApp.getDoubleValue(openCashPanel.open10Dollars));
			closing.setOpen20Dollars(ClosingsApp.getDoubleValue(openCashPanel.open20Dollars));
			closing.setOpen50Dollars(ClosingsApp.getDoubleValue(openCashPanel.open50Dollars));
			closing.setOpen100Dollars(ClosingsApp.getDoubleValue(openCashPanel.open100Dollars));

			closing.setClose1Cent(ClosingsApp.getDoubleValue(closeCashPanel.open1Cent));
			closing.setClose5Cents(ClosingsApp.getDoubleValue(closeCashPanel.open5Cents));
			closing.setClose25Cents(ClosingsApp.getDoubleValue(closeCashPanel.open25Cents));
			closing.setClose1Dollar(ClosingsApp.getDoubleValue(closeCashPanel.open1Dollar));
			closing.setClose5Dollars(ClosingsApp.getDoubleValue(closeCashPanel.open5Dollars));
			closing.setClose10Dollars(ClosingsApp.getDoubleValue(closeCashPanel.open10Dollars));
			closing.setClose20Dollars(ClosingsApp.getDoubleValue(closeCashPanel.open20Dollars));
			closing.setClose50Dollars(ClosingsApp.getDoubleValue(closeCashPanel.open50Dollars));
			closing.setClose100Dollars(ClosingsApp.getDoubleValue(closeCashPanel.open100Dollars));

			closing.setOpenerName(openerNameTextBox.getValue());
			closing.setCloserName(closerNameTextBox.getValue());
			closing.setNotes(notesTextArea.getValue());

			saveClosing(closing, null);
		}
	};

	public static void saveClosing(Closing closing, AsyncCallback callback) {
		String base = ClosingsApp.getURL("closing/" + closing.getId());
		
		String url = URL.encode(base);
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		GWT.log(" JsonUtils.stringify1");
		String postData = JsonUtils.stringify(closing);
		GWT.log(" JsonUtils.stringify2");
		// closingsApp.displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {

					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						// closingsApp.displayMessage(response.getText());
						callback.onSuccess(response);

					} else {
						GWT.log("bad result " + response.getStatusCode());
						callback.onFailure(new Exception( response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}
	}

	public void setClosing(Closing closing) {
		this.closing = closing;

	}

}
