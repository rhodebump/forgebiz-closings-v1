package com.forgebiz.closings.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
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
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
//import com.google.gwt.view.client.DefaultSelectionEventManager;
//import com.google.gwt.view.client.MultiSelectionModel;
//import com.google.gwt.view.client.ProvidesKey;
//import com.google.gwt.view.client.SelectionModel;
//import com.google.gwt.view.client.SingleSelectionModel;

public class ClosingIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	AsyncCallback newClosingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("newClosingCallback.onSuccess");
			ClosingSettings closingSettings = (ClosingSettings) response;
			CashPanel openCashPanel = new CashPanel();
			CashPanel closeCashPanel = new CashPanel();
			SalesPanel salesPanel = new SalesPanel(closingSettings);
			IncomePanel incomePanel = new IncomePanel(closingSettings);
			Button saveButton = new Button();

			DateBox closingDateBox = new DateBox();
			ListBox locationListBox = new ListBox();
			ClosingPanel closingPanel = new ClosingPanel(closingsApp, closingSettings, openCashPanel, closeCashPanel,
					salesPanel, incomePanel, saveButton, closingDateBox, locationListBox);
			openCashPanel.setClosingPanel(closingPanel);
			closeCashPanel.setClosingPanel(closingPanel);
			RootPanel.get("closingsMain").clear();
			RootPanel.get("closingsMain").add(closingPanel);

		}
	};

	@UiField
	ClosingsApp closingsApp;

	@UiField
	SimplePanel resultsPanel;

	@UiField
	DateBox startDatePicker = new DateBox();

	@UiField
	DateBox endDatePicker = new DateBox();

	@UiField(provided = true)
	ListBox locationListBox;

	@UiField
	Button searchButton = new Button("Search");
	public ClickHandler createNewClosingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			// new closing panel
			// how to swap out the panel?
			closingsApp.fetchClosingSettings(newClosingCallback);

		}
	};

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

		//opener
		//closer
		//close date
		//gross sales
		//trips
		//sales tax
		//actions, edit
		
		// JsArray<Closing> records =(JsArray<Closing>) response;
		CellTable<Closing> table = new CellTable<Closing>();
		
		/*
		 SelectionModel<Closing> selectionModel = new SingleSelectionModel<Closing>(
		        KEY_PROVIDER);
		 table.setSelectionModel(selectionModel,
		        DefaultSelectionEventManager.<Closing> createCheckboxManager());
		    
		    


		 Column<Closing, Boolean> checkColumn = new Column<Closing, Boolean>(
			        new CheckboxCell(true, false)) {
			      @Override
			      public Boolean getValue(Closing object) {
			        // Get the value from the selection model.
			        return selectionModel.isSelected(object);
			      }
			    };
			    table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
			    
			    */
		
		
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
		
		
		TextColumn<Closing> sales2Column = new TextColumn<Closing>() {
			@Override
			public String getValue(Closing closing) {
				return new Double(closing.getSales2()).toString();
			}
		};
		table.addColumn(sales2Column, "Sales 2");
		TextColumn<Closing> income1Column  = new TextColumn<Closing>() {
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
		//http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCellSampler
		
		/*
	    Column<Closing, ButtonCell> column = new Column<Closing, ButtonCell>(cell) {
	        @Override
	        public C getValue(Closing object) {
	          return getter.getValue(object);
	        }
	      };
	      column.setFieldUpdater(fieldUpdater);
	      if (cell instanceof AbstractEditableCell<?, ?>) {
	        editableCells.add((AbstractEditableCell<?, ?>) cell);
	      }
	      table.addColumn(column, "Actiosn");
	    }
		
*/
		
		Column<Closing, String> bc= addColumn(new ButtonCell(), "Edit", new GetValue<String>() {
	        @Override
	        public String getValue(Closing contact) {
	          return "Click " + contact.getId();
	        }
	      }, new FieldUpdater<Closing, String>() {
	        @Override
	        public void update(int index, Closing object, String value) {
	         // Window.alert("You clicked " + object.getId());
				closingsApp.fetchClosingSettings(newClosingCallback);
				
	        }
	      });

	    
	      table.addColumn(bc, "Actions");

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

	private ClickHandler searchHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("searchHandler.onClick");

			String url = URL.encode("http://localhost:8080/wp-json/forgebiz-closings/v1/closing/search");
			url = url + "?location_id=" + locationListBox.getSelectedValue();
			url = url + "&start_date=" + startDatePicker.getValue().toString();
			url = url + "&end_date=" + endDatePicker.getValue().toString();

			GWT.log("url = " + url);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
			ClosingsApp.setNonce(builder);
			try {
				builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						closingsApp.displayError("Couldn't retrieve JSON : " + exception.getMessage());
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							GWT.log("good result " + response.getStatusText());

							displayClosings(response);
							closingsApp.displayError(response.getText());
						} else {
							GWT.log("bad result " + response.getStatusCode());
							closingsApp.displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
						}
					}
				});
			} catch (RequestException e) {
				closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
			}
		}
	};

	public ClosingIndexPanel(ClosingsApp closingsApp) {
		initWidget((Widget) binder.createAndBindUi(this));
		this.startDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
		this.endDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));

		this.searchButton.addClickHandler(this.searchHandler);

		Button createClosingButton = new Button("New Closing");

		createClosingButton.addClickHandler(this.createNewClosingHandler);

		closingsApp.fetchLocations(gotLocationsCallback);

	}

	@UiTemplate("ClosingIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingIndexPanel> {
	}
}
