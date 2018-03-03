package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ClosingPanel
  extends Composite
{
  private static final MyBinder binder = (MyBinder)GWT.create(MyBinder.class);
  
  private void displayMessage(String error) {}
  
  private void displayError(String error)
  {
    displayMessage(error);
  }
  
  private ClosingSettings closingSettings = null;
  @UiField(provided=true)
  CashPanel openCashPanel;
  @UiField(provided=true)
  CashPanel closeCashPanel;
  @UiField(provided=true)
  SalesPanel salesPanel;
  @UiField(provided=true)
  IncomePanel incomePanel;
  
  public ClosingPanel(ClosingSettings closingSettings, CashPanel openCashPanel, CashPanel closeCashPanel, SalesPanel salesPanel, IncomePanel incomePanel)
  {
    this.closingSettings = closingSettings;
    this.openCashPanel = openCashPanel;
    this.closeCashPanel = closeCashPanel;
    this.salesPanel = salesPanel;
    this.incomePanel = incomePanel;
    
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
}
