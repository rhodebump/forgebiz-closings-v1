package com.forgebiz.closings.client;

public enum ColumnType {

	/*
	 * //total open cash //total close cash //total sales //total income
	 * //difference //notes
	 * 
	 */
	OPEN_CASH_TOTAL("Open Cash Total"), CLOSE_CASH_TOTAL("Close Cash Total"), SALES_TOTAL("Sales Total"), INCOME_TOTAL(
			"Income Total"), DIFFERENCE("Difference"), SALES_1("Sales 1"), SALES_2("Sales 2"), SALES_3(
					"Sales 3"), SALES_4("Sales 4"), SALES_5("Sales 5"), SALES_6("Sales 6"), SALES_7("Sales 7"), SALES_8(
							"Sales 8"), SALES_9("Sales 9"), INCOME_1("Income 1"), INCOME_2(
									"Income 2"), INCOME_3("Income 3"), INCOME_4("Income 4"), INCOME_5(
											"Income 5"), INCOME_6("Income 6"), INCOME_7(
													"Income 7"), INCOME_8("Income 8"), INCOME_9("Income 9");

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
		} else if (this == ColumnType.SALES_1) {
			return closing.getSales1();
		} else if (this == ColumnType.SALES_2) {
			return closing.getSales2();
		} else if (this == ColumnType.SALES_3) {
			return closing.getSales3();
		} else if (this == ColumnType.SALES_4) {
			return closing.getSales4();
		} else if (this == ColumnType.SALES_5) {
			return closing.getSales5();
		} else if (this == ColumnType.SALES_6) {
			return closing.getSales6();
		} else if (this == ColumnType.SALES_7) {
			return closing.getSales7();
		} else if (this == ColumnType.SALES_8) {
			return closing.getSales8();
		} else if (this == ColumnType.SALES_9) {
			return closing.getSales9();
		} else if (this == ColumnType.INCOME_1) {
			return closing.getIncome1();
		} else if (this == ColumnType.INCOME_2) {
			return closing.getIncome2();
		} else if (this == ColumnType.INCOME_3) {
			return closing.getIncome3();
		} else if (this == ColumnType.INCOME_4) {
			return closing.getIncome4();
		} else if (this == ColumnType.INCOME_5) {
			return closing.getIncome5();
		} else if (this == ColumnType.INCOME_6) {
			return closing.getIncome6();
		} else if (this == ColumnType.INCOME_7) {
			return closing.getIncome7();
		} else if (this == ColumnType.INCOME_8) {
			return closing.getIncome8();
		} else if (this == ColumnType.INCOME_9) {
			return closing.getIncome9();
		}

		return 0.0D;
	}
}
