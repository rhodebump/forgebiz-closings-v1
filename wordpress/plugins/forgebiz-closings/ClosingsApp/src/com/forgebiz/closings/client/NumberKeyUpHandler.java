package com.forgebiz.closings.client;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NumberKeyUpHandler implements KeyUpHandler {

	private ClosingPanel closingPanel = null;



	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}



	public void onKeyUp(KeyUpEvent event) {
		Object sender = event.getSource();
		if (sender instanceof Widget) {
			TextBox widget = (TextBox) sender;
			//FlowPanel fp = flowPanelMap.get(widget);

			boolean valid = NumberPanelHelper.isValidNumber(widget);
			if (valid) {
				widget.removeStyleName("is-invalid");
				widget.addStyleName("is-valid");

			} else {
				widget.addStyleName("is-invalid");
				widget.removeStyleName("is-valid");
			}

			

			//closingPanel.calculateAll();
		}

	}
}
