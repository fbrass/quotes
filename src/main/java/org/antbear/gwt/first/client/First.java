package org.antbear.gwt.first.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class First implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final FirstServiceAsync firstService = GWT.create(FirstService.class);
//    private final Messages messages = GWT.create(Messages.class);

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        final TextBox inputText = new TextBox();
        inputText.setText("Predefined text");
//        inputText.setText(messages.text());
        final Button okButton = new Button("Click");
//        final Button okButton = new Button(messages.button());
        final Label outputText = new Label();
        final Label errorLabel = new Label();

        // We can add style names to widgets
        okButton.addStyleName("okButton");

        // Add the nameField and sendButton to the RootPanel
        RootPanel.get("inputTextContainer").add(inputText);
        RootPanel.get("buttonContainer").add(okButton);
        RootPanel.get("outputTextContainer").add(outputText);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Focus the cursor on the name field when the app loads
        inputText.setFocus(true);
        inputText.selectAll();

        // Create a handler for the button and textField
        class MyHandler implements ClickHandler, KeyUpHandler {

            /**
             * Fired when the user clicks on the Button.
             */
            @Override
            public void onClick(ClickEvent event) {
                sendTextToServer();
            }

            /**
             * Fired when the user types in the inputText.
             */
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendTextToServer();
                }
            }

            /**
             * Send the text from the inputText to the server and wait for a response.
             */
            private void sendTextToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                final String text = inputText.getText();
                if (null == text || 0 == text.length()) {
                    errorLabel.setText("Please enter at least one character");
                    return;
                }

                // Then, we send the input to the server.
                okButton.setEnabled(false);
                outputText.setText("");

                firstService.first(text, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(final Throwable caught) {
                        // Show the RPC error message to the user
                        outputText.addStyleName("serverResponseLabelError");
                        outputText.setText("An error occurred while "
                                + "attempting to contact the server. Please check your network "
                                + "connection and try again.");
                        okButton.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(final String result) {
                        outputText.removeStyleName("serverResponseLabelError");
                        outputText.setText("hello from client " + result);
                        okButton.setEnabled(true);
                    }
                });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        okButton.addClickHandler(handler);
        inputText.addKeyUpHandler(handler);
    }
}
