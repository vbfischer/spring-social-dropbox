package org.springframework.social.dropbox.connect;

import com.dropbox.core.v2.DbxClientV2;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * Dropbox Connection Factory
 * 
 * @author Bryce Fischer
 * @author Robert Drysdale
 * @author Svetoslav Videnov
 */
public class DropboxConnectionFactory extends OAuth2ConnectionFactory<DbxClientV2> {
	
	/**
	 * Dropbox Connection Factory
	 * 
	 * @param appKey Registered key
	 * @param appSecret Registered
	 * @param clientIdentifier Client identifier typically in the form "Name/Version" to be used in the User-Agent header
	 */
    public DropboxConnectionFactory(String appKey, String appSecret, String clientIdentifier) {
        super("dropbox", 
				new DropboxServiceProvider(appKey, appSecret, clientIdentifier), 
				new DropboxAdapter());
    }
}
