package org.antbear.gwt.first.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("firstService")
public interface FirstService extends RemoteService {
    String first(String text) throws IllegalArgumentException;
}
