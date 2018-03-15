package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class NumberPanelHelper {
	

	public static FlowPanel getFlowPanel(Label label, TextBox textBox) {
		FlowPanel fp = new FlowPanel();
		fp.setStyleName("form-group");
		fp.add(label);
		label.setStyleName("control-label");
		fp.add(textBox);
		textBox.setStyleName("form-control");
		//add(fp);

		Label fp2 = new Label();
		fp2.setText("That's not a number, please fix it.");
		fp2.setStyleName("invalid-feedback");

		fp.add(fp2);
		Label fp3 = new Label();
		fp3.setText("Looks good!");
		fp3.setStyleName("valid-feedback");

		fp.add(fp3);

		//addKeyUpHandler(fp, textBox);
		return fp;
	}
	
	public static boolean isValidNumber(TextBox textBox) {
		try {
			Double.parseDouble(textBox.getValue());
			GWT.log("got valid number");
			return true;
		} catch (Exception e) {
			GWT.log("got invalid number");
			return false;
		}
		
	}

}
