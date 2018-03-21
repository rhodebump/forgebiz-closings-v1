package com.forgebiz.closings.client;

public enum ColumnType {

	SALES_1,SALES_2,SALES_3,INCOME_1;
	public  double getValue(Closing closing) {

		if (ct == ColumnType.SALES_1) {
			return closing.getSales1();
		}else 		if (ct == ColumnType.SALES_2) {
			return closing.getSales2();
		}

		
		return 1.25D;
	}
}
