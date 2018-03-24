package com.forgebiz.closings.client;

public enum ColumnType {

	
	/*
	 * 		//total open cash
		//total close cash
		//total sales
		//total income
		//difference
		//notes
		 * 
	 */
	OPEN_CASH_TOTAL("Open Cash Total"),CLOSE_CASH_TOTAL("Close Cash Total"), SALES_TOTAL("Sales Total"),INCOME_TOTAL("Income Total"),
	DIFFERENCE("Difference");
	
	private String name = null;
	
	public String getName() {
		return name;
	}
	
	ColumnType(String name) {
		this.name = name;
	}

	public Double getValue(Closing closing) {


				
		if (this == ColumnType.OPEN_CASH_TOTAL) {
			return closing.getOpenCashTotal();
		} else if (this == ColumnType.CLOSE_CASH_TOTAL) {
			return closing.getCloseCashTotal();
		} else if (this == ColumnType.SALES_TOTAL) {
			return closing.getSalesTotal();
		} else if (this == ColumnType.INCOME_TOTAL) {
			return closing.getIncomeTotal();
		} else if (this == ColumnType.DIFFERENCE) {
			return closing.getDifference();
		}

		return 0.0D;
	}
}
