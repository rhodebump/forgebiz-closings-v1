package com.forgebiz.closings.client;

public enum ColumnType {

	SALES_1("Sales 1"),SALES_2("Sales 2"), INCOME_1("Income 1"),INCOME_2("Income 2");
	
	private String name = null;
	
	public String getName() {
		return name;
	}
	
	ColumnType(String name) {
		this.name = name;
	}
	//SALES_1, SALES_2, SALES_3, INCOME_1;
	public double getValue(Closing closing) {

		if (this == ColumnType.SALES_1) {
			return closing.getSales1();
		} else if (this == ColumnType.SALES_2) {
			return closing.getSales2();
		}

		return 1.25D;
	}
}
