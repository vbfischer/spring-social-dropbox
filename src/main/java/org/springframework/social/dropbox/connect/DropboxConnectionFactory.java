package org.springframework.social.dropbox.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.dropbox.api.Dropbox;

/**
 * Dropbox Connection Factory
 * 
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public class DropboxConnectionFactory extends OAuth2ConnectionFactory<Dropbox> {
	
	/**
	 * Dropbox Connection Factory
	 * 
	 * appFolder must align with how app is registered in Dropbox.
	 * App can be allowed access to only an app folder (default)
	 * or the whole of Dropbox
	 * 
	 * @param appKey Registered key
	 * @param appSecret Registered
	 * @param appFolder Whether to use sandboxed app folder or access whole of Dropbox
	 */
    public DropboxConnectionFactory(String appKey, String appSecret, boolean appFolder) {
        super("dropbox", new DropboxServiceProvider(appKey, appSecret, appFolder), new DropboxAdapter());
    }
}
