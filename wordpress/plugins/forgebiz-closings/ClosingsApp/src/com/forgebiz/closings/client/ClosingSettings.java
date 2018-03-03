package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

public class ClosingSettings
  extends JavaScriptObject
{
  public static ClosingSettings create(String json)
  {
    GWT.log(json);
    return (ClosingSettings)JsonUtils.safeEval(json);
  }
  
  public final native void setShowSales1(boolean show_sales_1) /*-{
  this.show_sales_1 = show_sales_1;
  }-*/;
  
  public final native void setShowSales2(boolean show_sales_2) /*-{
  this.show_sales_2 = show_sales_2;
  }-*/;
  
  public final native void setShowSales3(boolean show_sales_3) /*-{
  this.show_sales_3 = show_sales_3;
  }-*/;
  
  public final native void setShowSales4(boolean show_sales_4) /*-{
  this.show_sales_4 = show_sales_4;
  }-*/;
  
  public final native void setShowSales5(boolean show_sales_5) /*-{
  this.show_sales_5 = show_sales_5;
  }-*/;
  
  public final native void setShowSales6(boolean show_sales_6) /*-{
  this.show_sales_6 = show_sales_6;
  }-*/;
  
  public final native void setShowSales7(boolean show_sales_7) /*-{
  this.show_sales_7 = show_sales_7;
  }-*/;
  
  public final native void setShowSales8(boolean show_sales_8) /*-{
  this.show_sales_8 = show_sales_8;
  }-*/;
  
  public final native void setShowSales9(boolean show_sales_9) /*-{
  this.show_sales_9 = show_sales_9;
  }-*/;
  
  public final native void setShowIncome1(boolean show_income_1) /*-{
		  this.show_income_1 = show_income_1;
		  }-*/;
  
  public final native void setShowIncome2(boolean show_income_2) /*-{
  this.show_income_2 = show_income_2;
  }-*/;
  
  public final native void setShowIncome3(boolean show_income_3) /*-{
  this.show_income_3 = show_income_3;
  }-*/;
  
  public final native void setShowIncome4(boolean show_income_4) /*-{
  this.show_income_4 = show_income_4;
  }-*/;
  
  public final native void setShowIncome5(boolean show_income_5) /*-{
  this.show_income_5 = show_income_5;
  }-*/;
  
  public final native void setShowIncome6(boolean show_income_6) /*-{
  this.show_income_6 = show_income_6;
  }-*/;
  
  public final native void setShowIncome7(boolean show_income_7) /*-{
  this.show_income_7 = show_income_7;
  }-*/;
  
  public final native void setShowIncome8(boolean show_income_8) /*-{
  this.show_income_8 = show_income_8;
  }-*/;
  
  public final native void setShowIncome9(boolean show_income_9) /*-{
  this.show_income_9 = show_income_9;
  }-*/;
  
  public final native void setIncomeLabel1(String income_label_1) /*-{
  this.income_label_1 = income_label_1;
  }-*/;
  
  public final native void setIncomeLabel2(String income_label_2) /*-{
  this.income_label_2 = income_label_2;
  }-*/;
  
  
  public final native void setIncomeLabel3(String income_label_3) /*-{
  this.income_label_3 = income_label_3;
  }-*/;
  
  public final native void setIncomeLabel4(String income_label_4) /*-{
  this.income_label_4 = income_label_4;
  }-*/;
  
  
  public final native void setIncomeLabel5(String income_label_5) /*-{
  this.income_label_5 = income_label_5;
  }-*/;
  
  public final native void setIncomeLabel6(String income_label_6) /*-{
  this.income_label_6 = income_label_6;
  }-*/;
  
  public final native void setIncomeLabel7(String income_label_7) /*-{
  this.income_label_7 = income_label_7;
  }-*/;
  
  public final native void setIncomeLabel8(String income_label_8) /*-{
  this.income_label_8 = income_label_8;
  }-*/;
  
  public final native void setIncomeLabel9(String income_label_9) /*-{
  this.income_label_9 = income_label_9;
  }-*/;
  
  public final native void setSalesLabel1(String sales_label_1) /*-{
  this.sales_label_1 = sales_label_1;
  }-*/;
  
  public final native void setSalesLabel2(String sales_label_2) /*-{
  this.sales_label_2 = sales_label_2;
  }-*/;
  
  public final native void setSalesLabel3(String sales_label_3) /*-{
  this.sales_label_3 = sales_label_3;
  }-*/;
  
  public final native void setSalesLabel4(String sales_label_4) /*-{
  this.sales_label_4 = sales_label_4;
  }-*/;
  
  public final native void setSalesLabel5(String sales_label_5) /*-{
  this.sales_label_5 = sales_label_5;
  }-*/;
  
  public final native void setSalesLabel6(String sales_label_6) /*-{
  this.sales_label_6 = sales_label_6;
  }-*/;
  
  public final native void setSalesLabel7(String sales_label_7) /*-{
  this.sales_label_7 = sales_label_7;
  }-*/;
  
  public final native void setSalesLabel8(String sales_label_8) /*-{
  this.sales_label_8 = sales_label_8;
  }-*/;
  
  public final native void setSalesLabel9(String sales_label_9) /*-{
  this.sales_label_9 = sales_label_9;
  }-*/;
  
  public final native int getId()/*-{
	return this.id;
	}-*/;

  
  public final native boolean getShowIncome1()/*-{
	return this.show_income_1;
	}-*/;

  
  public final native boolean getShowIncome2()/*-{
	return this.show_income_2;
	}-*/;

  
  public final native boolean getShowIncome3()/*-{
	return this.show_income_3;
	}-*/;

  
  public final native boolean getShowIncome4()/*-{
	return this.show_income_4;
	}-*/;

  
  public final native boolean getShowIncome5()/*-{
	return this.show_income_5;
	}-*/;

  
  public final native boolean getShowIncome6()/*-{
	return this.show_income_6;
	}-*/;

  
  public final native boolean getShowIncome7()/*-{
	return this.show_income_7;
	}-*/;

  
  public final native boolean getShowIncome8()/*-{
	return this.show_income_8;
	}-*/;
  
  public final native boolean getShowIncome9()/*-{
	return this.show_income_9;
	}-*/;

  
  public final native boolean getShowSales1();/*-{
	return this.show_sales_1;
	}-*/;

	
  public final native boolean getShowSales2()/*-{
	return this.show_sales_2;
	}-*/;
 
  public final native boolean getShowSales3()/*-{
	return this.show_sales_3;
	}-*/;

  
  public final native boolean getShowSales4()/*-{
	return this.show_sales_4;
	}-*/;

  
  public final native boolean getShowSales5()/*-{
	return this.show_sales_5;
	}-*/;
  
  public final native boolean getShowSales6()/*-{
	return this.show_sales_6;
	}-*/;
  
  public final native boolean getShowSales7()/*-{
	return this.show_sales_7;
	}-*/;

  
  public final native boolean getShowSales8()
  /*-{
	return this.show_sales_8;
	}-*/;

  public final native boolean getShowSales9()/*-{
	return this.show_sales_9;
	}-*/;
  
  public final native String getSalesLabel1()/*-{
	return this.sales_label_1;
	}-*/;

  
  public final native String getSalesLabel2()/*-{
	return this.sales_label_2;
	}-*/;

  public final native String getSalesLabel3()/*-{
	return this.sales_label_3;
	}-*/;
  
  public final native String getSalesLabel4()/*-{
	return this.sales_label_4;
	}-*/;
  
  public final native String getSalesLabel5()/*-{
	return this.sales_label_5;
	}-*/;
  
  public final native String getSalesLabel6()/*-{
	return this.sales_label_6;
	}-*/;
  
  public final native String getSalesLabel7()/*-{
	return this.sales_label_7;
	}-*/;
  
  public final native String getSalesLabel8()/*-{
	return this.sales_label_8;
	}-*/;
  
  public final native String getSalesLabel9()/*-{
	return this.sales_label_9;
	}-*/;
  
  public final native String getIncomeLabel1()/*-{
	return this.income_label_1;
	}-*/;
  
  public final native String getIncomeLabel2()/*-{
	return this.income_label_2;
	}-*/;
  
  public final native String getIncomeLabel3()/*-{
	return this.income_label_3;
	}-*/;
  
  public final native String getIncomeLabel4()/*-{
	return this.income_label_4;
	}-*/;
  
  public final native String getIncomeLabel5()/*-{
	return this.income_label_5;
	}-*/;
  
  public final native String getIncomeLabel6()/*-{
	return this.income_label_6;
	}-*/;
  
  public final native String getIncomeLabel7()/*-{
	return this.income_label_7;
	}-*/;
  
  public final native String getIncomeLabel8()/*-{
	return this.income_label_8;
	}-*/;
  
  public final native String getIncomeLabel9()/*-{
	return this.income_label_9;
	}-*/;

}
