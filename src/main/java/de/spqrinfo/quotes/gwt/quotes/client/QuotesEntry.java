package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class QuotesEntry implements EntryPoint {

    private final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);

    @Override
    public void onModuleLoad() {
        final RootPanel rootPanel = RootPanel.get();

        final QuotePanel quotePanel = new QuotePanel("Add a new quotation");
        rootPanel.getElement().appendChild(quotePanel.getElement());
        rootPanel.add(quotePanel);

//        final TextBox inputText = new TextBox();
//        inputText.setText("Predefined text");
//
//        final Button okButton = new Button("Click");
//        final Label outputText = new Label();
//        final Label errorLabel = new Label();
//
//        // We can add style names to rootPanel
//        okButton.addStyleName("okButton");
//
//        // Add the nameField and sendButton to the RootPanel
//        RootPanel.get("inputTextContainer").add(inputText);
//        RootPanel.get("buttonContainer").add(okButton);
//        RootPanel.get("outputTextContainer").add(outputText);
//        RootPanel.get("errorLabelContainer").add(errorLabel);
//
//        // Focus the cursor on the name field when the app loads
//        inputText.setFocus(true);
//        inputText.selectAll();
//
//        // Create a handler for the button and textField
//        class MyHandler implements ClickHandler, KeyUpHandler {
//
//            /**
//             * Fired when the user clicks on the Button.
//             */
//            @Override
//            public void onClick(ClickEvent event) {
//                sendTextToServer();
//            }
//
//            /**
//             * Fired when the user types in the inputText.
//             */
//            @Override
//            public void onKeyUp(KeyUpEvent event) {
//                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//                    sendTextToServer();
//                }
//            }
//
//            /**
//             * Send the text from the inputText to the server and wait for a response.
//             */
//            private void sendTextToServer() {
//                // First, we validate the input.
//                errorLabel.setText("");
//                final String text = inputText.getText();
//                if (null == text || 0 == text.length()) {
//                    errorLabel.setText("Please enter at least one character");
//                    return;
//                }
//
//                // Then, we send the input to the server.
//                okButton.setEnabled(false);
//                outputText.setText("");
//
//                quotesService.first(text, new AsyncCallback<String>() {
//                    @Override
//                    public void onFailure(final Throwable caught) {
//                        // Show the RPC error message to the user
//                        outputText.addStyleName("serverResponseLabelError");
//                        outputText.setText("An error occurred while "
//                                + "attempting to contact the server. Please check your network "
//                                + "connection and try again.");
//                        okButton.setEnabled(true);
//                    }
//
//                    @Override
//                    public void onSuccess(final String result) {
//                        outputText.removeStyleName("serverResponseLabelError");
//                        outputText.setText("hello from client " + result);
//                        okButton.setEnabled(true);
//                    }
//                });
//            }
//        }
//
//        // Add a handler to send the name to the server
//        MyHandler handler = new MyHandler();
//        okButton.addClickHandler(handler);
//        inputText.addKeyUpHandler(handler);
    }
}
