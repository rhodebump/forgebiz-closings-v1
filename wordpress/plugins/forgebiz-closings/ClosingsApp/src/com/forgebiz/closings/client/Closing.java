package com.forgebiz.closings.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Closing
extends JavaScriptObject
{
    protected Closing() {
    }
    
    /*
     * 		sales_1 decimal(15,2) NOT NULL,
		sales_2 decimal(15,2) NOT NULL,
		sales_3 decimal(15,2) NOT NULL,
		sales_4 decimal(15,2) NOT NULL,
		sales_5 decimal(15,2) NOT NULL,
		sales_6 decimal(15,2) NOT NULL,
		sales_7 decimal(15,2) NOT NULL,
		sales_8 decimal(15,2) NOT NULL,
		sales_9 decimal(15,2) NOT NULL,	
		
		
     */
    
    public final native double getIncome1()/*-{
  	return this.income_1;
  	}-*/;
    
    public final native double getLabel2()/*-{
  	return this.income_2;
  	}-*/;
    
    public final native double getLabel3()/*-{
  	return this.income_3;
  	}-*/;
    
    public final native double getLabel4()/*-{
  	return this.income__4;
  	}-*/;
    
    public final native double getLabel5()/*-{
  	return this.income_5;
  	}-*/;
    
    public final native double getLabel6()/*-{
  	return this.income_6;
  	}-*/;
    
    public final native double getLabel7()/*-{
  	return this.income_7;
  	}-*/;
    
    public final native double getLabel8()/*-{
  	return this.income_8;
  	}-*/;
    
    public final native double getLabel9()/*-{
  	return this.income_9;
  	}-*/;
    
    
}
