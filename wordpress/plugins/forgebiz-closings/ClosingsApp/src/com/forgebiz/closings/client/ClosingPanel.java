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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.JavaScriptObject;

public class ClosingPanel
  extends Composite
{
  private static final MyBinder binder = (MyBinder)GWT.create(MyBinder.class);
  

	 @UiField(provided=true)
		Button saveButton;
	  
  private ClosingSettings closingSettings = null;
  @UiField(provided=true)
  CashPanel openCashPanel;
  @UiField(provided=true)
  CashPanel closeCashPanel;
  @UiField(provided=true)
  SalesPanel salesPanel;
  @UiField(provided=true)
  IncomePanel incomePanel;
  
  ClosingsApp closingsApp = null;
  
  public ClosingPanel(ClosingsApp closingsApp,ClosingSettings closingSettings, CashPanel openCashPanel, CashPanel closeCashPanel, SalesPanel salesPanel, IncomePanel incomePanel, Button saveButton)
  {
	  this.closingsApp = closingsApp;
    this.closingSettings = closingSettings;
    this.openCashPanel = openCashPanel;
    this.closeCashPanel = closeCashPanel;
    this.salesPanel = salesPanel;
    this.incomePanel = incomePanel;
    this.saveButton = saveButton;
	this.saveButton.addClickHandler(this.saveHandler);
    initWidget((Widget)binder.createAndBindUi(this));
    GWT.log("closingpanel");
    
    
  }
  
  public void calculateAll()
  {
    GWT.log("calculateAll");
    this.openCashPanel.calculateAll();
    double openCashTotal = this.openCashPanel.getCashTotal();
    GWT.log("openCashTotal " + openCashTotal);
    this.closeCashPanel.calculateAll();
    double closeCashTotal = this.closeCashPanel.getCashTotal();
    GWT.log("closeCashTotal " + closeCashTotal);
    double totalCash = closeCashTotal - openCashTotal;
    GWT.log("totalCash " + totalCash);
    
    this.incomePanel.setCashTotalCash(totalCash);
  }
  
  @UiTemplate("ClosingPanel.ui.xml")
  static abstract interface MyBinder
    extends UiBinder<Widget, ClosingPanel>
  {}
  
  
  
	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");
			Closing closing= (Closing)JavaScriptObject.createObject().cast();
closing.setIncome1(incomePanel.xx);
closing.setIncome2(incomePanel.xx);
closing.setIncome3(incomePanel.xx);
closing.setIncome4(incomePanel.xx);
closing.setIncome5(incomePanel.xx);
closing.setIncome6(incomePanel.xx);
closing.setIncome7(incomePanel.xx);
closing.setIncome8(incomePanel.xx);
closing.setIncome19(incomePanel.xx);

closing.setSales1(incomePanel.xx);
closing.setSales2(incomePanel.xx);
closing.setSales3(incomePanel.xx);
closing.setSales4(incomePanel.xx);
closing.setSales5(incomePanel.xx);
closing.setSales6(incomePanel.xx);
closing.setSales7(incomePanel.xx);
closing.setSales8(incomePanel.xx);
closing.setSales9(incomePanel.xx);


			saveClosing(closing);
		}
	};
	
	private void saveClosing(Closing closing) {
		String url = URL
				.encode("http://localhost:8080//wp-json/forgebiz-closings/v1/closing/" + closingSettings.getId());
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		GWT.log(" JsonUtils.stringify1");
		String postData = JsonUtils.stringify(closing);
		GWT.log(" JsonUtils.stringify2");
		closingsApp.displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					closingsApp.displayError(
							"Couldn't retrieve JSON : http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						closingsApp.displayMessage(response.getText());
					} else {
						GWT.log("bad result " + response.getStatusCode());
						closingsApp.displayMessage(
								"Couldn't retrieve JSON (http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
										+ response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
		}
	}
	
}
