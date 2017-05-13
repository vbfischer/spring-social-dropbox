package org.springframework.social.dropbox.connect;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 * @author Svetoslav Videnov
 */
@Slf4j
public class DropboxAdapter implements ApiAdapter<DbxClientV2> {
	
    @Override
    public boolean test(DbxClientV2 dbx) {
        try {
			dbx.files().listFolder("");
            return true;
        } catch (DbxException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(DbxClientV2 dbx, ConnectionValues values) {
		try {
			FullAccount account = dbx.users().getCurrentAccount();
			values.setProviderUserId(account.getAccountId());
			values.setDisplayName(account.getName().getDisplayName());
			values.setProfileUrl(account.getReferralLink());
		} catch (DbxException ex) {
			log.warn("Exception dropbox adapter while setting connection values.", ex);
		}
    }

    @Override
    public UserProfile fetchUserProfile(DbxClientV2 dbx) {
		try {
			FullAccount account = dbx.users().getCurrentAccount();
			return new UserProfileBuilder()
					.setName(account.getName().getDisplayName())
					.setUsername(account.getEmail())
					.setEmail(account.getEmail())
					.build();
		} catch (DbxException ex) {
			log.warn("Exception dropbox adapter while fetching user profile.", ex);
			return null;
		}
    }

    @Override
    public void updateStatus(DbxClientV2 dropboxApi, String s) {
        // Not Supported
    }
}
