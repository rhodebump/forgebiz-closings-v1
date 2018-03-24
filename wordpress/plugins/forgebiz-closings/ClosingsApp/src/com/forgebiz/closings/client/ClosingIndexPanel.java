package com.forgebiz.closings.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingIndexPanel extends FlowPanel {

	public ClickHandler createNewClosingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("createNewClosingHandler click");
			Closing closing = (Closing) JavaScriptObject.createObject().cast();
			closing.setSubmitted(false);
			closing.setDeleted(false);
			ClosingPanel closingPanel = new ClosingPanel();
			closingPanel.setClosing(closing);
			ClosingsApp.getInstance().swapMain(closingPanel);
			
		}
	};

	CellTable<Closing> table = new CellTable();

	DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");

	SimpleCheckBox showDeletedCheckbox= new SimpleCheckBox();

	DateBox startDatePicker = new DateBox();

	DateBox endDatePicker = new DateBox();

	ListBox locationListBox = new ListBox();;

	Button searchButton = new Button("Search");

	Button createButton = new Button("Create Closing");

	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			JsArray<Location> records = (JsArray<Location>) response;
			locationListBox.addItem("All Locations", "");
			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);
				locationListBox.addItem(location.getLocationName(), new Integer(location.getId()).toString());
			}

		}
	};



	private void displayClosings(Response response) {
		JsArray<Closing> records = JsonUtils.<JsArray<Closing>>safeEval(response.getText());
		List<Closing> closings = new ArrayList<Closing>();

		for (int i = 0; i < records.length(); ++i) {
			closings.add(records.get(i));
		}

		// what about totals?
		// should we add up a subtotal for each type and add it as a closing?

		// Set the total row count. You might send an RPC request to determine the
		// total row count.
		table.setRowCount(records.length(), true);
		table.setRowData(0, closings);

	}

	private static interface GetValue<C> {
		C getValue(Closing contact);
	}

	private <C> Column<Closing, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<Closing, C> fieldUpdater) {
		Column<Closing, C> column = new Column<Closing, C>(cell) {
			@Override
			public C getValue(Closing object) {
				return getter.getValue(object);
			}
		};
		column.setFieldUpdater(fieldUpdater);
		if (cell instanceof AbstractEditableCell<?, ?>) {
			// editableCells.add((AbstractEditableCell<?, ?>) cell);
		}
		// contactList.addColumn(column, headerText);
		return column;
	}

	private String getDate(DateBox dateBox) {
		if (dateBox.getValue() == null) {

			return "";
		} else {

			return dtf.format(dateBox.getValue());
		}

	}
	
	private int getBoolean(SimpleCheckBox sc) {
		if (showDeletedCheckbox.isChecked()) {
			return 1;
		}else {
			return 0;
		}
	}

	private void searchClosings() {
		GWT.log("searchHandler.onClick");
		try {
			String base = ClosingsApp.getURL("/closing/search");
			String url = URL.encode(base);

			url = url + "?location_id=" + locationListBox.getSelectedValue();
			url = url + "&start_date=" + getDate(startDatePicker);
			url = url + "&end_date=" + getDate(endDatePicker);
			url = url + "&deleted=" + getBoolean(showDeletedCheckbox);

			GWT.log("url = " + url);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
			ClosingsApp.setNonce(builder);

			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					ClosingsApp.getInstance().displayError("Closing search failure: " + exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());

						displayClosings(response);
					} else {
						GWT.log("bad result " + response.getStatusCode());
						ClosingsApp.getInstance().displayError("Closing search failure: " + response.getText());
					}
				}
			});
		} catch (Exception e) {
			ClosingsApp.getInstance().displayError("Closing search failure : " + e.getMessage());
		}

	}

	private ClickHandler searchHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			searchClosings();
		}
	};


	public ClosingIndexPanel() {


		Label startDateLabel = new Label("Start Date");
		add(NumberPanelHelper.getBootStrapPanel(startDateLabel, startDatePicker));
		Label endDateLabel = new Label("End Date");
		add(NumberPanelHelper.getBootStrapPanel(endDateLabel, endDatePicker));
		Label locationLabel = new Label("Location");
		add(NumberPanelHelper.getBootStrapPanel(locationLabel, this.locationListBox));
		Label deletedLabel = new Label("Show Deleted");
		add(NumberPanelHelper.getBootStrapPanel(deletedLabel, this.showDeletedCheckbox));

		// Create name column.
		TextColumn<Closing> openerColumn = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return closing.getOpenerName();
			}
		};
		table.addColumn(openerColumn, "Opener");

		TextColumn<Closing> closerColumn = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return closing.getCloserName();
			}
		};
		table.addColumn(closerColumn, "Closer");

		TextColumn<Closing> closeDateColumn = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {

				// https://stackoverflow.com/questions/3075577/convert-mysql-datetime-stamp-into-javascripts-date-format
				// do we really need to convert this to a date?
				return ClosingPanel.getDisplayDate(closing);
			}
		};
		table.addColumn(closeDateColumn, "Close Date");

		for (final ColumnType columnType : ColumnType.values()) {
			TextColumn<Closing> salesColumn = new TextColumn<Closing>() {
				@Override
				public String getValue(Closing closing) {
					return columnType.getValue(closing).toString();
				}
			};
			Header<String> salesFooter = new ClosingHeader(table, columnType);
			table.addColumn(salesColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant(columnType.getName())), salesFooter);

		}


		Column<Closing, String> bc = addColumn(new ButtonCell(), "Edit", new GetValue<String>() {
			@Override
			public String getValue(Closing contact) {
				return "View";
			}
		}, new FieldUpdater<Closing, String>() {
			@Override
			public void update(int index, Closing closing, String value) {
				ClosingPanel closingPanel = new ClosingPanel();
				closingPanel.setClosing(closing);
				ClosingsApp.getInstance().swapMain(closingPanel);
			}
		});

		table.addColumn(bc, "Actions");

		searchButton.addClickHandler(searchHandler);
		searchButton.setStyleName("btn btn-primary");

		ClosingsApp.fetchLocations(gotLocationsCallback);

		this.startDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		this.endDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));

		createButton.addClickHandler(this.createNewClosingHandler);
		createButton.setStyleName("btn btn-primary");

		startDatePicker.setValue(new Date());

		add(searchButton);
		add(createButton);

		add(table);
		searchClosings();

	}


}
