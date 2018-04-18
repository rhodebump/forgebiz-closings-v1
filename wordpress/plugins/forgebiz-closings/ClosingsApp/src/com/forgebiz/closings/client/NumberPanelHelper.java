package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NumberPanelHelper {
	
	public static void decorateBootStrapPanel(FlowPanel fp,Label label, Widget textBox) {
		//FlowPanel fp = new FlowPanel();
		fp.setStyleName("form-group");
		fp.add(label);
		label.setStyleName("control-label");
		fp.add(textBox);
		textBox.setStyleName("form-control");
		
	}
	
	public static FlowPanel getBootStrapPanel(Label label, Widget textBox) {
		FlowPanel fp = new FlowPanel();
		decorateBootStrapPanel(fp,label,textBox);
		return fp;
		
	}
	
	public static void getFlowPanel2(FlowPanel fp, Label label, Widget textBox, String invalidText) {

		//add(fp);
		//FlowPanel fp = getBootStrapPanel(label,textBox);
		decorateBootStrapPanel(fp,label,textBox);
		
		Label fp2 = new Label();
		fp2.setText(invalidText);
		fp2.setStyleName("invalid-feedback");

		fp.add(fp2);
		Label fp3 = new Label();
		fp3.setText("Looks good!");
		fp3.setStyleName("valid-feedback");

		fp.add(fp3);

		//addKeyUpHandler(fp, textBox);
		
	}
	
	public static FlowPanel getFlowPanel(Label label, TextBox textBox) {

		//add(fp);
		FlowPanel fp = getBootStrapPanel(label,textBox);
		
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
