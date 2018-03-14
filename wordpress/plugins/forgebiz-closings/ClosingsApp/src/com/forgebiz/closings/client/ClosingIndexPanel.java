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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
//import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	public ClickHandler createNewClosingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("createNewClosingHandler click");
			Closing closing = (Closing) JavaScriptObject.createObject().cast();
			closing.setSubmitted(false);
			closing.setDeleted(false);
			ClosingPanel closingPanel = new ClosingPanel();
			closingPanel.setClosing(closing);
			ClosingsApp.getInstance().swapMain(closingPanel);
			// ClosingsApp.fetchClosingSettings(gotSettingsCallback);
		}
	};
	@UiField
	CellTable<Closing> table;
	
	
	@UiField
	SimpleCheckBox showDeletedCheckbox;

	@UiField
	DateBox startDatePicker = new DateBox();

	@UiField
	DateBox endDatePicker = new DateBox();

	@UiField
	ListBox locationListBox;

	@UiField
	Button searchButton = new Button("Search");

	@UiField
	Button createButton = new Button("Create Closing");

	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");

			JsArray<Location> records = (JsArray<Location>) response;
			locationListBox.addItem("All Locations", "");

			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);

				locationListBox.addItem(location.getLocationName(), new Integer(location.getId()).toString());

			}

		}
	};

	/*
	 * static final ProvidesKey<Closing> KEY_PROVIDER = new ProvidesKey<Closing>() {
	 * 
	 * @Override public Object getKey(Closing item) { return item == null ? null :
	 * item.getId(); } };
	 */

	private void displayClosings(Response response) {
		JsArray<Closing> records = JsonUtils.<JsArray<Closing>>safeEval(response.getText());
		List<Closing> closings = new ArrayList<Closing>();

		for (int i = 0; i < records.length(); ++i) {
			closings.add(records.get(i));
		}


		//what about totals?
		//should we add up a subtotal for each type and add it as a closing?
		


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
	// 2016-08-02 15:37 ISO 8601
	// MySQL retrieves and displays DATETIME values in 'YYYY-MM-DD HH:MM:SS'

	private String getDate(DateBox dateBox) {
		if (dateBox.getValue() == null) {

			return "";
		} else {
			DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
			return dtf.format(dateBox.getValue());
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
			url = url + "&deleted=" + showDeletedCheckbox.getValue().toString();

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
						ClosingsApp.getInstance()
								.displayError("Closing search failure: "  + response.getText());
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

	// DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
	public ClosingIndexPanel() {
		initWidget((Widget) binder.createAndBindUi(this));
		

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
				return closing.getCloseDate();
			}
		};
		table.addColumn(closeDateColumn, "Close Date");
		TextColumn<Closing> sales1Column = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return new Double(closing.getSales1()).toString();
			}
		};
		table.addColumn(sales1Column, "Sales 1");
		
		    Header<String> sales1Header = new Header<String>(new TextCell()) {
      @Override
      public String getValue() {
        List<ContactInfo> items = dataGrid.getVisibleItems();
        if (items.size() == 0) {
          return "";
        } else {
          double totalSales1 = 0.0D;
          for (Closing item : items) {
            totalSales1 += item.getSales1();
          }
          return "Sales 1 Total: " + totalSales1;
        }
      }
    };
    dataGrid.addColumn(ageColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant(constants
        .cwDataGridColumnAge())), ageFooter);
    dataGrid.setColumnWidth(ageColumn, 7, Unit.EM);
    
    

		TextColumn<Closing> sales2Column = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return new Double(closing.getSales2()).toString();
			}
		};
		table.addColumn(sales2Column, "Sales 2");
		TextColumn<Closing> income1Column = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return new Double(closing.getIncome1()).toString();
			}
		};
		table.addColumn(income1Column, "Income 1");
		TextColumn<Closing> income2Column = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return new Double(closing.getIncome2()).toString();
			}
		};
		table.addColumn(income2Column, "Income 2");

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
			}
		});

		table.addColumn(bc, "Actions");
		
		
		searchButton.addClickHandler(searchHandler);

		ClosingsApp.getInstance().fetchLocations(gotLocationsCallback);

		this.startDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		this.endDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));

		createButton.addClickHandler(this.createNewClosingHandler);
		startDatePicker.setValue(new Date());
		searchClosings();

	}

	@UiTemplate("ClosingIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingIndexPanel> {
	}
}
