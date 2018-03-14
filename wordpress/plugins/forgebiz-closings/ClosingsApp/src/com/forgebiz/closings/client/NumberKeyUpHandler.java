package com.forgebiz.closings.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NumberKeyUpHandler implements KeyUpHandler {

	private ClosingPanel closingPanel = null;

	public void addKeyUpHandler(TextBox textBox, FlowPanel fp) {
		flowPanelMap.put(textBox, fp);
		textBox.addKeyUpHandler(this);

	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}

	Map<TextBox, FlowPanel> flowPanelMap = new HashMap<TextBox, FlowPanel>();

	public void onKeyUp(KeyUpEvent event) {
		Object sender = event.getSource();
		if (sender instanceof Widget) {
			TextBox widget = (TextBox) sender;
			FlowPanel fp = flowPanelMap.get(widget);

			boolean valid = ClosingsApp.isValidNumber(widget);
			if (valid) {
				fp.removeStyleName("has-error");
				fp.addStyleName("has-success");

			} else {
				fp.removeStyleName("has-success");
				fp.addStyleName("has-error");
			}

			fp.addStyleName("has-feedback");

			closingPanel.calculateAll();
		}

	}
}
