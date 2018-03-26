package com.forgebiz.closings.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class ConfirmDialogBox extends DialogBox {

	public ConfirmDialogBox(String confirmMessage, final AsyncCallback callback) {
		// Set the dialog box's caption.
		setText(confirmMessage);

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		// DialogBox is a SimplePanel, so you have to set its widget property to
		// whatever you want its contents to be.
		FlowPanel fp = new FlowPanel();
		Button ok = new Button("OK");
		ok.setStyleName("btn btn-primary");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmDialogBox.this.hide();
				callback.onSuccess(null);
			}
		});
		Button cancel = new Button("Cancel");
		cancel.setStyleName("btn btn-primary");
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmDialogBox.this.hide();
				callback.onFailure(null);

			}
		});

		fp.add(ok);
		fp.add(cancel);

		setWidget(fp);
	}
}
