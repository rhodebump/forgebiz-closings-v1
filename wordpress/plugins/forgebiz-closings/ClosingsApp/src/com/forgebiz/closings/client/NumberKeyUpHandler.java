
	package com.forgebiz.closings.client;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

	public class NumberKeyUpHandler implements KeyUpHandler {
		
			private ClosingPanel closingPanel = null;
			
			

			
		public void 	addKeyUpHandler(FlowPanel fp ,TextBox textBox) {
		flowPanelMap.put(textBox,fp);
		textBox.addKeyUpHandler(numberKeyUpHandler);


	}
	
	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}
		Map<TextBox,FlowPanel> flowPanelMap = new HashMap<TextBox,FlowPanel> ();
		public void onKeyUp(KeyUpEvent event) {
			         Object sender = event.getSource();
			if (sender instanceof Widget) {
				     Widget widget = (Widget) sender;
				       FlowPanel fp = flowPanelMap.get(widget);
	
			
			if (valid) {
				fp.removeStyleName("has-error");
				fp.setStyleName("has-success");
				
			} else {
				p.removeStyleName("has-success");
				fp.setStyleName("has-error");
			}
			
			fp.addStyleName("has-feedback");
			
			closingPanel.calculateAll();
			}

		}
	}
	
	