package org.antbear.gwt.first.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface FirstServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.antbear.gwt.first.client.FirstService
     */
    void first( java.lang.String text, AsyncCallback<java.lang.String> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static FirstServiceAsync instance;

        public static final FirstServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (FirstServiceAsync) GWT.create( FirstService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
