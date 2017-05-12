package org.springframework.social.dropbox.connect;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 * @author Svetoslav Videnov
 */
public class DropboxServiceProvider extends AbstractOAuth2ServiceProvider<DbxClientV2> {
	private final String clientIdentifier;
	
    public DropboxServiceProvider(String appKey, String appSecret, String clientIdentifier) {
        super(getOAuth2Template(appKey, appSecret));
		this.clientIdentifier = clientIdentifier;
    }
	
	private static OAuth2Template getOAuth2Template(String appKey, String clientSecret) {
		OAuth2Template oauth2 = new OAuth2Template(appKey, clientSecret, 
				"https://www.dropbox.com/oauth2/authorize", 
				"https://api.dropboxapi.com/oauth2/token");
		return oauth2;
	}

    @Override
    public DbxClientV2 getApi(String accessToken) {
		DbxRequestConfig config = new DbxRequestConfig(this.clientIdentifier);
		return new DbxClientV2(config, accessToken);
    }
}
