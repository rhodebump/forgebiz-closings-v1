package com.forgebiz.closings.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LocationIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	@UiTemplate("LocationIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, LocationIndexPanel> {
	}

	@UiField
	CellTable<Location> resultsTable;
	@UiField
	Button createLocationButton;

	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");

			JsArray<Location> records = (JsArray<Location>) response;
			List<Location> locations = new ArrayList<Location>();

			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);
				locations.add(location);

			}
			resultsTable.setRowCount(records.length(), true);
			resultsTable.setRowData(0, locations);

		}
	};

	public ClickHandler createNewLocationHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			LocationPanel locationPanel = new LocationPanel();
			ClosingsApp.getInstance().swapMain(locationPanel);
		}
	};


	public  LocationIndexPanel() {
		initWidget((Widget) binder.createAndBindUi(this));

		createLocationButton.addClickHandler(this.createNewLocationHandler);
		ClosingsApp.fetchLocations(gotLocationsCallback);

		// opener
		// closer
		// close date
		// gross sales
		// trips
		// sales tax
		// actions, edit

		// JsArray<Closing> records =(JsArray<Closing>) response;
		//CellTable<Location> table = new CellTable<Location>();

		/*
		 * SelectionModel<Closing> selectionModel = new SingleSelectionModel<Closing>(
		 * KEY_PROVIDER); table.setSelectionModel(selectionModel,
		 * DefaultSelectionEventManager.<Closing> createCheckboxManager());
		 * 
		 * 
		 * 
		 * 
		 * Column<Closing, Boolean> checkColumn = new Column<Closing, Boolean>( new
		 * CheckboxCell(true, false)) {
		 * 
		 * @Override public Boolean getValue(Closing object) { // Get the value from the
		 * selection model. return selectionModel.isSelected(object); } };
		 * table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		 * 
		 */

		// Create name column.
		TextColumn<Location> openerColumn = new TextColumn<Location>() {
			@Override
			public String getValue(Location closing) {
				return closing.getLocationName();
			}
		};
		resultsTable.addColumn(openerColumn, "Name");

		TextColumn<Location> closerColumn = new TextColumn<Location>() {
			@Override
			public String getValue(Location closing) {
				return closing.getNotificationEmailAddresses();
			}
		};
		resultsTable.addColumn(closerColumn, "Notifications");

		Column<Location, String> bc = addColumn(new ButtonCell(), "Edit", new GetValue<String>() {
			@Override
			public String getValue(Location contact) {
				return "Edit";
			}
		}, new FieldUpdater<Location, String>() {
			@Override
			public void update(int index, Location location, String value) {
				// Window.alert("You clicked " + object.getId());
				// closingsApp.fetchClosingSettings(newClosingCallback);
				LocationPanel locationPanel = new LocationPanel();
				locationPanel.setLocation(location);
				ClosingsApp.getInstance().swapMain(locationPanel);;

			}
		});

		resultsTable.addColumn(bc, "Actions");

		// Set the total row count. You might send an RPC request to determine the
		// total row count.

	}

	private static interface GetValue<C> {
		C getValue(Location contact);
	}

	private <C> Column<Location, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<Location, C> fieldUpdater) {
		Column<Location, C> column = new Column<Location, C>(cell) {
			@Override
			public C getValue(Location object) {
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
}
