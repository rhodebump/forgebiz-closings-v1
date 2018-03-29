package com.forgebiz.closings.client;

//import com.google.gwt.i18n.client.NumberFormat;

//import java.text.DecimalFormat;

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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingPanel extends Composite {

	private Closing closing = null;

	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);
	@UiField
	Button cancelButton;

	@UiField
	Button saveButton;

	@UiField
	Button submitButton;

	@UiField
	Button saveButton2;

	@UiField
	Button submitButton2;

	@UiField
	TextBox differenceTextBox;

	@UiField
	Button deleteButton;

	@UiField
	TextBox openerNameTextBox;
	@UiField
	TextBox closerNameTextBox;

	@UiField
	TextArea notesTextArea;

	@UiField
	CashPanel openCashPanel;
	@UiField
	CashPanel closeCashPanel;
	@UiField
	SalesPanel salesPanel;
	@UiField
	IncomePanel incomePanel;

	@UiField
	TextBox totalIncomeTextBox;

	@UiField
	TextBox totalSalesTextBox;

	@UiField
	DateBox closingDateBox;

	ClosingsApp closingsApp = null;

	@UiField
	ListBox locationListBox = new ListBox();

	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			ClosingsApp.getInstance().displayError("Location fetch failure:" + throwable.getMessage());
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

	AsyncCallback deleteClosingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			ClosingsApp.getInstance().swapMain(closingsIndexPanel);
			ClosingsApp.getInstance().clearMessages();
			ClosingsApp.getInstance().displayError("Closing delete failure:" + throwable.getMessage());

		}

		public void onSuccess(Object response) {
			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			ClosingsApp.getInstance().swapMain(closingsIndexPanel);
			ClosingsApp.getInstance().clearMessages();
			ClosingsApp.getInstance().displayMessage("Closing deleted.");

		}
	};

	AsyncCallback saveClosingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			ClosingsApp.getInstance().clearMessages();
			ClosingsApp.getInstance().displayError("Closing save failure:" + throwable.getMessage());
		}

		public void onSuccess(Object response) {
			ClosingsApp.getInstance().clearMessages();
			ClosingsApp.getInstance().displayMessage("Closing saved");

		}
	};

	AsyncCallback gotClosingSettingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			ClosingSettings closingSettings = (ClosingSettings) response;

			incomePanel.setClosingSettings(closingSettings);
			incomePanel.setClosingPanel(ClosingPanel.this);
			salesPanel.setClosingSettings(closingSettings);
			salesPanel.setClosingPanel(ClosingPanel.this);

		}
	};

	public ClosingPanel() {
		initWidget((Widget) binder.createAndBindUi(this));

		this.saveButton.addClickHandler(saveHandler);
		this.submitButton.addClickHandler(submitHandler);
		this.saveButton2.addClickHandler(saveHandler);
		this.submitButton2.addClickHandler(submitHandler);
		this.cancelButton.addClickHandler(cancelHandler);

		this.closingDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		this.deleteButton.addClickHandler(deleteHandler);

		this.openCashPanel.setClosingPanel(this);
		this.closeCashPanel.setClosingPanel(this);

		GWT.log("closingpanel");
		ClosingsApp.fetchLocations(gotLocationsCallback);
		ClosingsApp.fetchClosingSettings(gotClosingSettingCallback);
		totalSalesTextBox.setEnabled(false);
		totalIncomeTextBox.setEnabled(false);
		differenceTextBox.setEnabled(false);

	}

	private double difference = 0.0D;

	public void calculateAll() {
		GWT.log("calculateAll");
		this.openCashPanel.calculateAll();
		double openCashTotal = this.openCashPanel.getCashTotal();
		closing.setOpenCashTotal(openCashTotal);
		this.closeCashPanel.calculateAll();
		double closeCashTotal = this.closeCashPanel.getCashTotal();
		closing.setCloseCashTotal(closeCashTotal);
		incomePanel.calculateAll();
		Double income_total = incomePanel.getTotal();
		closing.setIncomeTotal(income_total);

		salesPanel.calculateAll();
		double sales_total = salesPanel.getTotal();
		closing.setSalesTotal(sales_total);
		difference = income_total - sales_total;
		closing.setDifference(difference);
		updateControls();

	}

	private double getSafeDouble(Double d) {
		if (d == null) {
			return 0.0D;
		} else {
			return d;
		}

	}

	private void updateControls() {

		totalSalesTextBox.setValue(getCurrency(closing.getSalesTotal()));
		totalIncomeTextBox.setValue(getCurrency(closing.getIncomeTotal()));
		differenceTextBox.setValue(getCurrency(closing.getDifference()));
		double incomeCashTotal = getSafeDouble(closing.getCloseCashTotal()) - getSafeDouble(closing.getOpenCashTotal());

		incomePanel.cashTotalTextBox.setValue(getCurrency(incomeCashTotal));

	}

	static NumberFormat numberFormat = NumberFormat.getDecimalFormat();

	public static String getCurrency(Double value) {
		if (value == null) {
			return "";
		}
		numberFormat.overrideFractionDigits(2);
		return numberFormat.format(value.doubleValue());
	}

	@UiTemplate("ClosingPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingPanel> {
	}

	private boolean isNullOrEmpty(String val) {

		if (val == null) {
			return true;

		}
		if (val.trim().equals("")) {
			return true;

		}
		return false;
	}

	private static String CLOSING_ALREADY_SUBMITTED = "This closing has already been submitted, you cannot edit or make any changes to it.";
	public ClickHandler submitHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			// is it valid for submit??
			if (isNullOrEmpty(locationListBox.getSelectedValue())) {

				ClosingsApp.getInstance().displayError("Please choose a location");
				return;
			}
			if (closingDateBox.getValue() == null) {

				ClosingsApp.getInstance().displayError("Please set a date");
				return;
			}
			if (closing.getSubmitted()) {
				ClosingsApp.getInstance().displayError(CLOSING_ALREADY_SUBMITTED);
				return;
			}
			closing.setSubmitted(true);
			saveClosing(closing, saveClosingCallback);

		}
	};

	public ClickHandler deleteHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {

			ConfirmDialogBox cdb = new ConfirmDialogBox("Delete closing?", deleteCallback);
			cdb.show();
			cdb.center();

		}
	};

	AsyncCallback deleteCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
			// ClosingsApp.getInstance().displayError("Closing Settings fetch failure: " +
			// throwable.getMessage());
		}

		public void onSuccess(Object response) {
			closing.setDeleted(true);
			saveClosing(closing, deleteClosingCallback);

		}
	};

	public ClickHandler cancelHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {

			ClosingIndexPanel closingsIndexPanel = new ClosingIndexPanel();
			ClosingsApp.getInstance().swapMain(closingsIndexPanel);
		}
	};

	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			if (closing.getSubmitted()) {
				ClosingsApp.getInstance().displayError(CLOSING_ALREADY_SUBMITTED);
				return;
			}
			
			closing.setClosingDate(closingDateBox.getTextBox().getValue());
			closing.setLocationId(locationListBox.getSelectedValue());
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

			closing.setOpen1Cent(ClosingsApp.getDoubleValue(openCashPanel.open1Cent));
			closing.setOpen5Cents(ClosingsApp.getDoubleValue(openCashPanel.open5Cents));
			closing.setOpen10Cents(ClosingsApp.getDoubleValue(openCashPanel.open10Cents));
			closing.setOpen25Cents(ClosingsApp.getDoubleValue(openCashPanel.open25Cents));
			closing.setOpen1Dollar(ClosingsApp.getDoubleValue(openCashPanel.open1Dollar));
			closing.setOpen5Dollars(ClosingsApp.getDoubleValue(openCashPanel.open5Dollars));
			closing.setOpen10Dollars(ClosingsApp.getDoubleValue(openCashPanel.open10Dollars));
			closing.setOpen20Dollars(ClosingsApp.getDoubleValue(openCashPanel.open20Dollars));
			closing.setOpen50Dollars(ClosingsApp.getDoubleValue(openCashPanel.open50Dollars));
			closing.setOpen100Dollars(ClosingsApp.getDoubleValue(openCashPanel.open100Dollars));

			closing.setClose1Cent(ClosingsApp.getDoubleValue(closeCashPanel.open1Cent));
			closing.setClose5Cents(ClosingsApp.getDoubleValue(closeCashPanel.open5Cents));
			closing.setClose10Cents(ClosingsApp.getDoubleValue(closeCashPanel.open10Cents));
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
			closing.setOpenCashTotal(openCashPanel.getCashTotal());
			closing.setCloseCashTotal(closeCashPanel.getCashTotal());
			closing.setDifference(difference);

			closing.setSalesTotal(salesPanel.getTotal());
			closing.setIncomeTotal(incomePanel.getTotal());

			saveClosing(closing, saveClosingCallback);
		}
	};

	public static void saveClosing(Closing closing, final AsyncCallback callback) {
		String base = ClosingsApp.getURL("/closing/save");
		String url = URL.encode(base);
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		String postData = JsonUtils.stringify(closing);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {

					callback.onFailure(exception);
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						callback.onSuccess(response);
					} else {
						callback.onFailure(new Exception("Closing Save error: " + response.getText()));

					}
				}
			});
		} catch (RequestException e) {
			callback.onFailure(e);
		}
	}

	private void setSelectedValue(ListBox listBox, String str) {
		String text = str;
		int indexToFind = -1;
		for (int i = 0; i < listBox.getItemCount(); i++) {
			// Match by Value
			if (listBox.getValue(i).equals(text)) {
				indexToFind = i;
				break;
			}
			// Match by Text
			if (listBox.getItemText(i).equals(text)) {
				indexToFind = i;
				break;
			}
		}
		listBox.setSelectedIndex(indexToFind);
	}

	public void setClosing(Closing closing) {
		this.closing = closing;

		try {
			if (closing.getSubmitted()) {
				ClosingsApp.getInstance().displayMessage(CLOSING_ALREADY_SUBMITTED);
				return;
			}
			
			ClosingsApp.setString(closingDateBox.getTextBox(), getDisplayDate(closing), closing);

			// closingDateBox.getTextBox().setValue(getDisplayDate(closing));
			this.setSelectedValue(locationListBox, closing.getLocationId());

			ClosingsApp.setDouble(incomePanel.income1TextBox, closing.getIncome1(), closing);
			ClosingsApp.setDouble(incomePanel.income2TextBox, closing.getIncome2(), closing);
			ClosingsApp.setDouble(incomePanel.income3TextBox, closing.getIncome3(), closing);
			ClosingsApp.setDouble(incomePanel.income4TextBox, closing.getIncome4(), closing);
			ClosingsApp.setDouble(incomePanel.income5TextBox, closing.getIncome5(), closing);
			ClosingsApp.setDouble(incomePanel.income6TextBox, closing.getIncome6(), closing);
			ClosingsApp.setDouble(incomePanel.income7TextBox, closing.getIncome7(), closing);
			ClosingsApp.setDouble(incomePanel.income8TextBox, closing.getIncome8(), closing);
			ClosingsApp.setDouble(incomePanel.income9TextBox, closing.getIncome9(), closing);

			ClosingsApp.setDouble(salesPanel.income1TextBox, closing.getSales1(), closing);
			ClosingsApp.setDouble(salesPanel.income2TextBox, closing.getSales2(), closing);
			ClosingsApp.setDouble(salesPanel.income3TextBox, closing.getSales3(), closing);
			ClosingsApp.setDouble(salesPanel.income4TextBox, closing.getSales4(), closing);
			ClosingsApp.setDouble(salesPanel.income5TextBox, closing.getSales5(), closing);
			ClosingsApp.setDouble(salesPanel.income6TextBox, closing.getSales6(), closing);
			ClosingsApp.setDouble(salesPanel.income7TextBox, closing.getSales7(), closing);
			ClosingsApp.setDouble(salesPanel.income8TextBox, closing.getSales8(), closing);
			ClosingsApp.setDouble(salesPanel.income9TextBox, closing.getSales9(), closing);

			ClosingsApp.setDouble(openCashPanel.open1Cent, closing.getOpen1Cent(), closing);
			ClosingsApp.setDouble(openCashPanel.open5Cents, closing.getOpen5Cents(), closing);
			ClosingsApp.setDouble(openCashPanel.open10Cents, closing.getOpen10Cents(), closing);
			ClosingsApp.setDouble(openCashPanel.open25Cents, closing.getOpen25Cents(), closing);
			ClosingsApp.setDouble(openCashPanel.open1Dollar, closing.getOpen1Dollar(), closing);
			ClosingsApp.setDouble(openCashPanel.open5Dollars, closing.getOpen5Dollars(), closing);
			ClosingsApp.setDouble(openCashPanel.open10Dollars, closing.getOpen10Dollars(), closing);
			ClosingsApp.setDouble(openCashPanel.open20Dollars, closing.getOpen20Dollars(), closing);
			ClosingsApp.setDouble(openCashPanel.open50Dollars, closing.getOpen50Dollars(), closing);
			ClosingsApp.setDouble(openCashPanel.open100Dollars, closing.getOpen100Dollars(), closing);

			ClosingsApp.setDouble(closeCashPanel.open1Cent, closing.getClose1Cent(), closing);
			ClosingsApp.setDouble(closeCashPanel.open5Cents, closing.getClose5Cents(), closing);
			ClosingsApp.setDouble(closeCashPanel.open10Cents, closing.getClose10Cents(), closing);
			ClosingsApp.setDouble(closeCashPanel.open25Cents, closing.getClose25Cents(), closing);
			ClosingsApp.setDouble(closeCashPanel.open1Dollar, closing.getClose1Dollar(), closing);
			ClosingsApp.setDouble(closeCashPanel.open5Dollars, closing.getClose5Dollars(), closing);
			ClosingsApp.setDouble(closeCashPanel.open10Dollars, closing.getClose10Dollars(), closing);
			ClosingsApp.setDouble(closeCashPanel.open20Dollars, closing.getClose20Dollars(), closing);
			ClosingsApp.setDouble(closeCashPanel.open50Dollars, closing.getClose50Dollars(), closing);
			ClosingsApp.setDouble(closeCashPanel.open100Dollars, closing.getClose100Dollars(), closing);

			ClosingsApp.setString(openerNameTextBox, closing.getOpenerName(), closing);
			ClosingsApp.setString(closerNameTextBox, closing.getCloserName(), closing);
			ClosingsApp.setString(notesTextArea, closing.getNotes(), closing);

			if (closing.getOpenCashTotal() != null) {
				openCashPanel.setCashTotal(closing.getOpenCashTotal());
			}

			if (closing.getCloseCashTotal() != null) {
				closeCashPanel.setCashTotal(closing.getCloseCashTotal());
			}

			ClosingsApp.setDouble(differenceTextBox, closing.getDifference(), closing);

			if (closing.getSalesTotal() != null) {
				salesPanel.setTotal(closing.getSalesTotal());
			}
			if (closing.getIncomeTotal() != null) {
				incomePanel.setTotal(closing.getIncomeTotal());
			}

			updateControls();
		} catch (Exception e) {
			GWT.log("setClosing error", e);

		}

	}

	public static String getDisplayDate(Closing closing) {
		if (closing.getClosingDate() == null) {
			return "";
		}
		if (closing.getClosingDate().startsWith("0000-00-00")) {
			return "";
		} else {

			return closing.getClosingDate().substring(0, "0000-00-00".length());
		}
	}

}
